/*
 * Copyright 2020 https://github.com/chenyacheng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chenyacheng.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

final class SnackBarManager {

    private static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static final int LONG_DURATION_MS = 2750;

    private final Object lock = new Object();
    boolean flag = false;
    private SnackBarRecord currentSnackBar;
    private SnackBarRecord nextSnackBar;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_TIMEOUT) {
                handleTimeout((SnackBarRecord) msg.obj);
                return true;
            }
            return false;
        }
    });

    private SnackBarManager() {
    }

    static SnackBarManager getInstance() {
        return Holder.INSTANCE;
    }

    void show(int duration, Callback callback) {
        synchronized (lock) {
            if (isCurrentSnackBarLocked(callback)) {
                currentSnackBar.duration = duration;
                mHandler.removeCallbacksAndMessages(currentSnackBar);
                scheduleTimeoutLocked(currentSnackBar);
            } else {
                if (isNextSnackBarLocked(callback)) {
                    nextSnackBar.duration = duration;
                } else {
                    nextSnackBar = new SnackBarRecord(duration, callback);
                }
                if (currentSnackBar == null || !cancelSnackBarLocked(currentSnackBar)) {
                    currentSnackBar = null;
                    showNextSnackBarLocked();
                }
            }
        }
    }

    private boolean isCurrentSnackBarLocked(Callback callback) {
        if (currentSnackBar != null && currentSnackBar.isSnackBar(callback)) {
            flag = true;
            return true;
        }
        return false;
    }

    private void scheduleTimeoutLocked(SnackBarRecord r) {
        int minusOne = -1;
        if (r.duration != minusOne) {
            int durationMs = SHORT_DURATION_MS;
            if (r.duration == SnackBar.LENGTH_LONG) {
                durationMs = LONG_DURATION_MS;
            }
            mHandler.removeCallbacksAndMessages(r);
            mHandler.sendMessageDelayed(Message.obtain(mHandler, MSG_TIMEOUT, r), durationMs);
        }
    }

    private boolean isNextSnackBarLocked(Callback callback) {
        return nextSnackBar != null && nextSnackBar.isSnackBar(callback);
    }

    private boolean cancelSnackBarLocked(SnackBarRecord record) {
        final Callback callback = record.callback.get();
        if (callback != null) {
            mHandler.removeCallbacksAndMessages(record);
            callback.dismiss();
            return true;
        } else {
            return false;
        }
    }

    private void showNextSnackBarLocked() {
        if (nextSnackBar != null) {
            currentSnackBar = nextSnackBar;
            nextSnackBar = null;
            final Callback callback = currentSnackBar.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                currentSnackBar = null;
            }
        }
    }

    void onDismissed(Callback callback) {
        synchronized (lock) {
            if (isCurrentSnackBarLocked(callback)) {
                currentSnackBar = null;
                if (nextSnackBar != null) {
                    showNextSnackBarLocked();
                } else {
                    flag = false;
                }
            }
        }
    }

    void onShown(Callback callback) {
        synchronized (lock) {
            if (isCurrentSnackBarLocked(callback)) {
                scheduleTimeoutLocked(currentSnackBar);
            }
        }
    }

    private void handleTimeout(SnackBarRecord record) {
        synchronized (lock) {
            if (currentSnackBar == record || nextSnackBar == record) {
                cancelSnackBarLocked(record);
            }
        }
    }

    interface Callback {
        /**
         * 显示
         */
        void show();

        /**
         * 取消
         */
        void dismiss();
    }

    /**
     * 外部类初始化的时候不会初始化该内部类
     * 只有当调用getInstance方法时候才会初始化
     */
    private static class Holder {
        private static final SnackBarManager INSTANCE = new SnackBarManager();
    }

    private static class SnackBarRecord {
        private final WeakReference<Callback> callback;
        private int duration;

        SnackBarRecord(int duration, Callback callback) {
            this.callback = new WeakReference<>(callback);
            this.duration = duration;
        }

        boolean isSnackBar(Callback callback) {
            return callback != null && this.callback.get() == callback;
        }
    }
}

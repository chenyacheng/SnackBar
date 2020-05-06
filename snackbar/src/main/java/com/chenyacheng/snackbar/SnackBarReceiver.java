/*
 * Copyright 2020 https://github.com/yacheng199306
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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * SnackBar广播
 *
 * @author chenyacheng
 * @date 2019/07/12
 */
public class SnackBarReceiver extends BroadcastReceiver {

    private WeakReference<Activity> activityWeakReference;

    public SnackBarReceiver(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String className = activityWeakReference.get().getClass().getSimpleName();
        String content = intent.getStringExtra(className);
        SnackBarBuilder.getInstance().builder(activityWeakReference.get(), R.layout.snack_bar, 0, content, SnackBar.LENGTH_SHORT);
        // 注销广播
        context.unregisterReceiver(this);
    }
}

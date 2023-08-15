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

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * SnackBar类
 *
 * @author chenyacheng
 * @date 2019/07/12
 */
public final class SnackBar {

    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    private static final int MSG_SHOW = 10;
    private static final int MSG_DISMISS = 11;
    private static final Handler HANDLER;

    static {
        HANDLER = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_SHOW:
                        ((SnackBar) msg.obj).showView();
                        return true;
                    case MSG_DISMISS:
                        ((SnackBar) msg.obj).hideView(false);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private final SnackBarManager.Callback managerCallback = new SnackBarManager.Callback() {
        @Override
        public void show() {
            HANDLER.sendMessage(HANDLER.obtainMessage(MSG_SHOW, SnackBar.this));
        }

        @Override
        public void dismiss() {
            HANDLER.sendMessage(HANDLER.obtainMessage(MSG_DISMISS, SnackBar.this));
        }
    };
    private int duration;
    private ViewGroup targetParent;
    private View viewLayout;

    void make(View view, int layout, int resId, CharSequence text, int duration) {
        hideView(false);
        ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        } else {
            targetParent = parent;
            viewLayout = LayoutInflater.from(parent.getContext()).inflate(layout, targetParent, false);
            setText(text, resId);
            setDuration(duration);
        }
    }

    void hideView(boolean now) {
        SnackBarManager.getInstance().onDismissed(this.managerCallback);
        if (null != this.viewLayout) {
            final ViewParent parent = this.viewLayout.getParent();
            if (parent instanceof ViewGroup) {
                if (now) {
                    ((ViewGroup) parent).removeView(this.viewLayout);
                } else {
                    AnimationUtils.getInstance().hideAnimation((ViewGroup) parent, this.viewLayout);
                }
            }
        }
        targetParent = null;
        viewLayout = null;
    }

    private ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }
                fallback = (ViewGroup) view;
            }
            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return fallback;
    }

    private void setText(CharSequence message, int resId) {
        final TextView tv = viewLayout.findViewById(R.id.snack_bar_text);
        if (resId != 0) {
            Drawable drawableIcon = viewLayout.getResources().getDrawable(resId, null);
            if (null != drawableIcon) {
                drawableIcon.setBounds(0, 0, drawableIcon.getMinimumWidth(), drawableIcon.getMinimumHeight());
                tv.setCompoundDrawables(null, drawableIcon, null, null);
            }
        }
        tv.setText(message);
    }

    private void setDuration(int duration) {
        this.duration = duration;
    }

    void show() {
        SnackBarManager.getInstance().show(duration, managerCallback);
    }

    private void showView() {
        if (null != viewLayout) {
            if (viewLayout.getParent() == null) {
                targetParent.addView(viewLayout);
            }
            AnimationUtils.getInstance().showAnimation(viewLayout);
            SnackBarManager.getInstance().onShown(managerCallback);
        }
    }
}

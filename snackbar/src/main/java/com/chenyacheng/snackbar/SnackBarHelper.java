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

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 使用SnackBar入口之一，另一个入口是SnackBarBuilder
 *
 * @author chenyacheng
 * @date 2019/07/12
 */
public final class SnackBarHelper {

    private static String className;

    public static void startActivity(Activity activity, Intent intent) {
        SnackBarReceiver snackBarReceiver = new SnackBarReceiver(activity);
        className = activity.getClass().getSimpleName();
        // 实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("snackBar");
        activity.registerReceiver(snackBarReceiver, intentFilter);
        activity.startActivity(intent);
    }

    public static void finish(Activity activity, String string) {
        Intent intent = new Intent();
        intent.setAction("snackBar");
        intent.putExtra(className, string);
        // 发送一个广播
        activity.sendBroadcast(intent);
        activity.finish();
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        SnackBarReceiver snackBarReceiver = new SnackBarReceiver(activity);
        className = activity.getClass().getSimpleName();
        // 实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("snackBar");
        activity.registerReceiver(snackBarReceiver, intentFilter);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void setResult(Activity activity, int resultCode, String string) {
        Intent intent = new Intent();
        intent.setAction("snackBar");
        intent.putExtra(className, string);
        // 发送一个广播
        activity.sendBroadcast(intent);
        activity.setResult(resultCode);
        activity.finish();
    }
}

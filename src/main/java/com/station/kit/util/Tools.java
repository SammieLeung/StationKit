package com.firelfy.util;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.List;

public class Tools {
    protected static Application app;

    public static void init(Application application) {
        app = application;
    }

    public static void init(Activity activity) {
        if (app == null) {
            init(activity.getApplication());
        }
    }

    public static Application getApp() {
        return app;
    }


    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = getApp().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Throwable e) {
            Tools.printStackTrace(e);
            return false;
        }
    }

    // 存储权限
    public static boolean canOperateStorage() {
        try {
            return ContextCompat.checkSelfPermission(getApp(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            printStackTrace(e);
            return false;
        }
    }

    // 有权限吗
    public static boolean checkSelfPermission(String permission) {
        try {
            return ContextCompat.checkSelfPermission(getApp(), permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            printStackTrace(e);
            return false;
        }
    }

    public static boolean isUIThread() {
        try {
            return Looper.myLooper() == Looper.getMainLooper();
        } catch (Throwable e) {
            printStackTrace(e);
            return false;
        }
    }

    public static void runOnUiThread(Runnable run) {
        new Handler(Looper.getMainLooper()).post(run);
    }

    public static void runOnUiThread(Runnable run, long delayMillis) {
        if (delayMillis < 100)
            runOnUiThread(run);
        else
            new Handler(Looper.getMainLooper()).postDelayed(run, delayMillis);
    }

    public static void toast(final Exception e) {
        if (e != null) {
            toast(e.getMessage());
        }
    }

    public static void toast(final CharSequence msg) {
        debugLog(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(getApp(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void toast(String format, Object... args) {
        toast(String.format(format, args));
    }

    public static void toast(int resId) {
        toast(getString(resId));
    }


    public final static String getString(@StringRes int resId) {
        try {
            return getApp().getResources().getString(resId);
        } catch (Throwable e) {
            Tools.printStackTrace("firefly_debug", e);
            return "";
        }
    }

    public final static String getString(@StringRes int resId, Object... formatArgs) {
        try {
            return getApp().getResources().getString(resId, formatArgs);
        } catch (Throwable e) {
            Tools.printStackTrace("firefly_debug", e);
            return "";
        }
    }

    public static void printStackTrace(String tag, Throwable e) {
        Log.e(tag, e.getMessage(), e);
    }

    public static void printStackTrace(Object tag, Throwable e) {
        Log.e(tag.getClass().getSimpleName(), e.getMessage(), e);
    }

    public static void printStackTrace(Throwable e) {
        Log.e("firefly_debug", e.getMessage(), e);
    }

    public static void printStackTrace(String msg) {
        Log.e("firefly_debug", msg);
    }

    public static void debugLog(CharSequence msg) {
        log_d("firefly_debug", "" + msg);
    }

    public static void debugLog(String format, Object... args) {
        debugLog(String.format(format, args));
    }

    public static void debugLog(int tag, String format, Object... args) {
        debugLog(tag, String.format(format, args));
    }

    public static void debugLog(int tag, CharSequence msg) {
        log_d("firefly_debug" + tag, "" + msg);
    }

    public static void debugLogE(String format, Object... args) {
        if (!isApkDebugable())
            return;

        String tag = "firefly_debug";
        String msg = String.format(format, args);
        int maxLength = 2001 - tag.length();
        while (msg.length() > maxLength) {
            Log.e(tag, msg.substring(0, maxLength));
            msg = msg.substring(maxLength);
        }
        Log.e(tag, msg);
    }

    public static void debugLog(Bundle bundle) {
        try {
            StringBuilder sb = new StringBuilder();
            for (String key : bundle.keySet()) {
                Object obj = bundle.get(key);
                sb.append("\nkey:").append(key).append(", value:").append(obj);
            }
            debugLog(sb.toString());
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    public static void debugLog(List<?> mList) {
        if (mList != null)
            debugLog("size=%s: %s", mList.size(), mList.toString());
        else
            debugLog("List == null");
    }

    public static void debugLog() {
        debugLog("============================================");
    }

    public static void debugTrack() {
        try {
            StackTraceElement[] st = Thread.currentThread().getStackTrace();
            if (st == null) {
                debugLog("StackTraceElement == null");
                return;
            }

            StringBuffer sbf = new StringBuffer();
            for (StackTraceElement e : st) {
                if (sbf.length() > 0) {
                    sbf.append(" <- ");
                    sbf.append(System.getProperty("line.separator"));
                }
                sbf.append(String.format("%s.%s() %s", e.getClassName(), e.getMethodName(), e.getLineNumber()));
            }
            debugLog(sbf);
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void log_d(String tag, String msg) {
        if (!isApkDebugable())
            return;
        int maxLength = 2001 - tag.length();
        while (msg.length() > maxLength) {
            Log.d(tag, msg.substring(0, maxLength));
            msg = msg.substring(maxLength);
        }
        Log.d(tag, msg);
    }

    public static boolean isActivityDestroyed(Fragment fragment) {
        try {
            return fragment != null && fragment.isAdded() && isActivityDestroyed(fragment.getActivity());
        } catch (Throwable e) {
            Tools.printStackTrace(e);
            return true;
        }
    }

    public static boolean isActivityDestroyed(Activity activity) {
        try {
            if (activity == null || activity.isFinishing())
                return true;

            if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed())
                return true;

            return false;
        } catch (Throwable e) {
            Tools.printStackTrace(e);
            return true;
        }
    }

    public static String getNameByPath(String pVideoPath) {
        String videoName = "";
        if (pVideoPath.startsWith("http:") | pVideoPath.startsWith("https:") |
                pVideoPath.startsWith("rtsp:") | pVideoPath.startsWith("smb:")) {
            videoName = pVideoPath;
        } else {
            if (pVideoPath.lastIndexOf("/") < pVideoPath.lastIndexOf("."))
                videoName = pVideoPath.substring(pVideoPath.lastIndexOf("/") + 1, pVideoPath.lastIndexOf("."));
            else
                videoName = pVideoPath.substring(pVideoPath.lastIndexOf("/") + 1);
        }
        return videoName;
    }
}

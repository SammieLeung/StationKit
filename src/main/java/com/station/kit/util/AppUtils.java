package com.station.kit.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * author: Sam Leung
 * date:  2021/12/11
 */
public class AppUtils {
    public static boolean isAppStart(Context context, String packageName){
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(am!=null){
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =am.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo info:runningAppProcesses){
                if(info.processName.equals(packageName))
                    return true;
            }
        }
        return false;
    }
}

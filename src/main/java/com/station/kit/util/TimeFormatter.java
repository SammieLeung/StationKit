package com.station.kit.util;

/**
 * author: Sam Leung
 * date:  2022/11/11
 */
public class TimeFormatter {

    public static String formatMillisecondToHumanReadableString(long millisecond) {
        long second = Math.floorDiv(millisecond , 1000L);
        long hour = second / 3600;
        long min = second % 3600 / 60;
        long sec = second % 3600 % 60;
        return hour+":"+(min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec);
    }

}

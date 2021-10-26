package com.station.kit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: Sam Leung
 * date:  2021/10/25
 */
public class RegexMatcher {

    public static Matcher match(String regex, String str) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);
        return m;
    }
}

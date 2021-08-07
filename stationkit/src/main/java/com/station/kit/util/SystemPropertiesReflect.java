package com.station.kit.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: Sam Leung
 * date:  2021-01-19
 */
public class SystemPropertiesReflect {
    static String CLASS_SYSTEMPROPERTIES = "android.os.SystemProperties";
    static Class clazz_SystemProperties;
    static Method _getBoolean;
    static Method _get;
    static Method _set;

    static {
        clazz_SystemProperties = getClassSystemProperties();
        loadMethods();
    }

    private static Class getClassSystemProperties() {
        try {
            return Class.forName(CLASS_SYSTEMPROPERTIES);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void loadMethods() {
        try {
            _getBoolean = clazz_SystemProperties.getDeclaredMethod("getBoolean", String.class, boolean.class);
            _get = clazz_SystemProperties.getDeclaredMethod("get", String.class, String.class);
            _set=clazz_SystemProperties.getDeclaredMethod("set",String.class,String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean getBoolean(String key, boolean defalutValue) {
        try {
            return (boolean) _getBoolean.invoke(clazz_SystemProperties, key, defalutValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String get(String key, String def) {
        try {
            return (String) _get.invoke(clazz_SystemProperties, key, def);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set(String key, String val) {
        try{
            _set.invoke(clazz_SystemProperties,key,val);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}

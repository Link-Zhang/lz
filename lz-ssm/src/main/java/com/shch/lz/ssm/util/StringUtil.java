package com.shch.lz.ssm.util;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Link at 16:12 on 4/4/18.
 */
public class StringUtil {
    private static Pattern underlinePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String underlineToHump(String string) {
        if (null == string || "".equals(string)) {
            return string;
        }
        string = string.toLowerCase();
        Matcher matcher = underlinePattern.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        string = stringBuffer.toString();
        string = string.substring(0, 1).toUpperCase() + string.substring(1);
        return string;
    }

    public static String humpToUnderline(String string) {
        Matcher matcher = humpPattern.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString().substring(1);
    }

    public static String toLowerCaseInitial(String string) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        if (Character.isLowerCase(string.charAt(0))) {
            return string;
        } else {
            return (new StringBuffer()).append(Character.toLowerCase(string.charAt(0))).append(string.substring(1)).toString();
        }
    }

    public static String toUpperCaseInitial(String string) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        if (Character.isUpperCase(string.charAt(0))) {
            return string;
        } else {
            return (new StringBuffer()).append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).toString();
        }
    }

    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getString(Object object) {
        return getString(object, "");
    }

    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

//    public static void main(String[] args) {
//        String result = StringUtil.underlineToHump("hello_world_fuck");
//        System.out.println(result);
//        System.out.println(StringUtil.humpToUnderline(result));
//        String rs = StringUtil.humpToUnderline("HelloWorldFuck");
//        System.out.println(rs);
//        System.out.println(StringUtil.underlineToHump(rs));
//    }

}

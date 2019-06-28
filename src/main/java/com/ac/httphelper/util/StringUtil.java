package com.ac.httphelper.util;

import java.util.Objects;

/**
 * @author:artificialunintelligent
 * @Date:2019-06-25
 * @Time:22:04
 */
public class StringUtil {

    /**
     * 字符串为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * 字符串非空
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && s.trim().length() != 0;
    }

    /**
     * 任意一个字符串为空
     * @param s
     * @return
     */
    public static boolean isAnyEmpty(String... s){
        if (Objects.isNull(s)){
            return true;
        }
        for(String str : s){
            if(isEmpty(str)){
                return false;
            }
        }
        return true;
    }

    /**
     * 反转一个字符串
     * @param s
     * @return
     */
    public static String reverse(String s){
        if(StringUtil.isEmpty(s)){
            return s;
        }
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

}

package cn.huse.trace.sdk.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author: huanxi
 * @date: 2019/4/27 22:57
 */
public class StringUtil {
    public static String formatJsonString(String s) {
        return StringEscapeUtils.unescapeJava(s.substring(1, s.length() - 1));
    }
}

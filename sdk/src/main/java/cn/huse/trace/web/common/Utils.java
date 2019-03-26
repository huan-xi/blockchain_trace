//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.huse.trace.web.common;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {

    public static String passwd(String s) {
        return DigestUtils.md5Hex("imp" + s);
    }

    public static String generateToken() {
        return DigestUtils.md5Hex("imp" + System.currentTimeMillis() + Math.random());
    }
    public static String getUUID() {
        return DigestUtils.md5Hex("imp" + System.currentTimeMillis() + Math.random());
    }
}

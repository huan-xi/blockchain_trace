//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.huse.trace.web.controller;

import cn.huse.trace.web.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class LoginCache {
    private static Map<String, User> login = new HashMap();

    public LoginCache() {
    }

    public static void login(String token, User user) {
        login.put(token, user);
    }

    public static User getUser(String token) {
        return (User) login.get(token);
    }

    public static void remote(String token) {
        login.remove(token);
    }
}

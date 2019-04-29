package cn.huse.trace.web.common.auth.verify;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: huanxi
 * @date: 2019-04-16 00:21
 */
public interface Authenticator {
    boolean doVerify(HttpServletRequest request, HttpServletResponse response);
}

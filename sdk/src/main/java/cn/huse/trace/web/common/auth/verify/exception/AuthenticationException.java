package cn.huse.trace.web.common.auth.verify.exception;

/**
 * 权限认证异常
 * @author: huanxi
 * @date: 2019-04-16 00:31
 */
public class AuthenticationException extends Exception {
    public AuthenticationException(String msg) {
        super(msg);
    }
}

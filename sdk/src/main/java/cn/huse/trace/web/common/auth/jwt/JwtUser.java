package cn.huse.trace.web.common.auth.jwt;

/**
 * @author: huanxi
 * @date: 2019-04-15 09:33
 */
public interface JwtUser {
    Object getJwtUserId();
    void setJwtUserId(Object subject);

    void setJwtStatus(Object status);

    Object getJwtStatus();
}

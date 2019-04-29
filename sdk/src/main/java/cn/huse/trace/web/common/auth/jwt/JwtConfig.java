package cn.huse.trace.web.common.auth.jwt;

/**
 * @author: huanxi
 * @date: 2019-04-15 09:38
 */

public class JwtConfig {
    private static long expirationDate=10000000000L; //过期时间 ms
    private static String jwtSecret="c13d6445755cddf860b425a6b128ed1c"; //秘钥

    public static long getExpirationDate() {
        return expirationDate;
    }

    public static void setExpirationDate(long expirationDate) {
        JwtConfig.expirationDate = expirationDate;
    }

    public static String getJwtSecret() {
        return jwtSecret;
    }

    public static void setJwtSecret(String jwtSecret) {
        JwtConfig.jwtSecret = jwtSecret;
    }
}

package cn.huse.trace.web.common.auth.jwt;

import cn.huse.trace.web.common.auth.jwt.exception.JwtParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JWT工具类
 *
 */
@Component
public class JwtUserTokenUtil implements Serializable {

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + JwtConfig.getExpirationDate());
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, JwtConfig.getJwtSecret()).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(JwtConfig.getJwtSecret()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @return 令牌
     */
    public static String generateToken(JwtUser user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", user.getJwtUserId());
        claims.put("status", user.getJwtStatus());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserIdFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 获取jwtUser 对象
     * @param token
     * @return
     * @throws JwtParseException
     */
    public static JwtUser getUserFormToken(String token,Class u) throws JwtParseException {
        JwtUser user= null;
        try {
            user = (JwtUser) u.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Claims claims = getClaimsFromToken(token);
            user.setJwtStatus(claims.get("status"));
            user.setJwtUserId(claims.getSubject());
        }catch (Exception e){
            //抓到空指针异常,解析失败
            e.printStackTrace();
            throw new JwtParseException("parse this token error!");
        }

        return user;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public static Boolean validateToken(String token, JwtUser user) {
        String username = getUserIdFromToken(token);
        return (username.equals(user.getJwtUserId()) && !isTokenExpired(token));
    }

}

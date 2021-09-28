package com.macro.mall.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/18 10:25 上午
 * @desc
 */
@Component
public class JwtTokenUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 从token中获取用户名
     */
    public String retrieveUsername(String token) {
        String username = null;
        try{
            username = claimsFrom(token).getSubject();
        }catch(Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = retrieveUsername(token);
        boolean equal = username.equals(userDetails.getUsername());
        boolean expired = isExpired(token);
        return (equal == true) && (expired == false);
    }

    /**
     * 根据用户生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以刷新
     */
    public boolean canRefresh(String token) {
        return isExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String old) {
        String token = old.substring(tokenHead.length());
        Claims claims = claimsFrom(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }



    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpiration())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Claims claimsFrom(String token) {
        Claims payload = null;
        try{
            payload = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(Exception e) {
            log.info("JWT格式验证失败:{}" + token);
        }
        return payload;
    }

    private boolean isExpired(String token) {
        Date expired = retrieveExpiredDate(token);
        Date now = new Date();
        return expired.before(now);
    }

    private Date retrieveExpiredDate(String token){
        return claimsFrom(token).getExpiration();
    }
}

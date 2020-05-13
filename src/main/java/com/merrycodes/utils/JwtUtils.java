package com.merrycodes.utils;

import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.exception.TokenException;
import com.merrycodes.model.entity.JwtPayload;
import com.merrycodes.model.entity.User;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jdk.nashorn.internal.parser.Token;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 成功/校验Token的工具类
 *
 * @author MerryCodes
 * @date 2020/5/9 23:33
 */
public class JwtUtils {

    /**
     * 创建Token，私钥加密
     *
     * @param key        key-value（底层Map实现）
     * @param privateKey 私钥
     * @param user       封装用户对象
     * @param expire     Token过期时间,单位分钟
     * @return 生成的Token字符串
     */
    public static String generateToken(String key, PrivateKey privateKey, User user, Long expire) {
        LocalDateTime now = LocalDateTime.now();
        return Jwts.builder()
                .setId(generateJwtId())
                .setHeaderParam("type", "JWT")
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .claim(key, JsonUtils.writeValue(user).orElse(null))
                .setExpiration(DateUtils.asDate(now.plusMinutes(expire)))
                .setSubject(user.getUsername())
                .setIssuedAt(DateUtils.asDate(now))
                .compact();
    }

    /**
     * 生成Jwt的id
     *
     * @return id字符串
     */
    private static String generateJwtId() {
        return new String(Base64.encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 解析Token
     *
     * @param token     Token
     * @param publicKey 公钥
     * @return {@link Claims}
     */
    private static Claims parserToken(String token, PublicKey publicKey) {
        try {
            return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
            throw new TokenException(ResponseEnum.ILLEGAL_TOKEN);
        }
    }

    /**
     * 从Token中获取{@link JwtPayload}对象
     *
     * @param token     Token
     * @param key       key-value（底层Map实现）
     * @param publicKey 公钥
     * @return {@link JwtPayload}
     */
    public static JwtPayload getUserFromToken(String token, String key, PublicKey publicKey) throws TokenException {

        Claims body = parserToken(token, publicKey);
        return JwtPayload.builder().id(body.getId())
                .user(JsonUtils.readValue(body.get(key).toString(),
                        User.class).orElse(null))
                .expire(DateUtils.asLocalDateTime(body.getExpiration())).build();
    }

}

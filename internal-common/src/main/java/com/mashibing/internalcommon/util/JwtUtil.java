package com.mashibing.internalcommon.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

/***********************
 * @Description: jwt token工具类<BR>
 * @author: zhao.song
 * @since: 2021/5/12 7:42
 * @version: 1.0
 ***********************/

@SuppressWarnings("all")
@Slf4j
public final class JwtUtil {

    /**
     * 密钥,仅用于服务端存储
     */
    private static String SECRET = "_skLL345|uzks?qxMIw";


    public static String createToken(String subject, Date issueDate) {
        return Jwts.builder().setId("token")
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .setExpiration(new Date(issueDate.getTime() + 5 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public static JwtInfo parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            if (!Objects.isNull(claims)) {
                return JwtInfo.builder()
                        .subject(claims.getSubject())
                        .issueDate(claims.getIssuedAt().getTime())
                        .build();
            }
        } catch (Exception e) {
            log.error("jwt过期了!", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String expireToken = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ0b2tlbiIsInN1YiI6IntcInVzZXJuYW1lXCI6XCJrZXZpblwifSIsImlhdCI6MTYyMDc5MDI3NywiZXhwIjoxNjIwNzkwMzM3fQ.Mk5ySvhjCXNVOQRLwnmcDnwXrdt2Cx5OVOJoTUK_pQmSMsf44GQtqtvXIDApbhn7VcsXXO45374cPS5h2hczlw";
        String token = createToken("{\"username\":\"kevin\"}", new Date());
        System.out.println("generate token: " + token);
        System.out.println("parse token: " + parseToken(token));
    }
}

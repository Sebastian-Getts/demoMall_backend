package com.qin.security.util;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sebastian
 * @date 11/15/20
 */
@Component
public class TokenManager {

    private final String tokenSignKey = "123456";

    /**
     * create token by username
     *
     * @param username user name
     * @return token
     */
    public String createToken(String username) {
        // expiration time
        long tokenExpiration = 24 * 60 * 60 * 1000;
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * get user info from token
     *
     * @param token token
     * @return user info
     */
    public String getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }
}

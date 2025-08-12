package com.peachgb.userService.auth.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret_key;
    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(secret_key));
    }
    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(secret_key))
                .withSubject("User Details")
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}

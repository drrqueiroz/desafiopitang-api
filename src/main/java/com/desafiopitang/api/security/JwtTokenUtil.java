package com.desafiopitang.api.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000; // 5 minutos

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //gera token para user
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userName);
    }

    //Cria o token e devine tempo de expiração pra ele
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + getExpiration()))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public Boolean tokenValidoExpired(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(username != null && expirationDate != null && now.before(expirationDate)){
                return  true;
            }
        }
        return  false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            String username = claims.getSubject();
            return username;
        }
        return  null;
    }


    private Claims getClaims(String token) {
        try{
            return  Jwts.parser().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

    private long getExpiration(){
        return  expiration *  60000;
    }


}

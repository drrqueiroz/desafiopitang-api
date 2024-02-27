package com.desafiopitang.api.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000; // 5 minutos

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //retorna o username do token jwt
    public String getUsernameFromToken2(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retorna expiration date do token jwt
    public Date getExpirationDateFromToken2(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken2(token);
        return claimsResolver.apply(claims);
    }

    //para retornar qualquer informação do token nos iremos precisar da secret key
    private Claims getAllClaimsFromToken2(String token) {
        try{
            return null;//Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch(Exception e) {
            return null;
        }
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken2(token);
        return expiration.before(new Date());
    }

    //gera token para user
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userName);
    }

    //Cria o token e devine tempo de expiração pra ele
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    //valida o token
    public Boolean validateToken2(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken2(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
            return  null;//Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }


}

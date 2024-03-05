package com.desafiopitang.api.security;

public class JWTRotas {
    public static final String[] PUBLIC_MATCHERS = {
            "/actuator/**",
            "/h2-console/**", "/v3/api-docs/**", "/configuration/ui", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**", "/api/users/**", "/api/singin/**"
    };

    public static final String[] PUBLIC_MATCHERS_GET = {
            "/actuator/**", "/h2-console/**",
            "/authenticate", "/v3/api-docs/**", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**"
    };

}

package com.desafiopitang.api.authentication;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CredenciasDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil){
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        setFilterProcessesUrl("/login");
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            CredenciasDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredenciasDTO.class);

            byte[] usuarioByte = Base64.getDecoder().decode(creds.getUsuario());
            byte[] senhaByte = Base64.getDecoder().decode(creds.getSenha());
            String usuario = new String(usuarioByte);
            String senha = new String(senhaByte);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario,
                    senha,
                    new ArrayList<>());
            Authentication auth =  authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        String username = ((User) auth.getPrincipal()).getUsername();
        String token = jwtTokenUtil.generateToken(username);

        res.addHeader("Authorization", "Bearer " + token);
    }



    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Informações inválidas\", "
                    + "\"path\": \"/login\"}";
        }
    }
}

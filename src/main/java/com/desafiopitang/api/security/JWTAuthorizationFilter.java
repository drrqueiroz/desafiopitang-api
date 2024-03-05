package com.desafiopitang.api.security;

import com.desafiopitang.api.dto.ErroDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");


        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
              httpServletResponse(res, "Unauthorized - invalid session");
              return;
            }
        }else {
            String patch = req.getRequestURI();
            boolean isAuthorized = false;

            for (int i = 0; i < JWTRotas.PUBLIC_MATCHERS.length; i++){
                String publicMatchers = JWTRotas.PUBLIC_MATCHERS[i];
                if (publicMatchers.contains("**") && patch.contains(publicMatchers.substring(0, publicMatchers.indexOf("/**")))) {
                    isAuthorized = true;
                    break;
                }
            }
            if(!isAuthorized) {
                httpServletResponse(res, "Unauthorized");
                return;
            }
        }

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (jwtTokenUtil.tokenValidoExpired(token)) {
            String username = jwtTokenUtil.getUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }

    private void httpServletResponse(HttpServletResponse res, String message) throws IOException {

        ErroDTO error = new ErroDTO(message, String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectWriter.writeValueAsString(error);

        res.getWriter().write(json);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());

    }
}


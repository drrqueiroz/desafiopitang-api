package com.desafiopitang.api.security;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.ErroDTO;
import com.desafiopitang.api.exception.BusinessException;
import com.desafiopitang.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springfox.documentation.spring.web.json.Json;

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
                //Register date last login

            }else{
                //throw  new BusinessException("Unauthorized - invalid session”", HttpStatus.FORBIDDEN);
                ErroDTO error = new ErroDTO("Unauthorized - invalid session", String.valueOf(HttpStatus.FORBIDDEN.value()));
                ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectWriter.writeValueAsString(error);
                json= objectMapper.writeValueAsString(error);

                //res.getWriter().write(json);
                res.getWriter().write("Unauthorized - invalid session" );
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }else{
          //  res.getWriter().write("Unauthorized" );
          //  res.setStatus(HttpStatus.UNAUTHORIZED.value());
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
}


package com.desafiopitang.api.controllers;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CredentialDTO;
import com.desafiopitang.api.security.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    ;

    @PostMapping("/singin")
    public ResponseEntity singin(@RequestBody @Valid CredentialDTO value) {
        var credential = new UsernamePasswordAuthenticationToken(value.getLogin(), value.getPassword());
        var auth = this.authenticationManager.authenticate(credential);

        String username = ((User) auth.getPrincipal()).getUsername();
        String token = jwtTokenUtil.generateToken(username);

       // res.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok(token);
    }
}

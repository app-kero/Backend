package com.appkero.backend_kero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appkero.backend_kero.domain.usuario.LoginRequest;
import com.appkero.backend_kero.domain.usuario.LoginResponse;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.infra.JwtTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = jwtTokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }
    
    
}

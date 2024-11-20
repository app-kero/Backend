package com.appkero.backend_kero.controllers;

import com.appkero.backend_kero.controllers.DTOs.RefreshTokenRequest;
import com.appkero.backend_kero.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        Usuario usuario = (Usuario) auth.getPrincipal();

        var accessToken = jwtTokenService.generateAccessToken(usuario);
        var refreshToken = jwtTokenService.generateRefreshToken(usuario);

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        try {
            String email = jwtTokenService.validateToken(refreshToken.refreshToken());

            Usuario user = (Usuario) usuarioRepository.findByEmail(email);

            String newAccessToken = jwtTokenService.generateAccessToken(user);
            return ResponseEntity.ok(new LoginResponse(newAccessToken, refreshToken.refreshToken()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido ou expirado");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token não informado ou inválido.");
        }

        try {
            String token = authorizationHeader.substring(7);

            jwtTokenService.blackListToken(token);

            return ResponseEntity.ok("Logout realizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao realizar logout.");
        }
    }
    
}

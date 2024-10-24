package com.appkero.backend_kero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appkero.backend_kero.entities.DTOs.PasswordRecoveryRequest;
import com.appkero.backend_kero.entities.DTOs.PasswordResetRequest;
import com.appkero.backend_kero.services.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/auth")
public class PasswordRecoveryController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/recover-password")
    public ResponseEntity<String> recoverPassword(@RequestBody PasswordRecoveryRequest request) {
        usuarioService.sendRecoveryEmail(request.email());
        return ResponseEntity.ok("Email de recuperação de senha enviado com sucesso!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        usuarioService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }
    
    
    
}

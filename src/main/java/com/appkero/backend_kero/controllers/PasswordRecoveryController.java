package com.appkero.backend_kero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appkero.backend_kero.entities.PasswordResetToken;
import com.appkero.backend_kero.entities.DTOs.PasswordRecoveryDTO;
import com.appkero.backend_kero.services.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/password")
public class PasswordRecoveryController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/recovery")
    public ResponseEntity<String> recoverPassword(@RequestBody PasswordRecoveryDTO recovery) {
        usuarioService.sendRecoveryEmail(recovery.email());
    }
    
    @PostMapping("/reset-password")
public ResponseEntity<String> resetPassword(@RequestBody PasswordResetToken request) {
    if (usuarioService.isValidToken(request.getToken())) {
        usuarioService.updatePassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida ou token expirado!");
    }
}

}

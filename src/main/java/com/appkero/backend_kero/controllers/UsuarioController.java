package com.appkero.backend_kero.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appkero.backend_kero.entities.Usuario;
import com.appkero.backend_kero.entities.DTOs.UsuarioRequest;
import com.appkero.backend_kero.services.UsuarioService;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> insert(@RequestBody UsuarioRequest usuario) {
        Usuario usuarioDB = new Usuario();
        usuarioDB.setNome(usuario.nome());
        usuarioDB.setSobrenome(usuario.sobrenome());
        usuarioDB.setTelefone(usuario.telefone());
        usuarioDB.setEmail(usuario.email());
        this.usuarioService.insert(usuarioDB);
        return ResponseEntity.ok(usuarioDB);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = this.usuarioService.getAll();
        return ResponseEntity.ok(usuarios);
    }
    
}

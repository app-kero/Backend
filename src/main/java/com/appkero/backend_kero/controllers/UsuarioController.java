package com.appkero.backend_kero.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appkero.backend_kero.entities.Endereco;
import com.appkero.backend_kero.entities.Usuario;
import com.appkero.backend_kero.entities.DTOs.EnderecoRequest;
import com.appkero.backend_kero.entities.DTOs.UsuarioRequest;
import com.appkero.backend_kero.services.EnderecoService;
import com.appkero.backend_kero.services.UsuarioService;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;

    public UsuarioController(UsuarioService usuarioService, EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
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

    @PutMapping("/{usuarioId}/vincular-endereco")
    public ResponseEntity<Usuario> vincularEndereco(@PathVariable Long usuarioId, @RequestBody EnderecoRequest endereco) {
        Endereco enderecoDB = enderecoService.insert(endereco);
        Usuario usuario = usuarioService.vincularEndereco(usuarioId, enderecoDB.getId());
        return ResponseEntity.ok(usuario);
    }
    
}

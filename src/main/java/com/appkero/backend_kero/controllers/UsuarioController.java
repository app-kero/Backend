package com.appkero.backend_kero.controllers;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.domain.usuario.UsuarioRequest;
import com.appkero.backend_kero.domain.usuario.UsuarioUpdate;
import com.appkero.backend_kero.services.ArquivoService;
import com.appkero.backend_kero.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ArquivoService arquivoService;

    @PostMapping("/new")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioRequest request) {
        try {
            Usuario novoUsuario = usuarioService.insert(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @PatchMapping(value = "/completar-cadastro/{usuarioId}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> completarCadastro(
            @RequestPart("file") MultipartFile file,
            @RequestPart("user") @Valid UsuarioUpdate request,
            @PathVariable Long usuarioId) {
        try {
            Arquivo arquivo = arquivoService.uploadFile(file);
            Usuario usuarioAtualizado = usuarioService.completarCadastro(request, arquivo, usuarioId);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar cadastro.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = this.usuarioService.getAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> getById(@PathVariable Long usuarioId) {
        Usuario user = usuarioService.findById(usuarioId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long usuarioId) {
        try {
            usuarioService.deleteUser(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

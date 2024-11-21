package com.appkero.backend_kero.controllers;

import java.util.List;
import java.io.InputStream;
import java.io.FileNotFoundException;

import com.appkero.backend_kero.domain.usuario.UsuarioUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.endereco.EnderecoRequest;
import com.appkero.backend_kero.domain.usuario.UserRole;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.domain.usuario.UsuarioRequest;
import com.appkero.backend_kero.services.ArquivoService;
import com.appkero.backend_kero.services.EnderecoService;
import com.appkero.backend_kero.services.UsuarioService;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private ArquivoService arquivoService;

//    @PostMapping(value = "/new", consumes = {"multipart/form-data"})
//    public ResponseEntity<?> insert(
//        @RequestPart("user") UsuarioRequest user,
//        @RequestPart("file") MultipartFile file
//    ) {
//        try {
//            Arquivo arquivo = arquivoService.store(file);
//            Usuario newUser = usuarioService.insert(user, arquivo);
//            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao tentar inserir um novo usuário: " + e.getMessage());
//        }
//    }

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
            Usuario usuarioAtualizado = usuarioService.completarCadastro(request, file, usuarioId);
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

    @GetMapping("/foto-perfil/{arquivoId}")
    public ResponseEntity<InputStreamResource> getFotoPerfil(@PathVariable Long arquivoId) throws FileNotFoundException {
        try {
            InputStream file = arquivoService.getArquivo(arquivoId);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(file));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

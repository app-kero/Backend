package com.appkero.backend_kero.controllers;

import java.util.List;
import java.io.InputStream;
import java.io.FileNotFoundException;

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

    @PostMapping("/new")
    public ResponseEntity<Usuario> insert(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String nome,
            @RequestParam("sobrenome") String sobrenome,
            @RequestParam("telefone") String telefone,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") UserRole role) throws Exception {

        Arquivo arquivo = arquivoService.store(file);

        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .nome(nome)
                .sobrenome(sobrenome)
                .telefone(telefone)
                .email(email)
                .password(password)
                .role(role)
                .build();

        Usuario newUser = usuarioService.insert(usuarioRequest, arquivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
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

    @PutMapping("/{usuarioId}/vincular-endereco")
    public ResponseEntity<Usuario> vincularEndereco(@PathVariable Long usuarioId,
            @RequestBody EnderecoRequest endereco) {
        Endereco enderecoDB = enderecoService.insert(endereco);
        Usuario usuario = usuarioService.vincularEndereco(usuarioId, enderecoDB.getId());
        return ResponseEntity.ok(usuario);
    }

}

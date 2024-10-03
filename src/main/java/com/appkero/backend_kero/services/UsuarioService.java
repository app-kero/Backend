package com.appkero.backend_kero.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appkero.backend_kero.entities.Endereco;
import com.appkero.backend_kero.entities.Usuario;
import com.appkero.backend_kero.repositories.EnderecoRepository;
import com.appkero.backend_kero.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    public Usuario insert(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return this.usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public Usuario vincularEndereco(Long usuarioId, Long enderecoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Endereco endereco = enderecoRepository.findById(enderecoId)
            .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));

        usuario.setEndereco(endereco);

        return usuarioRepository.save(usuario);
    }

}

package com.appkero.backend_kero.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appkero.backend_kero.entities.Usuario;
import com.appkero.backend_kero.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    public Usuario insert(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

}

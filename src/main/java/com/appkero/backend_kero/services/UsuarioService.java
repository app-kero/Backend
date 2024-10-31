package com.appkero.backend_kero.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.domain.usuario.UsuarioRequest;
import com.appkero.backend_kero.infra.JwtTokenService;
import com.appkero.backend_kero.infra.SecurityConfiguration;
import com.appkero.backend_kero.repositories.EnderecoRepository;
import com.appkero.backend_kero.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    public Usuario insert(UsuarioRequest dto, Arquivo fotoperfil) {
        Usuario user = Usuario.builder()
            .nome(dto.nome())
            .sobrenome(dto.sobrenome())
            .email(dto.email())
            .telefone(dto.telefone())
            .password(securityConfiguration.passwordEncoder().encode(dto.password()))
            .fotoPerfil(fotoperfil)
            .build();
        
        return usuarioRepository.save(user);
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

    public void sendRecoveryEmail(String email) {
        var user = authenticationService.loadUserByUsername(email).isEnabled();
        if (user) {
            String token = jwtTokenService.generatePasswordResetToken(email);
            emailService.sendPasswordResetEmail(email, token);
        }
    }

    public void resetPassword(String token, String newPassword) {
        if (jwtTokenService.validateToken(token).length() > 0) {
            String email = jwtTokenService.extractEmailFromToken(token);
            var user = (Usuario) usuarioRepository.findByEmail(email);

            if (user != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
                usuarioRepository.save(user);
            } else {
                throw new IllegalArgumentException("Token inválido ou expirado!");
            }
        }
    }

}

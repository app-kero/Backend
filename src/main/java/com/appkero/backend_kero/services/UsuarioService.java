package com.appkero.backend_kero.services;

import java.util.List;

import com.appkero.backend_kero.domain.redeSocial.RedeSocial;
import com.appkero.backend_kero.domain.usuario.UserRole;
import com.appkero.backend_kero.domain.usuario.UsuarioUpdate;
import com.appkero.backend_kero.repositories.RedeSocialRepository;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ArquivoService arquivoService;
    @Autowired
    private RedeSocialRepository redeSocialRepository;

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

//    public Usuario insert(UsuarioRequest dto, Arquivo fotoperfil) {
//        if (usuarioRepository.findByEmail(dto.email()) != null) throw new RuntimeException("Usuário já cadastrado com o email " + dto.email());
//        Usuario user = Usuario.builder()
//            .nome(dto.nome())
//            .sobrenome(dto.sobrenome())
//            .email(dto.email())
//            .telefone(dto.telefone())
//            .password(securityConfiguration.passwordEncoder().encode(dto.password()))
//            .role(UserRole.USER)
//            .fotoPerfil(fotoperfil)
//            .build();
//
//        return usuarioRepository.save(user);
//    }

    public Usuario insert(UsuarioRequest usuario) {
        if (usuarioRepository.findByEmail(usuario.email()) != null) {
            throw new IllegalArgumentException("O email já está em uso.");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(usuario.nome())
                .email(usuario.email())
                .password(securityConfiguration.passwordEncoder().encode(usuario.password()))
                .role(UserRole.USER)
                .build();

        return usuarioRepository.save(novoUsuario);
    }

    public Usuario completarCadastro(UsuarioUpdate request, MultipartFile file, Long usuarioId) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (request.nome() != null) {
            usuario.setNome(request.nome());
        }
        if (request.sobrenome() != null) {
            usuario.setSobrenome(request.sobrenome());
        }
        if (request.telefone() != null) {
            usuario.setTelefone(request.telefone());
        }
        if (request.email() != null) {
            usuario.setEmail(request.email());
        }
        if (request.endereco() != null) {
            Endereco endereco = enderecoRepository.save(request.endereco());
            usuario.setEndereco(endereco);
        }
        if (request.redesSociais() != null) {
            RedeSocial rede = redeSocialRepository.save(request.redesSociais());
            usuario.setRedesSociais(rede);
        }
        if (file != null) {
            Arquivo arquivo = arquivoService.store(file);
            usuario.setFotoPerfil(arquivo);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return this.usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public void deleteUser(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        usuarioRepository.delete(user);
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
        if (!jwtTokenService.validateToken(token).isEmpty()) {
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

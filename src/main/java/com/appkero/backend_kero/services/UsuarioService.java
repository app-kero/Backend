package com.appkero.backend_kero.services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.appkero.backend_kero.entities.Endereco;
import com.appkero.backend_kero.entities.Role;
import com.appkero.backend_kero.entities.Usuario;
import com.appkero.backend_kero.entities.DTOs.LoginUsuarioDTO;
import com.appkero.backend_kero.entities.DTOs.RecoveryJwtTokenDTO;
import com.appkero.backend_kero.entities.DTOs.UsuarioRequest;
import com.appkero.backend_kero.repositories.EnderecoRepository;
import com.appkero.backend_kero.repositories.UsuarioRepository;
import com.appkero.backend_kero.security.authentication.JwtTokenService;
import com.appkero.backend_kero.security.config.SecurityConfiguration;
import com.appkero.backend_kero.security.userdetails.UserDetailsImpl;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final SecurityConfiguration securityConfiguration;
    
    public UsuarioService(UsuarioRepository usuarioRepository, 
                            EnderecoRepository enderecoRepository,
                            AuthenticationManager authenticationManager,
                            JwtTokenService jwtTokenService,
                            SecurityConfiguration securityConfiguration) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.securityConfiguration = securityConfiguration;
    }

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    public Usuario insert(UsuarioRequest dto) {
        Usuario user = Usuario.builder()
            .nome(dto.nome())
            .sobrenome(dto.sobrenome())
            .email(dto.email())
            .telefone(dto.telefone())
            .password(securityConfiguration.passwordEncoder().encode(dto.password()))
            .roles(List.of(Role.builder().name(dto.role()).build()))
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

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDTO authenticateUser(LoginUsuarioDTO loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    public void sendRecoveryEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendRecoveryEmail'");
    }

}

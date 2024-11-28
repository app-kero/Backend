package com.appkero.backend_kero;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.usuario.UserRole;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.domain.usuario.UsuarioRequest;

import com.appkero.backend_kero.repositories.UsuarioRepository;
import com.appkero.backend_kero.repositories.EnderecoRepository;


import com.appkero.backend_kero.services.UsuarioService;

import com.appkero.backend_kero.infra.JwtTokenService;
import com.appkero.backend_kero.infra.SecurityConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private SecurityConfiguration securityConfiguration;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testInsertUsuario() {
//
//        UsuarioRequest dto = UsuarioRequest.builder()
//                .nome("João")
//                .sobrenome("Silva")
//                .telefone("999999999")
//                .email("joao@example.com")
//                .password("123456")
//                .role(UserRole.USER)
//                .build();
//
//        Arquivo fotoPerfil = new Arquivo();
//
//        when(usuarioRepository.findByEmail(dto.email())).thenReturn(null);
//        when(securityConfiguration.passwordEncoder()).thenReturn(passwordEncoder);
//        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");
//
//        Usuario usuarioSalvo = Usuario.builder()
//                .nome(dto.nome())
//                .sobrenome(dto.sobrenome())
//                .email(dto.email())
//                .password("encodedPassword")
//                .role(UserRole.USER)
//                .fotoPerfil(fotoPerfil)
//                .build();
//
//        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);
//
//
//        Usuario resultado = usuarioService.insert(dto, fotoPerfil);
//
//
//        assertNotNull(resultado);
//        assertEquals(dto.nome(), resultado.getNome());
//        assertEquals("encodedPassword", resultado.getPassword());
//        assertEquals(UserRole.USER, resultado.getRole());
//    }

//    @Test
//    public void testFindByIdUsuarioExistente() {
//
//        Usuario usuarioMock = new Usuario();
//        usuarioMock.setId(1L);
//        usuarioMock.setNome("João");
//
//        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
//
//
//        Usuario usuario = usuarioService.findById(1L);
//
//
//        assertNotNull(usuario);
//        assertEquals("João", usuario.getNome());
//    }
//
//    @Test
//    public void testFindByIdUsuarioNaoExistente() {
//
//
//        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());
//
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            usuarioService.findById(2L);
//        });
//
//        assertEquals("Usuário não encontrado!", exception.getMessage());
//    }
//
//    @Test
//    public void testVincularEndereco() {
//
//        Usuario usuarioMock = new Usuario();
//        usuarioMock.setId(1L);
//
//        Endereco enderecoMock = new Endereco();
//        enderecoMock.setId(10L);
//
//        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
//        when(enderecoRepository.findById(10L)).thenReturn(Optional.of(enderecoMock));
//        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);
//
//
//        Usuario resultado = usuarioService.vincularEndereco(1L, 10L);
//
//
//        assertNotNull(resultado);
//        assertEquals(enderecoMock, resultado.getEndereco());
//    }

//    @Test
//    public void testInsertUsuarioJaExistente() {
//
//        UsuarioRequest dto = UsuarioRequest.builder()
//                .nome("João")
//                .sobrenome("Silva")
//                .telefone("999999999")
//                .email("joao@example.com")
//                .password("123456")
//                .role(UserRole.USER)
//                .build();
//
//        when(usuarioRepository.findByEmail(dto.email())).thenReturn(new Usuario());
//
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            usuarioService.insert(dto, new Arquivo());
//        });
//
//        assertEquals("Usuário já cadastrado com o email joao@example.com", exception.getMessage());
//    }

    

}
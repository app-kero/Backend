package com.appkero.backend_kero.infra;

import com.appkero.backend_kero.domain.usuario.UserRole;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private AdminUserProperties adminUserProperties;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByEmail(adminUserProperties.getEmail()) == null) {
            Usuario admin = Usuario.builder()
                    .nome(adminUserProperties.getNome())
                    .sobrenome(adminUserProperties.getSobrenome())
                    .telefone(adminUserProperties.getTelefone())
                    .email(adminUserProperties.getEmail())
                    .password(securityConfiguration.passwordEncoder().encode(adminUserProperties.getPassword()))
                    .role(UserRole.ADMIN)
                    .build();
            usuarioRepository.save(admin);
            System.out.println("Usuário administrador criado com sucesso!!!");
        } else {
            System.out.println("Usuário administrador já existe...");
        }
    }
}

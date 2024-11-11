package com.appkero.backend_kero.infra;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user.admin")
@Getter
@Setter
public class AdminUserProperties {
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String password;
}

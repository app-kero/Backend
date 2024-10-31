package com.appkero.backend_kero.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UsuarioRequest(
    @NotBlank String nome,
    @NotBlank String sobrenome,
    @NotBlank String telefone,
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotBlank UserRole role
) {  
}

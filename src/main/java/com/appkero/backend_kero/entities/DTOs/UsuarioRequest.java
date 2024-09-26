package com.appkero.backend_kero.entities.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
    @NotBlank String nome,
    @NotBlank String sobrenome,
    @NotBlank String telefone,
    @NotBlank @Email String email
) {  
}

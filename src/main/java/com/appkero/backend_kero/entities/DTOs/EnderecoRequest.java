package com.appkero.backend_kero.entities.DTOs;

public record EnderecoRequest(
    String rua,
    String bairro,
    Integer numero,
    String cidade,
    String estado
) {
    
}

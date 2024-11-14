package com.appkero.backend_kero.domain.endereco;


import lombok.Builder;

@Builder
public record EnderecoRequest(
    String rua,
    String bairro,
    Integer numero,
    String cidade,
    String estado
) {
    
}

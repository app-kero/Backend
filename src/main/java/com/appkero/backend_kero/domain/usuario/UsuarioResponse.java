package com.appkero.backend_kero.domain.usuario;

import lombok.Builder;

@Builder
public record UsuarioResponse(Long id, String nome, String email) {
}

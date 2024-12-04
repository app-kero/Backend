package com.appkero.backend_kero.domain.usuario;

public record LoginResponse(UsuarioResponse data, String accessToken, String refreshToken) {
    
}

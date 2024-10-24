package com.appkero.backend_kero.entities.DTOs;

public record PasswordResetRequest(
    String token,
    String newPassword
) {
    
}

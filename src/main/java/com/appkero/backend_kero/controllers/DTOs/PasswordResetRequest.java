package com.appkero.backend_kero.controllers.DTOs;

public record PasswordResetRequest(String token, String newPassword) {
    
}

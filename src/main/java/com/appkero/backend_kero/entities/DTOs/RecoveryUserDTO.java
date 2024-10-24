package com.appkero.backend_kero.entities.DTOs;

import java.util.*;

import com.appkero.backend_kero.entities.Role;

public record RecoveryUserDTO(
    Long id,
    String email,
    List<Role> roles
) {
    
}

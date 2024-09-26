package com.appkero.backend_kero.entities.DTOs;

import java.time.LocalTime;

public record ProdutoRequest(
    String nome,
    String descricao,
    String local,
    LocalTime horario,
    Long usuarioId,
    RedeSocialRequest redeSocial
) {
} 

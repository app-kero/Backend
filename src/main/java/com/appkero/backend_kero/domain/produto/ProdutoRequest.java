package com.appkero.backend_kero.domain.produto;

import java.time.LocalTime;

import com.appkero.backend_kero.domain.redeSocial.RedeSocialRequest;

public record ProdutoRequest(
    String nome,
    String descricao,
    String local,
    LocalTime horario,
    Long usuarioId,
    RedeSocialRequest redeSocial
) {
} 
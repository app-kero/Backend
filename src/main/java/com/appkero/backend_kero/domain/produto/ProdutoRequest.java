package com.appkero.backend_kero.domain.produto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import com.appkero.backend_kero.domain.redeSocial.RedeSocialRequest;

public record ProdutoRequest(
    String nome,
    String descricao,
    BigDecimal preco,
    String local,
    LocalTime horario,
    List<String> tags
) {
} 

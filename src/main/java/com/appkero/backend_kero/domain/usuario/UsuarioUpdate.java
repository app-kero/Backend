package com.appkero.backend_kero.domain.usuario;

import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.redeSocial.RedeSocial;

public record UsuarioUpdate(String nome, String sobrenome, String telefone, String email, Endereco endereco, RedeSocial redesSociais) {
}

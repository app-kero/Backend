package com.appkero.backend_kero.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usuario extends BasicEntity {

    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    
}

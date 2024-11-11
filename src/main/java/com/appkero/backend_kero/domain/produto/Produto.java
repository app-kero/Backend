package com.appkero.backend_kero.domain.produto;

import java.time.LocalTime;
import java.util.List;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.redeSocial.RedeSocial;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tb_produto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Produto extends BasicEntity {

    private String nome;
    private String descricao;
    private String local;
    private LocalTime horario;

    @ManyToOne 
    @JsonIgnore
    @JoinColumn(name = "usuario_id", nullable = false) 
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "produto_tag",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public String getHorarioFormatado() {
        return horario != null ? horario.toString() : "Horário não especificado";
    }
    
}

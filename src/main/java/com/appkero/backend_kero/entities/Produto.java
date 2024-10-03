package com.appkero.backend_kero.entities;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_produto")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Produto extends BasicEntity {

    @NotBlank(message = "O campo nome não pode ser vazio")
    private String nome;
    private String descricao;
    private String local;
    private LocalTime horario;

    @ManyToOne 
    @JsonIgnore
    @JoinColumn(name = "usuario_id", nullable = false) 
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rede_social_id", referencedColumnName = "id")
    private RedeSocial redeSocial;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    public String getHorarioFormatado() {
        return horario != null ? horario.toString() : "Horário não especificado";
    }
    
}

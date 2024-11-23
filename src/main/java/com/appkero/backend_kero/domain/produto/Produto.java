package com.appkero.backend_kero.domain.produto;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_produto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"usuario","fotos"})
@Builder
public class Produto extends BasicEntity {

    private String nome;
    private String descricao;
    private BigDecimal preco;
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
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private List<Arquivo> fotos = new ArrayList<>();

    public String getHorarioFormatado() {
        return horario != null ? horario.toString() : "Horário não especificado";
    }
    
}

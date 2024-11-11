package com.appkero.backend_kero.domain.produto;

import com.appkero.backend_kero.domain.BasicEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Tag extends BasicEntity {
    
    private String nome;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Produto> produtos;
}

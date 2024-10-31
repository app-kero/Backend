package com.appkero.backend_kero.domain.produto;

import com.appkero.backend_kero.domain.BasicEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_tag")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Tag extends BasicEntity {
    
    private String nome;

}

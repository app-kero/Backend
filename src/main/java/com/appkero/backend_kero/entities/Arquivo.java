package com.appkero.backend_kero.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_arquivo")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class Arquivo extends BasicEntity {

    private String name;
    private String contentType;
    private Long size;
    @Lob // armazenamento de objetos grandes no banco de dados
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private byte[] data;
    
    @OneToOne(mappedBy = "fotoPerfil")
    private Usuario usuario;
}

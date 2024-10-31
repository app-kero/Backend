package com.appkero.backend_kero.domain.arquivo;

import java.io.InputStream;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.usuario.Usuario;
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
    private String urlS3;
    
    @OneToOne(mappedBy = "fotoPerfil")
    private Usuario usuario;
}

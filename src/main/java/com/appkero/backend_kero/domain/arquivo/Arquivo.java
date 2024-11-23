package com.appkero.backend_kero.domain.arquivo;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.produto.Produto;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
}

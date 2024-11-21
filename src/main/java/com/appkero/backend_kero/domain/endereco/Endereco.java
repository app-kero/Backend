package com.appkero.backend_kero.domain.endereco;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Endereco extends BasicEntity {
    
    @NotBlank(message = "O campo rua não pode ser vazio")
    private String rua;
    @NotBlank(message = "O campo bairro não pode ser vazio")
    private String bairro;
    private Integer numero;
    @NotBlank(message = "O campo cidade não pode ser vazio")
    private String cidade;
    @NotBlank(message = "O campo estado não pode ser vazio")
    private String estado;

    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL)
    @JsonIgnore
    private Usuario usuario;

    public String getEnderecoCompleto() {

        return rua +
                ", " +
                (numero != null ? numero : "S/N") +
                ", " + bairro +
                ", " + cidade +
                ", " + estado;
    }
}

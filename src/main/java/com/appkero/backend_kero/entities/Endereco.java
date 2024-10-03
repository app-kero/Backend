package com.appkero.backend_kero.entities;

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
    @NotBlank(message = "O campo bairoo não pode ser vazio")
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
        StringBuilder enderecoCompleto = new StringBuilder();
        enderecoCompleto.append(rua)
                        .append(", ")
                        .append(numero != null ? numero : "S/N")
                        .append(", ").append(bairro)
                        .append(", ").append(cidade)
                        .append(", ").append(estado);
        
        return enderecoCompleto.toString();
    }
}

package com.appkero.backend_kero.entities;

import java.util.List;

import com.appkero.backend_kero.utils.ArquivoToArquivoResponseConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class Usuario extends BasicEntity {

    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    private String telefone;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne 
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "foto_perfil_id")
    @JsonSerialize(using = ArquivoToArquivoResponseConverter.class)
    private Arquivo fotoPerfil;

    public String getNomeCompleto() {
        return this.nome + " " + this.sobrenome;
    }
    
}

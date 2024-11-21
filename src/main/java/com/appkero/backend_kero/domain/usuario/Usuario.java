package com.appkero.backend_kero.domain.usuario;

import java.util.Collection;
import java.util.List;

import com.appkero.backend_kero.domain.redeSocial.RedeSocial;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.produto.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
public class Usuario extends BasicEntity implements UserDetails {

    private String nome;
    private String sobrenome;
    private String telefone;
    @Email
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @OneToOne 
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    private UserRole role;

    @OneToOne()
    @JoinColumn(name = "foto_perfil_id")
    private Arquivo fotoPerfil;

    @OneToOne
    @JoinColumn(name = "rede_social_id")
    private RedeSocial redesSociais;

    public String getNomeCompleto() {
        if (!this.sobrenome.isEmpty()){
            return this.nome + " " + this.sobrenome;
        } else {
            return this.nome;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
}

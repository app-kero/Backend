package com.appkero.backend_kero.domain.usuario;

import com.appkero.backend_kero.domain.BasicEntity;
import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.produto.Produto;
import com.appkero.backend_kero.domain.redeSocial.RedeSocial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private List<Produto> produtos = new ArrayList<>();

    private UserRole role;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "foto_perfil_id")
    private Arquivo fotoPerfil;

    @OneToOne
    @JoinColumn(name = "rede_social_id")
    private RedeSocial redesSociais;

    public String getNomeCompleto() {
        if (this.sobrenome != null && !this.sobrenome.isEmpty()){
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

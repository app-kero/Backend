package com.appkero.backend_kero.domain.redeSocial;

import com.appkero.backend_kero.domain.BasicEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_rede_social")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class RedeSocial extends BasicEntity {

    private String whatsapp;
    private String facebook;
    private String site;
    private String instagram;
    
}

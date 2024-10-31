package com.appkero.backend_kero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appkero.backend_kero.domain.redeSocial.RedeSocial;

@Repository
public interface RedeSocialRepository extends JpaRepository<RedeSocial, Long>{
    
}

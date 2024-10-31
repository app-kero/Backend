package com.appkero.backend_kero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appkero.backend_kero.entities.Arquivo;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long>{
    
}

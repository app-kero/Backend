package com.appkero.backend_kero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appkero.backend_kero.domain.produto.Produto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    public List<Produto> findByTagsNome(String nome);
}

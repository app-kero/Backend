package com.appkero.backend_kero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.appkero.backend_kero.domain.produto.Produto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    @Query("SELECT p FROM Produto p JOIN p.tags t WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> findByTagsNomeIgnoreCase(String nome);

    List<Produto> findProdutosByNomeContainsIgnoreCase(String nome);
}

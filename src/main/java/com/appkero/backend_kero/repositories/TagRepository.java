package com.appkero.backend_kero.repositories;

import com.appkero.backend_kero.domain.produto.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNome(String nome);
    List<Tag> findByNomeIn(List<String> nomes);
}

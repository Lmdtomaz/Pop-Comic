package com.store.popcomic.repository;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {
    Optional<Produto> findById(Long id);


}

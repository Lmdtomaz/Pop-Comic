package com.store.popcomic.repository;

import com.store.popcomic.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {

}

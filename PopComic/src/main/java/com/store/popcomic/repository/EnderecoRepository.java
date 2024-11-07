package com.store.popcomic.repository;

import com.store.popcomic.model.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository <Enderecos, Long>{

    Object findByClienteCpf(String cpf);
}

package com.store.popcomic.repository;

import com.store.popcomic.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByEmailAndSenha(String email, String senha);
}

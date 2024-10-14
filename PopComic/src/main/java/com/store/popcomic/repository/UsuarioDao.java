package com.store.popcomic.repository;

import com.store.popcomic.model.UsuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioDao extends JpaRepository <UsuarioCliente, Long > {

    @Query("SELECT u FROM UsuarioCliente u WHERE u.email = :email AND u.senha = :senha")
    UsuarioCliente buscarLogin(@Param("email") String email, @Param("senha") String senha);
}

package com.store.popcomic.repository;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}

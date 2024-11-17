package com.store.popcomic.repository;

import com.store.popcomic.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Método para buscar endereços pelo CPF do cliente
    List<Endereco> findByClienteCpf(String cpf);

    // Método para buscar um endereço específico de um cliente pelo id
    Endereco findByIdAndClienteCpf(Long id, String cpf);
}

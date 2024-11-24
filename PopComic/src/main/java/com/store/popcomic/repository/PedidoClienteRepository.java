package com.store.popcomic.repository;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.PedidoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PedidoClienteRepository extends JpaRepository<PedidoCliente, Long> {

    public Optional<PedidoCliente> findByCpfContaining(String cpf);

    List<PedidoCliente> findByCpf(String cpf);
}

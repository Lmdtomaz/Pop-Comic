package com.store.popcomic.repository;

import com.store.popcomic.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Podemos adicionar métodos customizados aqui, se necessário
}

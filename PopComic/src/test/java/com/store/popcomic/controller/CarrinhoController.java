package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.Produto;
import com.store.popcomic.repository.ClienteRepository;
import com.store.popcomic.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PopcomicApplicationTests {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Cliente cliente;
    private Produto produto;

    @BeforeEach
    void setup() {
        // Configuração do Cliente
        cliente = new Cliente();
        cliente.setCpf("12345678909");
        cliente.setNome("João da Silva");
        cliente.setEmail("joao.silva@example.com");
        cliente.setSenha("senhaSegura");
        cliente.setGenero("Masculino");

        // Configuração do Produto
        produto = new Produto();
        produto.setNome("Produto Exemplo");
        produto.setPreco(29.99);
        produto.setEstoque(10);
        produto.setDescricao("Descrição do Produto Exemplo");
    }

    // Testes para Cliente

    @Test
    void testCadastrarCliente() {
        Cliente savedCliente = clienteRepository.save(cliente);
        assertNotNull(savedCliente);
        assertEquals("João da Silva", savedCliente.getNome());
    }

    @Test
    void testAtualizarCliente() {
        Cliente savedCliente = clienteRepository.save(cliente);
        savedCliente.setNome("João Silva Atualizado");
        clienteRepository.save(savedCliente);

        Optional<Cliente> updatedCliente = clienteRepository.findById(cliente.getCpf());
        assertTrue(updatedCliente.isPresent());
        assertEquals("João Silva Atualizado", updatedCliente.get().getNome());
    }

    @Test
    void testExcluirCliente() {
        clienteRepository.save(cliente);
        clienteRepository.deleteById(cliente.getCpf());

        Optional<Cliente> deletedCliente = clienteRepository.findById(cliente.getCpf());
        assertFalse(deletedCliente.isPresent());
    }

    // Testes para Produto

    @Test
    void testCadastrarProduto() {
        Produto savedProduto = produtoRepository.save(produto);
        assertNotNull(savedProduto);
        assertEquals("Produto Exemplo", savedProduto.getNome());
    }

    @Test
    void testAtualizarProduto() {
        Produto savedProduto = produtoRepository.save(produto);
        savedProduto.setNome("Produto Atualizado");
        produtoRepository.save(savedProduto);

        Optional<Produto> updatedProduto = produtoRepository.findById(savedProduto.getId());
        assertTrue(updatedProduto.isPresent());
        assertEquals("Produto Atualizado", updatedProduto.get().getNome());
    }

    @Test
    void testExcluirProduto() {
        Produto savedProduto = produtoRepository.save(produto);
        produtoRepository.deleteById(savedProduto.getId());

        Optional<Produto> deletedProduto = produtoRepository.findById(savedProduto.getId());
        assertFalse(deletedProduto.isPresent());
    }

    @Test
    void testBuscarProdutoPorId() {
        Produto savedProduto = produtoRepository.save(produto);
        Optional<Produto> foundProduto = produtoRepository.findById(savedProduto.getId());
        assertTrue(foundProduto.isPresent());
        assertEquals(savedProduto.getId(), foundProduto.get().getId());
    }
}

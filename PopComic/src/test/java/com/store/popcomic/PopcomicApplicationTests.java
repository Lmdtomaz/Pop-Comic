package com.store.popcomic;

import com.store.popcomic.controller.LoginController;
import com.store.popcomic.model.Cliente;
import com.store.popcomic.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PopcomicApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClienteRepository clienteRepository;


	private Cliente cliente;

	@BeforeEach
	void setup() {
		cliente = new Cliente();
		cliente.setCpf("12345678909");
		cliente.setNome("João da Silva");
		cliente.setEmail("joao.silva@example.com");
		cliente.setSenha("senhaSegura");
		cliente.setGenero("Masculino");
		clienteRepository.save(cliente);

		// Configura o MockMvc para testar o LoginController
		mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
	}

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

	@Test
	void testLoginSucesso() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.param("email", "joao.silva@example.com")
						.param("senha", "senhaSegura"))
				.andExpect(MockMvcResultMatchers.redirectedUrl("/"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@Test
	void testLoginFalha() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.param("email", "joao.silva@example.com")
						.param("senha", "senhaErrada"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("erro"))
				.andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Email ou senha inválidos")));
	}

	@Test
	void testLogout() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/login/logout"))
				.andExpect(MockMvcResultMatchers.redirectedUrl("/"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
}

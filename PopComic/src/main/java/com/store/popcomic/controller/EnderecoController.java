package com.store.popcomic.controller;

import com.store.popcomic.model.Cliente;
import com.store.popcomic.model.Endereco;
import com.store.popcomic.repository.ClienteRepository;
import com.store.popcomic.repository.EnderecoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/perfil")
public class EnderecoController {



    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Rota para exibir o formulário de cadastro
    @GetMapping("/endereco/{cpf}")
    public String mostrarFormularioCadastro(@PathVariable String cpf, Model model) {
        // Buscar o cliente pelo CPF
        Cliente cliente = clienteRepository.findByCpf(cpf).orElse(null);
        if (cliente == null) {
            model.addAttribute("error", "Cliente com o CPF informado não encontrado!");
            return "Endereco"; // Retorna o formulário com erro
        }

        // Adiciona o CPF ao modelo para o formulário
        model.addAttribute("cpf", cpf);

        // Inicializa um novo objeto Endereco
        model.addAttribute("endereco", new Endereco());

        return "Endereco"; // Nome do template para o formulário
    }

    // Rota para salvar um novo endereço
    @PostMapping("/endereco/{cpf}")
    public String cadastrarEndereco(@PathVariable String cpf, @ModelAttribute Endereco endereco, Model model) {
        // Busca o cliente no banco de dados pelo CPF
        Cliente cliente = clienteRepository.findByCpf(cpf).orElse(null);

        if (cliente == null) {
            model.addAttribute("error", "Cliente com o CPF informado não encontrado!");
            return "Endereco"; // Retorna ao formulário em caso de erro
        }

        // Associa o cliente ao endereço
        endereco.setCliente(cliente);

        // Salva o endereço no banco de dados
        enderecoRepository.save(endereco);

        // Redireciona para a página do perfil após o cadastro
        return "redirect:/perfil"; // Redireciona para o perfil do cliente
    }

    @GetMapping("/buscarEndereco/{cep}")
    @ResponseBody
    public ResponseEntity<?> buscarEndereco(@PathVariable String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // Verifica se a API retornou um CEP inválido
            if (response != null && response.containsKey("erro") && (boolean) response.get("erro")) {
                return ResponseEntity.badRequest().body("CEP não encontrado.");
            }

            // Prepara os dados do endereço
            Map<String, String> endereco = new HashMap<>();
            assert response != null;
            endereco.put("logradouro", (String) response.get("logradouro"));
            endereco.put("cidade", (String) response.get("localidade"));
            endereco.put("estado", (String) response.get("uf"));
            endereco.put("bairro", (String) response.get("bairro"));

            return ResponseEntity.ok(endereco);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar o endereço: " + e.getMessage());
        }
    }

    @GetMapping("/listarEnderecos")
    public ModelAndView listar(HttpSession session) {
        // Obtém o CPF do cliente da sessão
        String cpfCliente = (String) session.getAttribute("userCpf");

        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/login") ; // Redireciona para a página de login, se não estiver logado
        }

        // Busca os endereços relacionados ao cliente pelo CPF
        List<Endereco> enderecos = enderecoRepository.findByClienteCpf(cpfCliente);

        // Prepara a visão com os endereços encontrados
        ModelAndView mv = new ModelAndView("listaEndereco");
        mv.addObject("enderecos", enderecos);
        return mv;
    }

    @PostMapping("/selecionarEndereco")
    public String selecionarEndereco(@RequestParam("enderecoSelecionado") Long idEndereco, HttpSession session) {
        // Busca o endereço pelo ID
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(idEndereco);

        if (enderecoOpt.isEmpty()) {
            // Caso o endereço não seja encontrado, redirecione para a listagem
            return "redirect:/listarEnderecos";
        }

        // Salva o endereço selecionado na sessão
        Endereco enderecoSelecionado = enderecoOpt.get();


        session.setAttribute("enderecoEntrega", enderecoSelecionado);

        // Redireciona para a próxima etapa
        return "redirect:/pagamento";
    }



}


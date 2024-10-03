package com.store.popcomic.controller;

import com.store.popcomic.model.Produto;
import com.store.popcomic.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("ecommerce")
public class ProdutoController {

    private static String caminhoImagens = "C:\\Users\\Operações\\Downloads\\imagens\\";

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("adm/produto/cadastrar")
    public ModelAndView cadastrar(Produto produto){
        ModelAndView mv = new ModelAndView("adm/produtos/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }
    @GetMapping("adm/produto/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("adm/produtos/lista");
        mv.addObject("produto", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("adm/produto/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isPresent()) {
            return cadastrar(produto.get());
        } else {
            return new ModelAndView("redirect:/adm/produto/listar"); // Redireciona se o produto não for encontrado
        }
    }



    @GetMapping("adm/produto/alterarStatus/{id}")
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
            if(produtoOpt.isPresent()) {
                Produto produto = produtoOpt.get();

                produto.setStatus(!produto.isStatus());

                produtoRepository.save(produto);
            }
        return new ModelAndView("redirect:/adm/produto/listar");
    }


    @GetMapping("adm/produto/uploadImagem/{id}")
    public ModelAndView uploadImagem(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            ModelAndView mv = new ModelAndView("adm/produtos/uploadImagem");
            mv.addObject("produto", produto.get());
            return mv;
        } else {
            // Redireciona caso o produto não exista
            return new ModelAndView("redirect:/ecommerce/adm/produto/listar");
        }
    }
    @GetMapping("produto/{id}")
    public ModelAndView visualizarProduto(@PathVariable("id") Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("produto/detalhes");
            mv.addObject("produto", produtoOpt.get());
            return mv;
        }
        return new ModelAndView("redirect:/produtodetalhes");
    }





    @PostMapping("adm/produto/salvarImagem")
    public ModelAndView salvarImagem(@RequestParam("file") MultipartFile arquivo, @RequestParam("id") Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            try {
                if (!arquivo.isEmpty()) {
                    byte[] bytes = arquivo.getBytes();
                            Path caminho = Paths.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
                    Files.write(caminho, bytes);

                    produto.setImagenPrincipal(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
                    produtoRepository.saveAndFlush(produto);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Redireciona para a lista de produtos após o upload
            return new ModelAndView("redirect:/ecommerce/adm/produto/listar");
        } else {
            // Redireciona caso o produto não exista
            return new ModelAndView("redirect:/ecommerce/adm/produto/listar");
        }
    }


    @GetMapping("/imagem/{nomeImagem}")
    @ResponseBody
    public byte[] mostrarImagem(@PathVariable("nomeImagem") String nomeImagem) throws IOException {
        Path caminho = Paths.get(caminhoImagens, nomeImagem);
        return Files.readAllBytes(caminho);
    }



    @PostMapping("adm/produto/salvarInicial")
    public ModelAndView salvarInicial(@Validated Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(produto);  // Volta para o formulário se houver erros
        }

        // Salva o produto sem a imagem
        produtoRepository.saveAndFlush(produto);

        // Redireciona para a página de upload de imagem com o ID do produto
        return new ModelAndView("redirect:/ecommerce/adm/produto/uploadImagem/" + produto.getId());
    }


}

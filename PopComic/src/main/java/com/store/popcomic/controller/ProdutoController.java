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

    private static String caminhoImagens = "C:\\Users\\leandro\\Downloads\\imagens";

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
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if(produtoOpt.isPresent()){
            ModelAndView mv = new ModelAndView("adm/produtos/cadastro");
            mv.addObject("produto", produtoOpt.get());
            return mv;
        }
        return new ModelAndView("redirect:/adm/produto/listar");
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

    @PostMapping("adm/produto/salvar")
    public ModelAndView salvar(@Validated Produto produto, BindingResult result, @RequestParam("file")MultipartFile arquivo){
        if(result.hasErrors()){
            return cadastrar(produto);
        }

        produtoRepository.saveAndFlush(produto);
        try{
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoImagens+ String.valueOf(produto.getId())+ arquivo.getOriginalFilename());
                Files.write(caminho, bytes);

                produto.setImagenPrincipal(String.valueOf(produto.getId())+ arquivo.getOriginalFilename());

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return cadastrar(new Produto());

    }

    //Pagina para mostrar o carrinho
    @GetMapping ("/carrinho")
    public ModelAndView chamarCarrinho(){
        ModelAndView mv = new ModelAndView("carrinho");
        return mv;
    }

//    @PostMapping("/carrinho/remover/{produtoId}")
//    public ModelAndView removerProdutoDoCarrinho(@PathVariable("produtoId") Long produtoId) {
//        Optional<Carrinho> carrinhoOpt = carrinhoRepository.findById(1L);
//        if (carrinhoOpt.isPresent()) {
//            Carrinho carrinho = carrinhoOpt.get();
//
//            // Buscar o produto que deve ser removido
//            Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);
//            if (produtoOpt.isPresent()) {
//                Produto produto = produtoOpt.get();
//
//                // Remover o produto da lista
//                carrinho.getProdutos().remove(produto);
//
//                // Recalcular o total
//                carrinho.calcularTotal();
//
//                // Salvar o carrinho atualizado
//                carrinhoRepository.save(carrinho);
//            }
//        }
//
//        // Retornar para a página do carrinho
//        return new ModelAndView("redirect:/ecommerce/carrinho");
//    }



}

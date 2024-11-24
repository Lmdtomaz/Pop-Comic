package com.store.popcomic.controller;


import com.store.popcomic.model.Usuario;
import com.store.popcomic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("ecommerce")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("adm/usuario/cadastrar")
    public ModelAndView cadastrarUsuario(Usuario usuario){
        ModelAndView mv = new ModelAndView("adm/usuarios/cadastro");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("adm/usuario/salvar")
    public ModelAndView salvarUsuario(@Validated Usuario usuario, BindingResult result){
        if(result.hasErrors()){
            return cadastrarUsuario(usuario);
        }
        usuarioRepository.saveAndFlush(usuario);
        return new ModelAndView("redirect:/ecommerce/adm/usuario/loginAdm");
    }



}



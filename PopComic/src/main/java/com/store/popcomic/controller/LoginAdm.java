package com.store.popcomic.controller;

import com.store.popcomic.model.Usuario;
import com.store.popcomic.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("ecommerce")
public class LoginAdm {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Exibe a tela de login
    @GetMapping("adm/usuario/loginAdm")
    public ModelAndView loginUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("adm/usuarios/login");
        mv.addObject("usuarioLogin", usuario);
        return mv;
    }

    // Verifica as credenciais do usuário e faz login
    @PostMapping("adm/usuario/logged")
    public ModelAndView login(@RequestParam("email") String email, @RequestParam("senha") String senha, HttpSession session) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndSenha(email, senha);

        if (usuario.isPresent()) {
            session.setAttribute("usuarioLogado", usuario.get());
            session.setAttribute("userCpf", usuario.get().getCpf());

            return new ModelAndView("redirect:/backoffice/areaadm");
        } else {
            ModelAndView mv = new ModelAndView("adm/usuarios/login");
            mv.addObject("erro", "Email ou senha inválidos.");
            return mv;
        }
    }

}

package com.store.popcomic.service;

import com.store.popcomic.model.UsuarioCliente;
import com.store.popcomic.repository.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUsuario {

    private final UsuarioDao repositorioUsuario;

    @Autowired
    public ServiceUsuario(UsuarioDao repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public void salvarUsuario(UsuarioCliente user) {
        // Implementar a lógica para salvar o usuário
        repositorioUsuario.save(user);
    }

    public UsuarioCliente loginUser(String email, String senha) throws ServiceExc {
        UsuarioCliente userLogin = repositorioUsuario.buscarLogin(email, senha);
        return repositorioUsuario.buscarLogin(email, senha);
    }
}

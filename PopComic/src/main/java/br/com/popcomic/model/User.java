package br.com.popcomic.model;

public class


User {

        private int idUser;
        private String nome;
        private String cpf;
        private String email;
        private boolean status; //habilitado ou desabilitado.
        private String grupo; //Administrador ou Estoquista.
        private String senha;
        private String repetirSenha;

        public User() {
        }

        public User(int idUser, String nome, String cpf, String email, boolean status, String grupo, String senha, String repetirSenha) {
            this.idUser = idUser;
            this.nome = nome;
            this.cpf = cpf;
            this.email = email;
            this.status = status;
            this.grupo = grupo;
            this.senha = senha;
            this.repetirSenha = repetirSenha;
        }


        public int getIdUser() {
            return idUser;
        }

        public void setIdUser(int idUser) {
            this.idUser = idUser;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getGrupo() {
            return grupo;
        }

        public void setGrupo(String grupo) {
            this.grupo = grupo;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getRepetirSenha() {
            return repetirSenha;
        }

        public void setRepetirSenha(String repetirSenha) {
            this.repetirSenha = repetirSenha;
        }
    }




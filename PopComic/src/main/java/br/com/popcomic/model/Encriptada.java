package br.com.popcomic.model;

import org.mindrot.jbcrypt.BCrypt;

public class Encriptada{
    public static void main(String[] args) {
        String plainPassword = "12345";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        System.out.println("Senha encriptada: " + hashedPassword);
    }
}

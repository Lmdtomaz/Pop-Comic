package com.store.popcomic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
@Id
private String cpf;
private String nome;
@Column(name = "data_nascimento") // Define o nome da coluna no banco, opcional
private LocalDate dataNascimento;
private String genero;
private String senha;

}

package br.senac.pr.exemplospringbootbasicauth.pessoas.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue
    private long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String departamento;
    private String telefone;
    private String endereco;
}

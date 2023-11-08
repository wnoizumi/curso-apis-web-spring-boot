package br.senac.pr.exemplospringbootjwt.seguranca.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrarRequisicao {

    @NotBlank
    private String usuario;

    @NotBlank
    private String senha;
}

package br.senac.pr.exemplospringbootbasicauth.seguranca.api.dtos;

import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Papel;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioView {

    private long id;
    private String usuario;
    private String papel;
}

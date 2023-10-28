package br.senac.pr.exemplospringbootbasicauth.seguranca.api;

import br.senac.pr.exemplospringbootbasicauth.seguranca.api.dtos.RegistrarUsuarioComando;
import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Papel;
import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Usuario;
import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.UsuarioRepositorio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publico/usuarios")
@AllArgsConstructor
public class RegistroUsuarioControlador {

    private UsuarioRepositorio usuarioRepositorio;
    private PasswordEncoder codificadorDeSenhas;

    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid RegistrarUsuarioComando comando) {

        if (comando.senhasNaoConferem()) {
            return ResponseEntity.badRequest().body("Senha diferente da confirmação");
        }

        if (usuarioRepositorio.findByUsuario(comando.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Nome de usuário indisponível!");
        }

        var usuarioSalvo = usuarioRepositorio.save(Usuario
                .builder()
                .usuario(comando.getUsuario())
                .senha(codificadorDeSenhas.encode(comando.getSenha()))
                .papel(Papel.USUARIO)
                .build());

        if (usuarioSalvo != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}

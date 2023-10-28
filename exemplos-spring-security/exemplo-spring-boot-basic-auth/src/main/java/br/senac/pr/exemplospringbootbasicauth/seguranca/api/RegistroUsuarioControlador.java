package br.senac.pr.exemplospringbootbasicauth.seguranca.api;

import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Usuario;
import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.UsuarioRepositorio;
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
    public ResponseEntity registrar(@RequestBody Usuario usuario) {
        var senhaCriptografada = codificadorDeSenhas.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        var usuarioSalvo = usuarioRepositorio.save(usuario);
        if (usuarioSalvo != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

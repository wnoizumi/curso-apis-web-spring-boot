package br.senac.pr.exemplospringbootbasicauth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Encoder;

@RestController
@RequestMapping("/publico/usuarios")
@AllArgsConstructor
public class RegistroUsuarioController {

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

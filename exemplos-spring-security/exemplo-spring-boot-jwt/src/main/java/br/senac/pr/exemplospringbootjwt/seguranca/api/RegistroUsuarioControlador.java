package br.senac.pr.exemplospringbootjwt.seguranca.api;

import br.senac.pr.exemplospringbootjwt.seguranca.api.dtos.EntrarRequisicao;
import br.senac.pr.exemplospringbootjwt.seguranca.api.dtos.RegistrarUsuarioRequisicao;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.Papel;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.Usuario;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.UsuarioRepositorio;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.jwt.JwtUtils;
import br.senac.pr.exemplospringbootjwt.seguranca.dtos.JwtResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/publico")
@AllArgsConstructor
public class RegistroUsuarioControlador {

    private UsuarioRepositorio usuarioRepositorio;
    private PasswordEncoder codificadorDeSenhas;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @PostMapping("/entrar")
    public ResponseEntity<?> entrar(@Valid @RequestBody EntrarRequisicao requisicao) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requisicao.getUsuario(), requisicao.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/usuarios")
    public ResponseEntity registrar(@RequestBody @Valid RegistrarUsuarioRequisicao requisicao) {

        if (requisicao.senhasNaoConferem()) {
            return ResponseEntity.badRequest().body("Senha diferente da confirmação");
        }

        if (usuarioRepositorio.findByUsuario(requisicao.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Nome de usuário indisponível!");
        }

        var usuarioSalvo = usuarioRepositorio.save(Usuario
                .builder()
                .usuario(requisicao.getUsuario())
                .senha(codificadorDeSenhas.encode(requisicao.getSenha()))
                .papel(Papel.USUARIO)
                .build());

        if (usuarioSalvo != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}

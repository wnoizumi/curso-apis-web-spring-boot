package br.senac.pr.exemplospringbootbasicauth.seguranca.dominio;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    public static final String USUARIO_ROLE = "USUARIO";
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null && !username.isBlank()) {
            var usuario = usuarioRepositorio.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found"));
            return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(USUARIO_ROLE)
                .build();
        }

        throw new UsernameNotFoundException("Not Found");
    }
}

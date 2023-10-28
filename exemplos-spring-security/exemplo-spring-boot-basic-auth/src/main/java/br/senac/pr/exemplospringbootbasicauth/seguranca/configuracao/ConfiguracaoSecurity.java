package br.senac.pr.exemplospringbootbasicauth.seguranca.configuracao;

import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.DefaultUserDetailsService;
import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Papel;
import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.UsuarioRepositorio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

import static br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Papel.*;
import static br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Papel.GERENTE;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSecurity {
    @Bean
    public PasswordEncoder myEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio) throws Exception {
        return new DefaultUserDetailsService(usuarioRepositorio);
    }
    @Bean
    public SecurityFilterChain myFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher("/publico/**")).permitAll()
                        .requestMatchers(antMatcher("/geral/**")).hasAnyRole(getNomesPapeis())
                        .requestMatchers(antMatcher("/restrito/**")).hasAnyRole(ADMIN.name(), GERENTE.name())
                        .requestMatchers(antMatcher("/admin/**")).hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
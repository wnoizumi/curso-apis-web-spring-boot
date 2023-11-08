package br.senac.pr.exemplospringbootjwt.seguranca.configuracao;

import br.senac.pr.exemplospringbootjwt.seguranca.dominio.DefaultUserDetailsService;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.UsuarioRepositorio;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.jwt.AuthEntryPointJwt;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.jwt.AuthTokenFilter;
import br.senac.pr.exemplospringbootjwt.seguranca.dominio.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.senac.pr.exemplospringbootjwt.seguranca.dominio.Papel.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSecurity {

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder myEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio) throws Exception {
        return new DefaultUserDetailsService(usuarioRepositorio);
    }

    @Bean
    public SecurityFilterChain myFilterChain(HttpSecurity http,
                                             AuthEntryPointJwt authenticationEntryPoint,
                                             DaoAuthenticationProvider authenticationProvider,
                                             AuthTokenFilter authenticationJwtTokenFilter) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher("/publico/**")).permitAll()
                        .requestMatchers(antMatcher("/geral/**")).hasAnyRole(getNomesPapeis())
                        .requestMatchers(antMatcher("/restrito/**")).hasAnyRole(ADMIN.name(), GERENTE.name())
                        .requestMatchers(antMatcher("/admin/**")).hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
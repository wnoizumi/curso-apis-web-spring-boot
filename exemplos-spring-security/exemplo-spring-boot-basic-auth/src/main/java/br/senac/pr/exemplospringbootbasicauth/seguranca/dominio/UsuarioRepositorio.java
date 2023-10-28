package br.senac.pr.exemplospringbootbasicauth.seguranca.dominio;

import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByUsername(String username);
}

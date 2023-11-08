package br.senac.pr.exemplospringbootjwt.seguranca.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    List<Usuario> searchByPapel(Papel papel);
}

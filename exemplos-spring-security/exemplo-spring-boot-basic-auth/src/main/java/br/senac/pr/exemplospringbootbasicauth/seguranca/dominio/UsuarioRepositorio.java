package br.senac.pr.exemplospringbootbasicauth.seguranca.dominio;

import br.senac.pr.exemplospringbootbasicauth.seguranca.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    List<Usuario> searchByPapel(Papel papel);
}

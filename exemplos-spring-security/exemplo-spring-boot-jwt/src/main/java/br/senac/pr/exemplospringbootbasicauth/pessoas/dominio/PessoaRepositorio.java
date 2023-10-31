package br.senac.pr.exemplospringbootbasicauth.pessoas.dominio;

import br.senac.pr.exemplospringbootbasicauth.pessoas.dominio.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {
}

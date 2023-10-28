package br.senac.pr.exemplospringbootbasicauth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@AllArgsConstructor
public class PessoaController {

    private PessoaRepositorio pessoaRepositorio;

    @GetMapping
    public List<Pessoa> findAll(){
        return pessoaRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable long id){
        return pessoaRepositorio.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody Pessoa pessoa){
        return ResponseEntity.ok((pessoaRepositorio.save(pessoa)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        pessoaRepositorio.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable("id") long id,
                                 @RequestBody Pessoa pessoa) {
        return pessoaRepositorio.findById(id)
                .map(record -> {
                    record.setNome(pessoa.getNome());
                    record.setEmail(pessoa.getEmail());
                    record.setDepartamento(pessoa.getDepartamento());
                    record.setDataNascimento(pessoa.getDataNascimento());
                    record.setTelefone(pessoa.getTelefone());
                    Pessoa updated = pessoaRepositorio.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}

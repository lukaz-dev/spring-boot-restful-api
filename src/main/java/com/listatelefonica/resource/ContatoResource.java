package com.listatelefonica.resource;

import com.listatelefonica.resource.exception.ConflictResourceException;
import com.listatelefonica.resource.exception.NoContentException;
import com.listatelefonica.resource.exception.ResourceNotFoundException;
import com.listatelefonica.model.Contato;
import com.listatelefonica.repository.ContatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/contatos")
public class ContatoResource {

    public static final Logger logger = LoggerFactory.getLogger(ContatoResource.class);

    @Autowired
    private ContatoRepository contatoRepository;

    @GetMapping
    public ResponseEntity<List<Contato>> listarContatos() {
        List<Contato> contatos = contatoRepository.findAll();
        if (contatos.isEmpty()) {
            throw new NoContentException("Nenhum contato disponível");
        }
        logger.info("Listando todos contatos...");
        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> getContato(@PathVariable Long id) {
        logger.info("Buscando contato com id {}", id);
        if (!contatoRepository.exists(id)) {
            throw new ResourceNotFoundException("Contato com id " + id + " não encontrado");
        }
        Contato contato = contatoRepository.findOne(id);
        return new ResponseEntity<>(contato, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> criarContato(@RequestBody Contato contato, UriComponentsBuilder ucBuilder) {
        logger.info("Criando contato : {}", contato);
        if (contatoRepository.existsByNomeAndTelefone(contato.getNome(), contato.getTelefone())) {
            throw new ConflictResourceException("Contato com o nome " + contato.getNome() + ", e telefone "
                + contato.getTelefone() + " já existe");
        }
        contatoRepository.save(contato);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/contatos/{id}").buildAndExpand(contato.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarContato(@PathVariable Long id, @RequestBody Contato contato) {
        logger.info("Atualizando contato com id {}", id);
        Contato currentContato = contatoRepository.findOne(id);
        if (currentContato == null) {
            throw new ResourceNotFoundException("Contato com id " + id + " não encontrado");
        }
        currentContato.setNome(contato.getNome());
        currentContato.setTelefone(contato.getTelefone());
        currentContato.setData(contato.getData());
        currentContato.setOperadora(contato.getOperadora());
        contatoRepository.save(currentContato);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarContato(@PathVariable Long id) {
        logger.info("Removendo contato com id {}", id);
        if (!contatoRepository.exists(id)) {
            throw new ResourceNotFoundException("Contato com id " + id + " não encontrado");
        }
        contatoRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}

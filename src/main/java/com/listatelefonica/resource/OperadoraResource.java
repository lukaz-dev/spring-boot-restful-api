package com.listatelefonica.resource;

import com.listatelefonica.resource.exception.ConflictResourceException;
import com.listatelefonica.resource.exception.NoContentException;
import com.listatelefonica.resource.exception.ResourceNotFoundException;
import com.listatelefonica.model.Operadora;
import com.listatelefonica.repository.OperadoraRepository;
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
@RequestMapping("/api/operadoras")
public class OperadoraResource {

    public static final Logger logger = LoggerFactory.getLogger(OperadoraResource.class);

    @Autowired
    private OperadoraRepository operadoraRepository;

    @GetMapping
    public ResponseEntity<List<Operadora>> listarOperadoras() {
        List<Operadora> operadoras = operadoraRepository.findAll();
        if (operadoras.isEmpty()) {
            throw new NoContentException("Nenhuma operadora disponível");
        }
        logger.info("Listando todas operadoras...");
        return new ResponseEntity<>(operadoras, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operadora> getOperadora(@PathVariable Long id) {
        logger.info("Buscando operadora com id {}", id);
        Operadora operadora = operadoraRepository.findOne(id);
        if (operadora == null) {
            throw new ResourceNotFoundException("Operadora com id " + id + " não encontrada");
        }
        return new ResponseEntity<>(operadora, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> criarOperadora(@RequestBody Operadora operadora, UriComponentsBuilder ucBuilder) {
        logger.info("Criando operadora : {}", operadora);
        if (operadoraRepository.existsByNome(operadora.getNome())) {
            throw new ConflictResourceException("Operadora com o nome " + operadora.getNome() + " já existe");
        }
        operadoraRepository.save(operadora);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/operadoras/{id}").buildAndExpand(operadora.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarOperadora(@PathVariable Long id, @RequestBody Operadora operadora) {
        logger.info("Atualizando operadora com id {}", id);
        Operadora currentOperadora = operadoraRepository.findOne(id);
        if (currentOperadora == null) {
            throw new ResourceNotFoundException("Operadora com id " + id + " não encontrada");
        }
        currentOperadora.setNome(operadora.getNome());
        currentOperadora.setCodigo(operadora.getCodigo());
        currentOperadora.setCategoria(operadora.getCategoria());
        currentOperadora.setPreco(operadora.getPreco());
        operadoraRepository.save(currentOperadora);
        return new ResponseEntity<>(currentOperadora, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarOperadora(@PathVariable Long id) {
        logger.info("Removendo operadora com id {}", id);
        if (!operadoraRepository.exists(id)) {
            throw new ResourceNotFoundException("Operadora com id " + id + " não encontrada");
        }
        operadoraRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}

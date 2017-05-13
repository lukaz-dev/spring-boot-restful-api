package com.listatelefonica.repository;

import com.listatelefonica.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    boolean existsByNomeAndTelefone(String nome, String telefone);
}

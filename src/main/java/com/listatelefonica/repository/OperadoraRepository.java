package com.listatelefonica.repository;

import com.listatelefonica.model.Operadora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperadoraRepository extends JpaRepository<Operadora, Long> {

    boolean existsByNome(String nome);
}

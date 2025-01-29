package com.gerenciador.projetos.domain.repository;

import com.gerenciador.projetos.domain.entity.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
}

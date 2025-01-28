package com.gerenciador.projetos.domain.repository;

import com.gerenciador.projetos.domain.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjetoRepository  extends JpaRepository<Projeto, Long> {
    Optional<Projeto> findByNome(String nome);
}

package com.gerenciador.projetos.domain.repository;

import com.gerenciador.projetos.domain.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByStatusNomeIgnoreCase(String trim);

    List<Tarefa> findByResponsavelNomeIgnoreCase(String trim);

    List<Tarefa> findByResponsavelNomeIgnoreCaseAndStatusNomeIgnoreCase(String trim, String upperCase);
}

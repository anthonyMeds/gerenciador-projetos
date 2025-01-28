package com.gerenciador.projetos.domain.repository;

import com.gerenciador.projetos.domain.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjetoRepository  extends JpaRepository<Projeto, Long> {
    Optional<Projeto> findByNomeIgnoreCase(String nome);

    boolean existsByNome(String nome);

    List<Projeto> findByEquipeIdAndStatusId(Long equipeId, Long statusId);

    List<Projeto> findByEquipeId(Long equipeId);

    List<Projeto> findByStatusId(Long statusId);

    List<Projeto> findByEquipeNomeIgnoreCaseAndStatusNomeIgnoreCase(String nomeEquipe, String nomeStatus);

    List<Projeto> findByEquipeNomeIgnoreCase(String nomeEquipe);

    List<Projeto> findByStatusNomeIgnoreCase(String nomeEquipe);
}

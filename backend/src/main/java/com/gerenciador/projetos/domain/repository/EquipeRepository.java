package com.gerenciador.projetos.domain.repository;

import com.gerenciador.projetos.domain.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}

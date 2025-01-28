package com.gerenciador.projetos.domain.repository;

import com.gerenciador.projetos.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}

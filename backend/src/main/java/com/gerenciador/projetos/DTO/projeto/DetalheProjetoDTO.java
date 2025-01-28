package com.gerenciador.projetos.DTO.projeto;

import com.gerenciador.projetos.domain.entity.Equipe;
import com.gerenciador.projetos.domain.entity.Status;

import java.time.LocalDate;

public record DetalheProjetoDTO(Long id,
                                String nome,
                                String descricao,
                                LocalDate dataInicio,
                                LocalDate dataFim,
                                String nomeEquipe,
                                String nomeStatus
) {
}

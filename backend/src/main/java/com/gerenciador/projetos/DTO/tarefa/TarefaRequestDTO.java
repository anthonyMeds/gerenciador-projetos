package com.gerenciador.projetos.DTO.tarefa;

public record TarefaRequestDTO(
        String titulo,
        String descricao,
        Long projetoId,
        Long responsavelId,
        Integer prazoDias,
        Long statusId
) {
}

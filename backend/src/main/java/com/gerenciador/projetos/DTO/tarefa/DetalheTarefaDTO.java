package com.gerenciador.projetos.DTO.tarefa;

public record DetalheTarefaDTO(
        Long id,
        String titulo,
        String descricao,
        String nomeProjeto,
        String nomeResponsavel,
        Integer prazoDias,
        String statusNome
) {
}

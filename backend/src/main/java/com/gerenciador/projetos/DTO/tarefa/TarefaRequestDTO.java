package com.gerenciador.projetos.DTO.tarefa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TarefaRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 100, message = "Tamanho máximo do campo de 100 dígitos")
        @Schema(example = "título teste")
        String titulo,

        @NotBlank(message = "A descrição da tarefa é obrigatória")
        @Size(max = 255, message = "Tamanho máximo do campo de 255 dígitos")
        @Schema(example = "título teste")
        String descricao,

        @NotNull(message = "O projeto é obrigatório")
        @Schema(example = "1")
        Long projetoId,

        @NotNull(message = "O responsável é obrigatório")
        @Schema(example = "1")
        Long responsavelId,

        @NotNull(message = "O prazo é obrigatório")
        @Schema(example = "10")
        Integer prazoDias,

        @NotNull(message = "O status é obrigatório")
        @Schema(example = "1")
        Long statusId
) {
}

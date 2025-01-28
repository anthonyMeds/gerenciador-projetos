package com.gerenciador.projetos.DTO.projeto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjetoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "Tamanho máximo do campo de 100 dígitos")
        @Schema(example = "projeto teste")
        String nome,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "Tamanho máximo do campo de 255 dígitos")
        @Schema(example = "descricao teste")
        String descricao,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(type = "string", pattern = "dd/MM/yyyy", description = "Data de início", example = "11/05/2012")
        LocalDate dataInicio,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(type = "string", pattern = "dd/MM/yyyy", description = "Data fim", example = "11/05/2012")
        LocalDate dataFim,

        @NotNull
        @Schema(example = "1")
        Long equipeId,

        @NotNull
        @Schema(example = "1")
        Long statusId

        ) {
}

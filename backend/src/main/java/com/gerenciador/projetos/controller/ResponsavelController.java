package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.responsavel.CriarResponsavelDTO;
import com.gerenciador.projetos.DTO.responsavel.DetalheResponsavelDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.ResponsavelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/responsaveis")
@Tag(name = "Responsavel", description = "Gerenciamento de Responsaveis pelas tarefas")
public class ResponsavelController {

    @Autowired
    private ResponsavelService responsavelService;

    @Operation(summary = "Criar novo responsável")
    @PostMapping
    public ResponseEntity<DetalheResponsavelDTO> criarResponsavel(@RequestBody @Valid CriarResponsavelDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.criarResponsavel(dto));
    }

    @Operation(summary = "Buscar detalhe do reponsável por id")
    @GetMapping("/{id}")
    public ResponseEntity<DetalheResponsavelDTO> buscarPorId(@PathVariable Long id) throws ServiceException {
        return ResponseEntity.ok(responsavelService.buscarPorId(id));
    }

    @Operation(summary = "Listar todos os responsáveis")
    @GetMapping
    public ResponseEntity<List<DetalheResponsavelDTO>> listarTodos() {
        return ResponseEntity.ok(responsavelService.listarTodos());
    }

    @Operation(summary = "Atualizar responsável")
    @PutMapping("/{id}")
    public ResponseEntity<DetalheResponsavelDTO> atualizarResponsavel(
            @PathVariable Long id, @RequestBody @Valid CriarResponsavelDTO dto) throws ServiceException {
        return ResponseEntity.ok(responsavelService.atualizarResponsavel(id, dto));
    }

    @Operation(summary = "Deletar responsável")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResponsavel(@PathVariable Long id) throws ServiceException {
        responsavelService.deletarResponsavel(id);
        return ResponseEntity.noContent().build();
    }
}

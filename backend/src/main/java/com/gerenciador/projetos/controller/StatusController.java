package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.status.DetalheStatusDTO;
import com.gerenciador.projetos.DTO.status.StatusRequestDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.StatusService;
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
@RequestMapping("/status")
@Tag(name = "Status", description = "Gerenciamento de Status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Operation(summary = "Criar novo status")
    @PostMapping
    public ResponseEntity<DetalheStatusDTO> criarStatus(@Valid @RequestBody StatusRequestDTO statusRequestDTO) throws ServiceException {
        DetalheStatusDTO response = statusService.criarStatus(statusRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar status")
    @PutMapping("/{id}")
    public ResponseEntity<DetalheStatusDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequestDTO statusRequestDTO
    ) throws ServiceException {
        DetalheStatusDTO response = statusService.atualizarStatus(id, statusRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar detalhe do status pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetalheStatusDTO> buscarStatusPorId(@PathVariable Long id) throws ServiceException {
        DetalheStatusDTO response = statusService.buscarStatusPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todos os status")
    @GetMapping
    public ResponseEntity<List<DetalheStatusDTO>> listarTodosStatus() {
        List<DetalheStatusDTO> response = statusService.listarTodosStatus();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar status")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarStatus(@PathVariable Long id) throws ServiceException {
        statusService.deletarStatus(id);
        return ResponseEntity.noContent().build();
    }
}

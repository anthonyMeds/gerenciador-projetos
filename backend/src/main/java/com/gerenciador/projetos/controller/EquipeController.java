package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.equipe.DetalheEquipeDTO;
import com.gerenciador.projetos.DTO.equipe.EquipeRequestDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.EquipeService;
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
@RequestMapping("/equipes")
@Tag(name = "Equipe", description = "Gerenciamento de Equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;


    @Operation(summary = "Criar nova equipe")
    @PostMapping
    public ResponseEntity<DetalheEquipeDTO> criarEquipe(@Valid @RequestBody EquipeRequestDTO equipeRequestDTO) throws ServiceException {
        DetalheEquipeDTO response = equipeService.criarEquipe(equipeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar equipe")
    @PutMapping("/{id}")
    public ResponseEntity<DetalheEquipeDTO> atualizarEquipe(
            @PathVariable Long id,
            @Valid @RequestBody EquipeRequestDTO equipeRequestDTO
    ) throws ServiceException {
        DetalheEquipeDTO response = equipeService.atualizarEquipe(id, equipeRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar detalhe da equipe pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetalheEquipeDTO> buscarEquipePorId(@PathVariable Long id) throws ServiceException {
        DetalheEquipeDTO response = equipeService.buscarEquipePorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todas as equipes")
    @GetMapping
    public ResponseEntity<List<DetalheEquipeDTO>> listarTodasEquipes() {
        List<DetalheEquipeDTO> response = equipeService.listarTodasEquipes();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar equipe")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEquipe(@PathVariable Long id) throws ServiceException {
        equipeService.deletarEquipe(id);
        return ResponseEntity.noContent().build();
    }
}


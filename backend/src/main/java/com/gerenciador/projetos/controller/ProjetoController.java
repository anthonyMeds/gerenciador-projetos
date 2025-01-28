package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.ProjetoRequestDTO;
import com.gerenciador.projetos.DTO.ProjetoResponseDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Operation(summary = "Cadastrar Projeto", description = "Efetua o cadastro de um novo projeto.")
    @PostMapping()
    public ResponseEntity<ProjetoResponseDTO> cadastrarProjeto (@Valid @RequestBody ProjetoRequestDTO projetoRequestDTO) throws ServiceException, ServiceException {
        ProjetoResponseDTO response = projetoService.cadastrarProjeto(projetoRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar Projeto", description = "Efetua a atualização de um projeto existente pelo id.")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> atualizarProjeto (@Valid @RequestBody ProjetoRequestDTO projetoRequestDTO,
                                                                @PathVariable @NotNull Long id) throws ServiceException, ServiceException {
        ProjetoResponseDTO response = projetoService.atualizarProjeto(projetoRequestDTO, id);
        return ResponseEntity.ok(response);
    }

}

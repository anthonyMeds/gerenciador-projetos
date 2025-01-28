package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.ProjetoRequestDTO;
import com.gerenciador.projetos.DTO.ProjetoResponseDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

}

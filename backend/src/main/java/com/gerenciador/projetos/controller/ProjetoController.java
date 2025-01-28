package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.DetalheProjetoDTO;
import com.gerenciador.projetos.DTO.ProjetoRequestDTO;
import com.gerenciador.projetos.DTO.ProjetoResponseDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Operation(summary = "Detalhar Projeto", description = "Obtém os detalhes de um projeto pelo id.")
    @GetMapping("/{id}")
    public ResponseEntity<DetalheProjetoDTO> buscarDetalheProjeto(@PathVariable @NotNull Long id) throws ServiceException {
        DetalheProjetoDTO response = projetoService.buscarDetalheProjeto(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Detalhar Projeto pelo nome", description = "Obtém os detalhes de um projeto pelo nome.")
    @GetMapping("/buscarPorNome")
    public ResponseEntity<DetalheProjetoDTO> buscarPorNome(
            @Parameter(description = "Nome do projeto", example = "projeto xpto", required = true)
            @RequestParam @NotBlank @Size(max = 100) String nome
    ) throws ServiceException {

        DetalheProjetoDTO response = projetoService.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Projetos", description = "Busca projetos pelo nome da equipe, status ou todos os projetos caso nenhum filtro seja informado.")
    @GetMapping("/buscarProjetos")
    public ResponseEntity<List<DetalheProjetoDTO>> buscarProjetos(
            @RequestParam(required = false) String nomeEquipe,
            @RequestParam(required = false) String nomeStatus
    ) throws ServiceException {
        List<DetalheProjetoDTO> projetos = projetoService.buscarProjetos(nomeEquipe, nomeStatus);
        return ResponseEntity.ok(projetos);
    }




}

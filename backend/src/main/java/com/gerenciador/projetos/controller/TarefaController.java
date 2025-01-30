package com.gerenciador.projetos.controller;

import com.gerenciador.projetos.DTO.tarefa.DetalheTarefaDTO;
import com.gerenciador.projetos.DTO.tarefa.TarefaRequestDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Gerenciamento de Tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(summary = "Criar Tarefa")
    @PostMapping
    public ResponseEntity<DetalheTarefaDTO> criarTarefa(@RequestBody TarefaRequestDTO request) throws ServiceException {
        DetalheTarefaDTO response = tarefaService.criarTarefa(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar Tarefa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetalheTarefaDTO> buscarTarefaPorId(@PathVariable Long id) throws ServiceException {
        DetalheTarefaDTO response = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Todas as Tarefas")
    @GetMapping
    public ResponseEntity<List<DetalheTarefaDTO>> listarTodasTarefas() {
        List<DetalheTarefaDTO> response = tarefaService.listarTodas();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Tarefas por responsável ou status", description = "Busca tarefas pelo nome do responsável, status ou todas as tarefas caso nenhum filtro seja informado.")
    @GetMapping("/buscarTarefas")
    public ResponseEntity<List<DetalheTarefaDTO>> buscarTarefas(
            @RequestParam(required = false) String nomeResponsavel,
            @RequestParam(required = false) String nomeStatus
    ) throws ServiceException {
        List<DetalheTarefaDTO> tarefas = tarefaService.buscarTarefas(nomeResponsavel, nomeStatus);
        return ResponseEntity.ok(tarefas);
    }


    @Operation(summary = "Atualizar Tarefa")
    @PutMapping("/{id}")
    public ResponseEntity<DetalheTarefaDTO> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaRequestDTO request) throws ServiceException {
        DetalheTarefaDTO response = tarefaService.atualizarTarefa(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir Tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) throws ServiceException {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
}

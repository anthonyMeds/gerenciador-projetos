package com.gerenciador.projetos.domain.service;

import com.gerenciador.projetos.DTO.tarefa.DetalheTarefaDTO;
import com.gerenciador.projetos.DTO.tarefa.TarefaRequestDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.entity.Projeto;
import com.gerenciador.projetos.domain.entity.Responsavel;
import com.gerenciador.projetos.domain.entity.Status;
import com.gerenciador.projetos.domain.entity.Tarefa;
import com.gerenciador.projetos.domain.repository.ProjetoRepository;
import com.gerenciador.projetos.domain.repository.ResponsavelRepository;
import com.gerenciador.projetos.domain.repository.StatusRepository;
import com.gerenciador.projetos.domain.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private StatusRepository statusRepository;

    public DetalheTarefaDTO criarTarefa(TarefaRequestDTO request) throws ServiceException {
        Projeto projeto = projetoRepository.findById(request.projetoId())
                .orElseThrow(() -> new ServiceException("Projeto não encontrado"));

        Responsavel responsavel = responsavelRepository.findById(request.responsavelId())
                .orElseThrow(() -> new ServiceException("Responsável não encontrado"));

        Status status = statusRepository.findById(request.statusId())
                .orElseThrow(() -> new ServiceException("Status não encontrado"));

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(request.titulo());
        tarefa.setDescricao(request.descricao());
        tarefa.setProjeto(projeto);
        tarefa.setResponsavel(responsavel);
        tarefa.setPrazoDias(request.prazoDias());
        tarefa.setStatus(status);

        tarefaRepository.save(tarefa);

        return mapToDetalheTarefaDTO(tarefa);
    }

    public DetalheTarefaDTO buscarPorId(Long id) throws ServiceException {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Tarefa não encontrada"));

        return mapToDetalheTarefaDTO(tarefa);
    }

    public List<DetalheTarefaDTO> listarTodas() {
        return tarefaRepository.findAll()
                .stream()
                .map(this::mapToDetalheTarefaDTO)
                .toList();
    }

    public DetalheTarefaDTO atualizarTarefa(Long id, TarefaRequestDTO request) throws ServiceException {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Tarefa não encontrada"));

        Projeto projeto = projetoRepository.findById(request.projetoId())
                .orElseThrow(() -> new ServiceException("Projeto não encontrado"));

        Responsavel responsavel = responsavelRepository.findById(request.responsavelId())
                .orElseThrow(() -> new ServiceException("Responsável não encontrado"));

        Status status = statusRepository.findById(request.statusId())
                .orElseThrow(() -> new ServiceException("Status não encontrado"));

        tarefa.setTitulo(request.titulo());
        tarefa.setDescricao(request.descricao());
        tarefa.setProjeto(projeto);
        tarefa.setResponsavel(responsavel);
        tarefa.setPrazoDias(request.prazoDias());
        tarefa.setStatus(status);

        tarefaRepository.save(tarefa);

        return mapToDetalheTarefaDTO(tarefa);
    }

    public void excluirTarefa(Long id) throws ServiceException {
        if (!tarefaRepository.existsById(id)) {
            throw new ServiceException("Tarefa não encontrada");
        }

        tarefaRepository.deleteById(id);
    }

    private DetalheTarefaDTO mapToDetalheTarefaDTO(Tarefa tarefa) {
        return new DetalheTarefaDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getProjeto().getNome(),
                tarefa.getResponsavel().getNome(),
                tarefa.getPrazoDias(),
                tarefa.getStatus().getNome()
        );
    }

    public List<DetalheTarefaDTO> buscarTarefas(String nomeResponsavel, String nomeStatus) throws ServiceException {
        List<Tarefa> tarefas;

        if (nomeResponsavel != null && nomeStatus != null) {
            tarefas = tarefaRepository.findByResponsavelNomeIgnoreCaseAndStatusNomeIgnoreCase(
                    nomeResponsavel.trim(), nomeStatus.trim().toUpperCase());
        } else if (nomeResponsavel != null) {
            tarefas = tarefaRepository.findByResponsavelNomeIgnoreCase(nomeResponsavel.trim());
        } else if (nomeStatus != null) {
            tarefas = tarefaRepository.findByStatusNomeIgnoreCase(nomeStatus.trim());
        } else {
            tarefas = tarefaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        if (tarefas.isEmpty()) {
            throw new ServiceException("Nenhuma tarefa encontrada com os filtros fornecidos.");
        }

        return tarefas.stream()
                .map(tarefa -> new DetalheTarefaDTO(
                        tarefa.getId(),
                        tarefa.getTitulo(),
                        tarefa.getDescricao(),
                        tarefa.getProjeto().getNome(),
                        tarefa.getResponsavel().getNome(),
                        tarefa.getPrazoDias(),
                        tarefa.getStatus().getNome()
                )).toList();
    }

}


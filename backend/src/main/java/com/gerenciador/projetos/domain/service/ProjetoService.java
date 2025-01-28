package com.gerenciador.projetos.domain.service;

import com.gerenciador.projetos.DTO.projeto.DetalheProjetoDTO;
import com.gerenciador.projetos.DTO.projeto.ProjetoRequestDTO;
import com.gerenciador.projetos.DTO.projeto.ProjetoResponseDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.entity.Equipe;
import com.gerenciador.projetos.domain.entity.Projeto;
import com.gerenciador.projetos.domain.entity.Status;
import com.gerenciador.projetos.domain.repository.EquipeRepository;
import com.gerenciador.projetos.domain.repository.ProjetoRepository;
import com.gerenciador.projetos.domain.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    private static final Logger logger = LoggerFactory.getLogger(ProjetoService.class);

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private StatusRepository statusRepository;


    public ProjetoResponseDTO cadastrarProjeto(ProjetoRequestDTO projetoRequestDTO) throws ServiceException {

        if (projetoRepository.findByNomeIgnoreCase(projetoRequestDTO.nome()).isPresent()) {
            throw new ServiceException("Projeto já existente");
        }

        Equipe equipe = equipeRepository.findById(projetoRequestDTO.equipeId())
                .orElseThrow(() -> new ServiceException("Equipe não encontrada"));

        Status status = statusRepository.findById(projetoRequestDTO.statusId())
                .orElseThrow(() -> new ServiceException("Status não encontrado"));

        Projeto projeto = new Projeto();

        BeanUtils.copyProperties(projetoRequestDTO, projeto);
        projeto.setEquipe(equipe);
        projeto.setStatus(status);

        try {
            projetoRepository.save(projeto);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Não foi possível cadastrar o projeto.");
        }

        return new ProjetoResponseDTO(projeto.getId(), "Cadastro realizado com sucesso");
    }

    public ProjetoResponseDTO atualizarProjeto(ProjetoRequestDTO projetoRequestDTO, Long id) throws ServiceException {

        Optional<Projeto> projetoOptional = projetoRepository.findById(id);

        if (projetoOptional.isEmpty()) {
            throw new ServiceException("Não foi encontrado um projeto com os dados informados.");
        }

        Projeto projeto = projetoOptional.get();

        Optional<Projeto> projetoComMesmoNome = projetoRepository.findByNomeIgnoreCase(projetoRequestDTO.nome());
        if (projetoComMesmoNome.isPresent() && !projetoComMesmoNome.get().getId().equals(id)) {
            throw new ServiceException("Já existe um projeto com o nome informado.");
        }

        Equipe equipe = equipeRepository.findById(projetoRequestDTO.equipeId())
                .orElseThrow(() -> new ServiceException("Equipe não encontrada"));

        Status status = statusRepository.findById(projetoRequestDTO.statusId())
                .orElseThrow(() -> new ServiceException("Status não encontrado"));

        BeanUtils.copyProperties(projetoRequestDTO, projeto);
        projeto.setEquipe(equipe);
        projeto.setStatus(status);

        try {
            projetoRepository.save(projeto);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Não foi possível atualizar o projeto.");
        }

        return new ProjetoResponseDTO(projeto.getId(), "Atualização realizada com sucesso");
    }

    public DetalheProjetoDTO buscarDetalheProjeto(Long idProjeto) throws ServiceException {

        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new ServiceException("Projeto não encontrado"));

        return new DetalheProjetoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getEquipe().getNome(),
                projeto.getStatus().getNome()
        );
    }

    public DetalheProjetoDTO buscarPorNome(String nome) throws ServiceException {
        Projeto projeto = projetoRepository.findByNomeIgnoreCase(nome.trim())
                .orElseThrow(() -> new ServiceException("Projeto não encontrado"));

        return new DetalheProjetoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getEquipe().getNome(),
                projeto.getStatus().getNome()
        );
    }

    public List<DetalheProjetoDTO> buscarProjetos(String nomeEquipe, String nomeStatus) throws ServiceException {
        List<Projeto> projetos;

        if (nomeEquipe != null && nomeStatus != null) {
            projetos = projetoRepository.findByEquipeNomeIgnoreCaseAndStatusNomeIgnoreCase(nomeEquipe.trim(), nomeStatus.trim().toUpperCase());
        } else if (nomeEquipe != null) {
            projetos = projetoRepository.findByEquipeNomeIgnoreCase(nomeEquipe.trim());
        } else if (nomeStatus != null) {
            projetos = projetoRepository.findByStatusNomeIgnoreCase(nomeStatus.trim());
        } else {
            projetos = projetoRepository.findAll();
        }

        if (projetos.isEmpty()) {
            throw new ServiceException("Nenhum projeto encontrado com os filtros fornecidos.");
        }

        return projetos.stream()
                .map(projeto -> new DetalheProjetoDTO(
                        projeto.getId(),
                        projeto.getNome(),
                        projeto.getDescricao(),
                        projeto.getDataInicio(),
                        projeto.getDataFim(),
                        projeto.getEquipe().getNome(),
                        projeto.getStatus().getNome()
                )).toList();
    }


    public ProjetoResponseDTO deletarProjeto(Long id) throws ServiceException {

        if (projetoRepository.findById(id).isEmpty()) {
            throw new ServiceException("Projeto não cadastrado.");
        }

        try {
            projetoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Não foi possível excluir o projeto.");
        }


        return new ProjetoResponseDTO(id, "Projeto deletado com sucesso.");

    }
}

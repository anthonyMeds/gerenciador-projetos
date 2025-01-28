package com.gerenciador.projetos.domain.service;

import com.gerenciador.projetos.DTO.ProjetoRequestDTO;
import com.gerenciador.projetos.DTO.ProjetoResponseDTO;
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

        if (projetoRepository.findByNome(projetoRequestDTO.nome()).isPresent()) {
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
}

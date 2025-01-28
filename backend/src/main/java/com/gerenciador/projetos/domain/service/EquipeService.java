package com.gerenciador.projetos.domain.service;

import com.gerenciador.projetos.DTO.equipe.DetalheEquipeDTO;
import com.gerenciador.projetos.DTO.equipe.EquipeRequestDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.entity.Equipe;
import com.gerenciador.projetos.domain.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;


    public DetalheEquipeDTO criarEquipe(EquipeRequestDTO equipeRequestDTO) throws ServiceException {
        Equipe equipe = new Equipe();
        equipe.setNome(equipeRequestDTO.nome().trim().toUpperCase());

        try {
            equipe = equipeRepository.save(equipe);
        } catch (Exception e) {
            throw new ServiceException("Já existe uma equipe com o mesmo nome.");
        }

        return new DetalheEquipeDTO(equipe.getId(), equipe.getNome());
    }

    public DetalheEquipeDTO atualizarEquipe(Long id, EquipeRequestDTO equipeRequestDTO) throws ServiceException {
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Equipe não encontrada."));

        equipe.setNome(equipeRequestDTO.nome().trim().toUpperCase());

        try {
            equipeRepository.save(equipe);
        } catch (Exception e) {
            throw new ServiceException("Já existe uma equipe com o mesmo nome.");
        }

        return new DetalheEquipeDTO(equipe.getId(), equipe.getNome());
    }

    public DetalheEquipeDTO buscarEquipePorId(Long id) throws ServiceException {
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Equipe não encontrada."));
        return new DetalheEquipeDTO(equipe.getId(), equipe.getNome());
    }

    public List<DetalheEquipeDTO> listarTodasEquipes() {
        return equipeRepository.findAll()
                .stream()
                .map(equipe -> new DetalheEquipeDTO(equipe.getId(), equipe.getNome()))
                .toList();
    }

    public void deletarEquipe(Long id) throws ServiceException {
        if (!equipeRepository.existsById(id)) {
            throw new ServiceException("Equipe não encontrada.");
        }
        equipeRepository.deleteById(id);
    }
}


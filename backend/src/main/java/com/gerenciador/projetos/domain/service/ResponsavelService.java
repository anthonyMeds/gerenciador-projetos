package com.gerenciador.projetos.domain.service;

import com.gerenciador.projetos.DTO.responsavel.CriarResponsavelDTO;
import com.gerenciador.projetos.DTO.responsavel.DetalheResponsavelDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.entity.Responsavel;
import com.gerenciador.projetos.domain.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {

    @Autowired
    private ResponsavelRepository responsavelRepository;

    public DetalheResponsavelDTO criarResponsavel(CriarResponsavelDTO dto) {
        Responsavel responsavel = new Responsavel();
        responsavel.setNome(dto.nome().trim().toUpperCase());
        responsavelRepository.save(responsavel);
        return new DetalheResponsavelDTO(responsavel.getId(), responsavel.getNome());
    }

    public DetalheResponsavelDTO buscarPorId(Long id) throws ServiceException {
        Responsavel responsavel = responsavelRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Responsável não encontrado"));
        return new DetalheResponsavelDTO(responsavel.getId(), responsavel.getNome());
    }

    public List<DetalheResponsavelDTO> listarTodos() {
        return responsavelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(responsavel -> new DetalheResponsavelDTO(responsavel.getId(), responsavel.getNome()))
                .toList();
    }

    public DetalheResponsavelDTO atualizarResponsavel(Long id, CriarResponsavelDTO dto) throws ServiceException {
        Responsavel responsavel = responsavelRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Responsável não encontrado"));
        responsavel.setNome(dto.nome().trim().toUpperCase());
        responsavelRepository.save(responsavel);
        return new DetalheResponsavelDTO(responsavel.getId(), responsavel.getNome());
    }

    public void deletarResponsavel(Long id) throws ServiceException {
        if (!responsavelRepository.existsById(id)) {
            throw new ServiceException( "Responsável não encontrado");
        }
        responsavelRepository.deleteById(id);
    }
}


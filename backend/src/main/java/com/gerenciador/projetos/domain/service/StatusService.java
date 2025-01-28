package com.gerenciador.projetos.domain.service;

import com.gerenciador.projetos.DTO.status.DetalheStatusDTO;
import com.gerenciador.projetos.DTO.status.StatusRequestDTO;
import com.gerenciador.projetos.config.exception.ServiceException;
import com.gerenciador.projetos.domain.entity.Status;
import com.gerenciador.projetos.domain.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public DetalheStatusDTO criarStatus(StatusRequestDTO statusRequestDTO) throws ServiceException {
        Status status = new Status();
        status.setNome(statusRequestDTO.nome().trim().toUpperCase());

        try {
            status = statusRepository.save(status);
        } catch (Exception e) {
            throw new ServiceException("Já existe um status com o mesmo nome.");
        }

        return new DetalheStatusDTO(status.getId(), status.getNome());
    }

    public DetalheStatusDTO atualizarStatus(Long id, StatusRequestDTO statusRequestDTO) throws ServiceException {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Status não encontrado."));

        status.setNome(statusRequestDTO.nome().trim().toUpperCase());

        try {
            statusRepository.save(status);
        } catch (Exception e) {
            throw new ServiceException("Já existe um status com o mesmo nome.");
        }

        return new DetalheStatusDTO(status.getId(), status.getNome());
    }

    public DetalheStatusDTO buscarStatusPorId(Long id) throws ServiceException {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Status não encontrado."));
        return new DetalheStatusDTO(status.getId(), status.getNome());
    }

    public List<DetalheStatusDTO> listarTodosStatus() {
        return statusRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(status -> new DetalheStatusDTO(status.getId(), status.getNome()))
                .toList();
    }


    public void deletarStatus(Long id) throws ServiceException {
        if (!statusRepository.existsById(id)) {
            throw new ServiceException("Status não encontrado.");
        }
        statusRepository.deleteById(id);
    }
}


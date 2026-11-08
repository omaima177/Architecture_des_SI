package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.mapper.AgenceMapper;
import tn.esprit.tic.ssa.autoloc1.repository.AgenceRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.AgenceDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    @Transactional
    public AgenceDTO addAgence(AgenceDTO dto) {
        Agence saved = agenceRepository.save(agenceMapper.toEntity(dto));
        return agenceMapper.toDto(saved);
    }

    @Override
    @Transactional
    public AgenceDTO updateAgence(Long id, AgenceDTO dto) {
        Agence existing = findAgence(id);
        agenceMapper.updateEntity(dto, existing);
        return agenceMapper.toDto(agenceRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO getAgenceById(Long id) {
        return agenceMapper.toDto(findAgence(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> getAllAgences() {
        return agenceRepository.findAll().stream().map(agenceMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteAgence(Long id) {
        Agence existing = findAgence(id);
        if (!existing.getVehicules().isEmpty() || !existing.getEmployes().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer cette agence : elle contient encore des véhicules ou des employés.");
        }
        agenceRepository.delete(existing);
    }

    private Agence findAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agence introuvable avec l'id : " + id));
    }
}
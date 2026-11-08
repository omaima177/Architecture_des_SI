package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.mapper.EquipementMapper;
import tn.esprit.tic.ssa.autoloc1.repository.EquipementRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.EquipementDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final EquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    @Transactional
    public EquipementDTO addEquipement(EquipementDTO dto) {
        Equipement saved = equipementRepository.save(equipementMapper.toEntity(dto));
        return equipementMapper.toDto(saved);
    }

    @Override
    @Transactional
    public EquipementDTO updateEquipement(Long id, EquipementDTO dto) {
        Equipement existing = findEquipement(id);
        equipementMapper.updateEntity(dto, existing);
        return equipementMapper.toDto(equipementRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementDTO getEquipementById(Long id) {
        return equipementMapper.toDto(findEquipement(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> getAllEquipements() {
        return equipementRepository.findAll().stream().map(equipementMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteEquipement(Long id) {
        Equipement existing = findEquipement(id);
        // Vehicule est le côté propriétaire du @ManyToMany : on retire d'abord le lien
        for (Vehicule v : new ArrayList<>(existing.getVehicules())) {
            v.getEquipements().remove(existing);
        }
        equipementRepository.delete(existing);
    }

    private Equipement findEquipement(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Équipement introuvable avec l'id : " + id));
    }
}
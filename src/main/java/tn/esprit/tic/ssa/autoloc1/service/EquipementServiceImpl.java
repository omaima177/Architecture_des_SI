package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.repository.EquipementRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final EquipementRepository equipementRepository;

    @Override
    @Transactional
    public Equipement addEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    @Transactional
    public Equipement updateEquipement(Long id, Equipement equipement) {
        Equipement existing = getEquipementById(id);
        existing.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement getEquipementById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Équipement introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEquipement(Long id) {
        Equipement existing = getEquipementById(id);
        // Vehicule est le côté propriétaire du @ManyToMany : on retire d'abord le lien
        for (Vehicule v : new ArrayList<>(existing.getVehicules())) {
            v.getEquipements().remove(existing);
        }
        equipementRepository.delete(existing);
    }
}
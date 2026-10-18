package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.repository.AgenceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;

    @Override
    @Transactional
    public Agence addAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    @Transactional
    public Agence updateAgence(Long id, Agence agence) {
        Agence existing = getAgenceById(id);
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return agenceRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agence introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAgence(Long id) {
        Agence existing = getAgenceById(id);
        // Pas de cascade de suppression Agence -> Vehicule / Employe (voir énoncé)
        if (!existing.getVehicules().isEmpty() || !existing.getEmployes().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer cette agence : elle contient encore des véhicules ou des employés.");
        }
        agenceRepository.delete(existing);
    }
}
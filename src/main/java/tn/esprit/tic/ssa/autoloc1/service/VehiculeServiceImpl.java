package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Vehicule addVehicule(Vehicule vehicule) {
        if (vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException(
                    "Un véhicule avec cette immatriculation existe déjà : "
                            + vehicule.getImmatriculation());
        }
        if (vehicule.getStatut() == null) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        Vehicule existing = getVehiculeById(id);
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        existing.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Véhicule introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> getVehiculesByStatut(StatutVehicule statut) {
        return vehiculeRepository.findByStatut(statut);
    }

    @Override
    @Transactional
    public void deleteVehicule(Long id) {
        Vehicule existing = getVehiculeById(id);
        vehiculeRepository.delete(existing);
    }
}
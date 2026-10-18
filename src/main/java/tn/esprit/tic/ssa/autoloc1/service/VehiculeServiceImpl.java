package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.repository.AgenceRepository;
import tn.esprit.tic.ssa.autoloc1.repository.EquipementRepository;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;

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
        vehicule.setAgence(findAgence(vehicule.getAgence()));
        vehicule.setEquipements(resolveEquipements(vehicule.getEquipements()));
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        Vehicule existing = getVehiculeById(id);
        if (vehicule.getImmatriculation() != null
                && !vehicule.getImmatriculation().equals(existing.getImmatriculation())
                && vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException(
                    "Un véhicule avec cette immatriculation existe déjà : "
                            + vehicule.getImmatriculation());
        }
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        if (vehicule.getStatut() != null) {
            existing.setStatut(vehicule.getStatut());
        }
        if (vehicule.getAgence() != null) {
            existing.setAgence(findAgence(vehicule.getAgence()));
        }
        // PUT = remplacement complet de la liste d'équipements
        List<Equipement> equipements = resolveEquipements(vehicule.getEquipements());
        existing.getEquipements().clear();
        existing.getEquipements().addAll(equipements);
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
        if (!existing.getReservations().isEmpty() || !existing.getMaintenances().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce véhicule : il a des réservations ou des maintenances.");
        }
        vehiculeRepository.delete(existing);
    }

    private Agence findAgence(Agence ref) {
        if (ref == null || ref.getIdAgence() == null) {
            throw new IllegalArgumentException("agenceId est obligatoire");
        }
        return agenceRepository.findById(ref.getIdAgence())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agence introuvable avec l'id : " + ref.getIdAgence()));
    }

    private List<Equipement> resolveEquipements(List<Equipement> refs) {
        if (refs == null || refs.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> ids = refs.stream()
                .map(Equipement::getIdEquipement)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        List<Equipement> found = equipementRepository.findAllById(ids);
        if (found.size() != ids.size()) {
            throw new EntityNotFoundException(
                    "Un ou plusieurs équipements sont introuvables : " + ids);
        }
        return new ArrayList<>(found);
    }
}
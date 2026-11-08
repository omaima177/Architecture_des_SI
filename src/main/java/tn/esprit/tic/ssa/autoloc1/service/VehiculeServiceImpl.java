package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.mapper.VehiculeMapper;
import tn.esprit.tic.ssa.autoloc1.repository.AgenceRepository;
import tn.esprit.tic.ssa.autoloc1.repository.EquipementRepository;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.VehiculeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private static final String IMMATRICULATION_EXISTS =
            "Un véhicule avec cette immatriculation existe déjà : ";

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional
    public VehiculeDTO addVehicule(VehiculeDTO dto) {
        if (vehiculeRepository.existsByImmatriculation(dto.immatriculation())) {
            throw new IllegalArgumentException(IMMATRICULATION_EXISTS + dto.immatriculation());
        }
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        if (vehicule.getStatut() == null) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
        }
        vehicule.setAgence(findAgence(dto.agenceId()));
        vehicule.setEquipements(resolveEquipements(dto.equipementIds()));
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeDTO updateVehicule(Long id, VehiculeDTO dto) {
        Vehicule existing = findVehicule(id);
        if (!dto.immatriculation().equals(existing.getImmatriculation())
                && vehiculeRepository.existsByImmatriculation(dto.immatriculation())) {
            throw new IllegalArgumentException(IMMATRICULATION_EXISTS + dto.immatriculation());
        }
        vehiculeMapper.updateEntity(dto, existing);
        existing.setAgence(findAgence(dto.agenceId()));
        // PUT = remplacement complet de la liste d'équipements
        List<Equipement> equipements = resolveEquipements(dto.equipementIds());
        existing.getEquipements().clear();
        existing.getEquipements().addAll(equipements);
        return vehiculeMapper.toDto(vehiculeRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO getVehiculeById(Long id) {
        return vehiculeMapper.toDto(findVehicule(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getAllVehicules() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getVehiculesByStatut(StatutVehicule statut) {
        return vehiculeRepository.findByStatut(statut).stream().map(vehiculeMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteVehicule(Long id) {
        Vehicule existing = findVehicule(id);
        if (!existing.getReservations().isEmpty() || !existing.getMaintenances().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce véhicule : il a des réservations ou des maintenances.");
        }
        vehiculeRepository.delete(existing);
    }

    private Vehicule findVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Véhicule introuvable avec l'id : " + id));
    }

    private Agence findAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agence introuvable avec l'id : " + id));
    }

    private List<Equipement> resolveEquipements(List<Long> equipementIds) {
        if (equipementIds == null || equipementIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> ids = equipementIds.stream()
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
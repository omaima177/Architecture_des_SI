package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Maintenance;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.repository.MaintenanceRepository;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Maintenance addMaintenance(Maintenance maintenance) {
        checkDates(maintenance);
        maintenance.setVehicule(findVehicule(maintenance.getVehicule()));
        return maintenanceRepository.save(maintenance);
    }

    @Override
    @Transactional
    public Maintenance updateMaintenance(Long id, Maintenance maintenance) {
        Maintenance existing = getMaintenanceById(id);
        checkDates(maintenance);
        existing.setDateDebut(maintenance.getDateDebut());
        existing.setDateFin(maintenance.getDateFin());
        existing.setDescription(maintenance.getDescription());
        if (maintenance.getVehicule() != null) {
            existing.setVehicule(findVehicule(maintenance.getVehicule()));
        }
        return maintenanceRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Maintenance introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteMaintenance(Long id) {
        maintenanceRepository.delete(getMaintenanceById(id));
    }

    private void checkDates(Maintenance m) {
        if (m.getDateDebut() == null || m.getDateFin() == null) {
            throw new IllegalArgumentException("dateDebut et dateFin sont obligatoires");
        }
        if (m.getDateFin().isBefore(m.getDateDebut())) {
            throw new IllegalArgumentException("dateFin ne peut pas précéder dateDebut");
        }
    }

    private Vehicule findVehicule(Vehicule ref) {
        if (ref == null || ref.getIdVehicule() == null) {
            throw new IllegalArgumentException("vehiculeId est obligatoire");
        }
        return vehiculeRepository.findById(ref.getIdVehicule())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Véhicule introuvable avec l'id : " + ref.getIdVehicule()));
    }
}
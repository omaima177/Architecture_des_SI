package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Maintenance;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.mapper.MaintenanceMapper;
import tn.esprit.tic.ssa.autoloc1.repository.MaintenanceRepository;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.MaintenanceDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    @Transactional
    public MaintenanceDTO addMaintenance(MaintenanceDTO dto) {
        checkDates(dto.dateDebut(), dto.dateFin());
        Maintenance maintenance = maintenanceMapper.toEntity(dto);
        maintenance.setVehicule(findVehicule(dto.vehiculeId()));
        return maintenanceMapper.toDto(maintenanceRepository.save(maintenance));
    }

    @Override
    @Transactional
    public MaintenanceDTO updateMaintenance(Long id, MaintenanceDTO dto) {
        Maintenance existing = findMaintenance(id);
        checkDates(dto.dateDebut(), dto.dateFin());
        maintenanceMapper.updateEntity(dto, existing);
        existing.setVehicule(findVehicule(dto.vehiculeId()));
        return maintenanceMapper.toDto(maintenanceRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO getMaintenanceById(Long id) {
        return maintenanceMapper.toDto(findMaintenance(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> getAllMaintenances() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteMaintenance(Long id) {
        maintenanceRepository.delete(findMaintenance(id));
    }

    private void checkDates(LocalDate debut, LocalDate fin) {
        if (fin.isBefore(debut)) {
            throw new IllegalArgumentException("dateFin ne peut pas précéder dateDebut");
        }
    }

    private Maintenance findMaintenance(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Maintenance introuvable avec l'id : " + id));
    }

    private Vehicule findVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Véhicule introuvable avec l'id : " + id));
    }
}
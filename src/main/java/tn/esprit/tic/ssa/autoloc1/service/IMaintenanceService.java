// IMaintenanceService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.MaintenanceDTO;

import java.util.List;

public interface IMaintenanceService {
    MaintenanceDTO addMaintenance(MaintenanceDTO dto);
    MaintenanceDTO updateMaintenance(Long id, MaintenanceDTO dto);
    MaintenanceDTO getMaintenanceById(Long id);
    List<MaintenanceDTO> getAllMaintenances();
    void deleteMaintenance(Long id);
}
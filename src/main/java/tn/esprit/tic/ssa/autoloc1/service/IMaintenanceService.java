package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {
    Maintenance addMaintenance(Maintenance maintenance);
    Maintenance updateMaintenance(Long id, Maintenance maintenance);
    Maintenance getMaintenanceById(Long id);
    List<Maintenance> getAllMaintenances();
    void deleteMaintenance(Long id);
}
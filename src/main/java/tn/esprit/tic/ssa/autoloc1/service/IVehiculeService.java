// IVehiculeService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.web.dto.VehiculeDTO;

import java.util.List;

public interface IVehiculeService {
    VehiculeDTO addVehicule(VehiculeDTO dto);
    VehiculeDTO updateVehicule(Long id, VehiculeDTO dto);
    VehiculeDTO getVehiculeById(Long id);
    List<VehiculeDTO> getAllVehicules();
    List<VehiculeDTO> getVehiculesByStatut(StatutVehicule statut);
    void deleteVehicule(Long id);
}
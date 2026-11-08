// IEquipementService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.EquipementDTO;

import java.util.List;

public interface IEquipementService {
    EquipementDTO addEquipement(EquipementDTO dto);
    EquipementDTO updateEquipement(Long id, EquipementDTO dto);
    EquipementDTO getEquipementById(Long id);
    List<EquipementDTO> getAllEquipements();
    void deleteEquipement(Long id);
}
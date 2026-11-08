// IAgenceService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.AgenceDTO;

import java.util.List;

public interface IAgenceService {
    AgenceDTO addAgence(AgenceDTO dto);
    AgenceDTO updateAgence(Long id, AgenceDTO dto);
    AgenceDTO getAgenceById(Long id);
    List<AgenceDTO> getAllAgences();
    void deleteAgence(Long id);
}
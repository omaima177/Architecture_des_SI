// IContratService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.ContratDTO;

import java.util.List;

public interface IContratService {
    ContratDTO addContrat(ContratDTO dto);
    ContratDTO updateContrat(Long id, ContratDTO dto);
    ContratDTO getContratById(Long id);
    List<ContratDTO> getAllContrats();
    void deleteContrat(Long id);
}
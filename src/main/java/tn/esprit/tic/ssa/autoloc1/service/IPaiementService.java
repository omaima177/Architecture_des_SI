// IPaiementService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.PaiementDTO;

import java.util.List;

public interface IPaiementService {
    PaiementDTO addPaiement(PaiementDTO dto);
    PaiementDTO updatePaiement(Long id, PaiementDTO dto);
    PaiementDTO getPaiementById(Long id);
    List<PaiementDTO> getAllPaiements();
    void deletePaiement(Long id);
}
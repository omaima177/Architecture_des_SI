package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.domain.Paiement;
import tn.esprit.tic.ssa.autoloc1.mapper.PaiementMapper;
import tn.esprit.tic.ssa.autoloc1.repository.ContratRepository;
import tn.esprit.tic.ssa.autoloc1.repository.PaiementRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.PaiementDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratRepository contratRepository;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional
    public PaiementDTO addPaiement(PaiementDTO dto) {
        Paiement paiement = paiementMapper.toEntity(dto);
        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(LocalDate.now());
        }
        paiement.setContrat(findContrat(dto.contratId()));
        return paiementMapper.toDto(paiementRepository.save(paiement));
    }

    @Override
    @Transactional
    public PaiementDTO updatePaiement(Long id, PaiementDTO dto) {
        Paiement existing = findPaiement(id);
        paiementMapper.updateEntity(dto, existing);
        existing.setContrat(findContrat(dto.contratId()));
        return paiementMapper.toDto(paiementRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO getPaiementById(Long id) {
        return paiementMapper.toDto(findPaiement(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> getAllPaiements() {
        return paiementRepository.findAll().stream().map(paiementMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deletePaiement(Long id) {
        paiementRepository.delete(findPaiement(id));
    }

    private Paiement findPaiement(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paiement introuvable avec l'id : " + id));
    }

    private Contrat findContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contrat introuvable avec l'id : " + id));
    }
}
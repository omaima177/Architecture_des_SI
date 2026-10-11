package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.domain.Paiement;
import tn.esprit.tic.ssa.autoloc1.repository.ContratRepository;
import tn.esprit.tic.ssa.autoloc1.repository.PaiementRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratRepository contratRepository;

    @Override
    @Transactional
    public Paiement addPaiement(Paiement paiement) {
        checkMontant(paiement);
        if (paiement.getModePaiement() == null) {
            throw new IllegalArgumentException("modePaiement est obligatoire");
        }
        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(LocalDate.now());
        }
        paiement.setContrat(findContrat(paiement.getContrat()));
        return paiementRepository.save(paiement);
    }

    @Override
    @Transactional
    public Paiement updatePaiement(Long id, Paiement paiement) {
        Paiement existing = getPaiementById(id);
        checkMontant(paiement);
        existing.setMontant(paiement.getMontant());
        if (paiement.getDatePaiement() != null) {
            existing.setDatePaiement(paiement.getDatePaiement());
        }
        if (paiement.getModePaiement() != null) {
            existing.setModePaiement(paiement.getModePaiement());
        }
        if (paiement.getContrat() != null) {
            existing.setContrat(findContrat(paiement.getContrat()));
        }
        return paiementRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Paiement getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paiement introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePaiement(Long id) {
        paiementRepository.delete(getPaiementById(id));
    }

    private void checkMontant(Paiement p) {
        if (p.getMontant() == null || p.getMontant().signum() <= 0) {
            throw new IllegalArgumentException("montant doit être strictement positif");
        }
    }

    private Contrat findContrat(Contrat ref) {
        if (ref == null || ref.getIdContrat() == null) {
            throw new IllegalArgumentException("contratId est obligatoire");
        }
        return contratRepository.findById(ref.getIdContrat())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contrat introuvable avec l'id : " + ref.getIdContrat()));
    }
}
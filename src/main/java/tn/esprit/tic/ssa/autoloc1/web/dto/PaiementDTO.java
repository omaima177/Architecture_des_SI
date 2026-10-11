package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.domain.ModePaiement;
import tn.esprit.tic.ssa.autoloc1.domain.Paiement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementDTO(
        Long idPaiement,
        BigDecimal montant,
        LocalDate datePaiement,
        ModePaiement modePaiement,
        Long contratId) {

    public static PaiementDTO from(Paiement p) {
        Long contratId = p.getContrat() != null ? p.getContrat().getIdContrat() : null;
        return new PaiementDTO(p.getIdPaiement(), p.getMontant(), p.getDatePaiement(),
                p.getModePaiement(), contratId);
    }

    public Paiement toEntity() {
        Paiement p = new Paiement();
        p.setMontant(montant);
        p.setDatePaiement(datePaiement);
        p.setModePaiement(modePaiement);
        if (contratId != null) {
            Contrat c = new Contrat();
            c.setIdContrat(contratId);
            p.setContrat(c);
        }
        return p;
    }
}
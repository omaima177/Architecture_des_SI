package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratDTO(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        boolean valide,
        Long reservationId) {

    public static ContratDTO from(Contrat c) {
        Long reservationId = c.getReservation() != null ? c.getReservation().getIdReservation() : null;
        return new ContratDTO(c.getIdContrat(), c.getDateSignature(), c.getMontantTotal(),
                c.isValide(), reservationId);
    }

    public Contrat toEntity() {
        Contrat c = new Contrat();
        c.setDateSignature(dateSignature);
        c.setMontantTotal(montantTotal);
        c.setValide(valide);
        if (reservationId != null) {
            Reservation r = new Reservation();
            r.setIdReservation(reservationId);
            c.setReservation(r);
        }
        return c;
    }
}
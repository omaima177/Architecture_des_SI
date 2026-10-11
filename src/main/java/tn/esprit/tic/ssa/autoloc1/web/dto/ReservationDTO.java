package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.domain.StatutReservation;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;

import java.time.LocalDate;

public record ReservationDTO(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long clientId,
        Long vehiculeId) {

    public static ReservationDTO from(Reservation r) {
        Long clientId = r.getClient() != null ? r.getClient().getIdClient() : null;
        Long vehiculeId = r.getVehicule() != null ? r.getVehicule().getIdVehicule() : null;
        return new ReservationDTO(r.getIdReservation(), r.getDateDebut(), r.getDateFin(),
                r.getStatut(), clientId, vehiculeId);
    }

    public Reservation toEntity() {
        Reservation r = new Reservation();
        r.setDateDebut(dateDebut);
        r.setDateFin(dateFin);
        r.setStatut(statut);
        if (clientId != null) {
            Client c = new Client();
            c.setIdClient(clientId);
            r.setClient(c);
        }
        if (vehiculeId != null) {
            Vehicule v = new Vehicule();
            v.setIdVehicule(vehiculeId);
            r.setVehicule(v);
        }
        return r;
    }
}
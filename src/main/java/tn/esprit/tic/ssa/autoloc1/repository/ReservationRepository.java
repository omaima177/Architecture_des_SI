package tn.esprit.tic.ssa.autoloc1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.domain.StatutReservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatut(StatutReservation statut);
}
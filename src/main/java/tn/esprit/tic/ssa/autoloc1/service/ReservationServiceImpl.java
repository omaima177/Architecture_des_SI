package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.domain.StatutReservation;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.repository.ClientRepository;
import tn.esprit.tic.ssa.autoloc1.repository.ReservationRepository;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Reservation addReservation(Reservation reservation) {
        checkDates(reservation);
        if (reservation.getStatut() == null) {
            reservation.setStatut(StatutReservation.EN_ATTENTE);
        }
        reservation.setClient(findClient(reservation.getClient()));
        reservation.setVehicule(findVehicule(reservation.getVehicule()));
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existing = getReservationById(id);
        checkDates(reservation);
        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        if (reservation.getStatut() != null) {
            existing.setStatut(reservation.getStatut());
        }
        if (reservation.getClient() != null) {
            existing.setClient(findClient(reservation.getClient()));
        }
        if (reservation.getVehicule() != null) {
            existing.setVehicule(findVehicule(reservation.getVehicule()));
        }
        return reservationRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Réservation introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation existing = getReservationById(id);
        if (existing.getContrat() != null) {
            throw new IllegalStateException(
                    "Impossible de supprimer cette réservation : un contrat y est rattaché.");
        }
        reservationRepository.delete(existing);
    }

    private void checkDates(Reservation r) {
        if (r.getDateDebut() == null || r.getDateFin() == null) {
            throw new IllegalArgumentException("dateDebut et dateFin sont obligatoires");
        }
        if (r.getDateFin().isBefore(r.getDateDebut())) {
            throw new IllegalArgumentException("dateFin ne peut pas précéder dateDebut");
        }
    }

    private Client findClient(Client ref) {
        if (ref == null || ref.getIdClient() == null) {
            throw new IllegalArgumentException("clientId est obligatoire");
        }
        return clientRepository.findById(ref.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client introuvable avec l'id : " + ref.getIdClient()));
    }

    private Vehicule findVehicule(Vehicule ref) {
        if (ref == null || ref.getIdVehicule() == null) {
            throw new IllegalArgumentException("vehiculeId est obligatoire");
        }
        return vehiculeRepository.findById(ref.getIdVehicule())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Véhicule introuvable avec l'id : " + ref.getIdVehicule()));
    }
}
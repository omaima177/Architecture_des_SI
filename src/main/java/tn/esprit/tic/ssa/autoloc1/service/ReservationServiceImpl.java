package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.domain.StatutReservation;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.mapper.ReservationMapper;
import tn.esprit.tic.ssa.autoloc1.repository.ClientRepository;
import tn.esprit.tic.ssa.autoloc1.repository.ReservationRepository;
import tn.esprit.tic.ssa.autoloc1.repository.VehiculeRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.ReservationDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationDTO addReservation(ReservationDTO dto) {
        checkDates(dto.dateDebut(), dto.dateFin());
        Reservation reservation = reservationMapper.toEntity(dto);
        if (reservation.getStatut() == null) {
            reservation.setStatut(StatutReservation.EN_ATTENTE);
        }
        reservation.setClient(findClient(dto.clientId()));
        reservation.setVehicule(findVehicule(dto.vehiculeId()));
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDTO updateReservation(Long id, ReservationDTO dto) {
        Reservation existing = findReservation(id);
        checkDates(dto.dateDebut(), dto.dateFin());
        reservationMapper.updateEntity(dto, existing);
        existing.setClient(findClient(dto.clientId()));
        existing.setVehicule(findVehicule(dto.vehiculeId()));
        return reservationMapper.toDto(reservationRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        return reservationMapper.toDto(findReservation(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(reservationMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation existing = findReservation(id);
        if (existing.getContrat() != null) {
            throw new IllegalStateException(
                    "Impossible de supprimer cette réservation : un contrat y est rattaché.");
        }
        reservationRepository.delete(existing);
    }

    private void checkDates(LocalDate debut, LocalDate fin) {
        if (fin.isBefore(debut)) {
            throw new IllegalArgumentException("dateFin ne peut pas précéder dateDebut");
        }
    }

    private Reservation findReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Réservation introuvable avec l'id : " + id));
    }

    private Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client introuvable avec l'id : " + id));
    }

    private Vehicule findVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Véhicule introuvable avec l'id : " + id));
    }
}
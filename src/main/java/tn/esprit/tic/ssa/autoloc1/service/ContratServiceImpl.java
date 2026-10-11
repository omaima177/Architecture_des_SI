package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.repository.ContratRepository;
import tn.esprit.tic.ssa.autoloc1.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public Contrat addContrat(Contrat contrat) {
        if (contrat.getMontantTotal() == null) {
            throw new IllegalArgumentException("montantTotal est obligatoire");
        }
        Reservation reservation = findReservation(contrat.getReservation());
        if (contratRepository.existsByReservation_IdReservation(reservation.getIdReservation())) {
            throw new IllegalArgumentException(
                    "Cette réservation possède déjà un contrat : " + reservation.getIdReservation());
        }
        contrat.setReservation(reservation);
        if (contrat.getDateSignature() == null) {
            contrat.setDateSignature(LocalDate.now());
        }
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional
    public Contrat updateContrat(Long id, Contrat contrat) {
        Contrat existing = getContratById(id);
        // La réservation d'un contrat n'est pas modifiable (relation @OneToOne unique)
        if (contrat.getDateSignature() != null) {
            existing.setDateSignature(contrat.getDateSignature());
        }
        if (contrat.getMontantTotal() != null) {
            existing.setMontantTotal(contrat.getMontantTotal());
        }
        existing.setValide(contrat.isValide());
        return contratRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat getContratById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contrat introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteContrat(Long id) {
        // cascade = ALL : les paiements du contrat sont supprimés avec lui
        contratRepository.delete(getContratById(id));
    }

    private Reservation findReservation(Reservation ref) {
        if (ref == null || ref.getIdReservation() == null) {
            throw new IllegalArgumentException("reservationId est obligatoire");
        }
        return reservationRepository.findById(ref.getIdReservation())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Réservation introuvable avec l'id : " + ref.getIdReservation()));
    }
}
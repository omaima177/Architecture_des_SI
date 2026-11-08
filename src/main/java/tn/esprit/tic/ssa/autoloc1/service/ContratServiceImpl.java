package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.mapper.ContratMapper;
import tn.esprit.tic.ssa.autoloc1.repository.ContratRepository;
import tn.esprit.tic.ssa.autoloc1.repository.ReservationRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.ContratDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    @Transactional
    public ContratDTO addContrat(ContratDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.reservationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Réservation introuvable avec l'id : " + dto.reservationId()));
        if (contratRepository.existsByReservation_IdReservation(reservation.getIdReservation())) {
            throw new IllegalArgumentException(
                    "Cette réservation possède déjà un contrat : " + reservation.getIdReservation());
        }
        Contrat contrat = contratMapper.toEntity(dto);
        contrat.setReservation(reservation);
        if (contrat.getDateSignature() == null) {
            contrat.setDateSignature(LocalDate.now());
        }
        return contratMapper.toDto(contratRepository.save(contrat));
    }

    @Override
    @Transactional
    public ContratDTO updateContrat(Long id, ContratDTO dto) {
        Contrat existing = findContrat(id);
        // La réservation d'un contrat n'est pas modifiable (relation @OneToOne unique)
        contratMapper.updateEntity(dto, existing);
        return contratMapper.toDto(contratRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public ContratDTO getContratById(Long id) {
        return contratMapper.toDto(findContrat(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> getAllContrats() {
        return contratRepository.findAll().stream().map(contratMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteContrat(Long id) {
        // cascade = ALL : les paiements du contrat sont supprimés avec lui
        contratRepository.delete(findContrat(id));
    }

    private Contrat findContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contrat introuvable avec l'id : " + id));
    }
}
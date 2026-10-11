package tn.esprit.tic.ssa.autoloc1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByValide(boolean valide);
    boolean existsByReservation_IdReservation(Long idReservation);
}
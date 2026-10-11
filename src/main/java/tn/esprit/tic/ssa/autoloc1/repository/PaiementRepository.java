package tn.esprit.tic.ssa.autoloc1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.ssa.autoloc1.domain.ModePaiement;
import tn.esprit.tic.ssa.autoloc1.domain.Paiement;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByModePaiement(ModePaiement modePaiement);
}
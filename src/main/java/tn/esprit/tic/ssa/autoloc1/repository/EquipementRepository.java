package tn.esprit.tic.ssa.autoloc1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {
}
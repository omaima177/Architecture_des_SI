package tn.esprit.tic.ssa.autoloc1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;

import java.util.List;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
    List<Agence> findByVille(String ville);
}
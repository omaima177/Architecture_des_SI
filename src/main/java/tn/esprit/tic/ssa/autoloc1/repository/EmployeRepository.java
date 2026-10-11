package tn.esprit.tic.ssa.autoloc1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.ssa.autoloc1.domain.Employe;
import tn.esprit.tic.ssa.autoloc1.domain.RoleEmploye;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByRole(RoleEmploye role);
}
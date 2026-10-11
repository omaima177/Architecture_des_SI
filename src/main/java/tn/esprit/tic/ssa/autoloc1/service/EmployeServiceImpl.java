package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.domain.Employe;
import tn.esprit.tic.ssa.autoloc1.domain.RoleEmploye;
import tn.esprit.tic.ssa.autoloc1.repository.AgenceRepository;
import tn.esprit.tic.ssa.autoloc1.repository.EmployeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;

    @Override
    @Transactional
    public Employe addEmploye(Employe employe) {
        employe.setAgence(findAgence(employe.getAgence()));
        if (employe.getRole() == null) {
            employe.setRole(RoleEmploye.AGENT);
        }
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe updateEmploye(Long id, Employe employe) {
        Employe existing = getEmployeById(id);
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        if (employe.getRole() != null) {
            existing.setRole(employe.getRole());
        }
        if (employe.getAgence() != null) {
            existing.setAgence(findAgence(employe.getAgence()));
        }
        return employeRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employé introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEmploye(Long id) {
        employeRepository.delete(getEmployeById(id));
    }

    private Agence findAgence(Agence ref) {
        if (ref == null || ref.getIdAgence() == null) {
            throw new IllegalArgumentException("agenceId est obligatoire");
        }
        return agenceRepository.findById(ref.getIdAgence())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agence introuvable avec l'id : " + ref.getIdAgence()));
    }
}
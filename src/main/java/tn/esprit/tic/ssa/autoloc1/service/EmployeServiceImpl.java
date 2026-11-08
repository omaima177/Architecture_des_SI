package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.domain.Employe;
import tn.esprit.tic.ssa.autoloc1.domain.RoleEmploye;
import tn.esprit.tic.ssa.autoloc1.mapper.EmployeMapper;
import tn.esprit.tic.ssa.autoloc1.repository.AgenceRepository;
import tn.esprit.tic.ssa.autoloc1.repository.EmployeRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.EmployeDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    @Transactional
    public EmployeDTO addEmploye(EmployeDTO dto) {
        Employe employe = employeMapper.toEntity(dto);
        employe.setAgence(findAgence(dto.agenceId()));
        if (employe.getRole() == null) {
            employe.setRole(RoleEmploye.AGENT);
        }
        return employeMapper.toDto(employeRepository.save(employe));
    }

    @Override
    @Transactional
    public EmployeDTO updateEmploye(Long id, EmployeDTO dto) {
        Employe existing = findEmploye(id);
        employeMapper.updateEntity(dto, existing);
        existing.setAgence(findAgence(dto.agenceId()));
        return employeMapper.toDto(employeRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeDTO getEmployeById(Long id) {
        return employeMapper.toDto(findEmploye(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> getAllEmployes() {
        return employeRepository.findAll().stream().map(employeMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteEmploye(Long id) {
        employeRepository.delete(findEmploye(id));
    }

    private Employe findEmploye(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employé introuvable avec l'id : " + id));
    }

    private Agence findAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Agence introuvable avec l'id : " + id));
    }
}
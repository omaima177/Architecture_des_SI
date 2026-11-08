// IEmployeService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.EmployeDTO;

import java.util.List;

public interface IEmployeService {
    EmployeDTO addEmploye(EmployeDTO dto);
    EmployeDTO updateEmploye(Long id, EmployeDTO dto);
    EmployeDTO getEmployeById(Long id);
    List<EmployeDTO> getAllEmployes();
    void deleteEmploye(Long id);
}
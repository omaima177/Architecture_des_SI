package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Employe;
import tn.esprit.tic.ssa.autoloc1.service.IEmployeService;
import tn.esprit.tic.ssa.autoloc1.web.dto.EmployeDTO;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@AllArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeDTO> addEmploye(@RequestBody EmployeDTO dto) {
        Employe saved = employeService.addEmploye(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeDTO.from(saved));
    }

    @GetMapping
    public List<EmployeDTO> getAllEmployes() {
        return employeService.getAllEmployes().stream().map(EmployeDTO::from).toList();
    }

    @GetMapping("/{id}")
    public EmployeDTO getEmployeById(@PathVariable("id") Long id) {
        return EmployeDTO.from(employeService.getEmployeById(id));
    }

    @PutMapping("/{id}")
    public EmployeDTO updateEmploye(@PathVariable("id") Long id, @RequestBody EmployeDTO dto) {
        return EmployeDTO.from(employeService.updateEmploye(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable("id") Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.noContent().build();
    }
}
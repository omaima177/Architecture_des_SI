package tn.esprit.tic.ssa.autoloc1.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tic.ssa.autoloc1.service.IEmployeService;
import tn.esprit.tic.ssa.autoloc1.web.dto.EmployeDTO;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@AllArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeDTO> addEmploye(@Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.addEmploye(dto));
    }

    @GetMapping
    public List<EmployeDTO> getAllEmployes() {
        return employeService.getAllEmployes();
    }

    @GetMapping("/{id}")
    public EmployeDTO getEmployeById(@PathVariable("id") Long id) {
        return employeService.getEmployeById(id);
    }

    @PutMapping("/{id}")
    public EmployeDTO updateEmploye(@PathVariable("id") Long id, @Valid @RequestBody EmployeDTO dto) {
        return employeService.updateEmploye(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable("id") Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.noContent().build();
    }
}
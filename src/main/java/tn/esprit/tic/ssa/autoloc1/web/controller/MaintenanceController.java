package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Maintenance;
import tn.esprit.tic.ssa.autoloc1.service.IMaintenanceService;
import tn.esprit.tic.ssa.autoloc1.web.dto.MaintenanceDTO;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@AllArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceDTO> addMaintenance(@RequestBody MaintenanceDTO dto) {
        Maintenance saved = maintenanceService.addMaintenance(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(MaintenanceDTO.from(saved));
    }

    @GetMapping
    public List<MaintenanceDTO> getAllMaintenances() {
        return maintenanceService.getAllMaintenances().stream().map(MaintenanceDTO::from).toList();
    }

    @GetMapping("/{id}")
    public MaintenanceDTO getMaintenanceById(@PathVariable("id") Long id) {
        return MaintenanceDTO.from(maintenanceService.getMaintenanceById(id));
    }

    @PutMapping("/{id}")
    public MaintenanceDTO updateMaintenance(@PathVariable("id") Long id, @RequestBody MaintenanceDTO dto) {
        return MaintenanceDTO.from(maintenanceService.updateMaintenance(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable("id") Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
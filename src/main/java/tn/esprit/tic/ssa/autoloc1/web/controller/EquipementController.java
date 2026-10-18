package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.service.IEquipementService;
import tn.esprit.tic.ssa.autoloc1.web.dto.EquipementDTO;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@AllArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @PostMapping
    public ResponseEntity<EquipementDTO> addEquipement(@RequestBody EquipementDTO dto) {
        Equipement saved = equipementService.addEquipement(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipementDTO.from(saved));
    }

    @GetMapping
    public List<EquipementDTO> getAllEquipements() {
        return equipementService.getAllEquipements().stream().map(EquipementDTO::from).toList();
    }

    @GetMapping("/{id}")
    public EquipementDTO getEquipementById(@PathVariable("id") Long id) {
        return EquipementDTO.from(equipementService.getEquipementById(id));
    }

    @PutMapping("/{id}")
    public EquipementDTO updateEquipement(@PathVariable("id") Long id, @RequestBody EquipementDTO dto) {
        return EquipementDTO.from(equipementService.updateEquipement(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable("id") Long id) {
        equipementService.deleteEquipement(id);
        return ResponseEntity.noContent().build();
    }
}
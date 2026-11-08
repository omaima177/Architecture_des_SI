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
import tn.esprit.tic.ssa.autoloc1.service.IEquipementService;
import tn.esprit.tic.ssa.autoloc1.web.dto.EquipementDTO;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@AllArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @PostMapping
    public ResponseEntity<EquipementDTO> addEquipement(@Valid @RequestBody EquipementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipementService.addEquipement(dto));
    }

    @GetMapping
    public List<EquipementDTO> getAllEquipements() {
        return equipementService.getAllEquipements();
    }

    @GetMapping("/{id}")
    public EquipementDTO getEquipementById(@PathVariable("id") Long id) {
        return equipementService.getEquipementById(id);
    }

    @PutMapping("/{id}")
    public EquipementDTO updateEquipement(@PathVariable("id") Long id, @Valid @RequestBody EquipementDTO dto) {
        return equipementService.updateEquipement(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable("id") Long id) {
        equipementService.deleteEquipement(id);
        return ResponseEntity.noContent().build();
    }
}
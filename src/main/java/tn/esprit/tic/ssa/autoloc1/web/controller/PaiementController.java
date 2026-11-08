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
import tn.esprit.tic.ssa.autoloc1.service.IPaiementService;
import tn.esprit.tic.ssa.autoloc1.web.dto.PaiementDTO;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@AllArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @PostMapping
    public ResponseEntity<PaiementDTO> addPaiement(@Valid @RequestBody PaiementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.addPaiement(dto));
    }

    @GetMapping
    public List<PaiementDTO> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("/{id}")
    public PaiementDTO getPaiementById(@PathVariable("id") Long id) {
        return paiementService.getPaiementById(id);
    }

    @PutMapping("/{id}")
    public PaiementDTO updatePaiement(@PathVariable("id") Long id, @Valid @RequestBody PaiementDTO dto) {
        return paiementService.updatePaiement(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable("id") Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}
package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Paiement;
import tn.esprit.tic.ssa.autoloc1.service.IPaiementService;
import tn.esprit.tic.ssa.autoloc1.web.dto.PaiementDTO;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@AllArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @PostMapping
    public ResponseEntity<PaiementDTO> addPaiement(@RequestBody PaiementDTO dto) {
        Paiement saved = paiementService.addPaiement(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(PaiementDTO.from(saved));
    }

    @GetMapping
    public List<PaiementDTO> getAllPaiements() {
        return paiementService.getAllPaiements().stream().map(PaiementDTO::from).toList();
    }

    @GetMapping("/{id}")
    public PaiementDTO getPaiementById(@PathVariable("id") Long id) {
        return PaiementDTO.from(paiementService.getPaiementById(id));
    }

    @PutMapping("/{id}")
    public PaiementDTO updatePaiement(@PathVariable("id") Long id, @RequestBody PaiementDTO dto) {
        return PaiementDTO.from(paiementService.updatePaiement(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable("id") Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}
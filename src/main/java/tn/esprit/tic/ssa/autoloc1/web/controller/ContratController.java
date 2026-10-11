package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.service.IContratService;
import tn.esprit.tic.ssa.autoloc1.web.dto.ContratDTO;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@AllArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @PostMapping
    public ResponseEntity<ContratDTO> addContrat(@RequestBody ContratDTO dto) {
        Contrat saved = contratService.addContrat(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ContratDTO.from(saved));
    }

    @GetMapping
    public List<ContratDTO> getAllContrats() {
        return contratService.getAllContrats().stream().map(ContratDTO::from).toList();
    }

    @GetMapping("/{id}")
    public ContratDTO getContratById(@PathVariable("id") Long id) {
        return ContratDTO.from(contratService.getContratById(id));
    }

    @PutMapping("/{id}")
    public ContratDTO updateContrat(@PathVariable("id") Long id, @RequestBody ContratDTO dto) {
        return ContratDTO.from(contratService.updateContrat(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable("id") Long id) {
        contratService.deleteContrat(id);
        return ResponseEntity.noContent().build();
    }
}
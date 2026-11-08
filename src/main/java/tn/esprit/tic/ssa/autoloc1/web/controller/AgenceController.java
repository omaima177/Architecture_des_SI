package tn.esprit.tic.ssa.autoloc1.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import tn.esprit.tic.ssa.autoloc1.service.IAgenceService;
import tn.esprit.tic.ssa.autoloc1.web.dto.AgenceDTO;

import java.util.List;

@Tag(name = "Agences", description = "Gestion des agences de location")
@RestController
@RequestMapping("/api/agences")
@AllArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Créer une agence", description = "Crée une nouvelle agence de location.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides (erreur de validation)")
    })
    @PostMapping
    public ResponseEntity<AgenceDTO> addAgence(@Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.addAgence(dto));
    }

    @Operation(summary = "Lister les agences", description = "Retourne la liste de toutes les agences.")
    @ApiResponse(responseCode = "200", description = "Liste retournée (peut être vide)")
    @GetMapping
    public List<AgenceDTO> getAllAgences() {
        return agenceService.getAllAgences();
    }

    @Operation(summary = "Consulter une agence", description = "Retourne une agence à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @GetMapping("/{id}")
    public AgenceDTO getAgenceById(@PathVariable("id") Long id) {
        return agenceService.getAgenceById(id);
    }

    @Operation(summary = "Modifier une agence", description = "Remplace les informations d'une agence existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @PutMapping("/{id}")
    public AgenceDTO updateAgence(@PathVariable("id") Long id, @Valid @RequestBody AgenceDTO dto) {
        return agenceService.updateAgence(id, dto);
    }

    @Operation(summary = "Supprimer une agence", description = "Supprime une agence qui ne contient ni véhicule ni employé.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable"),
            @ApiResponse(responseCode = "409", description = "L'agence contient encore des véhicules ou des employés")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable("id") Long id) {
        agenceService.deleteAgence(id);
        return ResponseEntity.noContent().build();
    }
}
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
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.service.IVehiculeService;
import tn.esprit.tic.ssa.autoloc1.web.dto.VehiculeDTO;

import java.util.List;

@Tag(name = "Véhicules", description = "Gestion de la flotte de véhicules")
@RestController
@RequestMapping("/api/vehicules")
@AllArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Créer un véhicule",
            description = "Crée un véhicule rattaché à une agence existante (agenceId obligatoire). L'immatriculation doit être unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Véhicule créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou immatriculation déjà utilisée"),
            @ApiResponse(responseCode = "404", description = "Agence ou équipement introuvable")
    })
    @PostMapping
    public ResponseEntity<VehiculeDTO> addVehicule(@Valid @RequestBody VehiculeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.addVehicule(dto));
    }

    @Operation(summary = "Lister les véhicules", description = "Retourne la liste de tous les véhicules.")
    @ApiResponse(responseCode = "200", description = "Liste retournée (peut être vide)")
    @GetMapping
    public List<VehiculeDTO> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

    @Operation(summary = "Consulter un véhicule", description = "Retourne un véhicule à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeDTO getVehiculeById(@PathVariable("id") Long id) {
        return vehiculeService.getVehiculeById(id);
    }

    @Operation(summary = "Filtrer les véhicules par statut",
            description = "Statuts possibles : DISPONIBLE, LOUE, MAINTENANCE.")
    @ApiResponse(responseCode = "200", description = "Liste filtrée retournée")
    @GetMapping("/statut/{statut}")
    public List<VehiculeDTO> getVehiculesByStatut(@PathVariable("statut") StatutVehicule statut) {
        return vehiculeService.getVehiculesByStatut(statut);
    }

    @Operation(summary = "Modifier un véhicule", description = "Remplace les informations d'un véhicule existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule modifié"),
            @ApiResponse(responseCode = "400", description = "Immatriculation déjà utilisée"),
            @ApiResponse(responseCode = "404", description = "Véhicule, agence ou équipement introuvable")
    })
    @PutMapping("/{id}")
    public VehiculeDTO updateVehicule(@PathVariable("id") Long id, @Valid @RequestBody VehiculeDTO dto) {
        return vehiculeService.updateVehicule(id, dto);
    }

    @Operation(summary = "Supprimer un véhicule", description = "Supprime un véhicule sans réservation ni maintenance.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Véhicule supprimé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable"),
            @ApiResponse(responseCode = "409", description = "Le véhicule a des réservations ou des maintenances")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable("id") Long id) {
        vehiculeService.deleteVehicule(id);
        return ResponseEntity.noContent().build();
    }
}
package tn.esprit.tic.ssa.autoloc1.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
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
            @ApiResponse(responseCode = "400", description = "Immatriculation déjà utilisée ou agenceId manquant"),
            @ApiResponse(responseCode = "404", description = "Agence ou équipement introuvable")
    })
    @PostMapping
    public ResponseEntity<VehiculeDTO> addVehicule(@RequestBody VehiculeDTO dto) {
        Vehicule saved = vehiculeService.addVehicule(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(VehiculeDTO.from(saved));
    }

    @Operation(summary = "Lister les véhicules", description = "Retourne la liste de tous les véhicules.")
    @ApiResponse(responseCode = "200", description = "Liste retournée (peut être vide)")
    @GetMapping
    public List<VehiculeDTO> getAllVehicules() {
        return vehiculeService.getAllVehicules().stream().map(VehiculeDTO::from).toList();
    }

    @Operation(summary = "Consulter un véhicule", description = "Retourne un véhicule à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeDTO getVehiculeById(@PathVariable("id") Long id) {
        return VehiculeDTO.from(vehiculeService.getVehiculeById(id));
    }

    @Operation(summary = "Filtrer les véhicules par statut",
            description = "Statuts possibles : DISPONIBLE, LOUE, MAINTENANCE.")
    @ApiResponse(responseCode = "200", description = "Liste filtrée retournée")
    @GetMapping("/statut/{statut}")
    public List<VehiculeDTO> getVehiculesByStatut(@PathVariable("statut") StatutVehicule statut) {
        return vehiculeService.getVehiculesByStatut(statut).stream().map(VehiculeDTO::from).toList();
    }

    @Operation(summary = "Modifier un véhicule", description = "Remplace les informations d'un véhicule existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule modifié"),
            @ApiResponse(responseCode = "400", description = "Immatriculation déjà utilisée"),
            @ApiResponse(responseCode = "404", description = "Véhicule, agence ou équipement introuvable")
    })
    @PutMapping("/{id}")
    public VehiculeDTO updateVehicule(@PathVariable("id") Long id, @RequestBody VehiculeDTO dto) {
        return VehiculeDTO.from(vehiculeService.updateVehicule(id, dto.toEntity()));
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
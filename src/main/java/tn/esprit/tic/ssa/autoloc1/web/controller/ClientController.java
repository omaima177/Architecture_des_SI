package tn.esprit.tic.ssa.autoloc1.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.service.IClientService;
import tn.esprit.tic.ssa.autoloc1.web.dto.ClientDTO;

import java.util.List;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Créer un client", description = "Crée un client. L'email doit être unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé"),
            @ApiResponse(responseCode = "400", description = "Email déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO dto) {
        Client saved = clientService.addClient(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientDTO.from(saved));
    }

    @Operation(summary = "Lister les clients", description = "Retourne la liste de tous les clients.")
    @ApiResponse(responseCode = "200", description = "Liste retournée (peut être vide)")
    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients().stream().map(ClientDTO::from).toList();
    }

    @Operation(summary = "Consulter un client", description = "Retourne un client à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable("id") Long id) {
        return ClientDTO.from(clientService.getClientById(id));
    }

    @Operation(summary = "Modifier un client", description = "Remplace les informations d'un client existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client modifié"),
            @ApiResponse(responseCode = "400", description = "Email déjà utilisé par un autre client"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable("id") Long id, @RequestBody ClientDTO dto) {
        return ClientDTO.from(clientService.updateClient(id, dto.toEntity()));
    }

    @Operation(summary = "Supprimer un client", description = "Supprime un client qui n'a aucune réservation.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable"),
            @ApiResponse(responseCode = "409", description = "Le client possède des réservations")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
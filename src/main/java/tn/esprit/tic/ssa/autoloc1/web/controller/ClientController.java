package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.service.IClientService;
import tn.esprit.tic.ssa.autoloc1.web.dto.ClientDTO;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO dto) {
        Client saved = clientService.addClient(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientDTO.from(saved));
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients().stream().map(ClientDTO::from).toList();
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable("id") Long id) {
        return ClientDTO.from(clientService.getClientById(id));
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable("id") Long id, @RequestBody ClientDTO dto) {
        return ClientDTO.from(clientService.updateClient(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
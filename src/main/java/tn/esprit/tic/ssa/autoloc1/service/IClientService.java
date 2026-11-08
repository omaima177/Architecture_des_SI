// IClientService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.ClientDTO;

import java.util.List;

public interface IClientService {
    ClientDTO addClient(ClientDTO dto);
    ClientDTO updateClient(Long id, ClientDTO dto);
    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
    void deleteClient(Long id);
}
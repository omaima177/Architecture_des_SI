package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Client;

import java.util.List;

public interface IClientService {
    Client addClient(Client client);
    Client updateClient(Long id, Client client);
    Client getClientById(Long id);
    List<Client> getAllClients();
    void deleteClient(Long id);
}
package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Client addClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException(
                    "Un client avec cet email existe déjà : " + client.getEmail());
        }
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {
        Client existing = getClientById(id);
        if (client.getEmail() != null
                && !client.getEmail().equals(existing.getEmail())
                && clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException(
                    "Un client avec cet email existe déjà : " + client.getEmail());
        }
        existing.setNom(client.getNom());
        existing.setPrenom(client.getPrenom());
        existing.setEmail(client.getEmail());
        existing.setTelephone(client.getTelephone());
        existing.setNumPermis(client.getNumPermis());
        return clientRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client introuvable avec l'id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Client existing = getClientById(id);
        if (!existing.getReservations().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce client : il possède des réservations.");
        }
        clientRepository.delete(existing);
    }
}
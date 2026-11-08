package tn.esprit.tic.ssa.autoloc1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.mapper.ClientMapper;
import tn.esprit.tic.ssa.autoloc1.repository.ClientRepository;
import tn.esprit.tic.ssa.autoloc1.web.dto.ClientDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {

    private static final String EMAIL_EXISTS = "Un client avec cet email existe déjà : ";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDTO addClient(ClientDTO dto) {
        if (clientRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException(EMAIL_EXISTS + dto.email());
        }
        Client client = clientMapper.toEntity(dto);
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO dto) {
        Client existing = findClient(id);
        if (!dto.email().equals(existing.getEmail())
                && clientRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException(EMAIL_EXISTS + dto.email());
        }
        clientMapper.updateEntity(dto, existing);
        return clientMapper.toDto(clientRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO getClientById(Long id) {
        return clientMapper.toDto(findClient(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(clientMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Client existing = findClient(id);
        if (!existing.getReservations().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce client : il possède des réservations.");
        }
        clientRepository.delete(existing);
    }

    private Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client introuvable avec l'id : " + id));
    }
}
package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Client;

import java.time.LocalDate;

public record ClientDTO(
        Long idClient,
        String nom,
        String prenom,
        String email,
        String telephone,
        String numPermis,
        LocalDate dateInscription) {

    public static ClientDTO from(Client c) {
        return new ClientDTO(c.getIdClient(), c.getNom(), c.getPrenom(), c.getEmail(),
                c.getTelephone(), c.getNumPermis(), c.getDateInscription());
    }

    public Client toEntity() {
        Client c = new Client();
        c.setNom(nom);
        c.setPrenom(prenom);
        c.setEmail(email);
        c.setTelephone(telephone);
        c.setNumPermis(numPermis);
        c.setDateInscription(dateInscription);
        return c;
    }
}
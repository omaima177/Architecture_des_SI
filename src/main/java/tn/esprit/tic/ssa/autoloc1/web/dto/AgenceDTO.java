package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Agence;

public record AgenceDTO(
        Long idAgence,
        String nom,
        String ville,
        String adresse,
        String telephone) {

    public static AgenceDTO from(Agence a) {
        return new AgenceDTO(a.getIdAgence(), a.getNom(), a.getVille(),
                a.getAdresse(), a.getTelephone());
    }

    public Agence toEntity() {
        Agence a = new Agence();
        a.setNom(nom);
        a.setVille(ville);
        a.setAdresse(adresse);
        a.setTelephone(telephone);
        return a;
    }
}
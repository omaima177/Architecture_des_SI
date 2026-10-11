package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.domain.Employe;
import tn.esprit.tic.ssa.autoloc1.domain.RoleEmploye;

public record EmployeDTO(
        Long idEmploye,
        String nom,
        String prenom,
        RoleEmploye role,
        Long agenceId) {

    public static EmployeDTO from(Employe e) {
        Long agenceId = e.getAgence() != null ? e.getAgence().getIdAgence() : null;
        return new EmployeDTO(e.getIdEmploye(), e.getNom(), e.getPrenom(), e.getRole(), agenceId);
    }

    public Employe toEntity() {
        Employe e = new Employe();
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setRole(role);
        if (agenceId != null) {
            Agence a = new Agence();
            a.setIdAgence(agenceId);
            e.setAgence(a);
        }
        return e;
    }
}
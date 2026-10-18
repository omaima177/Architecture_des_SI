package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Equipement;

public record EquipementDTO(
        Long idEquipement,
        String libelle) {

    public static EquipementDTO from(Equipement e) {
        return new EquipementDTO(e.getIdEquipement(), e.getLibelle());
    }

    public Equipement toEntity() {
        Equipement e = new Equipement();
        e.setLibelle(libelle);
        return e;
    }
}
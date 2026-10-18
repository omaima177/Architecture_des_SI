package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Agence;
import tn.esprit.tic.ssa.autoloc1.domain.CategorieVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;

import java.math.BigDecimal;
import java.util.List;

public record VehiculeDTO(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long agenceId,
        List<Long> equipementIds) {

    public static VehiculeDTO from(Vehicule v) {
        Long agenceId = v.getAgence() != null ? v.getAgence().getIdAgence() : null;
        List<Long> equipementIds = v.getEquipements().stream()
                .map(Equipement::getIdEquipement)
                .toList();
        return new VehiculeDTO(v.getIdVehicule(), v.getImmatriculation(), v.getMarque(),
                v.getModele(), v.getCategorie(), v.getTarifJournalier(), v.getStatut(),
                agenceId, equipementIds);
    }

    public Vehicule toEntity() {
        Vehicule v = new Vehicule();
        v.setImmatriculation(immatriculation);
        v.setMarque(marque);
        v.setModele(modele);
        v.setCategorie(categorie);
        v.setTarifJournalier(tarifJournalier);
        v.setStatut(statut);
        if (agenceId != null) {
            Agence a = new Agence();
            a.setIdAgence(agenceId);
            v.setAgence(a);
        }
        if (equipementIds != null) {
            for (Long id : equipementIds) {
                Equipement e = new Equipement();
                e.setIdEquipement(id);
                v.getEquipements().add(e);
            }
        }
        return v;
    }
}
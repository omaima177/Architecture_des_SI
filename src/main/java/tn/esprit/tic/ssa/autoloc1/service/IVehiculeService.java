package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {
    Vehicule addVehicule(Vehicule vehicule);
    Vehicule updateVehicule(Long id, Vehicule vehicule);
    Vehicule getVehiculeById(Long id);
    List<Vehicule> getAllVehicules();
    List<Vehicule> getVehiculesByStatut(StatutVehicule statut);
    void deleteVehicule(Long id);
}
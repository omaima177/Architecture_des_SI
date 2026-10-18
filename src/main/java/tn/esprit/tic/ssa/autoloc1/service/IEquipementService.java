package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Equipement;

import java.util.List;

public interface IEquipementService {
    Equipement addEquipement(Equipement equipement);
    Equipement updateEquipement(Long id, Equipement equipement);
    Equipement getEquipementById(Long id);
    List<Equipement> getAllEquipements();
    void deleteEquipement(Long id);
}
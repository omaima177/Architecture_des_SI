package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Agence;

import java.util.List;

public interface IAgenceService {
    Agence addAgence(Agence agence);
    Agence updateAgence(Long id, Agence agence);
    Agence getAgenceById(Long id);
    List<Agence> getAllAgences();
    void deleteAgence(Long id);
}
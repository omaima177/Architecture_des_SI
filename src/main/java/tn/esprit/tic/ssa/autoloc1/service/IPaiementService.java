package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Paiement;

import java.util.List;

public interface IPaiementService {
    Paiement addPaiement(Paiement paiement);
    Paiement updatePaiement(Long id, Paiement paiement);
    Paiement getPaiementById(Long id);
    List<Paiement> getAllPaiements();
    void deletePaiement(Long id);
}
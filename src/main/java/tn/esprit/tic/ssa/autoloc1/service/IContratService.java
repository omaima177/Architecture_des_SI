package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Contrat;

import java.util.List;

public interface IContratService {
    Contrat addContrat(Contrat contrat);
    Contrat updateContrat(Long id, Contrat contrat);
    Contrat getContratById(Long id);
    List<Contrat> getAllContrats();
    void deleteContrat(Long id);
}
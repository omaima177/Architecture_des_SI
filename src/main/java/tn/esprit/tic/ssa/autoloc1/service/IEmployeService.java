package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Employe;

import java.util.List;

public interface IEmployeService {
    Employe addEmploye(Employe employe);
    Employe updateEmploye(Long id, Employe employe);
    Employe getEmployeById(Long id);
    List<Employe> getAllEmployes();
    void deleteEmploye(Long id);
}
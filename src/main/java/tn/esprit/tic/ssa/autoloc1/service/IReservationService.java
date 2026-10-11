package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.domain.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Long id, Reservation reservation);
    Reservation getReservationById(Long id);
    List<Reservation> getAllReservations();
    void deleteReservation(Long id);
}
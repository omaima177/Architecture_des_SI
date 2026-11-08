// IReservationService.java
package tn.esprit.tic.ssa.autoloc1.service;

import tn.esprit.tic.ssa.autoloc1.web.dto.ReservationDTO;

import java.util.List;

public interface IReservationService {
    ReservationDTO addReservation(ReservationDTO dto);
    ReservationDTO updateReservation(Long id, ReservationDTO dto);
    ReservationDTO getReservationById(Long id);
    List<ReservationDTO> getAllReservations();
    void deleteReservation(Long id);
}
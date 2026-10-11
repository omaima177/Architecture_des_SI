package tn.esprit.tic.ssa.autoloc1.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.ssa.autoloc1.domain.Reservation;
import tn.esprit.tic.ssa.autoloc1.service.IReservationService;
import tn.esprit.tic.ssa.autoloc1.web.dto.ReservationDTO;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO dto) {
        Reservation saved = reservationService.addReservation(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationDTO.from(saved));
    }

    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAllReservations().stream().map(ReservationDTO::from).toList();
    }

    @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable("id") Long id) {
        return ReservationDTO.from(reservationService.getReservationById(id));
    }

    @PutMapping("/{id}")
    public ReservationDTO updateReservation(@PathVariable("id") Long id, @RequestBody ReservationDTO dto) {
        return ReservationDTO.from(reservationService.updateReservation(id, dto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
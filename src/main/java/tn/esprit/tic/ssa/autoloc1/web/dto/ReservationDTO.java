package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.tic.ssa.autoloc1.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idReservation,
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,
        StatutReservation statut,
        @NotNull(message = "clientId est obligatoire")
        @Positive(message = "clientId doit être un identifiant positif")
        Long clientId,
        @NotNull(message = "vehiculeId est obligatoire")
        @Positive(message = "vehiculeId doit être un identifiant positif")
        Long vehiculeId) {
}
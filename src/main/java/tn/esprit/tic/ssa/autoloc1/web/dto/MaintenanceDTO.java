package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MaintenanceDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idMaintenance,
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,
        @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
        String description,
        @NotNull(message = "vehiculeId est obligatoire")
        @Positive(message = "vehiculeId doit être un identifiant positif")
        Long vehiculeId) {
}
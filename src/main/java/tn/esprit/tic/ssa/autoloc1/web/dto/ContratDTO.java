package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idContrat,
        LocalDate dateSignature,
        @NotNull(message = "Le montant total est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant total doit être strictement positif")
        @Digits(integer = 8, fraction = 2, message = "Le montant total accepte 8 chiffres entiers et 2 décimales au maximum")
        BigDecimal montantTotal,
        boolean valide,
        @NotNull(message = "reservationId est obligatoire")
        @Positive(message = "reservationId doit être un identifiant positif")
        Long reservationId) {
}
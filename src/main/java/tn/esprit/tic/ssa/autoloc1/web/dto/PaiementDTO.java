package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import tn.esprit.tic.ssa.autoloc1.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idPaiement,
        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement positif")
        @Digits(integer = 8, fraction = 2, message = "Le montant accepte 8 chiffres entiers et 2 décimales au maximum")
        BigDecimal montant,
        @PastOrPresent(message = "La date de paiement ne peut pas être dans le futur")
        LocalDate datePaiement,
        @NotNull(message = "Le mode de paiement est obligatoire (CARTE, ESPECES, VIREMENT)")
        ModePaiement modePaiement,
        @NotNull(message = "contratId est obligatoire")
        @Positive(message = "contratId doit être un identifiant positif")
        Long contratId) {
}
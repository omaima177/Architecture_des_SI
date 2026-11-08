package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.tic.ssa.autoloc1.domain.CategorieVehicule;
import tn.esprit.tic.ssa.autoloc1.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.List;

public record VehiculeDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idVehicule,
        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
        String immatriculation,
        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
        String marque,
        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
        String modele,
        @NotNull(message = "La catégorie est obligatoire (CITADINE, BERLINE, SUV, UTILITAIRE)")
        CategorieVehicule categorie,
        @NotNull(message = "Le tarif journalier est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le tarif journalier doit être strictement positif")
        @Digits(integer = 8, fraction = 2, message = "Le tarif journalier accepte 8 chiffres entiers et 2 décimales au maximum")
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        @NotNull(message = "agenceId est obligatoire")
        @Positive(message = "agenceId doit être un identifiant positif")
        Long agenceId,
        List<@NotNull(message = "Un identifiant d'équipement ne peut pas être nul") Long> equipementIds) {
}
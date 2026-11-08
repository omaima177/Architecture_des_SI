package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AgenceDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idAgence,
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
        String nom,
        @NotBlank(message = "La ville est obligatoire")
        @Size(max = 50, message = "La ville ne doit pas dépasser 50 caractères")
        String ville,
        @NotBlank(message = "L'adresse est obligatoire")
        @Size(max = 100, message = "L'adresse ne doit pas dépasser 100 caractères")
        String adresse,
        @NotBlank(message = "Le téléphone est obligatoire")
        @Pattern(regexp = "^\\+?[0-9 ]{8,20}$", message = "Téléphone invalide (8 à 20 chiffres, + et espaces autorisés)")
        String telephone) {
}
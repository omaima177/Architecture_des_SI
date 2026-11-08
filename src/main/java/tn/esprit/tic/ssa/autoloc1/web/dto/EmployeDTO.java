package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.tic.ssa.autoloc1.domain.RoleEmploye;

public record EmployeDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idEmploye,
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
        String nom,
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
        String prenom,
        RoleEmploye role,
        @NotNull(message = "agenceId est obligatoire")
        @Positive(message = "agenceId doit être un identifiant positif")
        Long agenceId) {
}
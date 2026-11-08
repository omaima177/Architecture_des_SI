package tn.esprit.tic.ssa.autoloc1.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipementDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) Long idEquipement,
        @NotBlank(message = "Le libellé est obligatoire")
        @Size(max = 50, message = "Le libellé ne doit pas dépasser 50 caractères")
        String libelle) {
}
package tn.esprit.tic.ssa.autoloc1.web.dto;

import tn.esprit.tic.ssa.autoloc1.domain.Maintenance;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;

import java.time.LocalDate;

public record MaintenanceDTO(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long vehiculeId) {

    public static MaintenanceDTO from(Maintenance m) {
        Long vehiculeId = m.getVehicule() != null ? m.getVehicule().getIdVehicule() : null;
        return new MaintenanceDTO(m.getIdMaintenance(), m.getDateDebut(), m.getDateFin(),
                m.getDescription(), vehiculeId);
    }

    public Maintenance toEntity() {
        Maintenance m = new Maintenance();
        m.setDateDebut(dateDebut);
        m.setDateFin(dateFin);
        m.setDescription(description);
        if (vehiculeId != null) {
            Vehicule v = new Vehicule();
            v.setIdVehicule(vehiculeId);
            m.setVehicule(v);
        }
        return m;
    }
}
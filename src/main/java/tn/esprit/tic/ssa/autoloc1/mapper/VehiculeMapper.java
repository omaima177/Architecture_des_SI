package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.domain.Vehicule;
import tn.esprit.tic.ssa.autoloc1.web.dto.VehiculeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "agence.idAgence", target = "agenceId")
    @Mapping(source = "equipements", target = "equipementIds", qualifiedByName = "equipementsToIds")
    VehiculeDTO toDto(Vehicule entity);

    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Vehicule toEntity(VehiculeDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateEntity(VehiculeDTO dto, @MappingTarget Vehicule entity);

    @Named("equipementsToIds")
    default List<Long> equipementsToIds(List<Equipement> equipements) {
        if (equipements == null) {
            return List.of();
        }
        return equipements.stream().map(Equipement::getIdEquipement).toList();
    }
}
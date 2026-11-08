package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Maintenance;
import tn.esprit.tic.ssa.autoloc1.web.dto.MaintenanceDTO;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(source = "vehicule.idVehicule", target = "vehiculeId")
    MaintenanceDTO toDto(Maintenance entity);

    @Mapping(target = "idMaintenance", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idMaintenance", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    void updateEntity(MaintenanceDTO dto, @MappingTarget Maintenance entity);
}
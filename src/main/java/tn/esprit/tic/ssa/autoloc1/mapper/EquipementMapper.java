package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Equipement;
import tn.esprit.tic.ssa.autoloc1.web.dto.EquipementDTO;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    EquipementDTO toDto(Equipement entity);

    @Mapping(target = "idEquipement", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idEquipement", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    void updateEntity(EquipementDTO dto, @MappingTarget Equipement entity);
}
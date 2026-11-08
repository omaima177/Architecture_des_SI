package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Employe;
import tn.esprit.tic.ssa.autoloc1.web.dto.EmployeDTO;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(source = "agence.idAgence", target = "agenceId")
    EmployeDTO toDto(Employe entity);

    @Mapping(target = "idEmploye", ignore = true)
    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idEmploye", ignore = true)
    @Mapping(target = "agence", ignore = true)
    void updateEntity(EmployeDTO dto, @MappingTarget Employe entity);
}
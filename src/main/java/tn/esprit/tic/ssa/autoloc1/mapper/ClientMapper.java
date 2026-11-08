package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Client;
import tn.esprit.tic.ssa.autoloc1.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDto(Client entity);

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "dateInscription", ignore = true)
    void updateEntity(ClientDTO dto, @MappingTarget Client entity);
}
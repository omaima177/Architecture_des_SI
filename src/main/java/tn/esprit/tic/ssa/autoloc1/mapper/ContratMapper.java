package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Contrat;
import tn.esprit.tic.ssa.autoloc1.web.dto.ContratDTO;

@Mapper(componentModel = "spring")
public interface ContratMapper {

    @Mapping(source = "reservation.idReservation", target = "reservationId")
    ContratDTO toDto(Contrat entity);

    @Mapping(target = "idContrat", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    Contrat toEntity(ContratDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idContrat", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    void updateEntity(ContratDTO dto, @MappingTarget Contrat entity);
}
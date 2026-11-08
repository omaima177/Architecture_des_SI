package tn.esprit.tic.ssa.autoloc1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tn.esprit.tic.ssa.autoloc1.domain.Paiement;
import tn.esprit.tic.ssa.autoloc1.web.dto.PaiementDTO;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(source = "contrat.idContrat", target = "contratId")
    PaiementDTO toDto(Paiement entity);

    @Mapping(target = "idPaiement", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idPaiement", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    void updateEntity(PaiementDTO dto, @MappingTarget Paiement entity);
}
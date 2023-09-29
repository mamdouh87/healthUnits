package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.DoctorsUnit;
import com.mkh.healthunits.domain.ExtraPassUnit;
import com.mkh.healthunits.domain.Units;
import com.mkh.healthunits.service.dto.DoctorsUnitDTO;
import com.mkh.healthunits.service.dto.ExtraPassUnitDTO;
import com.mkh.healthunits.service.dto.UnitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Units} and its DTO {@link UnitsDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitsMapper extends EntityMapper<UnitsDTO, Units> {
    @Mapping(target = "doctorsUnit", source = "doctorsUnit", qualifiedByName = "doctorsUnitId")
    @Mapping(target = "extraPassUnit", source = "extraPassUnit", qualifiedByName = "extraPassUnitId")
    UnitsDTO toDto(Units s);

    @Named("doctorsUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DoctorsUnitDTO toDtoDoctorsUnitId(DoctorsUnit doctorsUnit);

    @Named("extraPassUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExtraPassUnitDTO toDtoExtraPassUnitId(ExtraPassUnit extraPassUnit);
}

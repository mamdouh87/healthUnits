package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.Doctors;
import com.mkh.healthunits.domain.DoctorsUnit;
import com.mkh.healthunits.domain.ExtraPassUnit;
import com.mkh.healthunits.service.dto.DoctorsDTO;
import com.mkh.healthunits.service.dto.DoctorsUnitDTO;
import com.mkh.healthunits.service.dto.ExtraPassUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Doctors} and its DTO {@link DoctorsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DoctorsMapper extends EntityMapper<DoctorsDTO, Doctors> {
    @Mapping(target = "doctorsUnit", source = "doctorsUnit", qualifiedByName = "doctorsUnitId")
    @Mapping(target = "extraPassUnit", source = "extraPassUnit", qualifiedByName = "extraPassUnitId")
    DoctorsDTO toDto(Doctors s);

    @Named("doctorsUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DoctorsUnitDTO toDtoDoctorsUnitId(DoctorsUnit doctorsUnit);

    @Named("extraPassUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExtraPassUnitDTO toDtoExtraPassUnitId(ExtraPassUnit extraPassUnit);
}

package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.DoctorPassImgs;
import com.mkh.healthunits.domain.Doctors;
import com.mkh.healthunits.domain.Units;
import com.mkh.healthunits.service.dto.DoctorPassImgsDTO;
import com.mkh.healthunits.service.dto.DoctorsDTO;
import com.mkh.healthunits.service.dto.UnitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DoctorPassImgs} and its DTO {@link DoctorPassImgsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DoctorPassImgsMapper extends EntityMapper<DoctorPassImgsDTO, DoctorPassImgs> {
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitsId")
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "doctorsId")
    DoctorPassImgsDTO toDto(DoctorPassImgs s);

    @Named("unitsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitsDTO toDtoUnitsId(Units units);

    @Named("doctorsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DoctorsDTO toDtoDoctorsId(Doctors doctors);
}

package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.DoctorsUnit;
import com.mkh.healthunits.service.dto.DoctorsUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DoctorsUnit} and its DTO {@link DoctorsUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface DoctorsUnitMapper extends EntityMapper<DoctorsUnitDTO, DoctorsUnit> {}

package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.ExtraPassUnit;
import com.mkh.healthunits.service.dto.ExtraPassUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtraPassUnit} and its DTO {@link ExtraPassUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExtraPassUnitMapper extends EntityMapper<ExtraPassUnitDTO, ExtraPassUnit> {}

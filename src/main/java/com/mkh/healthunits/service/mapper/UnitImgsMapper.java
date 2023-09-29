package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.UnitImgs;
import com.mkh.healthunits.domain.Units;
import com.mkh.healthunits.service.dto.UnitImgsDTO;
import com.mkh.healthunits.service.dto.UnitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnitImgs} and its DTO {@link UnitImgsDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitImgsMapper extends EntityMapper<UnitImgsDTO, UnitImgs> {
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitsId")
    UnitImgsDTO toDto(UnitImgs s);

    @Named("unitsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitsDTO toDtoUnitsId(Units units);
}

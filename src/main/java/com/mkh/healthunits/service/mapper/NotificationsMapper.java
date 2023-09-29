package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.Notifications;
import com.mkh.healthunits.domain.Units;
import com.mkh.healthunits.service.dto.NotificationsDTO;
import com.mkh.healthunits.service.dto.UnitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notifications} and its DTO {@link NotificationsDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationsMapper extends EntityMapper<NotificationsDTO, Notifications> {
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitsId")
    NotificationsDTO toDto(Notifications s);

    @Named("unitsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitsDTO toDtoUnitsId(Units units);
}

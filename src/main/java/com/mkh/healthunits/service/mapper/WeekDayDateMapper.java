package com.mkh.healthunits.service.mapper;

import com.mkh.healthunits.domain.WeekDayDate;
import com.mkh.healthunits.service.dto.WeekDayDateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WeekDayDate} and its DTO {@link WeekDayDateDTO}.
 */
@Mapper(componentModel = "spring")
public interface WeekDayDateMapper extends EntityMapper<WeekDayDateDTO, WeekDayDate> {}

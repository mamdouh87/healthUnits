package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.WeekDayDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WeekDayDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekDayDateRepository extends JpaRepository<WeekDayDate, Long> {}

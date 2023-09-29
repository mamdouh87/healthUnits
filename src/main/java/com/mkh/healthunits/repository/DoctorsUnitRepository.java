package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.DoctorsUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DoctorsUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorsUnitRepository extends JpaRepository<DoctorsUnit, Long> {}

package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.Units;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Units entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {}

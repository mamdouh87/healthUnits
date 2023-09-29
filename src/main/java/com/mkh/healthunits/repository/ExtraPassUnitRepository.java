package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.ExtraPassUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExtraPassUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraPassUnitRepository extends JpaRepository<ExtraPassUnit, Long> {}

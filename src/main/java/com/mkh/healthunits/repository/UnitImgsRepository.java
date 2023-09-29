package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.UnitImgs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UnitImgs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitImgsRepository extends JpaRepository<UnitImgs, Long> {}

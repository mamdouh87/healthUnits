package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.DoctorPassImgs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DoctorPassImgs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorPassImgsRepository extends JpaRepository<DoctorPassImgs, Long> {}

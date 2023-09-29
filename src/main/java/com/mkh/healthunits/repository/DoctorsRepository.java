package com.mkh.healthunits.repository;

import com.mkh.healthunits.domain.Doctors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doctors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Long> {}

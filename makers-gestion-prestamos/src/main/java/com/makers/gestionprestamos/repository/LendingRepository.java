package com.makers.gestionprestamos.repository;

import com.makers.gestionprestamos.entity.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {}

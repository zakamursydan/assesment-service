package com.techno.assesment.assesment_service.repository;

import com.techno.assesment.assesment_service.domain.entity.BrandMobilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandMobilRepository extends JpaRepository<BrandMobilEntity, Long> {
    boolean existsByName(String name);
}

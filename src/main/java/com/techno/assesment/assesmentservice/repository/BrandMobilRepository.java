package com.techno.assesment.assesmentservice.repository;

import com.techno.assesment.assesmentservice.domain.entity.BrandMobilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandMobilRepository extends JpaRepository<BrandMobilEntity, Long> {
    boolean existsByName(String name);
}

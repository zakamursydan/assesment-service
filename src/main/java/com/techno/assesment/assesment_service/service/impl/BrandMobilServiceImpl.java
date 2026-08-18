package com.techno.assesment.assesment_service.service.impl;

import com.techno.assesment.assesment_service.domain.dto.request.BrandMobilRequest;
import com.techno.assesment.assesment_service.domain.dto.response.BrandMobilResponse;
import com.techno.assesment.assesment_service.domain.entity.BrandMobilEntity;
import com.techno.assesment.assesment_service.exception.BadRequestException;
import com.techno.assesment.assesment_service.exception.ResourceNotFoundException;
import com.techno.assesment.assesment_service.repository.BrandMobilRepository;
import com.techno.assesment.assesment_service.service.BrandMobilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandMobilServiceImpl implements BrandMobilService {

    private final BrandMobilRepository brandMobilRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BrandMobilResponse> getAll() {
        return brandMobilRepository.findAll().stream()
                .map(brandMobilEntity -> BrandMobilResponse.builder()
                        .id(brandMobilEntity.getId())
                        .name(brandMobilEntity.getName())
                        .createdAt(brandMobilEntity.getCreatedAt())
                        .updatedAt(brandMobilEntity.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BrandMobilResponse getById(Long id) {
        BrandMobilEntity brandMobilEntity = findBrandMobilByIdOrThrow(id);
        return BrandMobilResponse.builder()
                .id(brandMobilEntity.getId())
                .name(brandMobilEntity.getName())
                .createdAt(brandMobilEntity.getCreatedAt())
                .updatedAt(brandMobilEntity.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public BrandMobilResponse create(BrandMobilRequest request) {
        if (brandMobilRepository.existsByName(request.getName())) {
            throw new BadRequestException("Brand name already exists");
        }

        BrandMobilEntity brandMobilEntity = BrandMobilEntity.builder()
                .name(request.getName())
                .build();

        BrandMobilEntity savedBrandMobilEntity = brandMobilRepository.save(brandMobilEntity);
        
        return BrandMobilResponse.builder()
                .id(savedBrandMobilEntity.getId())
                .name(savedBrandMobilEntity.getName())
                .createdAt(savedBrandMobilEntity.getCreatedAt())
                .updatedAt(savedBrandMobilEntity.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public BrandMobilResponse update(Long id, BrandMobilRequest request) {
        BrandMobilEntity brandMobilEntity = findBrandMobilByIdOrThrow(id);

        if (!brandMobilEntity.getName().equals(request.getName()) && brandMobilRepository.existsByName(request.getName())) {
            throw new BadRequestException("Brand name already exists");
        }

        brandMobilEntity.setName(request.getName());

        BrandMobilEntity updatedBrandMobilEntity = brandMobilRepository.save(brandMobilEntity);
        
        return BrandMobilResponse.builder()
                .id(updatedBrandMobilEntity.getId())
                .name(updatedBrandMobilEntity.getName())
                .createdAt(updatedBrandMobilEntity.getCreatedAt())
                .updatedAt(updatedBrandMobilEntity.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BrandMobilEntity brandMobilEntity = findBrandMobilByIdOrThrow(id);
        brandMobilRepository.delete(brandMobilEntity);
    }

    private BrandMobilEntity findBrandMobilByIdOrThrow(Long id) {
        return brandMobilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand Mobil not found with id: " + id));
    }
}

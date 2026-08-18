package com.techno.assesment.assesmentservice.service;

import com.techno.assesment.assesmentservice.domain.dto.request.BrandMobilRequest;
import com.techno.assesment.assesmentservice.domain.dto.response.BrandMobilResponse;

import java.util.List;

public interface BrandMobilService {

    List<BrandMobilResponse> getAll();

    BrandMobilResponse getById(Long id);

    BrandMobilResponse create(BrandMobilRequest request);

    BrandMobilResponse update(Long id, BrandMobilRequest request);

    void delete(Long id);
}

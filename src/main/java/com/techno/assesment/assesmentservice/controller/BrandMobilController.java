package com.techno.assesment.assesmentservice.controller;

import com.techno.assesment.assesmentservice.domain.dto.request.BrandMobilRequest;
import com.techno.assesment.assesmentservice.domain.dto.response.BrandMobilResponse;
import com.techno.assesment.assesmentservice.domain.dto.response.BaseResponse;
import com.techno.assesment.assesmentservice.service.BrandMobilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brand-mobil")
@RequiredArgsConstructor
public class BrandMobilController {

    private final BrandMobilService brandMobilService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<BrandMobilResponse>>> getAll() {
        List<BrandMobilResponse> data = brandMobilService.getAll();
        BaseResponse<List<BrandMobilResponse>> response = BaseResponse.<List<BrandMobilResponse>>builder()
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BrandMobilResponse>> getById(@PathVariable Long id) {
        BrandMobilResponse data = brandMobilService.getById(id);
        BaseResponse<BrandMobilResponse> response = BaseResponse.<BrandMobilResponse>builder()
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<BrandMobilResponse>> create(@Valid @RequestBody BrandMobilRequest request) {
        BrandMobilResponse data = brandMobilService.create(request);
        BaseResponse<BrandMobilResponse> response = BaseResponse.<BrandMobilResponse>builder()
                .message("Berhasil menambahkan brand mobil " + data.getName())
                .data(data)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<BrandMobilResponse>> update(@PathVariable Long id, @Valid @RequestBody BrandMobilRequest request) {
        BrandMobilResponse data = brandMobilService.update(id, request);
        BaseResponse<BrandMobilResponse> response = BaseResponse.<BrandMobilResponse>builder()
                .message("Berhasil mengupdate brand mobil " + data.getName())
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long id) {
        brandMobilService.delete(id);
        BaseResponse<String> response = BaseResponse.<String>builder()
                .data("Brand Mobil deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}

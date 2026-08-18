package com.techno.assesment.assesment_service.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    @Builder.Default
    private UUID reqId = UUID.randomUUID();

    @Builder.Default
    private String status = "T";

    @Builder.Default
    private String message = "Berhasil";

    private Object error;
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pagination pagination;
}

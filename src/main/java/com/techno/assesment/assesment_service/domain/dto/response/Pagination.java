package com.techno.assesment.assesment_service.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private Integer totalRecords;
    private Integer currentPage;
    private Integer totalPage;
    private Integer nextPage;
    private Integer prevPage;
    private Integer limit;
}

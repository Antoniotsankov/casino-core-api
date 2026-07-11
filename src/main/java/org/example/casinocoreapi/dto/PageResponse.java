package org.example.casinocoreapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Paginated response")
public class PageResponse<T> {

    @Schema(description = "Returned data")
    private List<T> content;

    @Schema(description = "Current page number", example = "0")
    private int page;

    @Schema(description = "Number of records per page", example = "10")
    private int size;

    @Schema(description = "Total number of records", example = "57")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "6")
    private int totalPages;
}

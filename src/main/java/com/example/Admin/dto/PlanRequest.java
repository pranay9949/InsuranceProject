package com.example.Admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanRequest {
    private String planName;
    private LocalDate planStartDate;
    private LocalDate planEndDate;

    @NotNull(message = "Category id cannot be null")
    private Long planCategoryId;

    private String planStatus;
}

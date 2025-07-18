package com.example.Admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanRequest {
    @NotNull
    private String planName;
    @NotNull
    private LocalDate planStartDate;
    @NotNull
    private LocalDate planEndDate;

    @NotNull(message = "Category id cannot be null")
    private Long planCategoryId;
  @NotNull
    private String planStatus;
}

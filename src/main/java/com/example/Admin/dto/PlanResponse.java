package com.example.Admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanResponse {
    private Long planId;
    private String planName;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private String planCategoryName;
    private String planStatus;
}

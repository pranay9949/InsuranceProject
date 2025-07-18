package com.example.Admin.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CaseWorkerResponse {

    private Integer caseWorkerId;
    private String caseWorkerName;


    private String caseWorkerEmailId;


    private Long   caseWorkerMobileNumber;


    private Character caseWorkerGender;

    private LocalDate caseWorkerDateOfBirth;


    private Long ssn;

}

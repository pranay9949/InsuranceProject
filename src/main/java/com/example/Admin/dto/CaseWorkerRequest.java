package com.example.Admin.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CaseWorkerRequest {




    private String caseWorkerName;


    private String caseWorkerEmailId;


    private Long   caseWorkerMobileNumber;


    private Character caseWorkerGender;

    private LocalDate caseWorkerDateOfBirth;


    private Long ssn;



}

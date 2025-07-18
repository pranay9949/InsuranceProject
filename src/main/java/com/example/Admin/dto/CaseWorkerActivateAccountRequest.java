package com.example.Admin.dto;

import lombok.Data;

@Data
public class CaseWorkerActivateAccountRequest {
    private String caseWorkerEmailId;
    private String tempPass;
    private String password;
    private String confirmPassword;
}

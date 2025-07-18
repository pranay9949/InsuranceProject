package com.example.Admin.dto;


import lombok.Data;

@Data
public class WorkerLoginRequest {
    private  String emailId;
    private String password;
}

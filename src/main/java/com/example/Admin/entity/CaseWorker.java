package com.example.Admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Data
public class CaseWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CASE_WORKER_ID")
    private Integer caseWorkerId;

    @Column(name="CASE_WORKER_NAME")
    private String caseWorkerName;

    @Column(name="EMAIL_ID",unique = true)
    private String caseWorkerEmailId;

    @Column(name="MOBILE_NUMBER")
    private Long   caseWorkerMobileNumber;

    @Column(name="GENDER")
    private Character caseWorkerGender;

    @Column(name="DOB")
    private LocalDate caseWorkerDateOfBirth;

    @Column(name="SSN")
    private Long ssn;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="ACCOUNT_STATUS")
    private String accStatus;



    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name = "UPDATED_DATE", insertable = false)
    @UpdateTimestamp
    private LocalDate updatedDate;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="UPDATED_BY")
    private String updatedBy;
}

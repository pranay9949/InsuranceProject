package com.example.Admin.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="PLAN_MASTER")
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PLAN_ID")
    private Long planId;

    @Column(name="PLAN_NAME")
    private String planName;

    @Column(name="PLAN_START_DATE")
    private LocalDate planStartDate;

    @Column(name="PLAN_END_DATE")
    private LocalDate planEndDate;

    @Column(name="ACTIVE_SW")
    private String planStatus;

  @Column(name="PLAN_CATEGORY_ID")
    private Long planCategoryId;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="UPDATED_BY")
    private String updatedBy;

    @Column(name="CREATED_AT", updatable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name="UPDATED_AT", insertable = false)
    @UpdateTimestamp
    private LocalDate updatedDate;
}

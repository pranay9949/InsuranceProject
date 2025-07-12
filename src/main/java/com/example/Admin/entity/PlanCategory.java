package com.example.Admin.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="PLAN_CATEGORY")
public class PlanCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PLAN_CATEGORY_ID")
    private Long planCategoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name="ACTIVE_SW")
    private String activeSw;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="UPDATED_BY")
    private String updatedBy;

    @Column(name = "CREATED_AT",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="UPDATED_AT", insertable = false)
     @UpdateTimestamp
    private LocalDateTime updatedAt;

}

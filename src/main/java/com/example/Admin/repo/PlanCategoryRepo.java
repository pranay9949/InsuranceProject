package com.example.Admin.repo;

import com.example.Admin.entity.PlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PlanCategoryRepo extends JpaRepository<PlanCategory,Long> {
}

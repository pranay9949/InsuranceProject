package com.example.Admin.repo;

import com.example.Admin.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlanRepo extends JpaRepository<Plan,Long> {
    public List<Plan> findByPlanCategoryId(Long id);
}

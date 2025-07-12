package com.example.Admin.repo;

import com.example.Admin.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepo extends JpaRepository<Plan,Long> {
}

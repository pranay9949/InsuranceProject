package com.example.Admin.service;

import com.example.Admin.dto.PlanRequest;
import com.example.Admin.dto.PlanResponse;
import com.example.Admin.entity.PlanCategory;
import com.example.Admin.exception.CategoryNotFoundException;
import com.example.Admin.exception.InvalidDateException;
import com.example.Admin.exception.PlanNotFoundException;
import com.example.Admin.repo.PlanCategoryRepo;
import jakarta.persistence.Lob;
import org.springframework.stereotype.Service;

import java.net.InterfaceAddress;
import java.util.List;
import java.util.Map;


public interface PlanService {

    public Map<Long,String> getAllCategories();
    public  Boolean createPlan(PlanRequest planRequest) throws InvalidDateException, CategoryNotFoundException;

    public List<PlanResponse> getAllPlans();

    public PlanResponse getPlanById(Long id) throws PlanNotFoundException;

    public Boolean updatePlanById(PlanRequest req , Long id) throws PlanNotFoundException, InvalidDateException;

    public Boolean deletePlan(Long id) throws PlanNotFoundException;

    public Boolean planStatusChange(Long id,String Status) throws PlanNotFoundException;


}

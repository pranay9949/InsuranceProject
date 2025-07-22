package com.example.Admin.service;


import com.example.Admin.dto.PlanRequest;
import com.example.Admin.dto.PlanResponse;
import com.example.Admin.entity.Plan;
import com.example.Admin.entity.PlanCategory;
import com.example.Admin.exception.CategoryNotFoundException;
import com.example.Admin.exception.InvalidDateException;
import com.example.Admin.exception.PlanNotFoundException;
import com.example.Admin.repo.PlanCategoryRepo;
import com.example.Admin.repo.PlanRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.zip.DataFormatException;

@Service
public class PlanServiceImpl implements PlanService {
    private PlanCategoryRepo planCategoryRepo;
    private PlanRepo planRepo;

    public PlanServiceImpl(PlanCategoryRepo planCategoryRepo, PlanRepo planRepo) {
        this.planCategoryRepo = planCategoryRepo;
        this.planRepo = planRepo;
    }


    @Override
    public Map<Long, String> getAllCategories() {
        Map<Long,String> mp = new HashMap<>();
        List<PlanCategory> category =  planCategoryRepo.findAll();
        category.stream().forEach(item->{
            mp.put(item.getPlanCategoryId(),item.getCategoryName());
        });


        return mp;
    }

    @Override
    public Boolean createPlan(PlanRequest planRequest) throws InvalidDateException, CategoryNotFoundException {
        if(planRequest.getPlanStartDate().isAfter(planRequest.getPlanEndDate())){
            throw new InvalidDateException("Please Enter the Valid Date");
        }
        PlanCategory planCategory = planCategoryRepo.findById(planRequest.getPlanCategoryId()).orElseThrow(()->new CategoryNotFoundException("Please enter a valid Category Id"));
        Plan p1 = new Plan();
       BeanUtils.copyProperties(planRequest,p1);

         Plan p = planRepo.save(p1);
        return p!=null;
    }

    @Override
    public List<PlanResponse> getAllPlans() {
      List<Plan> plans =  planRepo.findAll();
      List<PlanResponse> listRes = new ArrayList<>();

      for(Plan p : plans){
          PlanResponse res = new PlanResponse();
          PlanCategory category = planCategoryRepo.findById(p.getPlanCategoryId()).get();
          BeanUtils.copyProperties(p,res);
          res.setPlanCategoryName(category.getCategoryName());
          listRes.add(res);
      }
      return listRes;
    }

    @Override
    public PlanResponse getPlanById(Long id) throws PlanNotFoundException {
        Optional<Plan> p = planRepo.findById(id);
        PlanResponse pr = new PlanResponse();
        if(p.isPresent()){
            Plan p1 = p.get();
            PlanCategory category = planCategoryRepo.findById(p1.getPlanId()).get();
            pr.setPlanCategoryName(category.getCategoryName());
            BeanUtils.copyProperties(p1,pr);
            return pr;
        }
        else{
           throw  new PlanNotFoundException("Please enter a Valid Plan id ");
        }

    }


    @Override
    public Boolean updatePlanById(PlanRequest req, Long id) throws PlanNotFoundException, InvalidDateException {
        Optional<Plan> op = planRepo.findById(id);
        if(op.isEmpty()){
            throw  new PlanNotFoundException("Please enter a Valid Plan id ");
        }
        Plan p = op.get();
       LocalDate start = req.getPlanStartDate()!=null?req.getPlanStartDate():p.getPlanStartDate();
       LocalDate end = req.getPlanEndDate()!=null?req.getPlanEndDate():p.getPlanEndDate();

       if(start.isAfter(end)){
           throw new InvalidDateException("Please Enter the valid Dates");
       }



        if(req.getPlanEndDate()!=null){
            p.setPlanEndDate(req.getPlanEndDate());
        }
        if(req.getPlanStartDate()!=null){
            p.setPlanStartDate(req.getPlanStartDate());
        }
        if(req.getPlanName()!=null){
            p.setPlanName(req.getPlanName());
        }
        if(req.getPlanStatus()!=null){
            p.setPlanStatus(req.getPlanStatus());
        }
        if(req.getPlanCategoryId()!=null){
            p.setPlanCategoryId(req.getPlanCategoryId());
        }
        planRepo.save(p);
        PlanResponse response = new PlanResponse();
        BeanUtils.copyProperties(p,response);
        return response!=null;
    }



    @Override
    public Boolean deletePlan(Long id) throws PlanNotFoundException {
        Optional<Plan> op = planRepo.findById(id);
        if(op.isEmpty()){
            throw  new PlanNotFoundException("Please enter a Valid Plan id ");
        }
        Plan p = op.get();
        planRepo.deleteById(id);
        return true;
    }

    @Override
    public Boolean planStatusChange(Long id, String Status) throws PlanNotFoundException {
        Plan plan = planRepo.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("Please enter a valid Plan ID"));

        plan.setPlanStatus(Status);
        planRepo.save(plan);
        return true;

    }

    @Override
    public List<PlanResponse> getByCategoryId(Long id) throws PlanNotFoundException {
        List<Plan> p = planRepo.findByPlanCategoryId(id);
        if(p==null){
            throw   new PlanNotFoundException("Please Enter Valid Category Id");
        }
        List<PlanResponse> planResponse = new ArrayList<>();
        PlanCategory category = planCategoryRepo.findById(id).get();
        p.stream().forEach(plan->{
            PlanResponse res = new PlanResponse();
            BeanUtils.copyProperties(plan,res);
            res.setPlanCategoryName(category.getCategoryName());
            planResponse.add(res);
        });



        return planResponse;


    }


}

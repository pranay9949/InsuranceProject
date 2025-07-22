package com.example.Admin.controller;

import com.example.Admin.constants.AppConstants;
import com.example.Admin.dto.PlanRequest;
import com.example.Admin.dto.PlanResponse;
import com.example.Admin.exception.CategoryNotFoundException;
import com.example.Admin.exception.InvalidDateException;
import com.example.Admin.exception.PlanNotFoundException;
import com.example.Admin.props.AppProperties;
import com.example.Admin.service.PlanServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
    private PlanServiceImpl planService;
    private Map<String, String> messages;

    public PlanController(PlanServiceImpl planService, AppProperties appProperties) {

        this.planService = planService;
        this.messages=appProperties.getMessages();
    }
    @GetMapping("/categories")
    public ResponseEntity<Map<Long,String>> getAllCategories(){
        Map<Long,String> mp = planService.getAllCategories();
        return new ResponseEntity<>(mp, HttpStatus.OK);

    }
    @PostMapping("/addPlan")
    public ResponseEntity<String> savePlan(@Valid @RequestBody PlanRequest request) throws InvalidDateException, CategoryNotFoundException {
        String responseMsg = AppConstants.EMPTY_STR;


        Boolean data = planService.createPlan(request);
      if(data){
         responseMsg= messages.get(AppConstants.PLAN_SAVE_SUCC);
       }
       else{
          responseMsg= messages.get(AppConstants.PLAN_SAVE_FAIL);

      }
        return  new ResponseEntity<>(responseMsg,HttpStatus.CREATED);
    }
    @GetMapping("/getallplans")
    public ResponseEntity<List<PlanResponse>> getAllPlans(){
        List<PlanResponse> res = planService.getAllPlans();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/getPlan/{id}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Long id) throws PlanNotFoundException {
        PlanResponse res = planService.getPlanById(id);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePlan(@RequestBody PlanRequest req, @PathVariable Long id) throws PlanNotFoundException, InvalidDateException {
        String responseMsg = AppConstants.EMPTY_STR;

        Boolean res = planService.updatePlanById(req,id);
        if(res){
            responseMsg=messages.get(AppConstants.PLAN_UPDATE_SUCC);
        }
        else{
            responseMsg=messages.get(AppConstants.PLAN_UPDATE_FAIL);
        }
        return  new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) throws PlanNotFoundException {
        String responseMsg = AppConstants.EMPTY_STR;
        Boolean s = planService.deletePlan(id);
        System.out.println(AppConstants.PLAN_DELETE_SUCC);
        if(s){
            responseMsg=messages.get(AppConstants.PLAN_DELETE_SUCC);

        }
        else{
            responseMsg=messages.get(AppConstants.PLAN_DELETE_FAIL);
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.OK);

    }
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<String> statusChange(@PathVariable Long id, @PathVariable String status) throws PlanNotFoundException {
        String responseMsg = AppConstants.EMPTY_STR;
        boolean s = planService.planStatusChange(id,status);
        if(s){
            responseMsg=messages.get(AppConstants.PLAN_STATUS_CHANGE);

        }
        else{
            responseMsg=messages.get(AppConstants.PLAN_STATUS_CHANGE_FAIL);
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getByCategoryId/{id}")
    public ResponseEntity<List<PlanResponse>> getByCatId(@PathVariable Long id) throws PlanNotFoundException {
        List<PlanResponse> planResponse = planService.getByCategoryId(id);
        return new ResponseEntity<>(planResponse,HttpStatus.OK);
    }

}

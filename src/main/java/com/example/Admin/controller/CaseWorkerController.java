package com.example.Admin.controller;

import com.example.Admin.apiresponse.ApiResponse;
import com.example.Admin.dto.*;
import com.example.Admin.exception.InvalidPasswordException;
import com.example.Admin.exception.NoWorkerFoundException;
import com.example.Admin.exception.ValidEmailException;
import com.example.Admin.service.CaseWorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/worker")
public class CaseWorkerController {
    private final CaseWorkerService caseWorkerService;

    public CaseWorkerController(CaseWorkerService caseWorkerService) {
        this.caseWorkerService = caseWorkerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerWorker(@RequestBody CaseWorkerRequest request) {
        boolean isRegistered = caseWorkerService.registerCaseWorker(request);
        String message = isRegistered ? "Case Worker Registered Successfully" : "Registration Failed";
        return ResponseEntity.ok(new ApiResponse(message, isRegistered));
    }

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse> activateWorker(@RequestBody CaseWorkerActivateAccountRequest request)
            throws InvalidPasswordException, ValidEmailException {
        boolean isActivated = caseWorkerService.activateAccount(request);
        String message = isActivated ? "Account Activation Successful" : "Account Activation Failed";
        return ResponseEntity.ok(new ApiResponse(message, isActivated));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginWorker(@RequestBody WorkerLoginRequest loginRequest)
            throws ValidEmailException {
        String message = caseWorkerService.accountLogin(loginRequest); // Assuming message is meaningful
        return ResponseEntity.ok(new ApiResponse(message, true));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgetRequest request)
            throws ValidEmailException {
        boolean isSent = caseWorkerService.forgetPassword(request);
        String message = isSent ? "Password Sent Successfully" : "Failed to Send Password";
        return ResponseEntity.ok(new ApiResponse(message, isSent));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CaseWorkerResponse>> getAllWorkers() {
        List<CaseWorkerResponse> responses = caseWorkerService.getAllCaseWorkers();
        return ResponseEntity.ok(responses); // This is a data list, so return as-is
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CaseWorkerResponse> getWorker(@PathVariable Integer id)
            throws NoWorkerFoundException {
        CaseWorkerResponse response = caseWorkerService.getById(id);
        return ResponseEntity.ok(response); // Also data object, return as-is
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id)
            throws NoWorkerFoundException {
        boolean isDeleted = caseWorkerService.deleteById(id);
        String message = isDeleted ? "Deleted Successfully" : "Deletion Failed";
        return ResponseEntity.ok(new ApiResponse(message, isDeleted));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateWorker(@PathVariable Integer id,
                                                    @RequestBody CaseWorkerRequest request)
            throws NoWorkerFoundException {
        boolean isUpdated = caseWorkerService.update(id, request);
        String message = isUpdated ? "Updated Successfully" : "Update Failed";
        return ResponseEntity.ok(new ApiResponse(message, isUpdated));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ApiResponse> statusChange(@PathVariable Integer id,
                                                    @RequestParam String status)
            throws NoWorkerFoundException {
        boolean isChanged = caseWorkerService.statusChange(id, status);
        String message = isChanged ? "Status changed Successfully" : "Status change failed";
        return ResponseEntity.ok(new ApiResponse(message, isChanged));
    }
}

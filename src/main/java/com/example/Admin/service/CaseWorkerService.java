package com.example.Admin.service;

import com.example.Admin.dto.*;
import com.example.Admin.exception.InvalidPasswordException;
import com.example.Admin.exception.NoWorkerFoundException;
import com.example.Admin.exception.ValidEmailException;
import com.example.Admin.repo.CaseWorkerRepo;

import java.util.List;

public interface CaseWorkerService {

    public Boolean registerCaseWorker(CaseWorkerRequest request);

    public Boolean activateAccount(CaseWorkerActivateAccountRequest request) throws ValidEmailException, InvalidPasswordException;
    public String accountLogin(WorkerLoginRequest loginRequest) throws ValidEmailException;

    public Boolean forgetPassword(ForgetRequest request) throws ValidEmailException;

    public List<CaseWorkerResponse> getAllCaseWorkers();

    public CaseWorkerResponse getById(Integer id) throws NoWorkerFoundException;

    public Boolean deleteById(Integer id) throws NoWorkerFoundException;

    public Boolean update(Integer id,CaseWorkerRequest request) throws NoWorkerFoundException;

   public Boolean statusChange(Integer id, String status) throws NoWorkerFoundException;
}

package com.example.Admin.service;

import com.example.Admin.dto.*;
import com.example.Admin.entity.CaseWorker;
import com.example.Admin.exception.InvalidPasswordException;
import com.example.Admin.exception.NoWorkerFoundException;
import com.example.Admin.exception.ValidEmailException;
import com.example.Admin.repo.CaseWorkerRepo;
import com.example.Admin.utils.EmailUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CaseWorkerServiceImpl implements CaseWorkerService {
    private CaseWorkerRepo workerRepo;
    private EmailUtils emailUtils;

    public CaseWorkerServiceImpl(CaseWorkerRepo workerRepo,EmailUtils emailUtils) {
        this.workerRepo = workerRepo;
        this.emailUtils=emailUtils;
    }



    @Override
    public Boolean registerCaseWorker(CaseWorkerRequest request) {

        CaseWorker caseWorker = new CaseWorker();
        BeanUtils.copyProperties(request,caseWorker);
        caseWorker.setAccStatus("IN-ACTIVE");
        caseWorker.setPassword(generatePassword());
        String fileName = "CaseWorkerRegistrationBody.txt";
        String subject = "Case Worker Onboarding Mail";
        String emailBody = readEmailBody(request.getCaseWorkerName(), caseWorker.getPassword(), fileName);
        emailUtils.sendMail(request.getCaseWorkerEmailId(),subject,emailBody);
       workerRepo.save(caseWorker);
        return true;
    }

    public Boolean activateAccount(CaseWorkerActivateAccountRequest request) throws ValidEmailException, InvalidPasswordException {
        CaseWorker worker  = workerRepo.findByCaseWorkerEmailId(request.getCaseWorkerEmailId());


        if(worker==null ){
            throw new ValidEmailException("Please Enter A Valid Email Address");
        }
        if(worker.getAccStatus().equals("ACTIVE")){
            throw  new ValidEmailException("Your Account is Already Activated");
        }
        if(!request.getTempPass().equals(worker.getPassword())){
            throw new InvalidPasswordException("Please Enter A Valid Temporory Password");

        }
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidPasswordException("Password And Confirm Password Does Not Match");
        }
        worker.setPassword(request.getPassword());
        worker.setAccStatus("ACTIVE");
        workerRepo.save(worker);
        return worker!=null;

    }

    @Override
    public String accountLogin(WorkerLoginRequest loginRequest) throws ValidEmailException {

        CaseWorker worker  = workerRepo.findByCaseWorkerEmailId(loginRequest.getEmailId());
        if(worker==null || !loginRequest.getPassword().equals(worker.getPassword()) ){
            throw  new ValidEmailException("Please Enter Valid Credentials");
        }
        if (!"ACTIVE".equalsIgnoreCase(worker.getAccStatus())) {
            throw new ValidEmailException("Account is not active. Please verify your email.");
        }

        return "Login Successful";




    }

    @Override
    public Boolean forgetPassword(ForgetRequest request) throws ValidEmailException {
        CaseWorker worker  = workerRepo.findByCaseWorkerEmailId(request.getEmailId());

        if(worker==null ){
            throw new ValidEmailException("Please Enter A Valid Email Address");
        }
        String fileName = "CaseWorkerForgetPassword.txt";
        String subject = "Password Recovery Mail";
        String emailBody = readForgetEmailBody(worker.getCaseWorkerName(), worker.getPassword(), fileName);

        emailUtils.sendMail(request.getEmailId(), subject, emailBody);
        return  worker!=null;

    }

    @Override
    public List<CaseWorkerResponse> getAllCaseWorkers() {
        List<CaseWorker> list = workerRepo.findAll();
        return list.stream().map((item)->{
            CaseWorkerResponse response = new CaseWorkerResponse();
            BeanUtils.copyProperties(item,response);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public CaseWorkerResponse getById(Integer id) throws NoWorkerFoundException {
      CaseWorker worker = workerRepo.findById(id).orElseThrow(()->new NoWorkerFoundException("Please Enter a Valid Id"));
     CaseWorkerResponse response = new CaseWorkerResponse();
     BeanUtils.copyProperties(worker,response);
     return response;

    }

    @Override
    public Boolean deleteById(Integer id) throws NoWorkerFoundException {
        CaseWorker worker = workerRepo.findById(id).orElseThrow(()->new NoWorkerFoundException("Please Enter a Valid Id"));
        workerRepo.delete(worker);
        return true;
    }

    @Override
    public Boolean update(Integer id, CaseWorkerRequest request) throws NoWorkerFoundException {
        CaseWorker worker = workerRepo.findById(id).orElseThrow(()->new NoWorkerFoundException("Please Enter a Valid Id"));
        if(request.getCaseWorkerGender()!=null){
            worker.setCaseWorkerGender(request.getCaseWorkerGender());

        }
        if(request.getSsn()!=null){
            worker.setSsn(request.getSsn());

        }
        if(request.getCaseWorkerMobileNumber()!=null){
            worker.setCaseWorkerMobileNumber(request.getCaseWorkerMobileNumber());

        }
        if(request.getCaseWorkerEmailId()!=null){
            worker.setCaseWorkerEmailId(request.getCaseWorkerEmailId());

        }
        if(request.getCaseWorkerDateOfBirth()!=null){
            worker.setCaseWorkerDateOfBirth(request.getCaseWorkerDateOfBirth());

        }
        if(request.getCaseWorkerName()!=null){
            worker.setCaseWorkerName(request.getCaseWorkerName());

        }
        workerRepo.save(worker);
      return worker!=null;
    }

    @Override
    public Boolean statusChange(Integer id , String status) throws NoWorkerFoundException {
        CaseWorker worker = workerRepo.findById(id).orElseThrow(()->new NoWorkerFoundException("Please Enter a Valid Id"));
         worker.setAccStatus(status);
         workerRepo.save(worker);
         return worker!=null;
    }


    public String generatePassword(){
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        int length = 6;

        for(int i=0;i<length;i++){
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);

        }
        return sb.toString();
    }

    public String readEmailBody(String fullName, String password,String filename){
        String url ="";
        String mailBody = null;
        try{
            ClassPathResource resource = new ClassPathResource(filename);
            Path filePath = Path.of(resource.getURI());
            try(FileReader fr = new FileReader(filePath.toFile()); BufferedReader br = new BufferedReader(fr)) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while(line!=null){
                    sb.append(line);
                    line=br.readLine();
                }
                mailBody= sb.toString();
                mailBody = mailBody.replace("{FULL-NAME}",fullName);
                mailBody=  mailBody.replace("{TEMP-PWD}",password);
                mailBody =  mailBody.replace("{URL}",url);


            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mailBody;
    }

    public String readForgetEmailBody(String fullName, String password,String filename){

        String mailBody = null;
        try{
            ClassPathResource resource = new ClassPathResource(filename);
            Path filePath = Path.of(resource.getURI());
            try(FileReader fr = new FileReader(filePath.toFile()); BufferedReader br = new BufferedReader(fr)) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while(line!=null){
                    sb.append(line);
                    line=br.readLine();
                }
                mailBody= sb.toString();
                mailBody = mailBody.replace("{FULL-NAME}",fullName);
                mailBody=  mailBody.replace("{TEMP-PWD}",password);



            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mailBody;
    }
}

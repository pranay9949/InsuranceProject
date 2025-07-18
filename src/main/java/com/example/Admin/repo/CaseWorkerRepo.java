package com.example.Admin.repo;

import com.example.Admin.entity.CaseWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseWorkerRepo extends JpaRepository<CaseWorker,Integer> {



    public  CaseWorker findByCaseWorkerEmailId(String caseWorkerEmailId);
}

package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Prescription;
public interface PrescriptionRepository extends PagingAndSortingRepository<Prescription, Long>,JpaSpecificationExecutor<Prescription>{
}


package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Knowledge;
public interface KnowledgeRepository extends PagingAndSortingRepository<Knowledge, Long>,JpaSpecificationExecutor<Knowledge>{
}


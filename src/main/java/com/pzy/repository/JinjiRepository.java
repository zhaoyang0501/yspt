package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Jinji;
public interface JinjiRepository extends PagingAndSortingRepository<Jinji, Long>,JpaSpecificationExecutor<Jinji>{
}


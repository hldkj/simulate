package com.sft.simulate.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Created by yuyidi on 2019-04-15.
 * @desc
 */
@NoRepositoryBean
public interface JpaRepository<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {
}

package com.sft.simulate.repository;

import com.sft.simulate.entity.Trading;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Trading> {


    @Transactional
    @Modifying
    @Query("update Trading set status = ?1 where order_num = ?2")
    void updateStatusByOrderNum(Integer status,String orderNum);

    Optional<Trading> findByOrderNum(String orderNum);

}

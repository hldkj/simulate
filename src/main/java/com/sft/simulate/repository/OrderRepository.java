package com.sft.simulate.repository;

import com.sft.simulate.entity.Trading;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Trading> {


}

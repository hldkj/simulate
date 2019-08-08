package com.sft.simulate.repository;

import com.sft.simulate.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order> {
}

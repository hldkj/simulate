package com.sft.simulate.repository;

import com.sft.simulate.entity.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods> {


}

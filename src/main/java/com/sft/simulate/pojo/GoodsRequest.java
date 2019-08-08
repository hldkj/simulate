package com.sft.simulate.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class GoodsRequest {

   // @JSONField(name = "goods_id")
    private Long goods_id;

    private String name;

    //private String sn;

    private BigDecimal price;

    private String brief;



}

package com.sft.simulate.pojo.query.order;

import com.sft.simulate.pojo.query.PageAndSortQuery;
import lombok.Data;

@Data
public class OrderQuery extends PageAndSortQuery {

    private String orderNum;

    private String username;

    private String goodsname;

}

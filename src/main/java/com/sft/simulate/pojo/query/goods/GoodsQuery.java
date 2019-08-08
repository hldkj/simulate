package com.sft.simulate.pojo.query.goods;

import com.sft.simulate.pojo.query.PageAndSortQuery;
import lombok.Data;

@Data
public class GoodsQuery extends PageAndSortQuery {

    private String name;

    private String price;

}

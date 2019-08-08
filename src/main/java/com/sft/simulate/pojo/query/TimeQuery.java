package com.sft.simulate.pojo.query;

import lombok.Data;

import java.util.Date;

/**
 * @author Created by yuyidi on 2019-04-21.
 * @desc
 */
@Data
public class TimeQuery extends PageAndSortQuery {

    protected Date start;
    protected Date end;
}

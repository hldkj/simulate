package com.sft.simulate.pojo.query.member;

import com.sft.simulate.pojo.query.PageAndSortQuery;
import lombok.Data;

/**
 * @author Created by yuyidi on 2019-08-08.
 * @desc
 */
@Data
public class MemberQuery extends PageAndSortQuery {

    private String name;

}

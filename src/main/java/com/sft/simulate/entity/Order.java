package com.sft.simulate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String orderNum;

    @Column
    private String userName;

    @Column
    private String goodsName;

    @Column
    private BigDecimal price;

    @Column
    private Date payTime;

    @Column
    private Date createTime;

    @Column
    private Integer payStatus;

    @Column
    private String mobile;

}

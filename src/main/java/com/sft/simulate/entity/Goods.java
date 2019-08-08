package com.sft.simulate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Table
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Goods {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private String brief;

    @Column
    private Date addDate;

}

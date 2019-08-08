package com.sft.simulate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private int state;

    @Column
    private String cookie;

    @Column
    private Date createTime;

    @Column
    private String mobile;

}

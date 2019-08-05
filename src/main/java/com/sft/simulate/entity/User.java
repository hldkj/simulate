package com.sft.simulate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * @author Created by yuyidi on 2019-06-22.
 * @desc
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user")}, inverseJoinColumns =
            {@JoinColumn(name = "role")})
    private List<Role> roles;

}

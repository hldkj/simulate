package com.sft.simulate.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Created by yuyidi on 2019-04-23.
 * @desc
 */
@Getter
@Setter
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String code;
    @Column
    private String description;

    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role")}, inverseJoinColumns =
            {@JoinColumn(name = "permission")})
    private List<Permission> permissions;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    private List<User> users;
}

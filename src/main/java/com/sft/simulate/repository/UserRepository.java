package com.sft.simulate.repository;

import com.sft.simulate.entity.User;

import java.util.Optional;

/**
 * @author Created by yuyidi on 2019-08-05.
 * @desc
 */
public interface UserRepository extends JpaRepository<User> {

    Optional<User> findByUsername(String username);
}

package com.sft.simulate.repository;

import com.sft.simulate.entity.Member;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member>{


    Optional<Member> findById(Integer id);

    @Transactional
    @Modifying
    @Query("update Member set cookie = ?1 where id = ?2")
    void updateCookieById(String cookie,Long id);
}

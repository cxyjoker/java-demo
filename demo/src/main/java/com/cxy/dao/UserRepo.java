package com.cxy.dao;

import com.cxy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


/**
 * @ClassName UserRepo
 * @Author chenxiangyu-001
 * @Date 2018/10/30
 * @Version 1.0
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User getOne(Long id);

    User saveAndFlush(User user);

    @Query("select t from User t where t.name=:name")
    User findUserByName(@Param("name") String name);
}

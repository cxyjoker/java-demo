package com.cxy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @ClassName User
 * @Author chenxiangyu-001
 * @Date 2018/10/30
 * @Version 1.0
 */
@Entity
@Table(name="t_user")
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id="+id+",name="+name;
    }
}

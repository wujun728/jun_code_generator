package com.jun.plugin.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jun.plugin.base.model.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> findAll();
}
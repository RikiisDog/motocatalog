package com.example.motocatalog.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.example.motocatalog.beans.User;

@Mapper
public interface UserMapper {

    public User selectByUsername(String username);

}

package com.example.demo.mapper;

import com.example.demo.dao.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TypeMapper {
    void addType(String type);

    String selectName(Integer typeId);

    Type selectByName(String name);

    Integer selectNewId();
}

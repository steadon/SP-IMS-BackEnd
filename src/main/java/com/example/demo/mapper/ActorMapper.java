package com.example.demo.mapper;

import com.example.demo.dao.pojo.Actor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActorMapper {
    List<Actor> selectActor(Integer programId);

    Actor selectByName(String name);

    Integer selectNewId();

    Integer addActor(String name);
}

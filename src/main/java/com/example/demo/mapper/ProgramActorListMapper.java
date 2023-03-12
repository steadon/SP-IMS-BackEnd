package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramActorListMapper {
    Integer addOne(Integer programId, Integer actorId);

    Integer deleteOne(Integer id);
}

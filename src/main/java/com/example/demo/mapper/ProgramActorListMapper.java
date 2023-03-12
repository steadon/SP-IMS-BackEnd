package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramActorListMapper {
    void addOne(Integer programId, Integer actorId);

    void deleteOne(Integer id);
}

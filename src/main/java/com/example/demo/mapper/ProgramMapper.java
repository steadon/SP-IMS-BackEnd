package com.example.demo.mapper;

import com.example.demo.dao.result.ProgramResult;
import com.example.demo.dao.result.ProgramSearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProgramMapper {
    List<ProgramResult> selectAllByPage(int pageStart, int pageSize);

    List<ProgramSearchResult> selectAll();

    Integer add(Integer typeId, String name, String point);

    Integer selectNewId();

    void deleteOne(Integer id);
}

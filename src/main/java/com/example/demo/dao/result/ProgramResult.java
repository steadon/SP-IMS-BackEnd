package com.example.demo.dao.result;

import com.example.demo.dao.pojo.Actor;
import lombok.Data;

import java.util.List;

@Data
public class ProgramResult {
    private Integer id;
    private String typeName;
    private String name;
    private String view;

    private String actorList;
}

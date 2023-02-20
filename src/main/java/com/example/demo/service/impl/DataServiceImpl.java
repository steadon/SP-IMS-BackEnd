package com.example.demo.service.impl;

import com.example.demo.dao.CommonResult;
import com.example.demo.dao.param.AddParam;
import com.example.demo.dao.pojo.Actor;
import com.example.demo.dao.result.ProgramResult;
import com.example.demo.mapper.ActorMapper;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.service.DataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Resource
    ProgramMapper programMapper;

    @Resource
    ActorMapper actorMapper;


    @Override
    public CommonResult getProgramList(Integer pageNum) {

        int pageSize = 5;
        int pageStart = (pageNum - 1) * pageSize;

        List<ProgramResult> programResults = programMapper.selectAllByPage(pageStart, pageSize);

        for (ProgramResult programResult : programResults) {
            StringBuilder actors = new StringBuilder();
            for (Actor actor : actorMapper.selectActor(programResult.getId())) {
                if (actors.length() != 0) actors.append(",");
                actors.append(actor.getName());

            }

            programResult.setActorList(actors.toString());
        }

        return CommonResult.success(programResults);
    }

    @Override
    public CommonResult search(Integer typeId, Integer num, String name) {
        return null;
    }

    @Override
    public CommonResult addProgram(AddParam param) {
        return null;
    }

    @Override
    public CommonResult delete() {
        return null;
    }


}

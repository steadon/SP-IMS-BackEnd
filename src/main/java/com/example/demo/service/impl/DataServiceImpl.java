package com.example.demo.service.impl;

import com.example.demo.dao.CommonResult;
import com.example.demo.dao.param.AddParam;
import com.example.demo.dao.pojo.Actor;
import com.example.demo.dao.result.ProgramResult;
import com.example.demo.dao.result.ProgramResultList;
import com.example.demo.dao.result.ProgramSearchResult;
import com.example.demo.dao.result.ProgramSearchResultList;
import com.example.demo.mapper.ActorMapper;
import com.example.demo.mapper.ProgramActorListMapper;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.mapper.TypeMapper;
import com.example.demo.service.DataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    @Resource
    ProgramMapper programMapper;

    @Resource
    ActorMapper actorMapper;

    @Resource
    TypeMapper typeMapper;
    @Resource
    ProgramActorListMapper programActorListMapper;

    @Override
    public CommonResult<ProgramResultList> getProgramList(Integer pageNum) {
        int pageSize = 5;
        int pageStart = (pageNum - 1) * pageSize;

        List<ProgramResult> programResults = programMapper.selectAllByPage(pageStart, pageSize);

        for (ProgramResult programResult : programResults) {
            actorIn(programResult);
        }
        return CommonResult.success(new ProgramResultList(programResults));
    }

    @Override
    public CommonResult<ProgramSearchResultList> search(Integer typeId, Integer num, String name) {
        List<ProgramSearchResult> programSearchResults = programMapper.selectAll();
        String s = typeMapper.selectName(typeId);

        List<ProgramSearchResult> collect = programSearchResults.stream().filter(a -> a.getName().contains(name) || a.getNum().equals(num) || a.getTypeName().equals(s)).collect(Collectors.toList());
        for (ProgramResult programResult : collect) {
            actorIn(programResult);
        }
        return CommonResult.success(new ProgramSearchResultList(collect));
    }

    @Override
    public CommonResult<String> addProgram(AddParam param) {
        String type = param.getType();
        String name = param.getName();
        String actors = param.getActors();
        String point = param.getPoint();
        if (typeMapper.selectByName(name) == null) typeMapper.addType(type);
        Integer typeId = typeMapper.selectNewId();
        programMapper.add(typeId, name, point);
        Integer programId = programMapper.selectNewId();
        for (String s : actors.split(",")) {
            if (actorMapper.selectByName(s) == null) actorMapper.addActor(s);
            Integer id = actorMapper.selectByName(s).getId();
            programActorListMapper.addOne(programId, id);
        }
        return CommonResult.success("添加成功");
    }

    @Override
    public CommonResult<String> delete(Integer id) {
        programActorListMapper.deleteOne(id);
        programMapper.deleteOne(id);
        return CommonResult.success("删除成功");
    }

    public void actorIn(ProgramResult programResult) {
        StringBuilder actors = new StringBuilder();
        for (Actor actor : actorMapper.selectActor(programResult.getId())) {
            if (actors.length() != 0) actors.append(",");
            actors.append(actor.getName());
        }
        programResult.setActorList(actors.toString());
    }
}

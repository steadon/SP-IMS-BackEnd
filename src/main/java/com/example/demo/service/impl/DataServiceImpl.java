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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    //按页数缓存结果
    @Cacheable(cacheNames = "programList", key = "#pageNum")
    public CommonResult<ProgramResultList> getProgramList(Integer pageNum) {
        int pageSize = 5;
        int pageStart = (pageNum - 1) * pageSize;
        //分页查询
        List<ProgramResult> programResults = programMapper.selectAllByPage(pageStart, pageSize);

        for (ProgramResult programResult : programResults) {
            //装配actor
            actorIn(programResult);
        }
        return CommonResult.success(new ProgramResultList(programResults));
    }

    @Override
    public CommonResult<ProgramSearchResultList> search(Integer typeId, Integer num, String name) {
        //查询全部节目
        List<ProgramSearchResult> programSearchResults = programMapper.selectAll();
        String s = typeMapper.selectName(typeId);
        //使用stream流过滤不满足条件的结果
        List<ProgramSearchResult> collect = programSearchResults
                .stream()
                //使用contains代替SQL中的like（like有SQl注入风险）
                .filter(a -> a.getName().contains(name) || a.getNum().equals(num) || a.getTypeName().equals(s))
                .collect(Collectors.toList());

        for (ProgramResult programResult : collect) {
            //装配actor
            actorIn(programResult);
        }
        return CommonResult.success(new ProgramSearchResultList(collect));
    }

    @Override
    //数据更新，清除缓存
    @CacheEvict(cacheNames = "programList", allEntries = true)
    public CommonResult<String> addProgram(AddParam param) {
        //参数列表
        String type = param.getType();
        String name = param.getName();
        String actors = param.getActors();
        String point = param.getPoint();
        //添加类型
        if (typeMapper.selectByName(name) == null) {
            if (typeMapper.addType(type) == 0) {
                log.info("添加类型失败");
                return CommonResult.fail("添加类型失败");
            }
        }
        //添加节目
        Integer typeId = typeMapper.selectNewId();
        if (programMapper.add(typeId, name, point) == 0) {
            log.info("添加节目失败");
            return CommonResult.fail("添加节目失败");
        }
        //添加演员
        Integer programId = programMapper.selectNewId();
        for (String s : actors.split(",")) {
            if (actorMapper.selectByName(s) == null) {
                if (actorMapper.addActor(s) == 0) {
                    log.info("添加演员失败");
                    return CommonResult.fail("添加演员失败");
                }
            }
            //添加关联
            Integer id = actorMapper.selectByName(s).getId();
            if (programActorListMapper.addOne(programId, id) == 0) {
                log.info("添加关联失败");
                return CommonResult.fail("添加关联失败");
            }
        }
        log.info("添加节目" + param.getName() + "成功");
        return CommonResult.success("添加成功");
    }

    @Override
    //数据更新，清除缓存
    @CacheEvict(cacheNames = "programList", allEntries = true)
    public CommonResult<String> delete(Integer id) {
        //删除关联
        if (programActorListMapper.deleteOne(id) == 0) {
            log.info("删除关联失败");
            return CommonResult.fail("删除关联失败");
        }
        //删除节目
        if (programMapper.deleteOne(id) == 0) {
            log.info("删除节目失败");
            return CommonResult.fail("删除节目失败");
        }
        log.info("删除节目成功，programId=" + id);
        return CommonResult.success("删除成功");
    }

    public void actorIn(ProgramResult programResult) {
        StringBuilder actors = new StringBuilder();
        for (Actor actor : actorMapper.selectActor(programResult.getId())) {
            //用逗号拼接演员名称
            if (actors.length() != 0) actors.append(",");
            actors.append(actor.getName());
        }
        programResult.setActorList(actors.toString());
    }
}

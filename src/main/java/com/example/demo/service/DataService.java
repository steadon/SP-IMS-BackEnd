package com.example.demo.service;

import com.example.demo.dao.CommonResult;
import com.example.demo.dao.param.AddParam;
import com.example.demo.dao.result.ProgramResultList;
import com.example.demo.dao.result.ProgramSearchResultList;

public interface DataService {
    CommonResult<ProgramResultList> getProgramList(Integer pageNum);

    CommonResult<ProgramSearchResultList> search(Integer typeId, Integer num, String name);

    CommonResult<String> addProgram(AddParam param);

    CommonResult<String> delete(Integer id);
}

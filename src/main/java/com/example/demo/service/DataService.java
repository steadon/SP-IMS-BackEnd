package com.example.demo.service;

import com.example.demo.dao.CommonResult;
import com.example.demo.dao.param.AddParam;

public interface DataService {
    CommonResult getProgramList(Integer pageNum);

    CommonResult search(Integer typeId, Integer num, String name);

    CommonResult addProgram(AddParam param);

    CommonResult delete();
}

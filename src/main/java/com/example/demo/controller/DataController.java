package com.example.demo.controller;

import com.example.demo.dao.CommonResult;
import com.example.demo.dao.param.AddParam;
import com.example.demo.dao.param.DeleteParam;
import com.example.demo.dao.result.ProgramResultList;
import com.example.demo.dao.result.ProgramSearchResultList;
import com.example.demo.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Validated
@CrossOrigin
@RestController
public class DataController {


    final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("/get/programList")
    public CommonResult<ProgramResultList> getProgramList(@RequestParam Integer pageNum, HttpServletRequest request) {
        log.info(request.getRequestURI());
        return dataService.getProgramList(pageNum);
    }

    @GetMapping("/get/search")
    public CommonResult<ProgramSearchResultList> search(@RequestParam(required = false, defaultValue = "0") Integer type, @RequestParam(required = false, defaultValue = "0") Integer num, @RequestParam(required = false, defaultValue = "") String name, HttpServletRequest request) {
        log.info(request.getRequestURI());
        return dataService.search(type, num, name);
    }

    @PostMapping("/add/program")
    public CommonResult<String> addProgram(@RequestBody AddParam param, HttpServletRequest request) {
        log.info(request.getRequestURI());
        return dataService.addProgram(param);
    }

    @PostMapping("/delete/program")
    public CommonResult<String> delete(@RequestBody DeleteParam param, HttpServletRequest request) {
        log.info(request.getRequestURI());
        return dataService.delete(param.getId());
    }

}

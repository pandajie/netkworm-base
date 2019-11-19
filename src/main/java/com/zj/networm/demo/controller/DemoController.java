package com.zj.networm.demo.controller;

import com.zj.networm.common.exception.BizException;
import com.zj.networm.common.result.ResponseVO;
import com.zj.networm.demo.domain.DemoDomain;
import com.zj.networm.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/demo")
@Api
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/list/all",method = RequestMethod.GET)
    @ApiOperation("查询所有数据")
    public ResponseVO<List<DemoDomain>> findAll(String a){
        return ResponseVO.success(demoService.findAll());
    }
}

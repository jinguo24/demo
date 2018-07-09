package com.simple.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.simple.common.rest.Result;
import com.simple.common.rest.ResultData;
import com.simple.domain.po.DataInfo;
import com.simple.service.DataInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
@RestController
@RequestMapping("dataInfo")
public class DataInfoController extends BaseController
{
	@Autowired
    private DataInfoService dataInfoService;

    private Logger logger = Logger.getLogger(DataInfoController.class);

    @GetMapping("list")
        @ApiImplicitParams({
    	  @ApiImplicitParam(name="pageNum",value="页数",dataType="int", paramType = "query",required=true),
    	  @ApiImplicitParam(name="pageSize",value="每页条数",dataType="int", paramType = "query",required=true)})
    public ResultData list(@ModelAttribute DataInfo dataInfo,Integer pageNum, Integer pageSize) {
    	if (null == dataInfo) dataInfo = new DataInfo();
        final PageInfo<DataInfo> page = dataInfoService.listAsPage(dataInfo, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    public ResultData add(@ModelAttribute DataInfo dataInfo) {
        //Assert.notNull(dataInfo.getName(), "角色名不能为空");
        //Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        dataInfoService.saveOrUpdate(dataInfo);
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(@ModelAttribute  DataInfo dataInfo) {
        dataInfoService.saveOrUpdate(dataInfo);
        return new ResultData();
    }

    @GetMapping("/del")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData delete(String id) {
        dataInfoService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }
	
	
	
}

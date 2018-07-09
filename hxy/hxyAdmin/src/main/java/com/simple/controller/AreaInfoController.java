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
import com.simple.domain.po.AreaInfo;
import com.simple.service.AreaInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
@RestController
@RequestMapping("areaInfo")
public class AreaInfoController extends BaseController
{
	@Autowired
    private AreaInfoService areaInfoService;

    private Logger logger = Logger.getLogger(AreaInfoController.class);

    @GetMapping("list")
        @ApiImplicitParams({
    	  @ApiImplicitParam(name="pageNum",value="页数",dataType="int", paramType = "query",required=true),
    	  @ApiImplicitParam(name="pageSize",value="每页条数",dataType="int", paramType = "query",required=true)})
    public ResultData list(@ModelAttribute AreaInfo areaInfo,Integer pageNum, Integer pageSize) {
    	if (null == areaInfo) areaInfo = new AreaInfo();
        final PageInfo<AreaInfo> page = areaInfoService.listAsPage(areaInfo, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    public ResultData add(@ModelAttribute AreaInfo areaInfo) {
        //Assert.notNull(areaInfo.getName(), "角色名不能为空");
        //Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        areaInfoService.saveOrUpdate(areaInfo);
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(@ModelAttribute  AreaInfo areaInfo) {
        areaInfoService.saveOrUpdate(areaInfo);
        return new ResultData();
    }

    @GetMapping("/del")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData delete(String id) {
        areaInfoService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }
	
	
	
}

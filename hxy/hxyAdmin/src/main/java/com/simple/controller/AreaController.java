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
import com.simple.domain.po.Area;
import com.simple.service.AreaService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("area")
public class AreaController extends BaseController
{
	@Autowired
    private AreaService areaService;

    private Logger logger = Logger.getLogger(AreaController.class);

    @GetMapping("list")
        @ApiImplicitParams({
    	  @ApiImplicitParam(name="pageNum",value="页数",dataType="int", paramType = "query",required=true),
    	  @ApiImplicitParam(name="pageSize",value="每页条数",dataType="int", paramType = "query",required=true)})
    public ResultData list(@ModelAttribute Area area,Integer pageNum, Integer pageSize) {
    	if (null == area) area = new Area();
        final PageInfo<Area> page = areaService.listAsPage(area, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    public ResultData add(@ModelAttribute Area area) {
        //Assert.notNull(area.getName(), "角色名不能为空");
        //Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        areaService.saveOrUpdate(area);
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(@ModelAttribute  Area area) {
        areaService.saveOrUpdate(area);
        return new ResultData();
    }

    @GetMapping("/del")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData delete(String id) {
        areaService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }
	
	
	
}

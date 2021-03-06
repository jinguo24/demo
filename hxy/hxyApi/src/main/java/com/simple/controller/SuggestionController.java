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
import com.simple.domain.po.Suggestion;
import com.simple.service.SuggestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("suggestion")
@Api(description="投诉建议相关接口")
public class SuggestionController extends BaseController
{
	@Autowired
    private SuggestionService suggestionService;

    private Logger logger = Logger.getLogger(SuggestionController.class);

    @GetMapping("list")
        @ApiImplicitParams({
    	  @ApiImplicitParam(name="pageNum",value="页数",dataType="int", paramType = "query",required=true),
    	  @ApiImplicitParam(name="pageSize",value="每页条数",dataType="int", paramType = "query",required=true)})
    public ResultData list(@ModelAttribute Suggestion suggestion,Integer pageNum, Integer pageSize) {
    	if (null == suggestion) suggestion = new Suggestion();
        final PageInfo<Suggestion> page = suggestionService.listAsPage(suggestion, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    @ApiOperation("新增投诉建议接口")
    public ResultData add(@ModelAttribute Suggestion suggestion) {
        //Assert.notNull(suggestion.getName(), "角色名不能为空");
        //Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        suggestionService.saveOrUpdate(suggestion);
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(@ModelAttribute  Suggestion suggestion) {
        suggestionService.saveOrUpdate(suggestion);
        return new ResultData();
    }

    @GetMapping("/del")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData delete(String id) {
        suggestionService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }
	
	
	
}

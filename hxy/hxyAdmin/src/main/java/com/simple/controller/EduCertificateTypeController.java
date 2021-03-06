package com.simple.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.simple.common.rest.Result;
import com.simple.common.rest.ResultData;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;
import com.simple.domain.po.EduCertificateType;
import com.simple.service.EduCertificateTypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("eduCertificateType")
public class EduCertificateTypeController extends BaseController
{
	@Autowired
    private EduCertificateTypeService eduCertificateTypeService;

    private Logger logger = Logger.getLogger(EduCertificateTypeController.class);

    @GetMapping("list")
        @ApiImplicitParams({
    	  @ApiImplicitParam(name="pageNum",value="页数",dataType="int", paramType = "query",required=true),
    	  @ApiImplicitParam(name="pageSize",value="每页条数",dataType="int", paramType = "query",required=true)})
    public ResultData list(@ModelAttribute EduCertificateType eduCertificateType,Integer pageNum, Integer pageSize) {
    	if (null == eduCertificateType) eduCertificateType = new EduCertificateType();
        final PageInfo<EduCertificateType> page = eduCertificateTypeService.listAsPage(eduCertificateType, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    public ResultData add(@ModelAttribute EduCertificateType eduCertificateType) {
        //Assert.notNull(eduCertificateType.getName(), "角色名不能为空");
        //Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        eduCertificateTypeService.saveOrUpdate(eduCertificateType);
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(@ModelAttribute  EduCertificateType eduCertificateType) {
        eduCertificateTypeService.saveOrUpdate(eduCertificateType);
        return new ResultData();
    }

    @GetMapping("/del")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData delete(String id) {
        eduCertificateTypeService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }
	
	
	
}

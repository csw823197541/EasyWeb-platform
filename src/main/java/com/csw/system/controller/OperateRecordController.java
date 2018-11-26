package com.csw.system.controller;

import com.csw.common.base.PageResult;
import com.csw.system.entity.Authority;
import com.csw.system.service.OperateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by csw on 2018/11/25.
 * Description:
 */
@Api(value = "操作日志", tags = "operateRecords")
@RestController
@RequestMapping("/sys/operateRecords")
public class OperateRecordController {

    @Autowired
    private OperateRecordService operateRecordService;

    @ApiOperation(value = "查询所有操作日志(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "keyword", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/queryByPage")
    public PageResult<Authority> queryByPage(Integer page, Integer limit, String keyword) {
        if (page == null) {
            page = 0;
            limit = 10;
        } else {
            page = page - 1;
        }
        return operateRecordService.queryByPage(page, limit, keyword);
    }
}

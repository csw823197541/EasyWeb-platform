package com.csw.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csw.common.base.JsonResult;
import com.csw.common.base.PageResult;
import com.csw.system.entity.Authority;
import com.csw.system.param.AuthSyncParam;
import com.csw.system.param.AuthorityParam;
import com.csw.system.service.AuthorityService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(value = "权限管理", tags = "authorities")
@RestController
@RequestMapping("/sys/authorities")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @ApiOperation(value = "添加权限")
    @ApiImplicitParam(name = "param", value = "权限信息", required = true, dataType = "AuthorityParam")
    @PostMapping()
    public JsonResult create(@RequestBody AuthorityParam param) {
        authorityService.create(param);
        return JsonResult.ok();
    }

    @ApiOperation(value = "修改权限")
    @ApiImplicitParam(name = "param", value = "权限信息", required = true, dataType = "AuthorityParam")
    @PutMapping()
    public JsonResult update(@RequestBody AuthorityParam param) {
        authorityService.update(param);
        return JsonResult.ok();
    }

    @ApiOperation(value = "同步权限")
    @ApiImplicitParam(name = "param", value = "权限列表", required = true, dataType = "AuthSyncParam")
    @PostMapping("/sync")
    public JsonResult sync(@RequestBody AuthSyncParam param) {
        try {
            List<Authority> authorityList = Lists.newArrayList();
            JSONObject jsonObject = JSON.parseObject(param.getJson());
            JSONObject paths = jsonObject.getJSONObject("paths");
            Set<String> pathsKeys = paths.keySet();
            for (String pathKey : pathsKeys) {
                JSONObject apiObject = paths.getJSONObject(pathKey);
                Set<String> apiKeys = apiObject.keySet();
                for (String apiKey : apiKeys) {
                    JSONObject methodObject = apiObject.getJSONObject(apiKey);
                    Authority authority = new Authority();
                    authority.setAuthorityUrl(apiKey + ":" + pathKey);
                    authority.setAuthorityName(methodObject.getString("summary"));
                    authority.setParentMenuName(methodObject.getJSONArray("tags").getString(0));
                    authorityList.add(authority);
                }
            }
            authorityService.sync(authorityList);
            return JsonResult.ok("同步成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("同步失败");
        }
    }

    @ApiOperation(value = "查询所有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("/query")
    public PageResult<Authority> query(String keyword) {
        return authorityService.query(keyword);
    }

    @ApiOperation(value = "查询所有权限(分页)")
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
        return authorityService.queryByPage(page, limit, keyword);
    }

    @ApiOperation(value = "查询所有菜单权限")
    @GetMapping("/menuAuth")
    public JsonResult menuAuth() {
        return JsonResult.ok().put("data", authorityService.findMenuAuth());
    }

}

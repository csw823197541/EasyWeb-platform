package com.csw.system.controller;

import com.csw.common.base.JsonResult;
import com.csw.common.base.PageResult;
import com.csw.common.constant.StatusCode;
import com.csw.common.utils.JSONUtil;
import com.csw.system.entity.Role;
import com.csw.system.param.RoleParam;
import com.csw.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "角色管理", tags = "role")
@RestController
@RequestMapping("/sys/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询所有角色")
    @GetMapping()
    public JsonResult findAll() {
        List<Role> roleList = roleService.findAll(StatusCode.NORMAL.getCode());
        return JsonResult.ok().put("data", roleList);
    }

    @ApiOperation(value = "查询所有角色（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("/query")
    public PageResult<Role> query(String keyword) {
        return roleService.query(StatusCode.NORMAL.getCode(), keyword);
    }

    @ApiOperation(value = "添加角色")
    @ApiImplicitParam(name = "param", value = "角色信息", required = true, dataType = "RoleParam")
    @PostMapping()
    public JsonResult create(@RequestBody RoleParam param) {
        roleService.create(param);
        return JsonResult.ok("添加成功");
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParam(name = "param", value = "角色信息", required = true, dataType = "RoleParam")
    @PutMapping()
    public JsonResult update(@RequestBody RoleParam param) {
        roleService.update(param);
        return JsonResult.ok("修改成功！");
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "Integer")
    @DeleteMapping()
    public JsonResult delete(Integer id) {
        roleService.delete(id, StatusCode.DELETE.getCode());
        return JsonResult.ok("删除成功");
    }

    @ApiOperation(value = "角色拥有的权限(树)")
    @ApiImplicitParam(name = "param", value = "角色id", required = true, dataType = "RoleParam")
    @PostMapping("/authTree")
    public JsonResult findAuthTree(@RequestBody RoleParam param) {
        return JsonResult.ok().put("data", roleService.findAuthTree(param));
    }

    @ApiOperation(value = "绑定权限")
    @ApiImplicitParam(name = "param", value = "角色id", required = true, dataType = "RoleParam")
    @PostMapping("/updateRoleAuth")
    public JsonResult updateRoleAuth(@RequestBody RoleParam param) {
        roleService.updateRoleAuth(param.getRoleId(), JSONUtil.parseIntArray(param.getAuthIds()));
        return JsonResult.ok();
    }
}

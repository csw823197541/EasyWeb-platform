package com.csw.system.controller;

import com.csw.common.base.BaseComponent;
import com.csw.common.base.JsonResult;
import com.csw.common.base.PageResult;
import com.csw.common.constant.StatusCode;
import com.csw.system.entity.User;
import com.csw.system.param.UserParam;
import com.csw.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Api(value = "用户管理", tags = "user")
@RestController
@RequestMapping("/sys/users")
public class UserController extends BaseComponent {

    @Autowired
    private UserService userService;

    /**
     * 这里参数过多，并且参数含有中文，建议用post请求，用restful风格解决不了需求时，建议不要强行使用restful
     * 加了一个/query是避免跟添加用户接口冲突
     */
    @ApiOperation(value = "查询所有用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/query")
    public PageResult<User> query(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 10;
        } else {
            page = page - 1;
        }
        return userService.query(page, limit, StatusCode.NORMAL.getCode(), searchKey, searchValue);
    }

    @ApiOperation(value = "获取个人信息")
    @GetMapping("/userInfo")
    public JsonResult userInfo() {
        return JsonResult.ok().put("user", getLoginUser());
    }

    @ApiOperation(value = "添加用户", notes = "")
    @ApiImplicitParam(name = "param", value = "用户信息", required = true, dataType = "UserParam")
    @PostMapping()
    public JsonResult create(@RequestBody UserParam param) {
        userService.create(param);
        return JsonResult.ok("添加成功");
    }

    @ApiOperation(value = "修改用户", notes = "")
    @ApiImplicitParam(name = "param", value = "用户信息", required = true, dataType = "UserParam")
    @PutMapping()
    public JsonResult update(@RequestBody UserParam param) {
        userService.update(param);
        return JsonResult.ok("修改成功");
    }

    @ApiOperation(value = "修改用户状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "state", value = "状态：0正常，1冻结", required = false, dataType = "Integer")
    })
    @PutMapping("/state")
    public JsonResult updateState(Integer userId, Integer state) {
        userService.updateState(userId, state);
        return JsonResult.ok();
    }

    @ApiOperation(value = "修改自己密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPsw", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPsw", value = "新密码", required = true, dataType = "String")
    })
    @PutMapping("/psw")
    public JsonResult updatePsw(String oldPsw, String newPsw) {
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(oldPsw);
        userService.updatePsw(getLoginUserId(), newPsw);
        return JsonResult.ok("修改成功");
    }

    @ApiOperation(value = "分配角色", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "roleId", value = "角色id(逗号隔开)", required = true, dataType = "String")
    })
    @PutMapping("/assignRole")
    public JsonResult resetPsw(Integer userId, String roleId) {
        userService.assignRole(userId, roleId);
        return JsonResult.ok("分配角色成功");
    }
}

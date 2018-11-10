package com.csw.system.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class RoleParam {

    private Integer id;

    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String roleName;

    private String comments;

    private Integer roleId;
    private String authIds;

}
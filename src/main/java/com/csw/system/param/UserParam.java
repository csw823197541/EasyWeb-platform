package com.csw.system.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserParam {

    private Integer id;

    @Length(min = 1, max = 50, message = "角色名称长度需要在1-50个字之间")
    private String username;

    @Length(min = 1, max = 50, message = "角色名称长度需要在1-50个字之间")
    private String nickName;

    private String sex;

    private String phone;

    private Integer state; //状态：0正常，1冻结

    private String roleId; //用户角色id，多个用','分割
}

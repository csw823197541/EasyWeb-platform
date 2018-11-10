package com.csw.system.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Created by csw on 2018/9/14.
 * Description:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorityParam {

    private Integer id;

    private String authorityUrl;

    @Length(min = 2, max = 20, message = "权限名称长度需要在2-20个字之间")
    private String authorityName;

    private String menuUrl;

    private Integer menuType;

    private String menuIcon;

    @Builder.Default
    private Integer orderNumber = 0;

    private Integer parentId;
}

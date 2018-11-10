package com.csw.system.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_authority")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -6058060376656180793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String authorityUrl;

    private String authorityName;

    private String menuUrl;

    private Integer menuType;

    private String menuIcon;

    private Integer orderNumber;

    private Integer parentId;

    @Transient
    private String parentMenuName;

    private String operator;

    private Date createTime;

    private Date updateTime;

    @Override
    public String getAuthority() {
        return this.authorityUrl;
    }
}

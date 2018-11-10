package com.csw.system.entity;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sys_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 242146703513492331L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String avatar;

    private String sex;

    private String phone;

    private String email;

    private Integer emailVerified;

    private Integer state;

    private String operator;

    private Date createTime;

    private Date updateTime;

    @Transient
    private List<Authority> authorities = Lists.newArrayList();  //权限
    @Transient
    private List<Role> roles = Lists.newArrayList();  //角色

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  //账户是否未过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.state != 1;  //账户是否未锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  //凭证(密码)是否未过期
    }

    @Override
    public boolean isEnabled() {
        return true;  //用户是否启用
    }
}

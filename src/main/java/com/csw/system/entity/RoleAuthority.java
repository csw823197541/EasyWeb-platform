package com.csw.system.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_role_authority")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleAuthority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="authority_id")
    private Authority authority;

    private String operator;

    private Date createTime;

    private Date updateTime;

}
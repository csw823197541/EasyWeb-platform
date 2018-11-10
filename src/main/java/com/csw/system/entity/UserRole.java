package com.csw.system.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_user_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="role_id")
    private Role role;

    private String operator;

    private Date createTime;

    private Date updateTime;

}
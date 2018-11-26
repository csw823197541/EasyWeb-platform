package com.csw.system.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_operate_record")
@Data
public class OperateRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String serverName; // 系统模块

    private String operateType; // 操作类型

    private String operateAddress; // 操作Ip地址

    private String operateCity; // 操作地点

    private String requestUrl; // 请求url

    private String requestParam; // 请求参数

    private String responseMethod; // 接口方法

    private String operateStatus; // 操作状态

    private String operator; // 操作人员

    private Date createTime; // 操作时间

}
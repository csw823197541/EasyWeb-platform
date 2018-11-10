package com.csw.common.constant;

/**
 * Created by csw on 2018/3/28.
 * Description:
 */
public enum StatusCode {

    NORMAL(0, "正常"), DISABLE(1, "删除"), DELETE(2, "禁用");

    private int code;
    private String desc;

    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

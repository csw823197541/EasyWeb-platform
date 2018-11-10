package com.csw.common.constant;

/**
 * Created by csw on 2018/3/28.
 * Description:
 */
public enum MenuTypeCode {

    MENU(0, "菜单"), BUTTON(1, "按钮");

    private int code;
    private String desc;

    MenuTypeCode(int code, String desc) {
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

package com.csw.common.base;

import com.csw.system.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller基类
 * Created by csw on 2018-02-22 上午 11:29.
 */
public class BaseComponent {

    /**
     * 获取当前登录的user
     */
    protected User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object object = authentication.getPrincipal();
            if (object != null) {
                return (User) object;
            }
        }
        return null;
    }

    /**
     * 获取当前登录的userId
     */
    protected Integer getLoginUserId() {
        User loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getId();
    }

    /**
     * 获取当前登录的username
     */
    protected String getLoginUsername() {
        User loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUsername();
    }
}

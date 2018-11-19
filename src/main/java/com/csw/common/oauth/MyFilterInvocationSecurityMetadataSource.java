package com.csw.common.oauth;

import com.csw.common.constant.MenuTypeCode;
import com.csw.system.entity.Authority;
import com.csw.system.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by csw on 2018/11/18.
 * Description:
 */
@Service
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private AuthorityRepository authorityRepository;

    private Map<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<Authority> authorityList = (List<Authority>) authorityRepository.findAll();
        for (Authority authority : authorityList) {
            if (MenuTypeCode.BUTTON.getCode() == authority.getMenuType()) {
                array = new ArrayList<>();
                cfg = new SecurityConfig(authority.getAuthorityUrl());
                //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
                // 此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
                array.add(cfg);
                //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
                map.put(authority.getAuthorityUrl(), array);
            }
        }

    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String resUrl;
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            resUrl = iterator.next();
            String re = request.getMethod().toLowerCase() + ":" + request.getRequestURI();
            if (resUrl.matches(re)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

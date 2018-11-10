package com.csw.system.service;

import com.csw.common.base.BaseComponent;
import com.csw.common.base.PageResult;
import com.csw.common.constant.MenuTypeCode;
import com.csw.common.utils.BeanCopyUtil;
import com.csw.common.utils.BeanValidator;
import com.csw.common.utils.StringUtil;
import com.csw.system.entity.Authority;
import com.csw.system.param.AuthorityParam;
import com.csw.system.repository.AuthorityRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
@Service
public class AuthorityService extends BaseComponent {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional
    public void sync(List<Authority> authorityList) {
        int num = 0;
        for (Authority authority : authorityList) {
            Authority authorityParent = authorityRepository.findByAuthorityName(authority.getParentMenuName());
            if (authorityParent == null) {
                Authority authorityMenu = new Authority();
                authorityMenu.setAuthorityName(authority.getParentMenuName());
                authorityMenu.setMenuType(MenuTypeCode.MENU.getCode());
                authorityMenu.setOrderNumber(num++);
                authorityMenu.setParentId(-1);
                authorityMenu.setOperator(getLoginUsername());
                authorityParent = authorityRepository.save(authorityMenu);
            }
            Authority authorityNew = authorityRepository.findByAuthorityNameOrAuthorityUrl(authority.getAuthorityName(), authority.getAuthorityUrl());
            if (authorityNew != null) {
                authorityNew.setAuthorityName(authority.getAuthorityName());
                authorityNew.setAuthorityUrl(authority.getAuthorityUrl());
                authorityNew.setParentId(authorityParent.getId());
                authorityRepository.save(authorityNew);
            } else {
                authority.setMenuType(MenuTypeCode.BUTTON.getCode());
                authority.setOrderNumber(num++);
                authority.setParentId(authorityParent.getId());
                authority.setOperator(getLoginUsername());
                authorityRepository.save(authority);
            }
        }
    }

    public PageResult<Authority> query(String keyword) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderNumber");
        List<Authority> authorityList = (List<Authority>) authorityRepository.findAll(sort);
        return new PageResult<>(authorityList);
    }

    public List<Authority> findMenuAuth() {
        return authorityRepository.findAllByMenuType(MenuTypeCode.MENU.getCode());
    }

    public void create(AuthorityParam param) {
        BeanValidator.check(param);
        Authority authority = Authority.builder().build();
        authority = (Authority) BeanCopyUtil.copyBean(param, authority);
        authority.setAuthorityUrl(StringUtil.isNotBlank(param.getAuthorityUrl()) ? param.getAuthorityUrl() : null);
        authority.setOperator(getLoginUsername());
        authorityRepository.save(authority);
    }

    public void update(AuthorityParam param) {
        BeanValidator.check(param);
        Preconditions.checkNotNull(param.getId(), "修改记录Id不可以为null");
        Authority authority = authorityRepository.findById(param.getId()).get();
        Preconditions.checkNotNull(authority, "权限(id:" + param.getId() + ")不存在");
        authority.setAuthorityName(param.getAuthorityName());
        authority.setAuthorityUrl(StringUtil.isNotBlank(param.getAuthorityUrl()) ? param.getAuthorityUrl() : null);
        authority.setMenuUrl(param.getMenuUrl());
        authority.setMenuIcon(param.getMenuIcon());
        authority.setMenuType(param.getMenuType());
        authority.setOrderNumber(param.getOrderNumber());
        authority.setParentId(param.getParentId());
        authorityRepository.save(authority);
    }
}

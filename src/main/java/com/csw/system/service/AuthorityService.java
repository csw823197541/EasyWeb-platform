package com.csw.system.service;

import com.csw.common.base.BaseComponent;
import com.csw.common.base.PageResult;
import com.csw.common.constant.MenuTypeCode;
import com.csw.common.exception.BusinessException;
import com.csw.common.utils.BeanCopyUtil;
import com.csw.common.utils.BeanValidator;
import com.csw.common.utils.StringUtil;
import com.csw.system.entity.Authority;
import com.csw.system.entity.RoleAuthority;
import com.csw.system.param.AuthorityParam;
import com.csw.system.repository.AuthorityRepository;
import com.csw.system.repository.RoleAuthorityRepository;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
@Service
public class AuthorityService extends BaseComponent {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

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

    @Transactional
    public void delete(Integer id, int code) {
        Authority authority = getAuthorityById(id);
        List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findAllByAuthority(authority);
        if (roleAuthorityList.size() > 0) {
            StringBuilder str = new StringBuilder();
            for (RoleAuthority roleAuthority : roleAuthorityList) {
                str.append(roleAuthority.getRole().getRoleName()).append("、");
            }
            str.deleteCharAt(str.length() - 1);
            throw new BusinessException("该权限(id:" + id + ")已被角色(" + str.toString() + ")绑定，不能删除，请先解绑");
        }
        roleAuthorityRepository.deleteByAuthority(authority);
        authorityRepository.delete(authority);
    }

    public PageResult<Authority> query(String keyword) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderNumber");
        List<Authority> authorityList = (List<Authority>) authorityRepository.findAll(sort);
        return new PageResult<>(authorityList);
    }

    public PageResult<Authority> queryByPage(Integer page, Integer limit, String keyword) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderNumber");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Authority> authorityPage = authorityRepository.findAll(pageable);
        return new PageResult<>(authorityPage.getTotalElements(), authorityPage.getContent());
    }

    public List<Authority> findMenuAuth() {
        return authorityRepository.findAllByMenuType(MenuTypeCode.MENU.getCode());
    }

    @Transactional
    public void create(AuthorityParam param) {
        BeanValidator.check(param);
        Authority authority = Authority.builder().build();
        authority = (Authority) BeanCopyUtil.copyBean(param, authority);
        authority.setAuthorityUrl(StringUtil.isNotBlank(param.getAuthorityUrl()) ? param.getAuthorityUrl() : null);
        authority.setOperator(getLoginUsername());
        authorityRepository.save(authority);
    }

    @Transactional
    public void update(AuthorityParam param) {
        BeanValidator.check(param);
        Authority authority = getAuthorityById(param.getId());
        authority.setAuthorityName(param.getAuthorityName());
        authority.setAuthorityUrl(StringUtil.isNotBlank(param.getAuthorityUrl()) ? param.getAuthorityUrl() : null);
        authority.setMenuUrl(param.getMenuUrl());
        authority.setMenuIcon(param.getMenuIcon());
        authority.setMenuType(param.getMenuType());
        authority.setOrderNumber(param.getOrderNumber());
        authority.setParentId(param.getParentId());
        authorityRepository.save(authority);
    }

    private Authority getAuthorityById(Integer id) {
        Preconditions.checkNotNull(id, "更新权限信息，Id不可以为null");
        Authority authority = authorityRepository.findById(id).get();
        Preconditions.checkNotNull(authority, "待更新权限(id:" + id + ")不存在");
        return authority;
    }

}

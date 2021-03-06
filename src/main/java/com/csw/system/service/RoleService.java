package com.csw.system.service;

import com.csw.common.base.BaseComponent;
import com.csw.common.base.PageQuery;
import com.csw.common.base.PageResult;
import com.csw.common.constant.RoleTypeCode;
import com.csw.common.exception.BusinessException;
import com.csw.common.exception.ParameterException;
import com.csw.common.utils.BeanValidator;
import com.csw.common.utils.StringUtil;
import com.csw.system.entity.Authority;
import com.csw.system.entity.Role;
import com.csw.system.entity.RoleAuthority;
import com.csw.system.entity.UserRole;
import com.csw.system.param.RoleParam;
import com.csw.system.repository.AuthorityRepository;
import com.csw.system.repository.RoleAuthorityRepository;
import com.csw.system.repository.RoleRepository;
import com.csw.system.repository.UserRoleRepository;
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
import java.util.*;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
@Service
public class RoleService extends BaseComponent {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<Role> findAll(int code) {
        return (List<Role>) roleRepository.findAll();
    }

    public PageResult<Role> query(PageQuery pageQuery, int code, String keyword) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Pageable pageable = PageRequest.of(pageQuery.getPageNo(), pageQuery.getPageSize(), sort);
        Page<Role> rolePage;
        if (StringUtil.isNotBlank(keyword)) {
            Specification<Role> specification = (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicateList = Lists.newArrayList();
                predicateList.add(criteriaBuilder.like(root.get("roleName"), "%" + keyword + "%"));
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            };
            rolePage = roleRepository.findAll(specification, pageable);
        } else {
            rolePage = roleRepository.findAll(pageable);
        }
        List<Role> roleList = rolePage.getContent();
        return new PageResult<>(rolePage.getTotalElements(), roleList);
    }

    @Transactional
    public void create(RoleParam param) {
        BeanValidator.check(param);
        Role role = Role.builder().roleName(param.getRoleName()).comments(param.getComments()).roleType(RoleTypeCode.USER_ROLE.getType()).build();
        role.setOperator(getLoginUsername());
        roleRepository.save(role);
    }

    @Transactional
    public void update(RoleParam param) {
        Preconditions.checkNotNull(param.getId(), "修改记录Id不可以为null");
        Role role = roleRepository.findById(param.getId()).get();
        Preconditions.checkNotNull(role, "角色(id:" + param.getId() + ")不存在");
        role.setRoleName(param.getRoleName());
        role.setComments(param.getComments());
        role.setOperator(getLoginUsername());
        roleRepository.save(role);
    }

    @Transactional
    public void delete(Integer id, int code) {
        Role role = roleRepository.findById(id).get();
        Preconditions.checkNotNull(role, "角色(id:" + id + ")不存在");
        if (RoleTypeCode.ADMIN_ROLE.getType().equals(role.getRoleType())) {
            throw new ParameterException("对不起，管理员角色不能删除");
        } else if (RoleTypeCode.DEFAULT_ROLE.getType().equals(role.getRoleType())) {
            throw new ParameterException("对不起，默认角色不能删除");
        }
        List<UserRole> userRoleList = userRoleRepository.findAllByRole(role);
        if (userRoleList.size() > 0) {
            StringBuilder str = new StringBuilder();
            for (UserRole userRole : userRoleList) {
                str.append(userRole.getUser().getUsername()).append("、");
            }
            str.deleteCharAt(str.length() - 1);
            throw new BusinessException("该角色(id:" + id + ")已被用户(" + str.toString() + ")绑定，不能删除，请先解绑");
        }
        roleAuthorityRepository.deleteByRole(role);
        roleRepository.delete(role);

    }

    @Transactional
    public void updateRoleAuth(Integer roleId, List<Integer> authIdList) {
        Preconditions.checkNotNull(roleId, "角色Id不可以未null");
        Role role = roleRepository.findById(roleId).get();
        Preconditions.checkNotNull(role, "角色(id:" + roleId + ")不存在");
        roleAuthorityRepository.deleteByRole(role);
        Set<Integer> authIdSet = new HashSet<>(authIdList);
        if (authIdSet.size() > 0) {
            List<RoleAuthority> roleAuthorityList = Lists.newArrayList();
            for (Integer authId : authIdSet) {
                Authority authority = authorityRepository.findById(authId).get();
                RoleAuthority roleAuthority = RoleAuthority.builder().role(role).authority(authority).build();
                roleAuthority.setOperator(getLoginUsername());
                roleAuthorityList.add(roleAuthority);
            }
            roleAuthorityRepository.saveAll(roleAuthorityList);
        }
    }

    public List<Map<String, Object>> findAuthTree(RoleParam param) {
        List<RoleAuthority> roleAuthorityList = Lists.newArrayList();
        if (param.getRoleId() != null) {
            Role role = roleRepository.findById(param.getRoleId()).get();
            roleAuthorityList = roleAuthorityRepository.findAllByRole(role);
        }
        Sort sort = new Sort(Sort.Direction.ASC, "orderNumber");
        List<Authority> authorityList = (List<Authority>) authorityRepository.findAll(sort);
        List<Map<String, Object>> authTrees = new ArrayList<>();
        for (Authority authority : authorityList) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id", authority.getId());
            authTree.put("name", getAuthorityName(authority));
            authTree.put("pId", authority.getParentId());
            authTree.put("open", true);
            authTree.put("checked", false);
            for (RoleAuthority temp : roleAuthorityList) {
                if (temp.getAuthority().getId().equals(authority.getId())) {
                    authTree.put("checked", true);
                    break;
                }
            }
            authTrees.add(authTree);
        }
        return authTrees;
    }

    private String getAuthorityName(Authority authority) {
        for (Role role : getLoginUser().getRoles()) {
            if (RoleTypeCode.ADMIN_ROLE.getType().equals(role.getRoleType())) {
                if (StringUtil.isNotBlank(authority.getAuthorityUrl())) {
                    return authority.getAuthorityName() + "(" + StringUtil.getStr(authority.getAuthorityUrl()) + ")";
                } else {
                    return authority.getAuthorityName();
                }
            }
        }
        return authority.getAuthorityName();
    }
}

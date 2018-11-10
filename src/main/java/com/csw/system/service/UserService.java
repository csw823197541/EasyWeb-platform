package com.csw.system.service;

import com.csw.common.base.BaseComponent;
import com.csw.common.base.PageResult;
import com.csw.common.constant.StatusCode;
import com.csw.common.exception.BusinessException;
import com.csw.common.utils.BeanCopyUtil;
import com.csw.common.utils.BeanValidator;
import com.csw.common.utils.StringUtil;
import com.csw.system.entity.Role;
import com.csw.system.entity.User;
import com.csw.system.entity.UserRole;
import com.csw.system.param.UserParam;
import com.csw.system.repository.RoleRepository;
import com.csw.system.repository.UserRepository;
import com.csw.system.repository.UserRoleRepository;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
@Service
public class UserService extends BaseComponent {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void create(UserParam param) {
        BeanValidator.check(param);
        User user = User.builder().build();
        user = (User) BeanCopyUtil.copyBean(param, user);
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
        user.setPassword(finalSecret);
        user.setState(param.getState() != null ? param.getState() : StatusCode.NORMAL.getCode());
        user.setOperator(getLoginUsername());
        user = userRepository.save(user);
        if (StringUtil.isNotBlank(param.getRoleId())) {
            updateUserRole(user, param.getRoleId());
        }
    }

    @Transactional
    public void update(UserParam param) {
        Preconditions.checkNotNull(param.getId(), "修改记录Id不可以为null");
        User user = userRepository.findById(param.getId()).get();
        Preconditions.checkNotNull(user, "待更新用户(id:" + param.getId() + ")不存在");
        user.setUsername(param.getUsername());
        user.setNickName(param.getNickName());
        user.setSex(param.getSex());
        user.setPhone(param.getPhone());
        user.setOperator(getLoginUsername());
        userRepository.save(user);
        if (StringUtil.isNotBlank(param.getRoleId())) {
            userRoleRepository.deleteByUser(user);
            updateUserRole(user, param.getRoleId());
        }
    }

    @Transactional
    public void updateUserRole(User user, String roleIds) {
        String[] roleIdList = StringUtils.split(roleIds, ",");
        List<UserRole> userRoleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            Role role = roleRepository.findById(Integer.valueOf(roleId)).get();
            Preconditions.checkNotNull(role, "角色(id:" + roleId + ")不存在");
            UserRole userRole = UserRole.builder().user(user).role(role).operator(getLoginUsername()).build();
            userRoleList.add(userRole);
        }
        userRoleRepository.saveAll(userRoleList);
    }

    public void updateState(Integer userId, Integer state) {

    }

    public void updatePsw(Integer userId, String newPsw) {

    }

    public PageResult<User> query(Integer page, Integer limit, int stateCode, String searchKey, String searchValue) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<User> userPage;
        if (StringUtil.isNotBlank(searchKey) && StringUtil.isNotBlank(searchValue)) {
            Specification<User> specification = (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicateList = Lists.newArrayList();
                predicateList.add(criteriaBuilder.like(root.get(searchKey), searchValue + "%"));
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            };
            userPage = userRepository.findAll(specification, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }
        List<User> userList = userPage.getContent();
        for (User user : userList) {
            user.setRoles(Lists.newArrayList());
            List<UserRole> userRoleList = userRoleRepository.findAllByUser(user);
            for (UserRole userRole : userRoleList) {
                user.getRoles().add(userRole.getRole());
            }
        }
        return new PageResult<>(userPage.getTotalElements(), userList);
    }
}
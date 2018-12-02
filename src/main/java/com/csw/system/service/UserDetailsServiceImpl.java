package com.csw.system.service;

import com.csw.system.entity.*;
import com.csw.system.repository.RoleAuthorityRepository;
import com.csw.system.repository.UserRepository;
import com.csw.system.repository.UserRoleRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        List<Authority> authorityList = Lists.newArrayList();
        List<UserRole> userRoleList = userRoleRepository.findAllByUser(user);
        for (UserRole userRole : userRoleList) {
            List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findAllByRole(userRole.getRole());
            for (RoleAuthority roleAuthority : roleAuthorityList) {
                authorityList.add(roleAuthority.getAuthority());
            }
            user.getRoles().add(userRole.getRole()); //关联用户角色
        }
        user.setAuthorities(authorityList); //关联用户权限点
        return user;
    }
}

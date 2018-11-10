package com.csw.system.repository;

import com.csw.system.entity.Role;
import com.csw.system.entity.RoleAuthority;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
public interface RoleAuthorityRepository extends PagingAndSortingRepository<RoleAuthority, Integer> {

    List<RoleAuthority> findAllByRole(Role role);

    void deleteByRole(Role role);
}

package com.csw.system.repository;

import com.csw.system.entity.User;
import com.csw.system.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Integer> {

    List<UserRole> findAllByUser(User user);

    void deleteByUser(User user);
}

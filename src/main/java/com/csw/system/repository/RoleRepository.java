package com.csw.system.repository;

import com.csw.system.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
}

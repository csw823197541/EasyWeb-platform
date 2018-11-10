package com.csw.system.repository;

import com.csw.system.entity.Authority;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by csw on 2018/9/12.
 * Description:
 */
public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Integer>, JpaSpecificationExecutor<Authority> {

    List<Authority> findAllByMenuType(int code);

    int countByAuthorityName(String authorityName);

    Authority findByAuthorityName(String parentMenuName);

    Authority findByAuthorityNameAndAuthorityUrl(String authorityName, String authorityUrl);

    Authority findByAuthorityNameOrAuthorityUrl(String authorityName, String authorityUrl);
}

package com.csw.system.repository;

import com.csw.system.entity.OperateRecord;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by csw on 2018/11/26.
 * Description:
 */
public interface OperateRecordRepository extends PagingAndSortingRepository<OperateRecord, Integer> {
}

package com.qutalk.mall.db.dao;

import com.qutalk.mall.db.domain.LitemallOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") LitemallOrder order);
}
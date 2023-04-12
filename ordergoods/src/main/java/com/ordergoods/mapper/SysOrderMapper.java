package com.ordergoods.mapper;

import com.ordergoods.entity.SysOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author yan
 */
public interface SysOrderMapper extends BaseMapper<SysOrder> {

    List<HashMap<String, Integer>> countOrderNum(Long userId);
}

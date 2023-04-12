package com.ordergoods.service;

import com.ordergoods.entity.SysOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author yan
 * @since 2023-02-10
 */
public interface SysOrderService extends IService<SysOrder> {

    List<HashMap<String, Integer>> countOrderNum(Long userId);
}

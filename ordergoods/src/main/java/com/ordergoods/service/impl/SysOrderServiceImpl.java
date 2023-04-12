package com.ordergoods.service.impl;

import com.ordergoods.entity.SysOrder;
import com.ordergoods.mapper.SysOrderMapper;
import com.ordergoods.service.SysOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2023-03-20
 */
@Service
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder> implements SysOrderService {

    @Autowired
    SysOrderMapper orderMapper;

    @Override
    public List<HashMap<String, Integer>> countOrderNum(Long userId) {
        return orderMapper.countOrderNum( userId);
    }//统计某用户订单数量
}

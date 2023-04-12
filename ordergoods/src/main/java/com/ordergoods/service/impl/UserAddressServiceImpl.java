package com.ordergoods.service.impl;

import com.ordergoods.entity.UserAddress;
import com.ordergoods.mapper.UserAddressMapper;
import com.ordergoods.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收货地址表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2023-02-20
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

}

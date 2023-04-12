package com.ordergoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.CommonEnum;
import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import com.ordergoods.common.ToolsUtils;
import com.ordergoods.dto.PageDTO;
import com.ordergoods.entity.SysUser;
import com.ordergoods.entity.UserAddress;
import com.ordergoods.service.SysGoodsService;
import com.ordergoods.service.SysUserService;
import com.ordergoods.service.UserAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户收货地址表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2023-02-21
 */
@Controller
@RequestMapping({"/userAddress","wx/userAddress"})
public class UserAddressController {

    private static  final Logger logger= LoggerFactory.getLogger(UserAddressController.class);

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    SysGoodsService goodsService;

    private static  final String prefix="userAddress";

    @Autowired
    SysUserService userService;


    @RequestMapping("updateDefault")
    @ResponseBody
    public ResponseBean updateDefault(Long id,Long userId){
        UpdateWrapper<UserAddress> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_default","0").eq("user_id",userId);//将地址的 is_default 字段设置为 0，即之前的默认地址取消
        userAddressService.update(updateWrapper);
        UserAddress userAddress = userAddressService.getById(id);//根据传入的 id 获取需要被设置为默认地址的地址对象
        userAddress.setIsDefault(1);
        userAddressService.updateById(userAddress);
        return ResultUtil.success();
    }

    /**
     * 收货地址添加
     * @param userAddress
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseBean add(UserAddress userAddress,String regionCode,String region,String detailAddress){
        logger.debug("addUserAddress:"+userAddress);
        if(userAddress==null){
            return  ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        SysUser sysUser = userService.getById(userAddress.getUserId());
        userAddress.setUserName(sysUser.getName());
        if(regionCode==null||regionCode.isEmpty()){
            return ResultUtil.error("请选择省市区！");
        }
        if(region==null||region.isEmpty()){
            return ResultUtil.error("请选择省市区！");
        }
        String proviceName=region.split(",")[0];
        String cityName=region.split(",")[1];
        String areaName=region.split(",")[2];
        String proviceCode=regionCode.split(",")[0];
        String cityCode=regionCode.split(",")[1];
        String areaCode=regionCode.split(",")[2];
        userAddress.setProvinceName(proviceName);
        userAddress.setProvinceCode(proviceCode);
        userAddress.setCityName(cityName);
        userAddress.setCityCode(cityCode);
        userAddress.setAreaName(areaName);
        userAddress.setAreaCode(areaCode);
        userAddress.setAddress(proviceName+","+cityName+","+areaName+" "+detailAddress);
        // 如果是第一个地址，设置为默认地址
        QueryWrapper<UserAddress> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userAddress.getUserId());
        List<UserAddress> checkList = userAddressService.list(queryWrapper);
        if(checkList==null||checkList.isEmpty()){
            userAddress.setIsDefault(1);
        }
        userAddressService.save(userAddress);
        return  ResultUtil.success();
    }

    /**
     * 收货地址修改
     * @param userAddress
     * @return
     */
    @RequestMapping("wxedit")
    @ResponseBody
    public ResponseBean wxedit(UserAddress userAddress,String regionCode,String region,String detailAddress){
        logger.debug("addUserAddress:"+userAddress);
        if(userAddress==null){
            return  ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        SysUser sysUser = userService.getById(userAddress.getUserId());
        userAddress.setUserName(sysUser.getName());
        if(regionCode==null||regionCode.isEmpty()){
            return ResultUtil.error("请选择省市区！");
        }
        if(region==null||region.isEmpty()){
            return ResultUtil.error("请选择省市区！");
        }
        String proviceName=region.split(",")[0];
        String cityName=region.split(",")[1];
        String areaName=region.split(",")[2];
        String proviceCode=regionCode.split(",")[0];
        String cityCode=regionCode.split(",")[1];
        String areaCode=regionCode.split(",")[2];
        userAddress.setProvinceName(proviceName);
        userAddress.setProvinceCode(proviceCode);
        userAddress.setCityName(cityName);
        userAddress.setCityCode(cityCode);
        userAddress.setAreaName(areaName);
        userAddress.setAreaCode(areaCode);
        userAddress.setAddress(proviceName+","+cityName+","+areaName+" "+detailAddress);

        userAddressService.updateById(userAddress);
        return  ResultUtil.success();
    }




    /**
     * 收货地址修改
     * @param userAddress
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseBean updateById( UserAddress userAddress) {
        if (userAddress == null) {
            return ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        userAddress.setUpdateTime(LocalDateTime.now());
        boolean i = userAddressService.updateById(userAddress);
        return ResultUtil.success() ;
    }

    /**
     * 跳转收货地址列表页面
     * @return
     */
    @RequestMapping("/listPage")
    public String userAddressListPage() {
        return prefix+"/list";

    }


    /**
     * 分页列表查询
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(PageDTO pageDTO, UserAddress queryParam , HttpSession session) {
        logger.debug("查询收货地址列表参数："+queryParam+",pageDTO:"+pageDTO);
        QueryWrapper<UserAddress> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(queryParam.getUserId()!=null,"user_id",queryParam.getUserId());
        queryWrapper.like(!StringUtils.isEmpty(queryParam.getUserName()),"user_name",queryParam.getUserName());
        queryWrapper.like(!StringUtils.isEmpty(queryParam.getReceiverName()),"receiver_name",queryParam.getReceiverName());
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("asc"), ToolsUtils.camelToUnderline(pageDTO.getOrderByColumn()));
        IPage<UserAddress> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        IPage<UserAddress> listPage = userAddressService.page(indexPage, queryWrapper);
        logger.debug("获取的收货地址列表：{}",listPage);
        //获取分页信息
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);
        return ResultUtil.successData(pageInfo);
    }

    /**
     * 收货地址删除
     * @param idList
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResponseBean delete(@RequestParam("idList") List<Long> idList){
        if(idList==null||idList.isEmpty()){
            return ResultUtil.error("ID不合法！");
        }
        userAddressService.removeByIds(idList);
        return ResultUtil.success();
    }
}

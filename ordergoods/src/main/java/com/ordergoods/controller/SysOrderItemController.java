package com.ordergoods.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import com.ordergoods.common.ToolsUtils;
import com.ordergoods.dto.PageDTO;
import com.ordergoods.entity.SysOrder;
import com.ordergoods.entity.SysOrderItem;
import com.ordergoods.entity.SysUser;
import com.ordergoods.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author yan
 */
@Controller
@RequestMapping("/orderItem")
public class SysOrderItemController {

    private static  final Logger logger= LoggerFactory.getLogger(SysOrderItemController.class);

    @Autowired
    private SysOrderItemService orderItemService;

    @Autowired
    private SysFileService fileService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysOrderService orderService;

    @Autowired
    private SysGoodsService goodsService;

    @Value("${com.jane.file.baseFilePath}")
    private String baseFilePath;

    @ModelAttribute
    public void addInfoToModel(Model model, HttpSession session){
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",2);
        List<SysUser> userList = userService.list(queryWrapper);
        model.addAttribute("userList",userList);

    }

    //修改状态
    @RequestMapping("/updateState")
    @ResponseBody
    public ResponseBean  updateState(SysOrderItem orderItem){
        Integer state = orderItem.getState();
        String orderId=null;
        if(state==2){
            //从待配货修改为已配货
            SysOrderItem updVal = orderItemService.getById(orderItem.getId());
            orderId=updVal.getOrderId();
            updVal.setState(2);
            orderItemService.updateById(updVal);
            //检测是否都为已配货状态
            SysOrderItem queryObj=new SysOrderItem();
            queryObj.setOrderId(orderId);
            queryObj.setState(1);
            List<SysOrderItem> orderItemList = orderItemService.listByMap(ToolsUtils.convertObjToMap(queryObj, false));
            if(ToolsUtils.isEmpty(orderItemList)){
                //说明均为已发货状态，修改订单状态为已发货
                SysOrder sysOrder = orderService.getById(Long.parseLong(orderId));
                sysOrder.setState(2);
                orderService.updateById(sysOrder);
            }
        }
        return ResultUtil.success();
    }


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listPage/{id}")
    public String orderListPage(@PathVariable("id") Long orderId,Model model) {
        model.addAttribute("orderId",orderId);
        return "orderItem/list";
    }

    /**
     * 分页列表查询
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(SysOrderItem queryParam, PageDTO pageDTO, HttpSession session) {
        logger.debug("查询订单明细列表参数："+queryParam+",pageDTO:"+pageDTO);
        QueryWrapper<SysOrderItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(queryParam.getOrderId()),"order_id",queryParam.getOrderId());
        queryWrapper.eq(queryParam.getUserId()!=null,"user_id",queryParam.getUserId());
        queryWrapper.eq(queryParam.getGoodsId()!=null,"goods_id",queryParam.getGoodsId());
        queryWrapper.eq(queryParam.getState()!=null,"state",queryParam.getState());
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("asc"), ToolsUtils.camelToUnderline(pageDTO.getOrderByColumn()));
        IPage<SysOrderItem> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        IPage<SysOrderItem> listPage = orderItemService.page(indexPage, queryWrapper);
        List<SysOrderItem> orderItemList = listPage.getRecords();
        orderItemList.forEach(e->e.setGoodsObj(JSONObject.parseObject(e.getField0())));
        listPage.setRecords(orderItemList);
        logger.debug("获取的订单明细列表："+listPage);
        //获取分页信息
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);
        return ResultUtil.successData(pageInfo);
    }


    /**
     * 分类删除
     * @param idList
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResponseBean delete(@RequestParam("idList") List<Long> idList){
        if(idList==null||idList.isEmpty()){
            return ResultUtil.error("ID不合法！");
        }
        for(Long id:idList){
            orderItemService.removeById(id);
        }
        return ResultUtil.success();
    }

}

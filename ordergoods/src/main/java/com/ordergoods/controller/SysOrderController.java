package com.ordergoods.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.CommonEnum;
import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import com.ordergoods.common.ToolsUtils;
import com.ordergoods.dto.PageDTO;
import com.ordergoods.entity.*;
import com.ordergoods.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author yan
 */
@Controller
@RequestMapping({"/order","wx/order"})
public class SysOrderController {

    private static  final Logger logger= LoggerFactory.getLogger(SysOrderController.class);

    @Autowired
    SysOrderService orderService;

    @Autowired
    SysGoodsService goodsService;

    @Autowired
    SysUserService userService;

    @Autowired
    SysOrderItemService itemService;

    @Autowired
    SysCartService cartService;

    @Autowired
    UserAddressService userAddressService;

    private static  final String prefix="order";


    @ModelAttribute
    private void addModelInfo(Model model){
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",2);
        List<SysUser> userList = userService.list(queryWrapper);
        model.addAttribute("userList",userList);
    }



    /**
     * 订单添加
     * @param order
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseBean add(SysOrder order){
        logger.debug("addSysOrder:"+order);
        if(order==null){
            return  ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        orderService.save(order);
        return  ResultUtil.success();
    }


    //订单添加---立即购买
    @RequestMapping("addImmediately")
    @ResponseBody
    public ResponseBean addOrderImmediately(SysCart cart,Long addressId){
        Long userId = cart.getUserId();
        UserAddress addressObj = null;// 收货地址
        //处理收货地址
        if(addressId==null){//说明没有额外选择是默认收货地址
            QueryWrapper<UserAddress> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_id",userId);
            queryWrapper.eq("is_default",1);
            List<UserAddress> checkList = userAddressService.list(queryWrapper);
            if(checkList!=null&& !checkList.isEmpty()){
                addressObj= checkList.get(0);
            }

        }else{
            addressObj=userAddressService.getById(addressId);
        }
        String userName=cart.getUserName();

        Long goodsId = cart.getGoodsId();
        SysGoods goods = goodsService.getById(goodsId);
        Integer discount = Integer.parseInt(goods.getField1());
        //插入订单表
        BigDecimal money = cart.getPrice().multiply(new BigDecimal(cart.getGoodsNum())).multiply(new BigDecimal(discount)).divide(new BigDecimal(10));//金额
        String orderNum = ToolsUtils.createOrderNum();//生成订单编号
        String address = addressObj.getAddress();//订单地址-完整地址
        SysOrder order=new SysOrder();
        order.setState(0);
        order.setCode(orderNum);
        order.setUserId(userId);
        order.setUserName(userName);
        order.setAddress(address);
        order.setField1(String.valueOf(addressObj.getId()));//保存收货地址ID
        order.setMoney(money);
        orderService.save(order);
        logger.debug("插入订单后的结果：{}",order);
        Long orderId = order.getId();//订单ID
        Integer goodsNum = cart.getGoodsNum();
        BigDecimal price = cart.getPrice();
        //插入订单明细
        SysOrderItem orderItem=new SysOrderItem();
        orderItem.setState(0);
        orderItem.setField0(JSON.toJSONString(goods));
        orderItem.setUserId(userId);
        orderItem.setUserName(userName);
        orderItem.setOrderId(orderId+"");
        orderItem.setNumber(goodsNum);
        orderItem.setGoodsId(goodsId);
        orderItem.setPrice(price);
        itemService.save(orderItem);
        //更新goods
        String num= goods.getNum()==null?"0":goods.getNum();
        goods.setNum(String.valueOf(Integer.parseInt(num)+goodsNum));
        goods.setStock(goods.getStock()-goodsNum);
        goodsService.updateById(goods);
        return new ResponseBean<>(true, CommonEnum.SUCCESS_REQUEST);
    }


    /**
     * 订单修改
     * @param order
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseBean updateById( SysOrder order) {
        if (order == null) {
            return ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        order.setUpdateTime(LocalDateTime.now());
        boolean i = orderService.updateById(order);
        return ResultUtil.success() ;
    }

    /**
     * 跳转订单列表页面
     * @return
     */
    @RequestMapping("/listPage")
    public String orderListPage() {
        return prefix+"/list";

    }


    /**
     * 分页列表查询
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(PageDTO pageDTO, SysOrder queryParam , HttpSession session) {
        logger.debug("查询订单列表参数："+queryParam+",pageDTO:"+pageDTO);
        QueryWrapper<SysOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(queryParam.getState()!=null,"state",queryParam.getState());
        queryWrapper.eq(queryParam.getUserId()!=null,"user_id",queryParam.getUserId());
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("asc"), ToolsUtils.camelToUnderline(pageDTO.getOrderByColumn()));
        IPage<SysOrder> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        IPage<SysOrder> listPage = orderService.page(indexPage, queryWrapper);
        List<SysOrder> orderList = listPage.getRecords();
        orderList.forEach(e->{
            QueryWrapper<SysOrderItem> itemQueryWrapper=new QueryWrapper<>();
            itemQueryWrapper.eq("order_id",e.getId());
            List<SysOrderItem> itemList = itemService.list(itemQueryWrapper);
            itemList.forEach(e1->e1.setGoodsObj(JSONObject.parseObject(e1.getField0())));
            e.setOrderItemList(itemList);
        });
        logger.debug("获取的订单列表："+listPage);
        //获取分页信息
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);
        return ResultUtil.successData(pageInfo);
    }

    /**
     * 订单删除
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
            orderService.removeById(id);
        }
        return ResultUtil.success();
    }


    /**
     *订单支付
     * @param orderList
     * @return
     * 订单和订单明细状态改为1，
     */
    @RequestMapping("pay")
    @ResponseBody
    public ResponseBean payForOrder(String orderList,Long userId,HttpSession session){
        if(StringUtils.isEmpty(orderList)){
            return new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        SysUser user = userService.getById(userId);
        String[] split = orderList.split(",");
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("state",1);
        updateWrapper.in("id",split);
        //更新订单状态
        orderService.update(updateWrapper);
        //更新订单明细表状态
        updateWrapper=new UpdateWrapper();
        updateWrapper.set("state",1);
        updateWrapper.in("order_id",split);
        itemService.update(updateWrapper);
        //获取订单明细
        QueryWrapper<SysOrderItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("order_id",split);
        List<SysOrderItem> itemList = itemService.list(queryWrapper);
        return  new ResponseBean(true,CommonEnum.SUCCESS_OPTION);
    }

    /**
     * 订单评价
     * @param orderList
     * @param appraise
     * @return
     */
    @RequestMapping("/appraiseOrder")
    @ResponseBody
    public ResponseBean appraiseOrder(String orderList,String appraise,String score){
        if(StringUtils.isEmpty(orderList)){
            return new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        //评价的订单ID
        String[] split = orderList.split(",");
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("appraise",appraise);
        updateWrapper.set("state",3);
        updateWrapper.set("photo",score);
        updateWrapper.in("id",split);
        orderService.update(updateWrapper);
        updateWrapper=new UpdateWrapper();
        updateWrapper.set("aappraise",appraise);
        updateWrapper.set("ascore",score);
        updateWrapper.set("state",3);
        updateWrapper.in("order_id",split);
        //同步更新到订单明细
        itemService.update(updateWrapper);
        return new ResponseBean(true,CommonEnum.SUCCESS_OPTION);
    }

    /**
     * 购物车提交订单
     * @param userId 下单用户ID
     * @param list  选中的购物车明细ID
     * @return
     */
    @RequestMapping("divAdd")
    @ResponseBody
    public ResponseBean divAdd(Long userId,String cartId,Long addressId){
        logger.debug("购物车提交订单");
        if(StringUtils.isEmpty(cartId)){
            return new ResponseBean(false,"购物车ID为空！");
        }
        List<String> cartIdList = Arrays.asList(cartId.split(","));
        QueryWrapper<SysCart> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",cartIdList);
        List<SysCart> cartList= cartService.list(queryWrapper);
        SysUser user = userService.getById(userId);
        double money = cartList.stream().mapToDouble(e2 -> new BigDecimal(e2.getGoodsNum()).multiply(new BigDecimal(goodsService.getById(e2.getGoodsId()).getField1())).
                multiply(e2.getPrice()).divide(new BigDecimal(10)).doubleValue()).sum();//订单金额
        String orderNum = ToolsUtils.createOrderNum();//订单编号

        UserAddress addressObj = null;// 收货地址
        //处理收货地址
        if(addressId==null){//说明没有额外选择是默认收货地址
            QueryWrapper<UserAddress> queryAddressWrapper=new QueryWrapper<>();
            queryAddressWrapper.eq("user_id",userId);
            queryAddressWrapper.eq("is_default",1);
            List<UserAddress> checkList = userAddressService.list(queryAddressWrapper);
            if(checkList!=null&& !checkList.isEmpty()){
                addressObj= checkList.get(0);
            }

        }else{
            addressObj=userAddressService.getById(addressId);
        }

        String address = addressObj.getAddress();//订单地址
        //插入订单表
        SysOrder order=new SysOrder();
        order.setState(0);
        order.setCode(orderNum);
        order.setUserId(userId);
        order.setUserName(user.getName());
        order.setAddress(address);
        order.setField1(String.valueOf(addressObj.getId()));
        order.setMoney(new BigDecimal(money).setScale(1,BigDecimal.ROUND_HALF_UP));
        orderService.save(order);//插入订单表
        logger.debug("插入订单后的结果：{}",order);
        //循环遍历，插入订单明细表
        cartList.forEach(e->{
            Long orderId = order.getId();//订单ID
            List<Integer> delCartIdList=new ArrayList<>();//保存购物车Id list
            delCartIdList.add(e.getId().intValue());
            Integer goodsNum = e.getGoodsNum();
            BigDecimal price = e.getPrice();
            Long goodsId = e.getGoodsId();
            SysGoods goods = goodsService.getById(goodsId);
            SysOrderItem orderItem=new SysOrderItem();
            orderItem.setState(0);
            orderItem.setField0(JSON.toJSONString(goods));
            orderItem.setUserId(userId);
            orderItem.setUserName(user.getName());
            orderItem.setOrderId(orderId+"");
            orderItem.setNumber(goodsNum);
            orderItem.setPrice(price);
            orderItem.setGoodsId(goodsId);
            itemService.save(orderItem);
            logger.debug("插入订单明细表");
            //删除购物车内容
            cartService.removeById(e.getId());
            logger.debug("删除对应的购物车记录");
            userService.updateById(user);
        });
        return new ResponseBean<>(true, CommonEnum.SUCCESS_REQUEST);
    }

    /**
     * 用户取消订单
     * @param orderList
     * @return
     */
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public ResponseBean cancelOrder(String orderList){
        if(StringUtils.isEmpty(orderList)){
            return new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        // 将订单号列表转换为整型数组
        List<Integer> orderArray = Arrays.asList(orderList.split(",")).stream().map(e->  Integer.parseInt(e)).collect(Collectors.toList());
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("order_id",orderArray);
        //删除订单明细
        itemService.remove(queryWrapper);
        //删除订单
        queryWrapper=new QueryWrapper();
        queryWrapper.in("id",orderArray);
        orderService.remove(queryWrapper);
        return new ResponseBean(true,CommonEnum.SUCCESS_OPTION);
    }

    //统计用户订单数量
    @RequestMapping("/countOrderNum")
    @ResponseBody
    public ResponseBean countOrderNum(Long userId){
        if(userId==null||userId<1){
            return new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        //查询该用户的订单数量
        List<HashMap<String, Integer>> countOrderNum = orderService.countOrderNum(userId);
        Map<String,Object> orderNumMap=new HashMap<>();
        countOrderNum.forEach(e->{
            Object state = e.get("state");// 获取订单状态
            Object number = e.get("number");//获取订单数量
            orderNumMap.put(state.toString(),Integer.parseInt(number.toString()));// 将状态和数量存放到订单数量 Map 对象中
            e.remove("state");
            e.remove("number");
        });
        logger.debug("第一次转换key后的结果：{}",orderNumMap);
        //将订单状态从数字转换为字母
        List<String> collect = orderNumMap.keySet().stream().collect(Collectors.toList());
        collect.forEach(e->{
            String key = e;
            String transfer="";
            switch (key){
                case "0":
                    transfer="a";
                    break;
                case "1":
                    transfer="b";
                    break;
                case "2":
                    transfer="c";
                    break;
                case "3":
                    transfer="d";
                    break;
                default:
                    break;
            }
            orderNumMap.put(transfer,orderNumMap.get(key));
        });
        logger.debug("统计的用户当前订单数量为：{}",orderNumMap);
        return new ResponseBean(true,orderNumMap,CommonEnum.SUCCESS_OPTION);//将订单数量 Map 对象封装为 ResponseBean 统一返回给前端调用方
    }

}

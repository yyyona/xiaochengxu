package com.ordergoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.CommonEnum;
import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import com.ordergoods.common.ToolsUtils;
import com.ordergoods.dto.PageDTO;
import com.ordergoods.entity.SysCart;
import com.ordergoods.entity.SysGoods;
import com.ordergoods.service.SysCartService;
import com.ordergoods.service.SysGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 * @author yan
 */
@Controller
@RequestMapping({"/cart","wx/cart"})
public class SysCartController {

    private static  final Logger logger= LoggerFactory.getLogger(SysCartController.class);

    @Autowired
    SysCartService cartService;

    @Autowired
    SysGoodsService goodsService;

    private static  final String prefix="cart";

    /**
     * 跳到添加页
     * @param model
     * @return
     */
    @RequestMapping("addPage")
    public String addPage(Model model){
        return prefix+"/add";
    }


    /**
     * 添加
     * @param cart
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseBean add(SysCart cart){
        logger.debug("addSysCart:"+cart);
        if(cart==null){
            return  ResultUtil.error(CommonEnum.BAD_PARAM);//如果参数为空则返回错误信息
        }
        Long goodsId = cart.getGoodsId();//获取购物车中的商品ID
        QueryWrapper<SysCart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("goods_id",goodsId)
                    .eq("user_id",cart.getUserId());//根据用户ID和商品ID查询购物车表
        List<SysCart> cartList = cartService.list(queryWrapper);
        if(ToolsUtils.isEmpty(cartList)){//如果购物车表中不存在相同记录
            cartService.save(cart);//将购物车记录插入数据库
        }else{
            SysCart sysCart = cartList.get(0);//获取该购物车记录
            sysCart.setGoodsNum(cart.getGoodsNum()+sysCart.getGoodsNum());//更新购物车中商品数量
            cartService.updateById(sysCart);//更新购物车记录
        }
        return  ResultUtil.successData(cart);//返回成功信息以及购物车记录
    }

    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listPage")
    public String cartListPage() {
        return prefix+"/list";

    }

    /**
     * 统计用户的购物车商品数量
     * @param userId
     * @return
     */
    @RequestMapping("countCartNum")
    @ResponseBody
    public ResponseBean countCartNum(Long userId){
        QueryWrapper<SysCart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysCart> cartList = cartService.list( );
        int sum = cartList.stream().mapToInt(SysCart::getGoodsNum).sum();//使用流操作将购物车项的商品数量取出并求和
        return ResultUtil.successData(sum);
    }


    /**
     * 分页列表查询
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(PageDTO pageDTO, SysCart queryParam , HttpSession session) {//获取前端传递过来的分页参数
        logger.debug("查询列表参数："+queryParam+",pageDTO:"+pageDTO);
        QueryWrapper<SysCart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(queryParam.getUserId()!=null,"user_id",queryParam.getUserId());
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("asc"), ToolsUtils.camelToUnderline(pageDTO.getOrderByColumn()));
        IPage<SysCart> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        IPage<SysCart> listPage = cartService.page(indexPage, queryWrapper);
        List<SysCart> records = listPage.getRecords();
        records.forEach(e->{
            Long goodsId = e.getGoodsId();
            SysGoods goods = goodsService.getById(goodsId);
            e.setGoods(goods);
        });
        logger.debug("获取的列表："+listPage);
        //获取分页信息
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);//wrapperPageInfo方法接受一个ListPage对象,转换成一个Map类型的分页信息
        return ResultUtil.successData(pageInfo);
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResponseBean delete(@RequestParam("idList") List<Long> idList){
        if(idList==null||idList.isEmpty()){
            return ResultUtil.error("ID不合法！");
        }
       cartService.removeByIds(idList);
        return ResultUtil.success();
    }



}

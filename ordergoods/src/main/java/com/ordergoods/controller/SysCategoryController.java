package com.ordergoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.CommonEnum;
import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import com.ordergoods.common.ToolsUtils;
import com.ordergoods.dto.PageDTO;
import com.ordergoods.entity.SysCategory;
import com.ordergoods.entity.SysGoods;
import com.ordergoods.service.SysCategoryService;
import com.ordergoods.service.SysGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author yan
 */
@Controller
@RequestMapping({"/category","wx/category"})
public class SysCategoryController {

    private static  final Logger logger= LoggerFactory.getLogger(SysCategoryController.class);

    @Autowired
    SysCategoryService categoryService;

    @Autowired
    SysGoodsService goodsService;

    private static  final String prefix="category";

    /**
     * 跳到分类添加页
     * @param model
     * @return
     */
    @RequestMapping("addPage")
    public String addPage(Model model){
        return prefix+"/add";
    }


    /**
     * 分类添加
     * @param category
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseBean add(SysCategory category){
        logger.debug("addSysCategory:"+category);
        if(category==null){
            return  ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        //校验分类名称是否已经被占用
        QueryWrapper<SysCategory> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",category.getName()).or().eq("code",category.getCode());
        List<SysCategory> list = categoryService.list(queryWrapper);//调用list方法查询是否存在相同的分类名称或编号
        if(ToolsUtils.isNotEmpty(list)){
            return ResultUtil.error("分类名称或编号已经被占用！");//使用ResultUtil 返回错误信息
        }
        categoryService.save(category);
        return  ResultUtil.success();
    }

    /**
     * 跳转到分类修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/editPage/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return prefix+"/edit";
    }



    /**
     * 分类修改
     * @param category
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseBean updateById( SysCategory category) {
        if (category == null) {
            return ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        //校验分类名称是否已经被占用
        String name = category.getName();
        QueryWrapper<SysCategory> queryWrapper=new QueryWrapper<>();
        queryWrapper.and(e->e.eq("name",category.getName()).or().eq("code",category.getCode())).ne("id",category.getId());//查询条件 name 列等于 category.getName() 属性值。
        List<SysCategory> list =categoryService.list(queryWrapper);
        if(ToolsUtils.isNotEmpty(list)){
            return ResultUtil.error("分类名称或编码已经被占用！");
        }

        boolean i = categoryService.updateById(category);
        return ResultUtil.success() ;
    }

    /**
     * 跳转分类列表页面
     * @return
     */
    @RequestMapping("/listPage")
    public String categoryListPage() {
        return prefix+"/list";

    }


    /**
     * 分页列表查询
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(PageDTO pageDTO, SysCategory queryParam , HttpSession session) {
        logger.debug("查询分类列表参数："+queryParam+",pageDTO:"+pageDTO);
        QueryWrapper<SysCategory> queryWrapper=new QueryWrapper<>();//创建QueryWrapper对象
        queryWrapper.like(!StringUtils.isEmpty(queryParam.getName()),"name",queryParam.getName());//当查询参数中的名称不为空时，设置like查询条件
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("desc"), ToolsUtils.camelToUnderline(pageDTO.getOrderByColumn()));/// 根据分页信息构造排序条件，如果isAsc等于desc，则按倒序排列
        IPage<SysCategory> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());//创建IPage对象，并设置分页信息,Ipage是分页查询接口
        IPage<SysCategory> listPage = categoryService.page(indexPage, queryWrapper);//调用categoryService中的page方法，执行查询操作
        logger.debug("获取的分类列表："+listPage);
        //获取分页信息
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);//将分页请求的查询结果 listPage 封装成一个 Map 对象
        return ResultUtil.successData(pageInfo);//将 pageInfo 封装成一个 ResponseBean 对象
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
        QueryWrapper<SysGoods> queryWrapper=new QueryWrapper<>();
        for(Long id:idList){
            queryWrapper.eq("category_id",id);
            List<SysGoods> list = goodsService.list(queryWrapper);
            if(ToolsUtils.isNotEmpty(list)){
                return ResultUtil.error("分类下有商品，不可删除！");
            }
            categoryService.removeById(id);
        }
        return ResultUtil.success();
    }


}

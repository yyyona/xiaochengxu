package com.ordergoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.*;
import com.ordergoods.dto.PageDTO;
import com.ordergoods.entity.*;
import com.ordergoods.entity.SysGoods;
import com.ordergoods.service.*;
import com.ordergoods.service.SysGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author yan
 */
@Controller
@RequestMapping({"/goods","wx/goods"})
public class SysGoodsController {
    
    private static  final Logger logger= LoggerFactory.getLogger(SysGoodsController.class);

    @Autowired
    SysCategoryService categoryService;

    @Autowired
    SysGoodsService goodsService;
    
    @Autowired
    SysFileService fileService;

    @Autowired
    SysOrderService orderService;

    @Autowired
    SysOrderItemService orderItemService;

    private static  final String prefix="goods";


    @ModelAttribute
    private void addModelInfo(Model model){
        List<SysCategory> categoryList = categoryService.list();
        model.addAttribute("categoryList",categoryList);
    }


    /**
     * 获取推荐列表--新品推荐
     * @return
     */
    @RequestMapping("/recommend")
    @ResponseBody
    public ResponseBean getRecommendList(PageDTO pageDTO, Long userId, HttpSession session) {
        QueryWrapper<SysGoods> queryWrapper=new QueryWrapper<>();
        List<String> recommendList=null;
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("asc"), "id");//设置 orderBy 条件为 "id" 升序或降序排列
        IPage<SysGoods> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        IPage<SysGoods> listPage = goodsService.page(indexPage, queryWrapper);//将查询结果封装成 IPage 对象 listPage
        logger.debug("获取的商品推荐列表："+listPage);
        //获取分页信息
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);
        return ResultUtil.successData(pageInfo);
    }


    /**
     * 跳到商品添加页
     * @param model
     * @return
     */
    @RequestMapping("addPage")
    public String addPage(Model model){
        return prefix+"/add";
    }


    /**
     * 商品添加
     * @param goods
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseBean add(SysGoods goods, @RequestParam(name="file0",required = false) MultipartFile file0,
                            @RequestParam(name="file1",required = false) MultipartFile file1,
                            @RequestParam(name="file2",required = false) MultipartFile file2,
                            @RequestParam(name="file3",required = false) MultipartFile file3,
                            @RequestParam(name="file4",required = false) MultipartFile file4){
        logger.debug("addSysGoods:"+goods);
        if(goods==null){
            return  ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        //校验商品名称是否已经被占用
        QueryWrapper<SysGoods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",goods.getName()).or().eq("code",goods.getCode());
        List<SysGoods> list = goodsService.list(queryWrapper);
        if(ToolsUtils.isNotEmpty(list)){
            return ResultUtil.error("商品名称或编号已经被占用！");
        }
        SysCategory sysCategory = categoryService.getById(goods.getCategoryId());
        goods.setCategoryName(sysCategory.getName());
        goodsService.save(goods);
        //保存文件并更新saveName到main_pic
        if (file0!=null&&!file0.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_main_pic);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file0);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setMainPic(saveFile.getSaveName());
            }
        }
        if (file1!=null&&!file1.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic1);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file1);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic1(saveFile.getSaveName());
            }
        }
        if (file2!=null&&!file2.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic2);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file2);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic2(saveFile.getSaveName());
            }
        }
        if (file3!=null&&!file3.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic3);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file3);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic3(saveFile.getSaveName());
            }
        }
        if (file4!=null&&!file4.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic4);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file4);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic4(saveFile.getSaveName());
            }
        }
        boolean b = goodsService.updateById(goods);
        return  ResultUtil.success();
    }

    /**
     * 跳转到商品修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/editPage/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("goods", goodsService.getById(id));
        return prefix+"/edit";
    }


    /**
     * 返回给小程序的商品明细
     * @return
     */
    @RequestMapping("wxdetail")
    @ResponseBody
    public ResponseBean wxgoodsDetail(Long id){
        SysGoods goods = goodsService.getById(id);
        return ResultUtil.successData(goods);
    }


    /**
     * 商品修改
     * @param goods
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseBean updateById( SysGoods goods, @RequestParam(name="file0",required = false) MultipartFile file0,
                                    @RequestParam(name="file1",required = false) MultipartFile file1,
                                    @RequestParam(name="file2",required = false) MultipartFile file2,
                                    @RequestParam(name="file3",required = false) MultipartFile file3,
                                    @RequestParam(name="file4",required = false) MultipartFile file4) {
        if (goods == null) {
            return ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        SysCategory sysCategory = categoryService.getById(goods.getCategoryId());
        goods.setCategoryName(sysCategory.getName());
        //保存文件并更新saveName到main_pic,更新商品相关图片
        // 如果 file0 不为空且非空文件，则更新商品主图
        if (file0!=null&&!file0.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());//设置文件所属对象ID
            sysFile.setCategoryCode(ComCodeEnum.category_code_main_pic);//设置文件所属类别
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file0);//保存或更新商品主图文件
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setMainPic(saveFile.getSaveName());
            }
        }
        //更新商品副图
        if (file1!=null&&!file1.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic1);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file1);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic1(saveFile.getSaveName());
            }
        }
        if (file2!=null&&!file2.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic2);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file2);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic2(saveFile.getSaveName());
            }
        }
        if (file3!=null&&!file3.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic3);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file3);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic3(saveFile.getSaveName());
            }
        }
        if (file4!=null&&!file4.isEmpty()) {
            SysFile sysFile=new SysFile();
            sysFile.setObjectId(goods.getId());
            sysFile.setCategoryCode(ComCodeEnum.category_code_sub_pic4);
            SysFile saveFile=fileService.saveOrUpdateFile(sysFile,file4);
            if(saveFile!=null&&!StringUtils.isEmpty(saveFile.getSaveName())){
                goods.setSubPic4(saveFile.getSaveName());
            }
        }
        //校验商品名称是否已经被占用
        String name = goods.getName();
        QueryWrapper<SysGoods> queryWrapper=new QueryWrapper<>();
        queryWrapper.and(e->e.eq("name",goods.getName()).or().eq("code",goods.getCode())).ne("id",goods.getId());
        List<SysGoods> list =goodsService.list(queryWrapper);
        if(ToolsUtils.isNotEmpty(list)){
            return ResultUtil.error("商品名称或编码已经被占用！");
        }
        goods.setUpdateTime(LocalDateTime.now());
        boolean i = goodsService.updateById(goods);
        return ResultUtil.success() ;
    }

    /**
     * 跳转商品列表页面
     * @return
     */
    @RequestMapping("/listPage")
    public String goodsListPage() {
        return prefix+"/list";

    }


    /**
     * 分页列表查询
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(PageDTO pageDTO, SysGoods queryParam , HttpSession session) {
        logger.debug("查询商品列表参数："+queryParam+",pageDTO:"+pageDTO);
        QueryWrapper<SysGoods> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(queryParam.getName()),"name",queryParam.getName());
        queryWrapper.eq(queryParam.getCategoryId()!=null,"category_id",queryParam.getCategoryId());
        queryWrapper.eq(queryParam.getState()!=null,"state",queryParam.getState());
        queryWrapper.orderBy(true,pageDTO.getIsAsc().equals("asc"), ToolsUtils.camelToUnderline(pageDTO.getOrderByColumn()));
        IPage<SysGoods> indexPage = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());//构造分页对象
        IPage<SysGoods> listPage = goodsService.page(indexPage, queryWrapper);//调用service层中的page方法，根据查询条件和分页信息获取商品列表
        logger.debug("获取的商品列表："+listPage);
        //获取分页信息，封装到map中
        Map pageInfo = ToolsUtils.wrapperPageInfo(listPage);
        return ResultUtil.successData(pageInfo);
    }

    /**
     * 商品删除
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
            goodsService.removeById(id);
        }
        return ResultUtil.success();
    }


    /**
     * 上架/下架商品
     * @param id
     * @param state
     * @return
     */
    @RequestMapping("updateState")
    @ResponseBody
    public ResponseBean updateState(Long id,Integer state){
        SysGoods goods = goodsService.getById(id);// 根据id从数据库中获取到商品对象
        goods.setState(state);//更新商品状态
        goods.setUpdateTime(LocalDateTime.now());
        goodsService.updateById(goods);//调用Service层的updateById()方法更新商品信息
        return ResultUtil.successData(goods);
    }


}

package com.ordergoods.controller;

import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by yan
 */
@Controller
public class IndexController {

   //处理错误页面，接受status参数来确定错误类型，并根据不同类型返回不同的视图
    @RequestMapping("/error/{status}")
    public String errorPage(@PathVariable Integer status,String msg,Error error,Exception exception,Model model){
        if(!StringUtils.isEmpty(msg)){
            model.addAttribute("errorMsg",msg);
        }
        if(error!=null){
            model.addAttribute("errorMsg",error.getMessage());//向model中添加属性值
        }
        if(exception!=null){
            model.addAttribute("errorMsg",exception.getMessage());
        }
        switch (status){
            case 401:
            case 400:
            case 404:return "/error/404";
            case 500:return "/error/500";
            default:return "/error/default";
        }
    }


    @RequestMapping({"/index","","/admin/index"})
    public String index(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if(user==null){
            return "login";
        }
        return "index";
    }

    //跳转到后台登录页面
    @RequestMapping({"/admin/login"})
    public String adminLogin(HttpSession session,Model model) {
        removeLogin(session);
        return "login";//返回的字符串作为逻辑视图名，由 SpringMVC找到对应的物理视图进行渲染
    }

    @ApiOperation("退出登录")
    @RequestMapping( value = {"/logout","/admin/logout"})
    public String logout(HttpSession session,Model model){
        removeLogin(session);
        return "login";
    }
    @RequestMapping( value = {"/logout","/admin/logout"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean logoutRes(HttpSession session, Model model){
        removeLogin(session);
        return ResultUtil.success();
    }

    private void removeLogin(HttpSession session){
        session.removeAttribute("user");
        session.removeAttribute("type");
        session.removeAttribute("admin");
    }

}

package com.ordergoods.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ordergoods.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by yan at 2023/1/19.
 */
public class ToolsUtils {

    private static  final Logger logger= LoggerFactory.getLogger(ToolsUtils.class);

    //封装返回结果--针对layui table
    public static Map wrapperPageInfo(IPage listPage){
        Map<String, Object> pageInfo = new HashMap<String, Object>();//
        pageInfo.put("pageSize", listPage.getSize());//每页大小
        pageInfo.put("pageNum", listPage.getCurrent());//当前第几页
        pageInfo.put("pages", listPage.getPages());//总页数
        pageInfo.put("rows", listPage.getRecords());//查询的记录列表
        pageInfo.put("total", listPage.getTotal());//总记录数
        return pageInfo;
    }

    /**
     * 获取登录用户ID
     * @param session
     * @return
     */
    public static Long getLoginUserId(HttpSession session){
        Object user = session.getAttribute("user");
        if(user==null){
           return  null;
        }else{
            SysUser loginUser= (SysUser) user;
            return loginUser.getId();
        }
    }

    /**
     * 获取登录用户名称
     * @param session
     * @return
     */
    public static String getLoginUserName(HttpSession session){
        Object user = session.getAttribute("user");
        if(user==null){
           return null;
        }else{
            SysUser loginUser= (SysUser) user;
            return loginUser.getName();
        }
    }

    //驼峰转下划线
    public static String camelToUnderline(String fieldName){
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<fieldName.length();i++){
            char c = fieldName.charAt(i);
            //65-90是大写A到大写Z 97到122是小写a到小写z。
            if(i!=0&&i!=fieldName.length()-1&&c>=65&&c<=90){
                //说明是大写且非首末字母则需要转驼峰
                char lowerCase = Character.toLowerCase(c);
                stringBuffer.append("_").append(lowerCase);
            }else{
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    //对象转map且字段转换为下划线格式
    public static Map<String,Object> convertObjToMap(Object obj){
        if(obj == null){
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();

        List excludeFields=new ArrayList();
        excludeFields.add("serialVersionUID");
       //只取属性字段
       for (Field declaredField : obj.getClass().getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                String fieldName = declaredField.getName();
                if(!excludeFields.contains(fieldName)){
                    map.put(camelToUnderline(fieldName), declaredField.get(obj));
                }
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(),e);
            }
        }

        return map;
    }


    //对象转map且字段转换为下划线格式
    public static Map<String,Object> convertObjToMap(Object obj,Boolean isNull){
        if(obj == null){
            return Collections.emptyMap();
        }
        if(isNull==null){
            isNull=true;
        }
        Map<String, Object> map = new HashMap<>();

        List excludeFields=new ArrayList();
        excludeFields.add("serialVersionUID");
       //只取属性字段
       for (Field declaredField : obj.getClass().getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                String fieldName = declaredField.getName();
                if(!excludeFields.contains(fieldName)){
                    Object value = declaredField.get(obj);
                    if(!isNull){
                        if(value!=null){
                            map.put(camelToUnderline(fieldName),value );
                        }
                    }else{
                        map.put(camelToUnderline(fieldName),value );
                    }
                }
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return map;
    }

    /**
     * collection 是否为空
     * @param collection
     * @return true:is empty ;false:not empty
     */
    public static boolean isEmpty(Collection collection){
       return !isNotEmpty(collection);
    }
    /**
     * collection 是否不为空
     * @param collection
     * @return true:is empty ;false:not empty
     */
    public static boolean isNotEmpty(Collection collection){
        if(collection==null||collection.isEmpty()){
            return false;
        }else{
            return  true;
        }
    }

    //返回订单号
    public static  String createOrderNum(){
        String yyyyMMddHHmmss = DateUtils.format(new Date(), "yyyyMMddHHmmss");
        int i = new Random().nextInt(1000) + 1000;
        return "M"+yyyyMMddHHmmss+i;
    }
}

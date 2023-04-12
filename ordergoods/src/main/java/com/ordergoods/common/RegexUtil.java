package com.ordergoods.common;

import java.util.regex.Pattern;


public class RegexUtil {

    /**
     * 校验名字里面是否有特殊字符
     * @param str
     * @return
     */
    public static boolean checkSpecial(String str){
        String regEx="^.*[/`\\\\~!@#$%^&*()+=|{}':;,\\[\\]<>?！￥…（）【】‘；：”“’。，、？]+.*$";
        return Pattern.matches(regEx, str);
    }



    /**
     * 校验是否为手机号
     * @param mobile
     * @return true 手机号
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[345789]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }



    /**
     * 校验字符串长度
     * @param
     * @return true 长度在范围内
     */
    public static boolean checkStrlen(String str,int m) {
        String regex = "^.{0"+","+m+"}$";
        return Pattern.matches(regex,str);
    }


    public static void main(String[] args){
        String str = "1234";
        boolean matches = RegexUtil.checkStrlen(str,2);
        System.out.println(str +" , 校验结果 ："+matches);
    }
}

package com.ordergoods.common;


 // 统一返回前端结果包装类：封装了一些静态方法，避免重复创建ResponseBean对象，接口返回时，将结果封装成一个统一格式的响应对象进行返回
public class ResultUtil {
    //构造成功响应对象,包含commonEnum枚举类型参数中定义的状态码和信息
    public static ResponseBean success(CommonEnum commonEnum){
        return new ResponseBean(true,null,commonEnum);
    }
    //构造失败响应对象，包含commonEnum枚举类型参数中定义的状态码和信息
    public static ResponseBean error(CommonEnum commonEnum){
        return new ResponseBean(false,null,commonEnum);
    }
    //构造带数据和消息的成功响应对象，其中包含了data数据、msg信息和成功状态码。
    public static ResponseBean success( Object data, String msg){
        return new ResponseBean(true,data,CommonEnum.SUCCESS_OPTION.getCode(),msg);
    }
    //构造带数据的成功响应对象，包含了data数据和默认的成功状态码和信息
    public static ResponseBean successData( Object data){
        return new ResponseBean(true,data,CommonEnum.SUCCESS_OPTION);
    }
   //构造带消息的成功响应对象，包含了msg信息和默认的成功状态码
    public static ResponseBean success( String msg){
        return new ResponseBean(true,null,CommonEnum.SUCCESS_OPTION.getCode(),msg);
    }
    //构造不带数据和消息的成功响应对象，不包含任何数据或信息，仅包含默认的成功状态码和信息
    public static ResponseBean success( ){
        return new ResponseBean(true,null,CommonEnum.SUCCESS_OPTION);
    }
    //构造带数据和消息的失败响应对象，包含了data数据、msg信息和系统错误状态码
    public static ResponseBean error( Object data, String msg){
        return new ResponseBean(false,data,CommonEnum.SYSTEM_ERROR.getCode(),msg);
    }
    //构造带数据的失败响应对象，data数据和系统错误状态码和信息
    public static ResponseBean errorData( Object data){
        return new ResponseBean(false,data,CommonEnum.SYSTEM_ERROR);
    }
    //构造带消息的失败响应对象，msg信息和系统错误状态码
    public static ResponseBean error( String msg){
        return new ResponseBean(false,null,CommonEnum.SYSTEM_ERROR.getCode(),msg);
    }
    //构造不带数据和消息的失败响应对象，包含系统错误状态码和信息
    public static ResponseBean error( ){
        return new ResponseBean(false,null,CommonEnum.SYSTEM_ERROR);
    }
}

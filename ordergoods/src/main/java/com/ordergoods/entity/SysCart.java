package com.ordergoods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author yan
 */
@Data//Lombok 注解，自动生成 Java 类的 getter、setter 方法、toString() 方法、equals() 方法和 hashCode() 方法，简化 Java 类的定义
@EqualsAndHashCode(callSuper = false)//排除继承自父类的属性对equals(Object other)和hashCode()的影响
@Accessors(chain = true)//生成的setter方法会返回它所在对象本身（即this），以支持链式写法,生成链式访问器
@TableName("tb_sys_cart")
@ApiModel(value="SysCart对象", description="购物车表")
public class SysCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)//数据库表这个属性不存在，只是java实体类，查询购物车信息时可以关联查询商品信息，并将商品信息封装在goods属性中返回，而不是把商品信息存入数据库表中。因此，在数据库中并没有名为goods的字段
    private SysGoods goods;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "下单时商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "保存商家ID")
    private String field0;

    @ApiModelProperty(value = "保存商家名称")
    private String field1;

    @ApiModelProperty(value = "冗余字段2")
    private String field2;

    @ApiModelProperty(value = "冗余字段3")
    private String field3;

    @ApiModelProperty(value = "冗余字段4")
    private String field4;

    @ApiModelProperty(value = "冗余字段5")
    private String field5;

    @ApiModelProperty(value = "冗余字段")
    private String field6;

    @ApiModelProperty(value = "冗余字段")
    private String field7;

    @ApiModelProperty(value = "冗余字段")
    private String field8;

    @ApiModelProperty(value = "冗余字段")
    private String field9;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}

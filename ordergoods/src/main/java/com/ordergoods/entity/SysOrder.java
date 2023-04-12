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
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author yan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sys_order")
@ApiModel(value="SysOrder对象", description="订单表")
public class SysOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<SysOrderItem> orderItemList;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String code;

    @ApiModelProperty(value = "下单用户ID")
    private Long userId;

    @ApiModelProperty(value = "下单用户名称")
    private String userName;

    @ApiModelProperty(value = "订单状态：0 待付款；1.待发货；2.已发货；3.已评价；")
    private Integer state;

    @ApiModelProperty(value = "总价-最后实际支付价格")
    private BigDecimal money;

    @ApiModelProperty(value = "买家备注")
    private String remark;

    @ApiModelProperty(value = "订单对应的地址")
    private String address;

    @ApiModelProperty(value = "用户订单评价")
    private String appraise;

    @ApiModelProperty(value = "冗余字段")
    private String field0;

    @ApiModelProperty(value = "订单对应的地址id")
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

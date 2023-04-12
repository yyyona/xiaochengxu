package com.ordergoods.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户收货地址表
 * </p>
 *
 * @author yan
 * @since 2023-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user_address")
@ApiModel(value="UserAddress对象", description="用户收货地址表")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属用户名称")
    private String userName;

    @ApiModelProperty(value = "所属用户ID")
    private Long userId;

    @ApiModelProperty(value = "收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "收货人名称")
    private String receiverName;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "是否默认：0 否；1 是")
    private Integer isDefault;

    @ApiModelProperty(value = "省份名称")
    private String provinceName;

    @ApiModelProperty(value = "省份编码")
    private String provinceCode;

    @ApiModelProperty(value = "地市名称")
    private String cityName;

    @ApiModelProperty(value = "地市编码")
    private String cityCode;

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "区域编码")
    private String areaCode;

    @ApiModelProperty(value = "完整详细地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    private String field0;

    @ApiModelProperty(value = "冗余字段1")
    private String field1;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}

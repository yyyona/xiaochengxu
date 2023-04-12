package com.ordergoods.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 附件表，存放文件
 * </p>
 *
 * @author yan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sys_file")
@ApiModel(value="SysFile对象", description="附件表，存放文件")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "原始文件名")
    private String fileName;

    @ApiModelProperty(value = "所属对象ID")
    private Long objectId;

    @ApiModelProperty(value = "文件分类编码：0000-用户头像；0001--商品图片")
    private String categoryCode;

    @ApiModelProperty(value = "附件类型")
    private String fileType;

    @ApiModelProperty(value = "文件保存路径")
    private String fileUrl;

    @ApiModelProperty(value = "文件大小单位KB")
    private String fileSize;

    @ApiModelProperty(value = "实际保存的文件名字")
    private String saveName;

    @ApiModelProperty(value = "图片主题")
    private String field0;

    @ApiModelProperty(value = "冗余字段1")
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

    private LocalDateTime updateTime;


}

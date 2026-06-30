package com.lnsoft.ipc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@TableName("llq_favorites_nav")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IpcFavoritesNav对象", description = "工控机管控--收藏导航表")
public class IpcFavoritesNav extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "终端IP")
    private String ip;

}
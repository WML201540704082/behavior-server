/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.safeaccess.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author Idevelop
 * @since 2025-04-19
 */
@Data
@TableName("user_access")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Access对象", description = "Access对象")
public class SdnUserAccess extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private String company;
    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private String department;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型")
    private String deviceId;
    /**
     * 所属子网ID
     */
    @ApiModelProperty(value = "所属子网ID")
    private String subnetId;
    /**
     * 认证用户
     */
    @ApiModelProperty(value = "认证用户")
    private String authUser;
    /**
     * 认证密码
     */
    @ApiModelProperty(value = "认证密码")
    private String authPassword;
    /**
     * mac地址
     */
    @ApiModelProperty(value = "mac地址")
    private String macAddress;
    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ipAddress;
    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码")
    private String sbbm;
    /**
     * 入网开始时间
     */
    @ApiModelProperty(value = "入网开始时间")
    private String startTime;
    /**
     * 允许入网时间
     */
    @ApiModelProperty(value = "允许入网时间")
    private String allowDays;
    /**
     * 是否启用802.1X接入认证（0 不认证，1 802.1X，2 mac）
     */
    @ApiModelProperty(value = "是否启用802.1X接入认证（0 不认证，1 802.1X，2 mac）")
    private String is802;
    /**
     * 用户全名
     */
    @ApiModelProperty(value = "用户全名")
    private String fullUsername;
    /**
     * 是否认证成功（0 未认证，1 已认证）
     */
    @ApiModelProperty(value = "是否认证成功（0 未认证，1 已认证）")
    private String isAccess;
    /**
     * 同步时间（数据写入此表的时间）
     */
    @ApiModelProperty(value = "同步时间（数据写入此表的时间）")
    private String syncTime;
    /**
     * 同步标识（A 新增，U 更新，D删除，C 变更）
     */
    @ApiModelProperty(value = "同步标识（A 新增，U 更新，D删除，C 变更）")
    private String syncSign;
    /**
     * 数据读取状态（0 未读，1 已读）
     */
    @ApiModelProperty(value = "数据读取状态（0 未读，1 已读）")
    private String readState;
    /**
     * 数据来源（0 一体化平台，1 SDN第三方）
     */
    @ApiModelProperty(value = "数据来源（0 一体化平台，1 SDN第三方）")
    private String dataFrom;
    /**
     * 子网对应的vlanid号
     */
    @ApiModelProperty(value = "子网对应的vlanid号")
    private String vlanId;


}

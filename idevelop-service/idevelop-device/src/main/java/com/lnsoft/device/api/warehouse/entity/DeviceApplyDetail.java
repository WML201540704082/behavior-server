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
package com.lnsoft.device.api.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 设备申请单设备详情实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_device_apply_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceApplyDetail对象", description = "设备申请单设备详情")
public class DeviceApplyDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 设备申请单id
	 */
	@ApiModelProperty(value = "设备申请单id")
	private String applyId;
	/**
	 * 使用人
	 */
	@ApiModelProperty(value = "使用人")
	private String userName;
	/**
	 * 使用人联系方式
	 */
	@ApiModelProperty(value = "使用人联系方式")
	private String userPhone;
	/**
	 * 使用人身份证号
	 */
	@ApiModelProperty(value = "使用人身份证号")
	private String userCard;
	/**
	 * 安装地点
	 */
	@ApiModelProperty(value = "安装地点")
	private String address;
	/**
	 * 领用时间
	 */
	@ApiModelProperty(value = "领用时间")
	private Date userTime;
	/**
	 * 是否公用 0 公用 1 个人
	 */
	@ApiModelProperty(value = "是否公用 0 公用 1 个人")
	private Integer userType;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 旧设备CMDB的id
	 */
	@ApiModelProperty(value = "旧设备CMDB的id")
	private String oldDeviceId;
	/**
	 * 旧设备CMDB的Cid
	 */
	@ApiModelProperty(value = "旧设备CMDB的Cid")
	private String oldDeviceCid;
	/**
	 * 旧设备CMDB的UUid
	 */
	@ApiModelProperty(value = "旧设备CMDB的UUid")
	private String oldDeviceUuid;
	/**
	 * 旧设备设备编码
	 */
	@ApiModelProperty(value = "旧设备设备编码")
	private String oldDeviceCode;
	/**
	 * 旧设备设备名称
	 */
	@ApiModelProperty(value = "旧设备设备名称")
	private String oldDeviceName;
	/**
	 * 旧设备ip
	 */
	@ApiModelProperty(value = "旧设备ip")
	private String oldDeviceIp;
	/**
	 * 旧设备使用人
	 */
	@ApiModelProperty(value = "旧设备使用人")
	private String oldDeviceUser;
	/**
	 * 旧MAC地址
	 */
	@ApiModelProperty(value = "旧MAC地址")
	private String deviceOldMac;
	/**
	 * 旧设备所属子网id
	 */
	@ApiModelProperty(value = "旧设备所属子网id")
	private String deviceSubnet;
	/**
	 * 旧设备所属子网名称
	 */
	@ApiModelProperty(value = "旧设备所属子网名称")
	private String deviceSubnetName;
	/**
	 * 旧设备使用人联系方式
	 */
	@ApiModelProperty(value = "旧设备使用人联系方式")
	private String oldUserPhone;
	/**
	 * 旧设备使用人身份证号
	 */
	@ApiModelProperty(value = "旧设备使用人身份证号")
	private String oldUserCard;
	/**
	 * 旧设备安装地点
	 */
	@ApiModelProperty(value = "旧设备安装地点")
	private String oldAddress;
	/**
	 * 旧设备是否公用 0 公用 1 个人
	 */
	@ApiModelProperty(value = "旧设备是否公用 0 公用 1 个人")
	private Integer oldUserType;
	/**
	 * 认证账号
	 */
	@ApiModelProperty(value = "认证账号")
	private String authAccount;
	/**
	 * 认证密码
	 */
	@ApiModelProperty(value = "认证密码")
	private String authPassword;
	/**
	 * 交换机ip
	 */
	@ApiModelProperty(value = "交换机ip")
	private String switchesIp;
	/**
	 * 交换机密码
	 */
	@ApiModelProperty(value = "交换机密码")
	private String switchesPassword;
}

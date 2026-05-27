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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备出库单设备详情实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@TableName("idevelop_device_outbound_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOutboundDetail对象", description = "设备出库单设备详情")
public class DeviceOutboundDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 设备出库单id
	 */
	@ApiModelProperty(value = "设备出库单id")
	private String outboundId;
	/**
	 * 设备CMDB的id
	 */
	@ApiModelProperty(value = "设备CMDB的id")
	private String deviceId;
	/**
	 * 设备CMDB的Cid
	 */
	@ApiModelProperty(value = "设备CMDB的Cid")
	private String deviceCid;
	/**
	 * 设备CMDB的UUid
	 */
	@ApiModelProperty(value = "设备CMDB的UUid")
	private String deviceUuid;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	/**
	 * ERP资产编号
	 */
	@ApiModelProperty(value = "ERP资产编号")
	private String erpAssetCode;
	/**
	 * 出厂序列号
	 */
	@ApiModelProperty(value = "出厂序列号")
	private String factoryNumber;
	/**
	 * 领用时间
	 */
	@DateTimeFormat(pattern = "yyyy-Mm-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-Mm-dd HH:mm:ss")
	@ApiModelProperty(value = "领用时间")
	private Date userTime;
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
	 * 是否公用 0 公用 1 个人
	 */
	@ApiModelProperty(value = "是否公用 0 公用 1 个人")
	private Integer userType;
	/**
	 * 设备来源
	 */
	@ApiModelProperty(value = "设备来源")
	private String deviceSource;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;


}

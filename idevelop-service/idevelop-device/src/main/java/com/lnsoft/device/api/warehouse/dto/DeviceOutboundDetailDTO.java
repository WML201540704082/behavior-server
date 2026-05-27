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
package com.lnsoft.device.api.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.device.api.warehouse.entity.DeviceOutboundDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备出库单设备详情数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceOutboundDetailDTO extends DeviceOutboundDetail {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属子网id
	 */
	@ApiModelProperty(value = "所属子网id")
	private String deviceSubnet;
	/**
	 * 所属子网名称
	 */
	@ApiModelProperty(value = "所属子网名称")
	private String deviceSubnetName;
	/**
	 * 标准全称
	 */
	@ApiModelProperty(value = "标准全称")
	private String fullName;
	/**
	 * 售后服务到期时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "售后服务到期时间")
	private Date afterSaleExpDate;
	/**
	 * 品牌
	 */
	@ApiModelProperty(value = "品牌")
	private String brand;
	/**
	 * 系列
	 */
	@ApiModelProperty(value = "系列")
	private String series;
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String deviceModel;
	/**
	 * 是否信创设备 0 是 1 否
	 */
	@ApiModelProperty(value = "是否信创设备 0 是 1 否")
	private String isItal;
	/**
	 * 机房id
	 */
	@ApiModelProperty(value = "机房id")
	private Long roomId;
	/**
	 * 机房名称
	 */
	@ApiModelProperty(value = "机房名称")
	private String roomName;
	/**
	 * 机柜id
	 */
	@ApiModelProperty(value = "机柜id")
	private Long cabinetsId;
	/**
	 * 机柜名称
	 */
	@ApiModelProperty(value = "机柜名称")
	private String cabinetsName;
	/**
	 * 机架id
	 */
	@ApiModelProperty(value = "机架id")
	private Long racksId;
	/**
	 * 机架名称
	 */
	@ApiModelProperty(value = "机架名称")
	private String racksName;

}

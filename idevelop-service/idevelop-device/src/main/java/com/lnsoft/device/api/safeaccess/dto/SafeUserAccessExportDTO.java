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
package com.lnsoft.device.api.safeaccess.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实体类
 *
 * @author Idevelop
 * @since 2025-04-19
 */
@Data
public class SafeUserAccessExportDTO {

    private static final long serialVersionUID = 1L;

	@ExcelProperty("所属单位")
	private String companyName;

	@ExcelProperty("所属部门")
	private String deptName;


	@ExcelProperty("设备编码")
	private String sbbm;


	@ExcelProperty("所属子网")
	private String subnetName;

	@ExcelProperty("设备类型")
	private String deviceTypeName;


	@ExcelProperty("mac地址")
	private String macAddress;

	@ExcelProperty("ip地址")
	private String ipAddress;

	@ExcelProperty("安装地点")
    private String address;

	@ExcelProperty("联系电话")
    private String phone;

	@ExcelProperty("认证用户")
	private String approveuUser;

	@ExcelProperty("认证密码")
	private String approveuPassword;

	@ExcelProperty("状态")
	private String code;

	@ExcelProperty("入网开始时间")
	private String startTime;

	@ExcelProperty("允许入网时间")
	private String allowDays;

	@ApiModelProperty(value = "是否启用802.1X接入认证（0 不认证，1 802.1X，2 mac）")
	@ExcelProperty("认账方式")
	private String is802;

	@ApiModelProperty(value = "终端是否认证（0 未认证，1 已认证）")
	@ExcelProperty("终端是否认证")
	private String isAccess;

	@ApiModelProperty(value = "责任人")
	@ExcelProperty("责任人")
	private String miUser;

	@ApiModelProperty(value = "使用人")
	@ExcelProperty("使用人")
	private String miChargeUser;





}

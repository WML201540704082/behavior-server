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
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 交换机管理实体类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
@ApiModel(value = "SafeaccessSwitcheDTO对象", description = "交换机管理")
public class SafeaccessSwitcheDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 交换机标签
	 */
	@ExcelProperty(value = "交换机标签")
	private String swName;

	/**
	 * 制造商
	 */
	@ExcelProperty(value = "制造商")
	private String swMaker;
	/**
	 * 品牌
	 */
	@ExcelProperty(value = "品牌")
	private String swFirm;

	/**
	 * 系列
	 */
	@ExcelProperty(value = "系列")
	private String swSeries;
	/**
	 * 型号
	 */
	@ExcelProperty(value = "型号")
	private String swModel;
	/**
	 * 安装位置
	 */
	@ExcelProperty(value = "安装地点")
	private String swWhere;
	/**
	 * 用途
	 */
	@ExcelProperty(value = "用途")
	private String swPurpose;
	/**
	 * 所属单位
	 */
	@ExcelProperty(value = "所属单位")
	private String company;
	/**
	 * 交换机IP
	 */
	@ExcelProperty(value = "交换机IP(NASIP)")
	private String swIp;
	/**
	 * 交换机密码
	 */
	@ExcelProperty(value = "交换机密码(NASSECRET)")
	private String swPass;
	/**
	 * 网络层次：核心/汇聚/接入
	 */
	@ExcelProperty(value = "网络设备用途类型")
	private String is3;
	/**
	 * 工作状态：停用/运行
	 */
	@ExcelProperty(value = "工作状态")
	private String swState;
	/**
	 * 端口数量
	 */
	@ExcelProperty(value = "端口数量")
	private String portsCount;
	/**
	 * 工作vlans号
	 */
	@ExcelProperty(value = "工作vlans号")
	private String vlans;
	/**
	 * 管理ip
	 */
	@ExcelProperty(value = "管理ip")
	private String telIp;
	/**
	 * 管理用户
	 */
	@ExcelProperty(value = "管理用户")
	private String telUser;
	/**
	 * 管理密码
	 */
	@ExcelProperty(value = "管理密码")
	private String telPass;
	/**
	 * 配置密码
	 */
	@ExcelProperty(value = "配置密码")
	private String configPass;
	/**
	 * SNMP版本号
	 */
	@ExcelProperty(value = "SNMP版本号")
	private String snmpVersion;
	/**
	 * 设备编码
	 */
	@ExcelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * SNMP读字符串
	 */
	@ExcelProperty(value = "SNMP读字符串")
	private String snmpReadStr;
	/**
	 * SNMP写字符串
	 */
	@ExcelProperty(value = "SNMP写字符串")
	private String snmpWriteStr;
	/**
	 * 录入人
	 */
	@ExcelProperty(value = "录入人")
	private String fillMan;
	/**
	 * 录入时间
	 */
	@ExcelProperty(value = "录入时间")
	private String fillDate;
	/**
	 * 交换机是否认证 0否1是
	 */
	@ExcelProperty(value = "是否认证")
	private String isAccessSwitch;
	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	private String remark;




}

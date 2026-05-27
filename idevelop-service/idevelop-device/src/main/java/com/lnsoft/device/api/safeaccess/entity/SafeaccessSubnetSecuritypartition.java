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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 子网安全分区管理实体类
 *
 * @author Idevelop
 * @since 2024-03-11
 */
@Data
@TableName("idevelop_safeaccess_subnet_securitypartition")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SafeaccessSubnetSecuritypartition对象", description = "子网安全分区管理")
public class SafeaccessSubnetSecuritypartition extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "sp_id", type = IdType.ASSIGN_ID)
	private String spId;
	/**
	 * 安全分区名称
	 */
	@ApiModelProperty(value = "安全分区名称")
	private String spName;
	/**
	 * 安全分区详细描述
	 */
	@ApiModelProperty(value = "安全分区详细描述")
	private String spDesc;
	/**
	 * 安全分区策略
	 */
	@ApiModelProperty(value = "安全分区策略")
	private String spStrategy;
	/**
	 * mpls vpn id
	 */
	@ApiModelProperty(value = "mpls vpn id")
	private String mplsVpnId;
	/**
	 * mpls vpn name
	 */
	@ApiModelProperty(value = "mpls vpn name")
	private String mplsVpnName;
	/**
	 * mpls vpn 配置信息
	 */
	@ApiModelProperty(value = "mpls vpn 配置信息")
	private String mplsVpnConfiginfo;
	/**
	 * 路由协议
	 */
	@ApiModelProperty(value = "路由协议")
	private String spRoutingprotocol;
	/**
	 * 安全分区附件
	 */
	@ApiModelProperty(value = "安全分区附件")
	private String spEncl;
	/**
	 * 附件1
	 */
	@ApiModelProperty(value = "附件1")
	private String bakCol1;
	/**
	 * 附件2
	 */
	@ApiModelProperty(value = "附件2")
	private String bakCol2;
	/**
	 * 附件3
	 */
	@ApiModelProperty(value = "附件3")
	private String bakCol3;
	/**
	 * 市级机构编码
	 */
	@ApiModelProperty(value = "市级机构编码")
	private String orgCode4;
	/**
	 * 县级机构编码
	 */
	@ApiModelProperty(value = "县级机构编码")
	private String orgCode6;
	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	private String deptId;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty("创建部门")
	private Long createDept;

	@ApiModelProperty(value = "区域编码")
	private String regionCode;

	@ApiModelProperty(value = "部门编码")
	private String deptCode;
}

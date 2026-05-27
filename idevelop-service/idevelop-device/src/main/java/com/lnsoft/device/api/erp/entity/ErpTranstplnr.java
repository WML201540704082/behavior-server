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
package com.lnsoft.device.api.erp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ERP功能位置实体类
 *
 * @author Idevelop
 * @since 2024-03-23
 */
@Data
@TableName("idevelop_erp_transtplnr")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ErpTranstplnr对象", description = "ERP功能位置")
public class ErpTranstplnr extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 功能位置编码
	 */
	@ApiModelProperty(value = "功能位置编码")
	@TableId("trlnr")
	private String trlnr;
	/**
	 * 功能位置名称
	 */
	@ApiModelProperty(value = "功能位置名称")
	private String pltxt;
	/**
	 * 电压等级编码
	 */
	@ApiModelProperty(value = "电压等级编码")
	private String zsbdydj;
	/**
	 * 工厂区域编码, 003省公司 004县公司
	 */
	@ApiModelProperty(value = "工厂区域编码, 003省公司 004县公司")
	private String beber;
	/**
	 * 维护工厂编码
	 */
	@ApiModelProperty(value = "维护工厂编码")
	private String swerk;
	/**
	 * 上级功能位置编码
	 */
	@ApiModelProperty(value = "上级功能位置编码")
	private String tplma;

	/**
	 * 操作标识 C创建 M修改 D删除
	 */
	@ApiModelProperty(value = "操作标识 C创建 M修改 D删除")
	private String operation;

	/**
	 * i6000唯一标识
	 */
	@ApiModelProperty(value = "i6000唯一标识")
	private String i6000Code;

	/**
	 * i6000失败信息
	 */
	@ApiModelProperty(value = "i6000失败信息")
	private String i6000Message;

	/**
	 * ERP同步状态
	 */
	@ApiModelProperty(value = "ERP同步状态 0:同步成功,1同步失败")
	private Integer erpStatus;

	/**
	 * I6000同步状态
	 */
	@ApiModelProperty(value = "I6000同步状态 0:同步成功,1同步失败")
	private Integer i6000Status;


}

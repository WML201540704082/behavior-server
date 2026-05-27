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
package com.lnsoft.device.api.endpoint.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 数据共享接口申请表实体类
 *
 * @author Idevelop
 * @since 2024-07-17
 */
@Data
@TableName("idevelop_endpoint_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EndpointApply对象", description = "数据共享接口申请表")
public class EndpointApply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 工单编号
     */
    @ApiModelProperty(value = "工单编号")
    private String filingNo;

    /**
     * 申请项目组名
     */
    @ApiModelProperty(value = "申请项目组名")
    private String applyTeamName;
    /**
     * 项目组联系方式
     */
    @ApiModelProperty(value = "项目组联系方式")
    private String applyPhone;
    /**
     * 申请原因
     */
    @ApiModelProperty(value = "申请原因")
    private String applyCause;
    /**
     * 申请数量
     */
    @ApiModelProperty(value = "申请数量")
    private Long applyNumber;
    /**
     * 申请地址IP
     */
    @ApiModelProperty(value = "申请地址IP")
    private String applyIp;
    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID")
    private String processInsId;
    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    private String processStatus;
    /**
     * 有效时间
     */
    @ApiModelProperty(value = "有效时间")
    private LocalDate vaildTime;
	/**
	 * 申请人姓名
	 */
	@ApiModelProperty(value = "申请人姓名")
    private String userName;
	/**
	 * 申请人id
	 */
	@ApiModelProperty(value = "申请人id")
    private String userId;

}

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
package com.lnsoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 标准型号库反馈表实体类
 *
 * @author Idevelop
 * @since 2025-04-07
 */
@Data
@TableName("idevelop_standard_model_library")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StandardModelLibrary对象", description = "标准型号库反馈表")
public class StandardModelLibrary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private String id;
    /**
     * 反馈编号
     */
    @ApiModelProperty(value = "反馈编号")
    private String backNum;
    /**
     * 设备分类名称
     */
    @ApiModelProperty(value = "设备分类名称")
    private String deviceCategory;
    /**
     * 设备分类编码
     */
    @ApiModelProperty(value = "设备分类编码")
    private String deviceCategoryCode;
    /**
     * 制造商
     */
    @ApiModelProperty(value = "制造商")
    private String maker;
    /**
     * 制造商编码
     */
    @ApiModelProperty(value = "制造商编码")
    private String makerCode;
    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brand;
    /**
     * 品牌编码
     */
    @ApiModelProperty(value = "品牌编码")
    private String brandCode;
    /**
     * 系列
     */
    @ApiModelProperty(value = "系列")
    private String series;
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;
    /**
     * 是否采纳  0 未采纳 1 已采纳
     */
    @ApiModelProperty(value = "是否采纳  0 未采纳 1 已采纳")
    private String isAccept;
    /**
     * 所属单位名称
     */
    @ApiModelProperty(value = "所属单位名称")
    private String deptName;
    /**
     * 所属单位
     */
    @ApiModelProperty(value = "所属单位")
    private String dept;
    /**
     * 反馈人员id
     */
    @ApiModelProperty(value = "反馈人员id")
    private String userId;
    /**
     * 反馈人员名称
     */
    @ApiModelProperty(value = "反馈人员名称")
    private String userName;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String phone;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 未采纳原因
     */
    @ApiModelProperty(value = "未采纳原因")
    private String noAcceptDetail;
    /**
     * 预计完成时间
     */
    @ApiModelProperty(value = "预计完成时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expectedTime;
    /**
     * 反馈进度
     */
    @ApiModelProperty(value = "反馈进度")
    private String backStatus;
    /**
     * 是否完成  0 否 1 是
     */
    @ApiModelProperty(value = "是否完成  0 否 1 是")
    private String isFinish;


}

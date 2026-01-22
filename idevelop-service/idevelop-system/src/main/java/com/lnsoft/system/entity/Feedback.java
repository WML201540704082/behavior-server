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

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户反馈实体类
 *
 * @author Idevelop
 * @since 2025-03-26
 */
@Data
@TableName("idevelop_feedback")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Feedback对象", description = "用户反馈")
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelIgnore
    @ApiModelProperty(value = "主键ID")
    private String id;
    /**
     * 反馈编号
     */
    @ApiModelProperty(value = "反馈编号")
	@ExcelProperty("反馈编号")
    private String backNum;
	/**
	 * 反馈问题类型
	 */
	@ApiModelProperty(value = "反馈问题类型   0 系统问题  1 用户体验")
	@ExcelProperty("反馈问题类型")
	private String backType;
	/**
	 * 反馈问题简介
	 */
	@ApiModelProperty(value = "反馈问题简介")
	@ExcelProperty("反馈问题简介")
	private String backBriefly;
    /**
     * 反馈问题详述
     */
    @ApiModelProperty(value = "反馈问题详述")
	@ExcelProperty("反馈问题详述")
    private String backDetail;
    /**
     * 是否采纳  0 未采纳 1 已采纳
     */
    @ApiModelProperty(value = "是否采纳  0 未采纳 1 已采纳")
	@ExcelProperty("反馈信息")
    private String isAccept;
    /**
     * 所属单位
     */
	@ExcelIgnore
    @ApiModelProperty(value = "所属单位")
    private String dept;
    /**
     * 反馈人员id
     */
	@ExcelIgnore
    @ApiModelProperty(value = "反馈人员id")
    private String userId;
    /**
     * 反馈人员名称
     */
    @ApiModelProperty(value = "反馈人员名称")
	@ExcelProperty("反馈人员")
    private String userName;
    /**
     * 反馈图片
     */
	@ExcelIgnore
    @ApiModelProperty(value = "反馈图片")
    private String img;
    /**
     * 备注
     */
	@ExcelIgnore
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 未采纳原因
     */
	@ExcelIgnore
    @ApiModelProperty(value = "未采纳原因")
    private String noAcceptDetail;
    /**
     * 反馈进度
     */
    @ApiModelProperty(value = "反馈进度 0 已提交 1 处理中 2 已处理")
	@ExcelProperty("反馈进度")
    private String backStatus;

    /**
     * 所属单位名称
     */
    @ApiModelProperty(value = "所属单位名称")
	@ExcelProperty("所属单位")
    private String deptName;

    /**
     * 联系方式
    */
    @ApiModelProperty(value = "联系方式")
	@ExcelProperty("联系方式")
    private String phone;

	/**
     * 是否完成
    */
    @ApiModelProperty(value = "是否完成  0 否 1 是")
	@ExcelIgnore
    private String isFinish;

    /**
     * 预计完成时间
    */
    @ApiModelProperty(value = "预计完成时间")
	@ExcelIgnore
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectedTime;



}

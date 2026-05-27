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
package com.lnsoft.device.api.cmdb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 三方系统操作日志实体类
 *
 * @author Idevelop
 * @since 2024-03-29
 */
@Data
@TableName("idevelop_triple_api_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TripleApiLog对象", description = "三方系统操作日志")
public class TripleApiLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId("id")
    private String id;
    /**
     * 操作类型,新增,修改
     */
    @ApiModelProperty(value = "操作类型,新增,修改")
    private String value;

	/**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;

	/**
     * 操作类型,新增,修改
     */
    @ApiModelProperty(value = "方法名")
    private String methodName;

	/**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ip;
    /**
     * 三方系统
     */
    @ApiModelProperty(value = "三方系统")
    private String tripleType;

	/**
     * 用户区域编码
     */
    @ApiModelProperty(value = "用户区域编码")
    private String regionCode;

	/**
     * 用户区域名称
     */
    @ApiModelProperty(value = "用户区域名称")
    private String regionName;
    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    private String text;
	/**
     * 返回结果
     */
    @ApiModelProperty(value = "返回结果")
    private String result;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

	@ApiModelProperty("是否成功")
	private Integer success;


}

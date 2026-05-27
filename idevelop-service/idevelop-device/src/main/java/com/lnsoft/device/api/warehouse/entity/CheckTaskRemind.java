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

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.lnsoft.core.tool.utils.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 盘点任务实体类
 *
 * @author Idevelop
 * @since 2024-06-12
 */
@Data
@TableName("idevelop_check_task_remind")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CheckTaskRemind对象", description = "盘点任务")
public class CheckTaskRemind extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String taskName;
    /**
     * 任务开始时间
     */
    @ApiModelProperty(value = "任务开始时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date taskStartTime;
    /**
     * 任务结束时间
     */
    @ApiModelProperty(value = "任务结束时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date taskEndTime;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String user;
    /**
     * 负责人id
     */
    @ApiModelProperty(value = "负责人id")
    private Long userId;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}

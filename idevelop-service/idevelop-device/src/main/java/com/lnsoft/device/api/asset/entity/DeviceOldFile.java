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
package com.lnsoft.device.api.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 设备工单附件实体类
 *
 * @author Idevelop
 * @since 2024-06-25
 */
@Data
@TableName("idevelop_device_old_file")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOldFile对象", description = "设备工单附件")
public class DeviceOldFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;
    /**
     * 附件url
     */
    @ApiModelProperty(value = "附件url")
    private String fileUrl;
    /**
     * 附件名
     */
    @ApiModelProperty(value = "附件名")
    private String fileName;
    /**
     * 附件类型
     */
    @ApiModelProperty(value = "附件类型")
    private String fileType;



}

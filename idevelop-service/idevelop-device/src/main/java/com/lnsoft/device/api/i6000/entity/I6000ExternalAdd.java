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
package com.lnsoft.device.api.i6000.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 外部数据表实体类
 *
 * @author Idevelop
 * @since 2024-06-18
 */
@Data
@TableName("idevelop_i6000_external_add")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000ExternalAdd对象", description = "外部数据表")
public class I6000ExternalAdd extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 外部数据
     */
    @ApiModelProperty(value = "外部数据")
    private String extCode;
    /**
     * I6000唯一ID
     */
    @ApiModelProperty(value = "I6000唯一ID")
    private String extId;
    /**
     * 外部数据值名称
     */
    @ApiModelProperty(value = "外部数据值名称")
    private String extName;
    /**
     * 外部数据值父ID
     */
    @ApiModelProperty(value = "外部数据值父ID")
    private String extPid;
    /**
     * 外部数据值状态
     */
    @ApiModelProperty(value = "外部数据值状态")
    private String extState;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer rn;
  private String matchModelId;
  private String mfrHs;


}

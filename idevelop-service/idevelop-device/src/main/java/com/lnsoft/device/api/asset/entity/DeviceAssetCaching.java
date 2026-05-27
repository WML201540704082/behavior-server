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
import com.lnsoft.core.mp.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Idevelop
 * @since 2024-03-30
 */
@Data
@TableName("idevelop_device_asset_caching")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceAssetCaching对象", description = "DeviceAssetCaching对象")
public class DeviceAssetCaching extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备分类编码
     */
    @ApiModelProperty(value = "设备分类编码")
    private String deviceCategoryCode;
    /**
     * 资产总额-净值
     */
    @ApiModelProperty(value = "资产总额-净值")
    private String assetNetSum;
    /**
     * 资产总额(万元)-原值
     */
    @ApiModelProperty(value = "资产总额(万元)-原值")
    private Double assetOriginalSum;
    /**
     * 设备类型编码
     */
    @ApiModelProperty(value = "设备类型编码")
    private String deviceTypeCode;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String dept;
    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String regionCode;


}

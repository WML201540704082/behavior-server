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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目物料批次表实体类
 *
 * @author Idevelop
 * @since 2024-03-04
 */
@Data
@TableName("idevelop_project_purchase_batch")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectPurchaseBatch对象", description = "项目物料批次表")
public class ProjectPurchaseBatch extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;
    /**
     * wbs主键id
     */
    @ApiModelProperty(value = "wbs主键id")
    private Long wbsId;
    /**
     * wbs元素
     */
    @ApiModelProperty(value = "wbs元素")
    private String wbsCode;
    /**
     * 物料编码
     */
    @ApiModelProperty(value = "物料编码")
    private String materialCode;
    /**
     * 物料名称
     */
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    /**
     * 采购批次
     */
    @ApiModelProperty(value = "采购批次")
    private String purchaseBatch;
    /**
     * 采购订单数量
     */
    @ApiModelProperty(value = "采购订单数量")
    private String purchaseBatchNum;


}

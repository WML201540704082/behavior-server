/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.lnsoft.device.api.stock.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员批量导入I6000数据记录表实体类
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 30)
@HeadFontStyle(color = 0, bold = true)
@ApiModel(value = "I6000ImportLogImportDTO", description = "管理员批量导入I6000数据记录表")
public class I6000ImportLogImportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码")
    @ExcelProperty("设备编码")
    @ColumnWidth(30)
    private String deviceCode;
    /**
     * 导入情况
     */
    @ApiModelProperty(value = "导入情况")
    @ExcelProperty("同步情况")
    @ColumnWidth(100)
    @ContentStyle(wrapped = true, horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER)
    private String deviceInfo;

    /**
     * 同步时间
     */
    @ApiModelProperty(value = "同步时间")
    @ExcelProperty("同步时间")
    @ColumnWidth(30)
    private Date createTime;

}

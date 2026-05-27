package com.lnsoft.device.api.cmdb.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2025/11/2 14:05
 * @Description: I6000Erp
 */
@Data
@NoArgsConstructor
public class I6000ErpImport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty("id")
    private String id;

    @ExcelProperty("uuid")
    private String uuid;

    @ExcelProperty("标准全称")
    private String fullName;

    @ExcelProperty("设备编码")
    private String deviceCode;
}

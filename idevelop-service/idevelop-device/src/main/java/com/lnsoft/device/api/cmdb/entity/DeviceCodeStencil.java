package com.lnsoft.device.api.cmdb.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DeviceCodeStencil implements Serializable {

	private static final long serialVersionUID = 1L;

	@ColumnWidth(30)
	@ExcelProperty("设备编码")
	private String deviceCode;
}

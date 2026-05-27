package com.lnsoft.device.api.stock.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lnsoft.core.mp.base.BaseEntity;
import com.lnsoft.device.annotation.*;
import com.lnsoft.device.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据治理  设备台账实体类
 *
 */
@Data
public class HardwareBasicCmdbDeviceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

}

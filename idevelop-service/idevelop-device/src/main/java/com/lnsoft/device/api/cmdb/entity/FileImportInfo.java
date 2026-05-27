package com.lnsoft.device.api.cmdb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("idevelop_import_file")
@AllArgsConstructor
@NoArgsConstructor
public class FileImportInfo extends BaseEntity {

	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;

	//文件名
	private String fileName;

	//所在行数
	private int rowNum;

	//数据状态 0 解析未完成，1 解析完成
	private int dataStatus;

	//错误信息
	private String errorInfo;

	// 父级id
	private String parentId;
}

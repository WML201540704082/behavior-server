package com.lnsoft.device.api.cmdb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@TableName("idevelop_import_file_parent")
@AllArgsConstructor
@NoArgsConstructor
public class FileImportInfoParent extends BaseEntity {

	//id
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;

	//文件名
	private String fileName;

	//数据总条数
	private int allNum;

	//未解析条数
	private int noNum;

	//已解析条数
	private int yesNum;

	//解析错误条数
	private int errorNum;

	// 设备类型
	private String fileType;

	// 存储的文件
	private byte[] fileInfo;

	// 异常信息文件
	private byte[] errorFileInfo;

	// 是否完成解析
	private int isFinishMind;
}

package com.lnsoft.device.api.cmdb.entity;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("idevelop_export_file")
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo extends BaseEntity {

	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;

	private String fileName;

	private String exportJson;

	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date exportTime;

	private String link;

	private String ossName;

	private Integer nums;
}

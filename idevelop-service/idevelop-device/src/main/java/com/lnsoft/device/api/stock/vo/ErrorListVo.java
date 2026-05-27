package com.lnsoft.device.api.stock.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.device.api.cmdb.entity.FileImportInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.cms.PasswordRecipientId;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorListVo {

	// 入参分页
	private Page page;

	// 用户id
	private String userId;

	// 设备类型
	private String deviceType;

}

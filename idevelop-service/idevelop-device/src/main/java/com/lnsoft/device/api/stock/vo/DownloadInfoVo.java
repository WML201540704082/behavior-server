package com.lnsoft.device.api.stock.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadInfoVo {

	//分页
	private Page page;

	// userID
	private Long userId;

	// 区域
	private String area;
}

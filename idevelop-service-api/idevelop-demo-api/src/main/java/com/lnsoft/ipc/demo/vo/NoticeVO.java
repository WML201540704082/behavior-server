package com.lnsoft.ipc.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lnsoft.ipc.demo.entity.Notice;

/**
 * 通知公告视图类
 *
 * @author guozhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends Notice {

	@ApiModelProperty(value = "通知类型名")
	private String categoryName;

}

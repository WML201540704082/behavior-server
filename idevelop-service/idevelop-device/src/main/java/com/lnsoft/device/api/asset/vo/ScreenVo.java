package com.lnsoft.device.api.asset.vo;

import com.lnsoft.core.mp.support.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bouncycastle.cms.PasswordRecipientId;

@Data
@ApiModel(value = "ScreenVo", description = "ScreenVo")
public class ScreenVo {

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "产权单位")
	private String ownerUnitCode;

	@ApiModelProperty(value = "区域")
	private String area;

	@ApiModelProperty(value = "分页")
	private Query query;

}

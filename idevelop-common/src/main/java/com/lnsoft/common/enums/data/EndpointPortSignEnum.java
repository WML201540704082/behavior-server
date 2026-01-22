package com.lnsoft.common.enums.data;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.enums.IEnum;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public enum EndpointPortSignEnum implements IEnum<JSONObject> {

	READ_CIENTITY_SEARCH("/endpoint/cmdb/read/cientity/search", "配置项列表查询接口"),
	READ_CIENTITY_DEATIL("/endpoint/cmdb/read/cientity/detail", "配置项详情查询接口"),
	READ_CIENTITY_INSERT("/endpoint/cmdb/read/cientity/insert", "配置项新增接口"),
	READ_CIENTITY_UPDATE("/endpoint/cmdb/read/cientity/update", "配置项修改接口"),

	READ_ENUM_ALL("/endpoint/common/read/enum/all", "字典表枚举项查询接口"),
	READ_ENUM_VALUES("/endpoint/common/read/enum/values", "字典表枚举值查询接口"),
	READ_CI_TYPE("/endpoint/common/read/ci/type", "配置类型分类查询接口"),
	READ_CI_ATTR("/endpoint/common/read/ci/attr", "配置类型属性信息查询接口"),
	READ_REGION_LIST("/endpoint/common/read/region/list", "山东省用户区域编码查询接口"),
	READ_ENUM_CASCADE("/endpoint/common/read/enum/cascade", "字典级联查询接口"),

	WORK_ORDER_CREATE("/endpoint/work/order/create", "新增工单接口"),
	WORK_ORDER_STATUS("/endpoint/work/order/status", "查看工单状态接口"),

	SAFEACCESS_USER_LIST("/extrenal/credit/safeaccess/user/list", "查询终端用户入网信息列表接口"),
	SAFEACCESS_SUBNET_LIST("/extrenal/credit/safeaccess/subnet/list", "查询获取子网管理表列表接口"),

	DEFAULT_SIGN("/default", "默认接口");

	private String value;
	private String text;

	EndpointPortSignEnum(String value, String text) {
		this.value = value;
		this.text = text;
	}

	@Override
	public List<JSONObject> getValueTextList() {
		List<JSONObject> returnList = new ArrayList<>();
		for (EndpointPortSignEnum input : EndpointPortSignEnum.values()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", input.getValue());
			jsonObj.put("text", input.getText());
			returnList.add(jsonObj);
		}
		return returnList;
	}

}

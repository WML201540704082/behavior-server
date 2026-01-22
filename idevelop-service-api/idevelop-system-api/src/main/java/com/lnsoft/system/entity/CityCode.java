package com.lnsoft.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CityCode对象", description = "CityCode对象")
public class CityCode {

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "id")
	private Long id;
	@ApiModelProperty(value = "服务url")
	private String url;// 服务url
	@ApiModelProperty(value = "编码")
	private String selfCode;// 编码
	@ApiModelProperty(value = "名称")
	private String selfName;// 名称
	@ApiModelProperty(value = " 2市 3县")
	private String type;// 1省 2市 3县
	@ApiModelProperty(value = "上级编码")
	private String parentCode;// 上级编码
	@ApiModelProperty(value = "上级名称")
	private String parentName;// 上级名称

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CityCode{");
		sb.append("id=").append(id);
		sb.append(", url='").append(url).append('\'');
		sb.append(", selfCode='").append(selfCode).append('\'');
		sb.append(", selfName='").append(selfName).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append(", parentCode='").append(parentCode).append('\'');
		sb.append(", parentName='").append(parentName).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public CityCode() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSelfCode() {
		return selfCode;
	}

	public void setSelfCode(String selfCode) {
		this.selfCode = selfCode;
	}

	public String getSelfName() {
		return selfName;
	}

	public void setSelfName(String selfName) {
		this.selfName = selfName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

package com.lnsoft.system.vo;

import com.lnsoft.system.entity.CityCode;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "CityCodeVO对象", description = "CityCodeVO对象")
public class CityCodeVO extends CityCode {

	/**
	 * 是否有子孙节点
	 */
	private Boolean hasChildren;
	/**
	 * 子孙节点
	 */
	private List<CityCodeVO> children;

	public CityCodeVO() {
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public Boolean hasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<CityCodeVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return children;
	}

	public void setChildren(List<CityCodeVO> children) {
		this.children = children;
	}
}

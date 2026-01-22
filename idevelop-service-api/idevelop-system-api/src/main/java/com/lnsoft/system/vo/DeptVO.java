
package com.lnsoft.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lnsoft.core.tool.node.INode;
import com.lnsoft.system.entity.Dept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 视图实体类
 *
 * @author guozhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeptVO对象", description = "DeptVO对象")
public class DeptVO extends Dept implements INode<DeptVO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<DeptVO> children;

	@Override
	public List<DeptVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		if(this.children!=null){
			Collections.sort(this.children,(o1, o2)->{
				//类型一致按照排序
				if( (o1.getType()+"").equals(o2.getType())){
					if (o1.getSort() > o2.getSort()) {
						return 1;
					}
				}else{
					//类型不一致 部门优先
					if("CORP".equals(o1.getType())){
						return 1;
					}
				}
				return -1;
			});
		}
		return this.children;
	}

	/**
	 * 上级部门
	 */
	private String parentName;

}


package com.lnsoft.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * GrantVO
 *
 * @author guozhao
 */
@Data
public class GrantVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "roleIds集合")
	private List<Long> roleIds;

	@ApiModelProperty(value = "menuIds集合")
	private List<Long> menuIds;

	@ApiModelProperty(value = "dataScopeIds集合")
	private List<Long> dataScopeIds;

}

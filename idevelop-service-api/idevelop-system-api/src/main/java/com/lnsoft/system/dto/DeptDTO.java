
package com.lnsoft.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lnsoft.system.entity.Dept;

/**
 * 数据传输对象实体类
 *
 * @author guozhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends Dept {
	private static final long serialVersionUID = 1L;

}

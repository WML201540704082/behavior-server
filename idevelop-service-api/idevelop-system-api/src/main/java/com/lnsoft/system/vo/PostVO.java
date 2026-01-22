
package com.lnsoft.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lnsoft.system.entity.Post;

/**
 * 岗位表视图实体类
 *
 * @author guozhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PostVO对象", description = "岗位表")
public class PostVO extends Post {
	private static final long serialVersionUID = 1L;

	/**
	 * 岗位分类名
	 */
	private String categoryName;

}

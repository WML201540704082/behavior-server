
package com.lnsoft.system.wrapper;

import com.lnsoft.system.vo.PostVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.entity.Post;
import com.lnsoft.system.service.IDictService;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
		String categoryName = dictService.getValue("post_category", post.getCategory());
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}

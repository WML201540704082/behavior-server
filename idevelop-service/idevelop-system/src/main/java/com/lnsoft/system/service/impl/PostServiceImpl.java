
package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lnsoft.system.vo.PostVO;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.entity.Post;
import com.lnsoft.system.mapper.PostMapper;
import com.lnsoft.system.service.IPostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位表 服务实现类
 *
 * @author guozhao
 */
@Service
public class PostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements IPostService {

	@Override
	public IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post) {
		return page.setRecords(baseMapper.selectPostPage(page, post));
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		List<Post> postList = baseMapper.selectList(Wrappers.<Post>query().lambda().eq(Post::getTenantId, tenantId).in(Post::getPostName, Func.toStrList(postNames)));
		if (postList != null && postList.size() > 0) {
			return postList.stream().map(post -> Func.toStr(post.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return baseMapper.getPostNames(Func.toLongArray(postIds));
	}

}

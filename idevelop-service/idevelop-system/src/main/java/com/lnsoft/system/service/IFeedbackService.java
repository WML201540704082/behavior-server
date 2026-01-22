/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.system.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Feedback;
import com.lnsoft.system.vo.FeedbackVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 用户反馈 服务类
 *
 * @author Idevelop
 * @since 2025-03-26
 */
public interface IFeedbackService extends BaseService<Feedback> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param feedback
	 * @return
	 */
	IPage<Feedback> selectFeedbackPage(IPage<Feedback> page, FeedbackVO feedback);

	R<List<Dept>> getDeptList();

	/**
	 * 数据填充
	 * @return
	 */
	R<Feedback> load();

}

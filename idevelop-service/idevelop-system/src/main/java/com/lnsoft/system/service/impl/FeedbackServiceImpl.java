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
package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Feedback;
import com.lnsoft.system.mapper.DeptMapper;
import com.lnsoft.system.mapper.FeedbackMapper;
import com.lnsoft.system.service.IFeedbackService;
import com.lnsoft.system.vo.FeedbackVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户反馈 服务实现类
 *
 * @author Idevelop
 * @since 2025-03-26
 */
@Service
public class FeedbackServiceImpl extends BaseServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {
	@Resource
	private DeptMapper deptMapper;

	private static final String FK = "FK";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final AtomicInteger COUNTER = new AtomicInteger(1);

//	@Resource
//	private IDeviceOrderClient deviceOrderClient;

	@Override
	public IPage<Feedback> selectFeedbackPage(IPage<Feedback> page, FeedbackVO feedback) {
		LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.likeRight(StringUtil.isNotBlank(feedback.getBackNum()), Feedback::getBackNum, feedback.getBackNum())
			.eq(StringUtil.isNotBlank(feedback.getDept()), Feedback::getDept, feedback.getDept())
			.likeRight(StringUtil.isNotBlank(feedback.getUserName()), Feedback::getUserName, feedback.getUserName())
			.between(ObjectUtil.isNotEmpty(feedback.getStartTime()) && ObjectUtil.isNotEmpty(feedback.getEndTime()), Feedback::getCreateTime, feedback.getStartTime(), feedback.getEndTime())
			.eq(StringUtil.isNotBlank(feedback.getBackStatus()), Feedback::getBackStatus, feedback.getBackStatus())
			.eq(StringUtil.isNotBlank(feedback.getIsAccept()), Feedback::getIsAccept, feedback.getIsAccept())
			.like(StringUtil.isNotBlank(feedback.getBackDetail()), Feedback::getBackDetail, feedback.getBackDetail())
			.orderByDesc(Feedback::getCreateTime);
		return baseMapper.selectPage(page, queryWrapper);

	}

	@Override
	public R<List<Dept>> getDeptList() {
		List<Dept> list = deptMapper.getAllCorp();
		return R.data(list);
	}

	@Override
	public R<Feedback> load() {
//		IdevelopUser user = SecureUtil.getUser();
//		Feedback feedback = new Feedback();
//		NumberDataDTO numberDataDTO = new NumberDataDTO();
//		numberDataDTO.setRegionCode(user.getRegionCode());
//		numberDataDTO.setOrderType(FK);
//		feedback.setBackNum(deviceOrderClient.createOrderNumber(numberDataDTO));
//		feedback.setCreateTime(new Date());
//		feedback.setDept(user.getCorpId());
//		feedback.setDeptName(user.getCorpName());
//		feedback.setUserName(user.getUserName());
		return R.data(null);
	}
}

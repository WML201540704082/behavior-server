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
import com.lnsoft.system.entity.StandardModelLibrary;
import com.lnsoft.system.mapper.StandardModelLibraryMapper;
import com.lnsoft.system.service.IStandardModelLibraryService;
import com.lnsoft.system.vo.StandardModelLibraryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 标准型号库反馈表 服务实现类
 *
 * @author Idevelop
 * @since 2025-04-07
 */
@Service
public class StandardModelLibraryServiceImpl extends BaseServiceImpl<StandardModelLibraryMapper, StandardModelLibrary> implements IStandardModelLibraryService {

	private static final String BZXHK = "BZXHK";
//	@Resource
//	private IDeviceOrderClient deviceOrderClient;

	@Override
	public IPage<StandardModelLibrary> selectStandardModelLibraryPage(IPage<StandardModelLibrary> page, StandardModelLibraryVO standardModelLibrary) {
		LambdaQueryWrapper<StandardModelLibrary> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.likeRight(StringUtil.isNotBlank(standardModelLibrary.getBackNum()), StandardModelLibrary::getBackNum, standardModelLibrary.getBackNum())
			.eq(StringUtil.isNotBlank(standardModelLibrary.getDept()), StandardModelLibrary::getDept, standardModelLibrary.getDept())
			.likeRight(StringUtil.isNotBlank(standardModelLibrary.getUserName()), StandardModelLibrary::getUserName, standardModelLibrary.getUserName())
			.between(ObjectUtil.isNotEmpty(standardModelLibrary.getStartTime()) && ObjectUtil.isNotEmpty(standardModelLibrary.getEndTime()), StandardModelLibrary::getCreateTime, standardModelLibrary.getStartTime(), standardModelLibrary.getEndTime())
			.eq(StringUtil.isNotBlank(standardModelLibrary.getBackStatus()), StandardModelLibrary::getBackStatus, standardModelLibrary.getBackStatus())
			.eq(StringUtil.isNotBlank(standardModelLibrary.getIsAccept()), StandardModelLibrary::getIsAccept, standardModelLibrary.getIsAccept())
			.orderByDesc(StandardModelLibrary::getCreateTime);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public R<StandardModelLibrary> load() {
//		IdevelopUser user = SecureUtil.getUser();
//		StandardModelLibrary standardModelLibrary = new StandardModelLibrary();
//		NumberDataDTO numberDataDTO = new NumberDataDTO();
//		numberDataDTO.setRegionCode(user.getRegionCode());
//		numberDataDTO.setOrderType(BZXHK);
//		standardModelLibrary.setBackNum(deviceOrderClient.createOrderNumber(numberDataDTO));
//		standardModelLibrary.setCreateTime(new Date());
//		standardModelLibrary.setDept(user.getCorpId());
//		standardModelLibrary.setDeptName(user.getCorpName());
//		standardModelLibrary.setUserName(user.getUserName());
		return R.data(null);
	}

}

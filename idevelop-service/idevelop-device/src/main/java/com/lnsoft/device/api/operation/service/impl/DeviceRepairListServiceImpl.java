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
package com.lnsoft.device.api.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.device.vo.DeviceRepairListVO;
import com.lnsoft.device.api.operation.mapper.DeviceRepairListMapper;
import com.lnsoft.device.api.operation.service.IDeviceRepairListService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 设备报修详情表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@Service
public class DeviceRepairListServiceImpl extends BaseServiceImpl<DeviceRepairListMapper, DeviceRepairList> implements IDeviceRepairListService {

	@Override
	public IPage<DeviceRepairListVO> selectDeviceRepairListPage(IPage<DeviceRepairListVO> page, DeviceRepairListVO deviceRepairList) {
		return page.setRecords(baseMapper.selectDeviceRepairListPage(page, deviceRepairList));
	}

	@Override
	public void deleteByRepairId(String repairId) {
		LambdaQueryWrapper<DeviceRepairList> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(DeviceRepairList::getRepairId,repairId);
		int delete = baseMapper.delete(wrapper);
	}

	@Override
	public IPage<DeviceRepairList> selectByRepairId(DeviceRepairList deviceRepairList, Query query) {
		LambdaQueryWrapper<DeviceRepairList> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceRepairList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		queryWrapper.eq(DeviceRepairList::getRepairId,deviceRepairList.getRepairId());
		return baseMapper.selectPage(Condition.getPage(query),queryWrapper);
	}

	@Override
	public Integer insertDevice(DeviceRepairList deviceRepairList) {
		deviceRepairList.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		return baseMapper.insert(deviceRepairList);
	}

	@Override
	public List<DeviceRepairList> getByDeviceCode(String deviceCode) {
		return baseMapper.getByDeviceCode(deviceCode);
	}

}

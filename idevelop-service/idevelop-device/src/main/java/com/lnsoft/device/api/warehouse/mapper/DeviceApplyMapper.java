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
package com.lnsoft.device.api.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.warehouse.dto.DeviceApplyDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceApply;
import com.lnsoft.device.api.warehouse.vo.DeviceApplyVO;

import java.util.List;

/**
 * 设备申请表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-06
 */
public interface DeviceApplyMapper extends BaseMapper<DeviceApply> {

	/**
	 * 分页 设备申请表
	 *
	 * @param deviceApplyDTO 查询条件
	 * @param page           分页信息
	 * @return IPage
	 */
	IPage<DeviceApplyVO> selectDeviceApplyList(IPage<DeviceApply> page, DeviceApplyDTO deviceApplyDTO);
	/**
	 * 根据设备编码查询设备申请记录
	 *
	 */
	List<DeviceApply> getApply(String deviceCode);
}

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
package com.lnsoft.device.api.operation.mapper;

import com.lnsoft.device.dto.DeviceRepairDTO;
import com.lnsoft.device.entity.DeviceRepair;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.device.vo.DeviceRepairVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备报修 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-19
 */
public interface DeviceRepairMapper extends BaseMapper<DeviceRepair> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceRepair
	 * @return
	 */
	List<DeviceRepairVO> selectDeviceRepairPage(IPage page, DeviceRepairVO deviceRepair);

	/**
	 * 工作台获取设备报修列表
	 * @param dto
	 * @param page
	 * @return
	 */
	IPage<DeviceRepairVO> deskDeviceRepairList(DeviceRepairDTO dto, IPage<Object> page);

    IPage<DeviceRepair> findPage(@Param("page") IPage page, @Param("deviceRepair") DeviceRepairVO deviceRepair);

	/**
	 * 根据工单编号和状态查询设备列表
	 * @param repairId
	 * @return
	 */
    List<DeviceRepairList> getDeviceByRepairIdAndStatus(String repairId);
}

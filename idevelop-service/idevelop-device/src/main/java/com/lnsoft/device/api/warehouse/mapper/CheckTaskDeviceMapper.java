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

import com.lnsoft.device.api.warehouse.dto.CheckDeviceQueryDto;
import com.lnsoft.device.api.warehouse.entity.CheckStatistic;
import com.lnsoft.device.api.warehouse.entity.CheckTaskDevice;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceNumVo;
import com.lnsoft.device.api.warehouse.vo.CheckTaskDeviceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 盘点任务设备详情 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-04-19
 */
public interface CheckTaskDeviceMapper extends BaseMapper<CheckTaskDevice> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param checkTaskDevice
	 * @return
	 */
	List<CheckTaskDeviceVO> selectCheckTaskDevicePage(IPage page, CheckTaskDeviceVO checkTaskDevice);

	/**
	 * 获取盘点数
	 * @param ids
	 * @return
	 */
    List<CheckDeviceNumVo> selectCheckNum(@Param("ids") List<String> ids);

	/**
	 * 分页获取盘点任务设备详情列表
	 * @param page
	 * @param queryDto
	 * @return
	 */
	IPage<CheckTaskDevice> checkTaskList(IPage<Object> page, @Param("dto") CheckDeviceQueryDto queryDto);
	/**
	 * 根据关联盘点任务id删除设备详情
	 * @param taskId
	 * @return
	 */
	Integer removeById(String taskId);

	/**
	 * 获取未盘点数量
	 * @param id
	 * @return
	 */
	Long selectCheckNumNo(String id);

	/**
	 * 已盘点数
	 * @param id
	 * @return
	 */
	Long selectCheckNumIs(String id);

	/**
	 * 已盘盈
	 * @param id
	 * @return
	 */
	Long selectCheckNumPys(String id);

	/**
	 * 盘盈总数
	 * @param id
	 * @return
	 */
	Long selectCheckNumPy(String id);

	/**
	 * 盘亏总数
	 * @param id
	 * @return
	 */
	Long selectCheckNumPk(String id);

	/**
	 * 已盘亏
	 * @param id
	 * @return
	 */
	Long selectCheckNumPks(String id);

	Long selectCheckReturned(String id);

	Long selectChecklstw(String id);

	List<CheckStatistic> dept();

}

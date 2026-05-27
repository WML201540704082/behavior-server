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
import com.lnsoft.device.api.warehouse.dto.CheckTaskDTO;
import com.lnsoft.device.api.warehouse.dto.CheckTaskQueryDto;
import com.lnsoft.device.api.warehouse.entity.CheckTask;
import com.lnsoft.device.api.warehouse.vo.CheckTaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 盘点任务 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-04-19
 */
public interface CheckTaskMapper extends BaseMapper<CheckTask> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param checkTask
	 * @return
	 */
	List<CheckTaskVO> selectCheckTaskPage(IPage page, CheckTaskVO checkTask);

	/**
	 * 分页查询盘点任务列表
	 * @param page
	 * @param queryDto
	 * @return
	 */
    IPage<CheckTask> checkTaskList(IPage<CheckTask> page, @Param("dto") CheckTaskQueryDto queryDto);

	/**
	 * 工作台获取盘点任务列表
	 * @param dto
	 * @param page
	 * @return
	 */
    IPage<CheckTaskVO> deskCheckTaskList(@Param("dto") CheckTaskDTO dto, IPage<CheckTask> page);

	List<CheckTask> getCheckListByUser(@Param("unitId") String unitId, @Param("deptId") String deptId, @Param("userId") String userId);

	/**
	 * 根据设备编码获取工单记录
	 * @param deviceCode
	 * @return
	 */
	List<CheckTask> getCheck(String deviceCode);



}

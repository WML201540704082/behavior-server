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
package com.lnsoft.device.api.warehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDTO;
import com.lnsoft.device.api.warehouse.dto.CheckTaskQueryDto;
import com.lnsoft.device.api.warehouse.entity.CheckTask;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceNumVo;
import com.lnsoft.device.api.warehouse.vo.CheckTaskVO;

import java.util.List;

/**
 * 盘点任务 服务类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
public interface ICheckTaskService extends BaseService<CheckTask> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param checkTask
	 * @return
	 */
	IPage<CheckTaskVO> selectCheckTaskPage(IPage<CheckTaskVO> page, CheckTaskVO checkTask);

	/**
	 * 获取盘点任务详情
	 * @param id
	 * @return
	 */
    CheckTaskVO getDetail(String id);

	/**
	 * 获取盘点任务列表
	 * @param page
	 * @param queryDto
	 * @return
	 */
	IPage<CheckTaskVO> getList(IPage<CheckTask> page, CheckTaskQueryDto queryDto);

	/**
	 * 暂存盘点任务
	 * @param checkTask
	 * @return
	 */
	R<CheckTaskVO> saveTask(CheckTaskDTO checkTask);

	/**
	 * 逻辑删除盘点任务
	 * @param ids
	 * @return
	 */
	R<Integer> removeTask(String ids);

	/**
	 * 发起盘点任务流程
	 * @param dto
	 * @return
	 */
    R<CheckTaskVO> submitCheck(CheckTaskDTO dto) throws Exception;

	/**
	 * 更新盘点任务工单状态
	 * @param dto
	 * @return
	 */
	R<Integer> deskCheckTaskStatus(CheckTaskDTO dto) throws Exception;

	/**
	 * 工作台获取盘点任务列表
	 * @param dto
	 * @param query
	 * @return
	 */
	IPage<CheckTaskVO> deskCheckTaskList(CheckTaskDTO dto, Query query);

	/**
	 * 获取历史盘点任务
	 * @return
	 */
	List<CheckDeviceNumVo> historyTask();

	/**
	 * 获取盘点任务列表根据人
	 * @return
	 */
    R<List<CheckTask>> getCheckListByUser();

	/**
	 * 盘点任务周期校验
	 * @param format
	 */
	void checkTaskEnd(String format);

	/**
	 * 在线预览
	 * @return
	 */
	R<String> online();


}

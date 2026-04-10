/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.lnsoft.ipc.mapper;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.ipc.dto.IpcTerminalMonitoringDTO;
import com.lnsoft.ipc.entity.IpcTerminalMonitoring;
import com.lnsoft.ipc.entity.IpcUser;
import com.lnsoft.ipc.vo.IpcTerminalMonitoringVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.vo.RankVO;

import java.util.List;

/**
 * 工控机管控--终端运行状态监控表 Mapper 接口
 *
 * @author Idevelop
 * @since 2025-11-17
 */
public interface IpcTerminalMonitoringMapper extends BaseMapper<IpcTerminalMonitoring> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipcTerminalMonitoring
	 * @return
	 */
	List<IpcTerminalMonitoringVO> selectIpcTerminalMonitoringPage(IPage page, IpcTerminalMonitoringVO ipcTerminalMonitoring);

    List<RankVO> deptRank(IpcTerminalMonitoringDTO ipcTerminalMonitoringDTO);

	/**
	 * 获取带单位的日志列表
	 * @return
	 */
	List<IpcTerminalMonitoringVO> getOfDept(IpcTerminalMonitoringDTO ipcTerminalMonitoringDTO);

	/**
	 * 根据ip信息查询用户信息
	 * @param ip
	 * @return
	 */
	List<IpcUser> getUserByIp(String ip);

	/**
	 * 获取开关机时间列表
	 * @param ipcTerminalMonitoringDTO
	 * @return
	 */
	List<IpcTerminalMonitoringVO> getOfUser(IpcTerminalMonitoringDTO ipcTerminalMonitoringDTO);
}

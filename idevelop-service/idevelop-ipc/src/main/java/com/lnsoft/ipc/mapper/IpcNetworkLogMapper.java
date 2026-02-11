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

import com.lnsoft.ipc.dto.IpcNetworkLogDTO;
import com.lnsoft.ipc.entity.IpcNetworkLog;
import com.lnsoft.ipc.vo.IpcNetworkLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.ipc.vo.RankVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工控机管控-网络访问记录表 Mapper 接口
 *
 * @author Idevelop
 * @since 2025-11-17
 */
public interface IpcNetworkLogMapper extends BaseMapper<IpcNetworkLog> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipcNetworkLog
	 * @return
	 */
	List<IpcNetworkLogVO> selectIpcNetworkLogPage(IPage page, IpcNetworkLogVO ipcNetworkLog);

	/**
	 *
	 * @param ipcNetworkLog
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
    List<IpcNetworkLog> slaveList(@Param("ipc") IpcNetworkLog ipcNetworkLog, @Param("pageNum") Integer pageNum, @Param("pageSize")Integer pageSize);

	/**
	 *
	 * @param ipcNetworkLog
	 * @return
	 */
	Integer slaveListTotal(IpcNetworkLog ipcNetworkLog);

	/**
	 * 查询所有数据
	 * @return
	 */
    List<IpcNetworkLog> slaveListAll(List<String> ipList);

	List<RankVO> countRank(IpcNetworkLogDTO ipcNetworkLogDTO);

}

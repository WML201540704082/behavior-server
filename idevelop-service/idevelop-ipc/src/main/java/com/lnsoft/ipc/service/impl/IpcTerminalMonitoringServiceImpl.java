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
package com.lnsoft.ipc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.ipc.dto.IpcTerminalMonitoringDTO;
import com.lnsoft.ipc.entity.IpcTerminalMonitoring;
import com.lnsoft.ipc.entity.IpcUser;
import com.lnsoft.ipc.vo.IpcTerminalMonitoringVO;
import com.lnsoft.ipc.mapper.IpcTerminalMonitoringMapper;
import com.lnsoft.ipc.service.IIpcTerminalMonitoringService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.ipc.vo.RankVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Rank;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工控机管控--终端运行状态监控表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Service
@Slf4j
public class IpcTerminalMonitoringServiceImpl extends BaseServiceImpl<IpcTerminalMonitoringMapper, IpcTerminalMonitoring> implements IIpcTerminalMonitoringService {

	@Override
	public IPage<IpcTerminalMonitoringVO> selectIpcTerminalMonitoringPage(IPage<IpcTerminalMonitoringVO> page, IpcTerminalMonitoringVO ipcTerminalMonitoring) {
		return page.setRecords(baseMapper.selectIpcTerminalMonitoringPage(page, ipcTerminalMonitoring));
	}

	@Override
	public R<List<RankVO>> deptRank(IpcTerminalMonitoringDTO ipcTerminalMonitoringDTO) {
		List<IpcTerminalMonitoringVO> ofDept = baseMapper.getOfDept(ipcTerminalMonitoringDTO);
		for (IpcTerminalMonitoringVO ipcTerminalMonitoringVO : ofDept) {
			String ip = ipcTerminalMonitoringVO.getIp();
			//根据ip查询用户信息，获取部门信息
			List<IpcUser> list = baseMapper.getUserByIp(ip);
			if (CollectionUtil.isEmpty(list)){
				//为空则为对应不上用户表
				log.info("部门排名接口，根据终端ip查询用户信息为空："+ip);
				continue;
			}else {
				String department = list.get(0).getDepartment();
				ipcTerminalMonitoringVO.setDept(department);
			}
		}
		Map<String, Long> groupSumResult = ofDept.stream()
			// 分组：key=orderType，value=每组的Order列表
			.collect(Collectors.groupingBy(
				IpcTerminalMonitoringVO::getDept,  // 分组字段（Function）
				// 下游收集器：对每组的amount求和
				Collectors.summingLong(IpcTerminalMonitoringVO::getOnlineLength)
			));
		List<RankVO> rankVOS = new ArrayList<>();
		groupSumResult.forEach((dept,len)->{
			RankVO rankVO = new RankVO();
			rankVO.setDept(dept);
			rankVO.setLen(len);
			rankVOS.add(rankVO);
		});
		for (RankVO rankVO : rankVOS) {
			Long len = rankVO.getLen();
			if (len >0){
				long l = len / 60;
				rankVO.setLen(l);
			}
		}
		return R.data(rankVOS);
	}

}

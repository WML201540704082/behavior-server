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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.ipc.dto.IpcNetworkLogDTO;
import com.lnsoft.ipc.entity.IpcBusinessSystem;
import com.lnsoft.ipc.entity.IpcNetworkLog;
import com.lnsoft.ipc.mapper.IpcBusinessSystemMapper;
import com.lnsoft.ipc.vo.IpcNetworkLogVO;
import com.lnsoft.ipc.mapper.IpcNetworkLogMapper;
import com.lnsoft.ipc.service.IIpcNetworkLogService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.ipc.vo.RankVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * 工控机管控-网络访问记录表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Service
@Slf4j
public class IpcNetworkLogServiceImpl extends BaseServiceImpl<IpcNetworkLogMapper, IpcNetworkLog> implements IIpcNetworkLogService {
	@Resource
	IpcBusinessSystemMapper ipcBusinessSystemMapper;

	@Override
	public IPage<IpcNetworkLogVO> selectIpcNetworkLogPage(IPage<IpcNetworkLogVO> page, IpcNetworkLogVO ipcNetworkLog) {
		return page.setRecords(baseMapper.selectIpcNetworkLogPage(page, ipcNetworkLog));
	}

	@Override
	@DS("slave")
	public IPage<IpcNetworkLog> slaveList(IpcNetworkLog ipcNetworkLog, Query query) {
		Integer current = query.getCurrent();
		Integer size = query.getSize();
		if (current==null){
			current = 1;
		}
		if (size == null){
			size = 10;
		}
		//查询所有的数据
		List<IpcNetworkLog> allList = baseMapper.slaveListAll();
		int i = allList.size();
		// 1. 先处理空列表或单条数据的场景，直接返回（无需后续循环）
		if (allList == null || allList.size() <= 1) {
			return null;
		}

		// 2. 循环遍历（j < 列表长度-1，避免 j+1 越界）
		for (int j = 0; j < allList.size() - 1; j++) {
			IpcNetworkLog currentLog = allList.get(j);
			IpcNetworkLog nextLog = allList.get(j + 1);

			// 3. 用 Objects.equals 安全比较IP（自动处理null，避免空指针）
			if (Objects.equals(currentLog.getIp(), nextLog.getIp())) {
				currentLog.setEndTime(nextLog.getStartTime());
			}
		}
//		for (int j = 0; j < i-1; j++) {
//			if (allList.get(j).getIp().equals(allList.get(j+1).getIp())){
//				allList.get(j).setEndTime(allList.get(j+1).getStartTime());
//			}
//		}
		int total = allList.size();
		int startIndex = (current - 1) * size;
		int endIndex = Math.min(startIndex + size, total);

		// 截取子列表
		List<IpcNetworkLog> ipcNetworkLogs = allList.subList(startIndex, endIndex);
		//10.158.89.136,10.192.228.183,10.192.228.117,10.192.228.156,10.192.228.173,10.192.228.149,10.192.228.109,10.192.228.193

		for (IpcNetworkLog networkLog : ipcNetworkLogs) {
			LocalDateTime startTime = networkLog.getStartTime();
			LocalDateTime endTime = networkLog.getEndTime();
			if (ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime)){
				long secondsDiff = ChronoUnit.SECONDS.between(startTime, endTime);
				networkLog.setAccessLength(secondsDiff);
			}

		}
//		Integer total = baseMapper.slaveListTotal(ipcNetworkLog);
		Page<IpcNetworkLog> page = new Page<>();
		page.setRecords(ipcNetworkLogs);
		page.setCurrent(current);
		page.setSize(size);
		page.setTotal(total);
		return page;
	}

	@Override
	@DS("slave")
	public List<RankVO> countRank(IpcNetworkLogDTO ipcNetworkLogDTO) {
		List<RankVO> list = baseMapper.countRank(ipcNetworkLogDTO);
		for (RankVO rankVO : list) {
			String url = rankVO.getUrl();
			LambdaQueryWrapper<IpcBusinessSystem> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(IpcBusinessSystem::getUrl,url);
			List<IpcBusinessSystem> ipcBusinessSystems = ipcBusinessSystemMapper.selectList(queryWrapper);
			if (StringUtil.isBlank(rankVO.getBusinessName())){
				if (CollectionUtils.isNotEmpty(ipcBusinessSystems)){
					rankVO.setBusinessName(ipcBusinessSystems.get(0).getBusinessName());
				}
			}
		}
		return list;
	}

}

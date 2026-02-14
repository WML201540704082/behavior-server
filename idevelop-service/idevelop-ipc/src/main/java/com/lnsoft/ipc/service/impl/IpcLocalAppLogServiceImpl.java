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
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.ipc.entity.IpcLocalAppLog;
import com.lnsoft.ipc.mapper.IpcLocalAppLogMapper;
import com.lnsoft.ipc.service.IIpcLocalAppLogService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.ipc.vo.RankVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工控机管控-本地应用访问记录表 服务实现类
 *
 * @author Idevelop
 * @since 2026-02-13
 */
@Service
@Slf4j
public class IpcLocalAppLogServiceImpl extends BaseServiceImpl<IpcLocalAppLogMapper, IpcLocalAppLog> implements IIpcLocalAppLogService {

	@Override
	@DS("slave")
	public IPage<IpcLocalAppLog> slaveList(IpcLocalAppLog ipcLocalAppLog, Query query) {
		Integer current = query.getCurrent();
		Integer size = query.getSize();
		if (current==null){
			current = 1;
		}
		if (size == null){
			size = 10;
		}
		List<String> ipList = new ArrayList<>();
		if (StringUtil.isNotBlank(ipcLocalAppLog.getIp())){
			ipList.add(ipcLocalAppLog.getIp());
		}
		//查询所有的数据
		List<IpcLocalAppLog> allList = baseMapper.selectList(new LambdaQueryWrapper<>());
		int i = allList.size();
		// 1. 先处理空列表或单条数据的场景，直接返回（无需后续循环）
		if (allList == null || allList.size() <= 1) {
			Page<IpcLocalAppLog> page = new Page<>();
			page.setRecords(null);
			page.setCurrent(current);
			page.setSize(size);
			page.setTotal(0);
			return page;
		}

		// 2. 循环遍历（j < 列表长度-1，避免 j+1 越界）
		for (int j = 0; j < allList.size() - 1; j++) {
			IpcLocalAppLog currentLog = allList.get(j);
			IpcLocalAppLog nextLog = allList.get(j + 1);

			// 3. 用 Objects.equals 安全比较IP（自动处理null，避免空指针）
			if (Objects.equals(currentLog.getIp(), nextLog.getIp())) {
				currentLog.setEndTime(nextLog.getStartTime());
			}
		}
		int total = allList.size();
		int startIndex = (current - 1) * size;
		int endIndex = Math.min(startIndex + size, total);

		// 截取子列表
		List<IpcLocalAppLog> ipcLocalAppLogs = allList.subList(startIndex, endIndex);

		for (IpcLocalAppLog localAppLog : ipcLocalAppLogs) {
			LocalDateTime startTime = localAppLog.getStartTime();
			LocalDateTime endTime = localAppLog.getEndTime();
			if (ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime)){
				long secondsDiff = ChronoUnit.SECONDS.between(startTime, endTime);
				localAppLog.setAccessLength(secondsDiff);
			}
		}

		Page<IpcLocalAppLog> page = new Page<>();
		page.setRecords(ipcLocalAppLogs);
		page.setCurrent(current);
		page.setSize(size);
		page.setTotal(total);
		return page;
	}

	@Override
	@DS("slave")
	public List<RankVO> countRank(IpcLocalAppLog ipcLocalAppLog) {
		// 这里可以实现应用访问次数排名的逻辑
		// 暂时返回空列表，后续可以根据需要实现
		return new ArrayList<>();
	}

}

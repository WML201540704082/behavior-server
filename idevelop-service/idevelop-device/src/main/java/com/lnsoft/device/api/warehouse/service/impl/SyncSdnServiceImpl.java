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
package com.lnsoft.device.api.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.warehouse.dto.DSwitcherSyncDTO;
import com.lnsoft.device.api.warehouse.entity.SyncSdn;
import com.lnsoft.device.api.warehouse.mapper.SyncSdnMapper;
import com.lnsoft.device.api.warehouse.service.ISyncSdnService;
import com.lnsoft.device.api.warehouse.utils.DSwitcherSyncUtil;
import com.lnsoft.device.api.warehouse.vo.SyncSdnVO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 各地市同步sdn和radius控制表 服务实现类
 *
 * @author Idevelop
 * @since 2026-01-27
 */
@Service
public class SyncSdnServiceImpl extends BaseServiceImpl<SyncSdnMapper, SyncSdn> implements ISyncSdnService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncSdnServiceImpl.class);

	@Resource
	private DSwitcherSyncUtil dSwitcherSyncUtil;

	@Override
	public IPage<SyncSdnVO> selectSyncSdnPage(IPage<SyncSdnVO> page, SyncSdnVO syncSdn) {
		return page.setRecords(baseMapper.selectSyncSdnPage(page, syncSdn));
	}

	@Override
	public boolean syncSdnService() {
		try {
			LambdaQueryWrapper<SyncSdn> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(SyncSdn::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
			List<SyncSdn> syncSdns = this.list(queryWrapper);
			if (CollectionUtils.isEmpty(syncSdns)) {
				return true;
			}

			for (SyncSdn syncSdn : syncSdns) {
				String switcherType = syncSdn.getSwitcherType();

				// 设备投运推送数据同步服务
				if ("0".equals(switcherType) || "2".equals(switcherType) || "4".equals(switcherType)) {
					String request = syncSdn.getRequest();
					List<DSwitcherSyncDTO> dSwitcherSyncDTOS = JSON.parseArray(request, DSwitcherSyncDTO.class);
					dSwitcherSyncUtil.insertDSwitcherSync(dSwitcherSyncDTOS, switcherType, Boolean.TRUE);
				}
				// 设备退运推送数据同步服务
				if ("1".equals(switcherType) || "3".equals(switcherType)) {
					String request = syncSdn.getRequest();
					List<DSwitcherSyncDTO> dSwitcherSyncList = JSON.parseArray(request, DSwitcherSyncDTO.class);
					dSwitcherSyncUtil.delDSwitcherSync(dSwitcherSyncList, switcherType, Boolean.TRUE);
				}

				baseMapper.updateSyncSdnById(syncSdn.getId());

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		} catch (Exception e) {
			LOGGER.info("每三分钟处理SyncSdn中的数据异常: " + e.getMessage());
		}
		return true;
	}
}

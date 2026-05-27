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
package com.lnsoft.device.api.safeaccess.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessSwitcheSaveDTO;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSwitcheMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.service.IDSwitcherSyncService;
import com.lnsoft.device.dto.SafeaccessSwitcheDTO;
import com.lnsoft.device.entity.SafeaccessSwitche;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.so.SafeaccessSwitcheSO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 交换机管理 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Service
public class SafeaccessSwitcheServiceImpl extends BaseServiceImpl<SafeaccessSwitcheMapper, SafeaccessSwitche> implements ISafeaccessSwitcheService {


	@Resource
	private IRadiusBizcImpl radiusBizc;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private IDSwitcherSyncService idSwitcherSyncService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;

	@Override
	public IPage<SafeaccessSwitche> selectSafeaccessSwitchePage(SafeaccessSwitcheSO so, Query query) {
		LambdaQueryWrapper<SafeaccessSwitche> queryWrapper = new LambdaQueryWrapper<SafeaccessSwitche>();
		queryWrapper.like(StringUtil.isNotBlank(so.getSwName()), SafeaccessSwitche::getSwName, so.getSwName());
		queryWrapper.like(StringUtil.isNotBlank(so.getSwWhere()), SafeaccessSwitche::getSwWhere, so.getSwWhere());
		queryWrapper.eq(StringUtil.isNotBlank(so.getCompany()), SafeaccessSwitche::getDeptCode, so.getCompany());
		queryWrapper.eq(StringUtil.isNotBlank(so.getDeviceCode()), SafeaccessSwitche::getDeviceCode, so.getDeviceCode());
		queryWrapper.eq(StringUtil.isNotBlank(so.getIs3()), SafeaccessSwitche::getIs3, so.getIs3());
		queryWrapper.eq(StringUtil.isNotBlank(so.getSwModel()), SafeaccessSwitche::getSwModel, so.getSwModel());
		queryWrapper.eq(StringUtil.isNotBlank(so.getSwState()), SafeaccessSwitche::getSwState, so.getSwState());
		queryWrapper.eq(StringUtil.isNotBlank(so.getAuthConfig()), SafeaccessSwitche::getAuthConfig, so.getAuthConfig());
		queryWrapper.eq(StringUtil.isNotBlank(so.getAuthState()), SafeaccessSwitche::getAuthState, so.getAuthState());
		// 管理IP
		queryWrapper.eq(StringUtil.isNotBlank(so.getTelIp()), SafeaccessSwitche::getTelIp, so.getTelIp());
		queryWrapper.eq(StringUtil.isNotBlank(so.getSwIp()), SafeaccessSwitche::getSwIp, so.getSwIp());
		// 数据权限
		IdevelopUser sysUser = SecureUtil.getUser();
		queryWrapper.likeRight(SafeaccessSwitche::getRegionCode, sysUser.getRegionCode());
		queryWrapper.orderByDesc(SafeaccessSwitche::getCreateTime);
		IPage<SafeaccessSwitche> page = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
		List<SafeaccessSwitche> records = page.getRecords();
		page.setRecords(records);
		return page;
	}

	@Override
	public Boolean getRadiusState(String id)  {
		IdevelopUser sysUser = SecureUtil.getUser();
		SafeaccessSwitche switchs = baseMapper.selectById(id);

		String regionCode = sysUser.getRegionCode().length() > 4 ? sysUser.getRegionCode().substring(0, 4) : sysUser.getRegionCode();

		String switcherKey = CacheNames.DEVICE_SYNC_SDN_SWITCHER + regionCode;
		if (redisUtil.hasKey(switcherKey)) {
			throw new ServiceException("当前地市存在正在新增或者同步Radius流程, 请一分钟后尝试! ");
		}

		redisUtil.set(switcherKey, "1", 3, TimeUnit.MINUTES);

		// 设备投运推送数据同步服务
		List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();

		switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
				.deviceCategory("网络设备")
				.deviceType(cmdbCientityProperties.getT10302())
				.switchesIp(switchs.getSwIp())
				.switchesPassword(switchs.getSwPass())
				.build());

		if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
			System.out.println("switcherDeviceListDTOList" + JSONObject.toJSONString(switcherDeviceListDTOList));
			try {
				idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, regionCode, "0");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		redisUtil.del(switcherKey);

		return true;
	}

	@Override
	public List<String> selectSwitches(String subnet) {
		return baseMapper.selectSwitches(subnet);
	}

	@Override
	public void saveSwitches(SafeaccessSwitcheSaveDTO dto) {
		IdevelopUser sysUser = SecureUtil.getUser();
		SafeaccessSwitche entity = BeanUtil.copy(dto, SafeaccessSwitche.class);
		entity.setCreateDept(sysUser.getDeptId());
		entity.setCreateTime(new Date());
		entity.setCreateUser(sysUser.getUserId());
		entity.setDeptCode(sysUser.getDeptId());
		entity.setRegionCode(sysUser.getRegionCode());
		entity.setFillMan(sysUser.getUserName());
		entity.setFillDate(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
		this.save(entity);
	}

	/**
	 * 投运批量更新交换机数据
	 *
	 * @param safeAccessSwitchesDTOList 交换机数据
	 */
	@Override
	public void updateBatchSafeAccessSwitches(List<SafeaccessSwitcheDTO> safeAccessSwitchesDTOList) {
		baseMapper.updateBatchSafeAccessSwitches(safeAccessSwitchesDTOList);
	}

}

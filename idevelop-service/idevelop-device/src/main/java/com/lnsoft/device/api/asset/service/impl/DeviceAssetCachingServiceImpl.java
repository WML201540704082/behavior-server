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
package com.lnsoft.device.api.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.device.api.asset.entity.DeviceAssetCaching;
import com.lnsoft.device.api.asset.mapper.DeviceAssetCachingMapper;
import com.lnsoft.device.api.asset.service.IDeviceAssetCachingService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.cmdb.mapper.HandlerDeviceMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.props.CmdbDictProperties;
import org.ehcache.impl.internal.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lnsoft.device.props.CmdbCientityProperties;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-30
 */
@Service
public class DeviceAssetCachingServiceImpl extends BaseServiceImpl<DeviceAssetCachingMapper, DeviceAssetCaching> implements IDeviceAssetCachingService {

	@Autowired
	private ICmdbService iCmdbService;

	@Autowired
	private CmdbCientityProperties cmdbCientityProperties;

	@Autowired
	private HandlerDeviceMapper handlerDeviceMapper;

	@Autowired
	private CmdbDictProperties cmdbDictProperties;

	private static final Map<String, List<Map<String, Object>>> CONCURRENT_HASH_MAP = new ConcurrentHashMap<String, List<Map<String, Object>>>();

	@Override
	public List<Map<String, Object>> findAssetList(DeviceAssetCaching deviceAssetCaching) {
		IdevelopUser user = SecureUtil.getUser();
		LambdaQueryWrapper<DeviceAssetCaching> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceAssetCaching::getDeviceCategoryCode, deviceAssetCaching.getDeviceCategoryCode());
		queryWrapper.eq(DeviceAssetCaching::getDept, user.getCorpId());
//		queryWrapper.eq(DeviceAssetCaching::getDept,user.getDeptId());
		List<DeviceAssetCaching> deviceAssetCachings = baseMapper.selectList(queryWrapper);

		return deviceAssetCachings.stream().map(item -> {
			Map<String, Object> map = new HashMap<>();
			map.put("name", item.getDeviceTypeCode());
			map.put("value", item.getAssetOriginalSum());
			return map;
		}).collect(Collectors.toList());
	}

	@Override
	public DeviceAssetCaching getOneData(DeviceAssetCaching deviceAssetCaching) {
		return baseMapper.getOneData(deviceAssetCaching);
	}

}

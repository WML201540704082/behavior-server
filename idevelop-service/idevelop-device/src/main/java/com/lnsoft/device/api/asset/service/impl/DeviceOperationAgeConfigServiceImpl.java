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
import com.google.common.collect.Lists;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.entity.DeviceOperationAgeConfig;
import com.lnsoft.device.api.asset.mapper.DeviceOperationAgeConfigMapper;
import com.lnsoft.device.api.asset.service.IDeviceOperationAgeConfigService;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备年限配置管理表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-26
 */
@Service
public class DeviceOperationAgeConfigServiceImpl extends BaseServiceImpl<DeviceOperationAgeConfigMapper, DeviceOperationAgeConfig> implements IDeviceOperationAgeConfigService {

	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private ICmdbClient iCmdbClient;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;

	@Override
	public List<DeviceOperationAgeConfig> customList(DeviceOperationAgeConfig config) {
		LambdaQueryWrapper<DeviceOperationAgeConfig> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(StringUtil.isNotBlank(config.getDeviceCategory()), DeviceOperationAgeConfig::getDeviceCategory, config.getDeviceCategory());
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public boolean initializeData() {
		ArrayList<DeviceOperationAgeConfig> list = Lists.newArrayList();
		Map<Object, Object> deviceCategoryMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify());

		for (Map.Entry<Object, Object> entry : deviceCategoryMap.entrySet()) {
			String key = (String) entry.getKey();
			R<List<Map<String, Object>>> result = iCmdbClient.feignGetCiEntityDictListByPid(cmdbDictProperties.getDeviceType(), Long.valueOf(key));
			List<Map<String, Object>> typeList = result.getData();

			if (CollectionUtil.isNotEmpty(typeList)) {
				for (Map<String, Object> map : typeList) {
					DeviceOperationAgeConfig entity = new DeviceOperationAgeConfig();
					entity.setDeviceCategory(key);
					entity.setDeviceType(String.valueOf(map.get("dictKey")));
					entity.setDeviceTypeName(String.valueOf(map.get("dictValue")));
					list.add(entity);
				}
			}
		}
		return saveBatch(list);
	}

	@Override
	public String getOneByDeviceType(String deviceType) {
		return baseMapper.getOneByDeviceType(deviceType);
	}

	@Override
	public String syncByDeviceCategory() {
		List<String> deviceCategoryList = getDeviceCategoryList();
		int sum = 0;
		for (String deviceCategory : deviceCategoryList) {
			LambdaQueryWrapper<DeviceOperationAgeConfig> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(DeviceOperationAgeConfig::getDeviceCategory, deviceCategory);
			List<DeviceOperationAgeConfig> list = baseMapper.selectList(queryWrapper);
			// 删除原有数据
			baseMapper.physicalDelete(deviceCategory);
			Map<String, String> ageMap = list.stream().collect(Collectors.toMap(config -> config.getDeviceTypeName(), config1 -> config1.getOperationAge()));

			List<Map<String, Object>> newTypeList = iCmdbClient.feignGetCiEntityDictListByPid(cmdbDictProperties.getDeviceType(), Long.valueOf(deviceCategory)).getData();

			List<DeviceOperationAgeConfig> newList = Lists.newArrayList();

			for (Map<String, Object> type : newTypeList) {
				Object dictKey = type.get("dictKey");
				Object dictValue = type.get("dictValue");
				DeviceOperationAgeConfig entity = new DeviceOperationAgeConfig();
				entity.setDeviceCategory(deviceCategory);
				entity.setDeviceType(String.valueOf(dictKey));
				entity.setDeviceTypeName(String.valueOf(dictValue));
				if (ageMap.containsKey(dictValue)) {
					entity.setOperationAge(ageMap.get(dictValue));
				}
				newList.add(entity);
			}
			saveBatch(newList);
			sum += newTypeList.size();
			log.error("同步完成，本次同步" + newTypeList.size() + "条数据");
		}
		return "同步完成，共同步" + sum + "条数据";
	}

	private List<String> getDeviceCategoryList() {
		List<String> list = Lists.newArrayList();
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T101));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T102));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T103));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T104));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T105));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T106));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T107));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T108));
		list.add(cmdbCientityProperties.getCientityId(CmdbCientityConstant.T109));
		return list;
	}
}

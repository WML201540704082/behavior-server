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

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.safeaccess.dto.SdnUserAccessDTO;
import com.lnsoft.device.api.safeaccess.entity.SdnUserAccess;
import com.lnsoft.device.api.safeaccess.mapper.SdnUserAccessMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetService;
import com.lnsoft.device.api.safeaccess.service.ISdnUserAccessService;
import com.lnsoft.device.api.safeaccess.vo.SdnUserSdnUserAccessVO;
import com.lnsoft.device.api.warehouse.utils.DSwitcherSyncUtil;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.CmdbDictUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  服务实现类
 *
 * @author Idevelop
 * @since 2025-04-19
 */
@Service
public class SdnUserAccessServiceImpl extends BaseServiceImpl<SdnUserAccessMapper, SdnUserAccess> implements ISdnUserAccessService {

	@Resource
	private DSwitcherSyncUtil dSwitcherSyncUtil;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private ISafeaccessSubnetService subnetService;

	@Override
	public IPage<SdnUserSdnUserAccessVO> selectAccessPage(IPage<SdnUserSdnUserAccessVO> page, SdnUserSdnUserAccessVO access) {
		return page.setRecords(baseMapper.selectAccessPage(page, access));
	}

	@Override
	public IPage<SdnUserAccess> getList(IPage<Object> page, SdnUserAccessDTO sdnUserAccess) {
		String url = baseMapper.getUrl(sdnUserAccess.getRegionCode());
		HttpRequest http = HttpUtil.createPost("http://localhost:18888/api/idevelop-system/common/user/info");
		http.method(Method.GET);
		http.contentType("application/json");
		//参数
		Map<String, String> appQuery = new HashMap<>();
		http.form("appQuery", appQuery);
		HttpResponse response = http.execute();
		return null;

	}

	/**
	 * 获取各地市sdn中用户信息表
	 * @param sdnUserAccessDTO
	 * @return
	 */
	@Override
	public Map<String, Object> getUserAccessList(SdnUserAccessDTO sdnUserAccessDTO) {
		try {
			Map<String, Object> result = dSwitcherSyncUtil.getUserAccessList(sdnUserAccessDTO);
			if (result.isEmpty()) {
				return new HashMap<>();
			}
			Map<String, Object> data = (Map<String, Object>) result.get("data");
			List<Map<String, Object>> userAccessList = (List<Map<String, Object>>) data.get("userAccessList");
			for (Map<String, Object> map : userAccessList) {
				// 设备类型
				Object deviceId = map.get("deviceId");
				if (!Objects.isNull(deviceId)) {
					String deviceIdStr = deviceId.toString();
					String value = getDictValue(cmdbDictProperties.getDeviceType(), deviceIdStr);
					if (!StringUtils.isEmpty(value)) {
						map.put("deviceId", value);
					}
				}
				// 所属子网
				Object subnetId = map.get("subnetId");
				if (!Objects.isNull(subnetId)) {
					String subnetIdStr = subnetId.toString();
					SafeaccessSubnet safeaccessSubnet = subnetService.getById(subnetIdStr);
					if (Objects.nonNull(safeaccessSubnet)) {
						map.put("subnetId", safeaccessSubnet.getSubnetName());
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Map<String, Object> delUserAccess(SdnUserAccessDTO sdnUserAccessDTO) {
		try {
			return dSwitcherSyncUtil.delUserAccess(sdnUserAccessDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> updateUserAccess(SdnUserAccessDTO sdnUserAccessDTO) {
		try {
			return dSwitcherSyncUtil.updateUserAccess(sdnUserAccessDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getDictValue(Long ciId, String key) {
		String redisKey = CacheNames.CMDB_DICT_STORAGE + ciId;
		Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
		if (CollectionUtil.isEmpty(cache)) {
			R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
			cache = CmdbDictUtil.getValue(dict);
			redisUtil.set(redisKey, cache);
			if (CollectionUtil.isEmpty(cache)) {
				return null;
			}
		}
		return cache.get(key);
	}

}

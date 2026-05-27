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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessIppoolDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessIppoolVO;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessIppoolMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessIppoolService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.eums.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IP地址池 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@Service
@Slf4j
public class SafeaccessIppoolServiceImpl extends BaseServiceImpl<SafeaccessIppoolMapper, SafeaccessIppool> implements ISafeaccessIppoolService {

	@Resource
	private ICmdbService cmdbService;


	@Override
	public IPage<SafeaccessIppoolVO> selectSafeaccessIppoolPage(IPage<SafeaccessIppoolVO> page, SafeaccessIppoolVO safeaccessIppool) {
		return page.setRecords(baseMapper.selectSafeaccessIppoolPage(page, safeaccessIppool));
	}

	@Override
	public void insertIpPool(String tableName, List<Map<String, String>> ipList) {
		baseMapper.insertIpPool(tableName, ipList);
	}

	@Override
	public void updateGatewayByIp(Map<String, String> map) {
		baseMapper.updateGatewayByIp(map);
	}

	@Override
	public int deleteIpPool(SafeaccessIppoolDTO saTIppool) {
		return baseMapper.deleteIpPool(saTIppool);
	}

	@Override
	public List<String> selectUserAccess(String subnet) {
		return baseMapper.selectUserAccess(subnet);
	}

	@Override
	public void updateIpPool(SafeaccessIppoolDTO saTIppool) {
		baseMapper.updateIpPool(saTIppool);
	}

	@Override
	public int queryIpPoolCount(Map<String, String> ippool) {
		String compCode4 = getCompCode4();
		ippool.put("ipPoolName", SafeaccessIppool.getTablename() + compCode4);
		int ipUsedCount = baseMapper.getIpUsedCount(ippool);
		return ipUsedCount;
	}

	@Override
	public void updateIpPoolNewSubnet(Map tmap) {
		baseMapper.updateIpPoolNewSubnet(tmap);
	}

	@Override
	public void updateIpPoolOld(SafeaccessIppoolDTO ippool) {
		baseMapper.updateIpPoolOld(ippool);
	}

	@Override
	public List<SafeaccessIppool> selectIpPools(Map<String, Object> map) {
		return baseMapper.selectByMap(map);
	}

	@Override
	public int setIpLevel(SafeaccessIppoolDTO ippool) {
		ippool.setIpPoolName(SafeaccessIppool.getTablename() + getCompCode4());
		return baseMapper.updateIpLevel(ippool);
	}

	@Override
	public IPage<SafeaccessIppoolDTO> searchByPage(SafeaccessIppoolDTO safeaccessIppool, Query query) {
		if (StringUtil.isBlank(safeaccessIppool.getSubnet())) {
			throw new ServiceException("参数：所属子网不能为空");
		}
		Page<SafeaccessIppoolDTO> page = new Page<>(query.getCurrent(), query.getSize());
		safeaccessIppool.setIpPoolName(SafeaccessIppool.getTablename() + getCompCode4());
		return baseMapper.searchByPage(page, safeaccessIppool);
	}

	@Override
	public int setNewGateway(SafeaccessIppoolDTO ippool) {
		ippool.setIpPoolName(SafeaccessIppool.getTablename() + getCompCode4());
		return baseMapper.setNewGateway(ippool);
	}

	@Override
	public List<SafeaccessIppoolDTO> search(Map<String, String> map) {
		return baseMapper.search(map);
	}

	@Override
	public Map<String, Object> getQueryList(Map<String, String> saTIppool) {
		saTIppool.put("ipPoolName", SafeaccessIppool.getTablename() + getCompCode4());
		List<SafeaccessIppoolDTO> list = baseMapper.getQueryList(saTIppool);
		if (CollectionUtil.isNotEmpty(list)) {
			HashMap<String, Object> result = Maps.newHashMap();
			result.put("saTIpPool", list);
			return result;
		}
		return new HashMap<>();
	}

	private List<Map<String, Object>> queryCmdbByIpAndMac(String ip, String mac) {
		CiCientitySearchVO ipVO = new CiCientitySearchVO("IP", ip, Expression.EQUAL, Boolean.FALSE);
		CiCientitySearchVO macVO = new CiCientitySearchVO("MAC", mac, Expression.EQUAL, Boolean.FALSE);
		ArrayList<CiCientitySearchVO> param = Lists.newArrayList();
		param.add(ipVO);
		param.add(macVO);
		Query query = new Query().setCurrent(1).setSize(1);
		FeignCiCientity json = cmdbService.getCiCientityListByClaccify(param, query);
		return json.getData();
	}

	@Override
	public List<Map<String, Object>> getDetails(String ippoolId) {
		HashMap<String, Object> param = Maps.newHashMap();
		param.put("ippoolId", ippoolId);
		param.put("ipPoolName", SafeaccessIppool.getTablename() + getCompCode4());
		List<Map<String, Object>> details = baseMapper.getDetails(param);
		if (CollectionUtil.isEmpty(details)) {
			return details;
		}
		for (Map<String, Object> detail : details) {
			String isUsed = (String) detail.get("isUsed");
			if ("0".equals(isUsed)) {
				continue;
			}
			String ip = (String) detail.get("ip");
			String mac = (String) detail.get("appMac");
			List<Map<String, Object>> data = queryCmdbByIpAndMac(ip, mac);
			if (CollectionUtil.isNotEmpty(data)) {
				Map<String, Object> map = data.get(0);
				detail.put("basicDeviceName", map.get("deviceName"));
//				detail.put("basicDeviceType", map.get("deviceType"));
				detail.put("basicMaker", map.get("maker"));
//				detail.put("appMac", map.get("MAC"));
//				detail.put("lastAuthTime", "");
				detail.put("basicState", map.get("deviceStatus"));
				detail.put("OSTypeCode", map.get("OSTypeCode"));
//				detail.put("miUser", map.get("receivingPerson"));
//				detail.put("deviceCode", map.get("deviceCode"));
			}
		}
		return details;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setIsUsed(SafeaccessIppoolDTO ippool) {
		int rs = 0;
		//获取登录人编码前四位
		String compCode4 = getCompCode4();
		ippool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);

		SafeaccessIppool select = baseMapper.selectCustomById(ippool);
		//判断当前ip是否已经存在入网信息,存在则不允许操作
		SafeaccessUserAccess csTUserAccess = new SafeaccessUserAccess();
		csTUserAccess.setDeptCode(select.getOrgNo());
		csTUserAccess.setIpAddress(select.getIp());
		int count = baseMapper.selectByAccessIp(csTUserAccess);
		if (count == 1) {
			return 2;
		}
		rs = baseMapper.updateIsUsed(ippool);
		return rs;
	}

	@Override
	public Map<String, Object> getRadiusIp(Map<String, String> map) {
		String compCode4 = getCompCode4();
		map.put("ipPoolName", SafeaccessIppool.getTablename() + compCode4);
		Map<String, String> ip = baseMapper.selectByAuthId(map);

		Map<String, Object> switches = baseMapper.selectSwitches(ip.get("subnetId"));
		if (switches == null) {
			throw new ServiceException("未找到交换机信息");
		}
		Map<String, Object> port = baseMapper.selectSdnSwitchPort(ip);
		Map<String, Object> num = baseMapper.selectInterface(map);
		if (port != null) {
			switches.putAll(port);
		} else {
			switches.put("port", 0);
		}
		if (num != null) {
			switches.putAll(num);
		}
		return switches;
	}

	@Override
	public boolean releaseIpBySubnetIdAndIp(String subnetId, String ip) {
		String tableName = SafeaccessIppool.getTablename() + getCompCode4();
		return baseMapper.releaseIpBySubnetIdAndIp(tableName, subnetId, ip) == 1;
	}

	@Override
	public boolean setIsUsedBySubnetIdAndIp(String subnetId, String ip) {
		SafeaccessIppoolDTO ippool = new SafeaccessIppoolDTO();
		//获取登录人编码前四位
		String compCode4 = getCompCode4();
		ippool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
		ippool.setIp(ip);
		ippool.setSubnet(subnetId);
		SafeaccessIppool select = baseMapper.selectBySubnetIdAndIp(ippool);
		//判断当前ip是否已经存在入网信息,存在则不允许操作
		SafeaccessUserAccess csTUserAccess = new SafeaccessUserAccess();
		csTUserAccess.setDeptCode(select.getOrgNo());
		csTUserAccess.setIpAddress(select.getIp());
		int count = baseMapper.selectByAccessIp(csTUserAccess);
		if (count == 1) {
			throw new ServiceException("当前ip{" + ip + "}已经存在入网信息,无法使用");
		}
		ippool.setIpId(select.getIpId());
		ippool.setIsUsed("1");
		return baseMapper.updateIsUsed(ippool) > 0;
	}

	@Override
	public List<SafeaccessIppoolDTO> searchNoPage(SafeaccessIppoolDTO safeaccessIppool) {
		if (StringUtil.isBlank(safeaccessIppool.getSubnet())) {
			throw new ServiceException("参数：所属子网不能为空");
		}
		safeaccessIppool.setIpPoolName(SafeaccessIppool.getTablename() + getCompCode4());
		safeaccessIppool.setLevel("1");
		return baseMapper.searchNoPage(safeaccessIppool);
	}

	@Override
	public Map<String, Object> getRadiusIp(String ip) {
		String compCode4 = getCompCode4();
		HashMap<String, String> map = Maps.newHashMap();
		map.put("authIp", ip);
		map.put("ipPoolName", SafeaccessIppool.getTablename() + compCode4);
		Map<String, String> selectIp = baseMapper.selectByAuthId(map);

		Map<String, Object> switches = baseMapper.selectSwitches(selectIp.get("subnetId"));
		if (switches == null) {
			throw new ServiceException("未找到交换机信息");
		}
		Map<String, Object> port = baseMapper.selectSdnSwitchPort(selectIp);
		Map<String, Object> num = baseMapper.selectInterface(map);
		if (port != null) {
			switches.putAll(port);
		} else {
			switches.put("port", 0);
		}
		if (num != null) {
			switches.putAll(num);
		}
		return switches;
	}

	@Override
	public List<SafeaccessIppool> getSubnetList(String deviceIp) {
		String tableName = SafeaccessIppool.getTablename() + getCompCode4();
		return baseMapper.getSubnetList(tableName,deviceIp);
	}


	private String getCompCode4() {
		IdevelopUser sysUser = SecureUtil.getUser();
		String orgNo = sysUser.getRegionCode();
		if (orgNo.length() < 4) {
			throw new ServiceException("请确认当前用户所属地市");
		}
		return orgNo.length() >= 4 ? orgNo.substring(0, 4) : orgNo;
	}
}

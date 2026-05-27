package com.lnsoft.device.api.warehouse.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSubnetMapper;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSwitcheMapper;
import com.lnsoft.device.api.warehouse.dto.*;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnUserAccess;
import com.lnsoft.device.api.warehouse.service.IDSwitcherSyncService;
import com.lnsoft.device.api.warehouse.utils.DSwitcherSyncUtil;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.device.entity.SafeaccessSwitche;
import com.lnsoft.device.props.CmdbCientityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DSwitcherSyncServiceImpl implements IDSwitcherSyncService {

	@Resource
	private SafeaccessSubnetMapper safeaccessSubnetMapper;
	@Resource
	private SafeaccessSwitcheMapper safeaccessSwitcheMapper;
	@Resource
	private DSwitcherSyncUtil dSwitcherSyncUtil;
	@Resource
	private CmdbCientityProperties modelProperties;

	/**
	 * 设备投运推送数据同步服务
	 *
	 * @param switcherDeviceListDTOList 投运设备信息
	 * @param regionCode                区域编码
	 * @param switcherType              推送数据同步服务数据来源 0 设备投运 1 设备退运 2 子网管理（新增、修改） 3 子网管理（删除） 4 设备变更
	 */
	@Override
	public void insertDSwitcherSync(List<SwitcherDeviceListDTO> switcherDeviceListDTOList, String regionCode, String switcherType) throws Exception {
		// 根据区域编码获取市级下的子网信息，例如历城获取济南市下的
		String region = regionCode;
		if (regionCode.length() >= 6) {
			region = regionCode.substring(0, 4);
		}
		List<ClientsConfDTO> clientsConfDTOList = new ArrayList<>();
		List<SharedNetworkConfDTO> sharedNetworkConfList = new ArrayList<>();
		List<HostConfDTO> hostConfDTOList = new ArrayList<>();
		List<UsersConfDTO> usersConfDTOList = new ArrayList<>();
		List<String> deviceType = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
		List<String> deviceTypeList = modelProperties.getModelIdList(Constants.DEVICE_SAFE_ACCESS_TYPE);
		// 如果是设备投运进行数据推送服务，进行数据组装
		if ("0".equals(switcherType) || "4".equals(switcherType)) {
			List<SwitcherDeviceListDTO> deviceSubnetList = switcherDeviceListDTOList.stream().filter(switcherDeviceListDTO ->
				StringUtils.isNotBlank(switcherDeviceListDTO.getDeviceSubnet())).collect(Collectors.toList());
			List<SafeaccessSubnet> safeAccessSubnetList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(deviceSubnetList)) {
				LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>()
					.likeRight(SafeaccessSubnet::getRegionCode, region)
					.in(SafeaccessSubnet::getId, deviceSubnetList.stream().map(SwitcherDeviceListDTO::getDeviceSubnet).distinct().collect(Collectors.toList()));
				safeAccessSubnetList = safeaccessSubnetMapper.selectList(queryWrapper);
			}
			List<SafeaccessSubnet> finalSafeAccessSubnetList = safeAccessSubnetList;
			switcherDeviceListDTOList.forEach(item -> {
				// 如果设备是网络交换机，组装交换机的ip和密码信息
				if (deviceType.contains(item.getDeviceType())) {
					clientsConfDTOList.add(ClientsConfDTO.builder().ipaddr(item.getSwitchesIp()).secret(item.getSwitchesPassword()).build());
				} else {
					// 如果是非交换机类型的
					hostConfDTOList.add(HostConfDTO.builder().hardwareEthernet(item.getDeviceMac()).fixedAddress(item.getDeviceIp()).build());
					SafeaccessSubnet safeaccessSubnet = finalSafeAccessSubnetList.stream().filter(safeAccessSubnet ->
						item.getDeviceSubnet().equals(safeAccessSubnet.getId())).findFirst().orElse(new SafeaccessSubnet());

					String authAccount = item.getAuthAccount();
					String authPassword = item.getAuthPassword();
					String deviceMac = item.getDeviceMac();
					if (StringUtils.equals("2", item.getIs802())) {
						String replaceMac = deviceMac.toLowerCase().replace(":", "");
						authAccount = replaceMac;
						authPassword = replaceMac;
					}

					usersConfDTOList.add(UsersConfDTO.builder()
						.userId(authAccount)
						.cleartextPassword(authPassword)
						.callingStationId(deviceMac)
						.tunnelPrivateGroupID(safeaccessSubnet.getVlanId())
						.build());
				}
			});
		}
		// 如果是设备退运进行数据推送服务，进行数据组装
		if ("1".equals(switcherType)) {
			List<String> switchesList = switcherDeviceListDTOList.stream().filter(switcherDeviceListDTO -> deviceType.contains(switcherDeviceListDTO.getDeviceType()))
				.map(SwitcherDeviceListDTO::getDeviceCode).collect(Collectors.toList());
			List<SafeaccessSwitche> safeAccessSwitchesList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(switchesList)) {
				safeAccessSwitchesList = safeaccessSwitcheMapper.selectList(new LambdaQueryWrapper<SafeaccessSwitche>().in(SafeaccessSwitche::getDeviceCode, switchesList));
			}
			List<SafeaccessSwitche> finalSafeAccessSwitchesList = safeAccessSwitchesList;
			switcherDeviceListDTOList.forEach(item -> {
				// 如果设备是网络交换机，组装交换机的ip和密码信息
				if (deviceType.contains(item.getDeviceType())) {
					SafeaccessSwitche findSafeAccessSwitches = finalSafeAccessSwitchesList.stream()
						.filter(safeAccessSwitches -> item.getDeviceCode().equals(safeAccessSwitches.getDeviceCode())).findFirst().orElse(new SafeaccessSwitche());
					clientsConfDTOList.add(ClientsConfDTO.builder().ipaddr(findSafeAccessSwitches.getSwIp()).secret(findSafeAccessSwitches.getSwPass()).build());
				} else {
					// 如果是非交换机类型的
					hostConfDTOList.add(HostConfDTO.builder().hardwareEthernet(item.getDeviceMac()).fixedAddress(item.getDeviceIp()).build());
					usersConfDTOList.add(UsersConfDTO.builder()
						.userId(item.getAuthAccount())
						.cleartextPassword(item.getAuthPassword())
						.callingStationId(item.getDeviceMac())
						.build());
				}
			});
		}
		// 如果是子网管理进行数据推送服务，进行数据组装
		if ("2".equals(switcherType)) {
			switcherDeviceListDTOList.forEach(item -> {
				SharedNetworkConfDTO sharedNetworkConfDTO = SharedNetworkConfDTO.builder()
						.subnet(item.getSubnet())
						.netmask(item.getNetmask())
						.optionBroadcastAddress(item.getOptionBroadcastAddress())
						.optionRouters(item.getOptionRouters())
						.optionSubnetMask(item.getNetmask())
						.optionDomainNameServers(item.getOptionDomainNameServers())
						.defaultLeaseTime(item.getDefaultLeaseTime() == null ? 0 : item.getDefaultLeaseTime())
						.maxLeaseTime(Constants.MAX_LEASE_TIME)
						.build();
				sharedNetworkConfList.add(sharedNetworkConfDTO);
			});
		}
		ConfDataDTO confDataDTO = ConfDataDTO.builder()
				.clientsConfList(clientsConfDTOList)
				.sharedNetworkConfList(sharedNetworkConfList)
				.hostConfList(hostConfDTOList)
				.usersConfList(usersConfDTOList)
				.build();
		DSwitcherSyncDTO dSwitcherSyncDTO = new DSwitcherSyncDTO();
		dSwitcherSyncDTO.setServerInfo(ServerInfoDTO.builder().code(regionCode).type(regionCode.length() >= 6 ? "3" : "2").build());
		dSwitcherSyncDTO.setConfData(confDataDTO);
		List<DSwitcherSyncDTO> dSwitcherSyncList = Lists.newArrayList(dSwitcherSyncDTO);
		log.info("设备Radius推送数据同步服务：" + JSONObject.toJSONString(dSwitcherSyncList));
		// 设备投运推送数据同步服务
		if ("0".equals(switcherType) || "2".equals(switcherType) || "4".equals(switcherType)) {
			dSwitcherSyncUtil.insertDSwitcherSync(dSwitcherSyncList, switcherType, Boolean.FALSE);
		}
		// 设备退运推送数据同步服务
		if ("1".equals(switcherType) || "3".equals(switcherType)) {
			dSwitcherSyncUtil.delDSwitcherSync(dSwitcherSyncList, switcherType, Boolean.FALSE);
		}
	}

	/**
	 * 按区域组装数据
	 *
	 * @param switcherDeviceListDTOList
	 * @param regionCode
	 * @param switcherType
	 * @return
	 */
	@Override
	public List<DSwitcherSyncDTO> getDSwitcherSyncList(List<SwitcherDeviceListDTO> switcherDeviceListDTOList, String regionCode, String switcherType) {
		// 根据区域编码获取市级下的子网信息，例如历城获取济南市下的
		List<ClientsConfDTO> clientsConfDTOList = new ArrayList<>();
		List<SharedNetworkConfDTO> sharedNetworkConfList = new ArrayList<>();
		List<HostConfDTO> hostConfDTOList = new ArrayList<>();
		List<UsersConfDTO> usersConfDTOList = new ArrayList<>();
		// 如果是子网管理进行数据推送服务，进行数据组装
		if ("2".equals(switcherType) || "3".equals(switcherType)) {
			switcherDeviceListDTOList.forEach(item -> {
				sharedNetworkConfList.add(SharedNetworkConfDTO.builder()
					.subnet(item.getSubnet())
					.netmask(item.getNetmask())
					.optionBroadcastAddress(item.getOptionBroadcastAddress())
					.optionRouters(item.getOptionRouters())
					.optionSubnetMask(item.getNetmask())
					.optionDomainNameServers(item.getOptionDomainNameServers())
					.defaultLeaseTime(item.getDefaultLeaseTime() == null ? 0 : item.getDefaultLeaseTime())
					.maxLeaseTime(Constants.MAX_LEASE_TIME)
					.build());
			});
		}
		ConfDataDTO confDataDTO = new ConfDataDTO();
		confDataDTO.setClientsConfList(clientsConfDTOList);
		confDataDTO.setSharedNetworkConfList(sharedNetworkConfList);
		confDataDTO.setHostConfList(hostConfDTOList);
		confDataDTO.setUsersConfList(usersConfDTOList);
		List<DSwitcherSyncDTO> dSwitcherSyncList = new ArrayList<>();
		DSwitcherSyncDTO dSwitcherSyncDTO = new DSwitcherSyncDTO();
		dSwitcherSyncDTO.setServerInfo(ServerInfoDTO.builder().code(regionCode).type(regionCode.length() >= 6 ? "3" : "2").build());
		dSwitcherSyncDTO.setConfData(confDataDTO);
		dSwitcherSyncList.add(dSwitcherSyncDTO);
		return dSwitcherSyncList;
	}

	@Override
	public void run(List<DSwitcherSyncDTO> dSwitcherSyncList, String switcherType) throws Exception {
		log.info("设备Radius推送数据同步服务：" + dSwitcherSyncList.toString());
		// 设备投运推送数据同步服务
		if ("0".equals(switcherType) || "2".equals(switcherType)) {
			dSwitcherSyncUtil.insertDSwitcherSync(dSwitcherSyncList, switcherType, Boolean.FALSE);
		}
		// 设备退运推送数据同步服务
		if ("1".equals(switcherType) || "3".equals(switcherType)) {
			dSwitcherSyncUtil.delDSwitcherSync(dSwitcherSyncList, switcherType, Boolean.FALSE);
		}
	}

	/**
	 * 同步SDN
	 *
	 * @param deviceSdnUserAccessList 同步数据
	 */
	@Override
	public void deviceSdnUserAccess(List<DeviceSdnUserAccess> deviceSdnUserAccessList) throws Exception {
		log.info("设备sdn推送数据同步服务：" + deviceSdnUserAccessList.toString());
		dSwitcherSyncUtil.deviceSdnUserAccess(deviceSdnUserAccessList);
	}

}

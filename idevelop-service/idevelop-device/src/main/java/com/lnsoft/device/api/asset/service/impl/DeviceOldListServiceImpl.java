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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.common.utils.IdevelopUtils;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.DeviceOldListDTO;
import com.lnsoft.device.api.asset.entity.DeviceOldFile;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.eums.OldDeviceConversionEnum;
import com.lnsoft.device.api.asset.eums.OldDeviceScoreConfigEnum;
import com.lnsoft.device.api.asset.mapper.DeviceOldFileMapper;
import com.lnsoft.device.api.asset.service.*;
import com.lnsoft.device.api.asset.vo.DeviceOldListVO;
import com.lnsoft.device.api.asset.mapper.DeviceOldListMapper;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 老旧设备表	 服务实现类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Service
public class DeviceOldListServiceImpl extends BaseServiceImpl<DeviceOldListMapper, DeviceOldList> implements IDeviceOldListService {
	@Resource
	private IDeviceOldModelConfigService deviceOldModelConfigService;
	@Resource
	private IDeptClient deptClient;
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private IDeviceOperationAgeConfigService ageConfigService;
	@Resource
	private IDeviceOldFileService deviceOldFileService;
	@Resource
	private IResourceRoomService roomService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private DeviceOldFileMapper deviceOldFileMapper;

	@Override
	public IPage<DeviceOldList> selectDeviceOldListPage(Query query, DeviceOldListVO deviceOldList) {
		IdevelopUser user = SecureUtil.getUser();
		if (ObjectUtil.isNotEmpty(deviceOldList.getTop())) {
			query.setCurrent(1);
			query.setSize(deviceOldList.getTop());
		}
		LambdaQueryWrapper<DeviceOldList> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(StringUtil.isNotBlank(deviceOldList.getDeviceCategory()), DeviceOldList::getDeviceCategory, deviceOldList.getDeviceCategory())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getOperationCondition()), DeviceOldList::getOperationCondition, deviceOldList.getOperationCondition())
			.eq(StringUtil.isNotBlank(deviceOldList.getDeviceStatus()), DeviceOldList::getDeviceStatus, deviceOldList.getDeviceStatus())
			.eq(DeviceOldList::getRegionCode, user.getRegionCode())
			.eq(StringUtil.isNotBlank(deviceOldList.getDeviceType()), DeviceOldList::getDeviceType, deviceOldList.getDeviceType())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getIsChange()), DeviceOldList::getIsChange, deviceOldList.getIsChange())
			.eq(StringUtil.isNotBlank(deviceOldList.getDeviceCode()), DeviceOldList::getDeviceCode, deviceOldList.getDeviceCode())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getRoomType()), DeviceOldList::getRoomType, deviceOldList.getRoomType())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getRoomFunction()), DeviceOldList::getRoomFunction, deviceOldList.getRoomFunction())
			.eq(StringUtil.isNotBlank(deviceOldList.getOperationDeptCode()), DeviceOldList::getPropertyDeptCode, deviceOldList.getOperationDeptCode())
			.eq(StringUtil.isNotBlank(deviceOldList.getPropertyDeptCode()), DeviceOldList::getPropertyDeptCode, deviceOldList.getPropertyDeptCode())
			.between(ObjectUtil.isNotEmpty(deviceOldList.getStartScore()) && ObjectUtil.isNotEmpty(deviceOldList.getEndScore()), DeviceOldList::getScore, deviceOldList.getStartScore(), deviceOldList.getEndScore())
			.between(ObjectUtil.isNotEmpty(deviceOldList.getStartAge()) && ObjectUtil.isNotEmpty(deviceOldList.getEndAge()), DeviceOldList::getOverAge, deviceOldList.getStartAge(), deviceOldList.getEndAge())
			.orderByDesc(DeviceOldList::getScore);
		return baseMapper.selectPage(Condition.getPage(query), queryWrapper);

	}

	@Override
	public R change(List<DeviceOldList> deviceOldList) {
		IdevelopUser user = SecureUtil.getUser();
		Date date = new Date();
		for (DeviceOldList oldList : deviceOldList) {
			if (oldList.getStatus() == 1 || oldList.getStatus() == 2) {
				return R.fail("当前不可修改自评");
			}
			if (oldList.getIsChange().equals("10")) {
				oldList.setScore(0D);
				oldList.setAppriseOwnScore(0D);
			}
			oldList.setUpdateUser(user.getUserId());
			oldList.setChangeUser(user.getRealName());
			oldList.setUpdateTime(date);
		}
		compute(deviceOldList);
		return R.status(updateBatchById(deviceOldList));
	}

	@Override
	public List<DeviceOldList> insert(List<DeviceOldList> deviceOldList) {
		for (DeviceOldList oldList : deviceOldList) {
			LambdaQueryWrapper<DeviceOldList> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(DeviceOldList::getDeviceCode, oldList.getDeviceCode())
				.eq(DeviceOldList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
				.eq(DeviceOldList::getScoreCycle,deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.CYCLE.getMessage()).getValue());
			DeviceOldList selectOne = baseMapper.selectOne(wrapper);
			if (!ObjectUtil.isEmpty(selectOne)) {
				return null;
			}
			ResourceRoom roomDetail = roomService.getById(oldList.getRoomCode());
			oldList.setRoomType(roomDetail.getRoomType());
			String cycle = String.valueOf(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.CYCLE.getMessage()).getValue());
			LocalDate cycleDate = LocalDate.of(Integer.parseInt(cycle), 12, 31);
			LocalDate oprtDateFirst = oldList.getOprtDateFirst();
			String age = ageConfigService.getOneByDeviceType(oldList.getDeviceType());
			double between = ChronoUnit.DAYS.between(oprtDateFirst, cycleDate);
			double result = Math.ceil(between / 365) - Double.parseDouble(age);
			String roomUUID = oldList.getRoomCode();
			LambdaQueryWrapper<ResourceRoom> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ResourceRoom::getUuid, roomUUID).eq(ResourceRoom::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
			ResourceRoom room = roomService.getOne(queryWrapper);
			oldList.setRoomFunction(room.getRoomFunction());
			oldList.setIsChange(String.valueOf(Integer.parseInt(OldDeviceConversionEnum.NEED.getCode())));
			oldList.setOverAge(result);
			oldList.setReviewLibraryMark(0);
			oldList.setStatus(0);
			oldList.setScore(0D);
			oldList.setCustomizeScore(0D);
			oldList.setDeptScore(0D);
			oldList.setMaintenanceScore(0D);
			oldList.setAppriseOwnScore(0D);
			oldList.setServiceRiskScore(0D);
			oldList.setDeviceStatusScore(0D);
			oldList.setOverAgeScore(0D);
			oldList.setRoomFunctionScore(0D);
			oldList.setRoomLevelScore(0D);
			oldList.setScoreCycle(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.CYCLE.getMessage()).getValue());
		}
		//保存
		saveBatch(deviceOldList);
		//计算分数
		compute(deviceOldList);
		return deviceOldList;
	}

	@Override
	public DeviceOldListVO detail(DeviceOldListDTO deviceOldList) {
		IdevelopUser user = SecureUtil.getUser();
		DeviceOldListVO deviceOldListVO = baseMapper.getDetail(deviceOldList);
		String roomTypeItem = OldDeviceConversionEnum.getMessage(deviceOldListVO.getRoomType());
		String roomFunctionItem = OldDeviceConversionEnum.getMessage(String.valueOf(deviceOldListVO.getRoomFunction()));
		String operationItem = OldDeviceConversionEnum.getMessage(String.valueOf(deviceOldListVO.getOperationCondition()));
		Double result = deviceOldListVO.getOverAge();
		String overAgeItem = null;
		if (result > 0) {
			overAgeItem = OldDeviceScoreConfigEnum.OVERAGE.getMessage();
		} else {
			overAgeItem = OldDeviceScoreConfigEnum.NO_OVERAGE.getMessage();
		}
		//运维部门
		String operationDeptCode = deviceOldList.getOperationDeptCode();
		//产权部门
		String propertyDeptCode = deviceOldList.getPropertyDeptCode();
		String corpId = user.getCorpId();
		String deptItem = null;
		if (user.getRegionCode().length() == 4) {
			R<List<Dept>> listR = deptClient.getByPidAndFullName(corpId, "数字化与通信工作部（数据中心）");
			String id = String.valueOf(listR.getData().get(0).getId());
			if (operationDeptCode.equals(id) && propertyDeptCode.equals(id)) {
				deptItem = OldDeviceScoreConfigEnum.ALL_DIGITIZATION.getMessage();
			} else if (propertyDeptCode.equals(id)) {
				deptItem = OldDeviceScoreConfigEnum.PART_DIGITIZATION.getMessage();
			} else if (!operationDeptCode.equals(id) && !propertyDeptCode.equals(id)) {
				deptItem = OldDeviceScoreConfigEnum.NO_DIGITIZATION.getMessage();
			}
		} else {
			R<List<Dept>> listR = deptClient.getByPidAndFullName(corpId, "运维检修部");
			String id = String.valueOf(listR.getData().get(0).getId());
			if (id.equals(operationDeptCode) && id.equals(propertyDeptCode)) {
				deptItem = OldDeviceScoreConfigEnum.ALL_DIGITIZATION.getMessage();
			} else if (id.equals(propertyDeptCode)) {
				deptItem = OldDeviceScoreConfigEnum.PART_DIGITIZATION.getMessage();
			} else if (!id.equals(operationDeptCode) && !id.equals(propertyDeptCode)) {
				deptItem = OldDeviceScoreConfigEnum.NO_DIGITIZATION.getMessage();
			}
		}
		String serviceRiskItem = "近三年故障次数：" + deviceOldListVO.getFaultCount() + "  " + "近三年隐患次数：" + deviceOldListVO.getHiddenCount();
		String appriseOwnItem = OldDeviceConversionEnum.getMessage(String.valueOf(deviceOldListVO.getIsChange()));
		String customizeItem = null;
		Integer total = deviceOldListVO.getDeviceCount();
		if ("1119594493575168".equals(deviceOldListVO.getDeviceType()) || "1119595156275200".equals(deviceOldListVO.getDeviceType()) || "1119595382767616".equals(deviceOldListVO.getDeviceType())) {
			if (total <= 1) {
				customizeItem = OldDeviceScoreConfigEnum.NO_CONTENTING.getMessage();
			} else if (total > 1 && total == 2) {
				customizeItem = OldDeviceScoreConfigEnum.CONTENTING_EQUALS_TWO.getMessage();
			} else if (total > 1 && total > 2) {
				customizeItem = OldDeviceScoreConfigEnum.CONTENTING_AFTER_TWO.getMessage();
			}
		} else {
			customizeItem = OldDeviceScoreConfigEnum.OTHER.getMessage();
		}
		deviceOldListVO.setRoomTypeItem(roomTypeItem);
		deviceOldListVO.setRoomFunctionItem(roomFunctionItem);
		deviceOldListVO.setOverAgeItem(overAgeItem);
		deviceOldListVO.setMaintenanceItem(operationItem);
		deviceOldListVO.setDeptItem(deptItem);
		deviceOldListVO.setServiceRiskItem(serviceRiskItem);
		deviceOldListVO.setAppriseOwnItem(appriseOwnItem);
		deviceOldListVO.setCustomizeItem(customizeItem);
//		deviceOldListVO.setScoreCycle(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.CYCLE.getMessage()).getValue());
		LambdaQueryWrapper<DeviceOldFile> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceOldFile::getDeviceCode,deviceOldListVO.getDeviceCode());
		List<DeviceOldFile> deviceOldFiles = deviceOldFileMapper.selectList(queryWrapper);
		deviceOldListVO.setOldFileList(deviceOldFiles);
		return deviceOldListVO;
	}

	@Override
	public R compute(List<DeviceOldList> deviceOldList) {
		IdevelopUser user = SecureUtil.getUser();
		for (DeviceOldList device : deviceOldList) {
			//机房级别A
			Double roomLevel = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceConversionEnum.getMessage(device.getRoomType())).getValue());
			//机房功能B
			Double roomFunction = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceConversionEnum.getMessage(String.valueOf(device.getRoomFunction()))).getValue());
			//设备状态S
			Double deviceStatus = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceConversionEnum.getMessage(device.getDeviceStatus())).getValue());
			//运维情况C
			Double operationCondition = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceConversionEnum.getMessage(String.valueOf(device.getOperationCondition()))).getValue());
			//超龄时间T
			Double dateScore = 0.00;
			Double result = device.getOverAge();
			if (result > 0) {
				dateScore = result + Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.OVERAGE.getMessage()).getValue());
			} else {
				dateScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.NO_OVERAGE.getMessage()).getValue());
			}
			//运维部门
			String operationDeptCode = device.getOperationDeptCode();
			//产权部门
			String propertyDeptCode = device.getPropertyDeptCode();
			String corpId = user.getCorpId();
			//设备产权和运维部门D
			Double deptScore = 0.00;
			if (user.getRegionCode().length() == 4) {
				R<List<Dept>> listR = deptClient.getByPidAndFullName(corpId, "数字化与通信工作部（数据中心）");
				String id = String.valueOf(listR.getData().get(0).getId());
				if (operationDeptCode.equals(id) && propertyDeptCode.equals(id)) {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.ALL_DIGITIZATION.getMessage()).getValue());
				} else if (propertyDeptCode.equals(id)) {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.PART_DIGITIZATION.getMessage()).getValue());
				} else if (!operationDeptCode.equals(id) && !propertyDeptCode.equals(id)) {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.NO_DIGITIZATION.getMessage()).getValue());
				} else {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.NO_DIGITIZATION.getMessage()).getValue());
				}
			} else {
				R<List<Dept>> listR = deptClient.getByPidAndFullName(corpId, "运维检修部");
				String id = String.valueOf(listR.getData().get(0).getId());
				if (operationDeptCode.equals(id) && propertyDeptCode.equals(id)) {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.ALL_DIGITIZATION.getMessage()).getValue());
				} else if (propertyDeptCode.equals(id)) {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.PART_DIGITIZATION.getMessage()).getValue());
				} else if (!operationDeptCode.equals(id) && !propertyDeptCode.equals(id)) {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.NO_DIGITIZATION.getMessage()).getValue());
				} else {
					deptScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.NO_DIGITIZATION.getMessage()).getValue());
				}
			}
			//服务风险E
			Double failCount = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.FAULT_COUNT.getMessage()).getValue());
			Double hiddenCount = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.HIDDEN_COUNT.getMessage()).getValue());
			Double serviceRiskScore = device.getFaultCount() * failCount + device.getHiddenCount() * hiddenCount;
			//F 自评
			Double isChangeScore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceConversionEnum.getMessage(String.valueOf(device.getIsChange()))).getValue());
			//n-1 情况  H
			Double hscore = 0.00;
			if (OldDeviceScoreConfigEnum.POWER_ROOM.getMessage().equals(device.getRoomType()) || OldDeviceScoreConfigEnum.POWER_ROOM.getMessage().equals(device.getRoomType())) {
				hscore = 1.00;
			} else {
//				String deviceCode = device.getDeviceCode();
//				//查询cmdb 获取机房uuid
				Query query = new Query();
				query.setCurrent(1);
				query.setSize(1000);
//				List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
//				CiCientitySearchVO searchVO = new CiCientitySearchVO();
//				searchVO.setAttrName(CmdbAttrConstant.DEVICE_CODE);
//				searchVO.setExpression(Expression.EQUAL);
//				searchVO.setAttrValue(deviceCode);
//				ciCientitySearchVOS.add(searchVO);
//				FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
//				String roomUUID = null;
//				if (ObjectUtil.isNotEmpty(jsonObject.getData())) {
//					roomUUID = String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.COMPUTER_ROOM_CODE));
//				}
				String roomUUID = device.getRoomCode();
				//根据机房uuid查询，设备类型查询设备列表
				List<CiCientitySearchVO> ciCientitySearchVOS1 = new ArrayList<>();
				CiCientitySearchVO searchVO1 = new CiCientitySearchVO();
				searchVO1.setAttrName(CmdbAttrConstant.COMPUTER_ROOM_CODE);
				searchVO1.setExpression(Expression.EQUAL);
				searchVO1.setAttrValue(roomUUID);
				CiCientitySearchVO searchVO2 = new CiCientitySearchVO();
				searchVO2.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
				searchVO2.setAttrValue(device.getDeviceType());
				searchVO2.setExpression(Expression.EQUAL);
				ciCientitySearchVOS1.add(searchVO1);
				ciCientitySearchVOS1.add(searchVO2);
				FeignCiCientity jsonObject1 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS1, query);
				Integer total = 0;
				if (ObjectUtil.isNotEmpty(jsonObject1.getData())) {
					//机房下设备总数
					total = jsonObject1.getTotal();
					device.setDeviceCount(total);
				}
				//判断满不满足n-1
				if (cmdbCientityProperties.getT10901().equals(device.getDeviceType())) {
					if (total <= 1) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.NO_CONTENTING.getMessage(), cmdbCientityProperties.getT10901()).getValue());
					} else if (total > 1 && total == 2) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.CONTENTING_EQUALS_TWO.getMessage(), cmdbCientityProperties.getT10901()).getValue());
					} else if (total > 1 && total > 2) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.CONTENTING_AFTER_TWO.getMessage(), cmdbCientityProperties.getT10901()).getValue());
					}
				} else if (cmdbCientityProperties.getT10903().equals(device.getDeviceType())) {
					if (total <= 1) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.NO_CONTENTING.getMessage(), cmdbCientityProperties.getT10903()).getValue());
					} else if (total > 1 && total == 2) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.CONTENTING_EQUALS_TWO.getMessage(), cmdbCientityProperties.getT10903()).getValue());
					} else if (total > 1 && total > 2) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.CONTENTING_AFTER_TWO.getMessage(), cmdbCientityProperties.getT10903()).getValue());
					}
				} else if (cmdbCientityProperties.getT10902().equals(device.getDeviceType())) {
					if (total <= 1) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.NO_CONTENTING.getMessage(), cmdbCientityProperties.getT10902()).getValue());
					} else if (total > 1 && total == 2) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.CONTENTING_EQUALS_TWO.getMessage(), cmdbCientityProperties.getT10902()).getValue());
					} else if (total > 1 && total > 2) {
						hscore = Double.parseDouble(deviceOldModelConfigService.getValueOfType(OldDeviceScoreConfigEnum.CONTENTING_AFTER_TWO.getMessage(), cmdbCientityProperties.getT10902()).getValue());
					}
				} else {
					hscore = Double.parseDouble(deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.OTHER.getMessage()).getValue());
				}

			}
			Double aDouble = roomLevel * roomFunction * deviceStatus * (dateScore * operationCondition + serviceRiskScore + hscore) * deptScore * isChangeScore;
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			Double score = Double.parseDouble(decimalFormat.format(aDouble));
			Double serviceScore = Double.parseDouble(decimalFormat.format(serviceRiskScore));
			device.setRoomLevelScore(roomLevel);
			device.setRoomFunctionScore(roomFunction);
			device.setOverAgeScore(dateScore);
			device.setDeviceStatusScore(deviceStatus);
			device.setMaintenanceScore(operationCondition);
			device.setDeptScore(deptScore);
			device.setServiceRiskScore(serviceScore);
			device.setAppriseOwnScore(isChangeScore);
			device.setCustomizeScore(hscore);
			device.setScore(score);
		}
		if (!updateBatchById(deviceOldList)) {
			return R.fail("分数计算失败！");
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R getDeviceList(DeviceOldListDTO deviceOldListDTO, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(user.getRegionCode()).expression(Expression.EQUAL).build();
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getCientityId(CmdbCientityConstant.GOVERN_YES)).expression(Expression.EQUAL).build();
		CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_RESERVE).attrValue(cmdbCientityProperties.getCientityId(CmdbCientityConstant.YES)).expression(Expression.EQUAL).build();
		List<CiCientitySearchVO> ciCientitySearchVOS = getCiCientitySearchVOS(deviceOldListDTO);
		ciCientitySearchVOS.add(searchVO);
		ciCientitySearchVOS.add(searchVO1);
		ciCientitySearchVOS.add(searchVO2);
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		List<Map<String, Object>> data = jsonObject.getData();
		Iterator<Map<String, Object>> iterator = data.iterator();
		while (iterator.hasNext()){
			Map<String, Object> device = iterator.next();
			//过滤已经提交的
			String deviceCode = String.valueOf(device.get(CmdbAttrConstant.DEVICE_CODE));
			// 是否储备设备
			Object isReserveObj = device.get(CmdbAttrConstant.IS_RESERVE);
			device.put("isChange", "10");
			if (IdevelopUtils.isCmdbNotBlack(isReserveObj) && StringUtils.equals(cmdbCientityProperties.getYesNo(),String.valueOf(isReserveObj))) {
				device.put("isChange", "9");
			}
			LambdaQueryWrapper<DeviceOldList> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(DeviceOldList::getDeviceCode,deviceCode).eq(DeviceOldList::getIsDeleted,IdevelopConstant.DB_NOT_DELETED).eq(DeviceOldList::getScoreCycle,deviceOldModelConfigService.getValue(OldDeviceScoreConfigEnum.CYCLE.getMessage()).getValue());
			DeviceOldList selectOne = baseMapper.selectOne(wrapper);
			if (ObjectUtil.isNotEmpty(selectOne)){
				iterator.remove();
				continue;
			}
			LambdaQueryWrapper<ResourceRoom> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ResourceRoom::getUuid, device.get(CmdbAttrConstant.COMPUTER_ROOM_CODE)).eq(ResourceRoom::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
			ResourceRoom resourceRoom = roomService.getOne(queryWrapper);
			if (ObjectUtil.isNotEmpty(resourceRoom)){
				device.put("roomType", resourceRoom.getRoomType());
				device.put("roomFunction", resourceRoom.getRoomFunction());
			}
			String age = ageConfigService.getOneByDeviceType(String.valueOf(device.get(CmdbAttrConstant.DEVICE_TYPE_CODE)));
			device.put("referenceAge", age);
		}
		return R.data(jsonObject);
	}

	@Override
	public Integer getRank(DeviceOldListDTO deviceOldListDTO) {
		IdevelopUser user = SecureUtil.getUser();
		if (deviceOldListDTO.getScore() == 0D){
			return 0;
		}
		LambdaQueryWrapper<DeviceOldList> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceOldList::getDeviceType, deviceOldListDTO.getDeviceType())
			.eq(DeviceOldList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
			.eq(DeviceOldList::getRegionCode, user.getRegionCode())
			.orderByDesc(DeviceOldList::getScore);
		List<DeviceOldList> deviceOldLists = baseMapper.selectList(queryWrapper);

		int targetIndex = IntStream.range(0, deviceOldLists.size()).filter(i -> deviceOldLists.get(i).getId().equals(deviceOldListDTO.getId())).findFirst().orElse(-1);
		if (targetIndex != -1) {
			int rank = targetIndex + 1;
			return rank;
		} else {
			return -1;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R refine(List<DeviceOldListDTO> deviceOldListDTOList) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		for (DeviceOldListDTO deviceOldListDTO : deviceOldListDTOList) {
			if (1 == deviceOldListDTO.getStatus()) {
				return R.fail("设备" + deviceOldListDTO.getDeviceCode() + "已经上报，请勿重复上报！");
			} else if (2 == deviceOldListDTO.getStatus()) {
				return R.fail("设备" + deviceOldListDTO.getDeviceCode() + "已经通过审批，请勿重复上报！");
			}
			deviceOldListDTO.setReviewLibraryMark(1);
			deviceOldListDTO.setStatus(1);
			List<DeviceOldFile> deviceOldFileList = deviceOldListDTO.getDeviceOldFileList();
			//删除附件表
			deviceOldFileService.delete(deviceOldListDTO.getDeviceCode());
			if (ObjectUtil.isNotEmpty(deviceOldFileList)) {
				deviceOldFileList.forEach(item -> {
					item.setDeviceCode(deviceOldListDTO.getDeviceCode());
					item.setUpdateTime(new Date());
					item.setUpdateUser(user.getUserId());
					item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				});
				boolean saveBatch = deviceOldFileService.saveBatch(deviceOldFileList);
				if (!saveBatch) {
					throw new Exception("保存附件失败");
				}
			}
		}
		saveOrUpdateBatch(Convert.convert(new TypeReference<Collection<DeviceOldList>>() {
		}, deviceOldListDTOList));
		return R.success("保存成功！");
	}

	@Override
	public R approval(List<DeviceOldList> deviceOldListDTOList) {
		return R.status(saveOrUpdateBatch(deviceOldListDTOList));
	}

	@Override
	public IPage<DeviceOldList> getList(Query query, DeviceOldListVO deviceOldList) {
		if (ObjectUtil.isNotEmpty(deviceOldList.getTop())) {
			query.setCurrent(1);
			query.setSize(deviceOldList.getTop());
		}
		LambdaQueryWrapper<DeviceOldList> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceOldList::getReviewLibraryMark, 1)
			.eq(StringUtil.isNotBlank(deviceOldList.getDeviceCategory()), DeviceOldList::getDeviceCategory, deviceOldList.getDeviceCategory())
			.eq(StringUtil.isNotBlank(deviceOldList.getDeviceType()), DeviceOldList::getDeviceType, deviceOldList.getDeviceType())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getIsChange()), DeviceOldList::getIsChange, deviceOldList.getIsChange())
			.eq(StringUtil.isNotBlank(deviceOldList.getDeviceCode()), DeviceOldList::getDeviceCode, deviceOldList.getDeviceCode())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getRoomType()), DeviceOldList::getRoomType, deviceOldList.getRoomType())
			.eq(ObjectUtil.isNotEmpty(deviceOldList.getRoomFunction()), DeviceOldList::getRoomFunction, deviceOldList.getRoomFunction())
			.eq(StringUtil.isNotBlank(deviceOldList.getOperationDeptCode()), DeviceOldList::getPropertyDeptCode, deviceOldList.getOperationDeptCode())
			.eq(StringUtil.isNotBlank(deviceOldList.getPropertyDeptCode()), DeviceOldList::getPropertyDeptCode, deviceOldList.getPropertyDeptCode())
			.between(ObjectUtil.isNotEmpty(deviceOldList.getStartScore()) && ObjectUtil.isNotEmpty(deviceOldList.getEndScore()), DeviceOldList::getScore, deviceOldList.getStartScore(), deviceOldList.getEndScore())
			.between(ObjectUtil.isNotEmpty(deviceOldList.getStartAge()) && ObjectUtil.isNotEmpty(deviceOldList.getEndAge()), DeviceOldList::getOverAge, deviceOldList.getStartAge(), deviceOldList.getEndAge())
			.orderByDesc(DeviceOldList::getScore);
		return baseMapper.selectPage(Condition.getPage(query), queryWrapper);

	}

	@NotNull
	private static List<CiCientitySearchVO> getCiCientitySearchVOS(DeviceOldListDTO deviceOldListDTO) {
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		if (StringUtil.isNotBlank(deviceOldListDTO.getDeviceCategory())) {
			CiCientitySearchVO searchVO01 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE)
				.attrValue(deviceOldListDTO.getDeviceCategory())
				.expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO01);
		}
		if (StringUtil.isNotBlank(deviceOldListDTO.getDeviceType())) {
			CiCientitySearchVO searchVO02 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE)
				.attrValue(deviceOldListDTO.getDeviceType())
				.expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO02);
		}
		if (StringUtil.isNotBlank(deviceOldListDTO.getDeviceStatus())) {
			CiCientitySearchVO searchVO03 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_STATUS_CODE)
				.attrValue(deviceOldListDTO.getDeviceStatus())
				.expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO03);
		}
		if (StringUtil.isNotBlank(deviceOldListDTO.getDeviceCode())) {
			CiCientitySearchVO searchVO04 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE)
				.attrValue(deviceOldListDTO.getDeviceCode())
				.expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO04);
		}
		if (StringUtil.isNotBlank(deviceOldListDTO.getBeginAge()) && StringUtil.isNotBlank(deviceOldListDTO.getEndAge())) {
			CiCientitySearchVO searchVO05 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.USE_AGE)
				.attrValue(deviceOldListDTO.getBeginAge() + "~" + deviceOldListDTO.getEndAge())
				.expression(Expression.BETWEEN).build();
			ciCientitySearchVOS.add(searchVO05);
		}
		if (StringUtil.isNotBlank(deviceOldListDTO.getDeviceSourceCode())) {
			CiCientitySearchVO searchVO06 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_SOURCE_CODE)
				.attrValue(deviceOldListDTO.getDeviceSourceCode())
				.expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO06);
		}
		if (StringUtil.isNotBlank(deviceOldListDTO.getRoomCode())) {
			CiCientitySearchVO searchVO07 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.COMPUTER_ROOM_CODE)
				.attrValue(deviceOldListDTO.getRoomCode())
				.expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO07);
		}

		return ciCientitySearchVOS;
	}

	/**
	 * 参数校验
	 *
	 * @param deviceOldLists
	 * @return
	 */
	private R check(List<DeviceOldList> deviceOldLists) {
		for (DeviceOldList device : deviceOldLists) {
			if (StringUtil.isBlank(device.getRoomType())) {
				return R.fail("设备：" + device.getDeviceCode() + "请补充机房类型！");
			}
			if (ObjectUtil.isEmpty(device.getRoomFunction())) {
				return R.fail("设备：" + device.getDeviceCode() + "请补充机房功能！");
			}
			if (ObjectUtil.isEmpty(device.getFaultCount())) {
				return R.fail("设备：" + device.getDeviceCode() + "请补充近三年故障次数！");
			}
			if (ObjectUtil.isEmpty(device.getHiddenCount())) {
				return R.fail("设备：" + device.getDeviceCode() + "请补充近三年隐患次数！");
			}
		}
		return R.success(ResultCode.SUCCESS);
	}
}

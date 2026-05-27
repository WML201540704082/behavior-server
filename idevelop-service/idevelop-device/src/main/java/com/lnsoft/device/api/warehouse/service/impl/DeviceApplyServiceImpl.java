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
package com.lnsoft.device.api.warehouse.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.DeviceApplyEnum;
import com.lnsoft.common.enums.hussar.DeviceApplyOutboundOperationBpmNodeEnum;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.common.enums.hussar.OrderFileTypeEnum;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.mapper.DeviceInventoryMapper;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.DeviceApplyDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceApplyDetailDTO;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import com.lnsoft.device.api.warehouse.entity.*;
import com.lnsoft.device.api.warehouse.mapper.DeviceApplyMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOperationDetailMapper;
import com.lnsoft.device.api.warehouse.service.*;
import com.lnsoft.device.api.warehouse.vo.*;
import com.lnsoft.device.entity.DeviceOrderFile;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备申请表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Service
public class DeviceApplyServiceImpl extends BaseServiceImpl<DeviceApplyMapper, DeviceApply> implements IDeviceApplyService {

	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IDeviceApplyDetailService deviceApplyDetailService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private IDeviceOutboundService deviceOutboundService;
	@Resource
	private IDeviceOutboundDetailService deviceOutboundDetailService;
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private IDeviceOrderFileService deviceOrderFileService;
	@Resource
	private DeviceInventoryMapper deviceInventoryMapper;
	@Resource
	private DeviceOperationDetailMapper deviceOperationDetailMapper;

	/**
	 * 新增/暂存设备申请表
	 *
	 * @param deviceApplyDTO 新增/暂存设备申请单
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> insertDeviceApply(DeviceApplyDTO deviceApplyDTO) {
		IdevelopUser user = SecureUtil.getUser();
		DeviceApply deviceApply = Convert.convert(DeviceApply.class, deviceApplyDTO);
		// 验证设备个人信息身份证号信息
		String message = checkDeviceUserIdCard(deviceApplyDTO);
		if (StringUtil.isNotBlank(message)) {
			return R.fail(message);
		}
		deviceApply.setSubmitDigitalFlag(user.getExt().get("tag").toString().equals("1") ? 0 : 1);
		deviceApply.setRegionCode(user.getRegionCode());
		if ("0".equals(deviceApply.getOldToNew())) {
			deviceApplyDTO.setApplyNum(deviceApplyDTO.getDeviceApplyDetailDTOList().size());
		}
		// 判断是新增还是修改
		if (Objects.isNull(deviceApplyDTO.getId())) {
			String applyNum = orderNumberUtil.generateOrderNumber(WorkOrderTypeEnum.SQ.getValue(), CacheNames.DEVICE_APPLY_NUMBER);
			deviceApply.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			deviceApply.setStatus(0);
			deviceApply.setOperation("0");
			deviceApply.setApplyNo(applyNum);
			deviceApply.setCreateDept(user.getDeptId());
			deviceApply.setCreateTime(new Date());
			deviceApply.setCreateUser(user.getUserId());
			baseMapper.insert(deviceApply);
			deviceApplyDTO.setId(deviceApply.getId());
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(deviceApply.getId()).logData(deviceApplyDTO.toString()).params(deviceApplyDTO.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_APPLY.getCode()).title("新增暂存设备申请").build());
		} else {
			DeviceApply selectOne = baseMapper.selectOne(new LambdaQueryWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApplyDTO.getId())
				.eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (Objects.isNull(selectOne) || !(DeviceApplyEnum.TEMPORARILY.getCode().equals(selectOne.getStatus()) || DeviceApplyEnum.DEVICE_APPLY.getCode().equals(selectOne.getStatus()))) {
				return R.fail("当前工单不允许修改");
			}
			deviceApply.setUpdateTime(new Date());
			deviceApply.setUpdateUser(user.getUserId());
			baseMapper.updateById(deviceApply);
			// 删除设备申请单设备详情数据
			deleteDeviceApplyDetail(deviceApplyDTO);
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(deviceApply.getId()).logData(deviceApplyDTO.toString()).params(deviceApplyDTO.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_APPLY.getCode()).title("修改暂存设备申请").build());
		}
		// 新增设备申请工单详情数据
		batchInsertDeviceApplyDetail(deviceApplyDTO, user);
		// 新增设备工单附件
		batchInsertDeviceFile(OrderFileTypeEnum.APPLY.getOrderType(), deviceApply.getId(), deviceApplyDTO.getDeviceOrderFileDTOList());
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 验证设备个人使用身份证号是否已经存在
	 *
	 * @param deviceApplyDTO 设备信息
	 * @return R
	 */
	@Nullable
	private String checkDeviceUserIdCard(DeviceApplyDTO deviceApplyDTO) {
		if (CollectionUtil.isNotEmpty(deviceApplyDTO.getDeviceApplyDetailDTOList())) {
			List<String> collect = deviceApplyDTO.getDeviceApplyDetailDTOList().stream().filter(deviceApplyDetailDTO -> deviceApplyDetailDTO.getUserType() == 1)
				.map(DeviceApplyDetailDTO::getUserCard).collect(Collectors.toList());
			long count = deviceApplyDTO.getDeviceApplyDetailDTOList().stream().filter(deviceApplyDetailDTO -> deviceApplyDetailDTO.getUserType() == 1
				&& StringUtil.isNotBlank(deviceApplyDetailDTO.getUserCard())).count();
			if (collect.size() != count) {
				return "请维护个人设备的身份证号";
			}
			List<DeviceApplyDetail> checkUserInfo = deviceApplyDTO.getDeviceApplyDetailDTOList().stream().filter(deviceApplyDetailDTO ->
				StringUtils.isAnyBlank(deviceApplyDetailDTO.getUserName(), deviceApplyDetailDTO.getUserPhone())).collect(Collectors.toList());
			if (checkUserInfo.size() > 0) {
				return "请完善设备使用人信息";
			}
		}
		return null;
	}

	/**
	 * 新增设备工单附件
	 *
	 * @param orderType              工单类型
	 * @param id                     工单id
	 * @param deviceOrderFileDTOList 附件信息
	 */
	private void batchInsertDeviceFile(String orderType, String id, List<DeviceOrderFileDTO> deviceOrderFileDTOList) {
		deviceOrderFileService.remove(new LambdaQueryWrapper<DeviceOrderFile>().eq(DeviceOrderFile::getOrderId, id).eq(DeviceOrderFile::getOrderType, orderType)
			.eq(DeviceOrderFile::getOrderFileType, OrderFileTypeEnum.getFileType(orderType)));
		if (CollectionUtil.isNotEmpty(deviceOrderFileDTOList)) {
			IdevelopUser user = SecureUtil.getUser();
			deviceOrderFileDTOList.forEach(item -> {
				item.setId(null);
				item.setOrderId(id);
				item.setOrderType(orderType);
				item.setOrderFileType(OrderFileTypeEnum.getFileType(orderType));
				item.setCreateTime(new Date());
				item.setCreateUser(user.getUserId());
				item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			});
			deviceOrderFileService.saveBatch(Convert.convert(new TypeReference<Collection<DeviceOrderFile>>() {
			}, deviceOrderFileDTOList));
		}
	}

	/**
	 * 设备申请
	 *
	 * @param deviceApplyDTO 设备申请
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceApplyVO> submitDeviceApply(DeviceApplyDTO deviceApplyDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		DeviceApply deviceApply = Convert.convert(DeviceApply.class, deviceApplyDTO);
		// 验证申请设备库存信息
		String inventoryMessage = checkInventoryNum(deviceApplyDTO, user);
		if (StringUtils.isNotBlank(inventoryMessage)) {
			return R.fail(inventoryMessage);
		}
		// 验证设备个人信息身份证号信息
		String message = checkDeviceUserIdCard(deviceApplyDTO);
		if (StringUtil.isNotBlank(message)) {
			return R.fail(message);
		}
		// 如果是以旧换新的工单，需要判断选择了旧设备信息
		if ("0".equals(deviceApplyDTO.getOldToNew())) {
			List<DeviceApplyDetailDTO> applyDetailDTOList = deviceApplyDTO.getDeviceApplyDetailDTOList().stream().filter(deviceApplyDetailDTO ->
				StringUtils.isBlank(deviceApplyDetailDTO.getOldDeviceId())).collect(Collectors.toList());
			if (applyDetailDTOList.size() > 0) {
				return R.fail("以旧换新工单需要选择旧设备");
			}
			List<DeviceApply> deviceApplyList = baseMapper.selectList(new LambdaQueryWrapper<DeviceApply>()
				.in(DeviceApply::getStatus, DeviceApplyEnum.DEVICE_APPLY_DIRECTOR.getCode(), DeviceApplyEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getCode(),
					DeviceApplyEnum.DEVICE_OUTBOUND.getCode(), DeviceApplyEnum.DEVICE_OPERATION.getCode()).select(DeviceApply::getId));
			if (CollectionUtils.isNotEmpty(deviceApplyList)) {
				long count = deviceApplyDetailService.count(new LambdaQueryWrapper<DeviceApplyDetail>()
					.in(DeviceApplyDetail::getApplyId, deviceApplyList.stream().map(DeviceApply::getId).collect(Collectors.toList()))
					.in(DeviceApplyDetail::getOldDeviceId, deviceApplyDTO.getDeviceApplyDetailDTOList().stream().map(DeviceApplyDetailDTO::getOldDeviceId).collect(Collectors.toList())));
				if (count > 0) {
					return R.fail("所选设备已经进行以旧换新，请重新选择");
				}
			}
			if ("0".equals(deviceApply.getOldToNew())) {
				deviceApplyDTO.setApplyNum(deviceApplyDTO.getDeviceApplyDetailDTOList().size());
			}
		}
		deviceApply.setSubmitTime(new Date());
		deviceApply.setSubmitUser(user.getUserId().toString());
		deviceApply.setSubmitDigitalFlag(user.getExt().get("tag").toString().equals("1") ? 0 : 1);
		// 判断是新增还是修改
		if (Objects.isNull(deviceApplyDTO.getId())) {
			String applyNum = orderNumberUtil.generateOrderNumber(WorkOrderTypeEnum.SQ.getValue(), CacheNames.DEVICE_APPLY_NUMBER);
			deviceApply.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			deviceApply.setStatus(1);
			deviceApply.setOperation("0");
			deviceApply.setApplyNo(applyNum);
			deviceApply.setCreateDept(user.getDeptId());
			deviceApply.setCreateTime(new Date());
			deviceApply.setCreateUser(user.getUserId());
			baseMapper.insert(deviceApply);
			deviceApply.setRegionCode(user.getRegionCode());
			deviceApplyDTO.setId(deviceApply.getId());
		} else {
			Long count = baseMapper.selectCount(new LambdaQueryWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApplyDTO.getId())
				.and(i -> i.eq(DeviceApply::getStatus, DeviceApplyEnum.TEMPORARILY.getCode()).or().eq(DeviceApply::getStatus, DeviceApplyEnum.DEVICE_APPLY.getCode()))
				.eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (count == 0) {
				return R.fail("当前工单不允许提交审批");
			}
			deviceApply.setUpdateTime(new Date());
			deviceApply.setUpdateUser(user.getUserId());
			baseMapper.updateById(deviceApply);
			// 删除设备申请单设备详情数据
			deleteDeviceApplyDetail(deviceApplyDTO);
		}
		// 新增设备工单附件
		batchInsertDeviceFile(OrderFileTypeEnum.APPLY.getOrderType(), deviceApply.getId(), deviceApplyDTO.getDeviceOrderFileDTOList());
		// 新增设备申请工单详情数据
		batchInsertDeviceApplyDetail(deviceApplyDTO, user);
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", deviceApply.getId());
		variable.put("orderNo", deviceApply.getApplyNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getRealName());
		variable.put("regionCode", user.getRegionCode());
		variable.put("ordinaryFlag", user.getExt().get("tag").toString().equals("1") ? 0 : 1);
		HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder()
				.processDefinitionKey(HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark())
			.businessKey(deviceApply.getApplyNo()).variable(variable).build();
		HussarCreateVo hussarBpm = new HussarCreateVo();
		if (StringUtils.isBlank(deviceApplyDTO.getProcessInsId())) {
			// 发起流程
			try {
				hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
			} catch (Exception e) {
				throw new Exception("创建流程发生异常");
			}
		}
		//  获取当前节点操作角色
		List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceApply.getApplyNo());
		String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
		hussarBpmDTO.setBusinessKey(deviceApply.getApplyNo());
		// 判断是否是数字化部发起，如果是数字化部发起，过滤人员信息，如果不是，通过角色发起
		if (user.getExt().get("tag").toString().equals("1")) {
			hussarBpmDTO.setParticipantType("2");
		} else {
			hussarBpmDTO.setParticipantType("1");
			variable.put("digitalFlag", "0");
			variable.put("orderDept", user.getDeptId());
		}
		hussarBpmDTO.setVariable(variable);
		hussarBpmDTO.setTaskType("1");
		hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark());
		hussarBpmService.hussarSubmit(hussarBpmDTO);
		// 记录流程id
		baseMapper.update(new LambdaUpdateWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApply.getId())
			.set(DeviceApply::getStatus, user.getExt().get("tag").toString().equals("1") ? DeviceApplyEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getCode() :
				DeviceApplyEnum.DEVICE_APPLY_DIRECTOR.getCode())
			.set(DeviceApply::getProcessInsId, hussarBpm.getProcessInsId())
			.set(DeviceApply::getProcessStatus, user.getExt().get("tag").toString().equals("1") ? DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getNode()
				: DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode()));
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(deviceApply.getId()).logData(deviceApplyDTO.toString()).params(deviceApplyDTO.toString())
			.optRole(roleName).optType(OptTypeEnum.DEVICE_APPLY.getCode()).title("发起设备申请").build());
		// 记录审核流程
		approveRecordService.commonRecord(ApproveRecord.builder().filingNo(deviceApply.getId())
			.optRole(roleName).optType(OptTypeEnum.DEVICE_APPLY.getCode())
			.nodeId(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode())
			.nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode()))
			.filingCode(deviceApply.getApplyNo()).approveStatus(0)
			.optTitle("发起设备申请").optOpinion("发起设备申请").build());
		return R.data(Convert.convert(DeviceApplyVO.class, deviceApply));
	}

	/**
	 * 验证设备申请库存信息
	 *
	 * @param deviceApplyDTO 申请信息
	 * @param user           用户信息
	 * @return boolean
	 */
	private String checkInventoryNum(DeviceApplyDTO deviceApplyDTO, IdevelopUser user) {
		// 验证申请设备库存信息
		List<DeviceInventory> deviceInventoryList = deviceInventoryMapper.selectList(new LambdaQueryWrapper<DeviceInventory>()
			.eq(DeviceInventory::getRegionCode, user.getRegionCode()).eq(DeviceInventory::getCropId, user.getCorpId())
			.eq(DeviceInventory::getDeviceCategory, deviceApplyDTO.getDeviceCategory()).eq(DeviceInventory::getDeviceType, deviceApplyDTO.getDeviceType())
			.select(DeviceInventory::getInventoryNum));
		if (CollectionUtils.isEmpty(deviceInventoryList)) {
			return "暂无库存信息，无法进行设备申请";
		}
		int inventoryNum = deviceInventoryList.stream().mapToInt(DeviceInventory::getInventoryNum).sum();
		int applyNum = 0;
		List<DeviceApply> deviceApplyList = baseMapper.selectList(new LambdaQueryWrapper<DeviceApply>()
			.eq(DeviceApply::getRegionCode, user.getRegionCode()).eq(DeviceApply::getReceiveUnit, user.getCorpId())
			.eq(DeviceApply::getDeviceCategory, deviceApplyDTO.getDeviceCategory()).eq(DeviceApply::getDeviceType, deviceApplyDTO.getDeviceType())
			.notIn(DeviceApply::getStatus, DeviceApplyEnum.TEMPORARILY.getCode(), DeviceApplyEnum.DEVICE_APPLY.getCode(),
				DeviceApplyEnum.DEVICE_OPERATION.getCode(), DeviceApplyEnum.FINISH.getCode())
			.ne(Objects.nonNull(deviceApplyDTO.getId()), DeviceApply::getId, deviceApplyDTO.getId()).select(DeviceApply::getApplyNum));
		if (CollectionUtils.isNotEmpty(deviceApplyList)) {
			applyNum = deviceApplyList.stream().mapToInt(DeviceApply::getApplyNum).sum();
		}

		if (inventoryNum < deviceApplyDTO.getDeviceApplyDetailDTOList().size() + applyNum) {
			return "库存不足无法进行设备申请";
		}
		return null;
	}

	/**
	 * 删除 设备申请表
	 *
	 * @param ids 删除工单id
	 * @return R
	 */
	@Override
	public R<Integer> removeDeviceApply(String ids) {
		IdevelopUser user = SecureUtil.getUser();
		List<String> idList = Arrays.asList(ids.split(","));
		Long count = baseMapper.selectCount(new LambdaQueryWrapper<DeviceApply>().in(DeviceApply::getId, idList).eq(DeviceApply::getStatus, DeviceApplyEnum.TEMPORARILY.getCode())
			.eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (count != idList.size()) {
			return R.fail("所选设备申请单不允许删除");
		}
		if (baseMapper.update(new LambdaUpdateWrapper<DeviceApply>().in(DeviceApply::getId, idList).eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
			.set(DeviceApply::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceApply::getUpdateTime, new Date()).set(DeviceApply::getUpdateUser, user.getUserId())) != idList.size()) {
			return R.fail("删除失败");
		}
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 详情
	 *
	 * @param deviceApply 查询参数
	 * @return R
	 */
	@Override
	public R<DeviceApplyVO> deviceApply(DeviceApply deviceApply) {
		DeviceApply deviceApplyInfo = baseMapper.selectOne(new LambdaQueryWrapper<DeviceApply>()
			.eq(Objects.nonNull(deviceApply.getId()), DeviceApply::getId, deviceApply.getId())
			.eq(StringUtil.isNotBlank(deviceApply.getApplyNo()), DeviceApply::getApplyNo, deviceApply.getApplyNo())
			.eq(StringUtil.isNotBlank(deviceApply.getOutboundNo()), DeviceApply::getOutboundNo, deviceApply.getOutboundNo())
			.eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (Objects.isNull(deviceApplyInfo)) {
			return R.data(new DeviceApplyVO());
		}
		DeviceApplyVO deviceApplyVO = Convert.convert(DeviceApplyVO.class, deviceApplyInfo);
		List<DeviceOrderFileVO> deviceOrderFileVOList = new ArrayList<>();
		// 获取设备申请数据
		List<DeviceApplyDetail> deviceApplyDetailList = deviceApplyDetailService.list(new LambdaQueryWrapper<DeviceApplyDetail>()
			.eq(DeviceApplyDetail::getApplyId, deviceApplyVO.getId()).eq(DeviceApplyDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		deviceApplyVO.setDeviceApplyDetailVOList(Convert.convert(new TypeReference<List<DeviceApplyDetailVO>>() {
		}, deviceApplyDetailList));
		// 获取设备信息
		deviceOrderFileService.getOrderFileList(deviceOrderFileVOList, deviceApplyInfo.getId(), OrderFileTypeEnum.APPLY.getOrderType());
		if (StringUtil.isNotBlank(deviceApplyVO.getOutboundNo())) {
			// 获取设备出库信息数据
			DeviceOutbound deviceOutbound = deviceOutboundService.getOne(new LambdaQueryWrapper<DeviceOutbound>().eq(DeviceOutbound::getApplyNo, deviceApplyVO.getApplyNo())
				.eq(DeviceOutbound::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (Objects.nonNull(deviceOutbound)) {
				deviceApplyVO.setDeviceOutboundVO(Convert.convert(DeviceOutboundVO.class, deviceOutbound));
				List<DeviceOutboundDetail> deviceOutboundDetailList = deviceOutboundDetailService.list(new LambdaQueryWrapper<DeviceOutboundDetail>()
					.in(DeviceOutboundDetail::getOutboundId, deviceOutbound.getId()).eq(DeviceOutboundDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
				deviceApplyVO.setDeviceOperationDetailVOList(Convert.convert(new TypeReference<List<DeviceOperationDetailVO>>() {
				}, deviceOutboundDetailList));
				// 获取设备信息
				deviceOrderFileService.getOrderFileList(deviceOrderFileVOList, deviceOutbound.getId(), OrderFileTypeEnum.OUTBOUND.getOrderType());
			}
		}
		deviceApplyVO.setDeviceOrderFileVOList(deviceOrderFileVOList);
		return R.data(deviceApplyVO);
	}

	/**
	 * 分页 设备申请表
	 *
	 * @param deviceApplyDTO 查询条件
	 * @param query          分页信息
	 * @return R
	 */
	@Override
	public R<IPage<DeviceApplyVO>> selectDeviceApplyList(DeviceApplyDTO deviceApplyDTO, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		deviceApplyDTO.setRegionCode(user.getRegionCode());
		return R.data(baseMapper.selectDeviceApplyList(Condition.getPage(query), deviceApplyDTO));
	}

	/**
	 * 个人工作台查询申请单工单
	 *
	 * @param deviceApplyDTO 查询条件
	 * @param query          分页条件
	 * @return R
	 */
	@Override
	public R<IPage<DeviceApplyVO>> deskList(DeviceApplyDTO deviceApplyDTO, Query query) {
		if (StringUtil.isBlank(deviceApplyDTO.getOrderNoList())) {
			return R.data(new Page<>());
		}
		LambdaQueryWrapper<DeviceApply> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(DeviceApply::getApplyNo, Arrays.asList(deviceApplyDTO.getOrderNoList().split(",")))
			.orderByDesc(DeviceApply::getSubmitTime);
		if (deviceApplyDTO.getQueryHandleFlag() == 0) {
			queryWrapper.isNull(DeviceApply::getOutboundNo);
		}
		IPage<DeviceApply> deviceApplyIPage = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
		return R.data(Convert.convert(new TypeReference<IPage<DeviceApplyVO>>() {
		}, deviceApplyIPage));
	}

	/**
	 * 个人工作台审核更新工单状态，增加日志记录
	 *
	 * @param deviceApplyDTO 更新工单信息
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deskUpdateStatus(DeviceApplyDTO deviceApplyDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		DeviceApply deviceApply = baseMapper.selectOne(new LambdaQueryWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApplyDTO.getId()));
		if (Objects.isNull(deviceApply)) {
			return R.fail("当前工单不存在");
		}
		// 当前操作节点
		String recordStatus = deviceApply.getProcessStatus();
		String processStatus = null;
		//  获取当前节点操作角色
		List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceApply.getApplyNo());
		String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
		// 根据当前节点以及审批意见，更新工单状态
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode()) && deviceApplyDTO.getWorkerStatus() == 0) {
			// 验证申请设备库存信息
			String inventoryMessage = checkInventoryNum(deviceApplyDTO, user);
			if (StringUtils.isNotBlank(inventoryMessage)) {
				return R.fail(inventoryMessage);
			}
			deviceApplyDTO.setComment("发起设备申请");
			deviceApplyDTO.setSubmitTime(new Date());
			baseMapper.updateById(deviceApplyDTO);
			// 删除设备申请单设备详情数据
			deleteDeviceApplyDetail(deviceApplyDTO);
			// 新增设备工单附件
			batchInsertDeviceFile(OrderFileTypeEnum.APPLY.getOrderType(), deviceApply.getId(), deviceApplyDTO.getDeviceOrderFileDTOList());
			// 新增设备申请工单详情数据
			batchInsertDeviceApplyDetail(deviceApplyDTO, user);
			if (deviceApply.getSubmitDigitalFlag() == 0) {
				processStatus = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getNode();
			} else {
				processStatus = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode();
			}
		}
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode()) && deviceApplyDTO.getWorkerStatus() == 0) {
			processStatus = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getNode();
		}
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode()) && deviceApplyDTO.getWorkerStatus() == 1) {
			processStatus = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode();
			LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ApproveRecord::getOptOpinion,"待审批").set(ApproveRecord::getOptTitle,"待审批").eq(ApproveRecord::getFilingNo,deviceApplyDTO.getId()).eq(ApproveRecord::getNodeId,processStatus);
			approveRecordService.update(updateWrapper);
		}
		String outboundNo = null;
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getNode()) && deviceApplyDTO.getWorkerStatus() == 0) {
			DeviceOutbound deviceOutbound = Convert.convert(DeviceOutbound.class, deviceApply);
			// 生成出库单号，并赋值申请单号
			outboundNo = orderNumberUtil.generateOrderNumber(WorkOrderTypeEnum.CK.getValue(), CacheNames.DEVICE_OUTBOUND_NUMBER);
			deviceOutbound.setApplyUser(deviceApply.getApplyUser());
			deviceOutbound.setApplyUserName(deviceApply.getApplyUserName());
			deviceOutbound.setApplyDate(deviceApply.getApplyDate());
			deviceOutbound.setOutboundNo(outboundNo);
			deviceOutbound.setOutboundNum(deviceApply.getApplyNum());
			deviceOutbound.setSubmitTime(deviceApply.getSubmitTime());
			deviceOutbound.setProcessInsId(deviceApply.getProcessInsId());
			deviceOutbound.setProcessStatus(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OUTBOUND.getNode());
			deviceOutbound.setStatus(DeviceApplyEnum.DEVICE_OUTBOUND.getCode());
			deviceOutbound.setCreateUser(user.getUserId());
			deviceOutbound.setCreateDept(user.getDeptId());
			deviceOutbound.setCreateTime(new Date());
			deviceOutbound.setOutboundStatus("1");
			deviceOutbound.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			deviceOutboundService.save(deviceOutbound);
			// 新增设备详情信息
			batchInsertDeviceOutboundDetail(user, deviceApply, deviceOutbound);
			processStatus = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OUTBOUND.getNode();
		}
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getNode()) && deviceApplyDTO.getWorkerStatus() == 1) {
			processStatus = DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode();
			LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ApproveRecord::getOptOpinion,"待审批").set(ApproveRecord::getOptTitle,"待审批").eq(ApproveRecord::getFilingNo,deviceApplyDTO.getId()).eq(ApproveRecord::getNodeId,processStatus);
			approveRecordService.update(updateWrapper);
		}
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(deviceApplyDTO.getId()).logData(deviceApplyDTO.toString()).params(deviceApplyDTO.toString())
			.optType(OptTypeEnum.DEVICE_APPLY.getCode()).title(deviceApplyDTO.getComment()).optRole(roleName).time(new Date()).build());
		// 记录审核流程
		approveRecordService.commonRecord(ApproveRecord.builder().filingNo(deviceApplyDTO.getId())
			.optType(OptTypeEnum.DEVICE_APPLY.getCode()).nodeId(recordStatus).optRole(roleName)
			.nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(recordStatus)).optTitle(deviceApplyDTO.getComment())
			.optOpinion(deviceApplyDTO.getComment()).approveStatus(deviceApplyDTO.getWorkerStatus()).filingCode(deviceApply.getApplyNo()).build());
		// 更新工单信息
		baseMapper.update(new LambdaUpdateWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApplyDTO.getId()).set(DeviceApply::getUpdateTime, new Date())
			.set(DeviceApply::getUpdateUser, user.getUserId()).set(DeviceApply::getProcessStatus, processStatus)
			.set(DeviceApply::getStatus, DeviceApplyEnum.getCode(processStatus))
			.set(StringUtil.isNotBlank(outboundNo), DeviceApply::getOutboundNo, outboundNo));
		HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
		hussarBpmDTO.setBusinessKey(deviceApply.getApplyNo());
		hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark());
		Map<String, Object> variable = new HashMap<>();
		hussarBpmDTO.setParticipantType("2");
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode()) && deviceApplyDTO.getWorkerStatus() == 0) {
			if (deviceApply.getSubmitDigitalFlag() != 1) {
				hussarBpmDTO.setParticipantType("1");
				variable.put("orderDept", user.getDeptId());
			}
			variable.put("digitalFlag", deviceApply.getSubmitDigitalFlag());
		}
		if (recordStatus.equals(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode()) && deviceApplyDTO.getWorkerStatus() == 1) {
			hussarBpmDTO.setOrderId(deviceApply.getId());
			hussarBpmService.prevNodeReject(hussarBpmDTO);
		} else {
			if (DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIGITALIZE_DIRECTOR.getNode().equals(recordStatus) && deviceApplyDTO.getWorkerStatus() == 1) {
				hussarBpmDTO.setParticipantType("1");
				variable.put("returnType", 0);
				variable.put("orderUser", deviceApply.getSubmitUser());
			} else {
				variable.put("returnType", 1);
			}
			hussarBpmDTO.setVariable(variable);
			hussarBpmDTO.setTaskType("1");
			hussarBpmService.hussarSubmit(hussarBpmDTO);
		}
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 新增出库单详情信息
	 *
	 * @param user           用户信息
	 * @param deviceApply    设备申请单
	 * @param deviceOutbound 设备出库单
	 */
	private void batchInsertDeviceOutboundDetail(IdevelopUser user, DeviceApply deviceApply, DeviceOutbound deviceOutbound) {
		// 获取设备申请单详情信息
		List<DeviceApplyDetail> list = deviceApplyDetailService.list(new LambdaQueryWrapper<DeviceApplyDetail>().eq(DeviceApplyDetail::getApplyId, deviceApply.getId()));
		List<DeviceOutboundDetail> outboundDetailArrayList = Convert.convert(new TypeReference<List<DeviceOutboundDetail>>() {
		}, list);
		outboundDetailArrayList.forEach(item -> {
			item.setOutboundId(deviceOutbound.getId());
			item.setCreateUser(user.getUserId());
			item.setCreateTime(new Date());
			item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		});
		deviceOutboundDetailService.saveBatch(outboundDetailArrayList);
	}

	/**
	 * 获取设备申请工单状态字典
	 *
	 * @return List
	 */
	@Override
	public List<DictValueVO> deviceApplyDict() {
		List<DictValueVO> dictValueVOList = new ArrayList<>();
		DeviceApplyEnum[] values = DeviceApplyEnum.values();
		for (DeviceApplyEnum deviceApplyEnum : values) {
			dictValueVOList.add(DictValueVO.builder().node(deviceApplyEnum.getCode().toString()).nodeName(deviceApplyEnum.getMessage())
				.sort(deviceApplyEnum.getSort()).type(deviceApplyEnum.getType()).build());
		}
		return dictValueVOList.stream().sorted(Comparator.comparing(DictValueVO::getSort)).collect(Collectors.toList());
	}

	/**
	 * 根据设备id查询旧设备的使用类型
	 *
	 * @param deviceId 设备id
	 * @return DeviceOperationDetailVO 查询设备信息
	 */
	@Override
	public DeviceOperationDetailVO queryUserType(String deviceId) {
		DeviceOperationDetail deviceOperationDetail = deviceOperationDetailMapper.selectOne(new LambdaQueryWrapper<DeviceOperationDetail>()
			.eq(DeviceOperationDetail::getDeviceId, deviceId).orderByDesc(DeviceOperationDetail::getCreateTime)
			.last(" LIMIT 1"));
		return Convert.convert(DeviceOperationDetailVO.class, deviceOperationDetail);
	}

	/**
	 * 新增设备申请工单详情数据
	 *
	 * @param deviceApplyDTO 设备申请工单信息
	 * @param user           登录用户信息
	 */
	private void batchInsertDeviceApplyDetail(DeviceApplyDTO deviceApplyDTO, IdevelopUser user) {
		List<DeviceApplyDetailDTO> deviceApplyDetailDTOList = deviceApplyDTO.getDeviceApplyDetailDTOList();
		if (CollectionUtil.isNotEmpty(deviceApplyDetailDTOList)) {
			List<DeviceApplyDetail> deviceApplyDetailList = Convert.convert(new TypeReference<List<DeviceApplyDetail>>() {
			}, deviceApplyDetailDTOList);
			deviceApplyDetailList.forEach(item -> {
				item.setId(null);
				item.setApplyId(deviceApplyDTO.getId());
				item.setUserTime(new Date());
				item.setCreateTime(new Date());
				item.setCreateUser(user.getUserId());
				item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			});
			deviceApplyDetailService.saveBatch(deviceApplyDetailList);
		}
	}

	/**
	 * 删除设备申请单设备详情数据
	 *
	 * @param deviceApplyDTO 设备申请单信息
	 */
	private void deleteDeviceApplyDetail(DeviceApplyDTO deviceApplyDTO) {
		deviceApplyDetailService.remove(new LambdaQueryWrapper<DeviceApplyDetail>().eq(DeviceApplyDetail::getApplyId, deviceApplyDTO.getId()));
	}
}

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
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.protobuf.ServiceException;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.*;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.entity.ErpTransZcbf;
import com.lnsoft.device.api.erp.entity.ErpTransZcbfItem;
import com.lnsoft.device.api.erp.response.ErpTransZcbfResp;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.mapper.DeviceChangeListMapper;
import com.lnsoft.device.api.operation.mapper.DeviceChangeMapper;
import com.lnsoft.device.api.operation.mapper.DeviceRepairListMapper;
import com.lnsoft.device.api.operation.mapper.DeviceRepairMapper;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.DeviceScrapDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceScrapListDTO;
import com.lnsoft.device.api.warehouse.dto.ScrapErpDto;
import com.lnsoft.device.api.warehouse.entity.DeviceScrap;
import com.lnsoft.device.api.warehouse.entity.DeviceScrapList;
import com.lnsoft.device.api.warehouse.mapper.DeviceScrapListMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceScrapMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapListService;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapService;
import com.lnsoft.device.api.warehouse.vo.DeviceScrapListVO;
import com.lnsoft.device.api.warehouse.vo.DeviceScrapVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.constant.I6000Constant;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import com.lnsoft.device.entity.DeviceRepair;
import com.lnsoft.device.entity.DeviceRepairList;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.hussar.bpm.domain.dto.HussarBpmDto;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarComplateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarTaskVo;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.entity.UserInfo;
import com.lnsoft.system.user.feign.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备报废 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-18
 */
@Service
public class DeviceScrapServiceImpl extends BaseServiceImpl<DeviceScrapMapper, DeviceScrap> implements IDeviceScrapService {

	@Autowired
	private IDeviceScrapListService scrapListService;
	@Autowired
	private DeviceScrapListMapper scrapListMapper;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private ICmdbService iCmdbService;
	@Resource
	private IUserClient userClient;
	@Resource
	private IHussarBpmClient hussarBpmClient;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private IErpService erpService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private ThirdProperties thirdProperties;
	@Resource
	private II6000Service i6000Service;
	@Resource
	private DeviceChangeListMapper deviceChangeListMapper;
	@Resource
	private DeviceChangeMapper deviceChangeMapper;
	@Resource
	private DeviceRepairMapper deviceRepairMapper;
	@Resource
	private DeviceRepairListMapper deviceRepairListMapper;

	@Override
	public IPage<DeviceScrapVO> selectDeviceScrapPage(IPage<DeviceScrapVO> page, DeviceScrapVO deviceScrap) {
		return page.setRecords(baseMapper.selectDeviceScrapPage(page, deviceScrap));
	}

	/**
	 * 查看设备报废详情
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public R<DeviceScrapVO> detail(DeviceScrapDTO dto) {
		//获取用户信息
		IdevelopUser user = SecureUtil.getUser();
		if (Objects.isNull(user)) {
			return R.fail("用户信息异常！");
		}
		DeviceScrap deviceScrap = baseMapper.selectOne(Wrappers.<DeviceScrap>lambdaQuery().eq(DeviceScrap::getId, dto.getId()).eq(DeviceScrap::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (Objects.isNull(deviceScrap)) {
			return R.fail("设备报废不存在");
		}
		DeviceScrapVO vo = new DeviceScrapVO();
		BeanUtil.copyProperties(deviceScrap, vo);
		if (StringUtil.isNotBlank(vo.getAccessory())) {
			vo.setOrderFile(JSON.parseObject(vo.getAccessory(), DeviceOrderFileDTO.class));
		}
		List<DeviceScrapList> deviceScrapLists = scrapListMapper.selectList(Wrappers.<DeviceScrapList>lambdaQuery().eq(DeviceScrapList::getScrapId, dto.getId()).eq(DeviceScrapList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).orderByAsc(DeviceScrapList::getSort));
		if (CollectionUtil.isNotEmpty(deviceScrapLists)) {
			List<DeviceScrapListVO> scrapListVOS = deviceScrapLists.stream().map(item -> {
				DeviceScrapListVO listVO = new DeviceScrapListVO();
				BeanUtil.copyProperties(item, listVO);
				return listVO;
			}).collect(Collectors.toList());
			vo.setScrapListVOS(scrapListVOS);
		}
		return R.data(vo);
	}

	/**
	 * 设备报废列表
	 *
	 * @param dto
	 * @param query
	 * @return
	 */
	@Override
	public IPage<DeviceScrap> scrapList(DeviceScrapDTO dto, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		dto.setRegionCode(user.getRegionCode());

		return baseMapper.getPage(Condition.getPage(query), dto);
//		IPage<DeviceScrap> result = baseMapper.selectPage(Condition.getPage(query), Wrappers.<DeviceScrap>lambdaQuery().like(StringUtil.isNotBlank(dto.getFilingNo()), DeviceScrap::getFilingNo, dto.getFilingNo())
//			.eq(StringUtil.isNotBlank(dto.getProcessStatus()), DeviceScrap::getProcessStatus, dto.getProcessStatus()).like(StringUtil.isNotBlank(scrapTime), DeviceScrap::getScrapTime, scrapTime)
//			.eq(StringUtil.isNotBlank(dto.getApplyDept()), DeviceScrap::getApplyDept, dto.getApplyDept()).eq(DeviceScrap::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).likeRight(StringUtil.isNotBlank(dto.getApplyUserName()), DeviceScrap::getApplyUserName, dto.getApplyUserName())
//			.eq(StringUtil.isNotBlank(dto.getApplyUnit()), DeviceScrap::getApplyUnit, dto.getApplyUnit())
//			.likeRight(DeviceScrap::getRegionCode, user.getRegionCode()).orderByDesc(DeviceScrap::getCreateTime));
//		return result;
	}

	/**
	 * 保存设备报废信息
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceScrapVO> saveScrap(DeviceScrapDTO dto) {
		IdevelopUser user = SecureUtil.getUser();
		R<DeviceScrapVO> check = checkScrap(dto);
		R checks = check(dto);
		if (ResultCode.FAILURE.getCode() == checks.getCode()) {
			return checks;
		}
		if (check != null) {
			return check;
		}
		DeviceScrap scrap = BeanUtil.copy(dto, DeviceScrap.class);
		scrap.setRegionCode(user.getRegionCode());
		scrap.setProcessStatus("1");
		if (!Objects.isNull(scrap.getOrderFile())) {
			scrap.setAccessory(JSONUtil.toJsonStr(scrap.getOrderFile()));
		} else {
			scrap.setAccessory("");
		}
		if (Objects.isNull(scrap.getId())) {
			scrap.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.BF));
			if (Objects.isNull(scrap.getApplyUser())) {
				scrap.setApplyUser(user.getUserId());
				scrap.setApplyDept(user.getDeptId());
				scrap.setApplyUserName(user.getUserName());
				scrap.setApplyUnit(user.getCorpId());
			}
			if (StringUtil.isBlank(scrap.getScrapScale())) {
				scrap.setScrapScale("100%");
			}
			scrap.setCreateTime(new Date());
			scrap.setCreateUser(user.getUserId());
			scrap.setStatus(1);
			scrap.setErpStatus("0");
			scrap.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			baseMapper.insert(scrap);
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(scrap.getId()).logData(dto.toString()).params(dto.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title("新增暂存设备报废").build());
			dto.setFilingNo(scrap.getFilingNo());
		} else {
			scrap.setUpdateTime(new Date());
			scrap.setUpdateUser(user.getUserId());
			baseMapper.updateById(scrap);
			// 增加操作记录
			logOptService.commonLogOpt(LogOpt.builder().logId(scrap.getId()).logData(dto.toString()).params(dto.toString())
				.optRole("--").optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title("修改暂存设备报废").build());
		}
		dto.setId(scrap.getId());
		dto.setErpStatus(scrap.getErpStatus());
		dto.setI6000Status(scrap.getI6000Status());
		DeviceScrapVO vo = saveDeviceScrapList(dto, user);
		return R.data(vo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R removeDeviceScraps(String ids) {
		IdevelopUser user = SecureUtil.getUser();
		List<String> idList = Arrays.asList(ids.split(","));
		List<DeviceScrap> deviceScrapList = baseMapper.selectList(Wrappers.<DeviceScrap>lambdaQuery().in(DeviceScrap::getId, idList)
			.eq(DeviceScrap::getStatus, DeviceScrapEnum.TEMPORARILY.getCode())
			.eq(DeviceScrap::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (deviceScrapList.size() != idList.size()) {
			return R.fail("所选设备报废无法删除");
		}
		baseMapper.update(Wrappers.<DeviceScrap>lambdaUpdate().set(DeviceScrap::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceScrap::getUpdateTime, new Date()).set(DeviceScrap::getUpdateUser, user.getUserId())
			.in(DeviceScrap::getId, idList).eq(DeviceScrap::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		scrapListMapper.update(Wrappers.<DeviceScrapList>lambdaUpdate().set(DeviceScrapList::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceScrapList::getUpdateTime, new Date()).set(DeviceScrapList::getUpdateUser, user.getUserId())
			.in(DeviceScrapList::getScrapId, idList).eq(DeviceScrapList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		// 增加操作记录
		idList.forEach(item -> logOptService.commonLogOpt(LogOpt.builder().logId(item).logData(ids).params(ids).optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title("删除暂存设备报废").build()));
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 提交设备报废
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceScrapVO> deviceScrapSubmit(DeviceScrapDTO dto) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		R check = check(dto);
		if (ResultCode.FAILURE.getCode() == check.getCode()) {
			return check;
		}
		if (CollectionUtil.isEmpty(dto.getScrapListDTOS())) {
			return R.fail("请选择报废设备！");
		}
		R<DeviceScrapVO> saveResult = saveScrap(dto);
		if (saveResult.getCode() != 200) {
			return saveResult;
		}
		DeviceScrapVO vo = saveResult.getData();
		// 组装发起流程需要的参数
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", vo.getId());
		variable.put("orderNo", vo.getFilingNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());
		HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_SCRAP.getBpmMark())
			.businessKey(vo.getFilingNo()).variable(variable).build();
		HussarCreateVo hussarBpm;
		// 发起流程
		try {
			hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
		} catch (Exception e) {
			throw new Exception("创建流程发生异常");
		}

		dto.setTaskDefinitionKey(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode());
		dto.setWorkerStatus(0);
		dto.setComment("发起设备报废申请");
		dto.setFilingNo(vo.getFilingNo());
		deskDeviceScrapStatus(dto);
		// 记录流程id
		baseMapper.update(Wrappers.<DeviceScrap>lambdaUpdate().eq(DeviceScrap::getId, vo.getId())
			.set(DeviceScrap::getProcessInsId, hussarBpm.getProcessInsId()).set(DeviceScrap::getProcessStatus, DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_REVIEW.getNode()));
		return R.data(vo);
	}

	/**
	 * 获取工作台设备报废列表
	 *
	 * @param dto
	 * @param query
	 * @return
	 */
	@Override
	public R<IPage<DeviceScrapVO>> deskDeviceScrapList(DeviceScrapDTO dto, Query query) {
		if (StringUtil.isBlank(dto.getOrderNoList())) {
			return R.data(new Page<>());
		}
		dto.setFilingNoList(Arrays.asList(dto.getOrderNoList().split(",")));
		return R.data(baseMapper.deskDeviceScrapList(dto, Condition.getPage(query)));
	}

	/**
	 * 更新设备报废流程状态
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deskDeviceScrapStatus(DeviceScrapDTO dto) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		DeviceScrap scrap = getOne(Wrappers.<DeviceScrap>lambdaQuery().eq(!Objects.isNull(dto.getId()), DeviceScrap::getId, dto.getId()).eq(DeviceScrap::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		dto.setFilingNo(scrap.getFilingNo());
		if (StringUtil.isBlank(dto.getTaskDefinitionKey())) {
			dto.setTaskDefinitionKey(scrap.getProcessStatus());
		}
		if (!thirdProperties.getApiErp()) {
			if (DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode().equals(dto.getTaskDefinitionKey()) && 0 == dto.getWorkerStatus()) {
				ScrapErpDto scrapErpDto = new ScrapErpDto();
				scrapErpDto.setId(dto.getId());
				scrapErpDto.setFilingNo(scrap.getFilingNo());
				scrapErpDto.setStatus(dto.getWorkerStatus().toString());
				scrapErpDto.setScrapCode("通过");
				return deviceScrapErpReturn(scrapErpDto);
			}
		}
		String scrapNode = null;
		String optTitle = null;
		if (StringUtil.isBlank(dto.getTaskDefinitionKey())) {
			dto.setTaskDefinitionKey(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode());
		}
		dto.setProcessStatus(dto.getTaskDefinitionKey());
		if (DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode().equals(dto.getTaskDefinitionKey()) && dto.getWorkerStatus() == 0) {
			scrapNode = DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_REVIEW.getNode();
			dto.setComment(dto.getComment());
		}
		if (DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_REVIEW.getNode().equals(dto.getTaskDefinitionKey())) {
			if (dto.getWorkerStatus() == 1) {
				scrapNode = DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode();
//				dto.setComment("发起设备报废申请");
				optTitle = "发起设备报废申请";
			} else {
				scrapNode = DeviceScrapBpmNodeEnum.DEVICE_SCRAP_DIGITIZATION_DIVISION.getNode();
				optTitle = dto.getWorkerStatus() == 0 ? "运行专工同意" : "运行专工驳回";
				LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
				updateWrapper.set(ApproveRecord::getOptOpinion,"待审批").set(ApproveRecord::getOptTitle,"待审批").eq(ApproveRecord::getFilingNo,dto.getId()).eq(ApproveRecord::getNodeId,DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode());
				approveRecordService.update(updateWrapper);
			}
		}
		if (DeviceScrapBpmNodeEnum.DEVICE_SCRAP_DIGITIZATION_DIVISION.getNode().equals(dto.getTaskDefinitionKey())) {
			if (dto.getWorkerStatus() == 1) {
				scrapNode = DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode();
//				dto.setComment("发起设备报废申请");
				optTitle = "发起设备报废申请";
			} else {
				scrapNode = DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode();
				optTitle = dto.getWorkerStatus() == 0 ? "数字化部主任同意" : "数字化部主任驳回";
			}
		}
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", scrap.getId());
		variable.put("orderNo", scrap.getFilingNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());

		// 记录审核流程
		if (StringUtil.isBlank(dto.getExamineRole())) {
			//  获取当前节点操作角色
			List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(scrap.getFilingNo());
			String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
			dto.setExamineRole(roleName);
		}
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString())
			.optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title(dto.getComment()).optRole(dto.getExamineRole()).time(new Date()).build());
		approveRecordService.commonRecord(ApproveRecord.builder().filingNo(dto.getId()).optType(OptTypeEnum.DEVICE_SCRAP.getCode()).nodeId(dto.getTaskDefinitionKey())
			.optRole(dto.getExamineRole()).nodeName(DeviceScrapBpmNodeEnum.getMessage(dto.getTaskDefinitionKey()))
			.optTitle(optTitle).optOpinion(dto.getComment())
			.approveStatus(dto.getWorkerStatus()).filingCode(dto.getFilingNo())
			.build());
		// 更新工单信息
		baseMapper.update(Wrappers.<DeviceScrap>lambdaUpdate().eq(DeviceScrap::getId, dto.getId())
			.set(DeviceScrap::getUpdateTime, new Date())
			.set(DeviceScrap::getProcessStatus, scrapNode)
			.set(DeviceScrap::getStatus, DeviceScrapEnum.SCRAP_APPROVAL.getCode())
			.set(DeviceScrap::getErpStatus, "2"));
		if (DeviceScrapBpmNodeEnum.DEVICE_SCRAP_DIGITIZATION_DIVISION.getNode().equals(dto.getTaskDefinitionKey()) && 0 == dto.getWorkerStatus() && thirdProperties.getApiErp()) {
			// 同步ERP
			ErpTransZcbfResp result;
			try {
				dto.setScrapReason(scrap.getScrapReason());
				dto.setUseKeepDept(scrap.getUseKeepDept());
				dto.setSalvageAssets(scrap.getSalvageAssets());
				dto.setSqr(scrap.getSqr());
				result = syncErpScrap(dto);
				if (Objects.isNull(result) || Objects.equals(String.valueOf(ResultCode.SUCCESS.getCode()), result.getCode())) {
					throw new Exception();
				}
			} catch (Exception e) {
				throw new ServiceException("报废信息同步ERP失败: " + e.getMessage());
			}
		}
		if (dto.getWorkerStatus() == 1) {
			//驳回
			try {
				List<HussarTaskVo> hussarTaskVos = hussarBpmService.queryTaskId(scrap.getFilingNo());
				HussarBpmDto hussarBpmDto = new HussarBpmDto();
				hussarBpmDto.setTaskId(hussarTaskVos.get(0).getTaskId());
				hussarBpmDto.setUserId(user.getUserId().toString());
				hussarBpmDto.setComment(StringUtil.isNotBlank(dto.getComment()) ? dto.getComment() : "驳回");
				hussarBpmDto.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_SCRAP.getBpmMark());
				hussarBpmDto.setRejectNode(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode());
				hussarBpmDto.setBusinessKey(scrap.getFilingNo());
				hussarBpmDto.setVariable(variable);
				com.lnsoft.hussar.bpm.tool.api.R<JSONArray> hussarR = hussarBpmClient.anyNodeReject(hussarBpmDto);
				if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
					throw new Exception("驳回流程异常!");
				}
			} catch (Exception e) {
				throw new Exception("驳回流程异常!");
			}
		} else {
			//同意
			HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
			hussarBpmDTO.setBusinessKey(scrap.getFilingNo());
			hussarBpmDTO.setComment(dto.getComment());
			hussarBpmDTO.setVariable(variable);
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_SCRAP.getBpmMark());
			hussarBpmDTO.setTaskDefinitionKey(dto.getTaskDefinitionKey());
			hussarBpmDTO.setTaskType("1");
			try {
				R<List<HussarComplateVo>> hussarR = hussarBpmService.hussarSubmit(hussarBpmDTO);
				if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
					throw new Exception("提交流程异常!");
				}
			} catch (Exception e) {
				throw new Exception("提交流程异常!");
			}
		}
		return R.success(ResultCode.SUCCESS);
	}

	private ErpTransZcbfResp syncErpScrap(DeviceScrapDTO dto) {
		//获取数据
		Map<Object, Object> scrapCauseMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getScrapCause());
		// ErpPersonAuth erpPersonAuth = new ErpPersonAuth();
		// erpPersonAuth.setKostl(dto.getUseKeepDept());
		// ErpPersonAuthResp erpPersonAuthResp = erpService.personAuth(erpPersonAuth);
		ErpTransZcbf erpTransZcbf = new ErpTransZcbf();
		erpTransZcbf.setXtdocId(dto.getId());
		erpTransZcbf.setXtdocNo(dto.getFilingNo());
		//处理封装
		List<DeviceScrapList> scrapLists = scrapListMapper.selectList(Wrappers.<DeviceScrapList>lambdaQuery().eq(DeviceScrapList::getScrapId, dto.getId()).eq(DeviceScrapList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (CollectionUtil.isEmpty(scrapLists)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String format = dateFormat.format(new Date());
		List<ErpTransZcbfItem> erpList = scrapLists.stream().map(item -> {
			ErpTransZcbfItem erpTransZcbfItem = new ErpTransZcbfItem();
			erpTransZcbfItem.setXtbm(item.getUuid());
			erpTransZcbfItem.setXh(item.getSort());
			erpTransZcbfItem.setSqsj(format);
			erpTransZcbfItem.setSqr(dto.getSqr());
			erpTransZcbfItem.setEqunr(item.getDeviceCodeErp());
			erpTransZcbfItem.setBfbl(100.00);
			erpTransZcbfItem.setSqdms("");
			erpTransZcbfItem.setBfyy((String) scrapCauseMapErp.get(dto.getScrapReason()));
			erpTransZcbfItem.setXmbm("");
			erpTransZcbfItem.setXmmc("");
			erpTransZcbfItem.setSqbm(dto.getUseKeepDept());
			erpTransZcbfItem.setClgx(dto.getSalvageAssets());
			erpTransZcbfItem.setZsbzt("E0009");
			return erpTransZcbfItem;
		}).collect(Collectors.toList());
		erpTransZcbf.setErpTransZcbfItemList(erpList);
		ErpTransZcbfResp erpTransZcbfResp = erpService.transZcbf(erpTransZcbf);
		return erpTransZcbfResp;
	}

	/**
	 * 设备报废erp回调
	 *
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R deviceScrapErpReturn(ScrapErpDto dto) throws Exception {
		String scrapId = dto.getId();
		DeviceScrap scrap = getOne(Wrappers.<DeviceScrap>lambdaQuery().eq(DeviceScrap::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(DeviceScrap::getId, scrapId));
		if (Objects.isNull(scrap)) {
			return R.fail("报废单不存在！");
		}
		IdevelopUser idevelopUser = SecureUtil.getUser();
		User user = new User();
		if (!idevelopUser.getUserName().contains("管理员")) {
			R<UserInfo> userByRole = userClient.getUserByRole(Constants.ERP_ROLE, idevelopUser.getUserId());
			if (ResultCode.SUCCESS.getCode() == userByRole.getCode() && !Objects.isNull(userByRole.getData())) {
				user = userByRole.getData().getUser();
			} else {
				return R.fail("未获取到审核角色，请联系运维人员");
			}
		} else {
			user.setId(idevelopUser.getUserId());
		}
		try {
			if (Objects.equals("0", dto.getStatus())) {
				//通过
				List<DeviceScrap> updateList = new ArrayList<>();
				//同意
				HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
				hussarBpmDTO.setBusinessKey(dto.getFilingNo());
				hussarBpmDTO.setComment(dto.getScrapCode());
				hussarBpmDTO.setParticipantType("2");
				hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_SCRAP.getBpmMark());
				hussarBpmDTO.setTaskDefinitionKey(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode());
				hussarBpmDTO.setTaskType("1");
				try {
					R<List<HussarComplateVo>> hussarR = hussarBpmService.hussarSubmit(hussarBpmDTO);
					if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
						throw new Exception("提交流程异常!");
					}
				} catch (Exception e) {
					throw new Exception("提交流程异常!");
				}
//				hussarBpmService.endProcess(dto.getFilingNo(), user.getId().toString());
				// 增加操作记录
				logOptService.commonLogOpt(LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString())
					.optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title(dto.getScrapCode()).optRole(Constants.ERP_ROLE).time(new Date()).build());
				// 记录审核流程
				approveRecordService.commonRecord(ApproveRecord.builder().filingNo(dto.getId()).optType(OptTypeEnum.DEVICE_SCRAP.getCode())
					.nodeId(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode()).optRole(Constants.ERP_ROLE)
					.nodeName(DeviceTransferBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode()))
					.approveStatus(Integer.valueOf(dto.getStatus())).filingCode(dto.getFilingNo())
					.optTitle("ERP审批通过").optOpinion(dto.getScrapCode()).build());
				DeviceScrap deviceScrap = new DeviceScrap();
				deviceScrap.setId(dto.getId());
				deviceScrap.setErpScrapCode(dto.getScrapCode());
				deviceScrap.setErpStatus("2");
				deviceScrap.setStatus(3);
				deviceScrap.setProcessStatus(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode());
				deviceScrap.setUpdateTime(new Date());
				deviceScrap.setUpdateUser(user.getId());
				updateList.add(deviceScrap);
				// 增加操作记录
				LogOpt build = LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString()).optRole("--")
					.optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode())).optName("系统").build();
				build.setStatus(0);
				logOptService.commonLogOpt(build);
				// 记录审核流程
				ApproveRecord approveRecord = ApproveRecord.builder().filingNo(dto.getId())
					.optType(OptTypeEnum.DEVICE_SCRAP.getCode()).nodeId(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode())
					.nodeName(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode()))
					.optTitle(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode()))
					.optOpinion(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode()))
					.approveStatus(Integer.valueOf(dto.getStatus())).filingCode(dto.getFilingNo())
					.optName("系统").optRole("--").build();
				approveRecord.setStatus(1);
				approveRecordService.commonRecord(approveRecord);
				List<DeviceScrapList> deviceScrapLists = scrapListMapper.selectList(Wrappers.<DeviceScrapList>lambdaQuery().eq(DeviceScrapList::getScrapId, dto.getId()).eq(DeviceScrapList::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
				// 更新设备报废详情信息
				// 更新cmdb设备资产台账信息
				List<DeviceScrapListDTO> scrapListDTOS = Convert.convert(new TypeReference<List<DeviceScrapListDTO>>() {
				}, deviceScrapLists);
				if (CollectionUtil.isNotEmpty(scrapListDTOS)) {
					updateCmdbEntity(scrapListDTOS);
					updateI6000Entity(scrapListDTOS);
				}
				updateBatchById(updateList);
			} else {
				//终止
				hussarBpmService.endProcess(dto.getFilingNo(), user.getId().toString());
				// 增加操作记录
				logOptService.commonLogOpt(LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString())
					.optType(OptTypeEnum.DEVICE_SCRAP.getCode()).title(dto.getScrapCode()).optRole(Constants.ERP_ROLE).time(new Date()).build());
				// 记录审核流程
				approveRecordService.commonRecord(ApproveRecord.builder().filingNo(dto.getId()).optType(OptTypeEnum.DEVICE_SCRAP.getCode())
					.nodeId(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode())
					.optRole(Constants.ERP_ROLE)
					.nodeName(DeviceTransferBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_PROFESSIONAL_SERVICE.getNode()))
					.approveStatus(Integer.valueOf(dto.getStatus())).filingCode(dto.getFilingNo())
					.optTitle("ERP审批终结").optOpinion(dto.getScrapCode()).build());

				ApproveRecord approveRecord = ApproveRecord.builder().filingNo(dto.getId())
					.optType(OptTypeEnum.DEVICE_SCRAP.getCode()).nodeId(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode())
					.nodeName(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode()))
					.optTitle(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode()))
					.optOpinion(DeviceScrapBpmNodeEnum.getMessage(DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode()))
					.approveStatus(Integer.valueOf(dto.getStatus())).filingCode(dto.getFilingNo())
					.optName("系统").optRole("--").build();
				approveRecord.setStatus(1);
				approveRecordService.commonRecord(approveRecord);
				update(Wrappers.<DeviceScrap>lambdaUpdate()
					.set(DeviceScrap::getProcessStatus, DeviceScrapBpmNodeEnum.DEVICE_SCRAP_FINISH.getNode())
					.set(DeviceScrap::getStatus, 4)
					.set(DeviceScrap::getErpStatus, "3")
					.set(DeviceScrap::getUpdateTime, new Date())
					.eq(DeviceScrap::getId, scrapId));
			}
		} catch (Exception e) {
			throw new ServiceException("修改流程状态异常！", e);
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R check(DeviceScrapDTO dto) {
		List<DeviceScrapListDTO> deviceScrapListDTOList = dto.getScrapListDTOS();
		for (DeviceScrapListDTO deviceScrapListDTO : deviceScrapListDTOList) {
			String deviceCode = deviceScrapListDTO.getDeviceCode();
			//报废校验
			List<DeviceScrapList> scrapDeviceList = scrapListService.getByDeviceCode(deviceCode);
			if (ObjectUtil.isNotEmpty(scrapDeviceList)) {
				for (DeviceScrapList deviceScrapList : scrapDeviceList) {
					String scrapId = deviceScrapList.getScrapId();
					DeviceScrap deviceScrap = this.getById(scrapId);
					if (ObjectUtil.isEmpty(deviceScrap)) {
						continue;
					} else {
						Integer status = deviceScrap.getStatus();
						if (!DeviceScrapEnum.FINISH.getCode().equals(status) && StringUtil.isNotBlank(deviceScrap.getProcessInsId())
							&& !DeviceScrapBpmNodeEnum.DEVICE_SCRAP_APPLY.getNode().equals(deviceScrap.getProcessStatus())) {
							return R.fail("设备：" + deviceScrapList.getDeviceCode() + "正在已经发起报废，请不要重复发起");
						}
					}
				}
			}
			//变更校验
			List<DeviceChangeList> changeDeviceList = deviceChangeListMapper.getByDeviceCode(deviceCode);
			if (ObjectUtil.isNotEmpty(changeDeviceList)) {
				for (DeviceChangeList deviceChangeList : changeDeviceList) {
					String changeId = deviceChangeList.getChangeId();
					DeviceChange deviceChange = deviceChangeMapper.selectById(changeId);
					if (ObjectUtil.isEmpty(deviceChange)) {
						continue;
					} else {
						if (!DeviceChangeEnum.FINISH.getCode().equals(deviceChange.getTicketStatus())) {
							return R.fail("设备：" + deviceChangeList.getDeviceCode() + "正在变更中，无法发起报废");
						}
					}
				}
			}
			//报修校验
			List<DeviceRepairList> repairDeviceList = deviceRepairListMapper.getByDeviceCode(deviceCode);
			if (ObjectUtil.isNotEmpty(repairDeviceList)) {
				for (DeviceRepairList deviceRepairList : repairDeviceList) {
					String repairId = deviceRepairList.getRepairId();
					DeviceRepair deviceRepair = deviceRepairMapper.selectById(repairId);
					if (ObjectUtil.isEmpty(deviceRepair)) {
						continue;
					} else {
						Integer ticketStatus = deviceRepair.getTicketStatus();
						if (!DeviceRepairEnum.FINISH.getCode().equals(ticketStatus)) {
							return R.fail("设备：" + deviceRepairList.getDeviceCode() + "正在报修中，无法发起报废");
						}
					}
				}
			}
		}

		return R.success("操作成功");
	}

	/**
	 * 设备报废设备详情处理
	 *
	 * @param dto
	 * @param user
	 * @return
	 */
	private DeviceScrapVO saveDeviceScrapList(DeviceScrapDTO dto, IdevelopUser user) {
		DeviceScrapVO vo;
		List<DeviceScrapListDTO> scrapListDTOS = dto.getScrapListDTOS();
		vo = Convert.convert(DeviceScrapVO.class, dto);
		//解除原绑定设备信息
		scrapListService.update(Wrappers.<DeviceScrapList>lambdaUpdate().set(DeviceScrapList::getIsDeleted, IdevelopConstant.DB_IS_DELETED).eq(DeviceScrapList::getScrapId, dto.getId()));
		if (CollectionUtil.isNotEmpty(scrapListDTOS)) {
			//添加设备详情信息
			final int[] i = {0};
			List<DeviceScrapList> scrapLists = scrapListDTOS.stream().map(item -> {
				DeviceScrapList scrapList = new DeviceScrapList();
				BeanUtil.copyProperties(item, scrapList);
				if (Objects.isNull(scrapList.getDeviceId()) || scrapList.getDeviceId() == 0 || scrapList.getDeviceId() == -1) {
					scrapList.setDeviceId(Long.valueOf(scrapList.getId()));
				}
				scrapList.setId(null);
				scrapList.setScrapId(dto.getId());
				scrapList.setCreateTime(new Date());
				scrapList.setCreateUser(user.getUserId());
				scrapList.setCreateDept(user.getDeptId());
				scrapList.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				scrapList.setCmdbDevice(JSONUtil.toJsonStr(item.getEntity()));
				scrapList.setSort(String.valueOf(i[0]));
				i[0]++;
				return scrapList;
			}).collect(Collectors.toList());
			scrapListService.saveBatch(scrapLists);
			vo.setScrapListVOS(Convert.convert(new TypeReference<List<DeviceScrapListVO>>() {
			}, scrapListDTOS));
		}
		return vo;
	}

	/**
	 * 参数校验
	 *
	 * @param dto
	 * @return
	 */
	private R<DeviceScrapVO> checkScrap(DeviceScrapDTO dto) {
		/*if (Objects.isNull(dto.getOriginalValue()) || Objects.isNull(dto.getDepreciation()) || Objects.isNull(dto.getNetWorth())) {
			return R.fail("报废资产信息异常!");
		}*/
		if (StringUtil.isBlank(dto.getApplyPhone())) {
			return R.fail("请输入有效的联系方式!");
		}
		return null;
	}

	/**
	 * 保存 cmdb的接口
	 *
	 * @param deviceList
	 * @return
	 */
	public boolean updateCmdbEntity(List<DeviceScrapListDTO> deviceList) {
		if (CollectionUtil.isEmpty(deviceList)) {
			return false;
		}
		Map<Long, Map<String, Object>> hashMap = new HashMap<>();
		// 修改 资产台账
		deviceList.forEach(item -> {
			//获取模型 cid
			Long ciId = item.getCiId();
			Map<String, Object> entity = item.getEntity();
			if (Objects.isNull(entity)) {
				entity = new HashMap<>();
			}
			entity.put(CmdbAttrConstant.ID, item.getDeviceId());
			entity.put(CmdbAttrConstant.UUID, item.getUuid());
			entity.put(CmdbAttrConstant.CI_ID, ciId);
			entity.put(CmdbAttrConstant.DEVICE_NAME, item.getDeviceName());
			entity.put(CmdbAttrConstant.DEVICE_TYPE, item.getDeviceType());
			entity.put(CmdbAttrConstant.DEVICE_CODE, item.getDeviceCode());
			entity.put(CmdbAttrConstant.DEVICE_STATUS_CODE, cmdbCientityProperties.getScarpStatus());
			entity.put(CmdbAttrConstant.DEVICE_STATUS, DeviceConstant.SCARP_STATUS);
			hashMap.put(Long.parseLong(entity.get(CmdbAttrConstant.ID).toString()), entity);
		});
		//保存cmdb
		iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
		return true;
	}

	/**
	 * 同步i6000
	 *
	 * @param deviceList
	 */
	private boolean updateI6000Entity(List<DeviceScrapListDTO> deviceList) {
		if (CollectionUtil.isEmpty(deviceList)) {
			return false;
		}
		Map<String, Map<String, Object>> hashMap = new HashMap<>();
		Map<String, String> deviceTypeDict = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
		// 修改 资产台账
		deviceList.forEach(item -> {
			//获取模型 cid
			Long ciId = item.getCiId();
			Map<String, Object> ent = item.getEntity();
			if (Objects.isNull(ent)) {
				ent = new HashMap<>();
			}
			ent.put(CmdbAttrConstant.ID, item.getDeviceId());
			ent.put(CmdbAttrConstant.UUID, item.getUuid());
			ent.put(CmdbAttrConstant.CI_ID, ciId);
			ent.put(CmdbAttrConstant.DEVICE_NAME, item.getDeviceName());
			ent.put(CmdbAttrConstant.DEVICE_TYPE, item.getDeviceType());
			ent.put(CmdbAttrConstant.DEVICE_CODE, item.getDeviceCode());
			ent.put(CmdbAttrConstant.DEVICE_STATUS_CODE, cmdbCientityProperties.getScarpStatus());
			if (StringUtil.isNotBlank(deviceTypeDict.get(item.getDeviceType()))) {
				ent.put(I6000Constant.CITYPE_ID, deviceTypeDict.get(item.getDeviceType()));
				hashMap.put(ent.get(CmdbAttrConstant.UUID).toString(), ent);
			}
		});

		//同步i6000
		if (CollectionUtil.isNotEmpty(hashMap)) {
			i6000Service.i6000Batchupdate(hashMap);
		}
		return true;
	}

}

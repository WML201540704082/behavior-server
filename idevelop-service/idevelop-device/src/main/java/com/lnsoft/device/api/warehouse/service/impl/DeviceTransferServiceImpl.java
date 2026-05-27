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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.protobuf.ServiceException;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
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
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.controller.ErpTranstplnrController;
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransEqunrItem;
import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.service.IErpKostlService;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDetailDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceDetailDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceOrderDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceTransfer;
import com.lnsoft.device.api.warehouse.entity.DeviceTransferDetail;
import com.lnsoft.common.enums.hussar.DeviceTransferBpmNodeEnum;
import com.lnsoft.common.enums.hussar.DeviceTransferEnum;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.device.api.warehouse.mapper.DeviceTransferMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferDetailService;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferService;
import com.lnsoft.device.api.warehouse.vo.DeviceTransferDetailVO;
import com.lnsoft.device.api.warehouse.vo.DeviceTransferVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.eums.ErpOperationEnum;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.hussar.bpm.domain.dto.HussarBpmDto;
import com.lnsoft.hussar.bpm.domain.dto.HussarCompleteParam;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarComplateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarTaskVo;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import com.lnsoft.system.entity.Role;
import com.lnsoft.system.feign.ISysClient;
import com.lnsoft.system.user.entity.User;
import com.lnsoft.system.user.feign.IUserClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备转资 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@Service
public class DeviceTransferServiceImpl extends BaseServiceImpl<DeviceTransferMapper, DeviceTransfer> implements IDeviceTransferService {

	@Resource
	private IProjectManagerDetailService projectManagerDetailService;
	@Resource
	private IDeviceTransferDetailService deviceTransferDetailService;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private ILogOptService logOptService;
	@Resource
	private IApproveRecordService approveRecordService;
	@Resource
	private ICmdbService iCmdbService;
	@Resource
	private IHussarBpmService hussarBpmService;
	@Resource
	private IHussarBpmClient hussarBpmClient;
	@Resource
	private IUserClient userClient;
	@Resource
	private ISysClient sysClient;
	@Resource
	private IErpService erpService;
	@Resource
	private II6000Service ii6000Service;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private ThirdProperties thirdProperties;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private ErpTranstplnrController erpTranstplnrController;
	@Resource
	private ICmdbClient cmdbClient;
	@Resource
	private IErpKostlService erpKostlService;

	@Value(value = "${third.api-erp}")
	private boolean erpPush;
	@Value(value = "${device.record.boolean}")
	private boolean recordBoolean;

	/*@Override
	public IPage<DeviceTransferVO> selectDeviceTransferPage(IPage<DeviceTransferVO> page, DeviceTransferVO deviceTransfer) {
		return page.setRecords(baseMapper.selectDeviceTransferPage(page, deviceTransfer));
	}*/

	/**
	 * 新增或修改 设备转资暂存
	 *
	 * @param deviceTransferDTO 转资信息
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceTransferVO> temporarily(DeviceTransferDTO deviceTransferDTO) throws ServiceException {

		IdevelopUser user = SecureUtil.getUser();
		DeviceTransfer transfer = Convert.convert(DeviceTransfer.class, deviceTransferDTO);
		DeviceTransferVO deviceTransferVO;
		/*if (StringUtil.isBlank(deviceTransferDTO.getUseKeepDept()) && StringUtil.isNotBlank(deviceTransferDTO.getMaintenanceFactory())) {
			List<Map<String, String>> kostl = erpService.getKostl(deviceTransferDTO.getMaintenanceFactory());
		}*/
		// 判断是首次暂存还是二次修改
		if (Objects.isNull(deviceTransferDTO.getId())) {
			// 部分字段赋值
			transfer.setErpStatus("0");
			transfer.setStatus(1);
			transfer.setCreateTime(new Date());
			transfer.setCreateUser(user.getUserId());
			transfer.setCreateDept(Long.parseLong(user.getDeptId()));
			transfer.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			transfer.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.ZZ));
			transfer.setRegionCode(user.getRegionCode());
			// 暂存设备转资信息
			baseMapper.insert(transfer);
			// 增加操作记录
			LogOpt logOpt = LogOpt.builder().logId(transfer.getId()).logData(deviceTransferDTO.toString()).params(deviceTransferDTO.toString())
				.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).optRole("--").optName("--").title("新增暂存设备转资").build();
			logOptService.commonLogOpt(logOpt);
		} else {
			// 判断是否还可以进行修改操作
			DeviceTransfer deviceTransfer = baseMapper.selectOne(new LambdaQueryWrapper<DeviceTransfer>().eq(DeviceTransfer::getId, deviceTransferDTO.getId())
				.eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (Objects.isNull(deviceTransfer) || !DeviceTransferEnum.TEMPORARILY.getCode().equals(deviceTransfer.getStatus())) {
				return R.fail("当前设备转资无法进行暂存");
			}
			transfer.setUpdateTime(new Date());
			transfer.setUpdateUser(user.getUserId());
			baseMapper.updateById(transfer);
			batchDelDeviceTransferDetail(transfer);
			// 增加操作记录
			LogOpt logOpt = LogOpt.builder().logId(transfer.getId()).logData(deviceTransferDTO.toString()).params(deviceTransferDTO.toString())
				.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).optRole("--").optName("--").title("修改暂存设备转资").build();
			logOptService.commonLogOpt(logOpt);
		}
		deviceTransferDTO.setId(transfer.getId());
		deviceTransferDTO.setErpStatus(transfer.getErpStatus());
		deviceTransferDTO.setI6000Status(transfer.getI6000Status());
		deviceTransferVO = deviceTransferDetailHandle(deviceTransferDTO, user);
		return R.data(deviceTransferVO);
	}

	/**
	 * 发起设备转资
	 *
	 * @param deviceTransferDTO 设备转资
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<DeviceTransferVO> insert(DeviceTransferDTO deviceTransferDTO) throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		if (CollectionUtils.isEmpty(deviceTransferDTO.getDeviceTransferDetailDTOList())) {
			return R.fail("请选择要转资的设备");
		}
		String deviceTypeCode = deviceTransferDTO.getDeviceTransferDetailDTOList().get(0).getDeviceTypeCode();
		String deviceType = deviceTransferDTO.getDeviceTransferDetailDTOList().get(0).getDeviceType();
		String code = StringUtils.isNotEmpty(deviceTypeCode) ? deviceTypeCode : deviceType;
		if (!deviceTransferDTO.getDeviceType().equals(code)) {
			return R.fail("设备类型不一致");
		}
		DeviceTransfer transfer = Convert.convert(DeviceTransfer.class, deviceTransferDTO);
		transfer.setReceiver(user.getUserId().toString());
		transfer.setReceiverTime(new Date());
		if (Objects.isNull(deviceTransferDTO.getId())) {
			transfer.setErpStatus("1");
			transfer.setStatus(2);
			transfer.setCreateTime(new Date());
			transfer.setCreateUser(user.getUserId());
			transfer.setCreateDept(Long.parseLong(user.getDeptId()));
			transfer.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			transfer.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.ZZ));
			transfer.setRegionCode(user.getRegionCode());
			baseMapper.insert(transfer);
		} else {
			// 判断是否还可以进行设备转资
			DeviceTransfer deviceTransfer = baseMapper.selectOne(new LambdaQueryWrapper<DeviceTransfer>().eq(DeviceTransfer::getId, deviceTransferDTO.getId())
				.eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			if (Objects.isNull(deviceTransfer) || !DeviceTransferEnum.TEMPORARILY.getCode().equals(deviceTransfer.getStatus())) {
				return R.fail("无法进行设备转资");
			}
			if (StringUtil.isNotBlank(deviceTransfer.getFilingNo())) {
				transfer.setFilingNo(deviceTransfer.getFilingNo());
			}
			transfer.setErpStatus("1");
			transfer.setStatus(2);
			transfer.setUpdateTime(new Date());
			transfer.setUpdateUser(user.getUserId());
			baseMapper.updateById(transfer);
			batchDelDeviceTransferDetail(transfer);
		}
		deviceTransferDTO.setId(transfer.getId());
		deviceTransferDTO.setErpStatus(transfer.getErpStatus());
		deviceTransferDTO.setI6000Status(transfer.getI6000Status());
		DeviceTransferVO deviceTransferVO = deviceTransferDetailHandle(deviceTransferDTO, user);
		// 组装发起流程需要的参数
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", transfer.getId());
		variable.put("orderNo", transfer.getFilingNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());
		HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark())
			.businessKey(transfer.getFilingNo()).variable(variable).build();
		// 发起流程
		HussarCreateVo hussarBpm = null;
		boolean flag = true;
		if (StringUtil.isNotBlank(deviceTransferDTO.getProcessInsId()) && StringUtil.isNotBlank(deviceTransferDTO.getFilingNo())) {
			List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(deviceTransferDTO.getFilingNo());
			if (CollectionUtil.isNotEmpty(hussarAssignVos)) {
				flag = false;
			}
		}
		try {
			if (flag) {
				hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
			}
		} catch (Exception e) {
			throw new Exception("创建流程发生异常");
		}
		//  获取当前节点操作角色
		List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(transfer.getFilingNo());
		String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));

		//发起后提交一次进入专工节点
		deviceTransferDTO.setExamineRole(roleName);
		deviceTransferDTO.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode());
		deviceTransferDTO.setComment("发起设备转资申请");
		deviceTransferDTO.setWorkerStatus(0);
		deviceTransferDTO.setFilingNo(transfer.getFilingNo());
		// 处理流程
		deskDeviceTransferStatus(deviceTransferDTO);
		// 记录流程id
		baseMapper.update(Wrappers.<DeviceTransfer>lambdaUpdate().eq(DeviceTransfer::getId, transfer.getId())
			.set(DeviceTransfer::getProcessInsId, Objects.isNull(hussarBpm) ? deviceTransferDTO.getProcessInsId() : hussarBpm.getProcessInsId()).set(DeviceTransfer::getProcessStatus, DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_REVIEW.getNode()));
		return R.data(deviceTransferVO);
	}

	/**
	 * 删除 设备转资
	 *
	 * @param ids 设备转资id
	 * @return R
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deleteDeviceTransfer(String ids) {
		IdevelopUser user = SecureUtil.getUser();
		List<String> idList = Arrays.asList(ids.split(","));
		List<DeviceTransfer> deviceTransfers = baseMapper.selectList(new LambdaQueryWrapper<DeviceTransfer>().in(DeviceTransfer::getId, idList)
			.eq(DeviceTransfer::getStatus, DeviceTransferEnum.TEMPORARILY.getCode())
			.eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (deviceTransfers.size() != idList.size()) {
			return R.fail("所选设备转资无法删除");
		}
		baseMapper.update(new LambdaUpdateWrapper<DeviceTransfer>().in(DeviceTransfer::getId, idList).eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
			.set(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceTransfer::getUpdateTime, new Date()).set(DeviceTransfer::getUpdateUser, user.getUserId()));
		deviceTransferDetailService.remove(new LambdaQueryWrapper<DeviceTransferDetail>().in(DeviceTransferDetail::getTransferId, idList));
		// 增加操作记录
		idList.forEach(item -> logOptService.commonLogOpt(LogOpt.builder().logId(item).logData(ids).params(ids).optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).title("删除暂存设备转资").build()));
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 详情
	 *
	 * @param deviceTransferDTO 查询信息
	 * @return R
	 */
	@Override
	public R<DeviceTransferVO> detail(DeviceTransferDTO deviceTransferDTO) {
		DeviceTransfer deviceTransfer = baseMapper.selectOne(new LambdaQueryWrapper<DeviceTransfer>().eq(DeviceTransfer::getId, deviceTransferDTO.getId()).eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (Objects.isNull(deviceTransfer)) {
			return R.fail("设备转资不存在");
		}
		return R.data(Convert.convert(DeviceTransferVO.class, deviceTransfer));
	}

	/**
	 * 分页 设备转资
	 *
	 * @param deviceTransferDTO 查询参数
	 * @param query             分页参数
	 * @return R
	 */
	@Override
	public R<IPage<DeviceTransferVO>> deviceTransferList(DeviceTransferDTO deviceTransferDTO, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		deviceTransferDTO.setRegionCode(user.getRegionCode());
		return R.data(baseMapper.deviceTransferList(Condition.getPage(query), deviceTransferDTO));
	}

	/**
	 * 获取当前登录人
	 *
	 * @return R
	 */
	@Override
	public R<String> getLoginUser() {
		return R.success(SecureUtil.getUserName());
	}

	/**
	 * 删除设备转资详情
	 *
	 * @param transfer 设备转资信息
	 */
	private void batchDelDeviceTransferDetail(DeviceTransfer transfer) {
		deviceTransferDetailService.remove(new LambdaQueryWrapper<DeviceTransferDetail>().eq(DeviceTransferDetail::getTransferId, transfer.getId()));
	}

	/**
	 * 设备转资暂存详情页面处理
	 *
	 * @param deviceTransferDTO 设备转资详情
	 * @param user              用户信息
	 * @return DeviceTransferVO
	 */
	private DeviceTransferVO deviceTransferDetailHandle(DeviceTransferDTO deviceTransferDTO, IdevelopUser user) throws ServiceException {
		DeviceTransferVO deviceTransferVO;
		List<DeviceTransferDetailDTO> deviceTransferDetailDTOList = deviceTransferDTO.getDeviceTransferDetailDTOList();
		ErpTranstplnr erpTranstplnr = new ErpTranstplnr();
		erpTranstplnr.setSwerk(user.getErpUnitCode());
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(999);
		R<IPage<ErpTranstplnr>> list = erpTranstplnrController.list(erpTranstplnr, query);
		Map<String, String> swerkMap = list.getData().getRecords().stream().collect(Collectors.toMap(item1 -> item1.getTrlnr(), item2 -> item2.getPltxt()));
		deviceTransferVO = Convert.convert(DeviceTransferVO.class, deviceTransferDTO);
		if (CollectionUtils.isNotEmpty(deviceTransferDetailDTOList)) {
			try {
				deviceTransferDetailDTOList.forEach(deviceTransferDetailDTO -> {
					if (!Objects.isNull(deviceTransferDetailDTO.getEntity()) && Objects.isNull(deviceTransferDetailDTO.getDeviceId())) {
						Map<String, Object> entity = deviceTransferDetailDTO.getEntity();
						deviceTransferDetailDTO.setDeviceId((Long) entity.get("id"));
						deviceTransferDetailDTO.setDeviceUuid((String) entity.get("uuid"));
					}
					if (deviceTransferDetailDTO.getDeviceId() == 0) {
						deviceTransferDetailDTO.setDeviceId(Long.valueOf(deviceTransferDetailDTO.getId()));
					}
					deviceTransferDetailDTO.setId(null);
					deviceTransferDetailDTO.setTransferId(deviceTransferDTO.getId());
					deviceTransferDetailDTO.setCreateTime(new Date());
					deviceTransferDetailDTO.setCreateUser(user.getUserId());
					deviceTransferDetailDTO.setCreateDept(Long.parseLong(user.getDeptId()));
					deviceTransferDetailDTO.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
					deviceTransferDetailDTO.setWbsProject(deviceTransferDTO.getWbsProject());
					deviceTransferDetailDTO.setWbsElement(deviceTransferDTO.getWbsElement());
					deviceTransferDetailDTO.setErpStatus(deviceTransferDTO.getErpStatus());
					deviceTransferDetailDTO.setDeviceAddType(deviceTransferDTO.getDeviceAddType());
					deviceTransferDetailDTO.setDeviceChangeType(deviceTransferDTO.getDeviceChangeType());
					deviceTransferDetailDTO.setErpAssetStatus("0");
					deviceTransferDetailDTO.setI6000Status(deviceTransferDTO.getI6000Status());
					Object fullNameObj = deviceTransferDetailDTO.getEntity().get(CmdbAttrConstant.FULL_NAME);
					deviceTransferDetailDTO.setDeviceName(fullNameObj != null && !"".equals(fullNameObj) ? fullNameObj.toString() : deviceTransferDetailDTO.getDeviceName());
					deviceTransferDetailDTO.setSwerk(deviceTransferDTO.getMaintenanceFactory());
					if (StringUtil.isNotBlank(deviceTransferDTO.getFunLocation())) {
						deviceTransferDetailDTO.setZsb006(swerkMap.get(deviceTransferDTO.getFunLocation()));
						deviceTransferDetailDTO.setTplnr(deviceTransferDTO.getFunLocation());
					}
					if (StringUtil.isBlank(deviceTransferDetailDTO.getHerst())) {
						deviceTransferDetailDTO.setHerst(deviceTransferDetailDTO.getEntity().get("maker").toString());
						deviceTransferDetailDTO.setTypbz(deviceTransferDetailDTO.getEntity().get("deviceModel").toString());
						deviceTransferDetailDTO.setZsb004(deviceTransferDetailDTO.getEntity().get("voltageLevelCode").toString());
						deviceTransferDetailDTO.setSerge("0000");
						String factoryDate = deviceTransferDetailDTO.getEntity().get("factoryDate").toString();
						deviceTransferDetailDTO.setBaujj(factoryDate.substring(0, 4));
						deviceTransferDetailDTO.setBaumm(factoryDate.substring(5, 7));
					}
					if (StringUtil.isNotBlank(deviceTransferDetailDTO.getDeviceStatusCode())) {
						deviceTransferDetailDTO.setDeviceCategory(deviceTransferDetailDTO.getDeviceCategoryCode());
						deviceTransferDetailDTO.setDeviceStatus(deviceTransferDetailDTO.getDeviceStatusCode());
						deviceTransferDetailDTO.setDeviceType(deviceTransferDetailDTO.getDeviceTypeCode());
						deviceTransferDetailDTO.setWarehouse(deviceTransferDetailDTO.getInWarehouseCode());
					}
					deviceTransferDetailDTO.setEntityJson(JSONObject.toJSONString(deviceTransferDetailDTO.getEntity()));
				});
			} catch (Exception e) {
				throw new ServiceException("设备信息异常，请完善！", e);
			}
			List<DeviceTransferDetail> deviceTransferDetailList = Convert.convert(new TypeReference<List<DeviceTransferDetail>>() {
			}, deviceTransferDetailDTOList);
			deviceTransferDetailService.saveBatch(deviceTransferDetailList);
			//数据回写cmdb
			//审核 回写erp的事件 响应方法触发调用
//			updateCmdbEntity(deviceTransferDetailDTOList);
			deviceTransferVO.setDeviceTransferDetailVOList(Convert.convert(new TypeReference<List<DeviceTransferDetailVO>>() {
			}, deviceTransferDetailDTOList));
		}
		return deviceTransferVO;
	}

	/**
	 * 保存 cmdb的接口
	 *
	 * @param deviceList
	 * @return
	 */
	public boolean updateCmdbEntity(List<DeviceTransferDetailDTO> deviceList, DeviceTransfer transfer) {
		if (CollectionUtils.isEmpty(deviceList)) {
			return false;
		}

		Map<Long, Map<String, Object>> hashMap = new HashMap<>();
		List<ProjectManagerDetail> detailList = new ArrayList<>();
		// 修改 资产台账
		deviceList.forEach(item -> {
			//获取模型 cid
			Long ciId = item.getCiId();
			Map<String, Object> entity = item.getEntity();
			if (Objects.isNull(entity)) {
				entity = new HashMap<>();
			}
			entity.put(CmdbAttrConstant.ID, item.getDeviceId());
			entity.put(CmdbAttrConstant.UUID, item.getDeviceUuid());
			entity.put(CmdbAttrConstant.CI_ID, ciId);

			entity.put(CmdbAttrConstant.ERP_TRANSFER_STATUS, cmdbCientityProperties.getFinishTransfer());
			if (StringUtil.isNotBlank(transfer.getOwnerUnit())) {
				entity.put(CmdbAttrConstant.PROPERTY_DEPT, transfer.getPropertyDeptName());
				entity.put(CmdbAttrConstant.PROPERTY_DEPT_CODE, transfer.getPropertyDept());
				entity.put(CmdbAttrConstant.OWNER_UNIT_CODE, transfer.getOwnerUnit());
				entity.put(CmdbAttrConstant.OWNER_UNIT, transfer.getOwnerUnitName());
			}
			// 使用保管部门
			entity.put(CmdbAttrConstant.USE_KEEP_DEPT, transfer.getUseKeepDept());
			ErpKostl erpKostl = new ErpKostl();
			erpKostl.setKostl(transfer.getUseKeepDept());
			ErpKostl detail = erpKostlService.getOne(Condition.getQueryWrapper(erpKostl));
			entity.put(CmdbAttrConstant.USE_KEEP_DEPT_NAME, detail.getKostlT());

			// 实物管理部门
			entity.put(CmdbAttrConstant.REAL_MANAGE_DEPT, transfer.getEntityKeepDept());
			ErpKostl erpKostl1 = new ErpKostl();
			erpKostl1.setKostl(transfer.getEntityKeepDept());
			ErpKostl detail1 = erpKostlService.getOne(Condition.getQueryWrapper(erpKostl1));
			entity.put(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME, detail1.getKostlT());

			// 功能位置
			entity.put(CmdbAttrConstant.FUN_LOCATION, item.getZsb006());
			entity.put(CmdbAttrConstant.FUN_LOCATION_CODE, item.getTplnr());
			// 维护工厂
			entity.put(CmdbAttrConstant.MAINTENANCE_FACTORY, transfer.getMaintenanceName());
			entity.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, transfer.getMaintenanceFactory());

			// 工厂区域
			entity.put(CmdbAttrConstant.FACTORY_AREA_CODE, transfer.getFactoryArea());
			Map<String, Object> dictMap1 = this.getDictMap(Long.valueOf(transfer.getFactoryArea()), null, cmdbDictProperties.getFactoryAreaCode());
			entity.put(CmdbAttrConstant.FACTORY_AREA, dictMap1.get(CmdbAttrConstant.DICT_VALUE));

			// 设备增加方式
			entity.put(CmdbAttrConstant.DEVICE_ADD_TYPE_CODE, transfer.getDeviceAddType());
			Map<String, Object> dictMap2 = this.getDictMap(Long.valueOf(transfer.getDeviceAddType()), null, cmdbDictProperties.getDeviceAddType());
			entity.put(CmdbAttrConstant.DEVICE_ADD_TYPE, dictMap2.get(CmdbAttrConstant.DICT_VALUE));
			// 设备变动方式
			if (Objects.nonNull(dictMap2.get(CmdbAttrConstant.DICT_VALUE))) {
				Map<String, Object> dictMap3 = this.getDictMap(null, dictMap2.get(CmdbAttrConstant.DICT_VALUE).toString(), cmdbDictProperties.getDeviceChangeType());
				entity.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, dictMap3.get(CmdbAttrConstant.DICT_VALUE));
				entity.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE, dictMap3.get(CmdbAttrConstant.DICT_KEY));
			}
			// ERP资产编码
			entity.put(CmdbAttrConstant.ASSET_CODE_ERP, item.getErpAssetCode());
			entity.put(CmdbAttrConstant.DEVICE_CODE_ERP, item.getErpAccountCode());
			// ERP转资状态
			entity.put(CmdbAttrConstant.ERP_TRANSFER_STATUS, cmdbCientityProperties.getErpTransferStatus1());
			// 线站标识
			entity.put(CmdbAttrConstant.LINE_STATION, "00000000000000000");
			entity.put(CmdbAttrConstant.LINE_STATION_SIGN, "00000000000000000");
			// 制造国家与地区
			entity.put(CmdbAttrConstant.MAINTENANCE_COUNTRY, cmdbCientityProperties.getCountryArea1());

			hashMap.put(Long.parseLong(entity.get("id").toString()), entity);

			// 用于记录erp的生成情况
			detailList.add(ProjectManagerDetail.builder()
				.uuid(item.getDeviceUuid())
				.deviceCode(item.getDeviceCode())
				.deviceType(item.getDeviceType())
				.erpAssetCode(item.getErpAssetCode())
				.erpAccountCode(item.getErpAccountCode())
				.stage(WorkOrderTypeEnum.ZZ.getText())
				.erpAssetStatus(1)
				.erpStatus(2)
				.erpTransferStatus(cmdbCientityProperties.getErpTransferStatus1())
				.wbsName(item.getWbsProject())
				.wbsCode(item.getWbsElement())
				.build());
		});
		//保存cmdb
		iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
		// 用于记录erp的生成情况
		projectManagerDetailService.saveOrUpdateBatch(detailList);
		return true;
	}

	/**
	 * 保存i6000的接口
	 *
	 * @param deviceList
	 * @return
	 */
	public boolean updateI6000Entity(List<DeviceTransferDetailDTO> deviceList, DeviceTransfer transfer) {
		if (CollectionUtil.isEmpty(deviceList)) {
			return false;
		}
		Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
		Map<String, Map<String, Object>> hashMap = new HashMap<>();
		// 修改 资产台账
		deviceList.forEach(s -> {
			//获取模型 cid
			Long ciId = s.getCiId();
			Map<String, Object> ent = s.getEntity();
			if (Objects.isNull(ent)) {
				ent = new HashMap<>();
			}
			ent.put(CmdbAttrConstant.ID, s.getDeviceId());
			ent.put(CmdbAttrConstant.UUID, s.getDeviceUuid());
			ent.put(CmdbAttrConstant.CI_ID, ciId);
			ent.put(CmdbAttrConstant.DEVICE_NAME, s.getDeviceName());
			ent.put(CmdbAttrConstant.DEVICE_TYPE, s.getDeviceType());
			ent.put(CmdbAttrConstant.DEVICE_CODE, s.getDeviceCode());
			ent.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, s.getDeviceChangeType());
			ent.put(CmdbAttrConstant.ERP_TRANSFER_STATUS, cmdbCientityProperties.getFinishTransfer());
			if (StringUtil.isNotBlank(transfer.getOwnerUnit())) {
				ent.put(CmdbAttrConstant.PROPERTY_DEPT, transfer.getPropertyDeptName());
				ent.put(CmdbAttrConstant.PROPERTY_DEPT_CODE, transfer.getPropertyDept());
				ent.put(CmdbAttrConstant.OWNER_UNIT_CODE, transfer.getOwnerUnit());
				ent.put(CmdbAttrConstant.OWNER_UNIT, transfer.getOwnerUnitName());
			}
			ent.put(CmdbAttrConstant.USE_KEEP_DEPT, transfer.getUseKeepDept());
			ent.put(CmdbAttrConstant.REAL_MANAGE_DEPT, transfer.getEntityKeepDept());
			// todo 应该获取实际的ERP编码
			ent.put(CmdbAttrConstant.ASSET_CODE_ERP, s.getErpAssetCode());
			ent.put(CmdbAttrConstant.DEVICE_CODE_ERP, s.getErpAccountCode());
			if (StringUtil.isNotBlank(erpI6000MapByCiId.get(s.getDeviceType()))) {
				ent.put("CITYPE_ID", erpI6000MapByCiId.get(s.getDeviceType()));
				hashMap.put(ent.get(CmdbAttrConstant.UUID).toString(), ent);
			}
		});
		//同步i6000
		if (CollectionUtil.isNotEmpty(hashMap)) {
			ii6000Service.i6000Batchupdate(hashMap);
		}
		// 用于记录erp的生成情况
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> deskDeviceTransferStatus(DeviceTransferDTO dto) throws Exception {
		DeviceTransfer transfer = baseMapper.selectOne(Wrappers.<DeviceTransfer>lambdaQuery().eq(DeviceTransfer::getId, dto.getId()).eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (StringUtil.isBlank(dto.getTaskDefinitionKey())) {
			dto.setTaskDefinitionKey(transfer.getProcessStatus());
		}
		List<DeviceTransferDetail> list = deviceTransferDetailService.list(Wrappers.<DeviceTransferDetail>lambdaQuery().eq(DeviceTransferDetail::getTransferId, dto.getId()).eq(DeviceTransferDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		if (!thirdProperties.getApiErp()) {
			if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_SERVICE.getNode().equals(dto.getTaskDefinitionKey()) && dto.getWorkerStatus() == 0) {
				for (int i = 0; i < list.size(); i++) {
					ErpDeviceOrderDTO erpDeviceOrderDTO = new ErpDeviceOrderDTO();
					erpDeviceOrderDTO.setDeviceRecordId(dto.getId());
					erpDeviceOrderDTO.setFilingNo(transfer.getFilingNo());
					erpDeviceOrderDTO.setErpExamineStatus(0);
					erpDeviceOrderDTO.setErpExamineReason("通过");
					erpDeviceOrderDTO.setDeviceRecordListDTOList(Convert.convert(new TypeReference<List<ErpDeviceDetailDTO>>() {
					}, list.get(i)));
					erpDeviceTransfer(erpDeviceOrderDTO);
				}
				if (list.size() == 1) {
					ErpDeviceOrderDTO erpDeviceOrderDTO = new ErpDeviceOrderDTO();
					erpDeviceOrderDTO.setDeviceRecordId(dto.getId());
					erpDeviceOrderDTO.setFilingNo(transfer.getFilingNo());
					erpDeviceOrderDTO.setErpExamineStatus(0);
					erpDeviceOrderDTO.setErpExamineReason("通过");
					erpDeviceOrderDTO.setDeviceRecordListDTOList(Convert.convert(new TypeReference<List<ErpDeviceDetailDTO>>() {
					}, list));
					erpDeviceTransfer(erpDeviceOrderDTO);
				}
				return R.success(ResultCode.SUCCESS);
			}
		}
		//处理数据
		IdevelopUser user = SecureUtil.getUser();
		String transferNode = null;
		if (StringUtil.isBlank(dto.getTaskDefinitionKey())) {
			dto.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode());
		}
		dto.setProcessStatus(dto.getTaskDefinitionKey());
		if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode().equals(dto.getTaskDefinitionKey())) {
			transferNode = DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_REVIEW.getNode();
			dto.setComment(dto.getComment());
		}
		if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_REVIEW.getNode().equals(dto.getTaskDefinitionKey())) {
			if (dto.getWorkerStatus() == 1) {
				transferNode = DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode();
				dto.setComment(dto.getComment());
			} else {
				transferNode = DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_SERVICE.getNode();
			}
		}
		Map<String, Object> variable = new HashMap<>();
		variable.put("orderId", transfer.getId());
		variable.put("orderNo", transfer.getFilingNo());
		variable.put("userId", user.getUserId());
		variable.put("userName", user.getUserName());
		variable.put("regionCode", user.getRegionCode());

		// 记录审核流程
		if (StringUtil.isBlank(dto.getExamineRole())) {
			//  获取当前节点操作角色
			List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(transfer.getFilingNo());
			String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
			dto.setExamineRole(roleName);
		}
		// 增加操作记录
		logOptService.commonLogOpt(LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString())
			.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).title(dto.getComment()).optRole(dto.getExamineRole()).time(new Date()).build());
		String title = dto.getTaskDefinitionKey().equals(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode()) ? dto.getComment() : (dto.getWorkerStatus() == 0 ? "运行专工同意" : "运行专工驳回");
		approveRecordService.commonRecord(ApproveRecord.builder().wbsId(transfer.getWbsProject()).filingNo(transfer.getId())
			.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).nodeId(dto.getTaskDefinitionKey()).optRole(dto.getExamineRole())
			.nodeName(DeviceTransferBpmNodeEnum.getMessage(dto.getTaskDefinitionKey())).optTitle(title)
			.optOpinion(dto.getComment()).filingCode(transfer.getFilingNo()).approveStatus(dto.getWorkerStatus())
			.build());
		// 更新工单信息
		baseMapper.update(Wrappers.<DeviceTransfer>lambdaUpdate().eq(DeviceTransfer::getId, dto.getId()).set(DeviceTransfer::getUpdateTime, new Date())
			.set(DeviceTransfer::getUpdateUser, user.getUserId()).set(DeviceTransfer::getStatus, dto.getWorkerStatus() != 0 ? DeviceTransferEnum.TEMPORARILY.getCode() : DeviceTransferEnum.TRANSFER_APPROVAL.getCode()).set(DeviceTransfer::getProcessStatus, transferNode));

		if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_REVIEW.getNode().equals(dto.getTaskDefinitionKey()) && dto.getWorkerStatus() == 0 && thirdProperties.getApiErp()) {
			Map<Object, Object> deviceStatusMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceStatus());
			Map<Object, Object> deviceAddMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceAdd());
			Map<Object, Object> deviceCtMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
			try {
				// 推送erp审核
				ErpTransEqunr erpTransEqunr = new ErpTransEqunr();
				erpTransEqunr.setXtdocId(dto.getId());
				erpTransEqunr.setXtdocNo(transfer.getFilingNo());
				erpTransEqunr.setOperationType(ErpOperationEnum.C);
				//获取计量单位
				R<Map<String, Object>> mapR = cmdbClient.feignCientityDetailById(cmdbDictProperties.getDeviceType(), Long.parseLong(transfer.getDeviceType()));
				String unitName = "台";
				if (mapR.isSuccess()) {
					String unitCode = mapR.getData().get("UNIT_CODE").toString();
					if (StringUtil.isNotBlank(unitCode)) {
						R<Map<String, Object>> unit = cmdbClient.feignCientityDetailById(cmdbDictProperties.getUnifiedCode(), Long.parseLong(unitCode));
						if (unit.isSuccess()) {
							unitName = unit.getData().get("dictKey").toString();
						}
					}
				}
				if (CollectionUtil.isNotEmpty(list)) {
					List<ErpTransEqunrItem> erpTransEqunrItemList = list.stream().map(item -> {
						ErpTransEqunrItem equnrItem = new ErpTransEqunrItem();
						BeanUtil.copyProperties(item, equnrItem);
						equnrItem.setXtbm(item.getDeviceUuid());
						equnrItem.setXtbmNo(item.getDeviceCode());
						equnrItem.setSwid("");
						equnrItem.setEqktx(item.getDeviceName());
						equnrItem.setZsb001(transfer.getUseKeepDept());
						equnrItem.setZsb002(transfer.getEntityKeepDept());
						equnrItem.setZsb010(transfer.getUseKeepPerson());
						equnrItem.setZsb004(Constants.ERP_ZSB004);
						equnrItem.setStat(deviceStatusMapErp.get(item.getDeviceStatus()).toString());
						equnrItem.setZsb005(deviceAddMapErp.get(item.getDeviceAddType()).toString());
						equnrItem.setHerst(item.getHerst());
						equnrItem.setStort("");
						equnrItem.setSbfl(deviceCtMapErp.get(item.getDeviceType()).toString());
						equnrItem.setAnlnr("");
						equnrItem.setEqunr("");
						equnrItem.setBeber(user.getCorpName().contains("县") ? "004" : "003");
						equnrItem.setZcabn_ztpm1005(1);
						equnrItem.setPosid(item.getWbsElement());
						equnrItem.setSwerk(transfer.getMaintenanceFactory());
						equnrItem.setEqart("");
						equnrItem.setHerld("CN");
						equnrItem.setInbdt("");
						equnrItem.setTbbs("");
						return equnrItem;
					}).collect(Collectors.toList());
					for (ErpTransEqunrItem erpTransEqunrItem : erpTransEqunrItemList) {
						erpTransEqunrItem.setZcabn_ztpm1006(unitName);
					}
					erpTransEqunr.setErpTransEqunrItemList(erpTransEqunrItemList);
					// ERP发起审核
					ErpTransEqunrResp equnrResp = erpService.transEqunr(erpTransEqunr);
					if (!StringUtils.equals(equnrResp.getCode(), "E")) {
						List<ProjectManagerDetail> detailList = erpTransEqunrItemList.stream().map(item ->
								ProjectManagerDetail.builder()
										.uuid(item.getXtbm())
										.erpAssetStatus(1)
										.erpTransferStatus(cmdbCientityProperties.getErpTransferStatus3())
										.erpStatus(1)
										.stage(WorkOrderTypeEnum.ZZ.getText())
										.build()
						).collect(Collectors.toList());
						projectManagerDetailService.saveOrUpdateBatch(detailList);
					}
				}
			} catch (Exception e) {
				throw new ServiceException("同步erp设备信息异常！", e);
			}
		}
		if (dto.getWorkerStatus() == 1) {
			LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ApproveRecord::getOptOpinion, "待审批").set(ApproveRecord::getOptTitle, "待审批").eq(ApproveRecord::getFilingNo, transfer.getId()).eq(ApproveRecord::getNodeId, DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode());
			approveRecordService.update(updateWrapper);
			//驳回
			try {
				List<HussarTaskVo> hussarTaskVos = hussarBpmService.queryTaskId(transfer.getFilingNo());
				HussarBpmDto hussarBpmDto = new HussarBpmDto();
				hussarBpmDto.setTaskId(hussarTaskVos.get(0).getTaskId());
				hussarBpmDto.setUserId(user.getUserId().toString());
				hussarBpmDto.setComment(StringUtil.isNotBlank(dto.getComment()) ? dto.getComment() : "驳回");
				hussarBpmDto.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark());
				hussarBpmDto.setRejectNode(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode());
				hussarBpmDto.setVariable(variable);
				hussarBpmDto.setBusinessKey(transfer.getFilingNo());
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
			hussarBpmDTO.setBusinessKey(transfer.getFilingNo());
			hussarBpmDTO.setComment(dto.getComment());
			hussarBpmDTO.setVariable(variable);
			hussarBpmDTO.setParticipantType("2");
			hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark());
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

	@Override
	public R<IPage<DeviceTransferVO>> deskDeviceTransferList(DeviceTransferDTO dto, Query query) {
		if (StringUtil.isBlank(dto.getOrderNoList())) {
			return R.data(new Page<>());
		}
		dto.setFilingNoList(Arrays.asList(dto.getOrderNoList().split(",")));
		IPage<DeviceTransferVO> page = baseMapper.deskDeviceTransferList(dto, Condition.getPage(query));
		if (CollectionUtil.isNotEmpty(page.getRecords())) {
			List<String> ids = page.getRecords().stream().map(DeviceTransferVO::getId).collect(Collectors.toList());
			List<DeviceTransferDetail> list = deviceTransferDetailService.list(Wrappers.<DeviceTransferDetail>lambdaQuery().eq(DeviceTransferDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).in(DeviceTransferDetail::getTransferId, ids));
			Map<String, List<DeviceTransferDetail>> listMap = list.stream().collect(Collectors.groupingBy(DeviceTransferDetail::getTransferId));
			page.setRecords(page.getRecords().stream().map(item -> {
				item.setProcessType(DeviceTransferBpmNodeEnum.getType(item.getProcessStatus()));
				item.setDeviceNum(listMap.get(item.getId()).size());
				return item;
			}).collect(Collectors.toList()));
		}
		return R.data(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Integer> erpDeviceTransfer(ErpDeviceOrderDTO erpDeviceOrderDTO) throws Exception {
		DeviceTransfer transfer = baseMapper.selectOne(Wrappers.<DeviceTransfer>lambdaQuery().eq(DeviceTransfer::getId, erpDeviceOrderDTO.getDeviceRecordId()).eq(DeviceTransfer::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
		erpDeviceOrderDTO.setFilingNo(transfer.getFilingNo());
		if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode().equals(transfer.getProcessStatus())) {
			if (0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				if (CollectionUtil.isNotEmpty(erpDeviceOrderDTO.getDeviceRecordListDTOList())) {
					deviceTransferDetailService.updateBatchById(Convert.convert(new TypeReference<List<DeviceTransferDetail>>() {
					}, erpDeviceOrderDTO.getDeviceRecordListDTOList()), erpDeviceOrderDTO.getDeviceRecordListDTOList().size());
				}
			}
		} else {
			HussarCompleteParam hussarCompleteParam = new HussarCompleteParam();
			HussarTaskVo hussarTaskVo = new HussarTaskVo();
			com.lnsoft.hussar.bpm.tool.api.R<List<HussarTaskVo>> listR = hussarBpmClient.queryTaskIdByBusinessKey(erpDeviceOrderDTO.getFilingNo());
			if (ResultCode.SUCCESS.getCode() == listR.getCode()) {
				if (CollectionUtil.isNotEmpty(listR.getData())) {
					hussarTaskVo = listR.getData().get(0);
				} else {
					hussarTaskVo.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode());
				}
			} else {
				return R.fail(listR.getMsg());
			}
			hussarCompleteParam.setTaskId(hussarTaskVo.getTaskId());
			hussarCompleteParam.setParticipantType(null);
			hussarCompleteParam.setTaskType("1");
			Map<String, Object> variable = new HashMap<>();
			if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_SERVICE.getNode().equals(hussarTaskVo.getTaskDefinitionKey())) {
				if (0 == erpDeviceOrderDTO.getErpExamineStatus()) {
					hussarCompleteParam.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode());
					variable.put("flag", 0);
				} else {
					hussarCompleteParam.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode());
					variable.put("flag", 1);
				}
			}
			if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode().equals(hussarTaskVo.getTaskDefinitionKey())) {
				if (0 == erpDeviceOrderDTO.getErpExamineStatus()) {
					hussarCompleteParam.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode());
					variable.put("flag", 0);
				} else {
					hussarCompleteParam.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode());
					variable.put("flag", 1);
				}
			}
			IdevelopUser idevelopUser = SecureUtil.getUser();
			User user = new User();
			if (!idevelopUser.getUserName().contains("管理员")) {
				// TODO 待修复问题
				Role role = sysClient.getRoleByRoleName(Constants.ERP_ROLE);
				if (Objects.isNull(role)) {
					return R.fail("未获取到审核角色，请联系运维人员");
				}
				user.setId(idevelopUser.getUserId());
			} else {
				user.setId(idevelopUser.getUserId());
			}
			boolean flag = true;
			List<DeviceTransferDetail> details = deviceTransferDetailService.list(Wrappers.<DeviceTransferDetail>lambdaQuery().eq(DeviceTransferDetail::getTransferId, erpDeviceOrderDTO.getDeviceRecordId()).eq(DeviceTransferDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
			long count = details.stream().filter(item -> !Objects.equals(item.getErpStatus(), "2")).count();
			if (count > 1 && DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode().equals(hussarTaskVo.getTaskDefinitionKey())) {
				flag = false;
			}
			if (erpDeviceOrderDTO.getErpExamineStatus() == 1) {
				flag = false;
				//驳回
				try {
					// 用于记录erp的生成情况
					List<ProjectManagerDetail> detailList = couverErpDetail(details);
					projectManagerDetailService.saveOrUpdateBatch(detailList);
					List<HussarTaskVo> hussarTaskVos = hussarBpmService.queryTaskId(erpDeviceOrderDTO.getFilingNo());
					HussarBpmDto hussarBpmDto = new HussarBpmDto();
					hussarBpmDto.setTaskId(hussarTaskVos.get(0).getTaskId());
					hussarBpmDto.setUserId(user.getId().toString());
					hussarBpmDto.setComment(StringUtil.isNotBlank(erpDeviceOrderDTO.getErpExamineReason()) ? erpDeviceOrderDTO.getErpExamineReason() : "驳回");
					hussarBpmDto.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark());
					hussarBpmDto.setRejectNode(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode());
					hussarBpmDto.setVariable(variable);
					hussarBpmDto.setBusinessKey(transfer.getFilingNo());
					com.lnsoft.hussar.bpm.tool.api.R<JSONArray> hussarR = hussarBpmClient.anyNodeReject(hussarBpmDto);
					if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
						throw new Exception("驳回流程异常!");
					}
				} catch (Exception e) {
					throw new Exception("驳回流程异常!");
				}
			} else if (erpDeviceOrderDTO.getErpExamineStatus() == 0 && flag) {
				//同意
				HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
				hussarBpmDTO.setBusinessKey(erpDeviceOrderDTO.getFilingNo());
				hussarBpmDTO.setComment(erpDeviceOrderDTO.getErpExamineReason());
				hussarBpmDTO.setVariable(variable);
				hussarBpmDTO.setParticipantType("2");
				hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark());
				hussarBpmDTO.setTaskDefinitionKey(hussarTaskVo.getTaskDefinitionKey());
				hussarBpmDTO.setTaskType("1");
				try {
					R<List<HussarComplateVo>> hussarR = hussarBpmService.hussarSubmit(hussarBpmDTO);
					if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
						throw new Exception("提交流程异常!");
					}
					if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_SERVICE.getNode().equals(hussarTaskVo.getTaskDefinitionKey()) && details.size() == 1) {
						//转资设备仅一条时，同时erp财务层通过
						hussarBpmDTO.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode());
						hussarTaskVo.setTaskDefinitionKey(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode());
						hussarR = hussarBpmService.hussarSubmit(hussarBpmDTO);
						if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
							throw new Exception("提交流程异常!");
						}
					}
				} catch (Exception e) {
					throw new Exception("提交流程异常!");
				}
			}
			// 增加操作记录
			LogOpt logOpt = LogOpt.builder().logId(erpDeviceOrderDTO.getDeviceRecordId()).logData(erpDeviceOrderDTO.toString()).params(erpDeviceOrderDTO.toString())
				.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).title(erpDeviceOrderDTO.getErpExamineReason()).optRole("ERP审核角色").build();
			// 代表erp操作日志
			logOpt.setStatus(0);
			logOptService.commonLogOpt(logOpt);
			// 记录审核流程
			ApproveRecord record = ApproveRecord.builder().wbsId(transfer.getWbsProject()).filingNo(erpDeviceOrderDTO.getDeviceRecordId())
				.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).nodeId(hussarTaskVo.getTaskDefinitionKey())
				.nodeName(DeviceTransferBpmNodeEnum.getMessage(hussarTaskVo.getTaskDefinitionKey()))
				.optTitle(0 == erpDeviceOrderDTO.getErpExamineStatus() ? "ERP同意" : "ERP驳回")
				.optOpinion(erpDeviceOrderDTO.getErpExamineReason())
				.optRole("ERP审核角色")
				.filingCode(erpDeviceOrderDTO.getFilingNo())
				.approveStatus(erpDeviceOrderDTO.getErpExamineStatus()).build();
			approveRecordService.commonRecord(record);
			if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_SERVICE.getNode().equals(hussarTaskVo.getTaskDefinitionKey()) && 0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				// 根据ERP结果更新设备转资详情信息
				List<DeviceTransferDetail> collect = erpDeviceOrderDTO.getDeviceRecordListDTOList().stream().map(item -> {
					log.warn(item.toString());
					DeviceTransferDetail detail = new DeviceTransferDetail();
					detail.setId(item.getId());
					if (!thirdProperties.getApiErp()) {
						detail.setErpAssetCode(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
						detail.setErpAccountCode(UUID.randomUUID().toString().replace("-", "").substring(0, 18));
					} else {
						detail.setErpAssetCode(item.getErpAssetCode());
						detail.setErpAccountCode(item.getErpAccountCode());
					}
					detail.setErpStatus("2");
					return detail;
				}).collect(Collectors.toList());
				if (CollectionUtil.isNotEmpty(collect)) {
					if (!thirdProperties.getApiErp()) {
						deviceTransferDetailService.updateBatchById(collect);
					} else {
						deviceTransferDetailService.update(
							Wrappers.<DeviceTransferDetail>lambdaUpdate()
								.set(DeviceTransferDetail::getErpAssetCode, collect.get(0).getErpAssetCode())
								.set(DeviceTransferDetail::getErpStatus, "2")
								.set(DeviceTransferDetail::getErpAccountCode, collect.get(0).getErpAccountCode())
								.eq(DeviceTransferDetail::getDeviceUuid, collect.get(0).getId())
								.eq(DeviceTransferDetail::getTransferId, erpDeviceOrderDTO.getDeviceRecordId()));
					}
					List<String> detailIds = collect.stream().map(DeviceTransferDetail::getId).collect(Collectors.toList());
					List<DeviceTransferDetail> list = deviceTransferDetailService.list(Wrappers.<DeviceTransferDetail>lambdaQuery()
						.in(DeviceTransferDetail::getDeviceUuid, detailIds)
						.eq(DeviceTransferDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
						.eq(DeviceTransferDetail::getTransferId, erpDeviceOrderDTO.getDeviceRecordId()));
					List<DeviceTransferDetailDTO> convertList = Convert.convert(new TypeReference<List<DeviceTransferDetailDTO>>() {
					}, list);
					updateCmdbEntity(convertList, transfer);
				}
			}
			if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode().equals(hussarTaskVo.getTaskDefinitionKey()) && 0 == erpDeviceOrderDTO.getErpExamineStatus()) {
				// 根据ERP结果更新设备转资详情信息
				List<DeviceTransferDetail> collect = erpDeviceOrderDTO.getDeviceRecordListDTOList().stream().map(item -> {
					DeviceTransferDetail detail = new DeviceTransferDetail();
					log.warn(item.toString());
					detail.setId(item.getId());
					if (!thirdProperties.getApiErp()) {
						detail.setErpAssetCode(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
						detail.setErpAccountCode(UUID.randomUUID().toString().replace("-", "").substring(0, 18));
					} else {
						detail.setErpAssetCode(item.getErpAssetCode());
						detail.setErpAccountCode(item.getErpAccountCode());
					}
					detail.setErpStatus("2");
					return detail;
				}).collect(Collectors.toList());
				if (CollectionUtil.isNotEmpty(collect)) {
					if (!thirdProperties.getApiErp()) {
						deviceTransferDetailService.updateBatchById(collect);
					} else {
						deviceTransferDetailService.update(Wrappers.<DeviceTransferDetail>lambdaUpdate()
							.set(DeviceTransferDetail::getErpAssetCode, collect.get(0).getErpAssetCode())
							.set(DeviceTransferDetail::getErpStatus, "2")
							.set(DeviceTransferDetail::getErpAccountCode, collect.get(0).getErpAccountCode())
							.eq(DeviceTransferDetail::getDeviceUuid, collect.get(0).getId())
							.eq(DeviceTransferDetail::getTransferId, erpDeviceOrderDTO.getDeviceRecordId()));
					}
					List<String> detailIds = collect.stream().map(DeviceTransferDetail::getId).collect(Collectors.toList());
					List<DeviceTransferDetail> list = deviceTransferDetailService.
						list(Wrappers.<DeviceTransferDetail>lambdaQuery()
							.in(DeviceTransferDetail::getDeviceUuid, detailIds)
							.eq(DeviceTransferDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED)
							.eq(DeviceTransferDetail::getTransferId, erpDeviceOrderDTO.getDeviceRecordId())
						);
					List<DeviceTransferDetailDTO> convertList = Convert.convert(new TypeReference<List<DeviceTransferDetailDTO>>() {
					}, list);
					updateCmdbEntity(convertList, transfer);
				}
			}
			if (DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode().equals(hussarTaskVo.getTaskDefinitionKey()) && flag) {
				// 增加操作记录
				LogOpt build = LogOpt.builder().logId(erpDeviceOrderDTO.getDeviceRecordId()).logData(erpDeviceOrderDTO.toString()).params(erpDeviceOrderDTO.toString()).optRole("--")
					.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).title(DeviceTransferBpmNodeEnum.getMessage(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode())).optName("系统").build();
				build.setStatus(0);
				logOptService.commonLogOpt(build);
				// 记录审核流程
				ApproveRecord approveRecord = ApproveRecord.builder().wbsId(transfer.getWbsProject()).filingNo(erpDeviceOrderDTO.getDeviceRecordId())
					.optType(OptTypeEnum.DEVICE_TRANSFER.getCode()).nodeId(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode())
					.nodeName(DeviceTransferBpmNodeEnum.getMessage(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode()))
					.optTitle(DeviceTransferBpmNodeEnum.getMessage(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode()))
					.optOpinion(DeviceTransferBpmNodeEnum.getMessage(DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode()))
					.filingCode(erpDeviceOrderDTO.getFilingNo()).approveStatus(erpDeviceOrderDTO.getErpExamineStatus())
					.optName("系统").optRole("--").build();
				approveRecord.setStatus(1);
				approveRecordService.commonRecord(approveRecord);
				// 更新工单信息
				baseMapper.update(Wrappers.<DeviceTransfer>lambdaUpdate().eq(DeviceTransfer::getId, erpDeviceOrderDTO.getDeviceRecordId()).set(DeviceTransfer::getUpdateTime, new Date())
					.set(DeviceTransfer::getProcessStatus, DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_FINISH.getNode()).set(DeviceTransfer::getStatus, DeviceTransferEnum.FINISH.getCode()).set(DeviceTransfer::getErpStatus, "2"));
			} else if (0 != erpDeviceOrderDTO.getErpExamineStatus()) {
				baseMapper.update(Wrappers.<DeviceTransfer>lambdaUpdate().eq(DeviceTransfer::getId, erpDeviceOrderDTO.getDeviceRecordId()).set(DeviceTransfer::getUpdateTime, new Date())
					.set(DeviceTransfer::getProcessStatus, DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_APPLY.getNode()).set(DeviceTransfer::getStatus, DeviceTransferEnum.TEMPORARILY.getCode()).set(DeviceTransfer::getErpStatus, "3"));
			} else {
				// 更新工单信息
				baseMapper.update(Wrappers.<DeviceTransfer>lambdaUpdate().eq(DeviceTransfer::getId, erpDeviceOrderDTO.getDeviceRecordId()).set(DeviceTransfer::getUpdateTime, new Date())
					.set(DeviceTransfer::getProcessStatus, DeviceTransferBpmNodeEnum.DEVICE_TRANSFER_PROFESSIONAL_FINANCE.getNode()).set(DeviceTransfer::getStatus, DeviceTransferEnum.TRANSFER_APPROVAL.getCode()).set(DeviceTransfer::getErpStatus, "1"));
			}
		}
		return R.success(ResultCode.SUCCESS);
	}


	/**
	 * 用于记录erp的生成情况
	 *
	 * @param details
	 * @return
	 */
	private List<ProjectManagerDetail> couverErpDetail(List<DeviceTransferDetail> details) {
		return details.stream().map(item ->
			ProjectManagerDetail.builder()
				.deviceCode(item.getDeviceUuid())
				.erpTransferStatus(cmdbCientityProperties.getErpTransferStatus4()) // 转资失败
				.erpStatus(1) // 1 同步中
				.erpAssetStatus(0) // 0未使用
				.stage(WorkOrderTypeEnum.ZZ.getText())
				.wbsCode(item.getWbsElement())
				.wbsName(item.getWbsProject())
				.build()
		).collect(Collectors.toList());
	}


	private Map<String, Object> getDictMap(Long entityId, String dictValue, Long ciId) {
		FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
		if (StringUtils.isNotEmpty(dictValue)) {
			cientitySearch.setDictValue(dictValue);
		}
		if (Objects.nonNull(entityId)) {
			cientitySearch.setFilterCiEntityId(entityId);
		}
		cientitySearch.setCiId(ciId);
		R<List<Map<String, Object>>> dictListR = cmdbClient.feignGetCiEntityDictListById(cientitySearch);
		List<Map<String, Object>> dictList = dictListR.getData();
		if (org.springframework.util.CollectionUtils.isEmpty(dictList)) {
			return new HashMap<>();
		}
		return dictList.get(0);

	}


}

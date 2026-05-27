/**
 * .
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
import com.lnsoft.common.enums.hussar.*;
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
import com.lnsoft.device.api.asset.dto.DeviceInventoryDTO;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.warehouse.dto.DeviceOperationDetailDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceOutboundDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceOutboundDetailDTO;
import com.lnsoft.device.api.warehouse.entity.*;
import com.lnsoft.device.api.warehouse.mapper.DeviceApplyDetailMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceApplyMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOutboundMapper;
import com.lnsoft.device.api.warehouse.service.*;
import com.lnsoft.device.api.warehouse.utils.DSwitcherSyncUtil;
import com.lnsoft.device.api.warehouse.vo.DeviceOrderFileVO;
import com.lnsoft.device.api.warehouse.vo.DeviceOutboundDetailVO;
import com.lnsoft.device.api.warehouse.vo.DeviceOutboundVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageSdnVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.DeviceOrderFile;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.entity.Region;
import com.lnsoft.system.feign.IDeptClient;
import com.lnsoft.system.feign.IRegionClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备出库表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Service
public class DeviceOutboundServiceImpl extends BaseServiceImpl<DeviceOutboundMapper, DeviceOutbound> implements IDeviceOutboundService {

    @Resource
    private IDeviceOperationService deviceOperationService;
    @Resource
    private IDeviceOperationDetailService deviceOperationDetailService;
    @Resource
    private IDeviceOutboundDetailService deviceOutboundDetailService;
    @Resource
    private OrderNumberUtil orderNumberUtil;
    @Resource
    private DeviceApplyMapper deviceApplyMapper;
    @Resource
    private DeviceApplyDetailMapper deviceApplyDetailMapper;
    @Resource
    private ILogOptService logOptService;
    @Resource
    private IApproveRecordService approveRecordService;
    @Resource
    private IDeviceOrderFileService deviceOrderFileService;
    @Resource
    private ICmdbService iCmdbService;
    @Resource
    private IHussarBpmService hussarBpmService;
    @Resource
    private IDeviceInventoryService deviceInventoryService;
    @Resource
    private IDeptClient deptClient;
    @Resource
    private IRegionClient regionClient;
    @Resource
    private DSwitcherSyncUtil dSwitcherSyncUtil;

    /**
     * 分页 设备出库表
     *
     * @param page              分页参数
     * @param deviceOutboundDTO 查询条件
     */
    @Override
    public R<IPage<DeviceOutboundVO>> deviceOutboundList(IPage<DeviceOutbound> page, DeviceOutboundDTO deviceOutboundDTO) {
        IdevelopUser user = SecureUtil.getUser();
        deviceOutboundDTO.setRegionCode(user.getRegionCode());
//		LambdaQueryWrapper<DeviceOutbound> queryWrapper = new LambdaQueryWrapper<DeviceOutbound>().eq(DeviceOutbound::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
//		queryWrapper.likeRight(DeviceOutbound::getRegionCode, user.getRegionCode());
//		queryWrapper.like(StringUtil.isNotBlank(deviceOutboundDTO.getOutboundNo()), DeviceOutbound::getOutboundNo, deviceOutboundDTO.getOutboundNo());
//		queryWrapper.like(StringUtil.isNotBlank(deviceOutboundDTO.getApplyNo()), DeviceOutbound::getApplyNo, deviceOutboundDTO.getApplyNo());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOutboundDTO.getReceiveUnit()), DeviceOutbound::getReceiveUnit, deviceOutboundDTO.getReceiveUnit());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOutboundDTO.getWarehouse()), DeviceOutbound::getWarehouse, deviceOutboundDTO.getWarehouse());
//		queryWrapper.ge(Objects.nonNull(deviceOutboundDTO.getBeginTime()), DeviceOutbound::getCreateTime, deviceOutboundDTO.getBeginTime());
//		queryWrapper.le(Objects.nonNull(deviceOutboundDTO.getEndTime()), DeviceOutbound::getCreateTime, deviceOutboundDTO.getEndTime());
//		queryWrapper.eq(Objects.nonNull(deviceOutboundDTO.getStatus()), DeviceOutbound::getStatus, deviceOutboundDTO.getStatus());
//		queryWrapper.eq(Objects.nonNull(deviceOutboundDTO.getOutboundStatus()), DeviceOutbound::getOutboundStatus, deviceOutboundDTO.getOutboundStatus());
//		queryWrapper.orderByDesc(DeviceOutbound::getCreateTime);
//		IPage<DeviceOutbound> deviceOutboundIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(baseMapper.getPage(page, deviceOutboundDTO));
//		return R.data(Convert.convert(new TypeReference<IPage<DeviceOutboundVO>>() {
//		}, deviceOutboundIPage));
    }

    /**
     * 个人工作台查询出库工单
     *
     * @param deviceOutboundDTO 查询条件
     * @param query             分页条件
     * @return R
     */
    @Override
    public R<IPage<DeviceOutboundVO>> deskList(DeviceOutboundDTO deviceOutboundDTO, Query query) {
        if (StringUtil.isBlank(deviceOutboundDTO.getOrderNoList())) {
            return R.data(new Page<>());
        }

        List<String> strings = Arrays.asList(deviceOutboundDTO.getOrderNoList().split(","));
        if (strings.size() == 1 && strings.get(0).contains("CK")) {
            LambdaQueryWrapper<DeviceOutbound> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(DeviceOutbound::getOutboundNo, Arrays.asList(deviceOutboundDTO.getOrderNoList().split(",")))
                    .eq(DeviceOutbound::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).orderByDesc(DeviceOutbound::getCreateTime);
            IPage<DeviceOutbound> deviceOutboundIPage = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
            return R.data(Convert.convert(new TypeReference<IPage<DeviceOutboundVO>>() {
            }, deviceOutboundIPage));
        } else {
            LambdaQueryWrapper<DeviceOutbound> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(DeviceOutbound::getApplyNo, Arrays.asList(deviceOutboundDTO.getOrderNoList().split(",")))
                    .eq(DeviceOutbound::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).orderByDesc(DeviceOutbound::getCreateTime);
            if (deviceOutboundDTO.getQueryHandleFlag() == 0) {
                queryWrapper.isNull(DeviceOutbound::getOperationNo);
            } else {
                queryWrapper.isNotNull(DeviceOutbound::getOperationNo);
            }
            IPage<DeviceOutbound> deviceOutboundIPage = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
            return R.data(Convert.convert(new TypeReference<IPage<DeviceOutboundVO>>() {
            }, deviceOutboundIPage));
        }
    }

    /**
     * 个人工作台审核更新工单
     *
     * @param deviceOutboundDTO 工单信息
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Integer> deskUpdateStatus(DeviceOutboundDTO deviceOutboundDTO) throws Exception {
        HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
        String receiveUnit = deviceOutboundDTO.getReceiveUnit();
        R<Dept> byId = deptClient.getById(receiveUnit);
        if (byId.isSuccess()) {
            String regionCode = byId.getData().getRegionCode();
            R<Region> byCode = regionClient.getByCode(regionCode);
            if (byCode.isSuccess()) {
                Map<String, Object> map = new HashMap<>();
                map.put("regionName", byCode.getData().getShortName());
                hussarBpmDTO.setVariable(map);
            }
            deviceOutboundDTO.setRegionCode(regionCode);
        }
        deviceOutboundDTO.setCreateTime(null);
        deviceOutboundDTO.setCreateUser(null);
        IdevelopUser user = SecureUtil.getUser();
        DeviceOutbound deviceOutbound = baseMapper.selectById(deviceOutboundDTO.getId());
        if (Objects.isNull(deviceOutbound)) {
            return R.fail("出库工单不存在");
        }
        if (deviceOutboundDTO.getWorkerStatus() == 1) {
            return R.fail("出库工单无法进行驳回");
        }

        //  获取当前节点操作角色
        List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceOutbound.getApplyNo());
        String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
        String recordStatus = deviceOutbound.getProcessStatus();
        // 更新设备库存信息
        DeviceInventory deviceInventory = deviceInventoryService.getOne(new LambdaQueryWrapper<DeviceInventory>().eq(DeviceInventory::getRegionCode, user.getRegionCode())
                .eq(DeviceInventory::getCropId, user.getCorpId()).eq(DeviceInventory::getDeviceCategory, deviceOutbound.getDeviceCategory())
                .eq(DeviceInventory::getDeviceType, deviceOutbound.getDeviceType()).eq(DeviceInventory::getWarehouse, deviceOutboundDTO.getWarehouse()).last(" FOR UPDATE"));
        int outboundNum = deviceOutboundDTO.getDeviceOperationDetailDTOList().size();
        if (Objects.isNull(deviceInventory) || deviceInventory.getInventoryNum() < outboundNum) {
            return R.fail("库存不足，无法进行出库操作");
        }
        if (!deviceInventoryService.update(new LambdaUpdateWrapper<DeviceInventory>()
                .eq(DeviceInventory::getId, deviceInventory.getId())
                .eq(DeviceInventory::getVersion, deviceInventory.getVersion())
                .set(DeviceInventory::getInventoryNum, deviceInventory.getInventoryNum() - outboundNum)
                .set(DeviceInventory::getVersion, deviceInventory.getVersion() + 1)
                .set(DeviceInventory::getUpdateTime, new Date())
                .set(DeviceInventory::getUpdateUser, user.getUserId()))) {
            return R.fail("更新库存信息失败");
        }
        deviceOutboundDTO.setOutboundTime(new Date());
        deviceOutbound.setOutboundTime(deviceOutboundDTO.getOutboundTime());
        deviceOutboundDTO.setProcessStatus(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OPERATION.getNode());
        deviceOutboundDTO.setStatus(DeviceOutboundEnum.getCode(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OPERATION.getNode()));
        // 生成投运单号
        String operationNo = orderNumberUtil.generateOrderNumber(WorkOrderTypeEnum.TY.getValue(), CacheNames.DEVICE_OPERATION_NUMBER);
        deviceOutboundDTO.setOperationNo(operationNo);
        deviceOutboundDTO.setOutboundStatus("2");
        deviceOutboundDTO.setUpdateTime(new Date());
        deviceOutboundDTO.setUpdateUser(user.getUserId());
        deviceOutboundDTO.setSubmitTime(new Date());
        // 更新工单信息
        baseMapper.updateById(deviceOutboundDTO);
        // 更新工单设备信息
        batchInsertDeviceOutboundDetail(user, deviceOutboundDTO);
        // 新增设备附件信息
        batchInsertDeviceFile(OrderFileTypeEnum.OUTBOUND.getOrderType(), deviceOutboundDTO, user);
        DeviceApply deviceApply = deviceApplyMapper.selectOne(new LambdaQueryWrapper<DeviceApply>().eq(DeviceApply::getApplyNo, deviceOutbound.getApplyNo()));
        DeviceOperation operation = Convert.convert(DeviceOperation.class, deviceOutboundDTO);
        operation.setOperationNo(operationNo);
        operation.setApplyNo(deviceOutbound.getApplyNo());
        operation.setOutboundNo(deviceOutbound.getOutboundNo());
        operation.setApplyUser(deviceApply.getApplyUser());
        operation.setApplyUserName(deviceApply.getApplyUserName());
        operation.setApplyDate(deviceApply.getApplyDate());
        operation.setSubmitTime(deviceApply.getSubmitTime());
        operation.setProcessInsId(deviceOutbound.getProcessInsId());
        operation.setProcessStatus(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OPERATION.getNode());
        operation.setStatus(DeviceApplyEnum.DEVICE_OPERATION.getCode());
        operation.setOperationType("0");
        operation.setCreateUser(user.getUserId());
        operation.setCreateDept(user.getDeptId());
        operation.setCreateTime(new Date());
        operation.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        operation.setReceiveUnit(deviceOutboundDTO.getReceiveUnit());
        operation.setReceiveUnitName(deviceOutboundDTO.getReceiveUnitName());
        operation.setOperationNum(deviceOutboundDTO.getDeviceOperationDetailDTOList().size());
        deviceOperationService.save(operation);
        // 新增设备详情信息
        batchInsertDeviceOperationDetail(user, deviceOutboundDTO, operation.getId(), deviceApply);
        // 更新申请单工单状态
        deviceApplyMapper.update(new LambdaUpdateWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApply.getId())
                .set(DeviceApply::getOperationNo, operationNo)
                .set(DeviceApply::getProcessStatus, DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OPERATION.getNode())
                .set(DeviceApply::getStatus, DeviceApplyEnum.getCode(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_OPERATION.getNode()))
                .set(DeviceApply::getUpdateTime, new Date()).set(DeviceApply::getUpdateUser, user.getUserId()));
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(deviceApply.getId()).logData(deviceOutboundDTO.toString()).params(deviceOutboundDTO.toString())
                .optType(OptTypeEnum.DEVICE_OUTBOUND.getCode()).title(deviceOutboundDTO.getComment()).optRole(roleName).time(new Date()).build());
        // 记录审核流程
        approveRecordService.commonRecord(ApproveRecord.builder().filingNo(deviceOutbound.getId()).optType(OptTypeEnum.DEVICE_OUTBOUND.getCode()).nodeId(recordStatus).optRole(roleName)
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(recordStatus)).optTitle(deviceOutboundDTO.getComment())
                .filingCode(deviceOutbound.getOutboundNo()).approveStatus(0)
                .optOpinion(deviceOutboundDTO.getComment()).build());
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOutboundDTO.getDeviceOperationDetailDTOList();
        List<DeviceOutboundDetailDTO> deviceOutboundDetailDTOList = Convert.convert(new TypeReference<List<DeviceOutboundDetailDTO>>() {
        }, deviceOperationDetailDTOList);
        // 更新CMDB设备信息
        deviceOutbound.setReceiveUnit(deviceOutboundDTO.getReceiveUnit());
        deviceOutbound.setReceiveUnitName(deviceOutboundDTO.getReceiveUnitName());
        updateCmdbDevice(deviceOutbound, deviceOutboundDetailDTOList, user, deviceApply);

        if (deviceOutboundDTO.getRegionCode().contains("3702")) {
            //青岛同步sdn
            DeviceSdnQingDao deviceSdnQingDao = new DeviceSdnQingDao();
            List<DeviceStorageSdnVO> dynamicVOArrayList = new ArrayList<>();
            for (DeviceOperationDetailDTO deviceOperationDetailDTO : deviceOperationDetailDTOList) {
                String receiveUnitName = deviceOutboundDTO.getReceiveUnitName();
                if (StringUtils.equals("国网青岛供电公司", deviceOutboundDTO.getReceiveUnitName())) {
                    receiveUnitName = "国网青岛供电公司本部";
                }
                DeviceStorageSdnVO deviceStorageSdnVO = new DeviceStorageSdnVO();
                deviceStorageSdnVO.setDeviceCode(deviceOperationDetailDTO.getDeviceCode());
                deviceStorageSdnVO.setDeviceName(deviceOperationDetailDTO.getFullName());
                deviceStorageSdnVO.setId(deviceOperationDetailDTO.getId());
                deviceStorageSdnVO.setDeviceCategory(deviceOutboundDTO.getDeviceCategoryName());
                deviceStorageSdnVO.setDeviceType(deviceOutboundDTO.getDeviceTypeName());
                deviceStorageSdnVO.setOwnerUnit(receiveUnitName);
                deviceStorageSdnVO.setDept(deviceOutboundDTO.getReceiveDutyDeptName());
                deviceStorageSdnVO.setMaker(deviceOperationDetailDTO.getMakerName());
                deviceStorageSdnVO.setBrand(deviceOperationDetailDTO.getBrandName());
                deviceStorageSdnVO.setSeries(deviceOperationDetailDTO.getSeriesName());
                deviceStorageSdnVO.setDeviceModel(deviceOperationDetailDTO.getDeviceModelName());
                deviceStorageSdnVO.setSn(deviceOperationDetailDTO.getFactoryNumber());
                String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                deviceStorageSdnVO.setOutboundTime(format);
                deviceStorageSdnVO.setAccount(user.getAccount());
                dynamicVOArrayList.add(deviceStorageSdnVO);
            }
            deviceSdnQingDao.setFlag("1");
            deviceSdnQingDao.setSparesOutDTOList(dynamicVOArrayList);
            dSwitcherSyncUtil.deviceSdnQingDao(deviceSdnQingDao);
        }
        // 发起审批流程
        hussarBpmDTO.setBusinessKey(deviceOutbound.getApplyNo());
        hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark());
        hussarBpmDTO.setParticipantType("2");
        hussarBpmDTO.setTaskType("1");
        hussarBpmService.hussarSubmit(hussarBpmDTO);
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 更新cmdb系统设备信息
     *
     * @param deviceOutbound              设备出库信息
     * @param deviceOutboundDetailDTOList 设备信息
     * @param user                        用户信息
     * @param deviceApply                 申请单信息
     */
    public void updateCmdbDevice(DeviceOutbound deviceOutbound, List<DeviceOutboundDetailDTO> deviceOutboundDetailDTOList
            , IdevelopUser user, DeviceApply deviceApply) throws Exception {
        if (CollectionUtil.isNotEmpty(deviceOutboundDetailDTOList)) {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            Map<Long, Map<String, Object>> hashMap = new HashMap<>();
            // 修改 资产台账
            deviceOutboundDetailDTOList.forEach(deviceOutboundDetailDTO -> {
                HashMap<String, Object> deviceMap = new HashMap<>();
                deviceMap.put(CmdbAttrConstant.DEVICE_CODE, deviceOutboundDetailDTO.getDeviceCode());
                deviceMap.put(CmdbAttrConstant.DEVICE_NAME, deviceOutboundDetailDTO.getDeviceName());
                deviceMap.put(CmdbAttrConstant.ASSET_CODE_ERP, deviceOutboundDetailDTO.getErpAssetCode());
                deviceMap.put(CmdbAttrConstant.SN, deviceOutboundDetailDTO.getFactoryNumber());
                deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, deviceApply.getReceiveDutyPersonName());
                deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, deviceApply.getReceiveDutyPhone());
                deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, deviceApply.getReceiveDutyCard());
                deviceMap.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, deviceApply.getReceiveDutyPhone());
                deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, deviceApply.getReceiveDutyGroupName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, deviceApply.getReceiveDutyIscAccount());
                deviceMap.put(CmdbAttrConstant.USER, deviceOutboundDetailDTO.getUserName());
                deviceMap.put(CmdbAttrConstant.USER_TEL, deviceOutboundDetailDTO.getUserPhone());
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, deviceOutboundDetailDTO.getUserCard());
                deviceMap.put(CmdbAttrConstant.INSTALLATION_SITE, deviceOutboundDetailDTO.getAddress());
                if (Objects.nonNull(deviceOutboundDetailDTO.getUserTime())) {
                    deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, DATE_FORMAT.format(deviceOutboundDetailDTO.getUserTime()));
                }
                deviceMap.put(CmdbAttrConstant.CI_ID, deviceOutboundDetailDTO.getDeviceCid());
                deviceMap.put(CmdbAttrConstant.UUID, deviceOutboundDetailDTO.getDeviceUuid());
                deviceMap.put(CmdbAttrConstant.OUT_WAREHOUSE_DATE, DATE_FORMAT.format(deviceOutbound.getOutboundTime()));
                deviceMap.put(CmdbAttrConstant.OUT_WAREHOUSE_PERSON, user.getRealName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, deviceOutbound.getReceiveUnitName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, deviceOutbound.getReceiveUnit());
                if (StringUtil.isNotBlank(deviceOutboundDetailDTO.getDeviceId())) {
                    hashMap.put(Long.parseLong(deviceOutboundDetailDTO.getDeviceId()), deviceMap);
                }
            });
            //保存cmdb
            if (hashMap.size() > 0) {
                try {
                    iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
                } catch (Exception e) {
                    log.debug("更新CMDB设备台账信息失败" + e.getMessage());
                    throw new Exception("更新设备台账信息失败");
                }
            }
        }
    }

    /**
     * 新增设备详情信息
     *
     * @param user              用户信息
     * @param deviceOutboundDTO 设备详情信息
     */
    private void batchInsertDeviceOutboundDetail(IdevelopUser user, DeviceOutboundDTO deviceOutboundDTO) {
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOutboundDTO.getDeviceOperationDetailDTOList();
        List<DeviceOutboundDetail> deviceOutboundDetailList = Convert.convert(new TypeReference<List<DeviceOutboundDetail>>() {
        }, deviceOperationDetailDTOList);
        deviceOutboundDetailList.forEach(item -> {
            item.setUpdateUser(user.getUserId());
            item.setUpdateTime(new Date());
            item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        });
        deviceOutboundDetailService.updateBatchById(deviceOutboundDetailList);
    }

    /**
     * 新增设备详情信息
     *
     * @param user              用户信息
     * @param deviceOutboundDTO 设备详情信息
     * @param operationId       投运单id
     * @param deviceApply       申请工单信息
     */
    private void batchInsertDeviceOperationDetail(IdevelopUser user, DeviceOutboundDTO deviceOutboundDTO, String operationId, DeviceApply deviceApply) {
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOutboundDTO.getDeviceOperationDetailDTOList();
        List<DeviceOperationDetail> operationDetailList = Convert.convert(new TypeReference<List<DeviceOperationDetail>>() {
        }, deviceOperationDetailDTOList);
        List<DeviceApplyDetail> deviceApplyDetailList = new ArrayList<>();
        if ("0".equals(deviceOutboundDTO.getOldToNew())) {
            deviceApplyDetailList = deviceApplyDetailMapper.selectList(new LambdaQueryWrapper<DeviceApplyDetail>().eq(DeviceApplyDetail::getApplyId, deviceApply.getId()));
        }
        int applyNum = 0;
        for (DeviceOperationDetail deviceOperationDetail : operationDetailList) {
            deviceOperationDetail.setId(null);
            deviceOperationDetail.setOperationId(operationId);
            if ("0".equals(deviceOutboundDTO.getOldToNew())) {
                DeviceApplyDetail deviceApplyDetail = deviceApplyDetailList.get(applyNum);
                deviceOperationDetail.setOldDeviceId(deviceApplyDetail.getOldDeviceId());
                deviceOperationDetail.setOldDeviceCid(deviceApplyDetail.getOldDeviceCid());
                deviceOperationDetail.setOldDeviceUuid(deviceApplyDetail.getOldDeviceUuid());
                deviceOperationDetail.setOldDeviceCode(deviceApplyDetail.getOldDeviceCode());
                deviceOperationDetail.setOldDeviceName(deviceApplyDetail.getOldDeviceName());
                deviceOperationDetail.setOldDeviceIp(deviceApplyDetail.getOldDeviceIp());
                deviceOperationDetail.setOldDeviceUser(deviceApplyDetail.getOldDeviceUser());
                deviceOperationDetail.setDeviceOldMac(deviceApplyDetail.getDeviceOldMac());
                deviceOperationDetail.setDeviceSubnet(deviceApplyDetail.getDeviceSubnet());
                deviceOperationDetail.setOldDeviceReceiveUse(deviceApply.getReceiveDutyPersonName());
                deviceOperationDetail.setDeviceSubnetName(deviceApplyDetail.getDeviceSubnetName());
                deviceOperationDetail.setDeviceIp(deviceApplyDetail.getOldDeviceIp());
                deviceOperationDetail.setOldUserPhone(deviceApplyDetail.getOldUserPhone());
                deviceOperationDetail.setOldUserCard(deviceApplyDetail.getOldUserCard());
                deviceOperationDetail.setOldAddress(deviceApplyDetail.getOldAddress());
                deviceOperationDetail.setOldUserType(deviceApplyDetail.getOldUserType());
                deviceOperationDetail.setAuthAccount(deviceApplyDetail.getAuthAccount());
                deviceOperationDetail.setAuthPassword(deviceApplyDetail.getAuthPassword());
                deviceOperationDetail.setSwitchesIp(deviceApplyDetail.getSwitchesIp());
                deviceOperationDetail.setSwitchesPassword(deviceApplyDetail.getSwitchesPassword());
                applyNum++;
            }
            deviceOperationDetail.setDeviceCategory(deviceOutboundDTO.getDeviceCategory());
            deviceOperationDetail.setDeviceType(deviceOutboundDTO.getDeviceType());
            deviceOperationDetail.setDeviceCategoryName(deviceOutboundDTO.getDeviceCategoryName());
            deviceOperationDetail.setDeviceTypeName(deviceOutboundDTO.getDeviceTypeName());
            deviceOperationDetail.setReceiveUnit(deviceOutboundDTO.getReceiveUnit());
            deviceOperationDetail.setReceiveUnitName(deviceOutboundDTO.getReceiveUnitName());
            // deviceOperationDetail.setReceiveDutyDept(deviceApply.getReceiveDutyDept());
            // deviceOperationDetail.setReceiveDutyDeptName(deviceApply.getReceiveDutyDeptName());
            deviceOperationDetail.setReceiveDutyGroup(deviceApply.getReceiveDutyGroup());
            deviceOperationDetail.setReceiveDutyGroupName(deviceApply.getReceiveDutyGroupName());
            deviceOperationDetail.setReceiveUseName(deviceApply.getReceiveDutyPersonName());
            deviceOperationDetail.setReceiveUseCard(deviceApply.getReceiveDutyCard());
            deviceOperationDetail.setReceiveUsePhone(deviceApply.getReceiveDutyPhone());
            deviceOperationDetail.setNetworkType(deviceApply.getNetworkType());
            deviceOperationDetail.setTemporaryType(deviceApply.getTemporaryType());
            deviceOperationDetail.setTemporaryStartTime(deviceApply.getTemporaryStartTime());
            deviceOperationDetail.setTemporaryEndTime(deviceApply.getTemporaryEndTime());
            deviceOperationDetail.setNetworkType(deviceApply.getNetworkType());
            deviceOperationDetail.setNetworkTypeName(deviceApply.getNetworkTypeName());
            deviceOperationDetail.setCreateUser(user.getUserId());
            deviceOperationDetail.setCreateTime(new Date());
            deviceOperationDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            //补充字段
            CiCientitySearch cientitySearch = new CiCientitySearch();
            List<CiCientitySearchVO> entity = new ArrayList<>();
            CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).expression(Expression.EQUAL).attrValue(deviceOperationDetail.getDeviceCode()).build();
            entity.add(ciCientitySearchVO);
            cientitySearch.setEntity(entity);
            cientitySearch.setQuery(new Query().setCurrent(1).setSize(10));
            cientitySearch.setFullField(Boolean.TRUE);
            Map<String, Object> entityMap = iCmdbService.getCiCientityListByCondition(cientitySearch).getData().get(0);
            deviceOperationDetail.setOsIssueVersion(String.valueOf(entityMap.get(CmdbAttrConstant.OS_ISSUE_VERSION)));
            deviceOperationDetail.setOsTypeCode(String.valueOf(entityMap.get(CmdbAttrConstant.OS_TYPE_CODE)));
            deviceOperationDetail.setHardDiskType(String.valueOf(entityMap.get(CmdbAttrConstant.HARD_DISK_TYPE_CODE)));
            deviceOperationDetail.setOsVersion(String.valueOf(entityMap.get(CmdbAttrConstant.OS_VERSION)));
            deviceOperationDetail.setFullName(String.valueOf(entityMap.get(CmdbAttrConstant.FULL_NAME)));
        }
        deviceOperationDetailService.saveBatch(operationDetailList);
    }

    /**
     * 新增设备工单附件
     *
     * @param orderType         工单类型
     * @param deviceOutboundDTO 出库单信息
     * @param user              用户信息
     */
    private void batchInsertDeviceFile(String orderType, DeviceOutboundDTO deviceOutboundDTO, IdevelopUser user) {
        deviceOrderFileService.remove(new LambdaQueryWrapper<DeviceOrderFile>().eq(DeviceOrderFile::getId, deviceOutboundDTO.getId()).eq(DeviceOrderFile::getOrderType, orderType)
                .eq(DeviceOrderFile::getOrderFileType, OrderFileTypeEnum.getFileType(orderType)));
        if (CollectionUtil.isNotEmpty(deviceOutboundDTO.getDeviceOrderFileDTOList())) {
            List<DeviceOrderFileDTO> orderFileDTOList = deviceOutboundDTO.getDeviceOrderFileDTOList()
                    .stream().filter(deviceOrderFileDTO -> orderType.equals(deviceOrderFileDTO.getOrderType())).collect(Collectors.toList());
            orderFileDTOList.forEach(item -> {
                item.setId(null);
                item.setOrderId(deviceOutboundDTO.getId());
                item.setOrderType(orderType);
                item.setOrderFileType(OrderFileTypeEnum.getFileType(orderType));
                item.setCreateTime(new Date());
                item.setCreateUser(user.getUserId());
                item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            });
            deviceOrderFileService.saveBatch(Convert.convert(new TypeReference<Collection<DeviceOrderFile>>() {
            }, orderFileDTOList));
        }
    }

    /**
     * 获取设备出库工单状态字典
     *
     * @return List
     */
    @Override
    public List<DictValueVO> deviceOutboundDict() {
        List<DictValueVO> dictValueVOList = new ArrayList<>();
        DeviceOutboundEnum[] values = DeviceOutboundEnum.values();
        for (DeviceOutboundEnum deviceOutboundEnum : values) {
            dictValueVOList.add(DictValueVO.builder().node(deviceOutboundEnum.getCode().toString()).nodeName(deviceOutboundEnum.getMessage())
                    .sort(deviceOutboundEnum.getSort()).type(deviceOutboundEnum.getType()).build());
        }
        return dictValueVOList.stream().sorted(Comparator.comparing(DictValueVO::getSort)).collect(Collectors.toList());
    }

    /**
     * 工单审核之前校验设备信息
     *
     * @param deviceOutboundDTO 工单信息
     * @return R
     */
    @Override
    public R<Integer> checkDeviceOperation(DeviceOutboundDTO deviceOutboundDTO) {
        IdevelopUser user = SecureUtil.getUser();
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOutboundDTO.getDeviceOperationDetailDTOList();
        if (CollectionUtil.isEmpty(deviceOperationDetailDTOList)) {
            return R.fail("设备信息不能为空");
        }
        List<DeviceOperationDetailDTO> collect = deviceOperationDetailDTOList.stream().filter(deviceOperationDetailDTO ->
                StringUtil.isBlank(deviceOperationDetailDTO.getDeviceId())).collect(Collectors.toList());
        if (collect.size() > 0) {
            return R.fail("请完善出库设备信息");
        }
        Map<String, List<DeviceOperationDetailDTO>> listMap = deviceOperationDetailDTOList.stream().collect(Collectors.groupingBy(DeviceOperationDetailDTO::getDeviceId));
        if (listMap.size() != deviceOperationDetailDTOList.size()) {
            return R.fail("请勿选择重复设备");
        }
        List<DeviceOutbound> deviceOutboundList = baseMapper.selectList(new LambdaQueryWrapper<DeviceOutbound>()
                .eq(DeviceOutbound::getRegionCode, user.getRegionCode()).ne(DeviceOutbound::getId, deviceOutboundDTO.getId())
                .ne(DeviceOutbound::getStatus, DeviceOutboundEnum.FINISH.getCode()).select(DeviceOutbound::getId));
        if (CollectionUtils.isNotEmpty(deviceOutboundList)) {
            List<DeviceOutboundDetail> list = deviceOutboundDetailService.list(new LambdaQueryWrapper<DeviceOutboundDetail>()
                    .in(DeviceOutboundDetail::getOutboundId, deviceOutboundList.stream().map(DeviceOutbound::getId).collect(Collectors.toList()))
                    .in(DeviceOutboundDetail::getDeviceId, deviceOutboundDTO.getDeviceOperationDetailDTOList().stream().map(DeviceOperationDetail::getDeviceId).collect(Collectors.toList())));
            if (list.size() > 0) {
                return R.fail(list.get(0).getDeviceCode() + "设备已出库，请重新选择");
            }
        }
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 详情
     *
     * @param deviceOutboundDTO 出库信息
     * @return R
     */
    @Override
    public R<DeviceOutboundVO> detail(DeviceOutboundDTO deviceOutboundDTO) {
        // 获取设备出库信息数据
        DeviceOutbound deviceOutbound = baseMapper.selectOne(new LambdaQueryWrapper<DeviceOutbound>()
                .eq(StringUtils.isNotBlank(deviceOutboundDTO.getOutboundNo()), DeviceOutbound::getOutboundNo, deviceOutboundDTO.getOutboundNo())
                .eq(Objects.nonNull(deviceOutboundDTO.getId()), DeviceOutbound::getId, deviceOutboundDTO.getId())
                .eq(DeviceOutbound::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        DeviceOutboundVO deviceOutboundVO = Convert.convert(DeviceOutboundVO.class, deviceOutbound);
        if (Objects.nonNull(deviceOutboundVO)) {
            // 获取设备信息
            List<DeviceOutboundDetail> deviceOutboundDetailList = deviceOutboundDetailService.list(new LambdaQueryWrapper<DeviceOutboundDetail>()
                    .in(DeviceOutboundDetail::getOutboundId, deviceOutbound.getId()).eq(DeviceOutboundDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            deviceOutboundVO.setDeviceOutboundDetailVOList(Convert.convert(new TypeReference<List<DeviceOutboundDetailVO>>() {
            }, deviceOutboundDetailList));
            // 获取附件信息
            List<DeviceOrderFile> deviceOrderFileVOList = deviceOrderFileService.list(new LambdaQueryWrapper<DeviceOrderFile>().eq(DeviceOrderFile::getOrderId, deviceOutbound.getId())
                    .eq(DeviceOrderFile::getOrderType, OrderFileTypeEnum.OUTBOUND.getOrderType())
                    .eq(DeviceOrderFile::getOrderFileType, OrderFileTypeEnum.getFileType(OrderFileTypeEnum.OUTBOUND.getOrderType())));
            deviceOutboundVO.setDeviceOrderFileVOList(Convert.convert(new TypeReference<List<DeviceOrderFileVO>>() {
            }, deviceOrderFileVOList));
        }
        return R.data(deviceOutboundVO);
    }

    @Override
    public DeviceInventoryDTO getOutWarehouse(String startDate, String endDate, String warehouse) {
        return baseMapper.getOutWarehouse(startDate, endDate, warehouse);
    }
}

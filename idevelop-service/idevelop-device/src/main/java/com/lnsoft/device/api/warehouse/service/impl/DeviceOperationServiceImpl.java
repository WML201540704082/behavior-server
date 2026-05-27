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
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.lnsoft.core.tool.utils.DateUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.entity.SdnQingDaoNetWork;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessIppoolMapper;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSubnetMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.api.warehouse.dto.DeviceOperationDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceOperationDetailDTO;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.entity.*;
import com.lnsoft.device.api.warehouse.mapper.DeviceApplyMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOperationMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOutboundMapper;
import com.lnsoft.device.api.warehouse.service.*;
import com.lnsoft.device.api.warehouse.vo.*;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import com.lnsoft.device.dto.SafeaccessSwitcheDTO;
import com.lnsoft.device.entity.*;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.publisher.QDDSwitcherPublisher;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备投运表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@Service
public class DeviceOperationServiceImpl extends BaseServiceImpl<DeviceOperationMapper, DeviceOperation> implements IDeviceOperationService {

    @Resource
    private DeviceApplyMapper deviceApplyMapper;
    @Resource
    private DeviceOutboundMapper deviceOutboundMapper;
    @Resource
    private OrderNumberUtil orderNumberUtil;
    @Resource
    private ILogOptService logOptService;
    @Resource
    private IApproveRecordService approveRecordService;
    @Resource
    private IHussarBpmService hussarBpmService;
    @Resource
    private IDeviceOperationDetailService operationDetailService;
    @Resource
    private IDeviceOrderFileService deviceOrderFileService;
    @Resource
    private ICmdbService iCmdbService;
    @Resource
    private IDeviceReturnedService deviceReturnedService;
    @Resource
    private IDeviceReturnedDetailService deviceReturnedDetailService;
    @Resource
    private ISafeaccessSwitcheService safeAccessSwitchesService;
    @Resource
    private ISafeaccessUserAccessService userAccessService;
    @Resource
    private SafeaccessIppoolMapper safeaccessIppoolMapper;
    @Resource
    private IDSwitcherSyncService idSwitcherSyncService;
    @Resource
    private CmdbCientityProperties modelProperties;
    @Resource
    private IDeviceInventoryService deviceInventoryService;
    @Resource
    private II6000Service i6000Service;
    @Resource
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private CmdbCientityProperties cmdbCientityProperties;
    @Resource
    private SafeaccessSubnetMapper safeaccessSubnetMapper;

    /**
     * 新增/修改设备投运
     *
     * @param deviceOperationDTO 投运信息
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<DeviceOperationVO> insertOperation(DeviceOperationDTO deviceOperationDTO) {
        IdevelopUser user = SecureUtil.getUser();
        DeviceOperation deviceOperation = Convert.convert(DeviceOperation.class, deviceOperationDTO);
        deviceOperation.setOperationType("1");
        deviceOperation.setOperation("1");
        deviceOperation.setOldToNew("1");
        deviceOperation.setOperationNum(deviceOperationDTO.getDeviceOperationDetailDTOList().size());
        if (Objects.isNull(deviceOperation.getId())) {
            String operationNo = orderNumberUtil.generateOrderNumber(WorkOrderTypeEnum.TY.getValue(), CacheNames.DEVICE_OPERATION_NUMBER);
            deviceOperation.setReceiveUnit(user.getCorpId());
            deviceOperation.setReceiveUnitName(user.getExt().get("corpFullName").toString());
            deviceOperation.setReceiveDutyDept(user.getDeptId());
            deviceOperation.setReceiveDutyDeptName(user.getDeptName());
            deviceOperation.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            deviceOperation.setStatus(DeviceOperationEnum.TEMPORARILY.getCode());
            deviceOperation.setOperationNo(operationNo);
            deviceOperation.setCreateDept(user.getDeptId());
            deviceOperation.setCreateTime(new Date());
            deviceOperation.setCreateUser(user.getUserId());
            deviceOperation.setRegionCode(user.getRegionCode());
            baseMapper.insert(deviceOperation);
            deviceOperationDTO.setId(deviceOperation.getId());
            // 增加操作记录
            logOptService.commonLogOpt(LogOpt.builder().logId(deviceOperation.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString())
                    .optRole("--").optType(OptTypeEnum.DEVICE_APPLY.getCode()).title("新增暂存设备投运").build());
        } else {
            DeviceOperation selectOne = baseMapper.selectOne(new LambdaQueryWrapper<DeviceOperation>().eq(DeviceOperation::getId, deviceOperationDTO.getId())
                    .eq(DeviceOperation::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            if (Objects.isNull(selectOne) || !(DeviceOperationEnum.TEMPORARILY.getCode().equals(selectOne.getStatus()) || DeviceOperationEnum.DEVICE_APPLY.getCode().equals(selectOne.getStatus()))) {
                return R.fail("当前工单不允许修改");
            }
            deviceOperation.setUpdateTime(new Date());
            deviceOperation.setUpdateUser(user.getUserId());
            baseMapper.updateById(deviceOperation);
            // 删除设备投运单设备详情数据
            operationDetailService.remove(new LambdaQueryWrapper<DeviceOperationDetail>().eq(DeviceOperationDetail::getOperationId, deviceOperationDTO.getId()));
            // 增加操作记录
            logOptService.commonLogOpt(LogOpt.builder().logId(deviceOperation.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString())
                    .optRole("--").optType(OptTypeEnum.DEVICE_APPLY.getCode()).title("修改暂存设备投运").build());
        }
        // 新增设备工单附件
        batchInsertDeviceFile(OrderFileTypeEnum.OPERATION.getOrderType(), deviceOperationDTO);
        // 更新投运设备信息
        batchInsertDeviceOperationDetail(deviceOperationDTO, user);
        return R.data(Convert.convert(DeviceOperationVO.class, deviceOperation));
    }

    /**
     * 提交设备投运
     *
     * @param deviceOperationDTO 投运信息
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<DeviceOperationVO> submitOperation(DeviceOperationDTO deviceOperationDTO) throws Exception {
        IdevelopUser user = SecureUtil.getUser();
        DeviceOperation deviceOperation = Convert.convert(DeviceOperation.class, deviceOperationDTO);
        deviceOperation.setSubmitTime(new Date());
        deviceOperation.setStatus(DeviceOperationEnum.DEVICE_APPLY.getCode());
        deviceOperation.setProcessStatus(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode());
        deviceOperation.setOperationNum(deviceOperationDTO.getDeviceOperationDetailDTOList().size());
        // 判断是新增还是修改
        if (Objects.isNull(deviceOperationDTO.getId())) {
            String operationNo = orderNumberUtil.generateOrderNumber(WorkOrderTypeEnum.TY.getValue(), CacheNames.DEVICE_OPERATION_NUMBER);
            deviceOperation.setOperationType("1");
            deviceOperation.setOperation("1");
            deviceOperation.setOldToNew("1");
            deviceOperation.setReceiveUnit(user.getCorpId());
            deviceOperation.setReceiveUnitName(user.getExt().get("corpFullName").toString());
            deviceOperation.setReceiveDutyDept(user.getDeptId());
            deviceOperation.setReceiveDutyDeptName(user.getDeptName());
            deviceOperation.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            deviceOperation.setOperationNo(operationNo);
            deviceOperation.setCreateDept(user.getDeptId());
            deviceOperation.setCreateTime(new Date());
            deviceOperation.setCreateUser(user.getUserId());
            deviceOperation.setRegionCode(user.getRegionCode());
            baseMapper.insert(deviceOperation);
            deviceOperationDTO.setId(deviceOperation.getId());
        } else {
            Long count = baseMapper.selectCount(new LambdaQueryWrapper<DeviceOperation>().eq(DeviceOperation::getId, deviceOperationDTO.getId())
                    .eq(DeviceOperation::getStatus, DeviceOperationEnum.TEMPORARILY.getCode()).eq(DeviceOperation::getOperationType, 1)
                    .eq(DeviceOperation::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            if (count == 0) {
                return R.fail("当前工单不允许提交审批");
            }
            deviceOperation.setUpdateTime(new Date());
            deviceOperation.setUpdateUser(user.getUserId());
            baseMapper.updateById(deviceOperation);
            // 删除设备投运单设备详情数据
            operationDetailService.remove(new LambdaQueryWrapper<DeviceOperationDetail>().eq(DeviceOperationDetail::getOperationId, deviceOperationDTO.getId()));
        }
        // 新增设备工单附件
        batchInsertDeviceFile(OrderFileTypeEnum.OPERATION.getOrderType(), deviceOperationDTO);
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(deviceOperation.getId()).logData(deviceOperation.toString()).params(deviceOperationDTO.toString())
                .optRole("--").optType(OptTypeEnum.DEVICE_OPERATION.getCode()).title("发起设备投运").build());
        // 记录审核流程
        approveRecordService.commonRecord(ApproveRecord.builder().filingNo(deviceOperation.getId())
                .optRole("--").optType(OptTypeEnum.DEVICE_OPERATION.getCode())
                .nodeId(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode())
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode()))
                .approveStatus(0).filingCode(deviceOperation.getOperationNo())
                .optTitle("发起设备投运").optOpinion("发起设备投运").build());
        // 增加流程归档操作和审核记录流程
        LogOpt logOpt = LogOpt.builder().logId(deviceOperationDTO.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString()).optRole("--")
                .optType(OptTypeEnum.DEVICE_OPERATION.getCode()).title(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getMessage()).optName("系统").build();
        logOpt.setStatus(0);
        logOptService.commonLogOpt(logOpt);
        // 记录审核流程
        ApproveRecord approveRecord = ApproveRecord.builder().filingNo(deviceOperationDTO.getId()).optType(OptTypeEnum.DEVICE_OPERATION.getCode())
                .nodeId(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
                .optTitle(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
                .optOpinion(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
                .approveStatus(0).filingCode(deviceOperation.getOperationNo())
                .optName("系统").optRole("--").build();
        approveRecord.setStatus(1);
        approveRecordService.commonRecord(approveRecord);
        List<String> switchesTypeList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
        // 新增投运设备列表信息 （放在最后执行是因为在新增CMDB之后需要回填设备列表的信息）
        deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
            if (modelProperties.getT105().equals(item.getDeviceCategory())
                /*|| (switchesTypeList.contains(item.getDeviceType()) && modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))*/) {
                item.setDeviceSource(modelProperties.getNoDeviceSource());
                item.setDeviceCode(orderNumberUtil.generateCode(item.getDeviceType()));
                item.setDeviceName(orderNumberUtil.getDeviceName(item.getDeviceType()));
                String authUser = orderNumberUtil.generateDeviceAuthUser(user.getRegionCode(), CacheNames.OPERATION_AUTH_USER);
                item.setAuthAccount(authUser);
                try {
                    item.setAuthPassword(String.valueOf(SecureRandom.getInstance(Constants.SECURE_RANDOM).nextInt(900000) + 100000).substring(0, 6));
                } catch (NoSuchAlgorithmException e) {
                    log.debug("生成设备密码错误：" + e.getMessage());
                }
            }
        });
        batchInsertDeviceOperationDetail(deviceOperationDTO, user);
        // 同步CMDB系统
        insertCmdbDevice(deviceOperationDTO, user);
        // 新增用户入网数据和IP地址池校验
        batchInsertUserAccess(deviceOperationDTO.getDeviceOperationDetailDTOList(), user, deviceOperationDTO, null, switchesTypeList);
        operationDetailService.updateBatchById(Convert.convert(new TypeReference<Collection<DeviceOperationDetail>>() {
        }, deviceOperationDTO.getDeviceOperationDetailDTOList()));
        DeviceOperationVO operationVO = Convert.convert(DeviceOperationVO.class, deviceOperation);
        operationVO.setDeviceOperationDetailVOList(Convert.convert(new TypeReference<List<DeviceOperationDetailVO>>() {
        }, deviceOperationDTO.getDeviceOperationDetailDTOList()));
        // 设备投运推送数据同步服务
        List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
        List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();
        LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>()
                .likeRight(SafeaccessSubnet::getRegionCode, deviceOperation.getRegionCode())
                .in(SafeaccessSubnet::getId, deviceOperationDTO.getDeviceOperationDetailDTOList().stream().map(DeviceOperationDetailDTO::getDeviceSubnet).distinct().collect(Collectors.toList()));
        List<SafeaccessSubnet> safeAccessSubnetList = safeaccessSubnetMapper.selectList(queryWrapper);
        deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
            if (item.getNetworkType().equals(cmdbCientityProperties.getNetwork3())) {
                return;
            }
            if (modelProperties.getT105().equals(item.getDeviceCategory())
                    || (switchesTypeList.contains(item.getDeviceType()) && modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))
                    || modelProperties.getT107().equals(item.getDeviceCategory()) || modelProperties.getT10101().equals(item.getDeviceType())) {
                switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                        .deviceCategory(item.getDeviceCategory())
                        .deviceType(item.getDeviceType())
                        .deviceIp(item.getDeviceIp())
                        .deviceMac(item.getDeviceMac())
                        .authAccount(item.getAuthAccount())
                        .authPassword(item.getAuthPassword())
                        .deviceSubnet(item.getDeviceSubnet())
                        .switchesIp(item.getSwitchesIp())
                        .switchesPassword(item.getSwitchesPassword())
                        .deviceCode(item.getDeviceCode())
                        .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                        Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                        .build());
                if (modelProperties.getT105().equals(item.getDeviceCategory())) {
                    String receiveUnitName = item.getReceiveUnitName();
                    String deviceId = DeviceConstant.DEVICE_TYPE_CODE_MAP.get(item.getDeviceType());
                    if (StringUtils.equals("国网青岛供电公司", item.getReceiveUnitName())) {
                        receiveUnitName = "国网青岛供电公司本部";
                        deviceId = item.getDeviceTypeName();
                    }
                    SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                            .company(receiveUnitName)
                            .department(item.getReceiveDutyDeptName())
                            .address(item.getAddress())
                            .phone(item.getUserPhone())
                            .deviceId(StringUtils.isEmpty(deviceId) ? "58" : deviceId)
                            .subnetId(item.getDeviceSubnet())
                            .authUser(item.getAuthAccount())
                            .authPassword(item.getAuthPassword())
                            .macAddress(item.getDeviceMac())
                            .ipAddress(item.getDeviceIp())
                            .sbbm(item.getDeviceCode())
                            // 如果非临时使用，入网时间是当前时间，如果是临时使用，入网时间是开始时间
                            .startTime(Objects.nonNull(item.getTemporaryType()) && item.getTemporaryType() == 0 ?
                                    DATE_FORMAT_TIME.format(item.getTemporaryStartTime()) + " 00:00:00" : DATE_FORMAT_TIME.format(new Date()))
                            // 如果非临时使用，允许入网时长默认0，如果是临时使用，允许入网时长通过时间计算
                            .allowDays(Objects.nonNull(item.getTemporaryType()) && item.getTemporaryType() == 0 ?
                                    String.valueOf((item.getTemporaryEndTime().getTime() - item.getTemporaryStartTime().getTime()) / (24 * 60 * 60 * 1000)) : "0")
                            .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                    Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                            Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                            .fullUserName(item.getUserName())
                            .isAccess("0")
                            .syncTime(DATE_FORMAT_TIME.format(new Date()))
                            .syncSign("A")
                            .readState("0")
                            .dataFrom("0")
                            .sourcr("02")
                            .deviceName(item.getFullName())
                            .factoryNumber(item.getFactoryNumber())
                            .makerName(item.getMakerName())
                            .brandName(item.getBrandName())
                            .seriesName(item.getSeriesName())
                            .deviceModelName(item.getDeviceModelName())
                            .vlanId(safeAccessSubnetList.stream().filter(safeAccessSubnet ->
                                    safeAccessSubnet.getId().equals(item.getDeviceSubnet())).findFirst().orElse(new SafeaccessSubnet()).getVlanId())
                            .region(deviceOperation.getRegionCode().length() > 4 ? deviceOperation.getRegionCode().substring(0, 4) : deviceOperation.getRegionCode())
                            .build());
                }
            }
        });
        if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
            System.out.println("switcherDeviceListDTOList" + JSONObject.toJSONString(switcherDeviceListDTOList));
            idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, user.getRegionCode(), "0");
        }
        if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
            System.out.println("deviceSdnUserAccessList" + JSONObject.toJSONString(deviceSdnUserAccessList));
            idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
        }
        return R.data(operationVO);
    }

    /**
     * 提交设备投运 data
     *
     * @param deviceOperationDTO 投运信息
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<DeviceOperationVO> submitOperationData(DeviceOperationDTO deviceOperationDTO, IdevelopUser user) throws Exception {
        DeviceOperation deviceOperation = Convert.convert(DeviceOperation.class, deviceOperationDTO);
        deviceOperation.setSubmitTime(new Date());
        deviceOperation.setStatus(DeviceOperationEnum.FINISH.getCode());
        deviceOperation.setProcessStatus(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode());
        deviceOperation.setOperationNum(deviceOperationDTO.getDeviceOperationDetailDTOList().size());
        String operationNo = orderNumberUtil.generateOrderNumberData(WorkOrderTypeEnum.TY.getValue(), CacheNames.DEVICE_OPERATION_NUMBER, user);
        deviceOperation.setOperationType("0");
        deviceOperation.setOperation("1");
        deviceOperation.setOldToNew("1");
        deviceOperation.setReceiveUnit(user.getCorpId());
        deviceOperation.setReceiveUnitName(user.getExt().get("corpFullName").toString());
        deviceOperation.setReceiveDutyDept(user.getDeptId());
        deviceOperation.setReceiveDutyDeptName(user.getDeptName());
        deviceOperation.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        deviceOperation.setOperationNo(operationNo);
        deviceOperation.setCreateDept(user.getDeptId());
        deviceOperation.setCreateTime(new Date());
        deviceOperation.setCreateUser(user.getUserId());
        deviceOperation.setRegionCode(user.getRegionCode());
        baseMapper.insert(deviceOperation);
        deviceOperationDTO.setId(deviceOperation.getId());
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(deviceOperation.getId()).logData(deviceOperation.toString()).params(deviceOperationDTO.toString())
                .optRole("--").optType(OptTypeEnum.DEVICE_OPERATION.getCode()).title("发起设备投运").user(user).build());
        // 记录审核流程
        approveRecordService.commonRecord(ApproveRecord.builder().filingNo(deviceOperation.getId())
                .optRole("--").optType(OptTypeEnum.DEVICE_OPERATION.getCode())
                .nodeId(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode())
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY.getNode()))
                .filingCode(deviceOperation.getOperationNo()).approveStatus(0).optOpinion("发起设备投运")
                .optTitle("发起设备投运").user(user).build());
        // 增加流程归档操作和审核记录流程
        LogOpt logOpt = LogOpt.builder().logId(deviceOperationDTO.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString()).optRole("--")
                .optType(OptTypeEnum.DEVICE_OPERATION.getCode()).title(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getMessage()).optName("系统").user(user).build();
        logOpt.setStatus(0);
        logOptService.commonLogOpt(logOpt);
        // 记录审核流程
        ApproveRecord approveRecord = ApproveRecord.builder().filingNo(deviceOperationDTO.getId()).optType(OptTypeEnum.DEVICE_OPERATION.getCode())
                .nodeId(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
                .optTitle(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
                .optOpinion(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode()))
                .approveStatus(0).filingCode(deviceOperation.getOperationNo())
                .optName("系统").optRole("--").user(user).build();
        approveRecord.setStatus(1);
        approveRecordService.commonRecordDate(approveRecord, user);
        // 手动判断，如果是终端设备，根据ip和当前地市查询IP地址池管理，根据查询结果判断所属网络，所属子网进行赋值，如果没有获取，直接结束，如果获取到，进行用户入网和sdn推送等
        List<String> switchesTypeList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
        batchInsertDeviceOperationDetail(deviceOperationDTO, user);
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOperationDTO.getDeviceOperationDetailDTOList();
        DeviceOperationDetailDTO deviceOperationDetailDTO = deviceOperationDetailDTOList.get(0);
        deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
            item.setDeviceSource(modelProperties.getNoDeviceSource());
            item.setDeviceCode(orderNumberUtil.generateCodeData(deviceOperationDetailDTO.getDeviceType(), user));
            item.setDeviceName(orderNumberUtil.getDeviceName(deviceOperationDetailDTO.getDeviceType()));
            if (modelProperties.getT105().equals(deviceOperationDetailDTO.getDeviceCategory())) {
                String authUser = orderNumberUtil.generateDeviceAuthUser(user.getRegionCode(), CacheNames.OPERATION_AUTH_USER);
                item.setAuthAccount(authUser);
                try {
                    item.setAuthPassword(String.valueOf(SecureRandom.getInstance(Constants.SECURE_RANDOM).nextInt(900000) + 100000).substring(0, 6));
                } catch (NoSuchAlgorithmException e) {
                    log.debug("生成设备密码错误：" + e.getMessage());
                }
            }
        });
        if (modelProperties.getT105().equals(deviceOperationDetailDTO.getDeviceCategory())) {
            deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
                // 根据ip和所属地市判断当前设备所属子网
                if (deviceOperationDTO.getRegionCode().length() >= 4) {
                    String updateSurface = Constants.IP_POOL + deviceOperationDTO.getRegionCode().substring(0, 4);
                    SafeaccessIppool safeaccessIpPool = safeaccessIppoolMapper.selectIpPoolOne(updateSurface, deviceOperationDetailDTO.getDeviceIp());
                    if (Objects.nonNull(safeaccessIpPool)) {
                        SafeaccessSubnet safeaccessSubnet = safeaccessSubnetMapper.selectOne(new LambdaQueryWrapper<SafeaccessSubnet>()
                                .eq(SafeaccessSubnet::getRegionCode, user.getRegionCode()).eq(SafeaccessSubnet::getId, safeaccessIpPool.getSubnet()));
                        if (Objects.nonNull(safeaccessSubnet)) {
                            item.setDeviceSubnet(safeaccessSubnet.getId());
                            item.setDeviceSubnetName(safeaccessSubnet.getSubnetName());
                            item.setNetworkType(safeaccessSubnet.getNetworkType());
                            item.setNetworkTypeName(safeaccessSubnet.getNetworkTypeName());
                        }
                    }
                }
            });
        }
        // 同步CMDB系统
        insertCmdbDevice(deviceOperationDTO, user);
        operationDetailService.saveOrUpdateBatch(Convert.convert(new TypeReference<Collection<DeviceOperationDetail>>() {
        }, deviceOperationDTO.getDeviceOperationDetailDTOList()));
        DeviceOperationVO operationVO = Convert.convert(DeviceOperationVO.class, deviceOperation);
        operationVO.setDeviceOperationDetailVOList(Convert.convert(new TypeReference<List<DeviceOperationDetailVO>>() {
        }, deviceOperationDTO.getDeviceOperationDetailDTOList()));
        long count = deviceOperationDTO.getDeviceOperationDetailDTOList().stream()
                .filter(operationDetailDTO -> StringUtils.isNotBlank(operationDetailDTO.getDeviceSubnet()))
                .map(DeviceOperationDetailDTO::getDeviceSubnet).count();
        if (count == 0) {
            return R.data(operationVO);
        }
        // 新增用户入网数据和IP地址池校验
        batchInsertUserAccess(deviceOperationDTO.getDeviceOperationDetailDTOList(), user, deviceOperationDTO, null, switchesTypeList);
        // 设备投运推送数据同步服务
        List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
        // List<String> deviceTypeList = modelProperties.getModelIdList(Constants.DEVICE_SAFE_ACCESS_TYPE);
        List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();
        LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>()
                .likeRight(SafeaccessSubnet::getRegionCode, deviceOperation.getRegionCode())
                .in(SafeaccessSubnet::getId, deviceOperationDTO.getDeviceOperationDetailDTOList().stream().map(DeviceOperationDetailDTO::getDeviceSubnet).distinct().collect(Collectors.toList()));
        List<SafeaccessSubnet> safeAccessSubnetList = safeaccessSubnetMapper.selectList(queryWrapper);
        deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
            if (item.getNetworkType().equals(cmdbCientityProperties.getNetwork3())) {
                return;
            }
            if (modelProperties.getT105().equals(item.getDeviceCategory())
                    || (switchesTypeList.contains(item.getDeviceType()) && modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))
                    || modelProperties.getT107().equals(item.getDeviceCategory()) || modelProperties.getT10101().equals(item.getDeviceType())) {
                switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                        .deviceCategory(item.getDeviceCategory())
                        .deviceType(item.getDeviceType())
                        .deviceIp(item.getDeviceIp())
                        .deviceMac(item.getDeviceMac())
                        .authAccount(item.getAuthAccount())
                        .authPassword(item.getAuthPassword())
                        .deviceSubnet(item.getDeviceSubnet())
                        .switchesIp(item.getSwitchesIp())
                        .switchesPassword(item.getSwitchesPassword()).deviceCode(item.getDeviceCode())
                        .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" : Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" : Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                        .build());
                if (modelProperties.getT105().equals(item.getDeviceCategory()) || modelProperties.getT107().equals(item.getDeviceCategory()) || modelProperties.getT10101().equals(item.getDeviceType())) {
                    String receiveUnitName = item.getReceiveUnitName();
                    String deviceId = DeviceConstant.DEVICE_TYPE_CODE_MAP.get(item.getDeviceType());
                    if (StringUtils.equals("国网青岛供电公司", item.getReceiveUnitName())) {
                        receiveUnitName = "国网青岛供电公司本部";
                        deviceId = item.getDeviceTypeName();
                    }
                    SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                            .company(receiveUnitName)
                            .department(item.getReceiveDutyDeptName())
                            .address(item.getAddress())
                            .phone(item.getUserPhone())
                            .deviceId(StringUtils.isEmpty(deviceId) ? "58" : deviceId)
                            .subnetId(item.getDeviceSubnet())
                            .authUser(item.getAuthAccount())
                            .authPassword(item.getAuthPassword())
                            .macAddress(item.getDeviceMac())
                            .ipAddress(item.getDeviceIp())
                            .sbbm(item.getDeviceCode())
                            // 如果非临时使用，入网时间是当前时间，如果是临时使用，入网时间是开始时间
                            .startTime(Objects.nonNull(item.getTemporaryType()) && item.getTemporaryType() == 0 ?
                                    DATE_FORMAT_TIME.format(item.getTemporaryStartTime()) + " 00:00:00" : DATE_FORMAT_TIME.format(new Date()))
                            // 如果非临时使用，允许入网时长默认0，如果是临时使用，允许入网时长通过时间计算
                            .allowDays(Objects.nonNull(item.getTemporaryType()) && item.getTemporaryType() == 0 ?
                                    String.valueOf((item.getTemporaryEndTime().getTime() - item.getTemporaryStartTime().getTime()) / (24 * 60 * 60 * 1000)) : "0")
                            .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" : Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" : Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                            .fullUserName(item.getUserName())
                            .isAccess("0")
                            .syncTime(DATE_FORMAT_TIME.format(new Date()))
                            .syncSign("A")
                            .readState("0")
                            .dataFrom("0")
                            .sourcr("01")
                            .vlanId(safeAccessSubnetList.stream().filter(safeAccessSubnet ->
                                    safeAccessSubnet.getId().equals(item.getDeviceSubnet())).findFirst().orElse(new SafeaccessSubnet()).getVlanId())
                            .region(deviceOperation.getRegionCode().length() > 4 ? deviceOperation.getRegionCode().substring(0, 4) : deviceOperation.getRegionCode())
                            .deviceName(item.getFullName())
                            .factoryNumber(item.getFactoryNumber())
                            .makerName(item.getMakerName())
                            .brandName(item.getBrandName())
                            .seriesName(item.getSeriesName())
                            .deviceModelName(item.getDeviceModelName())
                            .build());
                }
            }
        });
        if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
            idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, user.getRegionCode(), "0");
        }
        if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
            idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
        }
        //青岛sdn同步
        if ((deviceOperationDTO.getRegionCode().contains("3702") && CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) && switchesTypeList.contains(deviceOperation.getDeviceType())) {
            DeviceSdnQingDao deviceSdnQingDao = new DeviceSdnQingDao();
            deviceSdnQingDao.setFlag("3");
            List<SdnQingDaoNetWork> deviceOperationDTOS = new ArrayList<>();
            List<SafeaccessSwitcheDTO> safeaccessSwitcheList = new ArrayList<>();
            String corpName = (String) user.getExt().get("corpFullName");
            if (StringUtils.equals("国网青岛供电公司", corpName)) {
                corpName = "国网青岛供电公司本部";
            }
            Map<Object, Object> dictMapByCiId = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getNetworkDeviceType());
            SdnQingDaoNetWork sdnQingDaoNetWork = SdnQingDaoNetWork.builder().id(deviceOperationDTO.getId()).applyDate(String.valueOf(deviceOperationDTO.getApplyDate()))
                    .applyNo(deviceOperationDTO.getApplyNo())
                    .applyUser(deviceOperationDTO.getApplyUser())
                    .applyUserName(deviceOperationDTO.getApplyUserName())
                    .createDept(deviceOperationDTO.getCreateDept())
                    .deviceCategory(deviceOperationDTO.getDeviceCategory())
                    .deviceCategoryName(deviceOperationDTO.getDeviceCategoryName())
                    .deviceType(deviceOperationDTO.getDeviceType())
                    .deviceTypeName(deviceOperationDTO.getDeviceTypeName())
                    .operation(deviceOperationDTO.getOperation())
                    .operationDept(deviceOperationDTO.getOperationDept())
                    .operationNo(deviceOperationDTO.getOperationNo())
                    .operationDeptName(deviceOperationDTO.getOperationDeptName())
                    .operationUnitName(corpName)
                    .regionCode(deviceOperationDTO.getRegionCode())
                    .sourcr("单位自购")
                    .operationPhone(deviceOperationDTO.getOperationPhone()).build();
            String finalCorpName = corpName;
            deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
                SafeaccessSwitcheDTO safeaccessSwitcheDTO = new SafeaccessSwitcheDTO();
                safeaccessSwitcheDTO.setSwName(item.getDeviceName());
                safeaccessSwitcheDTO.setSwMaker(item.getMakerName());
                safeaccessSwitcheDTO.setSwFirm(item.getBrandName());
                safeaccessSwitcheDTO.setSwSeries(item.getSeriesName());
                safeaccessSwitcheDTO.setSwModel(item.getDeviceModelName());
                safeaccessSwitcheDTO.setSwPurpose(item.getPurpose());
                safeaccessSwitcheDTO.setSwState("运行");
                safeaccessSwitcheDTO.setSwWhere(item.getAddress());
                safeaccessSwitcheDTO.setCompany(item.getOwnerUnit());
                safeaccessSwitcheDTO.setSwIp(item.getSwitchesIp());
                safeaccessSwitcheDTO.setSwPass(item.getSwitchesPassword());
                safeaccessSwitcheDTO.setAuthConfig(item.getIs802());
                safeaccessSwitcheDTO.setIs3(dictMapByCiId.get(item.getNetworkDeviceType()).toString());
                safeaccessSwitcheDTO.setPortsCount(item.getPortsCount());
                safeaccessSwitcheDTO.setVlans(item.getVlanNumber());
                safeaccessSwitcheDTO.setTelIp(item.getManageIp());
                safeaccessSwitcheDTO.setTelUser(item.getManageUser());
                safeaccessSwitcheDTO.setTelPass(item.getManagePassword());
                safeaccessSwitcheDTO.setConfigPass(item.getAllocationPassword());
                safeaccessSwitcheDTO.setSnmpReadStr(item.getSnmpRead());
                safeaccessSwitcheDTO.setSnmpVersion(item.getSnmpVersion());
                safeaccessSwitcheDTO.setSnmpWriteStr(item.getSnmpWrite());
                safeaccessSwitcheDTO.setFillMan(user.getUserName());
                safeaccessSwitcheDTO.setFillDate(new Date().toString());
                safeaccessSwitcheDTO.setIsAccessSwitch(item.getIsAccess());
                safeaccessSwitcheDTO.setDeviceCode(item.getDeviceCode());
                safeaccessSwitcheDTO.setOperationId(item.getOperationId());
                safeaccessSwitcheDTO.setOrgCode(finalCorpName);
                safeaccessSwitcheList.add(safeaccessSwitcheDTO);
            });
            sdnQingDaoNetWork.setSwitchesList(safeaccessSwitcheList);
            deviceOperationDTOS.add(sdnQingDaoNetWork);
            deviceSdnQingDao.setAccessNetworkDTOList(deviceOperationDTOS);
            QDDSwitcherPublisher.publishEvent(deviceSdnQingDao);
        }
        return R.data(operationVO);
    }

    /**
     * 删除设备投运
     *
     * @param ids 投运工单id
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Integer> removeOperation(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<DeviceOperation>().in(DeviceOperation::getId, idList)
                .eq(DeviceOperation::getStatus, DeviceOperationEnum.TEMPORARILY.getCode()).eq(DeviceOperation::getOperationType, 1));
        if (count != idList.size()) {
            return R.fail("所选设备投运单不允许删除");
        }
        baseMapper.deleteBatchIds(idList);
        operationDetailService.remove(new LambdaQueryWrapper<DeviceOperationDetail>().in(DeviceOperationDetail::getOperationId, idList));
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 设备投运分页
     *
     * @param page               分页条件
     * @param deviceOperationDTO 查询参数
     * @return R
     */
    @Override
    public R<IPage<DeviceOperationVO>> selectOperationList(IPage<DeviceOperation> page, DeviceOperationDTO deviceOperationDTO) {
        IdevelopUser user = SecureUtil.getUser();
        deviceOperationDTO.setRegionCode(user.getRegionCode());
        return R.data(baseMapper.getPage(page, deviceOperationDTO));
//		LambdaQueryWrapper<DeviceOperation> queryWrapper = new LambdaQueryWrapper<DeviceOperation>().eq(DeviceOperation::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
//		queryWrapper.likeRight(DeviceOperation::getRegionCode, user.getRegionCode());
//		queryWrapper.like(StringUtil.isNotBlank(deviceOperationDTO.getOperationNo()), DeviceOperation::getOperationNo, deviceOperationDTO.getOperationNo());
//		queryWrapper.eq(Objects.nonNull(deviceOperationDTO.getOperationType()), DeviceOperation::getOperationType, deviceOperationDTO.getOperationType());
//		queryWrapper.eq(Objects.nonNull(deviceOperationDTO.getOldToNew()), DeviceOperation::getOldToNew, deviceOperationDTO.getOldToNew());
//		queryWrapper.eq(Objects.nonNull(deviceOperationDTO.getStatus()), DeviceOperation::getStatus, deviceOperationDTO.getStatus());
//		queryWrapper.ge(Objects.nonNull(deviceOperationDTO.getStartTime()), DeviceOperation::getCreateTime, deviceOperationDTO.getStartTime());
//		queryWrapper.le(Objects.nonNull(deviceOperationDTO.getEndTime()), DeviceOperation::getCreateTime, deviceOperationDTO.getEndTime());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOperationDTO.getApplyUser()), DeviceOperation::getApplyUser, deviceOperationDTO.getApplyUser());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOperationDTO.getReceiveDutyDept()), DeviceOperation::getReceiveDutyDept, deviceOperationDTO.getReceiveDutyDept());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOperationDTO.getReceiveUnit()), DeviceOperation::getReceiveUnit, deviceOperationDTO.getReceiveUnit());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOperationDTO.getApplyNo()), DeviceOperation::getApplyNo, deviceOperationDTO.getApplyNo());
//		queryWrapper.eq(StringUtil.isNotBlank(deviceOperationDTO.getOutboundNo()), DeviceOperation::getOutboundNo, deviceOperationDTO.getOutboundNo());
//		queryWrapper.orderByDesc(DeviceOperation::getCreateTime);
//		IPage<DeviceOperation> deviceOperationIPage = baseMapper.selectPage(page, queryWrapper);
//		return R.data(Convert.convert(new TypeReference<IPage<DeviceOperationVO>>() {
//		}, deviceOperationIPage));
    }

    /**
     * 设备投运详情
     *
     * @param deviceOperationDTO 查询条件
     * @return R
     */
    @Override
    public R<DeviceOperationVO> detailOperation(DeviceOperationDTO deviceOperationDTO) {
        DeviceOperation deviceOperation = baseMapper.selectOne(new LambdaQueryWrapper<DeviceOperation>()
                .eq(Objects.nonNull(deviceOperationDTO.getId()), DeviceOperation::getId, deviceOperationDTO.getId())
                .eq(StringUtils.isNotBlank(deviceOperationDTO.getOperationNo()), DeviceOperation::getOperationNo, deviceOperationDTO.getOperationNo()));
        if (Objects.isNull(deviceOperation)) {
            return R.fail("设备投运工单不存在");
        }
        DeviceOperationVO deviceOperationVO = Convert.convert(DeviceOperationVO.class, deviceOperation);
        // 获取设备详情信息
        List<DeviceOperationDetail> deviceOperationDetailList = operationDetailService.list(new LambdaQueryWrapper<DeviceOperationDetail>()
                .eq(DeviceOperationDetail::getOperationId, deviceOperation.getId()).eq(DeviceOperationDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        deviceOperationVO.setDeviceOperationDetailVOList(Convert.convert(new TypeReference<List<DeviceOperationDetailVO>>() {
        }, deviceOperationDetailList));
        // 获取附件详情信息
        List<DeviceOrderFileVO> deviceOrderFileVOList = new ArrayList<>();
        // 获取附件信息
        deviceOrderFileService.getOrderFileList(deviceOrderFileVOList, deviceOperation.getId(), OrderFileTypeEnum.OPERATION.getOrderType());
        if ("0".equals(deviceOperation.getOperation())) {
            // 如果是申请_出库_投运三合一的单子，获取设备申请单和出库单信息以及附件信息
            DeviceApply deviceApply = deviceApplyMapper.selectOne(new LambdaQueryWrapper<DeviceApply>().eq(DeviceApply::getApplyNo, deviceOperation.getApplyNo()).eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            deviceOperationVO.setDeviceApplyVO(Convert.convert(DeviceApplyVO.class, deviceApply));
            // 获取设备信息
            deviceOrderFileService.getOrderFileList(deviceOrderFileVOList, deviceApply.getId(), OrderFileTypeEnum.APPLY.getOrderType());
            DeviceOutbound deviceOutbound = deviceOutboundMapper.selectOne(new LambdaQueryWrapper<DeviceOutbound>().eq(DeviceOutbound::getOutboundNo, deviceOperation.getOutboundNo()).eq(DeviceOutbound::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            deviceOperationVO.setDeviceOutboundVO(Convert.convert(DeviceOutboundVO.class, deviceOutbound));
            // 获取设备信息
            deviceOrderFileService.getOrderFileList(deviceOrderFileVOList, deviceOutbound.getId(), OrderFileTypeEnum.OUTBOUND.getOrderType());
            deviceOperationVO.setDeviceOrderFileVOList(deviceOrderFileVOList);
        }
        return R.data(deviceOperationVO);
    }

    /**
     * 获取设备投运工单状态字典
     *
     * @return R
     */
    @Override
    public R<List<DictValueVO>> deviceOperationDict() {
        List<DictValueVO> dictValueVOList = new ArrayList<>();
        DeviceOperationEnum[] values = DeviceOperationEnum.values();
        for (DeviceOperationEnum deviceOperationEnum : values) {
            dictValueVOList.add(DictValueVO.builder().node(deviceOperationEnum.getCode().toString()).nodeName(deviceOperationEnum.getMessage())
                    .sort(deviceOperationEnum.getSort()).type(deviceOperationEnum.getType()).build());
        }
        return R.data(dictValueVOList.stream().sorted(Comparator.comparing(DictValueVO::getSort)).collect(Collectors.toList()));
    }

    /**
     * 个人工作台查询投运工单
     *
     * @param deviceOperationDTO 查询条件
     * @param query              分页条件
     * @return R
     */
    @Override
    public R<IPage<DeviceOperationVO>> deskList(DeviceOperationDTO deviceOperationDTO, Query query) {
        if (StringUtil.isBlank(deviceOperationDTO.getOrderNoList())) {
            return R.data(new Page<>());
        }
        if (deviceOperationDTO.getQueryHandleFlag() == 0) {
            List<String> strings = Arrays.asList(deviceOperationDTO.getOrderNoList().split(","));
            if (strings.size() == 1 && strings.get(0).contains("TY")) {
                LambdaQueryWrapper<DeviceOperation> queryWrapper = new LambdaQueryWrapper<DeviceOperation>()
                        .in(DeviceOperation::getOperationNo, strings).eq(DeviceOperation::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).orderByDesc(DeviceOperation::getCreateTime);
                IPage<DeviceOperation> deviceOperationIPage = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
                return R.data(Convert.convert(new TypeReference<IPage<DeviceOperationVO>>() {
                }, deviceOperationIPage));
            } else {
                LambdaQueryWrapper<DeviceOperation> queryWrapper = new LambdaQueryWrapper<DeviceOperation>()
                        .in(DeviceOperation::getApplyNo, strings).eq(DeviceOperation::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).orderByDesc(DeviceOperation::getCreateTime);
                IPage<DeviceOperation> deviceOperationIPage = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
                return R.data(Convert.convert(new TypeReference<IPage<DeviceOperationVO>>() {
                }, deviceOperationIPage));
            }
        } else {
            return R.data(new Page<>());
        }
    }

    /**
     * 个人工作台审核更新工单
     *
     * @param deviceOperationDTO 审核工单信息
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<List<DeviceOperationDetailVO>> deskUpdateStatus(DeviceOperationDTO deviceOperationDTO) throws Exception {
        IdevelopUser user = SecureUtil.getUser();
        DeviceOperation deviceOperation = baseMapper.selectOne(new LambdaQueryWrapper<DeviceOperation>().eq(DeviceOperation::getId, deviceOperationDTO.getId())
                .eq(DeviceOperation::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        if (Objects.isNull(deviceOperation)) {
            return R.fail("投运工单不存在");
        }
        if (deviceOperationDTO.getWorkerStatus() == 1) {
            return R.fail("当前工单无法驳回");
        }
        if ("1".equals(deviceOperation.getOperationType())) {
            return R.fail("单独投运不需要执行审批流程");
        }
        DeviceApply deviceApply = deviceApplyMapper.selectOne(new LambdaQueryWrapper<DeviceApply>().eq(DeviceApply::getApplyNo, deviceOperation.getApplyNo())
                .eq(DeviceApply::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        if (Objects.isNull(deviceApply)) {
            return R.fail("关联申请工单不存在");
        }
        //  获取当前节点操作角色
        List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(deviceApply.getApplyNo());
        String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
        String recordStatus = deviceOperation.getProcessStatus();
        // 更新设备申请工单信息
        deviceApplyMapper.update(new LambdaUpdateWrapper<DeviceApply>().eq(DeviceApply::getId, deviceApply.getId()).set(DeviceApply::getUpdateTime, new Date())
                .set(DeviceApply::getUpdateUser, user.getUserId()).set(DeviceApply::getProcessStatus, DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
                .set(DeviceApply::getStatus, DeviceApplyEnum.FINISH.getCode()));
        // 更新设备出库工单信息
        deviceOutboundMapper.update(new LambdaUpdateWrapper<DeviceOutbound>().eq(DeviceOutbound::getOutboundNo, deviceOperation.getOutboundNo())
                .set(DeviceOutbound::getUpdateUser, user.getUserId()).set(DeviceOutbound::getUpdateTime, new Date())
                .set(DeviceOutbound::getProcessStatus, DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
                .set(DeviceOutbound::getStatus, DeviceOutboundEnum.FINISH.getCode())
        );
        // 更新投运单信息
        baseMapper.update(new LambdaUpdateWrapper<DeviceOperation>().eq(DeviceOperation::getId, deviceOperationDTO.getId())
                .set(DeviceOperation::getUpdateTime, new Date()).set(DeviceOperation::getUpdateUser, user.getUserId())
                .set(DeviceOperation::getProcessStatus, DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
                .set(DeviceOperation::getOperationUnit, deviceOperationDTO.getOperationUnit())
                .set(DeviceOperation::getOperationUnitName, deviceOperationDTO.getOperationUnitName())
                .set(DeviceOperation::getOperationDept, deviceOperationDTO.getOperationDept())
                .set(DeviceOperation::getOperationDeptName, deviceOperationDTO.getOperationDeptName())
                .set(DeviceOperation::getOperationUse, deviceOperationDTO.getOperationUse())
                .set(DeviceOperation::getOperationUseName, deviceOperationDTO.getOperationUseName())
                .set(DeviceOperation::getOperationPhone, deviceOperationDTO.getOperationPhone())
                .set(DeviceOperation::getStatus, DeviceOperationEnum.FINISH.getCode()).set(DeviceOperation::getSubmitTime, new Date()));
        // 新增设备附件信息
        batchInsertDeviceFile(OrderFileTypeEnum.OPERATION.getOrderType(), deviceOperationDTO);
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(deviceApply.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString())
                .optRole(roleName).optType(OptTypeEnum.DEVICE_OPERATION.getCode()).title(deviceOperationDTO.getComment()).build());
        // 记录审核流程
        approveRecordService.commonRecord(ApproveRecord.builder().filingNo(deviceApply.getId())
                .optRole(roleName)
                .optType(OptTypeEnum.DEVICE_OPERATION.getCode())
                .nodeId(recordStatus)
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.getMessage(recordStatus))
                .optTitle(deviceOperationDTO.getComment())
                .optOpinion(deviceOperationDTO.getComment())
                .approveStatus(deviceOperationDTO.getWorkerStatus())
                .filingCode(deviceOperation.getOperationNo())
                .build());
        // 增加流程归档操作和审核记录流程
        LogOpt logOpt = LogOpt.builder().logId(deviceApply.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString()).optRole("--")
                .optType(OptTypeEnum.DEVICE_OPERATION.getCode()).title(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getMessage()).optName("系统").build();
        logOpt.setStatus(0);
        logOptService.commonLogOpt(logOpt);
        // 记录审核流程
        ApproveRecord approveRecord = ApproveRecord.builder().filingNo(deviceApply.getId()).optType(OptTypeEnum.DEVICE_OPERATION.getCode())
                .nodeId(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getNode())
                .nodeName(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getMessage())
                .optTitle(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getMessage())
                .optOpinion(DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_RECORD_FINISH.getMessage())
                .approveStatus(0).filingCode(deviceOperation.getOperationNo())
                .optName("系统").optRole("--").build();
        approveRecord.setStatus(1);
        approveRecordService.commonRecord(approveRecord);
        List<String> deviceTypeList = modelProperties.getModelIdList(Constants.DEVICE_SAFE_ACCESS_TYPE);
        List<String> switchesTypeList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOperationDTO.getDeviceOperationDetailDTOList();
        // 如果是投运类型是以旧换新，需要走退网工单
        if ("0".equals(deviceOperation.getOldToNew())) {
            insertDeviceReturn(deviceOperationDTO, user, deviceTypeList, switchesTypeList);
        }
        // 生成认证账号和认证密码  账号生成规则：区域（六位不够后面补零）+ 加上五位递增的数据 密码：随机六位数
        deviceOperationDetailDTOList.forEach(item -> {
            if (!"0".equals(deviceOperation.getOldToNew())) {
                if (modelProperties.getT105().equals(item.getDeviceCategory())
                        || modelProperties.getT107().equals(item.getDeviceCategory())
                    /*|| deviceTypeList.contains(item.getDeviceType())*/) {
                    String authUser = orderNumberUtil.generateDeviceAuthUser(user.getRegionCode(), CacheNames.OPERATION_AUTH_USER);
                    item.setAuthAccount(authUser);
                    try {
                        item.setAuthPassword(String.valueOf(SecureRandom.getInstance(Constants.SECURE_RANDOM).nextInt(900000) + 100000).substring(0, 6));
                    } catch (NoSuchAlgorithmException e) {
                        log.debug("生成设备密码错误：" + e.getMessage());
                    }
                }
            }
            item.setOperationUnit(deviceOperationDTO.getOperationUnit());
            item.setOperationUnitName(deviceOperationDTO.getOperationUnitName());
            item.setOperationDept(deviceOperationDTO.getOperationDept());
            item.setOperationDeptName(deviceOperationDTO.getOperationDeptName());
            item.setOperationUse(deviceOperationDTO.getOperationUseName());
            item.setOperationPhone(deviceOperationDTO.getOperationPhone());
            item.setDeviceStatus(modelProperties.getInOperation());
            item.setUpdateTime(new Date());
            item.setUpdateUser(user.getUserId());
            if (StringUtils.isBlank(item.getDeviceIp()) && StringUtils.isNotBlank(item.getSwitchesIp())) {
                item.setDeviceIp(item.getSwitchesIp());
            }
        });
        List<DeviceOperationDetail> deviceOperationDetailList = Convert.convert(new TypeReference<List<DeviceOperationDetail>>() {
        }, deviceOperationDetailDTOList);
        // 更新交换机信息
        if (switchesTypeList.contains(deviceOperation.getDeviceType())) {
            // 处理交换机投运信息
            List<DeviceOperationDetailDTO> deviceOperationDetailDTOList1 = deviceOperationDetailDTOList.stream()
                    .filter(item -> (switchesTypeList.contains(item.getDeviceType())))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deviceOperationDetailDTOList1)) {
                handleSwitches(deviceOperationDetailDTOList1, deviceOperationDTO, user);
            }
        }
        // 更新投运设备信息
        operationDetailService.updateBatchById(deviceOperationDetailList);
        // 统一纳管的设备，工单的下的所有设备类型是唯一的，只需要取主表的进行判断就可以
        if (modelProperties.getT105().equals(deviceOperation.getDeviceCategory()) || modelProperties.getT107().equals(deviceOperation.getDeviceCategory())) {
            // 新增用户入网数据和IP地址池校验
            batchInsertUserAccess(deviceOperationDetailDTOList, user, deviceOperationDTO, deviceApply, switchesTypeList);
        }
        // 更新cmdb、i6000系统设备信息
        updateCmdbDevice(deviceOperationDetailList, deviceOperation.getOldToNew(), deviceApply, deviceOperationDTO);
        // 更新i6000
        // updateI6000Entity(deviceOperationDetailList, deviceOperation.getOldToNew(), deviceApply, deviceOperationDTO);
        // 以旧换新数据推送服务，交换机认证账号密码不变，不进行推送
        List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();
        if ("0".equals(deviceOperation.getOldToNew())) {
            List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
            deviceOperationDetailDTOList.forEach(item -> {
                if (modelProperties.getT105().equals(item.getDeviceCategory()) || (switchesTypeList.contains(item.getDeviceType())
                        && modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))) {
                    switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                            .deviceCategory(item.getDeviceCategory())
                            .deviceType(item.getDeviceType())
                            .deviceIp(item.getOldDeviceIp())
                            .deviceMac(item.getDeviceOldMac())
                            .deviceSubnet(item.getDeviceSubnet())
                            .switchesIp(item.getSwitchesIp())
                            .switchesPassword(item.getSwitchesPassword())
                            .deviceCode(item.getOldDeviceCode())
                            .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                    Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                            Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                            .build());
                }
                // 2025-02-11 根据IP和MAC增加查询用户入网表里面的认证用户(approveu_user)
                SafeaccessUserAccess safeaccessUserAccess = new SafeaccessUserAccess();
                safeaccessUserAccess.setIpAddress(item.getOldDeviceIp());
                safeaccessUserAccess.setMacAddress(item.getDeviceOldMac());
                QueryWrapper<SafeaccessUserAccess> queryWrapper = Condition.getQueryWrapper(safeaccessUserAccess);
                SafeaccessUserAccess safeaccessUserAccess1 = userAccessService.getOne(queryWrapper);
                if (Objects.isNull(safeaccessUserAccess1)) {
                    throw new RuntimeException("以旧换新时未查询到用户入网信息, 联系管理员处理.");
                }
                if (modelProperties.getT105().equals(item.getDeviceCategory())) {
                    deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                            .authUser(safeaccessUserAccess1.getApproveuUser())
                            .sbbm(item.getOldDeviceCode())
                            .syncSign("D").readState("0").dataFrom("0")
                            .region(deviceOperation.getRegionCode().length() > 4 ? deviceOperation.getRegionCode().substring(0, 4) : deviceOperation.getRegionCode())
                            .build());
                }
            });
            if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
                idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, user.getRegionCode(), "1");
            }
        }
        // 设备投运推送数据同步服务
        List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
        if (deviceTypeList.contains(deviceOperation.getDeviceType()) || switchesTypeList.contains(deviceOperation.getDeviceType())) {
            LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>()
                    .likeRight(SafeaccessSubnet::getRegionCode, deviceOperation.getRegionCode())
                    .in(SafeaccessSubnet::getId, deviceOperationDetailDTOList.stream().map(DeviceOperationDetailDTO::getDeviceSubnet).distinct().collect(Collectors.toList()));
            List<SafeaccessSubnet> safeAccessSubnetList = safeaccessSubnetMapper.selectList(queryWrapper);
            deviceOperationDetailDTOList.forEach(item -> {
                if (item.getNetworkType().equals(cmdbCientityProperties.getNetwork3())) {
                    return;
                }
                if (modelProperties.getT105().equals(item.getDeviceCategory())
                        || (switchesTypeList.contains(item.getDeviceType()) && modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))
                        || modelProperties.getT107().equals(item.getDeviceCategory()) || modelProperties.getT10101().equals(item.getDeviceType())) {
                    switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                            .deviceCategory(item.getDeviceCategory())
                            .deviceType(item.getDeviceType())
                            .deviceIp(item.getDeviceIp())
                            .deviceMac(item.getDeviceMac())
                            .authAccount(item.getAuthAccount())
                            .authPassword(item.getAuthPassword())
                            .deviceSubnet(item.getDeviceSubnet())
                            .switchesIp(item.getSwitchesIp())
                            .switchesPassword(item.getSwitchesPassword())
                            .deviceCode(item.getDeviceCode())
                            .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                    Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                            Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                            .build());
                    if (modelProperties.getT105().equals(item.getDeviceCategory()) || modelProperties.getT107().equals(item.getDeviceCategory()) || modelProperties.getT10101().equals(item.getDeviceType())) {
                        SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String deviceId = DeviceConstant.DEVICE_TYPE_CODE_MAP.get(item.getDeviceType());
                        String receiveUnitName = item.getReceiveUnitName();
                        if (StringUtils.equals("国网青岛供电公司", item.getReceiveUnitName())) {
                            receiveUnitName = "国网青岛供电公司本部";
                            deviceId = item.getDeviceTypeName();
                        }
                        deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                                .company(receiveUnitName)
                                .department(item.getReceiveDutyDeptName())
                                .address(item.getAddress())
                                .phone(item.getUserPhone())
                                .deviceId(StringUtils.isEmpty(deviceId) ? "58" : deviceId)
                                .subnetId(item.getDeviceSubnet())
                                .authUser(item.getAuthAccount())
                                .authPassword(item.getAuthPassword())
                                .macAddress(item.getDeviceMac())
                                .ipAddress(item.getDeviceIp())
                                .sbbm(item.getDeviceCode())
                                // 如果非临时使用，入网时间是当前时间，如果是临时使用，入网时间是开始时间
                                .startTime(item.getTemporaryType() == 0 ?
                                        DATE_FORMAT_TIME.format(item.getTemporaryStartTime()) + " 00:00:00" : DATE_FORMAT_TIME.format(new Date()))
                                // 如果非临时使用，允许入网时长默认0，如果是临时使用，允许入网时长通过时间计算
                                .allowDays(item.getTemporaryType() == 0 ?
                                        String.valueOf((item.getTemporaryEndTime().getTime() - item.getTemporaryStartTime().getTime()) / (24 * 60 * 60 * 1000)) : "0")
                                .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                        Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                                Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                                .fullUserName(item.getUserName())
                                .isAccess("0")
                                .syncTime(DATE_FORMAT_TIME.format(new Date()))
                                .syncSign("A")
                                .readState("0")
                                .dataFrom("0")
                                .sourcr("02")
                                .deviceName(item.getFullName())
                                .factoryNumber(item.getFactoryNumber())
                                .makerName(item.getMakerName())
                                .brandName(item.getBrandName())
                                .seriesName(item.getSeriesName())
                                .deviceModelName(item.getDeviceModelName())
                                .vlanId(safeAccessSubnetList.stream().filter(safeAccessSubnet ->
                                        safeAccessSubnet.getId().equals(item.getDeviceSubnet())).findFirst().orElse(new SafeaccessSubnet()).getVlanId())
                                .region(deviceOperation.getRegionCode().length() > 4 ? deviceOperation.getRegionCode().substring(0, 4) : deviceOperation.getRegionCode())
                                .build());
                    }
                }
            });
            if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
                idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, user.getRegionCode(), "0");
            }
            if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
                idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
            }
        }
        // 青岛sdn同步 网络交换机
        if ((deviceOperationDTO.getRegionCode().contains("3702") && CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) && switchesTypeList.contains(deviceOperation.getDeviceType())) {
            DeviceSdnQingDao deviceSdnQingDao = new DeviceSdnQingDao();
            deviceSdnQingDao.setFlag("3");
            List<SdnQingDaoNetWork> deviceOperationDTOS = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<SafeaccessSwitcheDTO> safeaccessSwitcheList = new ArrayList<>();
            String corpName = (String) user.getExt().get("corpFullName");
            if (StringUtils.equals("国网青岛供电公司", corpName)) {
                corpName = "国网青岛供电公司本部";
            }
            Map<Object, Object> dictMapByCiId = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getNetworkDeviceType());
            SdnQingDaoNetWork sdnQingDaoNetWork = SdnQingDaoNetWork.builder().id(deviceOperationDTO.getId())
                    .applyDate(Objects.isNull(deviceOperationDTO.getApplyDate()) ? "" : sdf.format(deviceOperationDTO.getApplyDate()))
                    .applyNo(deviceOperationDTO.getApplyNo())
                    .applyUser(deviceOperationDTO.getApplyUser())
                    .applyUserName(deviceOperationDTO.getApplyUserName())
                    .createDept(deviceOperationDTO.getCreateDept())
                    .deviceCategory(deviceOperationDTO.getDeviceCategory())
                    .deviceCategoryName(deviceOperationDTO.getDeviceCategoryName())
                    .deviceType(deviceOperationDTO.getDeviceType())
                    .deviceTypeName(deviceOperationDTO.getDeviceTypeName())
                    .operation(deviceOperationDTO.getOperation())
                    .operationDept(deviceOperationDTO.getOperationDept())
                    .operationNo(deviceOperationDTO.getOperationNo())
                    .operationDeptName(deviceOperationDTO.getOperationDeptName())
                    .operationUnitName(corpName)
                    .regionCode(deviceOperationDTO.getRegionCode())
                    .operationPhone(deviceOperationDTO.getOperationPhone())
                    .submitTime(Objects.isNull(deviceOperationDTO.getSubmitTime()) ? "" : sdf.format(deviceOperationDTO.getSubmitTime()))
                    .createTime(Objects.isNull(deviceOperationDTO.getCreateTime()) ? "" : sdf.format(deviceOperationDTO.getCreateTime()))
                    .createUser(user.getUserName())
                    .updateTime(Objects.isNull(deviceOperationDTO.getUpdateTime()) ? "" : sdf.format(deviceOperationDTO.getUpdateTime()))
                    .opinion(deviceOperationDTO.getComment())
                    .sourcr("公司配发")
                    .user(user.getUserName()).build();
            String finalCorpName = corpName;
            deviceOperationDTO.getDeviceOperationDetailDTOList().forEach(item -> {
                SafeaccessSwitcheDTO safeaccessSwitcheDTO = new SafeaccessSwitcheDTO();
                safeaccessSwitcheDTO.setSwName(item.getDeviceName());
                safeaccessSwitcheDTO.setSwMaker(item.getMakerName());
                safeaccessSwitcheDTO.setSwFirm(item.getBrandName());
                safeaccessSwitcheDTO.setSwSeries(item.getSeriesName());
                safeaccessSwitcheDTO.setSwModel(item.getDeviceModelName());
                safeaccessSwitcheDTO.setSwPurpose(item.getPurpose());
                safeaccessSwitcheDTO.setSwState("运行");
                safeaccessSwitcheDTO.setSwWhere(item.getAddress());
                safeaccessSwitcheDTO.setCompany(item.getOwnerUnit());
                safeaccessSwitcheDTO.setSwIp(item.getSwitchesIp());
                safeaccessSwitcheDTO.setSwPass(item.getSwitchesPassword());
                safeaccessSwitcheDTO.setAuthConfig(item.getIs802());
                safeaccessSwitcheDTO.setIs3(dictMapByCiId.get(item.getNetworkDeviceType()).toString());
                safeaccessSwitcheDTO.setPortsCount(item.getPortsCount());
                safeaccessSwitcheDTO.setVlans(item.getVlanNumber());
                safeaccessSwitcheDTO.setTelIp(item.getManageIp());
                safeaccessSwitcheDTO.setTelUser(item.getManageUser());
                safeaccessSwitcheDTO.setTelPass(item.getManagePassword());
                safeaccessSwitcheDTO.setConfigPass(item.getAllocationPassword());
                safeaccessSwitcheDTO.setSnmpReadStr(item.getSnmpRead());
                safeaccessSwitcheDTO.setSnmpVersion(item.getSnmpVersion());
                safeaccessSwitcheDTO.setSnmpWriteStr(item.getSnmpWrite());
                safeaccessSwitcheDTO.setFillMan(user.getUserName());
                safeaccessSwitcheDTO.setFillDate(new Date().toString());
                safeaccessSwitcheDTO.setIsAccessSwitch(item.getIsAccess());
                safeaccessSwitcheDTO.setDeviceCode(item.getDeviceCode());
                safeaccessSwitcheDTO.setOperationId(item.getOperationId());
                safeaccessSwitcheDTO.setOrgCode(finalCorpName);
                safeaccessSwitcheList.add(safeaccessSwitcheDTO);
            });
            sdnQingDaoNetWork.setSwitchesList(safeaccessSwitcheList);
            deviceOperationDTOS.add(sdnQingDaoNetWork);
            deviceSdnQingDao.setAccessNetworkDTOList(deviceOperationDTOS);
            QDDSwitcherPublisher.publishEvent(deviceSdnQingDao);
        }

        // 发起审批流程
        HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
        hussarBpmDTO.setBusinessKey(deviceOperationDTO.getApplyNo());
        hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark());
        hussarBpmDTO.setParticipantType("2");
        hussarBpmDTO.setTaskType("1");
        hussarBpmService.hussarSubmit(hussarBpmDTO);
        return R.data(Convert.convert(new TypeReference<List<DeviceOperationDetailVO>>() {
        }, deviceOperationDetailDTOList));
    }

    /**
     * 同步i6000
     *
     * @param deviceOperationDetailList 设备信息
     * @param oldToNew                  是否以旧换新标识
     * @param deviceApply               申请信息
     * @param deviceOperationDTO        投运设备信息
     **/
    public void updateI6000Entity(List<DeviceOperationDetail> deviceOperationDetailList,
                                  String oldToNew, DeviceApply deviceApply, DeviceOperationDTO deviceOperationDTO) {
        Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String deviceType = deviceOperationDTO.getDeviceType();
        String deviceCiTypeId = erpI6000MapByCiId.get(deviceType);
        if ("0".equals(oldToNew)) {
            Map<String, Map<String, Object>> hashMap = new HashMap<>();
            deviceOperationDetailList.forEach(deviceOperationDetail -> {
                HashMap<String, Object> deviceMap = new HashMap<>();
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM, "");
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, "");
                deviceMap.put(CmdbAttrConstant.CABINET_CODE, "");
                deviceMap.put(CmdbAttrConstant.CABINET, "");
                deviceMap.put(CmdbAttrConstant.IP, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, deviceOperationDetail.getReturnDeviceStatus());
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, modelProperties.getReturnWarehouse().equals(deviceOperationDetail.getReturnDeviceStatus()) ?
                        Constants.IN_WAREHOUSE : Constants.WAIT_SCARP);
                deviceMap.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, deviceOperationDetail.getReturnWarehouse());
                deviceMap.put(CmdbAttrConstant.IN_WAREHOUSE, deviceOperationDetail.getReturnWarehouseName());
                deviceMap.put(CmdbAttrConstant.WAREHOUSE_LOCATION, deviceOperationDetail.getReturnAddress());
                deviceMap.put(CmdbAttrConstant.SUBNET_ID, "");
                deviceMap.put(CmdbAttrConstant.SUBNET_NAME, "");
                deviceMap.put(CmdbAttrConstant.USER, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_TEAM, "");
                deviceMap.put(CmdbAttrConstant.USER_TEL, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, "");
                deviceMap.put(CmdbAttrConstant.USER_EMAIL, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, "");
                deviceMap.put(CmdbAttrConstant.MANAGE_USERS, "");
                deviceMap.put(CmdbAttrConstant.MANAGE_PASSWORD, "");
                deviceMap.put(CmdbAttrConstant.SWITCH_PASSWORD, "");
                deviceMap.put(CmdbAttrConstant.SNMP_READ_STRING, "");
                deviceMap.put(CmdbAttrConstant.SNMP_WRITE_STRING, "");
                deviceMap.put(CmdbAttrConstant.SNMP_VERSION, "");
                deviceMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, "");
                deviceMap.put(CmdbAttrConstant.WORK_VLAN, "");
                deviceMap.put(CmdbAttrConstant.CONFIG_PASSWORD, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_END, "");
                deviceMap.put(CmdbAttrConstant.CI_ID, deviceOperationDetail.getOldDeviceCid());
                deviceMap.put(CmdbAttrConstant.UUID, deviceOperationDetail.getOldDeviceUuid());
                if (StringUtils.isNotBlank(deviceCiTypeId)) {
                    deviceMap.put(CmdbAttrConstant.CITYPE_ID, deviceCiTypeId);
                    hashMap.put(deviceOperationDetail.getOldDeviceUuid(), deviceMap);
                }
            });
            if (hashMap.size() > 0) {
                i6000Service.i6000Batchupdate(hashMap);
            }
            Map<String, Map<String, Object>> operationDeviceMap = new HashMap<>();
            List<String> deviceIdList = deviceOperationDetailList.stream().map(DeviceOperationDetail::getDeviceId).collect(Collectors.toList());
            List<DeviceOperationDetail> list = operationDetailService.list(new LambdaQueryWrapper<DeviceOperationDetail>().ne(DeviceOperationDetail::getOperationId,
                    deviceOperationDetailList.get(0).getOperationId()).in(DeviceOperationDetail::getDeviceId, deviceIdList).eq(DeviceOperationDetail::getDeviceStatus, modelProperties.getInOperation()));
            // 修改 资产台账
            deviceOperationDetailList.forEach(deviceOperationDetail -> {
                Map<String, Object> deviceMap = new HashMap<>();
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_TEAM, deviceApply.getReceiveDutyGroupName());
                deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE_CODE, modelProperties.getDeviceSource());
                deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE, Constants.DEVICE_SOURCE);
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, modelProperties.getInOperation());
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, Constants.IN_OPERATION);
                deviceMap.put(CmdbAttrConstant.USER, deviceOperationDetail.getUserName());
                deviceMap.put(CmdbAttrConstant.USER_TEL, deviceOperationDetail.getUserPhone());
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, deviceOperationDetail.getUserCard());
                deviceMap.put(CmdbAttrConstant.DEVICE_CODE, deviceOperationDetail.getDeviceCode());
                deviceMap.put(CmdbAttrConstant.DEVICE_NAME, deviceOperationDetail.getDeviceName());
                deviceMap.put(CmdbAttrConstant.ASSET_CODE_ERP, deviceOperationDetail.getErpAssetCode());
                deviceMap.put(CmdbAttrConstant.SN, deviceOperationDetail.getFactoryNumber());
                deviceMap.put(CmdbAttrConstant.INSTALLATION_SITE, deviceOperationDetail.getAddress());
                if (Objects.nonNull(deviceOperationDetail.getUserTime())) {
                    deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, DATE_FORMAT.format(deviceOperationDetail.getUserTime()));
                }
                deviceMap.put(CmdbAttrConstant.SUBNET_ID, deviceOperationDetail.getDeviceSubnet());
                deviceMap.put(CmdbAttrConstant.SUBNET_NAME, deviceOperationDetail.getDeviceSubnetName());
                deviceMap.put(CmdbAttrConstant.NET_WORK_CODE, deviceOperationDetail.getNetworkType());
                deviceMap.put(CmdbAttrConstant.IP, deviceOperationDetail.getDeviceIp());
                deviceMap.put(CmdbAttrConstant.MAC, deviceOperationDetail.getDeviceMac());
                deviceMap.put(CmdbAttrConstant.OPERATION_UNIT_CODE, deviceOperationDetail.getOperationUnit());
                deviceMap.put(CmdbAttrConstant.OPERATION_UNIT, deviceOperationDetail.getOperationUnitName());
                deviceMap.put(CmdbAttrConstant.OPERATION_DEP_CODE, deviceOperationDetail.getOperationDept());
                deviceMap.put(CmdbAttrConstant.OPERATION_DEPT, deviceOperationDetail.getOperationDeptName());
                deviceMap.put(CmdbAttrConstant.OPERATION_PERSON, deviceOperationDetail.getOperationUse());
                deviceMap.put(CmdbAttrConstant.OPERATION_LEVEL, deviceOperationDetail.getOperationGradeCode());
                deviceMap.put(CmdbAttrConstant.OPERATION_TEL, deviceOperationDetail.getOperationPhone());
                deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE, deviceOperationDetail.getDeviceSource());
                deviceMap.put(CmdbAttrConstant.OPRT_DATE, DATE_FORMAT.format(new Date()));
                long count = list.stream().filter(deviceOperationDetailInfo -> deviceOperationDetailInfo.getDeviceId().equals(deviceOperationDetail.getDeviceId())).count();
                if (count == 0) {
                    deviceMap.put(CmdbAttrConstant.OPRT_DATE_FIRST, DATE_FORMAT.format(new Date()));
                }
                deviceMap.put(CmdbAttrConstant.CI_ID, deviceOperationDetail.getDeviceCid());
                deviceMap.put(CmdbAttrConstant.UUID, deviceOperationDetail.getDeviceUuid());
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, deviceOperationDetail.getReceiveUnitName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, deviceOperationDetail.getReceiveUnit());
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, deviceOperationDetail.getReceiveDutyDeptName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, deviceOperationDetail.getReceiveDutyDept());
                deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, deviceOperationDetail.getReceiveUseName());
                deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, deviceOperationDetail.getReceiveUsePhone());
                deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, deviceOperationDetail.getReceiveUseCard());
                deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, DATE_FORMAT.format(new Date()));
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM, deviceOperationDetail.getRoomName());
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, deviceOperationDetail.getRoomId());
                deviceMap.put(CmdbAttrConstant.CABINET_CODE, deviceOperationDetail.getCabinetsId());
                deviceMap.put(CmdbAttrConstant.CABINET, deviceOperationDetail.getCabinetsName());
                deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, deviceOperationDetail.getReceiveDutyGroupName());
                deviceMap.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, deviceOperationDetail.getReceiveUsePhone());
                deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, deviceApply.getReceiveDutyIscAccount());
                deviceMap.put(CmdbAttrConstant.MANAGE_USERS, deviceOperationDetail.getManageUser());
                deviceMap.put(CmdbAttrConstant.MANAGE_PASSWORD, deviceOperationDetail.getManagePassword());
                deviceMap.put(CmdbAttrConstant.SWITCH_PASSWORD, deviceOperationDetail.getSwitchesPassword());
                deviceMap.put(CmdbAttrConstant.SNMP_READ_STRING, deviceOperationDetail.getSnmpRead());
                deviceMap.put(CmdbAttrConstant.SNMP_WRITE_STRING, deviceOperationDetail.getSnmpWrite());
                deviceMap.put(CmdbAttrConstant.SNMP_VERSION, deviceOperationDetail.getSnmpVersion());
                deviceMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, deviceOperationDetail.getIs802());
                deviceMap.put(CmdbAttrConstant.WORK_VLAN, deviceOperationDetail.getVlanNumber());
                deviceMap.put(CmdbAttrConstant.CONFIG_PASSWORD, deviceOperationDetail.getAllocationPassword());
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, deviceOperationDetail.getDeviceStartHeight());
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_END, deviceOperationDetail.getDeviceEndHeight());
                if (StringUtils.isNotBlank(deviceCiTypeId)) {
                    deviceMap.put(CmdbAttrConstant.CITYPE_ID, deviceCiTypeId);
                    operationDeviceMap.put(deviceOperationDetail.getDeviceUuid(), deviceMap);
                }
            });
            if (operationDeviceMap.size() > 0) {
                i6000Service.i6000Batchupdate(operationDeviceMap);
            }
        }
    }

    /**
     * IP地址池进行占用处理
     *
     * @param deviceOperationDTO        设备投运信息
     * @param deviceOperationDetailList 投运设备信息
     * @param user                      用户信息
     * @param ipUse                     使用情况：0未分配 1已分配 2网关
     */
    private void batchOccUpdateIp(DeviceOperationDTO deviceOperationDTO, List<DeviceOperationDetail> deviceOperationDetailList, IdevelopUser user, String ipUse) throws Exception {
        if (deviceOperationDTO.getRegionCode().length() >= 4) {
            String updateSurface = Constants.IP_POOL + deviceOperationDTO.getRegionCode().substring(0, 4);
            Map<String, List<DeviceOperationDetail>> collect = deviceOperationDetailList.stream().collect(Collectors.groupingBy(DeviceOperationDetail::getDeviceSubnet));
            for (Map.Entry<String, List<DeviceOperationDetail>> entry : collect.entrySet()) {
                List<String> deviceIpList = entry.getValue().stream().map(DeviceOperationDetail::getDeviceIp).collect(Collectors.toList());
                if ("1".equals(ipUse)) {
                    // 校验所选设备IP是否被占用
                    int count = safeaccessIppoolMapper.selectBatchIpCount(updateSurface, deviceIpList);
                    if (count > 0) {
                        throw new Exception("所选IP地址池被占用，请重新选择");
                    }
                }
                safeaccessIppoolMapper.updateBatchIpPool(updateSurface, deviceIpList, user.getUserId(), ipUse, entry.getKey());
            }
        }
    }

    /**
     * 处理交换机投运信息
     *
     * @param deviceOperationDetailDTOList 投运设备信息
     * @param deviceOperationDTO           设备投运信息
     * @param user                         用户信息
     */
    private void handleSwitches(List<DeviceOperationDetailDTO> deviceOperationDetailDTOList, DeviceOperationDTO deviceOperationDTO, IdevelopUser user) {
        List<String> deviceIdList = deviceOperationDetailDTOList.stream().map(DeviceOperationDetail::getDeviceId).collect(Collectors.toList());
        List<SafeaccessSwitche> list = safeAccessSwitchesService.list(new LambdaQueryWrapper<SafeaccessSwitche>().in(SafeaccessSwitche::getDeviceId, deviceIdList));
        List<SafeaccessSwitche> safeAccessSwitchesDTOList = new ArrayList<>();
        SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        deviceOperationDetailDTOList.forEach(item -> {
            SafeaccessSwitche safeaccessSwitcheDTO = new SafeaccessSwitcheDTO();
            safeaccessSwitcheDTO.setSwName(item.getDeviceName());
            safeaccessSwitcheDTO.setSwMaker(item.getMakerName());
            safeaccessSwitcheDTO.setSwFirm(item.getBrandName());
            safeaccessSwitcheDTO.setSwSeries(item.getSeriesName());
            safeaccessSwitcheDTO.setSwModel(item.getDeviceModelName());
            safeaccessSwitcheDTO.setDeviceUuid(item.getDeviceUuid());
            safeaccessSwitcheDTO.setDeviceCode(item.getDeviceCode());
            safeaccessSwitcheDTO.setFillMan(user.getRealName());
            safeaccessSwitcheDTO.setFillDate(DATE_FORMAT_TIME.format(new Date()));
            safeaccessSwitcheDTO.setCompany(user.getRegionCode());
            safeaccessSwitcheDTO.setRegionCode(user.getRegionCode());
            safeaccessSwitcheDTO.setDeptCode(user.getDeptId());
            safeaccessSwitcheDTO.setCreateTime(new Date());
            safeaccessSwitcheDTO.setCreateUser(user.getUserId());
            safeaccessSwitcheDTO.setPortsCount(item.getPortsCount());

            safeaccessSwitcheDTO.setSwState("1");
            safeaccessSwitcheDTO.setAuthConfig(item.getIs802());
            safeaccessSwitcheDTO.setAuthState(item.getIsAccess());
            safeaccessSwitcheDTO.setVlans(item.getVlanNumber());
            safeaccessSwitcheDTO.setTelIp(item.getManageIp());
            safeaccessSwitcheDTO.setTelUser(item.getManageUser());
            safeaccessSwitcheDTO.setTelPass(item.getManagePassword());
            safeaccessSwitcheDTO.setSnmpVersion(item.getSnmpVersion());
            safeaccessSwitcheDTO.setConfigPass(item.getAllocationPassword());
            safeaccessSwitcheDTO.setSnmpReadStr(item.getSnmpRead());
            safeaccessSwitcheDTO.setSnmpWriteStr(item.getSnmpWrite());
            safeaccessSwitcheDTO.setOperationId(deviceOperationDTO.getId());
            safeaccessSwitcheDTO.setOperationFlag(0);
            safeaccessSwitcheDTO.setSubnetId(item.getDeviceSubnet());
            safeaccessSwitcheDTO.setDeviceId(item.getDeviceId());
            safeaccessSwitcheDTO.setSwIp(item.getSwitchesIp());
            safeaccessSwitcheDTO.setSwPass(item.getSwitchesPassword());
            safeaccessSwitcheDTO.setSwWhere(item.getAddress());
            safeaccessSwitcheDTO.setIs3(item.getNetworkDeviceType());
            // safeaccessSwitcheDTO.setStatus(Integer.parseInt(item.getDeviceStatus()));
            safeaccessSwitcheDTO.setSwPurpose(item.getPurpose());
            item.setFirstReceiveFlag(0);
            SafeaccessSwitche safeaccessSwitche = list.stream().filter(safeAccessSwitches -> item.getDeviceId().equals(safeAccessSwitches.getDeviceId())).findFirst().orElse(null);
            if (Objects.nonNull(safeaccessSwitche)) {
                item.setFirstReceiveFlag(StringUtils.isNotBlank(safeaccessSwitche.getOperationId()) ? 1 : 0);
//				item.setSwitchesType(modelProperties.getSwitchesType().equals(safeaccessSwitche.getIs3()) ? 0 : 1);
            }
            safeAccessSwitchesDTOList.add(safeaccessSwitcheDTO);
        });
//		safeAccessSwitchesService.updateBatchSafeAccessSwitches(safeAccessSwitchesDTOList);
        safeAccessSwitchesService.saveBatch(safeAccessSwitchesDTOList);
        // 交换机类型信息推送CMDB数据
        updateSafeSwitchesCmdbDevice(deviceOperationDetailDTOList);
    }

    /**
     * 更新CMDB交换机数据
     *
     * @param deviceOperationDetailDTOList 交换机数据
     */
    private void updateSafeSwitchesCmdbDevice(List<DeviceOperationDetailDTO> deviceOperationDetailDTOList) {
        if (CollectionUtil.isNotEmpty(deviceOperationDetailDTOList)) {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            Map<Long, Map<String, Object>> hashMap = new HashMap<>();
            for (DeviceOperationDetailDTO deviceOperationDetail : deviceOperationDetailDTOList) {
                HashMap<String, Object> deviceMap = new HashMap<>();
                deviceMap.put(CmdbAttrConstant.NET_WORK_CODE, deviceOperationDetail.getNetworkType());
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, deviceOperationDetail.getReceiveUnitName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, deviceOperationDetail.getReceiveUnit());
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, deviceOperationDetail.getReceiveDutyDeptName());
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, deviceOperationDetail.getReceiveDutyDept());
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM, deviceOperationDetail.getRoomName());
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, deviceOperationDetail.getRoomId());
                deviceMap.put(CmdbAttrConstant.CABINET, deviceOperationDetail.getCabinetsName());
                deviceMap.put(CmdbAttrConstant.CABINET_CODE, deviceOperationDetail.getCabinetsId());
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, deviceOperationDetail.getDeviceStartHeight());
                deviceMap.put(CmdbAttrConstant.NETWORK_DEVICE_TYPE, deviceOperationDetail.getNetworkDeviceType());
                deviceMap.put(CmdbAttrConstant.OPRT_DATE, DATE_FORMAT.format(new Date()));
                if (deviceOperationDetail.getFirstReceiveFlag() == 0) {
                    deviceMap.put(CmdbAttrConstant.OPRT_DATE_FIRST, DATE_FORMAT.format(new Date()));
                }
                deviceMap.put(CmdbAttrConstant.IP, deviceOperationDetail.getDeviceIp());
                deviceMap.put(CmdbAttrConstant.MAC, deviceOperationDetail.getDeviceMac());
                deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE_CODE, modelProperties.getDeviceSource());
                deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE, Constants.DEVICE_SOURCE);
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, modelProperties.getInOperation());
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, Constants.IN_OPERATION);
                deviceMap.put(CmdbAttrConstant.SUBNET_ID, deviceOperationDetail.getDeviceSubnet());
                deviceMap.put(CmdbAttrConstant.SUBNET_NAME, deviceOperationDetail.getDeviceSubnetName());
                deviceMap.put(CmdbAttrConstant.CI_ID, deviceOperationDetail.getDeviceCid());
                deviceMap.put(CmdbAttrConstant.UUID, deviceOperationDetail.getDeviceUuid());
                if (StringUtil.isNotBlank(deviceOperationDetail.getDeviceId())) {
                    hashMap.put(Long.parseLong(deviceOperationDetail.getDeviceId()), deviceMap);
                }
            }
            try {
                iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
            } catch (Exception e) {
                log.debug("更新CMDB设备台账信息失败" + e.getMessage());
            }
        }
    }

    /**
     * 新增用户入网数据
     *
     * @param deviceOperationDetailList 设备投运列表信息
     * @param user                      用户信息
     * @param deviceOperationDTO        设备投运主信息
     * @param deviceApply               申请工单信息
     * @param switchesTypeList          交换机类型
     */
    private void batchInsertUserAccess(List<DeviceOperationDetailDTO> deviceOperationDetailList, IdevelopUser user,
                                       DeviceOperationDTO deviceOperationDTO, DeviceApply deviceApply,
                                       List<String> switchesTypeList) throws Exception {
        List<SafeaccessUserAccess> safeAccessUserAccessArrayList = new ArrayList<>();
        List<DeviceOperationDetail> deviceOperationDetailPoolList = new ArrayList<>();
        SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        deviceOperationDetailList.forEach(item -> {
            if (modelProperties.getT105().equals(item.getDeviceCategory()) || modelProperties.getT107().equals(item.getDeviceCategory()) ||
                    !switchesTypeList.contains(item.getDeviceType()) ||
                    (switchesTypeList.contains(item.getDeviceType()) && modelProperties.getSwitchesType().equals(item.getNetworkDeviceType()))) {
                if (item.getNetworkType().equals(cmdbCientityProperties.getNetwork3())) {
                    return;
                }
                SafeaccessUserAccess safeAccessUserAccess = new SafeaccessUserAccess();
                safeAccessUserAccess.setCompany(item.getReceiveUnit());
                safeAccessUserAccess.setDepartment(item.getReceiveDutyDept());
                safeAccessUserAccess.setAddress(item.getAddress());
                safeAccessUserAccess.setPhone(item.getUserPhone());
                safeAccessUserAccess.setDeviceType(item.getDeviceType());
                safeAccessUserAccess.setDeviceCode(item.getDeviceCode());
                safeAccessUserAccess.setSubnetId(item.getDeviceSubnet());
                safeAccessUserAccess.setApproveuUser(item.getAuthAccount());
                safeAccessUserAccess.setApproveuPassword(item.getAuthPassword());
                safeAccessUserAccess.setMacAddress(item.getDeviceMac());
                safeAccessUserAccess.setIpAddress(item.getDeviceIp());
                safeAccessUserAccess.setCode("1");
                safeAccessUserAccess.setAcceptMan(deviceOperationDTO.getApplyUserName());
                safeAccessUserAccess.setAcceptDate(DATE_FORMAT.format(new Date()));
                safeAccessUserAccess.setNetInApplyId(deviceOperationDTO.getId());
                safeAccessUserAccess.setFillMan(user.getRealName());
                safeAccessUserAccess.setFillTime(DATE_FORMAT_TIME.format(new Date()));
                safeAccessUserAccess.setDeptCode(item.getReceiveDutyDept());
                safeAccessUserAccess.setMiUser(item.getReceiveUseName());
                safeAccessUserAccess.setMiChargeUser(item.getUserName());
                if (Objects.nonNull(item.getTemporaryType()) && item.getTemporaryType() == 0) {
                    safeAccessUserAccess.setStartTime(DATE_FORMAT_TIME.format(item.getTemporaryStartTime()));
                    long betweenDay = (item.getTemporaryEndTime().getTime() - item.getTemporaryStartTime().getTime()) / (24 * 60 * 60 * 1000);
                    safeAccessUserAccess.setAllowDays(String.valueOf(betweenDay));
                } else {
                    safeAccessUserAccess.setStartTime(DATE_FORMAT_TIME.format(new Date()));
                    safeAccessUserAccess.setAllowDays("0");
                }
                safeAccessUserAccess.setFullUsername(user.getRealName());
                safeAccessUserAccess.setIs802(item.getIs802());
                safeAccessUserAccess.setIsAccess("0");
                safeAccessUserAccess.setApplyType("1");
                safeAccessUserAccess.setDisableStatus("2");
                safeAccessUserAccess.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                safeAccessUserAccess.setStatus(0);
                safeAccessUserAccess.setRegionCode(user.getRegionCode());
                safeAccessUserAccess.setCreateUser(user.getUserId());
                safeAccessUserAccess.setCreateTime(new Date());
                safeAccessUserAccess.setCreateDept(user.getDeptId());
                safeAccessUserAccess.setDeviceId(item.getDeviceId());
                safeAccessUserAccess.setDeviceUuid(item.getDeviceUuid());
                safeAccessUserAccess.setDeviceCiId(item.getDeviceCid());
                safeAccessUserAccess.setDeviceTypeName(item.getDeviceTypeName());
                safeAccessUserAccessArrayList.add(safeAccessUserAccess);
                deviceOperationDetailPoolList.add(Convert.convert(DeviceOperationDetail.class, item));
            }
        });
        if (CollectionUtils.isNotEmpty(deviceOperationDetailPoolList)) {
            // IP地址池进行占用处理
            batchOccUpdateIp(deviceOperationDTO, deviceOperationDetailPoolList, user, "1");
        }
        if (CollectionUtils.isNotEmpty(safeAccessUserAccessArrayList)) {
            // 新增用户入网数据
            userAccessService.saveBatch(safeAccessUserAccessArrayList);
        }

    }

    /**
     * 投运以旧换新产生退运工单
     *
     * @param deviceOperationDTO 设备投运
     * @param user               用户信息
     * @param deviceTypeList     设备类型
     * @param switchesTypeList   交换机类型
     */
    private void insertDeviceReturn(DeviceOperationDTO deviceOperationDTO, IdevelopUser user, List<String> deviceTypeList, List<String> switchesTypeList) throws Exception {
        DeviceOperation deviceOperationInfo = baseMapper.selectOne(new LambdaQueryWrapper<DeviceOperation>().eq(DeviceOperation::getId, deviceOperationDTO.getId()));
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOperationDTO.getDeviceOperationDetailDTOList();
        DeviceReturned deviceReturned = new DeviceReturned();
        deviceReturned.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.TYD));
        deviceReturned.setApplyUnit(user.getCorpId());
        deviceReturned.setApplyUnitName(user.getExt().get("corpFullName").toString());
        deviceReturned.setAcceptUser(user.getUserId());
        deviceReturned.setAcceptUserName(user.getRealName());
        deviceReturned.setApplyDept(user.getDeptId());
        deviceReturned.setApplyDeptName(user.getDeptName());
        // R<UserInfo> userInfoR = userClient.userInfo(user.getUserId());
        deviceReturned.setAcceptPhone(Objects.isNull(user.getExt().get("phone")) ? "" : user.getExt().get("phone").toString());
        deviceReturned.setAcceptTime(DateUtil.formatDateTime(new Date()));
        deviceReturned.setReturnReason("投运工单以旧换新类型进行设备退运，关联投运单号：" + deviceOperationDTO.getOperationNo());
        deviceReturned.setDeviceReturnNum(String.valueOf(deviceOperationDetailDTOList.size()));
        deviceReturned.setIsAllReturned("1");
        deviceReturned.setOperationId(deviceOperationDTO.getId());
        deviceReturned.setProcessStatus(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode());
        deviceReturned.setRegionCode(user.getRegionCode());
        deviceReturned.setCreateUser(user.getUserId());
        deviceReturned.setCreateTime(new Date());
        deviceReturned.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        deviceReturned.setIsAuto("0");
        deviceReturned.setStatus(DeviceReturnedEnum.FINISH.getCode());
        deviceReturnedService.save(deviceReturned);
        List<DeviceReturnedDetail> deviceReturnedDetailList = new ArrayList<>();
        List<String> returnDeviceIdList = new ArrayList<>();
        List<String> returnSwtichesList = new ArrayList<>();
        List<String> oldDeviceInfo = deviceOperationDetailDTOList.stream().map(DeviceOperationDetailDTO::getOldDeviceId).collect(Collectors.toList());
        List<SafeaccessUserAccess> safeAccessUserAccessList = userAccessService.list(new LambdaQueryWrapper<SafeaccessUserAccess>().in(SafeaccessUserAccess::getDeviceId, oldDeviceInfo));
        List<DeviceOperationDetail> oldDeviceList = operationDetailService.list(new LambdaQueryWrapper<DeviceOperationDetail>()
                .in(DeviceOperationDetail::getDeviceId, oldDeviceInfo));
        deviceOperationDetailDTOList.forEach(item -> {
            DeviceReturnedDetail deviceReturnedDetail = new DeviceReturnedDetail();
            deviceReturnedDetail.setReturnedId(deviceReturned.getId());
            deviceReturnedDetail.setDeviceId(Long.parseLong(item.getOldDeviceId()));
            deviceReturnedDetail.setCiId(Long.parseLong(item.getOldDeviceCid()));
            deviceReturnedDetail.setUuid(item.getOldDeviceUuid());
            deviceReturnedDetail.setDeviceCode(item.getOldDeviceCode());
            deviceReturnedDetail.setDeviceName(item.getOldDeviceName());
            deviceReturnedDetail.setDeviceCategory(deviceOperationInfo.getDeviceCategory());
            deviceReturnedDetail.setDeviceType(deviceOperationInfo.getDeviceType());
            deviceReturnedDetail.setDeviceSource(item.getDeviceSource());
            deviceReturnedDetail.setDeviceStatus(item.getReturnDeviceStatus());
            deviceReturnedDetail.setDeviceIp(item.getOldDeviceIp());
            deviceReturnedDetail.setSubnetId(item.getDeviceSubnet());
            deviceReturnedDetail.setReceivingPerson(item.getOldDeviceUser());
            deviceReturnedDetail.setReceivingTel(item.getOldUserPhone());
            deviceReturnedDetail.setInWarehouseId(item.getReturnWarehouse());
            deviceReturnedDetail.setInWarehouse(item.getReturnWarehouseName());
            deviceReturnedDetail.setInWarehouseCode(item.getReturnWarehouseCode());
            deviceReturnedDetail.setAddress(item.getReturnAddress());
            deviceReturnedDetail.setDeviceMac(item.getDeviceOldMac());
            deviceReturnedDetail.setSwIp(item.getSwitchesIp());
            deviceReturnedDetail.setSwPass(item.getSwitchesPassword());
            deviceReturnedDetail.setIsReturned("0");
            deviceReturnedDetail.setCreateDept(user.getDeptId());
            deviceReturnedDetail.setCreateUser(user.getUserId());
            deviceReturnedDetail.setCreateTime(new Date());
            deviceReturnedDetail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            item.setOprtDept(user.getCorpId());
            item.setOprtDeptName(user.getCorpName());
            List<DeviceOperationDetail> operationDetailList = oldDeviceList.stream().filter(deviceOperationDetail -> deviceOperationDetail.getDeviceId().equals(item.getOldDeviceId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(operationDetailList)) {
                DeviceOperationDetail deviceOperationDetail = operationDetailList.get(0);
                item.setOperationGradeCode(deviceOperationDetail.getOperationGradeCode());
                item.setOperationGrade(deviceOperationDetail.getOperationGrade());
            }
            List<SafeaccessUserAccess> accessList = safeAccessUserAccessList.stream()
                    .filter(userAccess -> item.getOldDeviceId().equals(userAccess.getDeviceId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(accessList)) {
                SafeaccessUserAccess safeAccessUserAccess = accessList.get(0);
                item.setIs802(safeAccessUserAccess.getIs802());
                item.setIsAccess("0");
            }
            if (!switchesTypeList.contains(item.getDeviceType()) || (switchesTypeList.contains(item.getDeviceType()) && item.getSwitchesType() == 0)) {
                returnDeviceIdList.add(item.getOldDeviceId());
            }
            if (switchesTypeList.contains(item.getDeviceType())) {
                returnSwtichesList.add(item.getOldDeviceId());
            }
            deviceReturnedDetailList.add(deviceReturnedDetail);
        });
        deviceReturnedDetailService.saveBatch(deviceReturnedDetailList);
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(deviceReturned.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString()).optRole("--")
                .optType(OptTypeEnum.DEVICE_RETURNED.getCode())
                .title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode())).optName("系统").build());
        // 记录审核流程
        ApproveRecord approveRecord = ApproveRecord.builder().filingNo(deviceReturned.getId())
                .optType(OptTypeEnum.DEVICE_RETURNED.getCode())
                .nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode())
                .nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
                .optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
                .optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode()))
                .filingCode(deviceReturned.getFilingNo()).approveStatus(0)
                .optName("系统").optRole("--").build();
        approveRecord.setStatus(1);
        approveRecordService.commonRecord(approveRecord);
        // 增加归档记录
        LogOpt logOpt = LogOpt.builder().logId(deviceReturned.getId()).logData(deviceOperationDTO.toString()).params(deviceOperationDTO.toString()).optRole("--")
                .optType(OptTypeEnum.DEVICE_RETURNED.getCode())
                .title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())).optName("系统").build();
        logOpt.setStatus(0);
        logOptService.commonLogOpt(logOpt);
        // 记录审核流程
        ApproveRecord approveRecord1 = ApproveRecord.builder().filingNo(deviceReturned.getId())
                .optType(OptTypeEnum.DEVICE_RETURNED.getCode())
                .nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())
                .nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
                .optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
                .optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
                .filingCode(deviceReturned.getFilingNo()).approveStatus(0)
                .optName("系统").optRole("--").build();
        approveRecord1.setStatus(1);
        approveRecordService.commonRecord(approveRecord1);
        // 用户入网退网处理
        if (CollectionUtils.isNotEmpty(returnDeviceIdList)) {
            userAccessService.remove(new LambdaQueryWrapper<SafeaccessUserAccess>()
                    .in(SafeaccessUserAccess::getDeviceId, returnDeviceIdList));
            // IP地址池进行释放处理
            batchOccUpdateIp(deviceOperationDTO, Convert.convert(new TypeReference<List<DeviceOperationDetail>>() {
            }, deviceOperationDetailDTOList), user, "0");
        }
        // 交换机使用状态更新
        if (CollectionUtils.isNotEmpty(returnSwtichesList)) {
            safeAccessSwitchesService.update(new LambdaUpdateWrapper<SafeaccessSwitche>()
                    .set(SafeaccessSwitche::getSwState, "1").in(SafeaccessSwitche::getDeviceId, returnSwtichesList));
        }
        // 以旧换新归还仓库
        List<DeviceInventory> deviceInventoryList = new ArrayList<>();
        Map<String, Map<String, List<DeviceOperationDetailDTO>>> deviceOperationDetailMap = deviceOperationDetailDTOList.stream()
                .filter(deviceOperationDetailDTO -> deviceOperationDetailDTO.getReturnDeviceStatus().equals(modelProperties.getReturnWarehouse()))
                .collect(Collectors.groupingBy(DeviceOperationDetailDTO::getDeviceType,
                        Collectors.groupingBy(DeviceOperationDetailDTO::getReturnWarehouse)));
        for (Map.Entry<String, Map<String, List<DeviceOperationDetailDTO>>> entry : deviceOperationDetailMap.entrySet()) {
            Map<String, List<DeviceOperationDetailDTO>> deviceReturnTypeList = entry.getValue();
            for (Map.Entry<String, List<DeviceOperationDetailDTO>> deviceType : deviceReturnTypeList.entrySet()) {
                DeviceOperationDetailDTO deviceOperationDetailDTO = deviceType.getValue().get(0);
                DeviceInventory deviceInventory = deviceInventoryService.getOne(new LambdaQueryWrapper<DeviceInventory>()
                        .eq(DeviceInventory::getDeviceCategory, deviceOperationDetailDTO.getDeviceCategory())
                        .eq(DeviceInventory::getDeviceType, deviceOperationDetailDTO.getDeviceType())
                        .eq(DeviceInventory::getWarehouse, deviceOperationDetailDTO.getReturnWarehouse())
                        .eq(DeviceInventory::getRegionCode, user.getRegionCode())
                        .eq(DeviceInventory::getCropId, user.getCorpId())
                        .last(" FOR UPDATE"));
                if (Objects.isNull(deviceInventory)) {
                    deviceInventory = DeviceInventory.builder()
                            .regionCode(user.getRegionCode()).cropId(Long.parseLong(user.getCorpId()))
                            .deviceCategory(deviceOperationDetailDTO.getDeviceCategory())
                            .deviceCategoryName(deviceOperationDetailDTO.getDeviceCategoryName())
                            .deviceType(deviceOperationDetailDTO.getDeviceType())
                            .deviceTypeName(deviceOperationDetailDTO.getDeviceTypeName())
                            .warehouse(deviceOperationDetailDTO.getReturnWarehouse())
                            .warehouseName(deviceOperationDetailDTO.getReturnWarehouseName())
                            .inventoryNum(deviceType.getValue().size())
                            .version(0L)
                            .build();
                    deviceInventory.setCreateTime(new Date());
                    deviceInventory.setCreateUser(user.getUserId());
                    deviceInventory.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                } else {
                    deviceInventory.setInventoryNum(deviceInventory.getInventoryNum() + deviceType.getValue().size());
                    deviceInventory.setVersion(deviceInventory.getVersion());
                }
                deviceInventoryList.add(deviceInventory);
            }
        }
        List<DeviceInventory> insertDeviceInventory = deviceInventoryList.stream().filter(deviceInventory -> Objects.isNull(deviceInventory.getId())).collect(Collectors.toList());
        List<DeviceInventory> updateDeviceInventory = deviceInventoryList.stream().filter(deviceInventory -> Objects.nonNull(deviceInventory.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(insertDeviceInventory)) {
            deviceInventoryService.saveBatch(insertDeviceInventory);
        }
        if (CollectionUtils.isNotEmpty(updateDeviceInventory)) {
            deviceInventoryService.batchUpdateInventory(updateDeviceInventory);
        }
    }

    /**
     * 保存cmdb系统设备信息
     *
     * @param deviceOperationDTO 投运信息
     */
    public void insertCmdbDevice(DeviceOperationDTO deviceOperationDTO, IdevelopUser user) {
        if (CollectionUtil.isNotEmpty(deviceOperationDTO.getDeviceOperationDetailDTOList())) {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            // 根据设备进行类型分类，分开处理
            List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOperationDTO.getDeviceOperationDetailDTOList();
            Map<String, List<DeviceOperationDetailDTO>> deviceTypeMap = deviceOperationDetailDTOList.stream().collect(Collectors.groupingBy(DeviceOperationDetailDTO::getDeviceType));
            Map<String, String> dictI6000MapByCiId = cmdbDictProperties.getDictI6000MapByCiId(cmdbDictProperties.getDeviceType());
            for (Map.Entry<String, List<DeviceOperationDetailDTO>> entry : deviceTypeMap.entrySet()) {
                // 根据设备类型获取设备的CID
                HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
                hardwareBasicTree.setDeviceType(entry.getKey());
                Long ciId = iCmdbService.getCiId(hardwareBasicTree);
                Map<String, Map<String, Object>> map = new HashMap<>();
                // Map<String, Map<String, Object>> hashMap = new HashMap<>();
                String deviceCiTypeId = dictI6000MapByCiId.get(entry.getKey());
                List<DeviceOperationDetailDTO> operationDetailDTOList = entry.getValue();
                for (DeviceOperationDetailDTO deviceOperationDetail : operationDetailDTOList) {
                    Map<String, Object> deviceMap = new HashMap<>();
                    deviceMap.put(CmdbAttrConstant.OPERATION_CHAGE_ACCOUNT, "2".equals(user.getUserType()) ? user.getAccount() : "");
                    deviceMap.put(CmdbAttrConstant.DEVICE_CATEGORY_CODE, deviceOperationDetail.getDeviceCategory());
                    deviceMap.put(CmdbAttrConstant.DEVICE_CATEGORY, deviceOperationDetail.getDeviceCategoryName());
                    deviceMap.put(CmdbAttrConstant.DEVICE_TYPE_CODE, deviceOperationDetail.getDeviceType());
                    deviceMap.put(CmdbAttrConstant.DEVICE_TYPE, deviceOperationDetail.getDeviceTypeName());
                    deviceMap.put(CmdbAttrConstant.FULL_NAME, deviceOperationDetail.getFullName());
                    deviceMap.put(CmdbAttrConstant.SN, deviceOperationDetail.getFactoryNumber());
                    if (Objects.nonNull(deviceOperationDetail.getAfterSaleExpDate())) {
                        deviceMap.put(CmdbAttrConstant.AFTER_SALE_EXP_DATE, DATE_FORMAT.format(deviceOperationDetail.getAfterSaleExpDate()));
                    }
                    deviceMap.put(CmdbAttrConstant.IS_IT_AI_CODE, deviceOperationDetail.getIsItal());
                    deviceMap.put(CmdbAttrConstant.BRAND, deviceOperationDetail.getBrandName());
                    deviceMap.put(CmdbAttrConstant.BRAND_CODE, deviceOperationDetail.getBrand());
                    deviceMap.put(CmdbAttrConstant.MAKER, deviceOperationDetail.getMakerName());
                    deviceMap.put(CmdbAttrConstant.MAKER_CODE, deviceOperationDetail.getMaker());
                    deviceMap.put(CmdbAttrConstant.SERIES_CODE, deviceOperationDetail.getSeries());
                    deviceMap.put(CmdbAttrConstant.SERIES, deviceOperationDetail.getSeriesName());
                    deviceMap.put(CmdbAttrConstant.DEVICE_MODEL_CODE, deviceOperationDetail.getDeviceModel());
                    deviceMap.put(CmdbAttrConstant.DEVICE_MODEL, deviceOperationDetail.getDeviceModelName());
                    deviceMap.put(CmdbAttrConstant.CPU_BRAND_CODE, deviceOperationDetail.getCpuBrandCode());
                    deviceMap.put(CmdbAttrConstant.CPU_BRAND, deviceOperationDetail.getCpuBrand());
                    deviceMap.put(CmdbAttrConstant.OS_TYPE_CODE, deviceOperationDetail.getOsTypeCode());
                    deviceMap.put(CmdbAttrConstant.ERP_TRANSFER_STATUS, cmdbCientityProperties.getErpTransferStatus2());
                    deviceMap.put(CmdbAttrConstant.OPRT_DEPT, deviceOperationDetail.getOprtDeptName());
                    deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, deviceOperationDetail.getReceiveUnitName());
                    deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, deviceOperationDetail.getReceiveUnit());
                    deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, deviceOperationDetail.getReceiveDutyDeptName());
                    deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, deviceOperationDetail.getReceiveDutyDept());
                    deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, deviceOperationDetail.getReceiveUseName());
                    deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, deviceOperationDetail.getReceiveUseCard());
                    deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, deviceOperationDetail.getReceiveUsePhone());
                    deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, deviceOperationDetail.getReceiveDutyGroupName());
                    deviceMap.put(CmdbAttrConstant.USER, deviceOperationDetail.getUserName());
                    deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, deviceOperationDetail.getUserCard());
                    deviceMap.put(CmdbAttrConstant.USER_TEL, deviceOperationDetail.getUserPhone());
                    deviceMap.put(CmdbAttrConstant.SUBNET_ID, deviceOperationDetail.getDeviceSubnet());
                    deviceMap.put(CmdbAttrConstant.SUBNET_NAME, deviceOperationDetail.getDeviceSubnetName());
                    deviceMap.put(CmdbAttrConstant.IP, deviceOperationDetail.getDeviceIp());
                    if (StringUtils.isNotBlank(deviceOperationDetail.getDeviceMac())) {
                        deviceMap.put(CmdbAttrConstant.MAC, deviceOperationDetail.getDeviceMac());
                    }
                    deviceMap.put(CmdbAttrConstant.NET_WORK_CODE, deviceOperationDetail.getNetworkType());
                    deviceMap.put(CmdbAttrConstant.INSTALLATION_SITE, deviceOperationDetail.getAddress());
                    deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE_CODE, modelProperties.getNoDeviceSource());
                    deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE, Constants.NO_DEVICE_SOURCE);
                    deviceMap.put(CmdbAttrConstant.OPERATION_UNIT_CODE, deviceOperationDetail.getOperationUnit());
                    deviceMap.put(CmdbAttrConstant.OPERATION_UNIT, deviceOperationDetail.getOperationUnitName());
                    deviceMap.put(CmdbAttrConstant.OPERATION_DEP_CODE, deviceOperationDetail.getOperationDept());
                    deviceMap.put(CmdbAttrConstant.OPERATION_DEPT, deviceOperationDetail.getOperationDeptName());
                    deviceMap.put(CmdbAttrConstant.OPERATION_PERSON, deviceOperationDetail.getOperationUse());
                    deviceMap.put(CmdbAttrConstant.OPERATION_TEL, deviceOperationDetail.getOperationPhone());
                    deviceMap.put(CmdbAttrConstant.OPRT_DATE, DATE_FORMAT.format(Objects.isNull(deviceOperationDetail.getOprtDate()) ?
                            new Date() : deviceOperationDetail.getOprtDate()));
                    deviceMap.put(CmdbAttrConstant.OPRT_DATE_FIRST, DATE_FORMAT.format(new Date()));
                    deviceMap.put(CmdbAttrConstant.DEVICE_CODE, deviceOperationDetail.getDeviceCode());
                    deviceMap.put(CmdbAttrConstant.DEVICE_NAME, deviceOperationDetail.getDeviceName());
                    deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, modelProperties.getInOperation());
                    deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, Constants.IN_OPERATION);
                    deviceMap.put(CmdbAttrConstant.OPERATION_LEVEL, deviceOperationDetail.getOperationGradeCode());
                    deviceMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, deviceOperationDetail.getIs802());
                    deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, "2".equals(user.getUserType()) ? user.getAccount() : "");
                    deviceMap.put(CmdbAttrConstant.IS_TO_ERP_CODE, modelProperties.getNoNo());
                    deviceMap.put(CmdbAttrConstant.IS_TO_I6000, modelProperties.getNoNo());
                    deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, DATE_FORMAT.format(new Date()));
                    deviceMap.put(CmdbAttrConstant.DEVICE_USER_TEAM, deviceOperationDetail.getReceiveDutyGroupName());
                    deviceMap.put(CmdbAttrConstant.DEVICE_USE_DEPT, deviceOperationDetail.getReceiveDutyDeptName());
                    deviceMap.put(CmdbAttrConstant.DEVICE_LEVEL, deviceOperationDetail.getOperationGradeCode());
                    deviceMap.put(CmdbAttrConstant.AREA, user.getRegionCode());
                    deviceMap.put(CmdbAttrConstant.DEPT, user.getDeptId());
                    deviceMap.put(CmdbAttrConstant.IS_GOVERN, cmdbCientityProperties.getGovernYes());
                    deviceMap.put(CmdbAttrConstant.FACTORY_DATE, DATE_FORMAT.format(deviceOperationDetail.getFactoryDate()));
                    deviceMap.put(CmdbAttrConstant.PROCURE_TYPE_CODE, modelProperties.getProcureType1());
                    deviceMap.put(CmdbAttrConstant.OWNER_UNIT_CODE, deviceOperationDetail.getOwnerUnitCode());
                    deviceMap.put(CmdbAttrConstant.OWNER_UNIT, deviceOperationDetail.getOwnerUnit());
                    deviceMap.put(CmdbAttrConstant.PROPERTY_DEPT_CODE, deviceOperationDetail.getPropertyDeptCode());
                    deviceMap.put(CmdbAttrConstant.PROPERTY_DEPT, deviceOperationDetail.getPropertyDept());
                    deviceMap.put(CmdbAttrConstant.VOLTAGE_LEVEL, deviceOperationDetail.getVoltageLevel());
                    deviceMap.put(CmdbAttrConstant.VOLTAGE_LEVEL_CODE, deviceOperationDetail.getVoltageLevelCode());
                    deviceMap.put(CmdbAttrConstant.HARD_DISK_TYPE_CODE, deviceOperationDetail.getHardDiskType());
                    if (Objects.isNull(deviceOperationDetail.getOprtDateFirst())) {
                        deviceOperationDetail.setOprtDateFirst(new Date());
                    }
                    deviceMap.put(CmdbAttrConstant.OPRT_DATE_FIRST, DATE_FORMAT.format(deviceOperationDetail.getOprtDateFirst()));
                    deviceMap.put(CmdbAttrConstant.STANDBY_ATTR, deviceOperationDetail.getStandbyAttr());
                    deviceMap.put(CmdbAttrConstant.SECURITY_BOUNDARY, deviceOperationDetail.getSecurityBoundary());
                    if (StringUtil.isNotBlank(deviceOperationDetail.getMemSize())) {
                        deviceMap.put(CmdbAttrConstant.MEM_SIZE, deviceOperationDetail.getMemSize());
                    }
                    deviceMap.put(CmdbAttrConstant.RATED_POWER, deviceOperationDetail.getRatedPower());
                    deviceMap.put(CmdbAttrConstant.POWER_MODEL, deviceOperationDetail.getPowerModel());
                    deviceMap.put(CmdbAttrConstant.IS_CLOUD_MANGE, deviceOperationDetail.getIsCloudMange());
                    deviceMap.put(CmdbAttrConstant.SERVER_USE_TO_TYPE, deviceOperationDetail.getServerUseToType());
                    deviceMap.put(CmdbAttrConstant.HARD_DISK_CAPABILITY, deviceOperationDetail.getHardDiskCapability());
                    deviceMap.put(CmdbAttrConstant.PDU_RATED_POWER, deviceOperationDetail.getPduRatedPower());
                    deviceMap.put(CmdbAttrConstant.PDU_OPERATE_POWER, deviceOperationDetail.getPduOperatePower());
                    deviceMap.put(CmdbAttrConstant.OS_ISSUE_VERSION, deviceOperationDetail.getOsIssueVersion());
                    deviceMap.put(CmdbAttrConstant.SOURCE_SYSTEM, cmdbCientityProperties.getSourceSystem2());
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    deviceOperationDetail.setDeviceUuid(uuid);
                    deviceOperationDetail.setDeviceCid(ciId.toString());
                    deviceOperationDetailDTOList.stream().filter(deviceOperationDetailDTO ->
                                    deviceOperationDetailDTO.getId().equals(deviceOperationDetail.getId()))
                            .forEach(deviceOperationDetailDTO -> {
                                deviceOperationDetailDTO.setDeviceCode(deviceOperationDetail.getDeviceCode());
                                deviceOperationDetailDTO.setDeviceUuid(uuid);
                            });
                    map.put(uuid, deviceMap);
					/*if (StringUtils.isNotBlank(deviceCiTypeId)) {
						deviceMap.put(CmdbAttrConstant.CITYPE_ID, deviceCiTypeId);
						hashMap.put(uuid, deviceMap);
					}*/
                }
                Map<String, Object> stringObjectMap = iCmdbService.cientityBatchsave(ciId, map, TransactionActionType.INSERT);
                // 回填设备id等信息
                operationDetailDTOList.forEach(item -> {
                    Object deviceId = stringObjectMap.get(item.getDeviceUuid());
                    if (Objects.nonNull(deviceId)) {
                        item.setDeviceId(deviceId.toString());
                    }
                });
				/*if (hashMap.size() > 0) {
					// 同步i6000
					i6000Service.i6000Batchsave(deviceCiTypeId, hashMap);
				}*/
            }
            deviceOperationDTO.setDeviceOperationDetailDTOList(deviceOperationDetailDTOList);
        }
    }

    /**
     * 更新cmdb系统设备信息
     *
     * @param deviceOperationDetailList 设备信息
     * @param oldToNew                  是否以旧换新标识
     * @param deviceApply               申请信息
     * @param deviceOperationDTO        投运设备信息
     */
    public void updateCmdbDevice(List<DeviceOperationDetail> deviceOperationDetailList, String oldToNew, DeviceApply deviceApply, DeviceOperationDTO deviceOperationDTO) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        IdevelopUser user = SecureUtil.getUser();
        List<String> deviceIdList = deviceOperationDetailList.stream().map(DeviceOperationDetail::getDeviceId).collect(Collectors.toList());
        List<DeviceOperationDetail> list = operationDetailService.list(new LambdaQueryWrapper<DeviceOperationDetail>().ne(DeviceOperationDetail::getOperationId,
                deviceOperationDetailList.get(0).getOperationId()).in(DeviceOperationDetail::getDeviceId, deviceIdList).eq(DeviceOperationDetail::getDeviceStatus, modelProperties.getInOperation()));
        // List<String> switchesTypeList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
        Map<Long, Map<String, Object>> hashMap = new HashMap<>();
        Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
        String deviceType = deviceOperationDTO.getDeviceType();
        String deviceCiTypeId = erpI6000MapByCiId.get(deviceType);
        if ("0".equals(oldToNew)) {
            Map<Long, Map<String, Object>> oldHashMap = new HashMap<>();
            Map<String, Map<String, Object>> i6000OldHashMap = new HashMap<>();
            // 修改 资产台账
            deviceOperationDetailList.forEach(deviceOperationDetail -> {
                HashMap<String, Object> deviceMap = new HashMap<>();
                deviceMap.put(CmdbAttrConstant.OPRT_DEPT, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_USE_DEPT, "");
                deviceMap.put(CmdbAttrConstant.OPERATION_CHAGE_ACCOUNT, "");
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM, "");
                deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, "");
                deviceMap.put(CmdbAttrConstant.CABINET_CODE, "");
                deviceMap.put(CmdbAttrConstant.CABINET, "");
                deviceMap.put(CmdbAttrConstant.IP, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, deviceOperationDetail.getReturnDeviceStatus());
                deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, modelProperties.getReturnWarehouse().equals(deviceOperationDetail.getReturnDeviceStatus()) ?
                        Constants.IN_WAREHOUSE : Constants.WAIT_SCARP);
                deviceMap.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, deviceOperationDetail.getReturnWarehouse());
                deviceMap.put(CmdbAttrConstant.IN_WAREHOUSE, deviceOperationDetail.getReturnWarehouseName());
                deviceMap.put(CmdbAttrConstant.WAREHOUSE_LOCATION, deviceOperationDetail.getReturnAddress());
                deviceMap.put(CmdbAttrConstant.SUBNET_ID, "");
                deviceMap.put(CmdbAttrConstant.SUBNET_NAME, "");
                deviceMap.put(CmdbAttrConstant.USER, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_TEAM, "");
                deviceMap.put(CmdbAttrConstant.USER_TEL, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, "");
                deviceMap.put(CmdbAttrConstant.USER_EMAIL, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, "");
                deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, "");
                deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, "");
                deviceMap.put(CmdbAttrConstant.MANAGE_USERS, "");
                deviceMap.put(CmdbAttrConstant.MANAGE_PASSWORD, "");
                deviceMap.put(CmdbAttrConstant.SWITCH_PASSWORD, "");
                deviceMap.put(CmdbAttrConstant.SNMP_READ_STRING, "");
                deviceMap.put(CmdbAttrConstant.SNMP_WRITE_STRING, "");
                deviceMap.put(CmdbAttrConstant.SNMP_VERSION, "");
                deviceMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, "");
                deviceMap.put(CmdbAttrConstant.WORK_VLAN, "");
                deviceMap.put(CmdbAttrConstant.CONFIG_PASSWORD, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, "");
                deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_END, "");
                deviceMap.put(CmdbAttrConstant.IS_TO_ERP_CODE, modelProperties.getYesNo());
                deviceMap.put(CmdbAttrConstant.IS_TO_I6000, modelProperties.getYesNo());
                deviceMap.put(CmdbAttrConstant.CI_ID, deviceOperationDetail.getOldDeviceCid());
                deviceMap.put(CmdbAttrConstant.UUID, deviceOperationDetail.getOldDeviceUuid());
                oldHashMap.put(Long.parseLong(deviceOperationDetail.getOldDeviceId()), deviceMap);
                if (StringUtils.isNotBlank(deviceCiTypeId)) {
                    deviceMap.put(CmdbAttrConstant.CITYPE_ID, deviceCiTypeId);
                    i6000OldHashMap.put(deviceOperationDetail.getOldDeviceUuid(), deviceMap);
                }
            });
            //保存cmdb
            iCmdbService.cientityBatchupdate(oldHashMap, TransactionActionType.UPDATE);
            if (i6000OldHashMap.size() > 0) {
                i6000Service.i6000Batchupdate(i6000OldHashMap);
            }
        }
        Map<String, Map<String, Object>> operationDeviceMap = new HashMap<>();
        // 修改 资产台账
        deviceOperationDetailList.forEach(deviceOperationDetail -> {
            Map<String, Object> deviceMap = new HashMap<>();
            deviceMap.put(CmdbAttrConstant.OPRT_DEPT, deviceOperationDetail.getOprtDeptName());
            deviceMap.put(CmdbAttrConstant.DEVICE_USE_DEPT, deviceOperationDetail.getReceiveDutyDeptName());
            deviceMap.put(CmdbAttrConstant.OPERATION_CHAGE_ACCOUNT, "2".equals(user.getUserType()) ? user.getAccount() : "");
            deviceMap.put(CmdbAttrConstant.DEVICE_USER_TEAM, deviceApply.getReceiveDutyGroupName());
            deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE_CODE, deviceOperationDetail.getDeviceSource());
            deviceMap.put(CmdbAttrConstant.DEVICE_SOURCE, modelProperties.getDeviceSource().equals(deviceOperationDetail.getDeviceSource()) ? Constants.DEVICE_SOURCE : Constants.NO_DEVICE_SOURCE);
            deviceMap.put(CmdbAttrConstant.DEVICE_STATUS_CODE, modelProperties.getInOperation());
            deviceMap.put(CmdbAttrConstant.DEVICE_STATUS, Constants.IN_OPERATION);
            deviceMap.put(CmdbAttrConstant.USER, deviceOperationDetail.getUserName());
            deviceMap.put(CmdbAttrConstant.USER_TEL, deviceOperationDetail.getUserPhone());
            deviceMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, deviceOperationDetail.getUserCard());
            deviceMap.put(CmdbAttrConstant.DEVICE_CODE, deviceOperationDetail.getDeviceCode());
            deviceMap.put(CmdbAttrConstant.DEVICE_NAME, deviceOperationDetail.getDeviceName());
            deviceMap.put(CmdbAttrConstant.ASSET_CODE_ERP, deviceOperationDetail.getErpAssetCode());
            deviceMap.put(CmdbAttrConstant.SN, deviceOperationDetail.getFactoryNumber());
            deviceMap.put(CmdbAttrConstant.INSTALLATION_SITE, deviceOperationDetail.getAddress());
            if (Objects.nonNull(deviceOperationDetail.getUserTime())) {
                deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, DATE_FORMAT.format(deviceOperationDetail.getUserTime()));
            }
            deviceMap.put(CmdbAttrConstant.SUBNET_ID, deviceOperationDetail.getDeviceSubnet());
            deviceMap.put(CmdbAttrConstant.SUBNET_NAME, deviceOperationDetail.getDeviceSubnetName());
            deviceMap.put(CmdbAttrConstant.NET_WORK_CODE, deviceOperationDetail.getNetworkType());
            deviceMap.put(CmdbAttrConstant.IP, deviceOperationDetail.getDeviceIp());
            if (StringUtils.isNotBlank(deviceOperationDetail.getDeviceMac())) {
                deviceMap.put(CmdbAttrConstant.MAC, deviceOperationDetail.getDeviceMac());
            }
            deviceMap.put(CmdbAttrConstant.OPERATION_UNIT_CODE, deviceOperationDetail.getOperationUnit());
            deviceMap.put(CmdbAttrConstant.OPERATION_UNIT, deviceOperationDetail.getOperationUnitName());
            deviceMap.put(CmdbAttrConstant.OPERATION_DEP_CODE, deviceOperationDetail.getOperationDept());
            deviceMap.put(CmdbAttrConstant.OPERATION_DEPT, deviceOperationDetail.getOperationDeptName());
            deviceMap.put(CmdbAttrConstant.OPERATION_PERSON, deviceOperationDetail.getOperationUse());
            deviceMap.put(CmdbAttrConstant.OPERATION_LEVEL, deviceOperationDetail.getOperationGradeCode());
            deviceMap.put(CmdbAttrConstant.OPERATION_TEL, deviceOperationDetail.getOperationPhone());
            if (ObjectUtil.isNotEmpty(deviceOperationDetail.getOprtDate())) {
                deviceMap.put(CmdbAttrConstant.OPRT_DATE, DATE_FORMAT.format(deviceOperationDetail.getOprtDate()));
            } else {
                deviceMap.put(CmdbAttrConstant.OPRT_DATE, DATE_FORMAT.format(new Date()));
            }
            if (ObjectUtil.isNotEmpty(deviceOperationDetail.getOprtDateFirst())) {
                deviceMap.put(CmdbAttrConstant.OPRT_DATE_FIRST, DATE_FORMAT.format(deviceOperationDetail.getOprtDateFirst()));
            } else {
                deviceMap.put(CmdbAttrConstant.OPRT_DATE, DATE_FORMAT.format(new Date()));
            }
            deviceMap.put(CmdbAttrConstant.CI_ID, deviceOperationDetail.getDeviceCid());
            deviceMap.put(CmdbAttrConstant.UUID, deviceOperationDetail.getDeviceUuid());
            deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT, deviceOperationDetail.getReceiveUnitName());
            deviceMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, deviceOperationDetail.getReceiveUnit());
            deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT, deviceOperationDetail.getReceiveDutyDeptName());
            deviceMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, deviceOperationDetail.getReceiveDutyDept());
            deviceMap.put(CmdbAttrConstant.RECEIVING_PERSON, deviceOperationDetail.getReceiveUseName());
            deviceMap.put(CmdbAttrConstant.RECEIVING_TEL, deviceOperationDetail.getReceiveUsePhone());
            deviceMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, deviceOperationDetail.getReceiveUseCard());
            deviceMap.put(CmdbAttrConstant.RECEIVING_DATE, DATE_FORMAT.format(new Date()));
            deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM, deviceOperationDetail.getRoomName());
            deviceMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, deviceOperationDetail.getRoomId());
            deviceMap.put(CmdbAttrConstant.CABINET_CODE, deviceOperationDetail.getCabinetsId());
            deviceMap.put(CmdbAttrConstant.CABINET, deviceOperationDetail.getCabinetsName());
            deviceMap.put(CmdbAttrConstant.RECEIVING_GROUP, deviceOperationDetail.getReceiveDutyGroupName());
            deviceMap.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, deviceOperationDetail.getReceiveUsePhone());
            deviceMap.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, deviceApply.getReceiveDutyIscAccount());
            deviceMap.put(CmdbAttrConstant.MANAGE_USERS, deviceOperationDetail.getManageUser());
            deviceMap.put(CmdbAttrConstant.MANAGE_PASSWORD, deviceOperationDetail.getManagePassword());
            deviceMap.put(CmdbAttrConstant.SWITCH_PASSWORD, deviceOperationDetail.getSwitchesPassword());
            deviceMap.put(CmdbAttrConstant.SNMP_READ_STRING, deviceOperationDetail.getSnmpRead());
            deviceMap.put(CmdbAttrConstant.SNMP_WRITE_STRING, deviceOperationDetail.getSnmpWrite());
            deviceMap.put(CmdbAttrConstant.SNMP_VERSION, deviceOperationDetail.getSnmpVersion());
            deviceMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, deviceOperationDetail.getIs802());
            deviceMap.put(CmdbAttrConstant.WORK_VLAN, deviceOperationDetail.getVlanNumber());
            deviceMap.put(CmdbAttrConstant.CONFIG_PASSWORD, deviceOperationDetail.getAllocationPassword());
            deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN, deviceOperationDetail.getDeviceStartHeight());
            deviceMap.put(CmdbAttrConstant.DEVICE_HEIGHT_END, deviceOperationDetail.getDeviceEndHeight());
            deviceMap.put(CmdbAttrConstant.IS_TO_ERP_CODE, modelProperties.getYesNo());
            deviceMap.put(CmdbAttrConstant.IS_TO_I6000, modelProperties.getYesNo());
            if (StringUtil.isNotBlank(deviceOperationDetail.getVoltageLevel())) {
                deviceMap.put(CmdbAttrConstant.VOLTAGE_LEVEL, deviceOperationDetail.getVoltageLevel());
                deviceMap.put(CmdbAttrConstant.VOLTAGE_LEVEL_CODE, deviceOperationDetail.getVoltageLevelCode());
            }
            deviceMap.put(CmdbAttrConstant.HARD_DISK_TYPE_CODE, deviceOperationDetail.getHardDiskType());
            deviceMap.put(CmdbAttrConstant.STANDBY_ATTR, deviceOperationDetail.getStandbyAttr());
            deviceMap.put(CmdbAttrConstant.OS_VERSION, deviceOperationDetail.getOsVersion());
            deviceMap.put(CmdbAttrConstant.OS_TYPE_CODE, deviceOperationDetail.getOsTypeCode());
            deviceMap.put(CmdbAttrConstant.SECURITY_BOUNDARY, deviceOperationDetail.getSecurityBoundary());
            if (StringUtil.isNotBlank(deviceOperationDetail.getMemSize())) {
                deviceMap.put(CmdbAttrConstant.MEM_SIZE, deviceOperationDetail.getMemSize());
            }
            deviceMap.put(CmdbAttrConstant.RATED_POWER, deviceOperationDetail.getRatedPower());
            deviceMap.put(CmdbAttrConstant.POWER_MODEL, deviceOperationDetail.getPowerModel());
            deviceMap.put(CmdbAttrConstant.IS_CLOUD_MANGE, deviceOperationDetail.getIsCloudMange());
            deviceMap.put(CmdbAttrConstant.SERVER_USE_TO_TYPE, deviceOperationDetail.getServerUseToType());
            if (StringUtil.isNotBlank(deviceOperationDetail.getHardDiskCapability())) {
                deviceMap.put(CmdbAttrConstant.HARD_DISK_CAPABILITY, deviceOperationDetail.getHardDiskCapability());
            }
            deviceMap.put(CmdbAttrConstant.PDU_RATED_POWER, deviceOperationDetail.getPduRatedPower());
            deviceMap.put(CmdbAttrConstant.PDU_OPERATE_POWER, deviceOperationDetail.getPduOperatePower());
            if (StringUtil.isNotBlank(deviceOperationDetail.getNetworkDeviceType())) {
                deviceMap.put(CmdbAttrConstant.NETWORK_DEVICE_TYPE, deviceOperationDetail.getNetworkDeviceType());
            }
            deviceMap.put(CmdbAttrConstant.OS_ISSUE_VERSION, deviceOperationDetail.getOsIssueVersion());
            hashMap.put(Long.parseLong(deviceOperationDetail.getDeviceId()), deviceMap);
            if (StringUtils.isNotBlank(deviceCiTypeId)) {
                deviceMap.put(CmdbAttrConstant.CITYPE_ID, deviceCiTypeId);
                operationDeviceMap.put(deviceOperationDetail.getDeviceUuid(), deviceMap);
            }
        });
        //保存cmdb
        iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
        if (operationDeviceMap.size() > 0) {
            i6000Service.i6000Batchupdate(operationDeviceMap);
        }
    }

    /**
     * 新增设备投运工单详情数据
     *
     * @param deviceOperationDTO 设备投运信息
     * @param user               用户信息
     */
    private void batchInsertDeviceOperationDetail(DeviceOperationDTO deviceOperationDTO, IdevelopUser user) {
        if (CollectionUtil.isNotEmpty(deviceOperationDTO.getDeviceOperationDetailDTOList())) {
            List<DeviceOperationDetail> deviceOperationDetailList = Convert.convert(new TypeReference<List<DeviceOperationDetail>>() {
            }, deviceOperationDTO.getDeviceOperationDetailDTOList());
            deviceOperationDetailList.forEach(item -> {
                item.setId(null);
                item.setOperationId(deviceOperationDTO.getId());
                item.setOperationUnit(deviceOperationDTO.getOperationUnit());
                item.setOperationUnitName(deviceOperationDTO.getOperationUnitName());
                item.setOperationDept(deviceOperationDTO.getOperationDept());
                item.setOperationDeptName(deviceOperationDTO.getOperationDeptName());
                item.setOperationUse(deviceOperationDTO.getOperationUseName());
                item.setOperationPhone(deviceOperationDTO.getOperationPhone());
                item.setDeviceSource(modelProperties.getNoDeviceSource());
                item.setDeviceStatus(modelProperties.getInOperation());
                item.setUserTime(new Date());
                item.setCreateTime(new Date());
                item.setCreateUser(user.getUserId());
                item.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                item.setFactoryDate(new Date());
                item.setProcureTypeCode(modelProperties.getProcureType1());
                item.setOwnerUnitCode(deviceOperationDTO.getOwnerUnitCode());
                item.setOwnerUnit(deviceOperationDTO.getOwnerUnit());
                item.setPropertyDeptCode(deviceOperationDTO.getPropertyDeptCode());
                item.setPropertyDept(deviceOperationDTO.getPropertyDept());
            });
            operationDetailService.saveBatch(deviceOperationDetailList);
        }
    }

    /**
     * 新增设备工单附件
     *
     * @param orderType          工单类型
     * @param deviceOperationDTO 投运单信息
     */
    private void batchInsertDeviceFile(String orderType, DeviceOperationDTO deviceOperationDTO) {
        deviceOrderFileService.remove(new LambdaQueryWrapper<DeviceOrderFile>().eq(DeviceOrderFile::getId, deviceOperationDTO.getId()).eq(DeviceOrderFile::getOrderType, orderType)
                .eq(DeviceOrderFile::getOrderFileType, OrderFileTypeEnum.getFileType(orderType)));
        if (CollectionUtil.isNotEmpty(deviceOperationDTO.getDeviceOrderFileDTOList())) {
            IdevelopUser user = SecureUtil.getUser();
            List<DeviceOrderFileDTO> deviceOrderFileDTOList = deviceOperationDTO.getDeviceOrderFileDTOList()
                    .stream().filter(deviceOrderFileDTO -> orderType.equals(deviceOrderFileDTO.getOrderType())).collect(Collectors.toList());
            deviceOrderFileDTOList.forEach(item -> {
                item.setOrderId(deviceOperationDTO.getId());
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
     * 工单审核之前校验设备信息
     *
     * @param deviceOperationDTO 投运信息
     * @return R
     */
    @Override
    public R<Integer> checkDeviceOperation(DeviceOperationDTO deviceOperationDTO) {
        IdevelopUser user = SecureUtil.getUser();
        List<DeviceOperationDetailDTO> deviceOperationDetailDTOList = deviceOperationDTO.getDeviceOperationDetailDTOList();
        if (deviceOperationDTO.getOldToNew().equals("1")) {
            if (modelProperties.getT105().equals(deviceOperationDTO.getDeviceCategory())) {
                List<DeviceOperationDetailDTO> emptyDeviceList = deviceOperationDetailDTOList.stream().filter(deviceOperationDetailDTO ->
                        StringUtils.isBlank(deviceOperationDetailDTO.getDeviceMac()) || StringUtils.isBlank(deviceOperationDetailDTO.getIs802())
                ).collect(Collectors.toList());
                if (emptyDeviceList.size() > 0) {
                    return R.fail("请完善设备网络信息");
                }
                List<String> modelIdList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
                List<DeviceOperationDetailDTO> deviceOperationDetailList = new ArrayList<>();
                Map<String, List<DeviceOperationDetailDTO>> deviceTypeMap = deviceOperationDTO.getDeviceOperationDetailDTOList().stream()
                        .collect(Collectors.groupingBy(DeviceOperationDetailDTO::getDeviceType));
                for (Map.Entry<String, List<DeviceOperationDetailDTO>> entry : deviceTypeMap.entrySet()) {
                    if (!modelIdList.contains(entry.getKey())) {
                        deviceOperationDetailList.addAll(entry.getValue());
                    }
                }
                if (CollectionUtils.isNotEmpty(deviceOperationDetailList)) {
                    Map<String, List<DeviceOperationDetailDTO>> collect = deviceOperationDetailList.stream().collect(Collectors.groupingBy(DeviceOperationDetailDTO::getDeviceMac));
                    if (collect.size() < deviceOperationDetailList.size()) {
                        return R.fail("设备MAC地址重复，请重新录入");
                    }
                }
            }
            if (modelProperties.getT10302().equals(deviceOperationDTO.getDeviceType())) {
                List<DeviceOperationDetailDTO> collect = deviceOperationDetailDTOList.stream().filter(deviceOperationDetailDTO ->
                        StringUtils.isBlank(deviceOperationDetailDTO.getSwitchesIp()) || StringUtils.isBlank(deviceOperationDetailDTO.getSwitchesPassword())
                                || StringUtils.isBlank(deviceOperationDetailDTO.getNetworkDeviceType())).collect(Collectors.toList());
                if (collect.size() > 0) {
                    return R.fail("请完善交换机信息");
                }
            }
        }
        if (deviceOperationDTO.getOldToNew().equals("0")) {
            List<DeviceOperationDetailDTO> returnDeviceList = deviceOperationDetailDTOList.stream().filter(deviceOperationDetailDTO ->
                    StringUtils.isBlank(deviceOperationDetailDTO.getDeviceMac()) ||
                            StringUtils.isBlank(deviceOperationDetailDTO.getReturnWarehouse()) ||
                            StringUtils.isBlank(deviceOperationDetailDTO.getReturnDeviceStatus())).collect(Collectors.toList());
            if (returnDeviceList.size() > 0) {
                return R.fail("请完善设备归还信息");
            }
        }
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 生成标准全称
     *
     * @param deviceType 设备类型
     * @return R
     */
    @Override
    public R<String> createFullName(String deviceType) {
        String deviceFullName = orderNumberUtil.getDeviceFullName("1", null, null, deviceType);
        return R.success(deviceFullName);
    }

}

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
package com.lnsoft.device.api.operation.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.*;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransEqunrItem;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.erp.service.IErpSyncService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.operation.dto.DeviceChangeDTO;
import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.entity.DeviceChangeLogs;
import com.lnsoft.device.api.operation.mapper.DeviceChangeMapper;
import com.lnsoft.device.api.operation.service.*;
import com.lnsoft.device.api.operation.vo.DeviceChangeVO;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSubnetMapper;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessUserAccessMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessIppoolService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.stock.service.II6000ERPService;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceScrap;
import com.lnsoft.device.api.warehouse.entity.DeviceScrapList;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnUserAccess;
import com.lnsoft.device.api.warehouse.service.IDSwitcherSyncService;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapListService;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.dto.SafeaccessUserAccessDTO;
import com.lnsoft.device.entity.*;
import com.lnsoft.device.eums.ErpOperationEnum;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.ChangeUtils;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备变更 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Service
@Slf4j
public class DeviceChangeServiceImpl extends BaseServiceImpl<DeviceChangeMapper, DeviceChange> implements IDeviceChangeService {
    @Resource
    private IDeviceChangeLogsService deviceChangeLogsService;
    @Resource
    private IDeviceChangeListService deviceChangeListService;
    @Resource
    private OrderNumberUtil orderNumberUtil;
    @Resource
    private ILogOptService logOptService;
    @Resource
    private IHussarBpmService hussarBpmService;
    @Resource
    private IApproveRecordService approveRecordService;
    @Resource
    private ICmdbService cmdbService;
    @Resource
    private II6000Service ii6000Service;
    @Resource
    private IDeviceRepairListService deviceRepairListService;
    @Resource
    private IDeviceRepairService deviceRepairService;
    @Resource
    private SafeaccessSubnetMapper safeaccessSubnetMapper;
    @Resource
    private SafeaccessUserAccessMapper safeaccessUserAccessMapper;
    @Resource
    private ISafeaccessIppoolService safeaccessIppoolService;
    @Resource
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private CmdbCientityProperties modelProperties;
    @Resource
    private IDSwitcherSyncService idSwitcherSyncService;
    @Resource
    private IDeviceScrapListService deviceScrapListService;
    @Resource
    private IDeviceScrapService deviceScrapService;
    @Resource
    private ISafeaccessSubnetService safeaccessSubnetService;
    @Resource
    private IErpService erpService;
    @Resource
    private ICmdbClient cmdbClient;
    @Resource
    private II6000ERPService i6000ERPService;
    @Resource
    private ISafeaccessSwitcheService safeaccessSwitcheService;
    @Resource
    private IErpSyncService i6000ErpService;
    @Value(value = "${third.api-erp}")
    private boolean erpPush;

    @Override
    public IPage<DeviceChangeVO> selectDeviceChangePage(IPage<DeviceChangeVO> page, DeviceChangeVO deviceChange) {
        return page.setRecords(baseMapper.selectDeviceChangePage(page, deviceChange));
    }

    @Override
    public R<IPage<DeviceChange>> deviceChangeList(DeviceChangeDTO deviceChange, Query query) {
        IdevelopUser user = SecureUtil.getUser();
        deviceChange.setRegionCode(user.getRegionCode());
        IPage<DeviceChange> list = baseMapper.findPage(Condition.getPage(query), deviceChange);
        return R.data(list);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<DeviceChangeVO> add(DeviceChangeDTO deviceChangeDTO) throws Exception {
        R check = check(deviceChangeDTO);
        if (ResultCode.FAILURE.getCode() == check.getCode()) {
            return check;
        }
        DeviceChange change = new DeviceChange();
        BeanUtils.copyProperties(deviceChangeDTO, change);
        IdevelopUser user = SecureUtil.getUser();
        change.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        if (ObjectUtil.isEmpty(change.getTicketCreatType())) {
            change.setTicketCreatType(DeviceChangeTypeEnum.MANUAL.getCode());
        }
        change.setRegionCode(user.getRegionCode());
        if (StringUtil.isEmpty(deviceChangeDTO.getFilingNo())) {
            change.setTicketStatus(DeviceChangeEnum.TEMPORARILY.getCode());
            change.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.BG));
        }
        change.setUpdateUser(user.getUserId());
        change.setUpdateTime(new Date());
        if (DeviceChangeSubmitEnum.WAIT_APPROVAL.getCode().equals(deviceChangeDTO.getSubmitStatus())) {
            change.setTicketStatus(DeviceChangeEnum.WAIT_APPROVAL.getCode());
            change.setCreateUser(user.getUserId());
        }
        String id = deviceChangeDTO.getId();

        if ("".equals(id) || StringUtil.isBlank(id)) {
            //保存工单表
            change.setCreateTime(new Date());
            baseMapper.insert(change);
            // 增加操作记录
            if (DeviceChangeSubmitEnum.TEMPORARILY.getCode().equals(deviceChangeDTO.getSubmitStatus())) {
                logOptService.commonLogOpt(LogOpt.builder().logId(change.getId()).logData(deviceChangeDTO.toString()).params(deviceChangeDTO.toString())
                        .optRole("--").optType(OptTypeEnum.DEVICE_CHANGE.getCode()).title("新增暂存设备变更").build());
            }
            id = change.getId();
        } else {
            change.setUpdateTime(new Date());
            change.setUpdateUser(user.getUserId());
            //修改工单表
            baseMapper.updateById(change);
            // 增加操作记录
            if ((DeviceChangeSubmitEnum.TEMPORARILY.getCode()).equals(deviceChangeDTO.getSubmitStatus())) {
                logOptService.commonLogOpt(LogOpt.builder().logId(change.getId()).logData(deviceChangeDTO.toString()).params(deviceChangeDTO.toString())
                        .optRole("--").optType(OptTypeEnum.DEVICE_CHANGE.getCode()).title("修改暂存设备变更").build());
            }
        }
        //保存设备参数填充(需求已变更)
//		DeviceChangeDTO changeDTO = stuff(deviceChangeDTO);
        List<DeviceChangeList> oldChangeDeviceDTOList = deviceChangeDTO.getOldChangeDeviceDTOList();
        List<DeviceChangeList> newChangeDeviceDTOList = deviceChangeDTO.getNewChangeDeviceDTOList();
        int i = deviceChangeListService.removeByChangeId(id);
        //保存设备表
        if (newChangeDeviceDTOList != null && newChangeDeviceDTOList.size() > 0) {
            for (DeviceChangeList device : newChangeDeviceDTOList) {
                if (ObjectUtil.isEmpty(device.getDeviceId())) {
                    device.setDeviceId(device.getId());
                }
                device.setId(null);
                device.setChangeId(id);
                deviceChangeListService.save(device);
            }
        }
        if (oldChangeDeviceDTOList != null && oldChangeDeviceDTOList.size() > 0) {
            for (DeviceChangeList device : oldChangeDeviceDTOList) {
                device.setDeviceId(device.getId());
            }
        }
        //保存记录表
        for (DeviceChangeList oldDevice : oldChangeDeviceDTOList) {
            for (DeviceChangeList newDevice : newChangeDeviceDTOList) {
                if (oldDevice.getUuid().equals(newDevice.getUuid())) {
                    HashMap<String, List<Object>> map = ChangeUtils.getChangedFields(oldDevice, newDevice);
                    for (String key : map.keySet()) {
                        DeviceChangeLogs logs = new DeviceChangeLogs();
                        List<Object> values = map.get(key);
                        LambdaQueryWrapper<DeviceChangeLogs> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(DeviceChangeLogs::getChangeId, newDevice.getChangeId());
                        queryWrapper.eq(DeviceChangeLogs::getDeviceCode, newDevice.getDeviceCode());
                        queryWrapper.eq(DeviceChangeLogs::getAttributeName, key);
                        DeviceChangeLogs one = deviceChangeLogsService.getOne(queryWrapper);
                        if (one != null) {
                            logs.setId(one.getId());
                            logs.setChangeAfter(values.get(1).toString());
                            if (one.getChangeBefore().equals(values.get(1).toString())) {
                                deviceChangeLogsService.deleteOne(one.getId());
                            } else {
                                deviceChangeLogsService.updateById(logs);
                            }
                        } else {
                            logs.setAttributeName(key);
                            logs.setChangeBefore(values.get(0).toString());
                            logs.setChangeAfter(values.get(1).toString());
                            logs.setChangeId(id);
                            logs.setDeviceCode(newDevice.getDeviceCode());
                            deviceChangeLogsService.saveOrUpdate(logs);
                        }
                    }
                }
            }
        }
        //如果是提交，发起流程
        if ((DeviceChangeSubmitEnum.WAIT_APPROVAL.getCode()).equals(deviceChangeDTO.getSubmitStatus())) {
            String roleName = null;
            String processInsId = null;
            if (StringUtil.isNotBlank(deviceChangeDTO.getProcessInsId())) {
                //  获取当前节点操作角色
                List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(deviceChangeDTO.getFilingNo());
                roleName = hussarAssignVos.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
                HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
                hussarBpmDTO.setBusinessKey(deviceChangeDTO.getFilingNo());
                hussarBpmDTO.setParticipantType("2");
                hussarBpmDTO.setTaskType("1");
                hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark());
                hussarBpmService.hussarSubmit(hussarBpmDTO);
            } else {
                Map<String, Object> variable = new HashMap<>();
                variable.put("orderId", change.getId());
                variable.put("orderNo", change.getFilingNo());
                variable.put("userId", user.getUserId());
                variable.put("userName", user.getUserName());
                variable.put("regionCode", user.getRegionCode());
                variable.put(DeviceConstant.APPROVAL_OPINION, "发起设备变更申请");
                HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO
                        .builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark())
                        .businessKey(change.getFilingNo()).variable(variable).build();
                HussarCreateVo hussarBpm;
                // 发起流程
                try {
                    hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
                    //  获取当前节点操作角色
                    List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(change.getFilingNo());
                    roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
                    processInsId = hussarBpm.getProcessInsId();
                } catch (Exception e) {
                    throw new Exception("创建流程发生异常");
                }
                HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
                hussarBpmDTO.setBusinessKey(change.getFilingNo());
                hussarBpmDTO.setParticipantType("2");
                hussarBpmDTO.setTaskType("1");
                hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark());
                hussarBpmService.hussarSubmit(hussarBpmDTO);
            }


            // 记录流程id
            baseMapper.update(new LambdaUpdateWrapper<DeviceChange>().eq(DeviceChange::getId, change.getId())
                    .set(StringUtils.isNotBlank(processInsId), DeviceChange::getProcessInsId, processInsId)
                    .set(DeviceChange::getProcessStatus, DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode()));
            // 增加操作记录
            logOptService.commonLogOpt(LogOpt.builder().logId(change.getId()).logData(deviceChangeDTO.toString()).params(deviceChangeDTO.toString())
                    .optRole(roleName).optType(OptTypeEnum.DEVICE_CHANGE.getCode()).title("发起设备变更申请").build());
            // 记录审核流程
            approveRecordService.commonRecord(ApproveRecord.builder().filingNo(change.getId())
                    .optRole(roleName).optType(OptTypeEnum.DEVICE_CHANGE.getCode()).nodeId(DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode())
                    .nodeName(DeviceChangeBpmNodeEnum.getMessage(DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode()))
                    .optTitle("发起设备变更申请").optOpinion("发起设备变更申请").filingCode(change.getFilingNo()).approveStatus(0).build());
            return R.data(Convert.convert(DeviceChangeVO.class, change));
        }
        return R.data(Convert.convert(DeviceChangeVO.class, change));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R delete(String ids) {
        List<String> idList = Func.toStrList(ids);
        if (idList != null && idList.size() > 0) {
            for (String id : idList) {
                DeviceChange deviceChange = baseMapper.selectById(id);
                if (Objects.isNull(deviceChange)) {
                    continue;
                }
                if (deviceChange.getTicketStatus() == 1) {
                    deviceChangeListService.removeByChangeId(id);
                    baseMapper.deleteById(id);
                    deviceChangeLogsService.delete(id);
                }
            }
        }
        return R.success("删除成功");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Integer> deskUpdateStatus(OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception {
        IdevelopUser user = SecureUtil.getUser();
        DeviceChange deviceChange = baseMapper.selectOne(new LambdaQueryWrapper<DeviceChange>().eq(DeviceChange::getId, orderUpdateStatusDTO.getId()));
        if (Objects.isNull(deviceChange)) {
            return R.fail("当前工单不存在");
        }
        // 获取当前节点操作角色
        List<HussarAssignVo> hussarAssignVos = hussarBpmService.queryTaskInfo(deviceChange.getFilingNo());
        String roleName = hussarAssignVos.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
        String recordNode = null;
        String processStatus = null;
        LambdaUpdateWrapper<DeviceChange> wrapper = new LambdaUpdateWrapper<>();
        if (DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode().equals(deviceChange.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 0) {
            processStatus = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode();
            recordNode = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode();
            orderUpdateStatusDTO.setComment("发起设备变更");
        }
        if (DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode().equals(deviceChange.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 0) {
            processStatus = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_FINISH.getNode();
            recordNode = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode();
            //同步cmdb与i6000
            Map<Long, Map<String, Object>> map = new HashMap<>();
            String id = orderUpdateStatusDTO.getId();
            List<DeviceChangeList> deviceChangeLists = deviceChangeListService.getByChangeId(id);
            if (deviceChangeLists == null || deviceChangeLists.size() == 0) {
                return R.fail("当前工单下设备不存在");
            }
            List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();
            List<String> deviceTypeList = modelProperties.getModelIdList(Constants.DEVICE_SAFE_ACCESS_TYPE);
            List<String> switchesTypeList = modelProperties.getModelIdList(Constants.SWITCHES_TYPE);
            List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
            if ("2".equals(deviceChange.getChangeType())) {
                LambdaQueryWrapper<SafeaccessSubnet> lambdaQueryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>()
                        .likeRight(SafeaccessSubnet::getRegionCode, deviceChange.getRegionCode())
                        .in(SafeaccessSubnet::getId, deviceChangeLists.stream().map(DeviceChangeList::getSubnetId).distinct().collect(Collectors.toList()));
                List<SafeaccessSubnet> safeAccessSubnetList = safeaccessSubnetMapper.selectList(lambdaQueryWrapper);

                for (DeviceChangeList device : deviceChangeLists) {

                    String deviceTypeCode = device.getDeviceTypeCode();

                    //获取到旧的子网和ip
                    LambdaQueryWrapper<DeviceChangeLogs> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(DeviceChangeLogs::getChangeId, orderUpdateStatusDTO.getId());
                    queryWrapper.eq(DeviceChangeLogs::getDeviceCode, device.getDeviceCode());
                    List<DeviceChangeLogs> deviceChangeLogsList = deviceChangeLogsService.list(queryWrapper);
                    String oldIp = null;
                    String oldNet = null;
                    String newIp = null;
                    String newNet = null;
                    String oldNetWorkCode = null;
                    String oldSwitchesIp = null;
                    String newSwitchesIp = null;
                    String oldSwitchesPassword = null;
                    String newSwitchesPassword = null;
                    String net = device.getSubnetId();
                    if (ObjectUtil.isNotEmpty(deviceChangeLogsList)) {
                        for (DeviceChangeLogs logs : deviceChangeLogsList) {
                            String attributeName = logs.getAttributeName();
                            if ("IP地址".equals(attributeName)) {
                                oldIp = logs.getChangeBefore();
                                newIp = logs.getChangeAfter();
                            }
                            if ("所属子网ID".equals(attributeName)) {
                                oldNet = logs.getChangeBefore();
                                newNet = logs.getChangeAfter();
                            }
                            if ("所属网络编码".equals(attributeName)) {
                                oldNetWorkCode = logs.getChangeBefore();
                            }
                            if ("交换机ip".equals(attributeName)) {
                                oldSwitchesIp = logs.getChangeBefore();
                                newSwitchesIp = logs.getChangeAfter();
                            }
                            if ("交换机密码".equals(attributeName)) {
                                oldSwitchesPassword = logs.getChangeBefore();
                                newSwitchesPassword = logs.getChangeAfter();
                            }
                        }
                    }

                    // 终端设备和办公设备
                    if (deviceTypeList.contains(deviceTypeCode)) {
                        //1.判断原状态是否为空，为空表明没有修改联网状态，在判断新状态是否为未联网，
                        //未联网：如果有脏数据，新ip不为空，释放ip
                        //联网：执行正常的流程
                        //2.原状态不为空，执行正常流程
                        if (StringUtils.isEmpty(oldNetWorkCode)) {
                            //原状态为空，未进行修改
                            if (modelProperties.getNetwork3().equals(device.getNetWorkCode())) {
                                if (StringUtils.isNotEmpty(device.getIP())) {
                                    //释放ip
                                    String subnetId = checkSubnetAndIp(device.getIP());
                                    if (StringUtils.isNotEmpty(subnetId)) {
                                        //释放旧ip
                                        safeaccessIppoolService.releaseIpBySubnetIdAndIp(subnetId, device.getIP());
                                    }
                                }
                            } else {
                                // 正常进行流程
                                if (StringUtil.isNotBlank(oldIp) && StringUtils.isNotEmpty(newIp)) {
                                    //正常网络变更
                                    //增加校验
                                    String subnetId = checkSubnetAndIp(oldIp);
                                    if (StringUtils.isEmpty(subnetId)) {
                                        throw new RuntimeException("设备变更-根据ip:" + oldIp + "-查询所属子网异常，请联系管理员");
                                    }
                                    if (!StringUtils.equals(oldIp, newIp)) {
                                        //释放旧ip
                                        safeaccessIppoolService.releaseIpBySubnetIdAndIp(subnetId, oldIp);
                                        //启用新ip
                                        safeaccessIppoolService.setIsUsedBySubnetIdAndIp(net, device.getIP());
                                    }
                                } else if (StringUtils.isEmpty(oldIp)) {
                                    //原来没有ip，不能变更，需进行设备退运之后重新投运
                                    throw new RuntimeException("设备变更-该设备没有原ip，" + oldIp + "，请进行设备退运之后再进行设备投运" + "设备编码：" + device.getDeviceCode());
                                } else if (StringUtils.isEmpty(device.getIP())) {
                                    //原状态联网，修改为未联网
                                    //释放旧ip
                                    safeaccessIppoolService.releaseIpBySubnetIdAndIp(oldNet, oldIp);
                                }
                            }
                        } else {
                            //正常进行流程
                            if (StringUtil.isNotBlank(oldIp) && StringUtils.isNotEmpty(newIp)) {
                                //正常网络变更
                                //增加校验
                                String subnetId = checkSubnetAndIp(oldIp);
                                if (StringUtils.isEmpty(subnetId)) {
                                    throw new RuntimeException("设备变更-根据ip:" + oldIp + "-查询所属子网异常，请联系管理员");
                                }
                                if (!StringUtils.equals(oldIp, newIp)) {
                                    //释放旧ip
                                    safeaccessIppoolService.releaseIpBySubnetIdAndIp(subnetId, oldIp);
                                    //启用新ip
                                    safeaccessIppoolService.setIsUsedBySubnetIdAndIp(net, device.getIP());
                                }
                            } else if (StringUtils.isEmpty(oldIp)) {
                                //原来没有ip，不能变更，需进行设备退运之后重新投运
                                throw new RuntimeException("设备变更-该设备没有原ip，" + oldIp + "，请进行设备退运之后再进行设备投运" + "设备编码：" + device.getDeviceCode());
                            } else if (StringUtils.isEmpty(device.getIP())) {
                                //原状态联网，修改为未联网
                                //释放旧ip
                                safeaccessIppoolService.releaseIpBySubnetIdAndIp(oldNet, oldIp);
                            }
                        }

                        //根据mac和旧ip查询
                        SafeaccessUserAccessDTO userAndPasswordUpdate = new SafeaccessUserAccessDTO();
                        userAndPasswordUpdate.setIpAddress(oldIp);
                        userAndPasswordUpdate.setMacAddress(device.getMAC());
                        SafeaccessUserAccessDTO userAccessDTO = safeaccessUserAccessMapper.getApproveuUserAndPassword(userAndPasswordUpdate);
                        if (Objects.isNull(userAccessDTO)) {
                            throw new RuntimeException("终端设备变更-根据mac，" + device.getMAC() + "，和旧ip查询，" + oldIp + "，未查询到用户入网信息" + "设备编码：" + device.getDeviceCode());
                        }
                        String approveuUser = userAccessDTO.getApproveuUser();
                        String approveuPassword = userAccessDTO.getApproveuPassword();
                        //组装参数
                        DeviceChangeList deviceChangeList = new DeviceChangeList();
                        BeanUtils.copyProperties(device, deviceChangeList);
                        deviceChangeList.setUserAccessId(userAccessDTO.getId());

                        //同步到IP资源 修改或者删除用户入网信息.
                        if (StringUtils.isEmpty(deviceChangeList.getIP())) {
                            safeaccessSubnetMapper.deviceChangeDelete(deviceChangeList);
                        } else {
                            safeaccessSubnetMapper.deviceChangeUpdate(deviceChangeList);
                        }

                        //根据所属网络判断，如果是未联网状态，直接跳过
                        if (modelProperties.getNetwork3().equals(device.getNetWorkCode())) {
                            continue;
                        }
                        switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                                .deviceType(device.getDeviceTypeCode())
                                .deviceIp(device.getIP())
                                .deviceMac(device.getMAC())
                                .authAccount(approveuUser)
                                .authPassword(approveuPassword)
                                .deviceSubnet(device.getSubnetId())
                                .deviceCode(device.getDeviceCode())
                                .is802(Constants.NO_AUTHENTICATION.equals(device.getNetworkAccessMethod()) ? "0" :
                                        Constants.I802_AUTHENTICATION.equals(device.getNetworkAccessMethod()) ? "1" :
                                                Constants.MAC_AUTHENTICATION.equals(device.getNetworkAccessMethod()) ? "2" : "3")
                                .build());
                        // 2025-02-11 根据IP和MAC增加查询用户入网表里面的认证用户(approveu_user)
                        deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                                .ipAddress(device.getIP())
                                .deviceName(device.getFullName())
                                .factoryNumber(device.getSn())
                                .oldIpAddress(StringUtils.equals(oldIp, newIp) ? "" : oldIp)
                                .subnetId(device.getSubnetId())
                                .sbbm(device.getDeviceCode())
                                .is802(Constants.NO_AUTHENTICATION.equals(device.getNetworkAccessMethod()) ? "0" :
                                        Constants.I802_AUTHENTICATION.equals(device.getNetworkAccessMethod()) ? "1" :
                                                Constants.MAC_AUTHENTICATION.equals(device.getNetworkAccessMethod()) ? "2" : "3")
                                .syncSign("U")
                                .authUser(approveuUser)
                                .authPassword(approveuPassword)
                                .readState("0")
                                .isAccess("0")
                                .dataFrom("0")
                                .region(user.getRegionCode().length() > 4 ? user.getRegionCode().substring(0, 4) : user.getRegionCode())
                                .vlanId(safeAccessSubnetList.stream().filter(safeAccessSubnet ->
                                        safeAccessSubnet.getId().equals(device.getSubnetId())).findFirst().orElse(new SafeaccessSubnet()).getVlanId())
                                .build());
                    }

                    if (switchesTypeList.contains(deviceTypeCode)) {

                        SafeaccessSwitche safeaccessSwitche = new SafeaccessSwitche();
                        safeaccessSwitche.setSwIp(device.getSwitchesIp());
                        QueryWrapper<SafeaccessSwitche> queryWrapper1 = Condition.getQueryWrapper(safeaccessSwitche);
                        SafeaccessSwitche safeaccessSwitcheOne = safeaccessSwitcheService.getOne(queryWrapper1);
                        if (Objects.isNull(safeaccessSwitcheOne)) {
                            throw new RuntimeException("网络设备变更-根据旧ip查询，" + device.getSwitchesIp() + "，未查询到网络交换机入网信息" + "设备编码：" + device.getDeviceCode());
                        }
                        safeaccessSwitcheOne.setAuthConfig(device.getNetworkAccessMethod());
                        safeaccessSwitcheOne.setSwIp(device.getSwitchesIp());
                        safeaccessSwitcheOne.setSwPass(device.getSwitchesPassword());
                        safeaccessSwitcheOne.setTelIp(device.getManageIp());
                        safeaccessSwitcheOne.setTelUser(device.getManageUser());
                        safeaccessSwitcheOne.setTelPass(device.getManagePassword());
                        safeaccessSwitcheOne.setConfigPass(device.getAllocationPassword());
                        safeaccessSwitcheOne.setSnmpReadStr(device.getSnmpRead());
                        safeaccessSwitcheOne.setSnmpWriteStr(device.getSnmpWrite());
                        safeaccessSwitcheOne.setSnmpVersion(device.getSnmpVersion());
                        safeaccessSwitcheOne.setVlans(device.getVlanNumber());
                        safeaccessSwitcheOne.setPortsCount(device.getPortsCount());
                        safeaccessSwitcheOne.setSwPurpose(device.getPurpose());

                        safeaccessSwitcheService.updateById(safeaccessSwitcheOne);

                        switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                                .switchesIp(device.getSwitchesIp())
                                .switchesPassword(device.getSwitchesPassword())
                                .build());
                    }
                }


                // 循环结束
                // 数据推送
                if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
                    idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, user.getRegionCode(), "4");
                }

                // sdn数据推送
                if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
                    idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
                }

            }

            String changeType = deviceChange.getChangeType();
            for (DeviceChangeList device : deviceChangeLists) {
                //参数组装cmdb和i6000
                Map<String, Object> cmdbMap = build(device, changeType);
                map.put(Long.valueOf(device.getDeviceId()), cmdbMap);
            }
            cmdbService.cientityBatchupdate(map, TransactionActionType.UPDATE);
            if ("1".equals(deviceChange.getChangeType()) || "2".equals(deviceChange.getChangeType())) {
                for (DeviceChangeList changeList : deviceChangeLists) {
                    if (deviceTypeList.contains(changeList.getDeviceTypeCode())) {
                        //同步到用户入网 使用人 责任人 安装地点 责任人联系方式  领用部门
                        safeaccessSubnetMapper.deviceChangeUpdateOnline(changeList);
                    }
                }
                //同步erp
                syncErp(deviceChange, deviceChangeLists);
                // 异步同步 I6000 系统
                List<String> deviceCodeList = deviceChangeLists.stream().map(DeviceChangeList::getDeviceCode).collect(Collectors.toList());
                i6000ERPService.importSyncI6000Detail(deviceCodeList);
            }
            wrapper.set(DeviceChange::getTicketStatus, DeviceChangeEnum.FINISH.getCode());
        } else if (DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode().equals(deviceChange.getProcessStatus()) && orderUpdateStatusDTO.getWorkerStatus() == 1) {
            processStatus = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode();
            recordNode = DeviceChangeBpmNodeEnum.DEVICE_CHANGE_OPERATION_REVIEW.getNode();
            LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(ApproveRecord::getOptOpinion, "待审批").set(ApproveRecord::getOptTitle, "待审批").eq(ApproveRecord::getFilingNo, orderUpdateStatusDTO.getId()).eq(ApproveRecord::getNodeId, processStatus);
            approveRecordService.update(updateWrapper);
        }
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(orderUpdateStatusDTO.getId())
                .logData(orderUpdateStatusDTO.toString())
                .params(orderUpdateStatusDTO.toString())
                .optType(OptTypeEnum.DEVICE_CHANGE.getCode())
                .title(orderUpdateStatusDTO.getComment())
                .optRole(roleName).time(new Date()).build());
        // 记录审核流程
        approveRecordService.commonRecord(ApproveRecord.builder()
                .filingNo(orderUpdateStatusDTO.getId())
                .optType(OptTypeEnum.DEVICE_CHANGE.getCode())
                .nodeId(recordNode)
                .optRole(roleName)
                .nodeName(DeviceChangeBpmNodeEnum.getMessage(recordNode))
                .optTitle(orderUpdateStatusDTO.getComment())
                .optOpinion(orderUpdateStatusDTO.getComment())
                .approveStatus(orderUpdateStatusDTO.getWorkerStatus())
                .filingCode(deviceChange.getFilingNo())
                .build());
        // 更新工单信息
        baseMapper.update(wrapper
                .eq(DeviceChange::getId, orderUpdateStatusDTO.getId())
                .set(DeviceChange::getUpdateTime, new Date())
                .set(DeviceChange::getUpdateUser, user.getUserId())
                .set(DeviceChange::getProcessStatus, processStatus));
        if (orderUpdateStatusDTO.getWorkerStatus() == 0) {
            HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
            hussarBpmDTO.setBusinessKey(deviceChange.getFilingNo());
            hussarBpmDTO.setParticipantType("2");
            hussarBpmDTO.setTaskType("1");
            hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark());
            hussarBpmService.hussarSubmit(hussarBpmDTO);
        }
        if (orderUpdateStatusDTO.getWorkerStatus() == 1) {
            HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
            hussarBpmDTO.setBusinessKey(deviceChange.getFilingNo());
            hussarBpmDTO.setOrderId(deviceChange.getId());
            hussarBpmService.prevNodeReject(hussarBpmDTO);

        }
        if (StringUtils.equals(DeviceChangeBpmNodeEnum.DEVICE_CHANGE_FINISH.getNode(), processStatus)) {
            ApproveRecord approveRecord = ApproveRecord.builder()
                    .filingNo(orderUpdateStatusDTO.getId())
                    .optType(OptTypeEnum.DEVICE_CHANGE.getCode())
                    .nodeId(DeviceChangeBpmNodeEnum.DEVICE_CHANGE_FINISH.getNode())
                    .optRole("--")
                    .nodeName(DeviceChangeBpmNodeEnum.getMessage(DeviceChangeBpmNodeEnum.DEVICE_CHANGE_FINISH.getNode()))
                    .optTitle("自动归档")
                    .optOpinion("自动归档")
                    .optName("系统")
                    .approveStatus(orderUpdateStatusDTO.getWorkerStatus())
                    .filingCode(deviceChange.getFilingNo())
                    .build();
            approveRecord.setStatus(1);
            approveRecordService.commonRecord(approveRecord);
            Thread.sleep(500);
            LogOpt logOpt = LogOpt.builder().logId(orderUpdateStatusDTO.getId())
                    .logData(orderUpdateStatusDTO.toString())
                    .params(orderUpdateStatusDTO.toString())
                    .optType(OptTypeEnum.DEVICE_CHANGE.getCode())
                    .title("自动归档")
                    .optName("系统")
                    .optRole("--").time(new Date()).build();
            logOpt.setStatus(0);
            logOptService.commonLogOpt(logOpt);
        }
        return R.success(ResultCode.SUCCESS);
    }

    @Override
    public R erpSync(List<DeviceChangeList> deviceChangeLists) {
        //同步erp
        return R.success("同步成功");
    }

    private ErpTransEqunrResp syncErp(DeviceChange dto, List<DeviceChangeList> list) throws IllegalArgumentException {
        Map<Object, Object> powerLevelMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getPowerLevel());
        Map<Object, Object> deviceAddMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceAdd());
        Map<Object, Object> deviceTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
        Map<Object, Object> unifiedCodeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getUnifiedCode());
        Map<Object, Object> deviceChangeTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceChangeType());
//		Map<Object, Object> countryAreaMapErp = cmdbDictProperties.getCountryAreaErpMapByCiId(cmdbDictProperties.getCountryArea());
        Map<Object, Object> factoryAreaMapErp = cmdbDictProperties.getFactoryAreaErpMapByCiId(cmdbDictProperties.getFactoryAreaCode());
        Map<Object, Object> statusErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceStatus());

        ErpTransEqunr erp = new ErpTransEqunr();
        erp.setXtdocId(dto.getId());
        erp.setXtdocNo(dto.getFilingNo());
        erp.setOperationType(ErpOperationEnum.M);
        List<ErpTransEqunrItem> erpList = Lists.newArrayList();
        for (DeviceChangeList change : list) {
            if (StringUtils.isBlank(change.getDeviceCodeErp()) || StringUtils.isBlank(change.getAssetCodeErp())) {
                continue;
            }
            //默认计量单位
            change.setMeasureUnit("1125222511345664");  //台
            //获取计量单位
            R<Map<String, Object>> mapR = cmdbClient.feignCientityDetailById(cmdbDictProperties.getDeviceType(), Long.parseLong(change.getDeviceTypeCode()));
            if (mapR.isSuccess()) {
                String unitCode = mapR.getData().get("UNIT_CODE").toString();
                if (StringUtil.isNotBlank(unitCode)) {
                    R<Map<String, Object>> unit = cmdbClient.feignCientityDetailById(cmdbDictProperties.getUnifiedCode(), Long.parseLong(unitCode));
                    if (unit.isSuccess()) {
                        String unitName = unit.getData().get("dictKey").toString();
                        change.setMeasureUnit(unitCode);
                    }
                }
            }
            CiCientitySearch cientitySearch = new CiCientitySearch();
            List<CiCientitySearchVO> entity = new ArrayList<>();
            CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder()
                    .attrName(CmdbAttrConstant.DEVICE_CODE)
                    .expression(Expression.EQUAL)
                    .attrValue(change.getDeviceCode()).build();
            entity.add(ciCientitySearchVO);
            Query query = new Query().setCurrent(1).setSize(10);
            cientitySearch.setEntity(entity);
            cientitySearch.setQuery(query);
            cientitySearch.setFullField(Boolean.TRUE);
            FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
            if (feignCiCientity.getTotal() == 0) {
                throw new ServiceException("请核对设备编码");
            }
            Map<String, Object> map = feignCiCientity.getData().get(0);
            ErpTransEqunrItem erpItem = new ErpTransEqunrItem();
            erpItem.setXtbm(change.getUuid());
            erpItem.setXtbmNo(change.getDeviceCode());
            erpItem.setSwid("");
            //标准全称
            erpItem.setEqktx(String.valueOf(map.get(CmdbAttrConstant.FULL_NAME)));
            erpItem.setZsb001(change.getUseKeepDept());
            erpItem.setZsb002(change.getRealManageDept());
            // 2024-4-28 当前登陆人
            Object receivingPerson = map.get(CmdbAttrConstant.RECEIVING_PERSON);
            if (receivingPerson != null && !"".equals(receivingPerson)) {
                erpItem.setZsb010(String.valueOf(receivingPerson));
            } else {
                erpItem.setZsb010("");
            }
            // 电压等级
            Object powerLevel = powerLevelMapErp.get(change.getVoltageLevelCode());
            erpItem.setZsb004(String.valueOf(powerLevel));
            Object status = statusErp.get(String.valueOf(map.get(CmdbAttrConstant.DEVICE_STATUS_CODE)));
            erpItem.setStat(String.valueOf(status));
            // 设备增加方式
            Object deviceAddType = deviceAddMapErp.get(change.getDeviceAddTypeCode());
            erpItem.setZsb005(String.valueOf(deviceAddType));
            //设备变动方式
            Object deviceChangeType = deviceChangeTypeMapErp.get(change.getDeviceChangeTypeCode());
            erpItem.setStort(String.valueOf(deviceChangeType));
            erpItem.setEqart("");
            // 设备类型
            Object deviceType = deviceTypeMapErp.get(change.getDeviceTypeCode());
            erpItem.setSbfl(String.valueOf(deviceType));
            // 制造商
            erpItem.setHerst(change.getMaker());
            // 制造国家
//			Object country = countryAreaMapErp.get(storage.getMaintenanceCountry());
            erpItem.setHerld("CN");
            erpItem.setPosid(String.valueOf(feignCiCientity.getData().get(0).get(CmdbAttrConstant.WBS_ELEMENT)));
            erpItem.setZsb006(change.getFunLocation());
            erpItem.setTplnr(change.getFunLocationCode());
            erpItem.setZcabn_ztpm1005(1);
            // 计量单位
            Object unit = unifiedCodeMapErp.get(change.getMeasureUnit());
            erpItem.setZcabn_ztpm1006(String.valueOf(unit));
            // 工厂区域
            String factoryAreaCodeId = change.getFactoryAreaCode();
            Object factoryAreaCode = factoryAreaMapErp.get(factoryAreaCodeId);
            if (ObjectUtil.isEmpty(factoryAreaCode)) {
                erpItem.setBeber("");
            } else {
                erpItem.setBeber(String.valueOf(factoryAreaCode));
            }
            if (ObjectUtil.isEmpty(change.getOprtDate())) {
                erpItem.setInbdt("");
            } else {
                Date oprtDate = change.getOprtDate();
                erpItem.setInbdt(new SimpleDateFormat("yyyyMMdd").format(oprtDate));
            }
            String deviceModel = change.getDeviceModel();
            if (deviceModel.length() > 20) {
                deviceModel = change.getDeviceModel().substring(20);
            }
            erpItem.setTypbz(deviceModel);
            String sn = change.getSn();
            if (change.getSn().length() > 30) {
                sn = change.getSn().substring(30);
            }
            erpItem.setSerge(sn);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date factoryDate = change.getFactoryDate();
            String factoryTime = format1.format(factoryDate);
            erpItem.setBaujj(factoryTime.substring(0, 4));
            erpItem.setBaumm(factoryTime.substring(5, 7));
            erpItem.setSwerk(String.valueOf(map.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE)));
            erpItem.setAnlnr(change.getAssetCodeErp());
            erpItem.setEqunr(change.getDeviceCodeErp());
            erpItem.setTbbs("");
            erpItem.setZsb011("00000000000000000");
            erpList.add(erpItem);
        }
        erp.setErpTransEqunrItemList(erpList);
        log.info("推送erp数据:{}", erpList);
        if (erpList.size() == 0) {
            return null;
        }
        if (erpPush) {
            ErpTransEqunrResp erpTransEqunrResp = erpService.transEqunr(erp);
            log.info("erp返回数据:{}", erpTransEqunrResp);
            return erpTransEqunrResp;
        }
        return null;
    }

    @Override
    public R<IPage<DeviceChangeVO>> deskDeviceChangeList(Query query, DeviceChangeDTO deviceChange) {
        if (StringUtil.isBlank(deviceChange.getOrderNoList())) {
            return R.data(new Page<>());
        }
        deviceChange.setFilingNoList(Arrays.asList(deviceChange.getOrderNoList().split(",")));
        return R.data(baseMapper.deskDeviceChangeList(deviceChange, Condition.getPage(query)));
    }

    @Override
    public R load() {
        //初始化数据
        IdevelopUser user = SecureUtil.getUser();
        Map<String, Object> ext = user.getExt();
        DeviceChange deviceChange = new DeviceChange();
        deviceChange.setApplyUnitName(String.valueOf(ext.get("corpFullName")));
        deviceChange.setApplyUnit(user.getCorpId());
        deviceChange.setApplyDept(user.getDeptId());
        deviceChange.setApplyDeptName(user.getDeptName());
        deviceChange.setApplyUser(String.valueOf(user.getUserId()));
        deviceChange.setApplyUserName(user.getRealName());
        deviceChange.setReceiverTime(LocalDateTime.now());
        deviceChange.setChangeType("1");
        return R.data(deviceChange);
    }

    private Map<String, Object> build(DeviceChangeList device, String changeType) {
        Map<String, Object> cmdbMap = new HashMap<>();

        //制造商品牌系列型号 暂时不更新
        // cmdbMap.put(CmdbAttrConstant.MAKER, device.getMaker());
        // log.info("CmdbAttrConstant.MAKER: {}", device.getMaker());
        // cmdbMap.put(CmdbAttrConstant.MAKER_CODE, device.getMakerCode());
        // log.info("CmdbAttrConstant.MAKER_CODE: {}", device.getMakerCode());
        //
        // cmdbMap.put(CmdbAttrConstant.BRAND, device.getBrand());
        // log.info("CmdbAttrConstant.BRAND: {}", device.getBrand());
        // cmdbMap.put(CmdbAttrConstant.BRAND_CODE, Long.valueOf(device.getBrandCode()));
        // log.info("CmdbAttrConstant.BRAND_CODE: {}", Long.valueOf(device.getBrandCode()));
        //
        // cmdbMap.put(CmdbAttrConstant.SERIES, device.getSeries());
        // log.info("CmdbAttrConstant.SERIES: {}", device.getSeries());
        // cmdbMap.put(CmdbAttrConstant.SERIES_CODE, Long.valueOf(device.getSeriesCode()));
        // log.info("CmdbAttrConstant.SERIES_CODE: {}", Long.valueOf(device.getSeriesCode()));
        //
        // cmdbMap.put(CmdbAttrConstant.DEVICE_MODEL, device.getDeviceModel());
        // log.info("CmdbAttrConstant.DEVICE_MODEL: {}", device.getDeviceModel());
        // cmdbMap.put(CmdbAttrConstant.DEVICE_MODEL_CODE, Long.valueOf(device.getDeviceModelCode()));
        // log.info("CmdbAttrConstant.DEVICE_MODEL_CODE: {}", Long.valueOf(device.getDeviceModelCode()));

        cmdbMap.put(CmdbAttrConstant.SN, device.getSn());

        if (StringUtils.equals("2", changeType)) {
            cmdbMap.put(CmdbAttrConstant.SUBNET_NAME, device.getSubnetName());
            cmdbMap.put(CmdbAttrConstant.SUBNET_ID, device.getSubnetId());
            cmdbMap.put(CmdbAttrConstant.NET_WORK_CODE, device.getNetWorkCode());
            // if (StringUtil.isNotBlank(device.getSubnetId())){
            // 	//根据所属子网更新所属网络
            // 	LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<>();
            // 	queryWrapper.eq(SafeaccessSubnet::getId,device.getSubnetId()).eq(SafeaccessSubnet::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
            // 	SafeaccessSubnet subnet = safeaccessSubnetService.getOne(queryWrapper);
            // 	cmdbMap.put(CmdbAttrConstant.NET_WORK_CODE, subnet.getNetworkType());
            // }
            cmdbMap.put(CmdbAttrConstant.IP, device.getIP());
            cmdbMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, device.getNetworkAccessMethod());
        } else {
            cmdbMap.put(CmdbAttrConstant.OPERATION_PERSON, device.getOperationPerson());
            cmdbMap.put(CmdbAttrConstant.INSTALLATION_SITE, device.getInstallationSite());
            cmdbMap.put(CmdbAttrConstant.RECEIVE_DEPT, device.getReceiveDept());
            cmdbMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, device.getReceiveDeptCode());
            cmdbMap.put(CmdbAttrConstant.USER, device.getUser());
            cmdbMap.put(CmdbAttrConstant.USER_TEL, device.getUserTel());
            cmdbMap.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, device.getDeviceUserIDCard());
            cmdbMap.put(CmdbAttrConstant.OPERATION_DEPT, device.getOperationDept());
            cmdbMap.put(CmdbAttrConstant.OPERATION_DEP_CODE, device.getOperationDepCode());
            cmdbMap.put(CmdbAttrConstant.OPERATION_TEL, device.getOperationTel());
            cmdbMap.put(CmdbAttrConstant.OPERATION_LEVEL, device.getOperationLevel());
            // 2025-6-13 新增字段
            cmdbMap.put(CmdbAttrConstant.USE_KEEP_DEPT, device.getUseKeepDept());
            cmdbMap.put(CmdbAttrConstant.USE_KEEP_DEPT_NAME, device.getUseKeepDeptName());
            cmdbMap.put(CmdbAttrConstant.REAL_MANAGE_DEPT, device.getRealManageDept());
            cmdbMap.put(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME, device.getEntityManagementDeptName());
            cmdbMap.put(CmdbAttrConstant.VOLTAGE_LEVEL, device.getVoltageLevel());
            cmdbMap.put(CmdbAttrConstant.VOLTAGE_LEVEL_CODE, device.getVoltageLevelCode());
            cmdbMap.put(CmdbAttrConstant.DEVICE_ADD_TYPE, device.getDeviceAddType());
            cmdbMap.put(CmdbAttrConstant.DEVICE_ADD_TYPE_CODE, device.getDeviceAddTypeCode());
            cmdbMap.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, device.getDeviceChangeType());
            cmdbMap.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE, device.getDeviceChangeTypeCode());
            cmdbMap.put(CmdbAttrConstant.FUN_LOCATION, device.getFunLocation());
            cmdbMap.put(CmdbAttrConstant.FUN_LOCATION_CODE, device.getFunLocationCode());
            cmdbMap.put(CmdbAttrConstant.FACTORY_AREA, device.getFactoryArea());
            cmdbMap.put(CmdbAttrConstant.FACTORY_AREA_CODE, device.getFactoryAreaCode());
            cmdbMap.put(CmdbAttrConstant.RECEIVING_GROUP, device.getReceivingGroup());
            cmdbMap.put(CmdbAttrConstant.RECEIVING_PERSON, device.getReceivingPerson());
            cmdbMap.put(CmdbAttrConstant.RECEIVING_ID_CARD, device.getReceivingIDCard());
            cmdbMap.put(CmdbAttrConstant.RECEIVING_TEL, device.getReceivingTel());
            cmdbMap.put(CmdbAttrConstant.MAINTENANCE_FACTORY, device.getMaintenanceFactory());
            cmdbMap.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, device.getMaintenanceFactoryCode());

            cmdbMap.put(CmdbAttrConstant.CPU_BRAND, device.getCpuBrand());
            cmdbMap.put(CmdbAttrConstant.CPU_BRAND_CODE, device.getCpuBrandCode());
            cmdbMap.put(CmdbAttrConstant.CPU_MODEL, device.getCpuModel());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (ObjectUtil.isNotEmpty(device.getFactoryDate())) {
            String format = sdf.format(device.getFactoryDate());
            cmdbMap.put(CmdbAttrConstant.FACTORY_DATE, format);
        }
        if (ObjectUtil.isNotEmpty(device.getOprtDate())) {
            String format = sdf.format(device.getOprtDate());
            cmdbMap.put(CmdbAttrConstant.OPRT_DATE, format);
        }
        cmdbMap.put(CmdbAttrConstant.FULL_NAME, device.getFullName());
        cmdbMap.put(CmdbAttrConstant.ID, device.getDeviceId());
        cmdbMap.put(CmdbAttrConstant.CI_ID, device.getCiId());
        cmdbMap.put(CmdbAttrConstant.UUID, device.getUuid());
        return cmdbMap;
    }

    @Override
    public R check(DeviceChangeDTO deviceChangeDTO) {
        List<DeviceChangeList> newChangeDeviceDTOList = deviceChangeDTO.getNewChangeDeviceDTOList();
        for (DeviceChangeList device : newChangeDeviceDTOList) {
            String deviceCode = device.getDeviceCode();
            //变更校验
            List<DeviceChangeList> changeDeviceList = deviceChangeListService.getByDeviceCode(deviceCode);
            if (ObjectUtil.isNotEmpty(changeDeviceList)) {
                for (DeviceChangeList deviceChangeList : changeDeviceList) {
                    String changeId = deviceChangeList.getChangeId();
                    DeviceChange deviceChange = baseMapper.selectById(changeId);
                    if (!ObjectUtil.isEmpty(deviceChange)) {
                        if (!DeviceChangeEnum.FINISH.getCode().equals(deviceChange.getTicketStatus()) && StringUtil.isNotBlank(deviceChange.getProcessInsId())
                                && !DeviceChangeBpmNodeEnum.DEVICE_CHANGE_APPLY.getNode().equals(deviceChange.getProcessStatus())) {
                            return R.fail("设备：" + deviceChangeList.getDeviceCode() + "已经提交变更，请不要重复，变更工单编号：" + deviceChange.getFilingNo());
                        }
                    }
                }
            }
            //报修校验
            List<DeviceRepairList> repairDeviceList = deviceRepairListService.getByDeviceCode(deviceCode);
            if (ObjectUtil.isNotEmpty(repairDeviceList)) {
                for (DeviceRepairList deviceRepairList : repairDeviceList) {
                    String repairId = deviceRepairList.getRepairId();
                    DeviceRepair deviceRepair = deviceRepairService.getById(repairId);
                    if (ObjectUtil.isEmpty(deviceRepair)) {
                        continue;
                    } else {
                        Integer ticketStatus = deviceRepair.getTicketStatus();
                        if (!DeviceRepairEnum.FINISH.getCode().equals(ticketStatus)) {
                            return R.fail("设备：" + deviceRepairList.getDeviceCode() + "正在报修，无法发起变更");
                        }
                    }
                }
            }
            //报废校验
            List<DeviceScrapList> scrapDeviceList = deviceScrapListService.getByDeviceCode(deviceCode);
            if (ObjectUtil.isNotEmpty(scrapDeviceList)) {
                for (DeviceScrapList deviceScrapList : scrapDeviceList) {
                    String scrapId = deviceScrapList.getScrapId();
                    DeviceScrap deviceScrap = deviceScrapService.getById(scrapId);
                    if (ObjectUtil.isEmpty(deviceScrap)) {
                        continue;
                    } else {
                        Integer status = deviceScrap.getStatus();
                        if (!DeviceScrapEnum.FINISH.getCode().equals(status)) {
                            return R.fail("设备：" + deviceScrapList.getDeviceCode() + "正在报废，无法发起变更");
                        }
                    }
                }
            }

        }
        return R.success("校验成功");
    }

    private String checkSubnetAndIp(String deviceIp) {
        IdevelopUser sysUser = SecureUtil.getUser();
        List<SafeaccessIppool> safeaccessIppools = safeaccessIppoolService.getSubnetList(deviceIp);
        if (ObjectUtil.isEmpty(safeaccessIppools)) {
            return null;
        }
        if (safeaccessIppools.size() == 1) {
            return safeaccessIppools.get(0).getSubnet();
        } else {
            String regionCode = sysUser.getRegionCode();
            List<SafeaccessSubnet> list = safeaccessSubnetService.getByRegon(regionCode);
            List<String> subnetList = safeaccessIppools.stream().map(item -> item.getSubnet()).collect(Collectors.toList());
            Integer count = 0;
            String subnet = null;
            for (String id : subnetList) {
                for (SafeaccessSubnet safeaccessSubnet : list) {
                    if (id.equals(safeaccessSubnet.getId())) {
                        count++;
                        subnet = safeaccessSubnet.getId();
                    }
                    if (count > 1) {
                        subnet = null;
                    }
                }
            }
            return subnet;
        }

    }
}

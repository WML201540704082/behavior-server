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
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.res.dto.HussarBpmCreateDTO;
import com.lnsoft.device.api.res.dto.HussarBpmDTO;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.IHussarBpmService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessIppoolService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnUserAccess;
import com.lnsoft.common.enums.hussar.DeviceReturnedBpmNodeEnum;
import com.lnsoft.common.enums.hussar.DeviceReturnedEnum;
import com.lnsoft.common.enums.hussar.HussarBpmTypeEnum;
import com.lnsoft.device.api.warehouse.mapper.DeviceReturnedDetailMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceReturnedMapper;
import com.lnsoft.device.api.warehouse.service.IDSwitcherSyncService;
import com.lnsoft.device.api.warehouse.service.IDeviceReturnedDetailService;
import com.lnsoft.device.api.warehouse.service.IDeviceReturnedService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.dto.DeviceOrderFileDTO;
import com.lnsoft.device.dto.DeviceReturnedDTO;
import com.lnsoft.device.dto.DeviceReturnedDetailDTO;
import com.lnsoft.device.entity.*;
import com.lnsoft.common.enums.hussar.OptTypeEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.DeviceReturnedDetailVO;
import com.lnsoft.device.vo.DeviceReturnedVO;
import com.lnsoft.hussar.bpm.domain.dto.HussarBpmDto;
import com.lnsoft.hussar.bpm.domain.vo.HussarAssignVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarComplateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarCreateVo;
import com.lnsoft.hussar.bpm.domain.vo.HussarTaskVo;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 设备退运 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-25
 */
@Service
public class DeviceReturnedServiceImpl extends BaseServiceImpl<DeviceReturnedMapper, DeviceReturned> implements IDeviceReturnedService {

    @Resource
    private OrderNumberUtil orderNumberUtil;
    @Resource
    private ILogOptService logOptService;
    @Resource
    private IHussarBpmService hussarBpmService;
    @Resource
    private IApproveRecordService approveRecordService;
    @Autowired
    private IDeviceReturnedDetailService detailService;
    @Autowired
    private DeviceReturnedDetailMapper detailMapper;
    @Autowired
    private ISafeaccessIppoolService safeaccessIppoolService;
    @Resource
    private ICmdbService iCmdbService;
    @Resource
    private IHussarBpmClient hussarBpmClient;
    @Resource
    private ISafeaccessUserAccessService userAccessService;
    @Resource
    private IDSwitcherSyncService idSwitcherSyncService;
    @Resource
    private ISafeaccessSwitcheService safeaccessSwitcheService;
    @Resource
    private ISafeaccessSwitcheService safeAccessSwitchesService;
    @Resource
    private CmdbCientityProperties cmdbCientityProperties;
    @Resource
    private IDeviceInventoryService deviceInventoryService;
    @Resource
    private II6000Service i6000Service;
    @Resource
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private ISafeaccessSubnetService safeaccessSubnetService;

    @Override
    public IPage<DeviceReturnedVO> selectDeviceReturnedPage(IPage<DeviceReturnedVO> page, DeviceReturnedVO deviceReturned) {
        return page.setRecords(baseMapper.selectDeviceReturnedPage(page, deviceReturned));
    }

    /**
     * 设备退运详情
     * @param dto
     * @return
     */
    @Override
    public R<DeviceReturnedVO> detail(DeviceReturnedDTO dto) {
        //获取用户信息
        IdevelopUser user = SecureUtil.getUser();
        if (Objects.isNull(user)) {
            return R.fail("用户信息异常！");
        }
        DeviceReturned returned = baseMapper.selectOne(Wrappers.<DeviceReturned>lambdaQuery().eq(DeviceReturned::getId, dto.getId()).eq(DeviceReturned::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        if (Objects.isNull(returned)) {
            return R.fail("设备退运不存在");
        }
        List<DeviceReturnedDetail> detailList = detailMapper.selectList(Wrappers.<DeviceReturnedDetail>lambdaQuery().eq(DeviceReturnedDetail::getReturnedId, dto.getId()).eq(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        DeviceReturnedVO vo = new DeviceReturnedVO();
        if (StringUtil.isNotBlank(returned.getDevicePhoto())) {
            returned.setOrderFile(JSONUtil.toBean(returned.getDevicePhoto(), DeviceOrderFileDTO.class));
        } else {
            returned.setOrderFile(null);
            returned.setDevicePhoto(null);
        }
        BeanUtil.copyProperties(returned, vo);
        if (CollectionUtil.isNotEmpty(detailList)) {
            List<DeviceReturnedDetailVO> vos = detailList.stream().map(item -> {
                DeviceReturnedDetailVO detailVO = new DeviceReturnedDetailVO();
                BeanUtil.copyProperties(item, detailVO);
                return detailVO;
            }).collect(Collectors.toList());
            vo.setReturnedDetailVOS(vos);
        }
        return R.data(vo);
    }

    /**
     * 查看设备退运列表
     *
     * @param dto
     * @param query
     * @return
     */
    @Override
    public IPage<DeviceReturned> returnedPage(DeviceReturnedDTO dto, Query query) {
        //获取用户信息
        IdevelopUser user = SecureUtil.getUser();
        dto.setRegionCode(user.getRegionCode());
        return baseMapper.getPage(Condition.getPage(query), dto);
        //查询数据
//		IPage<DeviceReturned> result = baseMapper.selectPage(Condition.getPage(query), Wrappers.<DeviceReturned>lambdaQuery()
//			.eq(StringUtil.isNotBlank(dto.getApplyUnit()), DeviceReturned::getApplyUnit, dto.getApplyUnit())
//			.eq(StringUtil.isNotBlank(dto.getProcessStatus()), DeviceReturned::getProcessStatus, dto.getProcessStatus()).eq(StringUtil.isNotBlank(dto.getApplyDept()), DeviceReturned::getApplyDept, dto.getApplyDept())
//			.eq(StringUtil.isNotBlank(dto.getIsAllReturned()), DeviceReturned::getIsAllReturned, dto.getIsAllReturned()).eq(DeviceReturned::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).like(StringUtil.isNotBlank(dto.getFilingNo()), DeviceReturned::getFilingNo, dto.getFilingNo())
//			.likeRight(DeviceReturned::getRegionCode, user.getRegionCode()).orderByDesc(DeviceReturned::getCreateTime));
//		return result;
    }

    /**
     * 保存设备退运详情
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<DeviceReturnedVO> saveReturned(DeviceReturnedDTO dto) {
        //数据校验
        IdevelopUser user = SecureUtil.getUser();
        R<DeviceReturnedVO> check = checkReturned(dto);
        if (check != null) {
            return check;
        }
        //数据处理
        DeviceReturned returned = BeanUtil.copy(dto, DeviceReturned.class);
        returned.setRegionCode(user.getRegionCode());
        returned.setProcessStatus("1");
        if (CollectionUtil.isNotEmpty(dto.getDetailDTOS())) {
            int size = dto.getDetailDTOS().size();
            returned.setDeviceReturnNum(String.valueOf(size));
        }
        if (!Objects.isNull(returned.getOrderFile())) {
            returned.setDevicePhoto(JSONUtil.toJsonStr(returned.getOrderFile()));
        } else {
            returned.setDevicePhoto("");
        }
        if (Objects.isNull(returned.getId())) {
            if (ObjectUtil.isEmpty(returned.getFilingNo())) {
                returned.setFilingNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.TYD));
            }
            if (Objects.isNull(returned.getAcceptUser())) {
                returned.setAcceptUser(user.getUserId());
                returned.setAcceptUserName(user.getUserName());
                returned.setApplyDept(user.getDeptId());
                returned.setApplyDeptName(user.getDeptName());
                returned.setApplyUnit(user.getCorpId());
                returned.setApplyUnitName(user.getCorpName());
            }
            returned.setCreateTime(new Date());
            returned.setCreateUser(user.getUserId());
            returned.setStatus(1);
            returned.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            baseMapper.insert(returned);
            // 增加操作记录
            logOptService.commonLogOpt(LogOpt.builder().logId(returned.getId()).logData(dto.toString()).params(dto.toString())
                    .optRole("--").optType(OptTypeEnum.DEVICE_RETURNED.getCode()).title("新增暂存设备退运").build());
            dto.setFilingNo(returned.getFilingNo());
        } else {
            // 判断是否还可以进行修改操作
            DeviceReturned deviceReturned = baseMapper.selectOne(Wrappers.<DeviceReturned>lambdaQuery().eq(DeviceReturned::getId, dto.getId())
                    .eq(DeviceReturned::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            if (Objects.isNull(deviceReturned) || !DeviceReturnedEnum.TEMPORARILY.getCode().equals(deviceReturned.getStatus())) {
                return R.fail("当前设备退运无法进行暂存");
            }
            returned.setUpdateTime(new Date());
            returned.setUpdateUser(user.getUserId());
            baseMapper.updateById(returned);
            // 增加操作记录
            logOptService.commonLogOpt(LogOpt.builder().logId(returned.getId()).logData(dto.toString()).params(dto.toString())
                    .optRole("--").optType(OptTypeEnum.DEVICE_RETURNED.getCode()).title("修改暂存设备退运").build());
        }
        dto.setId(returned.getId());
        return saveDeviceReturnedDetail(dto, user);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R removeReturned(String ids) {
        IdevelopUser user = SecureUtil.getUser();
        List<String> idList = Arrays.asList(ids.split(","));
        List<DeviceReturned> deviceReturneds = baseMapper.selectList(Wrappers.<DeviceReturned>lambdaQuery().in(DeviceReturned::getId, idList)
                .eq(DeviceReturned::getStatus, DeviceReturnedEnum.TEMPORARILY.getCode()).eq(DeviceReturned::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        if (idList.size() != deviceReturneds.size()) {
            return R.fail("所选设备退运无法删除！");
        }
        baseMapper.update(Wrappers.<DeviceReturned>lambdaUpdate().set(DeviceReturned::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceReturned::getUpdateTime, new Date()).set(DeviceReturned::getUpdateUser, user.getUserId())
                .in(DeviceReturned::getId, idList).eq(DeviceReturned::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        detailService.update(Wrappers.<DeviceReturnedDetail>lambdaUpdate().set(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_IS_DELETED).set(DeviceReturnedDetail::getUpdateTime, new Date()).set(DeviceReturnedDetail::getUpdateUser, user.getUserId())
                .in(DeviceReturnedDetail::getReturnedId, idList).eq(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        // 增加操作记录
        idList.forEach(item -> logOptService.commonLogOpt(LogOpt.builder().logId(item).logData(ids).params(ids).optType(OptTypeEnum.DEVICE_RETURNED.getCode()).title("删除暂存设备退运").build()));
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 发起设备退运流程
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<DeviceReturnedVO> deviceReturnedSubmit(DeviceReturnedDTO dto) throws Exception {
        //保存退运信息
        IdevelopUser user = SecureUtil.getUser();
        if (CollectionUtil.isEmpty(dto.getDetailDTOS())) {
            return R.fail("请选择退运设备！");
        }
        R<DeviceReturnedVO> saveResult = saveReturned(dto);
        if (saveResult.getCode() != 200) {
            return saveResult;
        }
        DeviceReturnedVO vo = saveResult.getData();
        //处理数据
        // 组装发起流程需要的参数
        Map<String, Object> variable = new HashMap<>();
        variable.put("orderId", vo.getId());
        variable.put("orderNo", vo.getFilingNo());
        variable.put("userId", user.getUserId());
        variable.put("userName", user.getUserName());
        variable.put("regionCode", user.getRegionCode());
        HussarBpmCreateDTO hussarBpmCreateDTO = HussarBpmCreateDTO.builder().processDefinitionKey(HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark())
                .businessKey(vo.getFilingNo()).variable(variable).build();
        HussarCreateVo hussarBpm;
        // 发起流程
        try {
            hussarBpm = hussarBpmService.createHussarBpm(hussarBpmCreateDTO);
        } catch (Exception e) {
            throw new Exception("创建流程发生异常");
        }

        dto.setTaskDefinitionKey(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode());
        dto.setWorkerStatus(0);
        dto.setComment("发起设备退运申请");
        dto.setFilingNo(vo.getFilingNo());
        deskDeviceReturnedStatus(dto);
        // 记录流程id
        baseMapper.update(Wrappers.<DeviceReturned>lambdaUpdate().set(DeviceReturned::getProcessInsId, hussarBpm.getProcessInsId())
                .set(DeviceReturned::getProcessStatus, DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_PROFESSIONAL_REVIEW.getNode()).eq(DeviceReturned::getId, vo.getId()));
        return R.data(vo);
    }

    /**
     * 工作台查看设备退运
     *
     * @param dto
     * @param query
     * @return
     */
    @Override
    public R<IPage<DeviceReturnedVO>> deskDeviceReturnedList(DeviceReturnedDTO dto, Query query) {
        if (StringUtil.isBlank(dto.getOrderNoList())) {
            return R.data(new Page<>());
        }
        dto.setFilingNoList(Arrays.asList(dto.getOrderNoList().split(",")));
        return R.data(baseMapper.deskDeviceReturnedList(dto, Condition.getPage(query)));
    }

    /**
     * 更新设备退运状态
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Integer> deskDeviceReturnedStatus(DeviceReturnedDTO dto) throws Exception {
        IdevelopUser user = SecureUtil.getUser();
        DeviceReturned returned = getOne(Wrappers.<DeviceReturned>lambdaQuery().eq(!Objects.isNull(dto.getId()), DeviceReturned::getId, dto.getId()).eq(DeviceReturned::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        String returnNode = null;
        String optTitle = null;
        if (StringUtil.isBlank(dto.getTaskDefinitionKey())) {
            dto.setTaskDefinitionKey(returned.getProcessStatus());
        }
        dto.setProcessStatus(dto.getTaskDefinitionKey());
        if (DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode().equals(dto.getTaskDefinitionKey())) {
            if (StringUtils.equals("0", dto.getIsAuto())) {
                returnNode = DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode();
            } else {
                returnNode = DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_PROFESSIONAL_REVIEW.getNode();
            }
            optTitle = "发起设备退运申请";
        }
        if (DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_PROFESSIONAL_REVIEW.getNode().equals(dto.getTaskDefinitionKey())) {
            if (dto.getWorkerStatus() == 1) {
                returnNode = DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode();
                optTitle = dto.getComment();
            } else {
                returnNode = DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode();
                optTitle = dto.getComment();
            }
        }
        Map<String, Object> variable = new HashMap<>();
        variable.put("orderId", returned.getId());
        variable.put("orderNo", returned.getFilingNo());
        variable.put("userId", user.getUserId());
        variable.put("userName", user.getUserName());
        variable.put("regionCode", user.getRegionCode());
        List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();
        // 记录审核流程
        if (StringUtil.isBlank(dto.getExamineRole())) {
            //  获取当前节点操作角色
            List<HussarAssignVo> hussarAssignVoList = hussarBpmService.queryTaskInfo(returned.getFilingNo());
            String roleName = hussarAssignVoList.stream().map(HussarAssignVo::getRoleName).collect(Collectors.joining(","));
            dto.setExamineRole(roleName);
        }
        // 增加操作记录
        logOptService.commonLogOpt(LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString())
                .optType(OptTypeEnum.DEVICE_RETURNED.getCode()).title(optTitle).optRole(dto.getExamineRole()).time(new Date()).build());
        approveRecordService.commonRecord(ApproveRecord.builder()
                .filingNo(dto.getId())
                .filingCode(returned.getFilingNo())
                .optType(OptTypeEnum.DEVICE_RETURNED.getCode())
                .nodeId(dto.getTaskDefinitionKey())
                .optRole(dto.getExamineRole())
                .nodeName(DeviceReturnedBpmNodeEnum.getMessage(dto.getTaskDefinitionKey()))
                .optTitle(optTitle)
                .optOpinion(dto.getComment())
                .approveStatus(dto.getWorkerStatus())
                .build());
        // 更新工单信息
        if (DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_PROFESSIONAL_REVIEW.getNode().equals(dto.getTaskDefinitionKey()) && 0 == dto.getWorkerStatus()) {
            baseMapper.update(Wrappers.<DeviceReturned>lambdaUpdate().eq(DeviceReturned::getId, dto.getId()).set(DeviceReturned::getUpdateTime, new Date())
                    .set(DeviceReturned::getProcessStatus, returnNode).set(DeviceReturned::getStatus, DeviceReturnedEnum.FINISH.getCode()));
            // 增加操作记录
            LogOpt build = LogOpt.builder().logId(dto.getId()).logData(dto.toString()).params(dto.toString()).optRole("--")
                    .optType(OptTypeEnum.DEVICE_RETURNED.getCode()).title(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())).optName("系统").build();
            build.setStatus(0);
            logOptService.commonLogOpt(build);
            // 记录审核流程
            ApproveRecord approveRecord = ApproveRecord.builder().filingNo(dto.getId()).filingCode(returned.getFilingNo())
                    .optType(OptTypeEnum.DEVICE_RETURNED.getCode()).nodeId(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode())
                    .nodeName(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
                    .optTitle(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
                    .optOpinion(DeviceReturnedBpmNodeEnum.getMessage(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_FINISH.getNode()))
                    .approveStatus(0).optName("系统")
                    .optRole("--").build();
            approveRecord.setStatus(1);
            approveRecordService.commonRecord(approveRecord);
            // 更新工单信息
            baseMapper.update(Wrappers.<DeviceReturned>lambdaUpdate().eq(DeviceReturned::getId, dto.getId()).set(DeviceReturned::getUpdateTime, new Date())
                    .set(DeviceReturned::getProcessStatus, returnNode).set(DeviceReturned::getStatus, DeviceReturnedEnum.FINISH.getCode()));
            List<DeviceReturnedDetail> detailList = detailMapper.selectList(Wrappers.<DeviceReturnedDetail>lambdaQuery().eq(DeviceReturnedDetail::getReturnedId, returned.getId()).eq(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
            // 更新cmdb设备资产台账信息
            if (CollectionUtil.isEmpty(dto.getDetailDTOS())) {
                dto.setDetailDTOS(Convert.convert(new TypeReference<List<DeviceReturnedDetailDTO>>() {
                }, detailList));
            }
            if (CollectionUtil.isNotEmpty(dto.getDetailDTOS())) {
                dto.getDetailDTOS().forEach(item -> {
                    //2025-8-21 如果没有ip，直接往下走 不同步sdn，只更新台账
                    if (StringUtils.isEmpty(item.getDeviceIp())) {
                        return;
                    }
                    if (cmdbCientityProperties.getT105().equals(item.getDeviceCategory()) || cmdbCientityProperties.getT107().equals(item.getDeviceCategory())) {
                        // 2025-02-11 根据IP和MAC增加查询用户入网表里面的认证用户(approveu_user)
                        SafeaccessUserAccess safeaccessUserAccess = new SafeaccessUserAccess();
                        safeaccessUserAccess.setIpAddress(item.getDeviceIp());
                        safeaccessUserAccess.setMacAddress(item.getDeviceMac());
                        QueryWrapper<SafeaccessUserAccess> queryWrapper = Condition.getQueryWrapper(safeaccessUserAccess);
                        SafeaccessUserAccess safeaccessUserAccess1 = userAccessService.getOne(queryWrapper);
                        if (Objects.isNull(safeaccessUserAccess1)) {
                            throw new RuntimeException("终端或者办公设备退运时未查询到用户入网信息, 联系管理员处理.");
                        }
                        deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                                .sbbm(item.getDeviceCode())
                                .authUser(safeaccessUserAccess1.getApproveuUser())
                                .syncSign("D").readState("0").dataFrom("0")
                                .region(user.getRegionCode().length() > 4 ? user.getRegionCode().substring(0, 4) : user.getRegionCode())
                                .build());
                    }

					if (cmdbCientityProperties.getT10302().equals(item.getDeviceType())){
                        SafeaccessSwitche safeaccessSwitche = new SafeaccessSwitche();
                        safeaccessSwitche.setSwIp(item.getDeviceIp());
                        QueryWrapper<SafeaccessSwitche> queryWrapper = Condition.getQueryWrapper(safeaccessSwitche);
                        SafeaccessSwitche safeaccessSwitche1 = safeAccessSwitchesService.getOne(queryWrapper);
                        if (Objects.isNull(safeaccessSwitche1)) {
                            throw new RuntimeException("网络交换机设备退运时未查询到网络交换机入网信息, 联系管理员处理.");
                        }
                    }

                });
                updateCmdbEntity(dto.getDetailDTOS());
                updateI6000Entity(dto.getDetailDTOS());
            }
            //修改库存信息
            for (DeviceReturnedDetail detail : detailList) {
                if (!cmdbCientityProperties.getReturnWarehouse().equals(detail.getDeviceStatus())) {
                    continue;
                }
                DeviceReturnedDetailDTO returnedDetailDTO = JSONObject.parseObject(detail.getDeviceJson(), DeviceReturnedDetailDTO.class);
                DeviceInventory deviceInventory = deviceInventoryService.getOne(new LambdaQueryWrapper<DeviceInventory>().eq(DeviceInventory::getRegionCode, user.getRegionCode())
                        .eq(DeviceInventory::getCropId, user.getCorpId()).eq(DeviceInventory::getDeviceCategory, detail.getDeviceCategory())
                        .eq(DeviceInventory::getDeviceType, detail.getDeviceType()).eq(DeviceInventory::getWarehouse, detail.getInWarehouseId()).last(" FOR UPDATE"));
                if (!Objects.isNull(deviceInventory)) {
                    deviceInventoryService.update(Wrappers.<DeviceInventory>lambdaUpdate()
                            .eq(DeviceInventory::getId, deviceInventory.getId())
                            .eq(DeviceInventory::getVersion, deviceInventory.getVersion())
                            .set(DeviceInventory::getInventoryNum, deviceInventory.getInventoryNum() + 1)
                            .set(DeviceInventory::getVersion, deviceInventory.getVersion() + 1)
                            .set(DeviceInventory::getUpdateTime, new Date())
                            .set(DeviceInventory::getUpdateUser, user.getUserId()));
                } else {
                    deviceInventory = new DeviceInventory();
                    deviceInventory.setRegionCode(user.getRegionCode());
                    deviceInventory.setCropId(Long.valueOf(user.getCorpId()));
                    deviceInventory.setDeviceCategory(returnedDetailDTO.getDeviceCategoryCode());
                    deviceInventory.setDeviceCategoryName(returnedDetailDTO.getDeviceCategory());
                    deviceInventory.setDeviceType(returnedDetailDTO.getDeviceTypeCode());
                    deviceInventory.setDeviceTypeName(returnedDetailDTO.getDeviceType());
                    deviceInventory.setWarehouse(detail.getInWarehouseId());
                    deviceInventory.setWarehouseName(detail.getInWarehouse());
                    deviceInventory.setInventoryNum(1);
                    deviceInventory.setVersion(0L);
                    deviceInventory.setCreateUser(user.getUserId());
                    deviceInventory.setCreateTime(new Date());
                    deviceInventory.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                    deviceInventoryService.save(deviceInventory);
                }
            }

            //释放设备IP资源
            if (CollectionUtil.isNotEmpty(detailList)) {
                List<Map<String, String>> userAccessServiceList = new ArrayList<>();
                List<Long> returnSwtichesList = new ArrayList<>();
                List<SwitcherDeviceListDTO> switcherDeviceListDTOList = detailList.stream().map(item -> {
                    SwitcherDeviceListDTO deviceListDTO = new SwitcherDeviceListDTO();
                    if (StringUtil.isNotBlank(item.getReturnedId()) && StringUtil.isNotBlank(item.getDeviceIp())) {
                        //增加校验
                        List<String> deviceTypeList = cmdbCientityProperties.getModelIdList(Constants.DEVICE_SAFE_ACCESS_TYPE);
                        if(deviceTypeList.contains(item.getDeviceType())) {
                            String subnetId = checkSubnetAndIp(item.getDeviceIp());
                            if (StringUtils.isEmpty(subnetId)) {
                                throw new RuntimeException("设备退运-查询所属子网异常" + item.getDeviceIp());
                            }
                            safeaccessIppoolService.releaseIpBySubnetIdAndIp(subnetId, item.getDeviceIp());
                        }
                        // todo 调用设备退运推送数据同步服务
                        deviceListDTO.setDeviceIp(item.getDeviceIp());
                        deviceListDTO.setDeviceMac(item.getDeviceMac());
                        deviceListDTO.setDeviceSubnet(item.getSubnetId());
                        deviceListDTO.setDeviceCode(item.getDeviceCode());
                        deviceListDTO.setDeviceType(item.getDeviceType());
                        if (item.getDeviceType().equals(cmdbCientityProperties.getT10302())) {
                            if (StringUtil.isNotBlank(item.getSwIp())) {
                                deviceListDTO.setSwitchesIp(item.getSwIp());
                                deviceListDTO.setSwitchesPassword(item.getSwPass());
                            }
                            returnSwtichesList.add(item.getDeviceId());
                        } else {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("IP", item.getDeviceIp());
                            map.put("MAC", item.getDeviceMac());
                            userAccessServiceList.add(map);
                        }
                    }
                    return deviceListDTO;
                }).collect(Collectors.toList());
                idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, user.getRegionCode(), "1");
                // 交换机使用状态更新
                if (CollectionUtils.isNotEmpty(returnSwtichesList)) {
                    safeAccessSwitchesService.update(Wrappers.<SafeaccessSwitche>lambdaUpdate()
                            .set(SafeaccessSwitche::getSwState, "0").in(SafeaccessSwitche::getDeviceId, returnSwtichesList));
                }
                //清除用户入网信息
                if (CollectionUtil.isNotEmpty(userAccessServiceList)) {
                    for (Map<String, String> map : userAccessServiceList) {
                        userAccessService.update(Wrappers.<SafeaccessUserAccess>lambdaUpdate()
                                .set(SafeaccessUserAccess::getIsDeleted, IdevelopConstant.DB_IS_DELETED)
                                .eq(SafeaccessUserAccess::getIpAddress, map.get("IP"))
                                .eq(SafeaccessUserAccess::getMacAddress, map.get("MAC"))
                        );
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
                idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
            }
        }
        if (1 == dto.getWorkerStatus()) {
            baseMapper.update(Wrappers.<DeviceReturned>lambdaUpdate().eq(DeviceReturned::getId, dto.getId()).set(DeviceReturned::getUpdateTime, new Date())
                    .set(DeviceReturned::getProcessStatus, returnNode).set(DeviceReturned::getStatus, DeviceReturnedEnum.TEMPORARILY.getCode()));
            detailMapper.update(Wrappers.<DeviceReturnedDetail>lambdaUpdate().set(DeviceReturnedDetail::getDeviceStatus, "").set(DeviceReturnedDetail::getInWarehouse, "")
                    .set(DeviceReturnedDetail::getInWarehouseCode, "").set(DeviceReturnedDetail::getAddress, "").eq(DeviceReturnedDetail::getReturnedId, dto.getId()));
        }
        if (dto.getWorkerStatus() == 1) {
            LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(ApproveRecord::getOptOpinion, "待审批").set(ApproveRecord::getOptTitle, "待审批").eq(ApproveRecord::getFilingNo, dto.getId()).eq(ApproveRecord::getNodeId, DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode());
            approveRecordService.update(updateWrapper);
            //驳回
            try {
                List<HussarTaskVo> hussarTaskVos = hussarBpmService.queryTaskId(returned.getFilingNo());
                HussarBpmDto hussarBpmDto = new HussarBpmDto();
                hussarBpmDto.setTaskId(hussarTaskVos.get(0).getTaskId());
                hussarBpmDto.setUserId(user.getUserId().toString());
                hussarBpmDto.setComment(StringUtil.isNotBlank(dto.getComment()) ? dto.getComment() : "驳回");
                hussarBpmDto.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark());
                hussarBpmDto.setRejectNode(DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_APPLY.getNode());
                hussarBpmDto.setVariable(variable);
                hussarBpmDto.setBusinessKey(returned.getFilingNo());
                com.lnsoft.hussar.bpm.tool.api.R<JSONArray> hussarR = hussarBpmClient.anyNodeReject(hussarBpmDto);
                if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
                    throw new RuntimeException("驳回流程异常!");
                }
            } catch (Exception e) {
                throw new RuntimeException("驳回流程异常!");
            }
        } else {
            //同意
            HussarBpmDTO hussarBpmDTO = new HussarBpmDTO();
            hussarBpmDTO.setBusinessKey(returned.getFilingNo());
            hussarBpmDTO.setComment(dto.getComment());
            hussarBpmDTO.setVariable(variable);
            hussarBpmDTO.setParticipantType("2");
            hussarBpmDTO.setProcessDefinitionKey(HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark());
            hussarBpmDTO.setTaskDefinitionKey(dto.getTaskDefinitionKey());
            hussarBpmDTO.setTaskType("1");
            try {
                R<List<HussarComplateVo>> hussarR = hussarBpmService.hussarSubmit(hussarBpmDTO);
                if (hussarR.getCode() != ResultCode.SUCCESS.getCode()) {
                    throw new RuntimeException("提交流程异常!");
                }
            } catch (Exception e) {
                throw new RuntimeException("提交流程异常!");
            }
        }
        return R.success(ResultCode.SUCCESS);
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

    /**
     * 同步i6000
     *
     * @param deviceList
     */
    @Override
    public boolean updateI6000Entity(List<DeviceReturnedDetailDTO> deviceList) {
        if (CollectionUtil.isEmpty(deviceList)) {
            return false;
        }
        Map<String, Map<String, Object>> hashMap = new HashMap<>();
        Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
        // 修改 资产台账
        deviceList.forEach(s -> {
            //获取模型 cid
            Long ciId = s.getCiId();
            Map<String, Object> ent = s.getEntity();
            if (Objects.isNull(ent)) {
                ent = new HashMap<>();
            }
            ent.put("erpAssetStatus", 3);
            ent.put(CmdbAttrConstant.ID, s.getDeviceId());
            ent.put(CmdbAttrConstant.UUID, s.getUuid());
            ent.put(CmdbAttrConstant.CI_ID, ciId);
            ent.put(CmdbAttrConstant.DEVICE_NAME, s.getDeviceName());
            ent.put(CmdbAttrConstant.DEVICE_TYPE, s.getDeviceType());
            ent.put(CmdbAttrConstant.DEVICE_CODE, s.getDeviceCode());
            ent.put(CmdbAttrConstant.DEVICE_STATUS_CODE, s.getDeviceStatus());
            ent.put(CmdbAttrConstant.DEVICE_STATUS, cmdbCientityProperties.getReturnWarehouse().equals(s.getDeviceStatus()) ? "退运在库" : "待报废");
            ent.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, s.getInWarehouseId());
            ent.put(CmdbAttrConstant.IN_WAREHOUSE, s.getInWarehouse());
            ent.put(CmdbAttrConstant.WAREHOUSE_LOCATION, s.getAddress());
            ent.put(CmdbAttrConstant.IP, "");
            ent.put(CmdbAttrConstant.SUBNET_ID, "");
            ent.put(CmdbAttrConstant.SUBNET_NAME, "");
            ent.put(CmdbAttrConstant.USER, "");
            ent.put(CmdbAttrConstant.DEVICE_USER_TEAM, "");
            ent.put(CmdbAttrConstant.USER_TEL, "");
            ent.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, "");
            ent.put(CmdbAttrConstant.USER_EMAIL, "");
            ent.put(CmdbAttrConstant.RECEIVING_PERSON, "");
            ent.put(CmdbAttrConstant.RECEIVING_TEL, "");
            ent.put(CmdbAttrConstant.RECEIVING_ID_CARD, "");
            ent.put(CmdbAttrConstant.RECEIVING_GROUP, "");
            ent.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, "");
            ent.put(CmdbAttrConstant.DEVICE_CHARGE_TEL, "");
            ent.put(CmdbAttrConstant.ASSET_CODE_ERP, s.getAssetCodeErp());
            if (StringUtil.isNotBlank(erpI6000MapByCiId.get(s.getDeviceType()))) {
                ent.put("CITYPE_ID", erpI6000MapByCiId.get(s.getDeviceType()));
                hashMap.put(ent.get(CmdbAttrConstant.UUID).toString(), ent);
            }
        });
        //同步i6000
        if (CollectionUtil.isNotEmpty(hashMap)) {
            i6000Service.i6000Batchupdate(hashMap);
        }
        return true;
    }

    /**
     * 校验是否全部归还
     *
     * @param dto
     * @return
     */
    @Override
    public R checkIsAllReturn(DeviceReturnedDTO dto) {
        if (CollectionUtil.isNotEmpty(dto.getDetailDTOS())) {
            List<DeviceReturnedDetail> detailList = dto.getDetailDTOS().stream().map(item -> {
                DeviceReturnedDetail detail = new DeviceReturnedDetail();
                BeanUtil.copyProperties(item, detail);
                detail.setIsReturned("1");
                return detail;
            }).collect(Collectors.toList());
            detailService.updateBatchById(detailList);
			/*long count = detailList.stream().filter(item -> Objects.equals("1", item.getIsReturned())).count();
			if (count != detailList.size()) {
				return R.fail("设备未全部归还!");
			}*/
            update(Wrappers.<DeviceReturned>lambdaUpdate().set(DeviceReturned::getIsAllReturned, "1").eq(DeviceReturned::getId, dto.getId()));
            return R.success(ResultCode.SUCCESS);
        }
		/*Long notReturnedSize = detailMapper.selectCount(Wrappers.<DeviceReturnedDetail>lambdaQuery().eq(DeviceReturnedDetail::getReturnedId, dto.getId()).eq(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(DeviceReturnedDetail::getIsReturned, "0"));
		if (notReturnedSize != 0) {
			return R.fail("设备未全部归还!");
		}*/
        return R.success(ResultCode.SUCCESS);
    }

    private R<DeviceReturnedVO> saveDeviceReturnedDetail(DeviceReturnedDTO dto, IdevelopUser user) {
        List<DeviceReturnedDetailDTO> detailDTOS = dto.getDetailDTOS();
        DeviceReturnedVO vo = Convert.convert(DeviceReturnedVO.class, dto);
        if (CollectionUtil.isEmpty(detailDTOS)) {
            return R.data(vo);
        }
        detailService.update(Wrappers.<DeviceReturnedDetail>lambdaUpdate().set(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_IS_DELETED).eq(DeviceReturnedDetail::getReturnedId, dto.getId()));
        List<String> collect = detailDTOS.stream().map(DeviceReturnedDetailDTO::getUuid).collect(Collectors.toList());
        List<DeviceReturnedDetail> list = detailService.list(Wrappers.<DeviceReturnedDetail>lambdaQuery().eq(DeviceReturnedDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).in(DeviceReturnedDetail::getUuid, collect));
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> collect1 = list.stream().map(DeviceReturnedDetail::getReturnedId).collect(Collectors.toList());
            List<DeviceReturned> one = list(Wrappers.<DeviceReturned>lambdaQuery().ne(DeviceReturned::getStatus, "3").in(DeviceReturned::getId, collect1));
            if (ObjectUtil.isNotEmpty(one)) {
                return R.fail(ResultCode.FAILURE, list.get(0).getDeviceName() + "该设备已在" + one.get(0).getFilingNo() + "中添加");
            }
        }
        List<SafeaccessSwitche> switches = safeaccessSwitcheService.list(Wrappers.<SafeaccessSwitche>lambdaQuery().eq(SafeaccessSwitche::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(SafeaccessSwitche::getSwState, "1").in(SafeaccessSwitche::getDeviceUuid, collect));
        Map<String, SafeaccessSwitche> switcheMap = switches.stream().collect(Collectors.toMap(SafeaccessSwitche::getDeviceUuid, Function.identity()));
        if (CollectionUtil.isNotEmpty(detailDTOS)) {
            List<DeviceReturnedDetail> returnedDetails = new ArrayList<>();
            for (DeviceReturnedDetailDTO item : detailDTOS) {
                DeviceReturnedDetail detail = new DeviceReturnedDetail();
                BeanUtil.copyProperties(item, detail);
                if (StringUtil.isBlank(detail.getDeviceJson())) {
                    detail.setDeviceJson(JSONObject.toJSONString(item));
                    detail.setDeviceType(item.getDeviceTypeCode());
                    detail.setDeviceCategory(item.getDeviceCategoryCode());
                    detail.setDeviceSource(item.getDeviceSourceCode());
                    detail.setReceivingPerson(item.getUser());
                    detail.setReceivingTel(item.getUserTel());
                }
                detail.setId(null);
                detail.setReturnedId(dto.getId());
                detail.setCreateTime(new Date());
                detail.setCreateUser(user.getUserId());
                detail.setCreateDept(user.getDeptId());
                if (item.getDeviceType().contains("交换")) {
                    detail.setSwIp(switcheMap.get(item.getUuid()).getSwIp());
                    detail.setSwPass(switcheMap.get(item.getUuid()).getSwPass());
                }
                detail.setDeviceStatus(item.getDeviceStatusCode());
                detail.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                returnedDetails.add(detail);
            }
            detailService.saveBatch(returnedDetails);
            vo.setReturnedDetailVOS(Convert.convert(new TypeReference<List<DeviceReturnedDetailVO>>() {
            }, detailDTOS));
        }
        return R.data(vo);
    }

    private R<DeviceReturnedVO> checkReturned(DeviceReturnedDTO dto) {
        if (StringUtil.isBlank(dto.getReturnReason())) {
            return R.fail("退运资产信息异常!");
        }
		/*if (StringUtil.isBlank(dto.getAcceptPhone())) {
			return R.fail("请输入有效的联系方式!");
		}*/
        return null;
    }

    /**
     * 保存 cmdb的接口
     *
     * @param deviceList
     * @return
     */
    public boolean updateCmdbEntity(List<DeviceReturnedDetailDTO> deviceList) {
        if (CollectionUtil.isEmpty(deviceList)) {
            return false;
        }
        Map<Long, Map<String, Object>> hashMap = new HashMap<>();
        // 修改 资产台账
        deviceList.forEach(s -> {
            //获取模型 cid
            Long ciId = s.getCiId();
            Map<String, Object> ent = s.getEntity();
            if (Objects.isNull(ent)) {
                ent = new HashMap<>();
            }
            ent.put("erpAssetStatus", 3);
            ent.put(CmdbAttrConstant.ID, s.getDeviceId());
            ent.put(CmdbAttrConstant.UUID, s.getUuid());
            ent.put(CmdbAttrConstant.CI_ID, ciId);
            // ent.put(CmdbAttrConstant.DEVICE_NAME, s.getDeviceName());
            // ent.put(CmdbAttrConstant.DEVICE_TYPE, s.getDeviceType());
            // ent.put(CmdbAttrConstant.DEVICE_CODE, s.getDeviceCode());
            ent.put(CmdbAttrConstant.DEVICE_STATUS_CODE, s.getDeviceStatus());
            ent.put(CmdbAttrConstant.DEVICE_STATUS, cmdbCientityProperties.getReturnWarehouse().equals(s.getDeviceStatus()) ? "退运在库" : "待报废");
            ent.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, s.getInWarehouseId());
            ent.put(CmdbAttrConstant.IN_WAREHOUSE, s.getInWarehouse());
            ent.put(CmdbAttrConstant.WAREHOUSE_LOCATION, s.getAddress());
            ent.put(CmdbAttrConstant.IP, "");
            ent.put(CmdbAttrConstant.SUBNET_ID, "");
            ent.put(CmdbAttrConstant.SUBNET_NAME, "");
            ent.put(CmdbAttrConstant.USER, "");
            ent.put(CmdbAttrConstant.DEVICE_USER_TEAM, "");
            ent.put(CmdbAttrConstant.USER_TEL, "");
            ent.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, "");
            ent.put(CmdbAttrConstant.USER_EMAIL, "");
            ent.put(CmdbAttrConstant.RECEIVING_PERSON, "");
            ent.put(CmdbAttrConstant.RECEIVING_TEL, "");
            ent.put(CmdbAttrConstant.RECEIVING_ID_CARD, "");
            ent.put(CmdbAttrConstant.RECEIVING_GROUP, "");
            ent.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, "");
            ent.put(CmdbAttrConstant.DEVICE_CHARGE_TEL, "");
            // todo 应该获取实际的ERP编码
            ent.put(CmdbAttrConstant.ASSET_CODE_ERP, s.getAssetCodeErp());
            hashMap.put(Long.parseLong(ent.get(CmdbAttrConstant.ID).toString()), ent);
        });
        //保存cmdb
        iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
        return true;
    }


}

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

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.*;
import com.lnsoft.device.api.asset.entity.DeviceInventory;
import com.lnsoft.device.api.asset.entity.DeviceInventoryLog;
import com.lnsoft.device.api.asset.mapper.DeviceInventoryLogMapper;
import com.lnsoft.device.api.asset.mapper.DeviceInventoryMapper;
import com.lnsoft.device.api.asset.service.IDeviceInventoryService;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransEqunrItem;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.i6000.response.I6000ResultResp;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.entity.LogOpt;
import com.lnsoft.device.api.res.service.IDeviceAttachService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.api.warehouse.dto.*;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnQingDao;
import com.lnsoft.device.api.warehouse.entity.DeviceStorage;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageList;
import com.lnsoft.device.api.warehouse.mapper.DeviceStorageMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceRecordListService;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageListService;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageService;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageExportDynamicVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageExportVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageSdnVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.constant.CommonConstant;
import com.lnsoft.device.dto.DeviceRecordListDTO;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.eums.ErpOperationEnum;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.publisher.QDDSwitcherPublisher;
import com.lnsoft.device.utils.CmdbDictUtil;
import com.lnsoft.device.utils.OrderNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备入库表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Service
@Slf4j
public class DeviceStorageServiceImpl extends BaseServiceImpl<DeviceStorageMapper, DeviceStorage> implements IDeviceStorageService {

    @Resource
    private IProjectManagerDetailService projectManagerDetailService;
    @Resource
    private OrderNumberUtil orderNumberUtil;
    @Resource
    private ILogOptService logOptService;
    @Resource
    private IDeviceStorageListService deviceStorageListService;
    @Resource
    private IDeviceAttachService deviceAttachService;
    @Resource
    private ICmdbService cmdbService;
    @Resource
    private IDeviceRecordListService deviceRecordListService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private II6000Service ii6000Service;
    @Resource
    private IErpService erpService;
    @Resource
    private ISafeaccessSwitcheService switchesService;
    @Resource
    private CmdbCientityProperties ciEntityProperties;
    @Resource
    private IDeviceInventoryService deviceInventoryService;
    @Resource
    private DeviceInventoryMapper inventoryMapper;
    @Resource
    private DeviceInventoryLogMapper inventoryLogMapper;

    private static final String STATUS = "0";


    @Override
    public IPage<DeviceStorageVO> selectDeviceStoragePage(IPage<DeviceStorageVO> page, DeviceStorageVO deviceStorage) {
        return page.setRecords(baseMapper.selectDeviceStoragePage(page, deviceStorage));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceStorage saveEntry(DeviceStorageDTO entity) {
        entity.setIsTemp(0);
        entity.setStatus(1);
        entity.setStorageTime(LocalDateTime.now());
        // 默认交流220V
        entity.setVoltageLevel(ciEntityProperties.getCientityId(CmdbCientityConstant.DC_220V));
        IdevelopUser user = SecureUtil.getUser();
        if (StringUtil.isNotBlank(entity.getId())) {
            this.updateById(entity);
        } else {
            fillData(entity, user);
            this.save(entity);
            insertLog(entity, CommonConstant.STORAGE_ADD);
        }
        // 保存子表
        long time1 = System.currentTimeMillis();
        saveDeviceList(entity);
        long time2 = System.currentTimeMillis();
        log.info("保存入库子表-saveDeviceList方法耗时》》》{}毫秒", time2 - time1);
        // 同步cmdb
        syncCmdb(entity);
        long time4 = System.currentTimeMillis();
        log.info("同步cmdb-syncCmdb方法耗时》》》{}毫秒", time4 - time2);
        // 库存管理
        storageInventory(entity);
        long time6 = System.currentTimeMillis();
        log.info("增加库存-storageInventory方法耗时》》》{}毫秒", time6 - time4);
        insertLog(entity, CommonConstant.STORAGE_END);
        if (entity.getRegionCode().contains("3702")) {
            // 同步sdn青岛
            long time8 = System.currentTimeMillis();
            try {
                sdnQingDao(entity);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            log.info("同步青岛sdn-sdnQingDao方法耗时》》》{}毫秒", time8 - time6);
        }
        return entity;
    }

    private void sdnQingDao(DeviceStorageDTO entity) {
        IdevelopUser user = SecureUtil.getUser();
        List<DeviceStorageList> devices = entity.getDevices();
        DeviceSdnQingDao deviceSdnQingDao = new DeviceSdnQingDao();
        List<DeviceStorageSdnVO> dynamicVOArrayList = new ArrayList<>();
        for (DeviceStorageList device : devices) {
            String ownerUnitName = entity.getOwnerUnitName();
            if (StringUtils.equals("国网青岛供电公司", entity.getOwnerUnitName())) {
                ownerUnitName = "国网青岛供电公司本部";
            }
            String deviceCategoryValue = getDictValue(cmdbDictProperties.getDeviceClaccify(), entity.getDeviceCategory());
            String deviceTypeValue = getDictValue(cmdbDictProperties.getDeviceType(), entity.getDeviceType());
            String deviceInfo = device.getDeviceHardwareInfo();
            DeviceStorageSdnVO dynamic = JSON.parseObject(deviceInfo, DeviceStorageSdnVO.class);
            dynamic.setDeviceCode(device.getDeviceCode());
            dynamic.setDeviceName(device.getFullName());
            dynamic.setId(device.getId());
            dynamic.setStorageTime(String.valueOf(entity.getStorageTime()));
            dynamic.setDeviceCategory(deviceCategoryValue);
            dynamic.setDeviceType(deviceTypeValue);
            dynamic.setOwnerUnit(ownerUnitName);
            dynamic.setDept(entity.getDeptName());
            dynamic.setWbsProject(entity.getWbsProject());
            dynamic.setWbsElement(entity.getWbsElement());
            dynamic.setAccount(user.getAccount());
            dynamicVOArrayList.add(dynamic);
        }
        deviceSdnQingDao.setFlag("0");
        deviceSdnQingDao.setSparesInDTOList(dynamicVOArrayList);
        QDDSwitcherPublisher.publishEvent(deviceSdnQingDao);
    }


    private void syncCmdb(DeviceStorageDTO dto) {
        List<DeviceStorageSaveCmdbDTO> list = baseMapper.findById(dto.getId());
        Map<String, Object> backMap;
        Map<String, Map<String, Object>> map = Maps.newHashMap();
        Map<Object, Object> deviceTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
        Map<String, String> dictI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());

        DeviceStorageSaveCmdbDTO storage = list.get(0);
        String deviceType = storage.getDeviceType();
        String deviceCategory = storage.getDeviceCategory();
        long time1 = System.currentTimeMillis();
        for (DeviceStorageSaveCmdbDTO deviceStorage : list) {
            String uuid = deviceStorage.getUuid();
            // 从json中解析资产信息、硬件配置等导入数据
            String deviceInfo = deviceStorage.getDeviceHardwareInfo();
            DeviceStorageExportDynamicVO dynamic = JSON.parseObject(deviceInfo, DeviceStorageExportDynamicVO.class);
            if (dynamic != null) {
                BeanUtils.copyProperties(dynamic, deviceStorage);
            }
            // 填充字典key、value
            try {
                fillDeviceData(deviceStorage, deviceTypeMapErp, dictI6000MapByCiId);
                fillSwitchesInfo(deviceStorage);
            } catch (Exception e) {
                log.info("填充cmdb字典key、value异常：{}", e.getMessage());
                throw new ServiceException("填充保存cmdb字典key、value失败,原因:" + e.getMessage());
            }
            Map<String, Object> dataMap = BeanUtil.beanToMap(deviceStorage, false, true);
            dataMap.put(CmdbAttrConstant.SOURCE_SYSTEM, ciEntityProperties.getSourceSystem1());
            dataMap.put(CmdbAttrConstant.OPERATION_UNIT_CODE, deviceStorage.getOwnerUnitCode());
            dataMap.put(CmdbAttrConstant.OPERATION_UNIT, deviceStorage.getOwnerUnit());
            map.put(uuid, dataMap);
        }
        long time2 = System.currentTimeMillis();
        log.info("保存cmdb填充数据耗时》》》{}毫秒", time2 - time1);

        HardwareBasicTree basicTree = new HardwareBasicTree();
        basicTree.setDeviceType(deviceType);
        basicTree.setDeviceClaccify(deviceCategory);
        Long ciId = cmdbService.getCiId(basicTree);
        try {
            backMap = cmdbService.cientityBatchsave(ciId, map, TransactionActionType.INSERT);
        } catch (Exception e) {
            log.info("保存cmdb异常：{}", e.getMessage());
            throw new ServiceException("新增cmdb台账失败,原因:" + e.getMessage());
        }
        long time3 = System.currentTimeMillis();
        log.info("执行保存cmdb耗时》》》{}毫秒", time3 - time2);
        if (CollectionUtil.isNotEmpty(backMap)) {
            // 更新子表配置项id、新增台账是否成功
            baseMapper.updateByMap(backMap);
        }
        long time4 = System.currentTimeMillis();
        log.info("更新子表、新增交换机耗时》》》{}毫秒", time4 - time3);

        if (StringUtils.equals(dto.getDeviceSource(), ciEntityProperties.getDeviceSource())) {
            // 同步i6000 1成功2失败
            Integer i6000Status = 2;
            Boolean i6000Erp = Boolean.FALSE;
            if (dictI6000MapByCiId.containsKey(deviceType)) {
                try {
                    // 同步I6000
                    I6000ResultResp i6000ResultResp = ii6000Service.i6000Batchsave(dictI6000MapByCiId.get(deviceType), map);
                    String result = i6000ResultResp.getSuccessful();
                    if ("true".equals(result)) {
                        i6000Status = 1;
                        Map<Long, Map<String, Object>> updateEntity = new HashMap<>();
                        Map<String, Object> updateMap = new HashMap<>();

                        for (Map.Entry<String, Map<String, Object>> stringMapEntry : map.entrySet()) {
                            Map<String, Object> value = stringMapEntry.getValue();
                            Object I6000CiId = value.get(CmdbAttrConstant.I6000_CI_ID);

                            updateMap.put(CmdbAttrConstant.I6000_CI_ID, I6000CiId);
                            updateEntity.put(ciId, updateMap);
                        }
                        cmdbService.cientityBatchupdate(updateEntity, TransactionActionType.UPDATE);
                    }
                    i6000Erp = Boolean.TRUE;
                } catch (Exception e) {
                    log.info("同步I6000出现异常，异常原因：{}", e.getMessage());
                }
                DeviceStorage deviceStorage = new DeviceStorage();
                deviceStorage.setId(dto.getId());
                deviceStorage.setStatusI6000(i6000Status);
                this.updateById(deviceStorage);
            }
            long time5 = System.currentTimeMillis();
            log.info("同步I6000耗时》》》{}毫秒", time5 - time4);
            // 同步erp
            ErpTransEqunrResp result = null;
            if (ciEntityProperties.getCientityId(CmdbCientityConstant.T105).equals(storage.getDeviceCategoryCode())
                    || ciEntityProperties.getCientityId(CmdbCientityConstant.T107).equals(storage.getDeviceCategoryCode())) {
                if (deviceTypeMapErp.containsKey(deviceType)) {
                    try {
                        // 更新ERP资产编码使用状态：已使用、i6000同步状态：已同步
                        defaultUpdateRecordList(dto, 1, "1");
                        i6000Erp = Boolean.TRUE;

                        // 同步ERP
                        result = syncErp(dto, list);
                    } catch (Exception e) {
                        log.info("同步ERP出现异常，异常原因：{}", e.getMessage());
                        ErpTransEqunrResp erpTransEqunrResp = new ErpTransEqunrResp();
                        erpTransEqunrResp.setCode("E");
                        result = erpTransEqunrResp;
                    }
                }
            }
            long time6 = System.currentTimeMillis();
            log.info("同步erp耗时》》》{}毫秒", time6 - time5);
            // 更新erp、i6000同步状态
            if (i6000Erp) {
                syncStorageList(dto, result, i6000Status);
            }
            long time7 = System.currentTimeMillis();
            log.info("更新设备列表erp、i6000同步状态耗时》》》{}毫秒", time7 - time6);
        }

    }

    private void storageInventory(DeviceStorageDTO dto) {
        IdevelopUser user = SecureUtil.getUser();
        LambdaQueryWrapper<DeviceInventory> queryWrapper = new LambdaQueryWrapper<DeviceInventory>()
                .eq(DeviceInventory::getRegionCode, user.getRegionCode())
                .eq(DeviceInventory::getCropId, user.getCorpId())
                .eq(DeviceInventory::getDeviceCategory, dto.getDeviceCategory())
                .eq(DeviceInventory::getDeviceType, dto.getDeviceType())
                .eq(DeviceInventory::getWarehouse, dto.getWarehouse());
        DeviceInventory deviceInventory = deviceInventoryService.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(deviceInventory)) {
            // 库存不存在,新增
            deviceInventory = new DeviceInventory();
            deviceInventory.setDeviceCategory(dto.getDeviceCategory());
            deviceInventory.setDeviceCategoryName(getDictValue(cmdbDictProperties.getDeviceClaccify(), dto.getDeviceCategory()));
            deviceInventory.setDeviceType(dto.getDeviceType());
            deviceInventory.setDeviceTypeName(getDictValue(cmdbDictProperties.getDeviceType(), dto.getDeviceType()));
            deviceInventory.setWarehouse(dto.getWarehouse());
            deviceInventory.setWarehouseName(inventoryMapper.getWarehouseName(dto.getWarehouse()));
            deviceInventory.setInventoryNum(dto.getDeviceNum());
            deviceInventory.setVersion(0L);
            deviceInventory.setCropId(Long.valueOf(user.getCorpId()));
            deviceInventory.setRegionCode(user.getRegionCode());
            deviceInventory.setCreateUser(user.getUserId());
            deviceInventory.setCreateTime(new Date());
            deviceInventory.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
            deviceInventoryService.save(deviceInventory);
        } else {
            // 更新
            queryWrapper.last(" FOR UPDATE");
            deviceInventory = deviceInventoryService.getOne(queryWrapper);
            deviceInventory.setVersion(deviceInventory.getVersion() + 1);
            deviceInventory.setInventoryNum(deviceInventory.getInventoryNum() + dto.getDeviceNum());
            deviceInventory.setUpdateTime(new Date());
            deviceInventory.setUpdateUser(user.getUserId());
            deviceInventoryService.updateById(deviceInventory);
        }
        changeInventoryLog(dto.getDeviceNum(), deviceInventory);
    }

    private void changeInventoryLog(Integer deviceNum, DeviceInventory source) {
        DeviceInventoryLog inventoryLog = BeanUtil.copyProperties(source, DeviceInventoryLog.class);
        inventoryLog.setId(null);
        IdevelopUser user = SecureUtil.getUser();
        inventoryLog.setInventoryNum(deviceNum);
        inventoryLog.setInventoryMan(user.getUserName());
        inventoryLog.setInventoryType("+");
        inventoryLogMapper.insert(inventoryLog);
    }

    private void fillSwitchesInfo(DeviceStorageSaveCmdbDTO deviceStorage) {
        // 交换机标签
        if (ciEntityProperties.getCientityId(CmdbCientityConstant.T103).equals(deviceStorage.getDeviceCategoryCode()) &&
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10302).equals(deviceStorage.getDeviceTypeCode())) {
            deviceStorage.setSwitchLabel(deviceStorage.getDeviceName());
        }
    }

    private void defaultUpdateRecordList(DeviceStorageDTO dto, Integer i6000Status, String erpStatus) {
        List<DeviceStorageList> devices = dto.getDevices();
        for (DeviceStorageList device : devices) {
            DeviceRecordListDTO updateDto = new DeviceRecordListDTO();
            updateDto.setI6000Status(String.valueOf(i6000Status));
            updateDto.setErpAssetStatus(erpStatus);
            updateDto.setErpAssetCode(device.getErpAssetCode());
            deviceRecordListService.batchUpdateDeviceRecordErpAssetStatus(updateDto);
        }
    }

/*	private List<SafeaccessSwitche> saveSwitchesFillData(Map<String, Map<String, Object>> dataMap, Map<String, Object> backMap) {
		IdevelopUser user = SecureUtil.getUser();
		List<SafeaccessSwitche> result = Lists.newArrayList();
		for (Map.Entry<String, Map<String, Object>> entry : dataMap.entrySet()) {
			SafeaccessSwitche switches = new SafeaccessSwitche();
			String uuid = entry.getKey();
			Map<String, Object> value = entry.getValue();
			Object ciEntityId = backMap.get(uuid);
			switches.setDeviceId(String.valueOf(ciEntityId));
			switches.setSwName(String.valueOf(value.get("deviceName")));
			switches.setSwMaker(String.valueOf(value.get("maker")));
			switches.setSwFirm(String.valueOf(value.get("brand")));
			switches.setSwSeries(String.valueOf(value.get("series")));
			switches.setSwModel(String.valueOf(value.get("deviceModel")));
			switches.setDeviceUuid(uuid);
			switches.setDeviceCode(String.valueOf(value.get("deviceCode")));
			switches.setAuthState("0");
//			switches.setIs3(String.valueOf(value.get("networkDeviceType")));
			switches.setFillMan(user.getUserName());
			switches.setFillDate(Constants.DATE_FORMAT_TIME.format(new Date()));
			switches.setCompany(user.getRegionCode());
			switches.setRegionCode(user.getRegionCode());
			switches.setDeptCode(user.getDeptId());
			switches.setCreateTime(new Date());
			switches.setCreateUser(user.getUserId());
			if (ObjectUtil.isNotEmpty(value.get("remark"))) {
				switches.setRemark(String.valueOf(value.get("remark")));
			}
			Integer opticalPortNum = ObjectUtil.isEmpty(value.get("opticalPortNum")) ? 0 : Integer.valueOf((String) value.get("opticalPortNum"));
			Integer electricPortNum = ObjectUtil.isEmpty(value.get("electricPortNum")) ? 0 : Integer.valueOf((String) value.get("electricPortNum"));
			switches.setPortsCount(String.valueOf(opticalPortNum + electricPortNum));
			result.add(switches);
		}
		return result;
	}*/

    private ErpTransEqunrResp syncErp(DeviceStorageDTO dto, List<DeviceStorageSaveCmdbDTO> list) {
        Map<Object, Object> powerLevelMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getPowerLevel());
        Map<Object, Object> deviceAddMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceAdd());
        Map<Object, Object> deviceTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
        Map<Object, Object> unifiedCodeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getUnifiedCode());
//		Map<Object, Object> countryAreaMapErp = cmdbDictProperties.getCountryAreaErpMapByCiId(cmdbDictProperties.getCountryArea());
        Map<Object, Object> factoryAreaMapErp = cmdbDictProperties.getFactoryAreaErpMapByCiId(cmdbDictProperties.getFactoryAreaCode());

        ErpTransEqunr erp = new ErpTransEqunr();
        erp.setXtdocId(dto.getId());
        erp.setXtdocNo(dto.getSerialNumber());
        erp.setOperationType(ErpOperationEnum.M);
        List<ErpTransEqunrItem> erpList = Lists.newArrayList();
        for (DeviceStorageSaveCmdbDTO storage : list) {
            ErpTransEqunrItem erpItem = new ErpTransEqunrItem();
            DeviceStorageSyncErpDTO fill = baseMapper.findById2Erp(storage.getUuid());
            erpItem.setXtbm(storage.getUuid());
            erpItem.setXtbmNo(storage.getDeviceCode());
            erpItem.setSwid("");
            erpItem.setEqktx(storage.getFullName());
            erpItem.setZsb001(storage.getUseKeepDept());
            erpItem.setZsb002(storage.getRealManageDept());
            // 2024-4-28 当前登陆人
            IdevelopUser user = SecureUtil.getUser();
            erpItem.setZsb010(user.getUserName());
            // 电压等级
            Object powerLevel = powerLevelMapErp.get(storage.getVoltageLevelCode());
            erpItem.setZsb004(String.valueOf(powerLevel));
            erpItem.setStat("E0002");
            // 设备增加方式
            Object deviceChangeType = deviceAddMapErp.get(storage.getDeviceAddTypeCode());
            erpItem.setZsb005(String.valueOf(deviceChangeType));
            erpItem.setStort("");
            erpItem.setEqart("");
            // 设备类型
            Object deviceType = deviceTypeMapErp.get(storage.getDeviceTypeCode());
            erpItem.setSbfl(String.valueOf(deviceType));
            // 制造商
            erpItem.setHerst(storage.getMaker());
            // 制造国家
//			Object country = countryAreaMapErp.get(storage.getMaintenanceCountry());
            erpItem.setHerld("CN");
            erpItem.setPosid(storage.getWbsElement());
            erpItem.setZsb006(storage.getFunLocation());
            erpItem.setTplnr(storage.getFunLocationCode());
            erpItem.setZcabn_ztpm1005(1);
            // 计量单位
            Object unit = unifiedCodeMapErp.get(fill.getUnit());
            erpItem.setZcabn_ztpm1006(String.valueOf(unit));
            // 工厂区域
            String factoryAreaCodeId = storage.getFactoryAreaCode();
            Object factoryAreaCode = factoryAreaMapErp.get(factoryAreaCodeId);
            erpItem.setBeber(String.valueOf(factoryAreaCode));
            erpItem.setInbdt(new SimpleDateFormat("yyyyMMdd").format(fill.getOprtDate()));
            erpItem.setTypbz(storage.getDeviceModel());
            erpItem.setSerge(fill.getNameplateNo());
            erpItem.setBaujj(storage.getFactoryDate().substring(0, 4));
            erpItem.setBaumm(storage.getFactoryDate().substring(5, 7));
            erpItem.setSwerk(storage.getMaintenanceFactoryCode());
            erpItem.setAnlnr(storage.getAssetCodeErp());
            erpItem.setEqunr(storage.getDeviceCodeErp());
            erpItem.setTbbs("");
            erpItem.setZsb011("00000000000000000");
            erpList.add(erpItem);
        }
        erp.setErpTransEqunrItemList(erpList);
        log.info("推送erp数据:{}", erpList);
        ErpTransEqunrResp erpTransEqunrResp = erpService.transEqunr(erp);
        log.info("erp返回数据:{}", erpTransEqunrResp);
        return erpTransEqunrResp;
    }


    /**
     * 更新erp、i6000同步状态
     *
     * @param dto
     * @param result
     * @param i6000Status
     */
    private void syncStorageList(DeviceStorageDTO dto, ErpTransEqunrResp result, Integer i6000Status) {
        List<ProjectManagerDetail> detailList = new ArrayList<>();
        List<DeviceStorageList> devices;
        String deviceType = dto.getDeviceType();
        Boolean isErpTransfer = Boolean.FALSE;
        if (ObjectUtil.isNotEmpty(result) && StringUtil.isNotBlank(result.getCode())) {
            String code = result.getCode();
            isErpTransfer = Boolean.TRUE;
            if ("E".equalsIgnoreCase(code)) {
                // 全部失败  同步i6000 1成功2失败
                devices = dto.getDevices();
                for (DeviceStorageList device : devices) {
                    // 用于记录erp的生成情况
                    int erpStatus = 3;
                    ProjectManagerDetail projectManagerDetail = couverManagerDetail(dto, device, erpStatus, i6000Status, isErpTransfer);
                    detailList.add(projectManagerDetail);

                    // 更新入库明细表
                    device.setSyncErpStatus("3");
                    device.setSyncI6000Status(String.valueOf(i6000Status));
                }
            } else {
                List<ErpTransEqunrResp.ItemResp> itemResp = result.getItemResp();
                Map<String, ErpTransEqunrResp.ItemResp> itemRespMap = itemResp.stream().collect(Collectors.toMap(item -> item.getEqunr(), item2 -> item2));
                devices = dto.getDevices();
                for (DeviceStorageList device : devices) {
                    ErpTransEqunrResp.ItemResp item = itemRespMap.get(device.getErpAccountCode());
                    // 用于记录erp的生成情况
                    int erpStatus = StringUtils.equals("S", item.getTbbs()) ? 2 : 3;
                    ProjectManagerDetail projectManagerDetail = couverManagerDetail(dto, device, erpStatus, i6000Status, isErpTransfer);
                    detailList.add(projectManagerDetail);

                    // 2成功3失败
                    device.setSyncErpStatus("S".equalsIgnoreCase(item.getTbbs()) ? "2" : "3");
                    device.setSyncI6000Status(String.valueOf(i6000Status));
                }
            }
        } else {
            // erp同步未开启
            devices = dto.getDevices();
            for (DeviceStorageList device : devices) {
                // 用于记录erp的生成情况
                // ProjectManagerDetail projectManagerDetail = couverManagerDetail(dto, device, 0, i6000Status, isErpTransfer);
                // detailList.add(projectManagerDetail);
                device.setSyncI6000Status(String.valueOf(i6000Status));
            }
        }
        if (CollectionUtil.isNotEmpty(devices)) {
            deviceStorageListService.updateBatchById(devices);
        }
        if (CollectionUtils.isNotEmpty(detailList)) {
            projectManagerDetailService.saveOrUpdateBatch(detailList);
        }
    }


    /**
     * 用于记录erp的生成情况
     *
     * @param device
     * @param erpStatus
     * @param i6000Status
     * @return
     */
    private ProjectManagerDetail couverManagerDetail(DeviceStorageDTO dto, DeviceStorageList device, int erpStatus, Integer i6000Status, Boolean isErpTransfer) {
        ProjectManagerDetail detail = ProjectManagerDetail.builder().uuid(device.getUuid())
                .deviceType(dto.getDeviceType())
                .deviceCode(device.getDeviceCode())
                .deviceName(device.getDeviceName())
                .wbsCode(dto.getWbsElement())
                .wbsName(dto.getWbsProject())
                .erpAssetStatus(1)
                .erpTransferStatus(isErpTransfer ? ciEntityProperties.getErpTransferStatus1() : ciEntityProperties.getErpTransferStatus2())
                .erpStatus(erpStatus)
                .stage(WorkOrderTypeEnum.RK.getText())
                .i6000Status(i6000Status == 1 ? 2 : 3).build();
        return detail;
    }

    private void fillDeviceData(DeviceStorageSaveCmdbDTO deviceStorage, Map<Object, Object> erp, Map<String, String> i6000) {
        // 设备状态 deviceStatusCode
        deviceStorage.setDeviceStatusCode(ciEntityProperties.getCientityId(CmdbCientityConstant.DEVICE_STATUS_0));
        deviceStorage.setDeviceStatus("库存备用");
        // ERP转资状态
        if (getDeviceTypeList().contains(deviceStorage.getDeviceType())) {
            deviceStorage.setErpTransferStatus(ciEntityProperties.getCientityId(CmdbCientityConstant.ERP_STATUS_1));
        } else {
            deviceStorage.setErpTransferStatus(ciEntityProperties.getCientityId(CmdbCientityConstant.ERP_STATUS_2));
        }
        // 是否同步给ERP
        if ((ciEntityProperties.getCientityId(CmdbCientityConstant.T105).equals(deviceStorage.getDeviceCategory())
                || ciEntityProperties.getCientityId(CmdbCientityConstant.T107).equals(deviceStorage.getDeviceCategory()))
                && erp.containsKey(deviceStorage.getDeviceType())) {
            deviceStorage.setIsToErpCode(ciEntityProperties.getCientityId(CmdbCientityConstant.YES));
        } else {
            deviceStorage.setIsToI6000(ciEntityProperties.getCientityId(CmdbCientityConstant.NO_NO));
        }
        // 标准全称赋值给设备名称
        deviceStorage.setDeviceName(deviceStorage.getFullName());
        // 是否同步给I6000
        if (i6000.containsKey(deviceStorage.getDeviceType())) {
            deviceStorage.setIsToI6000(ciEntityProperties.getCientityId(CmdbCientityConstant.YES));
        } else {
            deviceStorage.setIsToI6000(ciEntityProperties.getCientityId(CmdbCientityConstant.NO_NO));
        }
        // 是否治理
        deviceStorage.setIsGovern(ciEntityProperties.getCientityId(CmdbCientityConstant.GOVERN_YES));

        // 制造商
        if (StringUtil.isNotBlank(deviceStorage.getMaker()) && StringUtil.isBlank(deviceStorage.getMakerCode())) {
            String key = getDictKey(cmdbDictProperties.getMaker(), deviceStorage.getMaker());
            deviceStorage.setMakerCode(key);
        }
        // 品牌
        if (StringUtil.isNotBlank(deviceStorage.getBrand()) && StringUtil.isBlank(deviceStorage.getBrandCode())) {
            String key = getDictKey(cmdbDictProperties.getBrand(), deviceStorage.getBrand());
            deviceStorage.setBrandCode(key);
        }
        // 系列
        if (StringUtil.isNotBlank(deviceStorage.getSeries()) && StringUtil.isBlank(deviceStorage.getSeriesCode())) {
            String dictKey = getDictKey(cmdbDictProperties.getSeries(), deviceStorage.getSeries());
            deviceStorage.setSeriesCode(dictKey);
        }
        // 型号
        if (StringUtil.isNotBlank(deviceStorage.getDeviceModel()) && StringUtil.isBlank(deviceStorage.getDeviceModelCode())) {
            String key = getDictKey(cmdbDictProperties.getModel(), deviceStorage.getDeviceModel());
            deviceStorage.setDeviceModelCode(key);
        }
        // 设备分类 deviceCategoryCode
        if (StringUtil.isNotBlank(deviceStorage.getDeviceCategory())) {
            deviceStorage.setDeviceCategoryCode(deviceStorage.getDeviceCategory());
            String value = getDictValue(cmdbDictProperties.getDeviceClaccify(), deviceStorage.getDeviceCategory());
            deviceStorage.setDeviceCategory(value);
        }
        // 设备类型 deviceTypeCode
        if (StringUtil.isNotBlank(deviceStorage.getDeviceType())) {
            deviceStorage.setDeviceTypeCode(deviceStorage.getDeviceType());
            String value = getDictValue(cmdbDictProperties.getDeviceType(), deviceStorage.getDeviceType());
            deviceStorage.setDeviceType(value);
        }
        // 设备来源 deviceSourceCode
        if (StringUtil.isNotBlank(deviceStorage.getDeviceSource())) {
            deviceStorage.setDeviceSourceCode(deviceStorage.getDeviceSource());
            String value = getDictValue(cmdbDictProperties.getDeviceSource(), deviceStorage.getDeviceSource());
            deviceStorage.setDeviceSource(value);
        }
        // 电压等级
        if (StringUtil.isNotBlank(deviceStorage.getVoltageLevel())) {
            deviceStorage.setVoltageLevelCode(deviceStorage.getVoltageLevel());
            String value = getDictValue(cmdbDictProperties.getPowerLevel(), deviceStorage.getVoltageLevel());
            deviceStorage.setVoltageLevel(value);
        }
        // 实物管理部门，sql查询，无需填充 realManageDept entityManagementDeptName
        // 使用保管部门，sql查询，无需填充 useKeepDept useKeepDeptName
        // 采购方式 procureTypeCode
        if (StringUtil.isNotBlank(deviceStorage.getProcureType())) {
            String key = getDictKey(cmdbDictProperties.getProcureTypeCode(), deviceStorage.getProcureType());
            deviceStorage.setProcureTypeCode(key);
        }
        // 操作系统版本号
//		if (StringUtil.isNotBlank(deviceStorage.getOSVersion())) {
//			String key = getDictKey(cmdbDictProperties.getOSVersion(), deviceStorage.getOSVersion());
//			deviceStorage.setOSVersionCode(key);
//		}
        // 操作系统类型 OSTypeCode
        if (StringUtil.isNotBlank(deviceStorage.getOSType())) {
            String key = getDictKey(cmdbDictProperties.getOSTypeCode(), deviceStorage.getOSType());
            deviceStorage.setOSTypeCode(key);
        }
        // CPU架构 cpuArchCode
        if (StringUtil.isNotBlank(deviceStorage.getCpuArch())) {
            String key = getDictKey(cmdbDictProperties.getCpuArchCode(), deviceStorage.getCpuArch());
            deviceStorage.setCpuArchCode(key);
        }
        // CPU品牌
        if (StringUtil.isNotBlank(deviceStorage.getCpuBrand())) {
            String key = getDictKey(cmdbDictProperties.getCpuBrand(), deviceStorage.getCpuBrand());
            deviceStorage.setCpuBrandCode(key);
        }
        // 硬盘类型 hardDiskTypeCode
        if (StringUtil.isNotBlank(deviceStorage.getHardDiskType())) {
            String key = getDictKey(cmdbDictProperties.getHardDiskTypeCode(), deviceStorage.getHardDiskType());
            deviceStorage.setHardDiskTypeCode(key);
        }
        // 备品备件类型
        if (StringUtil.isNotBlank(deviceStorage.getSparePartsType())) {
            String key = getDictKey(cmdbDictProperties.getSparePartsType(), deviceStorage.getSparePartsType());
            deviceStorage.setSparePartsTypeId(key);
        }
        // 设备增加方式
        if (StringUtil.isNotBlank(deviceStorage.getDeviceAddType())) {
            String key = getDictKey(cmdbDictProperties.getDeviceAdd(), deviceStorage.getDeviceAddType());
            deviceStorage.setDeviceAddTypeCode(key);
        }
        // 设备变动方式
        if (StringUtil.isNotBlank(deviceStorage.getDeviceChangeType())) {
            deviceStorage.setDeviceChangeTypeCode(deviceStorage.getDeviceChangeType());
            String value = getDictValue(cmdbDictProperties.getDeviceChangeType(), deviceStorage.getDeviceChangeType());
            deviceStorage.setDeviceChangeType(value);
        }
        // 网口类型 netPortType
        if (StringUtil.isNotBlank(deviceStorage.getNetPortType())) {
            String key = getDictKey(cmdbDictProperties.getNetWorkCode(), deviceStorage.getNetPortType());
            deviceStorage.setNetPortType(key);
        }
        // 存储RAID冗余方式 raidStorageType
        if (StringUtil.isNotBlank(deviceStorage.getRaidStorageType())) {
            String key = getDictKey(cmdbDictProperties.getRaidStorageType(), deviceStorage.getRaidStorageType());
            deviceStorage.setRaidStorageType(key);
        }
        // 是否纳入云管 isCloudMange
        if (StringUtil.isNotBlank(deviceStorage.getIsCloudMange())) {
            String key = getDictKey(cmdbDictProperties.getYesNo(), deviceStorage.getIsCloudMange());
            deviceStorage.setIsCloudMange(key);
        }
        // 是否信创设备 isITAICode
        if (StringUtil.isNotBlank(deviceStorage.getIsITAI())) {
            String key = getDictKey(cmdbDictProperties.getYesNo(), deviceStorage.getIsITAI());
            deviceStorage.setIsITAICode(key);
        }
        // 产权状态 ownerStatus 1131249961074688 暂无
        // 是否可报废 isScrapCode 1104198218612736L 暂无
        // 是否可用 isUse
        if (StringUtil.isNotBlank(deviceStorage.getIsUse())) {
            String key = getDictKey(cmdbDictProperties.getYesNo(), deviceStorage.getIsUse());
            deviceStorage.setIsUse(key);
        }
        // 云类型 cloudType 1131080184037376L 暂无
        // 主机设备用途类型 serverUseToType ??? 暂无
        // 网络设备用途类型 networkDeviceType
        if (StringUtil.isNotBlank(deviceStorage.getNetworkDeviceType())) {
            String key = getDictKey(cmdbDictProperties.getNetworkDeviceType(), deviceStorage.getNetworkDeviceType());
            deviceStorage.setNetworkDeviceType(key);
        }
        // 是否外单位设备 isExternalUnitCode
        if (StringUtil.isNotBlank(deviceStorage.getIsExternalUnit())) {
            String key = getDictKey(cmdbDictProperties.getYesNo(), deviceStorage.getIsExternalUnit());
            deviceStorage.setIsExternalUnitCode(key);
        }
        // 空调类型   1131064128241664L 暂无
        // 功能描述   1131120852008960L 暂无
        // 服务级别 serviceLevel
        if (StringUtil.isNotBlank(deviceStorage.getServiceLevel())) {
            String key = getDictKey(cmdbDictProperties.getServiceLevel(), deviceStorage.getServiceLevel());
            deviceStorage.setServiceLevelCode(key);
        }
        // 产权单位
        if (StringUtil.isNotBlank(deviceStorage.getOwnerUnitName())) {
            deviceStorage.setOwnerUnit(deviceStorage.getOwnerUnitName());
        }
        // 产权部门
        if (StringUtil.isNotBlank(deviceStorage.getPropertyDeptName())) {
            deviceStorage.setPropertyDept(deviceStorage.getPropertyDeptName());
        }
        // cpu主频 模型中有两个cpu主频字段，cpuClockSpeed，cpuFrequecy，全量保存
        if (StringUtil.isNotBlank(deviceStorage.getCpuFrequecy())) {
            deviceStorage.setCpuClockSpeed(deviceStorage.getCpuFrequecy());
        }
    }

    private List<String> getDeviceTypeList() {
        return Arrays.asList(ciEntityProperties.getCientityId(CmdbCientityConstant.T10501),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10502),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10503),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10504),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10505),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10701),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10702),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10703),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10704),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10705),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10706),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10707),
                ciEntityProperties.getCientityId(CmdbCientityConstant.T10708));
    }

    private String getDictValue(Long ciId, String key) {
        String redisKey = CacheNames.CMDB_DICT_STORAGE + ciId;
        Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
        if (CollectionUtil.isEmpty(cache)) {
            R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
            cache = CmdbDictUtil.getValue(dict);
            redisUtil.set(redisKey, cache);
            if (CollectionUtil.isEmpty(cache)) {
                return null;
            }
        }
        return cache.get(key);
    }

    private String getDictKey(Long ciId, String value) {
        String redisKey = CacheNames.CMDB_DICT_STORAGE + ciId;
        Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
        if (CollectionUtil.isEmpty(cache)) {
            R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
            cache = CmdbDictUtil.getCache(dict);
            redisUtil.set(redisKey, cache);
            // 转换
            cache = CmdbDictUtil.getKey(cache);
            if (CollectionUtil.isEmpty(cache)) {
                return null;
            }
        } else {
            cache = CmdbDictUtil.getKey(cache);
//			String key = cache.get(value);
//			if (StringUtil.isBlank(key)) {
//				R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
//				cache = CmdbDictUtil.getCache(dict);
//				redisUtil.set(redisKey, cache);
//				cache = CmdbDictUtil.getKey(cache);
//			}
        }
        return cache.get(value);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceStorage tempSave(DeviceStorageDTO entity) {
        entity.setIsTemp(1);
        entity.setStatus(0);
        IdevelopUser user = SecureUtil.getUser();
        if (StringUtil.isBlank(entity.getId()) && StringUtil.isBlank(entity.getSerialNumber())) {
            fillData(entity, user);
            this.save(entity);
            // 第一次保存增加日志
            insertLog(entity, CommonConstant.STORAGE_ADD);
        } else {
            this.updateById(entity);
        }
        // 保存子表
        saveDeviceList(entity);
        return entity;
    }

    private void saveDeviceList(DeviceStorageDTO entity) {
        List<DeviceStorageList> devices = entity.getDevices();
        if (CollectionUtil.isEmpty(devices)) {
            return;
        }
        devices.stream().forEach(device -> device.setStorageId(entity.getId()));
        deviceStorageListService.customSaveBatch(devices);
    }

    private void fillData(DeviceStorageDTO deviceStorage, IdevelopUser user) {
        if (user != null) {
            Map<String, Object> ext = user.getExt();
            deviceStorage.setCreateUser(user.getUserId());
            deviceStorage.setCreateDept(user.getDeptId());
            deviceStorage.setRegionCode(user.getRegionCode());
            deviceStorage.setDeptCode(user.getDeptId());
            deviceStorage.setDeptName(user.getDeptName());
            deviceStorage.setOwnerUnit(user.getCorpId());
            deviceStorage.setOwnerUnitName(String.valueOf(ext.get("corpFullName")));
            deviceStorage.setPropertyDept(user.getDeptId());
            deviceStorage.setPropertyDeptName(user.getDeptName());
            deviceStorage.setOperationUnit(user.getCorpName());
            deviceStorage.setOperationUnitCode(user.getCorpId());
        }
        deviceStorage.setCreateTime(new Date());
        deviceStorage.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        deviceStorage.setSerialNumber(orderNumberUtil.generateNumber(WorkOrderTypeEnum.RK));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        for (Long id : ids) {
            deviceStorageListService.deleteByStorageId(String.valueOf(id));
            deviceAttachService.deleteByStorageId(String.valueOf(id));
        }
        return this.deleteLogic(ids);
    }

    @Override
    public void export(DeviceStorageExportSO so, HttpServletResponse response) {
        List<DeviceStorageExportVO> dataList;
        if (StringUtil.isNotBlank(so.getIds())) {
            dataList = baseMapper.selectByExportIds(Func.toLongList(so.getIds()));
        } else {
            IdevelopUser user = SecureUtil.getUser();
            so.setRegionCode(user.getRegionCode());
            dataList = baseMapper.selectByExportParam(so);
        }
        dataList.stream().forEach(entity -> fillData(entity));

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String fileName = URLEncoder.encode("入库管理导出", StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), DeviceStorageExportVO.class).sheet("入库管理表").doWrite(dataList);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private void fillData(DeviceStorageExportVO entity) {
        // 设备来源、设备分类、设备类型、电压等级、是否同步i6000、是否暂存、设备状态
        entity.setDeviceStatus("库存备用");
        entity.setIsTemp(STATUS.equals(entity.getIsTemp()) ? "否" : "是");
        // 设备分类
        if (StringUtil.isNotBlank(entity.getDeviceCategory())) {
            String value = getDictValue(cmdbDictProperties.getDeviceClaccify(), entity.getDeviceCategory());
            entity.setDeviceCategory(value);
        }
        // 设备类型
        if (StringUtil.isNotBlank(entity.getDeviceType())) {
            String value = getDictValue(cmdbDictProperties.getDeviceType(), entity.getDeviceType());
            entity.setDeviceType(value);
        }
        // 设备来源
        if (StringUtil.isNotBlank(entity.getDeviceSource())) {
            String value = getDictValue(cmdbDictProperties.getDeviceSource(), entity.getDeviceSource());
            entity.setDeviceSource(value);
        }
        // 电压等级
        if (StringUtil.isNotBlank(entity.getVoltageLevel())) {
            String value = getDictValue(cmdbDictProperties.getPowerLevel(), entity.getVoltageLevel());
            entity.setVoltageLevel(value);
        }

        String deviceInfo = entity.getDeviceHardwareInfo();
        DeviceStorageExportDynamicVO dynamicVO = JSON.parseObject(deviceInfo, DeviceStorageExportDynamicVO.class);
        if (dynamicVO != null) {
            BeanUtils.copyProperties(dynamicVO, entity);
        }
    }

    @Override
    public IPage<DeviceStorage> selectDeviceStorage(DeviceStorageSO so, Query query) {
        IdevelopUser user = SecureUtil.getUser();
        so.setRegionCode(user.getRegionCode());
        return baseMapper.customSelectPage(Condition.getPage(query), so);
    }

    @Override
    public boolean refreshCache(List<Long> ciIds) {
        try {
            if (CollectionUtil.isEmpty(ciIds)) {
                ciIds = getCiIds();
            }
            for (Long ciId : ciIds) {
                String redisKey = CacheNames.CMDB_DICT_STORAGE + ciId;
                R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
                Map<String, String> cache = CmdbDictUtil.getValue(dict);
                redisUtil.set(redisKey, cache);
            }

        } catch (Exception e) {
            log.info("手动刷新字典失败,失败原因:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public String getFullName(GetFullNameDTO dto) {
        return orderNumberUtil.getDeviceFullName(dto.getType(), "", dto.getProjectName(), dto.getDeviceType());
    }


    private List<Long> getCiIds() {
        List<Long> ciIds = new ArrayList<>();
        ciIds.add(cmdbDictProperties.getMaker());
        ciIds.add(cmdbDictProperties.getBrand());
        ciIds.add(cmdbDictProperties.getSeries());
        ciIds.add(cmdbDictProperties.getModel());
        ciIds.add(cmdbDictProperties.getYesNo());
        ciIds.add(cmdbDictProperties.getCpuBrand());
        ciIds.add(cmdbDictProperties.getCpuArchCode());
        ciIds.add(cmdbDictProperties.getHardDiskTypeCode());
        ciIds.add(cmdbDictProperties.getRaidStorageType());
        ciIds.add(cmdbDictProperties.getNetPortType());
        ciIds.add(cmdbDictProperties.getProcureTypeCode());
        ciIds.add(cmdbDictProperties.getNetworkDeviceType());
        ciIds.add(cmdbDictProperties.getOSTypeCode());
        ciIds.add(cmdbDictProperties.getSparePartsType());
        ciIds.add(cmdbDictProperties.getServiceLevel());
        return ciIds;
    }

    private void insertLog(DeviceStorage deviceStorage, String title) {
        LogOpt logOpt = new LogOpt();
        logOpt.setLogId(deviceStorage.getSerialNumber());
        logOpt.setOptType(WorkOrderTypeEnum.RK.getText());
        logOpt.setTitle(title);
        logOptService.commonLogOpt(logOpt);
    }

}

package com.lnsoft.device.api.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.tool.CommonUtil;
import com.lnsoft.common.utils.IdevelopUtils;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransEqunrItem;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.service.IErpMaintainService;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.erp.service.IErpSyncService;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import com.lnsoft.device.api.i6000.dto.I6000CiCientityDTO;
import com.lnsoft.device.api.i6000.dto.I6000EntityIdDTO;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.stock.entity.I6000ImportLog;
import com.lnsoft.device.api.stock.service.II6000ImportLogService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.eums.ErpOperationEnum;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author: xuel
 * @CreateTime: 2025/7/11 9:07
 * @Description: I6000ErpServiceImpl
 */
@Service
@AllArgsConstructor
public class ErpSyncServiceImpl implements IErpSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErpSyncServiceImpl.class);
    private IErpService iErpService;
    private OrderNumberUtil orderNumberUtil;
    private CmdbDictProperties cmdbDictProperties;
    private II6000ImportLogService i6000ImportLogService;
    private ICmdbService cmdbService;
    private IZfitXtCwztService zfitXtCwztService;
    private IProjectManagerDetailService projectManagerDetailService;
    private CmdbCientityProperties cmdbCientityProperties;
    private IErpMaintainService erpMaintainService;
    private II6000Service i6000Service;


    /**
     * 设备台账主数据同步接口(手动维护) xtyth -> erp
     *
     * @param map
     * @return
     */
    @Override
    public ErpTransEqunrResp manualTransEqunr(Map<String, Object> map, ZfitXtCwzt zfitXtCwzt) {
        LOGGER.info("进入方法: 设备台账主数据同步接口(手动维护ERP): {}", map);

        try {
            Map<Object, Object> deviceChangeTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceChangeType());
            Map<Object, Object> deviceAddMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceAdd());
            Map<Object, Object> deviceTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
            Map<Object, Object> unifiedCodeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getUnifiedCode());
            Map<Object, Object> factoryAreaMapErp = cmdbDictProperties.getFactoryAreaErpMapByCiId(cmdbDictProperties.getFactoryAreaCode());
            Map<Object, Object> statusErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceStatus());

            ErpTransEqunr erpTransEqunr = new ErpTransEqunr();

            erpTransEqunr.setXtdocId(String.valueOf(System.currentTimeMillis()));
            erpTransEqunr.setXtdocNo(orderNumberUtil.generateNumber(WorkOrderTypeEnum.BG));
            erpTransEqunr.setOperationType(ErpOperationEnum.M);
            List<ErpTransEqunrItem> erpTransEqunrItemList = new ArrayList<>();
            ErpTransEqunrItem erpTransEqunrItem = new ErpTransEqunrItem();
            LOGGER.info("UUID, {}", map.get(CmdbAttrConstant.UUID));
            erpTransEqunrItem.setXtbm(map.get(CmdbAttrConstant.UUID).toString());
            LOGGER.info("DEVICE_CODE, {}", map.get(CmdbAttrConstant.DEVICE_CODE));
            erpTransEqunrItem.setXtbmNo(map.get(CmdbAttrConstant.DEVICE_CODE).toString());
            erpTransEqunrItem.setSwid("");
            LOGGER.info("FULL_NAME, {}", map.get(CmdbAttrConstant.FULL_NAME));
            erpTransEqunrItem.setEqktx(map.get(CmdbAttrConstant.FULL_NAME).toString());
            LOGGER.info("ASSET_CODE_ERP, {}", map.get(CmdbAttrConstant.ASSET_CODE_ERP));
            erpTransEqunrItem.setAnlnr(map.get(CmdbAttrConstant.ASSET_CODE_ERP).toString());
            LOGGER.info("DEVICE_CODE_ERP, {}", map.get(CmdbAttrConstant.DEVICE_CODE_ERP));
            erpTransEqunrItem.setEqunr(map.get(CmdbAttrConstant.DEVICE_CODE_ERP).toString());
            // 使用保管部门
            Object useKeepDept = map.get(CmdbAttrConstant.USE_KEEP_DEPT);
            LOGGER.info("USE_KEEP_DEPT, {}", useKeepDept);
            erpTransEqunrItem.setZsb001(IdevelopUtils.isCmdbNotBlack(useKeepDept) ? useKeepDept.toString() : zfitXtCwzt.getKostl());
            // 实物管理部门
            Object realManageDept = map.get(CmdbAttrConstant.REAL_MANAGE_DEPT);
            LOGGER.info("REAL_MANAGE_DEPT, {}", realManageDept);
            erpTransEqunrItem.setZsb002(IdevelopUtils.isCmdbNotBlack(realManageDept) ? realManageDept.toString() : zfitXtCwzt.getZsb002());
            // 使用保管人
            Object receivingPerson = map.get(CmdbAttrConstant.RECEIVING_PERSON);
            LOGGER.info("RECEIVING_PERSON, {}", receivingPerson);
            erpTransEqunrItem.setZsb010(IdevelopUtils.isCmdbNotBlack(receivingPerson) ? receivingPerson.toString() : zfitXtCwzt.getZsb010());
            erpTransEqunrItem.setZsb004(Constants.ERP_ZSB004);
            // 设备状态
            Object deviceStatusCode = map.get(CmdbAttrConstant.DEVICE_STATUS_CODE);
            LOGGER.info("DEVICE_STATUS_CODE, {}", deviceStatusCode);
            erpTransEqunrItem.setStat(statusErp.get(deviceStatusCode.toString()).toString());

            // 设备变动方式编码
            Object deviceChangeTypeCode = map.get(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE);
            LOGGER.info("DEVICE_CHANGE_TYPE_CODE, {}", deviceChangeTypeCode);
            erpTransEqunrItem.setStort(IdevelopUtils.isCmdbNotBlack(deviceChangeTypeCode) ? deviceChangeTypeMapErp.get(deviceChangeTypeCode.toString()).toString() : "");

            // 设备增加方式
            Object deviceAddTypeCode = map.get(CmdbAttrConstant.DEVICE_ADD_TYPE_CODE);
            LOGGER.info("DEVICE_ADD_TYPE_CODE, {}", deviceAddTypeCode);
            erpTransEqunrItem.setZsb005(IdevelopUtils.isCmdbNotBlack(deviceAddTypeCode) ? deviceAddMapErp.get(deviceAddTypeCode.toString()).toString() : "");
            erpTransEqunrItem.setEqart("");

            // 设备类型
            try {
                Object deviceTypeCode = map.get(CmdbAttrConstant.DEVICE_TYPE_CODE);
                LOGGER.info("DEVICE_TYPE_CODE, {}", deviceTypeCode);
                erpTransEqunrItem.setSbfl(deviceTypeMapErp.get(deviceTypeCode.toString()).toString());
            } catch (Exception e) {
                CommonUtil.StringWriter(e, "信通一体化平台同步ERP系统异常(ERP转换设备类型异常)");
                throw new RuntimeException("信通一体化平台同步ERP转换设备类型异常: " + e);
            }


            // 制造商
            Object maker = map.get(CmdbAttrConstant.MAKER);
            LOGGER.info("MAKER, {}", maker);
            erpTransEqunrItem.setHerst(IdevelopUtils.isCmdbNotBlack(maker) ? maker.toString() : zfitXtCwzt.getHerst());
            erpTransEqunrItem.setHerld("CN");

            // WBS元素
            Object wbsElement = map.get(CmdbAttrConstant.WBS_ELEMENT);
            LOGGER.info("WBS_ELEMENT, {}", wbsElement);
            erpTransEqunrItem.setPosid(IdevelopUtils.isCmdbNotBlack(wbsElement) ? wbsElement.toString() : zfitXtCwzt.getPosid());

            // 功能位置
            Object funLocation = map.get(CmdbAttrConstant.FUN_LOCATION);
            LOGGER.info("FUN_LOCATION, {}", funLocation);
            erpTransEqunrItem.setZsb006(IdevelopUtils.isCmdbNotBlack(funLocation) ? funLocation.toString() : zfitXtCwzt.getTplnrT());

            // 功能位置编码
            Object funLocationCode = map.get(CmdbAttrConstant.FUN_LOCATION_CODE);
            LOGGER.info("FUN_LOCATION_CODE, {}", funLocationCode);
            erpTransEqunrItem.setTplnr(IdevelopUtils.isCmdbNotBlack(funLocationCode) ? funLocationCode.toString() : zfitXtCwzt.getTplnr());
            erpTransEqunrItem.setZcabn_ztpm1005(1);

            // 计量单位
            Object measureUnit = map.get(CmdbAttrConstant.MEASURE_UNIT);
            LOGGER.info("MEASURE_UNIT, {}", measureUnit);
            erpTransEqunrItem.setZcabn_ztpm1006(IdevelopUtils.isCmdbNotBlack(measureUnit) ? unifiedCodeMapErp.get(measureUnit.toString()).toString() : zfitXtCwzt.getZcabnZtpm1006());

            // 工厂区域编码
            Object factoryAreaCode = map.get(CmdbAttrConstant.FACTORY_AREA_CODE);
            LOGGER.info("FACTORY_AREA_CODE, {}", factoryAreaCode);
            erpTransEqunrItem.setBeber(IdevelopUtils.isCmdbNotBlack(factoryAreaCode) ? factoryAreaMapErp.get(factoryAreaCode.toString()).toString() : zfitXtCwzt.getBeber());

            // 投运日期
            Object oprtDate = map.get(CmdbAttrConstant.OPRT_DATE);
            LOGGER.info("OPRT_DATE, {}", oprtDate);
            if (IdevelopUtils.isCmdbNotBlack(oprtDate)) {
                String oprtDateStr = (String) oprtDate;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(oprtDateStr);
                    String format = new SimpleDateFormat("yyyyMMdd").format(date);
                    erpTransEqunrItem.setInbdt(format);
                } catch (ParseException e) {
                    throw new RuntimeException("投运日期转换异常: " + oprtDate);
                }
            } else {
                Date inbdt = zfitXtCwzt.getInbdt();
                if (IdevelopUtils.isCmdbNotBlack(inbdt)) {
                    try {
                        String format = new SimpleDateFormat("yyyyMMdd").format(inbdt);
                        erpTransEqunrItem.setInbdt(format);
                    } catch (Exception e) {
                        throw new RuntimeException("erp投运日期转换异常: " + oprtDate);
                    }
                } else {
                    erpTransEqunrItem.setInbdt("");
                }
            }
            // 型号
            Object deviceModel = map.get(CmdbAttrConstant.DEVICE_MODEL);
            LOGGER.info("DEVICE_MODEL, {}", deviceModel);
            if (IdevelopUtils.isCmdbNotBlack(deviceModel)) {
                String deviceModelStr = deviceModel.toString();
                if (deviceModelStr.length() > 20) {
                    erpTransEqunrItem.setTypbz(deviceModelStr.substring(0, 20));
                } else {
                    erpTransEqunrItem.setTypbz(deviceModelStr);
                }
            } else {
                erpTransEqunrItem.setTypbz(zfitXtCwzt.getTypbz());
            }

            // 制造商设备铭牌号
            Object sn = map.get(CmdbAttrConstant.SN);
            LOGGER.info("SN, {}", sn);
            if (IdevelopUtils.isCmdbNotBlack(sn)) {
                String snStr = sn.toString();
                if (snStr.length() > 30) {
                    erpTransEqunrItem.setSerge(snStr.substring(0, 30));
                } else {
                    erpTransEqunrItem.setSerge(snStr);
                }
            } else {
                erpTransEqunrItem.setSerge(zfitXtCwzt.getSerge());
            }

            // 出厂日期
            Object factoryDate = map.get(CmdbAttrConstant.FACTORY_DATE);
            LOGGER.info("FACTORY_DATE, {}", factoryDate);
            if (IdevelopUtils.isCmdbNotBlack(factoryDate)) {
                String factoryDateStr = (String) factoryDate;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(factoryDateStr);
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    // 出厂年份
                    erpTransEqunrItem.setBaujj(format.substring(0, 4));
                    // 出厂月份
                    erpTransEqunrItem.setBaumm(format.substring(5, 7));
                } catch (ParseException e) {
                    throw new RuntimeException("出厂日期转换异常: " + factoryDate);
                }
            }

            // 维护工厂编码
            Object maintenanceFactoryCode = map.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE);
            LOGGER.info("MAINTENANCE_FACTORY_CODE, {}", maintenanceFactoryCode);
            erpTransEqunrItem.setSwerk(IdevelopUtils.isCmdbNotBlack(maintenanceFactoryCode) ? maintenanceFactoryCode.toString() : zfitXtCwzt.getSwerk());
            erpTransEqunrItem.setZsb011("00000000000000000");
            erpTransEqunrItem.setTbbs("");
            erpTransEqunrItemList.add(erpTransEqunrItem);
            erpTransEqunr.setErpTransEqunrItemList(erpTransEqunrItemList);

            return iErpService.transEqunr(erpTransEqunr);
        } catch (Exception e) {
            CommonUtil.StringWriter(e, "信通一体化平台同步ERP系统异常!");
            throw new RuntimeException(e);
        }

    }


    /**
     * 根据信通一体化设备编码, 同步Erp系统数据
     *
     * @param deviceCodeStencils
     * @return
     */
    @Override
    public String importSyncErpDetail(List<DeviceCodeStencil> deviceCodeStencils) {
        String result = UuidUtils.uuid();

        for (DeviceCodeStencil deviceCodeStencil : deviceCodeStencils) {

            I6000ImportLog i6000ImportLog = new I6000ImportLog();
            String deviceCode = deviceCodeStencil.getDeviceCode();

            syncErpDetail(deviceCode, i6000ImportLog, result);
            i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "更新ERP系统数据结束;");
            i6000ImportLogService.save(i6000ImportLog);
        }
        return result;
    }

    /**
     * 获取I6000台账 填充信通一体化实物ID
     *
     * @param i6000EntityIdDTO
     * @return
     */
    @Override
    public String getI6000EntityId(I6000EntityIdDTO i6000EntityIdDTO) {

        List<String> deviceList = i6000EntityIdDTO.getDeviceList();
        String area = i6000EntityIdDTO.getArea();

        CiCientitySearch cientitySearch = new CiCientitySearch();
        List<CiCientitySearchVO> entity = new ArrayList<>();
        CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder()
                .attrName(CmdbAttrConstant.AREA).expression(Expression.LIKE).attrValue(area).build();
        entity.add(ciCientitySearchVO);

        if (!CollectionUtils.isEmpty(deviceList)) {
            CiCientitySearchVO ciCientitySearchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE)
                    .expression(Expression.EQUAL)
                    .attrValue(i6000EntityIdDTO.getArea())
                    .isBatch(true)
                    .attrValue(StringUtil.join(deviceList.toArray(), "--"))
                    .build();
            entity.add(ciCientitySearchVO1);
        }
        cientitySearch.setEntity(entity);
        cientitySearch.setQuery(new Query().setCurrent(1).setSize(10));

        Map<String, String> sortConfigMap = new HashMap<>();
        sortConfigMap.put("attr_" + "1082375867269120", "ASC");
        cientitySearch.setSortConfig(sortConfigMap);

        FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
        Integer total = feignCiCientity.getTotal();

        LOGGER.info("查询共 {} 条", total);

        int requestSize = 200;
        int count = total / requestSize;
        if (total % requestSize != 0) {
            count++;
        }
        count = count + 1;
        LOGGER.info("需要循环 {} 次", count);

        for (int i = 1; i < count + 1; i++) {
            cientitySearch.setQuery(new Query().setCurrent(i).setSize(200));
            cientitySearch.setFullField(Boolean.TRUE);
            FeignCiCientity feignCiCientity1 = cmdbService.getCiCientityListByCondition(cientitySearch);
            List<Map<String, Object>> dataList = feignCiCientity1.getData();
            if (CollectionUtils.isEmpty(dataList)) {
                continue;
            }
            for (Map<String, Object> map : dataList) {
                try {

                    // ERP资产编码
                    Object assetCodeErpObj = map.get(CmdbAttrConstant.ASSET_CODE_ERP);
                    // ERP设备台账编码
                    Object deviceCodeErpObj = map.get(CmdbAttrConstant.DEVICE_CODE_ERP);
                    if (Objects.isNull(assetCodeErpObj) || "".equals(assetCodeErpObj) || Objects.isNull(deviceCodeErpObj) || "".equals(deviceCodeErpObj)) {
                        System.out.println("ERP资产编码为空, 或者ERP设备台账编码为空");
                        continue;
                    }

                    Object realID = map.get(CmdbAttrConstant.REAL_ID);
                    if (IdevelopUtils.isCmdbNotBlack(realID)) {
                        continue;
                    }

                    Object i6000CiId = map.get(CmdbAttrConstant.I6000_CI_ID);
                    if (IdevelopUtils.isCmdbNotBlack(i6000CiId)) {
                        I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
                        i6000CiCientityDTO.setAttrCode("CI_ID,NEW_ENTITY_ID");

                        List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();

                        // 资源ID
                        I6000CiCientityDTO.Conditions conditions = new I6000CiCientityDTO.Conditions();
                        conditions.setOperator("=");
                        conditions.setAttrCode(I6000AttrConstant.CI_ID);
                        conditions.setValue(i6000CiId.toString());
                        orConditionList.add(conditions);

                        // 删除标记
                        I6000CiCientityDTO.Conditions conditions4 = new I6000CiCientityDTO.Conditions();
                        conditions4.setOperator("=");
                        conditions4.setAttrCode(I6000AttrConstant.DELETED_FLAG);
                        conditions4.setValue("N");
                        orConditionList.add(conditions4);

                        i6000CiCientityDTO.setConditions(orConditionList);
                        i6000CiCientityDTO.setPageStart("1");
                        i6000CiCientityDTO.setPageSize("10");

                        List<Map<String, Object>> i6000ResultList = i6000Service.selectCiCientity("T1", i6000CiCientityDTO);
                        System.out.println("获取I6000台账 填充信通一体化实物IDList(1): " + JSONObject.toJSONString(i6000ResultList));

                        if (!CollectionUtils.isEmpty(i6000ResultList)) {
                            Map<String, Object> i6000Map = i6000ResultList.get(0);
                            Object newEntityId = i6000Map.get(I6000AttrConstant.NEW_ENTITY_ID);
                            if (Objects.isNull(newEntityId)) {
                                Object citype = i6000Map.get(I6000AttrConstant.CITYPE);
                                i6000ResultList = i6000Service.selectCiCientity(citype.toString(), i6000CiCientityDTO);
                                System.out.println("获取I6000台账 填充信通一体化实物IDList(1.1): " + JSONObject.toJSONString(i6000ResultList));
                                i6000Map = i6000ResultList.get(0);
                            }

                            map.put(CmdbAttrConstant.REAL_ID, i6000Map.get(I6000AttrConstant.NEW_ENTITY_ID));
                            map.put(CmdbAttrConstant.I6000_CI_ID, i6000Map.get(I6000AttrConstant.CI_ID));

                            Long id = (Long) map.get(CmdbAttrConstant.ID);
                            Map<Long, Map<String, Object>> map1 = new HashMap<>();
                            map1.put(id, map);

                            Map<String, Object> returnMap = cmdbService.cientityBatchupdate(map1, TransactionActionType.UPDATE);
                            Boolean committed = (Boolean) returnMap.get("committed");
                            if (Objects.nonNull(committed) && committed) {
                                System.out.println("实物ID台账更新完成!");
                            } else {
                                System.out.println("实物ID台账更新失败!");
                            }
                        } else {
                            System.out.println("未获取I6000台账信息");
                        }
                    } else {
                        // 维护工厂编码
                        Object factoryCode = map.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE);
                        if (Objects.isNull(factoryCode) || "".equals(factoryCode)) {
                            System.out.println("维护工厂编码为空;");
                        }
                        // 通过维护工厂编码查询 当前维护工厂所在市县的维护工厂数据.
                        List<String> swerkList = erpMaintainService.selectErpListBySwerk(String.valueOf(factoryCode));

                        String swerkStr = String.join(",", swerkList);

                        // ERP资产编码
                        String assetCodeErp = String.valueOf(assetCodeErpObj);
                        // ERP设备台账编码
                        String deviceCodeErp = String.valueOf(deviceCodeErpObj);

                        String deviceCodeErp1 = deviceCodeErp;
                        if (deviceCodeErp.startsWith("00")) {
                            // 不带00
                            deviceCodeErp1 = deviceCodeErp1.substring(2);
                        }

                        I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
                        i6000CiCientityDTO.setAttrCode("CI_ID,NEW_ENTITY_ID");

                        List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();
                        // ERP资产编码
                        I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
                        conditions1.setOperator("=");
                        conditions1.setAttrCode(I6000AttrConstant.ERP_ASSET_NO);
                        conditions1.setValue(assetCodeErp);
                        orConditionList.add(conditions1);

                        // ERP设备台账编码
                        I6000CiCientityDTO.Conditions conditions2 = new I6000CiCientityDTO.Conditions();
                        conditions2.setOperator("like");
                        conditions2.setAttrCode(I6000AttrConstant.ERP_LEDGER_NO);
                        conditions2.setValue(deviceCodeErp1);
                        orConditionList.add(conditions2);

                        I6000CiCientityDTO.Conditions conditions3 = new I6000CiCientityDTO.Conditions();
                        conditions3.setOperator("in");
                        conditions3.setAttrCode(I6000AttrConstant.OPDEP);
                        conditions3.setValue(swerkStr);
                        orConditionList.add(conditions3);

                        // 删除标记
                        I6000CiCientityDTO.Conditions conditions4 = new I6000CiCientityDTO.Conditions();
                        conditions4.setOperator("=");
                        conditions4.setAttrCode(I6000AttrConstant.DELETED_FLAG);
                        conditions4.setValue("N");
                        orConditionList.add(conditions4);

                        i6000CiCientityDTO.setConditions(orConditionList);
                        i6000CiCientityDTO.setPageStart("1");
                        i6000CiCientityDTO.setPageSize("10");

                        List<Map<String, Object>> i6000ResultList = i6000Service.selectCiCientity("T1", i6000CiCientityDTO);
                        System.out.println("获取I6000台账 填充信通一体化实物IDList(2): " + JSONObject.toJSONString(i6000ResultList));

                        if (!CollectionUtils.isEmpty(i6000ResultList)) {
                            Map<String, Object> i6000Map = i6000ResultList.get(0);
                            Object newEntityId = i6000Map.get(I6000AttrConstant.NEW_ENTITY_ID);
                            if (Objects.isNull(newEntityId)) {
                                Object citype = i6000Map.get(I6000AttrConstant.CITYPE);
                                i6000ResultList = i6000Service.selectCiCientity(citype.toString(), i6000CiCientityDTO);
                                System.out.println("获取I6000台账 填充信通一体化实物IDList(2.1): " + JSONObject.toJSONString(i6000ResultList));
                                i6000Map = i6000ResultList.get(0);
                            }

                            map.put(CmdbAttrConstant.REAL_ID, i6000Map.get(I6000AttrConstant.NEW_ENTITY_ID));
                            map.put(CmdbAttrConstant.I6000_CI_ID, i6000Map.get(I6000AttrConstant.CI_ID));
                            map.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                            map.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());

                            Long id = (Long) map.get(CmdbAttrConstant.ID);
                            Map<Long, Map<String, Object>> map1 = new HashMap<>();
                            map1.put(id, map);

                            Map<String, Object> returnMap = cmdbService.cientityBatchupdate(map1, TransactionActionType.UPDATE);
                            Boolean committed = (Boolean) returnMap.get("committed");
                            if (Objects.nonNull(committed) && committed) {
                                System.out.println("实物ID台账更新完成!");
                            } else {
                                System.out.println("实物ID台账更新失败!");
                            }
                        } else {
                            System.out.println("未获取I6000台账信息");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("未获取I6000台账信息");
                }

            }
        }
        return "完成更新";
    }

    /**
     * 同步Erp系统数据
     *
     * @param deviceCode
     * @param i6000ImportLog
     * @param result
     */
    private void syncErpDetail(String deviceCode, I6000ImportLog i6000ImportLog, String result) {
        StringBuilder sBuilder = new StringBuilder();
        i6000ImportLog.setImportUuid(result);
        i6000ImportLog.setDeviceCode(deviceCode);
        try {
            sBuilder.append("设备编码: ").append(deviceCode).append(";");

            CiCientitySearch cientitySearch = new CiCientitySearch();
            List<CiCientitySearchVO> entity = new ArrayList<>();
            CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).expression(Expression.EQUAL).attrValue(deviceCode).build();
            entity.add(ciCientitySearchVO);
            cientitySearch.setEntity(entity);
            cientitySearch.setQuery(new Query().setCurrent(1).setSize(10));
            cientitySearch.setFullField(Boolean.TRUE);

            Map<String, Object> entityMap = cmdbService.getCiCientityListByCondition(cientitySearch).getData().get(0);
            LOGGER.info("根据设备编码查询台账数据: {}", JSONObject.toJSONString(entityMap));

            // ERP资产编码
            Object assetCodeErpObj = entityMap.get(CmdbAttrConstant.ASSET_CODE_ERP);
            // ERP设备台账编码
            Object deviceCodeErpObj = entityMap.get(CmdbAttrConstant.DEVICE_CODE_ERP);
            if (Objects.isNull(assetCodeErpObj) || "".equals(assetCodeErpObj) || Objects.isNull(deviceCodeErpObj) || "".equals(deviceCodeErpObj)) {
                sBuilder.append("ERP资产编码为空, 或者ERP设备台账编码为空;");
                throw new RuntimeException(sBuilder.toString());
            }

            // ERP资产编码
            String assetCodeErp = String.valueOf(assetCodeErpObj);
            // ERP设备台账编码
            String deviceCodeErp = String.valueOf(deviceCodeErpObj);

            // 设备UUID
            String uuid = String.valueOf(entityMap.get(CmdbAttrConstant.UUID));
            // 标准全称
            String fullName = String.valueOf(entityMap.get(CmdbAttrConstant.FULL_NAME));
            // 设备类型code
            String deviceTypeCode = String.valueOf(entityMap.get(CmdbAttrConstant.DEVICE_TYPE_CODE));

            String deviceCodeErp1 = deviceCodeErp;
            if (deviceCodeErp.startsWith("00")) {
                // 不带00
                deviceCodeErp1 = deviceCodeErp1.substring(2);
            }

            ZfitXtCwzt zfitXtCwztQuery = new ZfitXtCwzt();
            QueryWrapper<ZfitXtCwzt> zfitXtCwztqueryWrapper = Condition.getQueryWrapper(zfitXtCwztQuery);
            zfitXtCwztqueryWrapper.lambda().like(ZfitXtCwzt::getEqunr, deviceCodeErp1);
            ZfitXtCwzt zfitXtCwzt = zfitXtCwztService.getOne(zfitXtCwztqueryWrapper);
            if (Objects.isNull(zfitXtCwzt)) {
                sBuilder.append("ERP设备台账编码:").append(deviceCodeErp1).append("未查询到ZfitXtCwzt;");
                throw new RuntimeException(sBuilder.toString());
            }

            String anlnr = zfitXtCwzt.getAnlnr();
            String equnr = zfitXtCwzt.getEqunr();
            String posid = zfitXtCwzt.getPosid();
            String posidT = zfitXtCwzt.getPosidT();

            ProjectManagerDetail projectManagerDetailQuery1 = new ProjectManagerDetail();
            QueryWrapper<ProjectManagerDetail> queryWrapper1 = Condition.getQueryWrapper(projectManagerDetailQuery1);
            queryWrapper1.lambda().like(ProjectManagerDetail::getErpAccountCode, deviceCodeErp1);
            ProjectManagerDetail projectManagerDetail = projectManagerDetailService.getOne(queryWrapper1);

            // 查询不到新增 ProjectManagerDetail 表
            if (Objects.isNull(projectManagerDetail)) {
                ProjectManagerDetail projectManagerDetailAdd = new ProjectManagerDetail();
                projectManagerDetailAdd.setUuid(uuid);
                projectManagerDetailAdd.setDeviceCode(String.valueOf(deviceCode));
                projectManagerDetailAdd.setDeviceName(fullName);
                projectManagerDetailAdd.setErpAssetCode(anlnr);
                projectManagerDetailAdd.setErpAccountCode(equnr);
                projectManagerDetailAdd.setErpAssetStatus(1);
                projectManagerDetailAdd.setErpTransferStatus(cmdbCientityProperties.getErpTransferStatus2());
                projectManagerDetailAdd.setErpStatus(2);
                projectManagerDetailAdd.setI6000Status(0);
                projectManagerDetailAdd.setDeviceType(deviceTypeCode);
                projectManagerDetailAdd.setWbsCode(posid);
                projectManagerDetailAdd.setWbsName(posidT);
                projectManagerDetailAdd.setStage("存量数据治理");
                try {
                    boolean save = projectManagerDetailService.save(projectManagerDetailAdd);
                    if (!save) {
                        sBuilder.append("新增ProjectManagerDetail失败;");
                        throw new RuntimeException(sBuilder.toString());
                    }
                } catch (Exception e) {
                    sBuilder.append("新增ProjectManagerDetail异常").append(e.getMessage()).append(";");
                    throw new RuntimeException(sBuilder.toString());
                }
            }

            // 维护工厂编码
            Object factoryCode = entityMap.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE);
            if (Objects.isNull(factoryCode) || "".equals(factoryCode)) {
                sBuilder.append("维护工厂编码为空;");
                throw new RuntimeException(sBuilder.toString());
            }
            // 通过维护工厂编码查询 当前维护工厂所在市县的维护工厂数据.
            List<String> swerkList = erpMaintainService.selectErpListBySwerk(String.valueOf(factoryCode));
            if (CollectionUtils.isEmpty(swerkList)) {
                sBuilder.append("通过维护工厂编码查询 未查询到当前维护工厂所在市县的维护工厂数据;");
                throw new RuntimeException(sBuilder.toString());
            }
            String swerkStr = String.join(",", swerkList);
            LOGGER.info("通过维护工厂编码查询 当前维护工厂所在市县的维护工厂数据: {}", swerkStr);


            ProjectManagerDetail projectManagerDetailQuery2 = new ProjectManagerDetail();
            QueryWrapper<ProjectManagerDetail> queryWrapper2 = Condition.getQueryWrapper(projectManagerDetailQuery2);
            queryWrapper2.lambda().like(ProjectManagerDetail::getErpAccountCode, deviceCodeErp1);
            ProjectManagerDetail projectManagerDetailUpdate = projectManagerDetailService.getOne(queryWrapper2);

            LOGGER.info("开始更新erp,更新数据: {}", entityMap);
            try {
                // 更新erp
                ErpTransEqunrResp equnrResp = this.manualTransEqunr(entityMap, zfitXtCwzt);
                sBuilder.append("更新请求erp结果: ").append(JSONObject.toJSON(equnrResp)).append(";");
                if (StringUtils.equals("S", equnrResp.getCode())) {
                    sBuilder.append("erp接口请求成功;");
                    projectManagerDetailUpdate.setErpStatus(2);
                    projectManagerDetailUpdate.setErpTransferStatus(cmdbCientityProperties.getErpTransferStatus2());
                    projectManagerDetailUpdate.setErpAssetStatus(1);
                    // 更新cmdb
                    Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                    entityMap.put(CmdbAttrConstant.IS_TO_ERP_CODE, cmdbCientityProperties.getYesNo());
                    longMapMap.put(Long.parseLong(entityMap.get(CmdbAttrConstant.ID).toString()), entityMap);

                    cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                } else {
                    projectManagerDetailUpdate.setErpStatus(3);
                    sBuilder.append("erp接口请求失败;");
                }
            } catch (Exception e) {
                projectManagerDetailUpdate.setErpStatus(3);
                sBuilder.append("更新erp台账抛出异常: ").append(e.getMessage()).append(";");
                LOGGER.info("更新erp抛出异常: {}", e.toString());
            }
            projectManagerDetailService.updateById(projectManagerDetailUpdate);
            i6000ImportLog.setDeviceInfo(sBuilder.toString());
        } catch (Exception e) {
            i6000ImportLog.setDeviceInfo(e.toString());
        }
    }


}

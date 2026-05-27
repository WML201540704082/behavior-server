package com.lnsoft.device.api.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lnsoft.common.tool.CommonUtil;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.api.cmdb.entity.I6000ErpImport;
import com.lnsoft.device.api.cmdb.excel.I6000ErpSyncListener;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.service.IErpMaintainService;
import com.lnsoft.device.api.erp.service.IErpSyncService;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import com.lnsoft.device.api.i6000.dto.I6000CiCientityDTO;
import com.lnsoft.device.api.i6000.response.I6000ResultResp;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.stock.dto.I6000ErpImportMasterDTO;
import com.lnsoft.device.api.stock.entity.I6000ErpImportMaster;
import com.lnsoft.device.api.stock.entity.I6000ImportLog;
import com.lnsoft.device.api.stock.service.II6000ERPService;
import com.lnsoft.device.api.stock.service.II6000ErpImportMasterService;
import com.lnsoft.device.api.stock.service.II6000ImportLogService;
import com.lnsoft.device.api.stock.vo.I6000ErpImportMasterVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.dto.I6000SrynDTO;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class I6000ERPServiceImpl implements II6000ERPService {

    private static final Logger LOGGER = LoggerFactory.getLogger(I6000ERPServiceImpl.class);

    private II6000Service i6000Service;
    private II6000ErpImportMasterService i6000ErpImportMasterService;
    private ICmdbService cmdbService;
    private IHardwareBasicTreeService hardwareBasicTreeService;
    private IProjectManagerDetailService projectManagerDetailService;
    private CmdbCientityProperties cmdbCientityProperties;
    private IZfitXtCwztService zfitXtCwztService;
    private II6000ImportLogService i6000ImportLogService;
    private IErpMaintainService erpMaintainService;
    private IErpSyncService ii6000ErpService;
    private ThirdProperties thirdProperties;


    @Override
    public void importSyncI6000Detail(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            throw new ServiceException("请上传文件!");
        }
        if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx")) && !StringUtils.endsWithIgnoreCase(filename, ".xlsm")) {
            throw new ServiceException("请上传正确的excel文件!");
        }
        if (file.getSize() > 1024 * 1024 * 100) {
            throw new ServiceException("文件大小超过限制，最大允许" + 1024 * 1024 * 100 + "MB");
        }

        InputStream inputStream = null;
        try {
            I6000ErpImportMasterDTO i6000ErpImportMasterDTO = new I6000ErpImportMasterDTO();
            IdevelopUser user = SecureUtil.getUser();
            i6000ErpImportMasterDTO.setImportStatus(0);
            List<I6000ErpImportMasterVO> i6000ErpImportMasterVOS = i6000ErpImportMasterService.selectI6000ErpImportMasterPageByUser(i6000ErpImportMasterDTO);
            int size = i6000ErpImportMasterVOS.size();
            if (size > 15) {
                throw new ServiceException("当前系统同步数量已超过同步限制,请稍后尝试! ");
            }
            List<Long> createUserList = i6000ErpImportMasterVOS.stream().map(I6000ErpImportMasterVO::getCreateUser).collect(Collectors.toList());
            if (createUserList.contains(user.getUserId()) && !org.apache.commons.lang3.StringUtils.equals("37", user.getRegionCode())) {
                throw new ServiceException("当前用户已存在数据同步文件, 请等待上次同步文件结束后在上传文件! ");
            }

            I6000ErpSyncListener i6000ErpSyncListener = new I6000ErpSyncListener();
            inputStream = new BufferedInputStream(file.getInputStream());
            EasyExcel.read(inputStream, I6000ErpImport.class, i6000ErpSyncListener).sheet().doRead();
            List<I6000ErpImport> list = i6000ErpSyncListener.getList();
            List<String> deviceCodeList = list.stream().map(I6000ErpImport::getDeviceCode).collect(Collectors.toList());
            if (deviceCodeList.size() > 1000 && !org.apache.commons.lang3.StringUtils.equals("37", user.getRegionCode())) {
                throw new ServiceException("请上传文件设备同步数量请小于1000条,便于查看同步记录! ");
            }

            String uuid = UuidUtils.uuid();
            I6000ErpImportMaster i6000ErpImportMaster = new I6000ErpImportMaster();
            i6000ErpImportMaster.setImportUuid(uuid);
            i6000ErpImportMaster.setImportName(filename);
            i6000ErpImportMaster.setImportNumber(deviceCodeList.size());
            i6000ErpImportMaster.setImportStatus(0);
            i6000ErpImportMasterService.save(i6000ErpImportMaster);

            this.importSyncI6000ErpDetail(deviceCodeList, uuid, Boolean.TRUE);

        } catch (IOException e) {
            LOGGER.error("流读取失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭失败");
                }
            }
        }
    }


    /**
     * 根据信通一体化设备编码, 同步I6000系统数据
     *
     * @param deviceCodeStencils
     * @param type               0：新增；1：修改
     * @return
     */
    @Override
    public String importSyncI6000Detail(List<DeviceCodeStencil> deviceCodeStencils, Integer type) {

        String result = UuidUtils.uuid();

        for (DeviceCodeStencil deviceCodeStencil : deviceCodeStencils) {

            I6000ImportLog i6000ImportLog = new I6000ImportLog();
            String deviceCode = deviceCodeStencil.getDeviceCode();

            if (type == 0) {
                syncI6000DetailAdd(deviceCode, i6000ImportLog, result);
                i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "{有资产数据}: 新增I6000系统数据结束;");
            }
            if (type == 1) {
                syncI6000DetailUpdate(deviceCode, i6000ImportLog, result);
                i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "{有资产数据}: 更新I6000系统数据结束;");
            }
            if (type == 99) {
                // 无资产新增或者修改
                syncI6000DetailAddNoErp(deviceCode, i6000ImportLog, result);
                i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "{无资产数据}: 新增/修改I6000系统数据结束;");
            }

            i6000ImportLogService.save(i6000ImportLog);
        }
        return result;
    }

    private void syncI6000DetailAddNoErp(String deviceCode, I6000ImportLog i6000ImportLog, String result) {
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
            entityMap.put(CmdbAttrConstant.REMARK, "信通一体化平台设备编码为:" + deviceCode);
            LOGGER.info("根据设备编码查询台账数据: {}", JSONObject.toJSONString(entityMap));

            // 设备UUID
            String uuid = String.valueOf(entityMap.get(CmdbAttrConstant.UUID));
            // 设备类型code
            String deviceTypeCode = String.valueOf(entityMap.get(CmdbAttrConstant.DEVICE_TYPE_CODE));

            String i6000CiIdStr = "";
            if (entityMap.containsKey(CmdbAttrConstant.I6000_CI_ID)) {
                Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                if (!Objects.isNull(i6000CiId) && !"".equals(i6000CiId)) {
                    i6000CiIdStr = String.valueOf(i6000CiId);
                }
            }

            HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(String.valueOf(deviceTypeCode)).build();
            HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
            if (Objects.isNull(hardwareBasicTreeOne)) {
                sBuilder.append("选择的设备类型在I6000模型中不存在;");
                throw new RuntimeException(sBuilder.toString());
            }

            Map<String, Map<String, Object>> i6000EntityMap = new HashMap<>();
            String ciTypeId = hardwareBasicTreeOne.getI6000Code();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(i6000CiIdStr)) {
                entityMap.put(I6000AttrConstant.CITYPE_ID, ciTypeId);
                entityMap.remove(CmdbAttrConstant.ASSET_CODE_ERP);
                entityMap.remove(CmdbAttrConstant.DEVICE_CODE_ERP);
                i6000EntityMap.put(i6000CiIdStr, entityMap);
                LOGGER.info("开始更新i6000,更新数据: {}", entityMap);
                try {
                    List<I6000ResultResp> resultResp = i6000Service.i6000BatchupdateCopy(i6000EntityMap);
                    LOGGER.info("更新请求i6000结果: {}", JSONObject.toJSON(resultResp));
                    if (org.apache.commons.lang3.StringUtils.equals("true", resultResp.get(0).getSuccessful())) {
                        sBuilder.append("更新请求i6000结果: ").append("同步成功(1)").append(";");
                        // 更新cmdb
                        Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                        entityMap.put(CmdbAttrConstant.I6000_CI_ID, i6000CiIdStr);
                        entityMap.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                        entityMap.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());
                        entityMap.remove(CmdbAttrConstant.REMARK);
                        longMapMap.put(Long.parseLong(entityMap.get(CmdbAttrConstant.ID).toString()), entityMap);

                        cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                    } else {
                        sBuilder.append("更新请求i6000结果: ").append("同步失败").append(";");
                    }
                } catch (Exception e) {
                    sBuilder.append("更新i6000台账抛出异常: ").append(e).append(";");
                    LOGGER.info("更新i6000台账抛出异常: {}", e.toString());
                }
            } else {
                try {
                    Map<String, Map<String, Object>> entityMap2 = new HashMap<>();
                    entityMap2.put(String.valueOf(uuid), entityMap);
                    LOGGER.info("开始新增i6000,新增数据: {}", entityMap);
                    I6000ResultResp resultResp = i6000Service.i6000BatchsaveCopy(ciTypeId, entityMap2);
                    LOGGER.info("新增请求i6000结果: {}", JSONObject.toJSON(resultResp));
                    sBuilder.append("新增请求i6000结果: ").append(JSONObject.toJSON(resultResp)).append(";");
                    if (org.apache.commons.lang3.StringUtils.equals("true", resultResp.getSuccessful())) {

                        sBuilder.append("i6000新增接口请求成功;");
                        //更新cmdb
                        Map<Long, Map<String, Object>> updateCmdbMap = new HashMap<>();
                        entityMap.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                        entityMap.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());
                        entityMap.remove(CmdbAttrConstant.REMARK);
                        updateCmdbMap.put(Long.parseLong(String.valueOf(entityMap.get(CmdbAttrConstant.ID))), entityMap);
                        cmdbService.cientityBatchupdate(updateCmdbMap, TransactionActionType.UPDATE);
                    } else {
                        sBuilder.append("i6000接口请求失败;");
                    }
                } catch (Exception e) {
                    sBuilder.append("新增i6000台账抛出异常: ").append(e.getMessage()).append(";");
                    LOGGER.info("新增i6000台账抛出异常: {}", e.getMessage());
                }
            }
            i6000ImportLog.setDeviceInfo(sBuilder.toString());
        } catch (Exception e) {
            i6000ImportLog.setDeviceInfo(e.getMessage());
        }
    }


    @Override
    @Async
    public void importSyncI6000Detail(List<String> deviceCodeList) {

        String result = UuidUtils.uuid();

        for (String deviceCode : deviceCodeList) {

            I6000ImportLog i6000ImportLog = new I6000ImportLog();

            this.syncI6000DetailUpdateChange(deviceCode, i6000ImportLog, result);
            i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "更新I6000系统数据结束;");

            i6000ImportLogService.save(i6000ImportLog);
        }
    }


    /**
     * 根据信通一体化设备编码, 同步I6000系统数据
     *
     * @param deviceCodeList
     * @return
     */
    @Override
    public String importSyncI6000ErpDetail(List<String> deviceCodeList, String importUuid, Boolean isBatch) {

        String msg = "";
        if (isBatch) {
            for (String deviceCode : deviceCodeList) {

                I6000ImportLog i6000ImportLog = new I6000ImportLog();
                i6000ImportLog.setDeviceInfo("{用户自主更新数据}: ");
                syncI6000DetailUpdate(deviceCode, i6000ImportLog, importUuid);
                i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "更新I6000系统和ERP系统数据结束.");
                i6000ImportLogService.save(i6000ImportLog);
            }

            i6000ErpImportMasterService.update(Wrappers.<I6000ErpImportMaster>lambdaUpdate()
                    .set(I6000ErpImportMaster::getImportStatus, 1)
                    .eq(I6000ErpImportMaster::getImportUuid, importUuid));
            return msg;
        } else {
            for (String deviceCode : deviceCodeList) {
                I6000ImportLog i6000ImportLog = new I6000ImportLog();
                syncI6000DetailUpdate(deviceCode, i6000ImportLog, importUuid);
                i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "更新I6000系统和ERP系统数据结束.");
                msg = i6000ImportLog.getDeviceInfo();
                LOGGER.info("用户自主更新数据: {}", msg);
            }
            return msg;
        }

    }


    /**
     * (task)根据设备编码, 信通一体化同步I6000系统数据
     *
     * @param i6000SrynDTO
     * @return
     */
    @Override
    public String syncI6000DetailTask(I6000SrynDTO i6000SrynDTO) {

        String result = i6000SrynDTO.getTaskUUID();

        String deviceCode = i6000SrynDTO.getDeviceCode();
        I6000ImportLog i6000ImportLog = new I6000ImportLog();

        // 同步I6000
        syncI6000DetailUpdate(deviceCode, i6000ImportLog, result);

        i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + "更新I6000系统数据结束;");
        i6000ImportLogService.save(i6000ImportLog);

        return result;
    }

    @Override
    public String importSyncI6000DetailOne(String deviceCode) {

        try {
            List<String> deviceCodeList = Collections.singletonList(deviceCode);
            String uuid = UuidUtils.uuid();
            return this.importSyncI6000ErpDetail(deviceCodeList, uuid, Boolean.FALSE);
        } catch (Exception e) {
            throw new RuntimeException("设备编码: " + deviceCode + "同步失败: " + e.getMessage());
        }

    }


    private void syncI6000DetailAdd(String deviceCode, I6000ImportLog i6000ImportLog, String result) {

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
            entityMap.put(CmdbAttrConstant.REMARK, "信通一体化平台设备编码为:" + deviceCode);
            LOGGER.info("根据设备编码查询台账数据: {}", JSONObject.toJSONString(entityMap));
            Map<String, Object> erpEntityMap = new HashMap<>();
            erpEntityMap.putAll(entityMap);

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
            String zcYz = zfitXtCwzt.getZcYz();
            String zcJz = zfitXtCwzt.getZcJz();
            // 资产原值
            entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
            // 项目名称
            entityMap.put(CmdbAttrConstant.PROJECT_NAME, posidT);
            // 净值
            entityMap.put(CmdbAttrConstant.NET_WORTH, zcJz);

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

            String i6000CiIdStr = "";
            if (entityMap.containsKey(CmdbAttrConstant.I6000_CI_ID)) {
                Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                if (!Objects.isNull(i6000CiId) && !"".equals(i6000CiId)) {
                    i6000CiIdStr = String.valueOf(i6000CiId);
                }
            }

            HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(String.valueOf(deviceTypeCode)).build();
            HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
            if (Objects.isNull(hardwareBasicTreeOne)) {
                sBuilder.append("选择的设备类型在I6000模型中不存在;");
                throw new RuntimeException(sBuilder.toString());
            }

            String ciTypeId = hardwareBasicTreeOne.getI6000Code();
            ProjectManagerDetail projectManagerDetailQuery2 = new ProjectManagerDetail();
            QueryWrapper<ProjectManagerDetail> queryWrapper2 = Condition.getQueryWrapper(projectManagerDetailQuery2);
            queryWrapper2.lambda().like(ProjectManagerDetail::getErpAccountCode, deviceCodeErp1);
            ProjectManagerDetail projectManagerDetailUpdate = projectManagerDetailService.getOne(queryWrapper2);

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(i6000CiIdStr)) {
                sBuilder.append("数据已同步过I6000系统，无需新增").append(";");
            } else {
                I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
                i6000CiCientityDTO.setAttrCode("CI_ID,CITYPE_ID,ERP_ASSET_NO,ERP_LEDGER_NO");

                List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();

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

                i6000CiCientityDTO.setConditions(orConditionList);
                i6000CiCientityDTO.setPageStart("1");
                i6000CiCientityDTO.setPageSize("10");

                try {
                    List<Map<String, Object>> i6000ResultMap = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);

                    if (CollectionUtils.isEmpty(i6000ResultMap)) {
                        try {
                            Map<String, Map<String, Object>> entityMap2 = new HashMap<>();
                            entityMap2.put(String.valueOf(uuid), entityMap);
                            LOGGER.info("开始新增i6000,新增数据: {}", entityMap);
                            I6000ResultResp resultResp = i6000Service.i6000BatchsaveCopy(ciTypeId, entityMap2);
                            LOGGER.info("新增请求i6000结果: {}", JSONObject.toJSON(resultResp));
                            sBuilder.append("新增请求i6000结果: ").append(JSONObject.toJSON(resultResp)).append(";");
                            if (org.apache.commons.lang3.StringUtils.equals("true", resultResp.getSuccessful())) {
                                projectManagerDetailUpdate.setI6000Status(2);
                                projectManagerDetailUpdate.setI6000Remake("同步成功!");
                                projectManagerDetailUpdate.setDeviceCode(deviceCode);
                                projectManagerDetailUpdate.setDeviceName(fullName);
                                sBuilder.append("i6000新增接口请求成功;");

                                try {
                                    // 更新erp
                                    ErpTransEqunrResp equnrResp = ii6000ErpService.manualTransEqunr(erpEntityMap, zfitXtCwzt);
                                    if (org.apache.commons.lang3.StringUtils.equals("S", equnrResp.getCode())) {
                                        sBuilder.append("更新请求erp结果: ").append("同步成功").append(";");
                                        sBuilder.append("更新请求erp结果: ").append(JSONObject.toJSON(equnrResp)).append(";");
                                        sBuilder.append("erp接口请求成功;");
                                        projectManagerDetailUpdate.setErpStatus(2);
                                        projectManagerDetailUpdate.setErpTransferStatus(cmdbCientityProperties.getErpTransferStatus2());
                                        projectManagerDetailUpdate.setErpAssetStatus(1);
                                    } else {
                                        sBuilder.append("更新请求erp结果: ").append("同步失败: ").append(JSONObject.toJSON(equnrResp)).append(";");
                                        projectManagerDetailUpdate.setErpStatus(3);
                                        sBuilder.append("erp接口请求失败;");
                                    }
                                } catch (Exception e) {
                                    projectManagerDetailUpdate.setErpStatus(3);
                                    sBuilder.append("更新erp异常(同步失败): ").append(e.getMessage()).append(";");
                                    LOGGER.info("更新erp异常(同步失败): {}", e.toString());
                                }

                                //更新cmdb
                                Map<Long, Map<String, Object>> updateCmdbMap = new HashMap<>();
                                entityMap.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                                entityMap.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());
                                entityMap.remove(CmdbAttrConstant.REMARK);
                                entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
                                entityMap.put(CmdbAttrConstant.NET_WORTH, posidT);
                                updateCmdbMap.put(Long.parseLong(String.valueOf(entityMap.get(CmdbAttrConstant.ID))), entityMap);
                                cmdbService.cientityBatchupdate(updateCmdbMap, TransactionActionType.UPDATE);
                            } else {
                                projectManagerDetailUpdate.setI6000Status(3);
                                projectManagerDetailUpdate.setI6000Remake("同步失败!");
                                sBuilder.append("i6000接口请求失败;");
                            }
                        } catch (Exception e) {
                            sBuilder.append("新增i6000台账抛出异常: ").append(e.getMessage()).append(";");
                            LOGGER.info("新增i6000台账抛出异常: {}", e.getMessage());
                        }
                        projectManagerDetailService.updateById(projectManagerDetailUpdate);
                    } else {
                        sBuilder.append("I6000系统已存在，无需新增").append(";");
                    }
                } catch (Exception e) {
                    sBuilder.append("同步I6000系统错误, 异常信息: ").append(e.getMessage()).append(";");
                }
            }
            i6000ImportLog.setDeviceInfo(sBuilder.toString());
        } catch (Exception e) {
            i6000ImportLog.setDeviceInfo(e.getMessage());
        }
    }

    private void syncI6000DetailUpdate(String deviceCode, I6000ImportLog i6000ImportLog, String result) {
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
            entityMap.put(CmdbAttrConstant.REMARK, "信通一体化平台设备编码为:" + deviceCode);
            LOGGER.info("根据设备编码查询台账数据: {}", JSONObject.toJSONString(entityMap));
            Map<String, Object> erpEntityMap = new HashMap<>();
            erpEntityMap.putAll(entityMap);

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
            // 设备起始高度(U)
            String deviceHeightBegin = String.valueOf(entityMap.get(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN));
            // 设备高度
            String deviceHeight = String.valueOf(entityMap.get(CmdbAttrConstant.DEVICE_HEIGHT));
            //
            String deviceHeightEnd = String.valueOf(entityMap.get(CmdbAttrConstant.DEVICE_HEIGHT_END));
            // // 设备分类code
            // String deviceCategoryCode = String.valueOf(entityMap.get(CmdbAttrConstant.DEVICE_CATEGORY_CODE));

            if (!thirdProperties.getIsSyncI6000()) {
                IdevelopUser user = SecureUtil.getUser();
                if ((org.apache.commons.lang3.StringUtils.equals(cmdbCientityProperties.getT10503(), deviceTypeCode)
                        || org.apache.commons.lang3.StringUtils.equals(cmdbCientityProperties.getT10501(), deviceTypeCode))
                        && !org.apache.commons.lang3.StringUtils.equals("37", user.getRegionCode())) {
                    sBuilder.append("台式机和笔记本电脑数据同步, 请联系管理员(8302321);");
                    throw new RuntimeException(sBuilder.toString());
                }
            }

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
            String zcYz = zfitXtCwzt.getZcYz();
            String zcJz = zfitXtCwzt.getZcJz();
            // 资产原值
            entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
            // 净值
            entityMap.put(CmdbAttrConstant.NET_WORTH, zcJz);
            // 项目名称
            entityMap.put(CmdbAttrConstant.PROJECT_NAME, posidT);

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

            HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(String.valueOf(deviceTypeCode)).build();
            HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
            if (Objects.isNull(hardwareBasicTreeOne)) {
                sBuilder.append("选择的设备类型在I6000模型中不存在;");
                throw new RuntimeException(sBuilder.toString());
            }

            String ciTypeId = hardwareBasicTreeOne.getI6000Code();

            String i6000CiIdStr = "";
            if (entityMap.containsKey(CmdbAttrConstant.I6000_CI_ID)) {
                Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                if (!Objects.isNull(i6000CiId) && !"".equals(i6000CiId)) {
                    i6000CiIdStr = String.valueOf(i6000CiId);

                    I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
                    i6000CiCientityDTO.setAttrCode("CI_ID,CITYPE_ID,ERP_ASSET_NO,ERP_LEDGER_NO,CYCLE_STATUS");

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

                    // 维护工厂
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

                    List<Map<String, Object>> i6000ResultMap = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);
                    if (CollectionUtils.isEmpty(i6000ResultMap)) {
                        i6000CiIdStr = "";
                    } else {
                        Map<String, Object> map = i6000ResultMap.get(0);
                        String ciId = String.valueOf(map.get("CI_ID"));
                        String cycleStatus = String.valueOf(map.get("CYCLE_STATUS"));
                        // 判断I6000设备状态
                        exCycleStatus(cycleStatus, sBuilder);
                        // 判断是否更换关联关系
                        if (!org.apache.commons.lang3.StringUtils.equals(i6000CiIdStr, ciId)) {
                            sBuilder.append("更换关联关系: ").append(ciId).append(";");
                            i6000CiIdStr = ciId;
                        } else {
                            sBuilder.append("关联关系不变: ").append(ciId).append(";");
                        }
                    }
                }
            }

            Map<String, Map<String, Object>> i6000EntityMap = new HashMap<>();

            ProjectManagerDetail projectManagerDetailQuery2 = new ProjectManagerDetail();
            QueryWrapper<ProjectManagerDetail> queryWrapper2 = Condition.getQueryWrapper(projectManagerDetailQuery2);
            queryWrapper2.lambda().like(ProjectManagerDetail::getErpAccountCode, deviceCodeErp1);
            ProjectManagerDetail projectManagerDetailUpdate = projectManagerDetailService.getOne(queryWrapper2);

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(i6000CiIdStr)) {

                entityMap.put(I6000AttrConstant.CITYPE_ID, ciTypeId);
                entityMap.remove(CmdbAttrConstant.ASSET_CODE_ERP);
                entityMap.remove(CmdbAttrConstant.DEVICE_CODE_ERP);
                i6000EntityMap.put(i6000CiIdStr, entityMap);
                LOGGER.info("开始更新i6000,更新数据: {}", entityMap);
                try {
                    List<I6000ResultResp> resultResp = i6000Service.i6000BatchupdateCopy(i6000EntityMap);
                    LOGGER.info("更新请求i6000结果: {}", JSONObject.toJSON(resultResp));
                    if (org.apache.commons.lang3.StringUtils.equals("true", resultResp.get(0).getSuccessful())) {
                        projectManagerDetailUpdate.setI6000Status(2);
                        projectManagerDetailUpdate.setI6000Remake("同步成功!");
                        projectManagerDetailUpdate.setDeviceCode(deviceCode);
                        projectManagerDetailUpdate.setDeviceName(fullName);
                        sBuilder.append("更新请求i6000结果: ").append("同步成功(1)").append(";");

                        try {
                            // 更新erp
                            ErpTransEqunrResp equnrResp = ii6000ErpService.manualTransEqunr(erpEntityMap, zfitXtCwzt);
                            if (org.apache.commons.lang3.StringUtils.equals("S", equnrResp.getCode())) {
                                sBuilder.append("更新请求erp结果: ").append("同步成功").append(";");
                                projectManagerDetailUpdate.setErpStatus(2);
                                projectManagerDetailUpdate.setErpTransferStatus(cmdbCientityProperties.getErpTransferStatus2());
                                projectManagerDetailUpdate.setErpAssetStatus(1);
                            } else {
                                sBuilder.append("更新请求erp结果: ").append("同步失败: ").append(JSONObject.toJSON(equnrResp)).append(";");
                                projectManagerDetailUpdate.setErpStatus(3);
                            }
                        } catch (Exception e) {
                            projectManagerDetailUpdate.setErpStatus(3);
                            sBuilder.append("更新erp抛出异常(同步失败): ").append(e.getMessage()).append(";");
                            LOGGER.info("更新erp抛出异常(同步失败): {}", e.toString());
                        }

                        // 更新cmdb
                        Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                        entityMap.put(CmdbAttrConstant.I6000_CI_ID, i6000CiIdStr);
                        entityMap.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                        entityMap.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());
                        entityMap.remove(CmdbAttrConstant.REMARK);
                        entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
                        entityMap.put(CmdbAttrConstant.NET_WORTH, posidT);
                        longMapMap.put(Long.parseLong(entityMap.get(CmdbAttrConstant.ID).toString()), entityMap);

                        cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                    } else {
                        projectManagerDetailUpdate.setI6000Status(3);
                        projectManagerDetailUpdate.setI6000Remake("同步失败!");
                        sBuilder.append("更新请求i6000结果: ").append("同步失败").append(";");
                    }
                } catch (Exception e) {
                    sBuilder.append("更新i6000台账抛出异常: ").append(e).append(";");
                    LOGGER.info("更新i6000台账抛出异常: {}", e.toString());
                }
                projectManagerDetailService.updateById(projectManagerDetailUpdate);
            } else {
                I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
                i6000CiCientityDTO.setAttrCode("CI_ID,CITYPE_ID,ERP_ASSET_NO,ERP_LEDGER_NO,CYCLE_STATUS");

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

                try {
                    List<Map<String, Object>> i6000ResultMap = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);

                    if (CollectionUtils.isEmpty(i6000ResultMap)) {
                        sBuilder.append("I6000未查询到数据, 请核实I6000系统中数据;");
                    } else {
                        Map<String, Object> map = i6000ResultMap.get(0);
                        String ciId = String.valueOf(map.get("CI_ID"));
                        Object ciTypeId1 = map.get("CITYPE_ID");
                        String cycleStatus = String.valueOf(map.get("CYCLE_STATUS"));
                        // 判断I6000设备状态
                        exCycleStatus(cycleStatus, sBuilder);

                        entityMap.put(I6000AttrConstant.CITYPE_ID, ciTypeId1);
                        entityMap.put(CmdbAttrConstant.I6000_CI_ID, ciId);
                        entityMap.remove(CmdbAttrConstant.ASSET_CODE_ERP);
                        entityMap.remove(CmdbAttrConstant.DEVICE_CODE_ERP);
                        i6000EntityMap.put(ciId, entityMap);
                        LOGGER.info("开始更新i6000,更新数据: {}", entityMap);
                        try {
                            List<I6000ResultResp> resultResp = i6000Service.i6000BatchupdateCopy(i6000EntityMap);
                            LOGGER.info("更新请求i6000结果: {}", JSONObject.toJSON(resultResp));
                            if (org.apache.commons.lang3.StringUtils.equals("true", resultResp.get(0).getSuccessful())) {
                                projectManagerDetailUpdate.setI6000Status(2);
                                projectManagerDetailUpdate.setI6000Remake("同步成功!");
                                projectManagerDetailUpdate.setDeviceCode(deviceCode);
                                projectManagerDetailUpdate.setDeviceName(fullName);
                                sBuilder.append("更新请求i6000结果: ").append("同步成功(2)").append(";");

                                try {
                                    // 更新erp
                                    ErpTransEqunrResp equnrResp = ii6000ErpService.manualTransEqunr(erpEntityMap, zfitXtCwzt);
                                    if (org.apache.commons.lang3.StringUtils.equals("S", equnrResp.getCode())) {
                                        sBuilder.append("更新请求erp结果: ").append("同步成功").append(";");
                                        projectManagerDetailUpdate.setErpStatus(2);
                                        projectManagerDetailUpdate.setErpTransferStatus(cmdbCientityProperties.getErpTransferStatus2());
                                        projectManagerDetailUpdate.setErpAssetStatus(1);
                                    } else {
                                        sBuilder.append("更新请求erp结果: ").append("同步失败: ").append(JSONObject.toJSON(equnrResp)).append(";");
                                        projectManagerDetailUpdate.setErpStatus(3);
                                    }
                                } catch (Exception e) {
                                    projectManagerDetailUpdate.setErpStatus(3);
                                    sBuilder.append("更新erp抛出异常(同步失败): ").append(e.getMessage()).append(";");
                                    LOGGER.info("更新erp抛出异常(同步失败): {}", e.toString());
                                }

                                //更新cmdb
                                Map<Long, Map<String, Object>> updateCmdbMap = new HashMap<>();
                                entityMap.put(CmdbAttrConstant.I6000_CI_ID, ciId);
                                entityMap.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                                entityMap.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());
                                entityMap.remove(CmdbAttrConstant.REMARK);
                                entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
                                entityMap.put(CmdbAttrConstant.NET_WORTH, posidT);
                                updateCmdbMap.put(Long.parseLong(String.valueOf(entityMap.get(CmdbAttrConstant.ID))), entityMap);
                                cmdbService.cientityBatchupdate(updateCmdbMap, TransactionActionType.UPDATE);
                            } else {
                                projectManagerDetailUpdate.setI6000Status(3);
                                projectManagerDetailUpdate.setI6000Remake("同步失败!");
                                sBuilder.append("更新请求i6000结果: ").append("同步失败").append(";");
                            }
                        } catch (Exception e) {
                            sBuilder.append("更新i6000台账抛出异常: ").append(e).append(";");
                        }
                        projectManagerDetailService.updateById(projectManagerDetailUpdate);
                    }
                } catch (Exception e) {
                    sBuilder.append("同步I6000系统错误, 异常信息: ").append(e).append(";");
                }
            }
            i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + sBuilder);
        } catch (Exception e) {
            CommonUtil.StringWriter(e, "信通一体化平台同步ERP和I6000系统异常!");
            i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + e);
        }
    }

    /**
     * 判断I6000设备状态
     *
     * @param cycleStatus
     * @param sBuilder
     */
    private static void exCycleStatus(String cycleStatus, StringBuilder sBuilder) {
        if (org.apache.commons.lang3.StringUtils.equals(cycleStatus, "40000707")) {
            sBuilder.append("该查询I6000系统侧设备状态为报废状态, 不允许更改状态;");
            throw new RuntimeException(sBuilder.toString());
        }
    }

    /**
     * 设备变更异步同步I6000系统
     *
     * @param deviceCode
     * @param i6000ImportLog
     * @param result
     */
    public void syncI6000DetailUpdateChange(String deviceCode, I6000ImportLog i6000ImportLog, String result) {
        StringBuilder sBuilder = new StringBuilder();
        i6000ImportLog.setImportUuid(result);
        i6000ImportLog.setDeviceCode(deviceCode);
        try {
            sBuilder.append("<设备变更异步同步I6000系统>");
            sBuilder.append("设备编码: ").append(deviceCode).append(";");

            CiCientitySearch cientitySearch = new CiCientitySearch();
            List<CiCientitySearchVO> entity = new ArrayList<>();
            CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).expression(Expression.EQUAL).attrValue(deviceCode).build();
            entity.add(ciCientitySearchVO);
            cientitySearch.setEntity(entity);
            cientitySearch.setQuery(new Query().setCurrent(1).setSize(10));
            cientitySearch.setFullField(Boolean.TRUE);

            Map<String, Object> entityMap = cmdbService.getCiCientityListByCondition(cientitySearch).getData().get(0);
            entityMap.put(CmdbAttrConstant.REMARK, "信通一体化平台设备编码为:" + deviceCode);
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
            String zcYz = zfitXtCwzt.getZcYz();
            String zcJz = zfitXtCwzt.getZcJz();
            // 资产原值
            entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
            // 项目名称
            entityMap.put(CmdbAttrConstant.PROJECT_NAME, posidT);
            // 净值
            entityMap.put(CmdbAttrConstant.NET_WORTH, zcJz);

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
                projectManagerDetailAdd.setStage("设备变更");
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

            HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(String.valueOf(deviceTypeCode)).build();
            HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
            if (Objects.isNull(hardwareBasicTreeOne)) {
                sBuilder.append("选择的设备类型在I6000模型中不存在;");
                throw new RuntimeException(sBuilder.toString());
            }


            String ciTypeId = hardwareBasicTreeOne.getI6000Code();
            Map<String, Map<String, Object>> i6000EntityMap = new HashMap<>();
            String i6000CiIdStr = "";
            if (entityMap.containsKey(CmdbAttrConstant.I6000_CI_ID)) {
                Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                if (!Objects.isNull(i6000CiId) && !"".equals(i6000CiId)) {
                    i6000CiIdStr = String.valueOf(i6000CiId);

                    I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
                    i6000CiCientityDTO.setAttrCode("CI_ID,CITYPE_ID,ERP_ASSET_NO,ERP_LEDGER_NO,CYCLE_STATUS");

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

                    List<Map<String, Object>> i6000ResultMap = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);
                    if (CollectionUtils.isEmpty(i6000ResultMap)) {
                        i6000CiIdStr = "";
                    } else {
                        Map<String, Object> map = i6000ResultMap.get(0);
                        String ciId = String.valueOf(map.get("CI_ID"));
                        String cycleStatus = String.valueOf(map.get("CYCLE_STATUS"));
                        // 判断I6000设备状态
                        exCycleStatus(cycleStatus, sBuilder);
                        if (!org.apache.commons.lang3.StringUtils.equals(i6000CiIdStr, ciId)) {
                            sBuilder.append("更换关联关系: ").append(ciId).append(";");
                            i6000CiIdStr = ciId;
                        } else {
                            sBuilder.append("关联关系不变: ").append(ciId).append(";");
                        }
                    }
                }
            }

            ProjectManagerDetail projectManagerDetailQuery2 = new ProjectManagerDetail();
            QueryWrapper<ProjectManagerDetail> queryWrapper2 = Condition.getQueryWrapper(projectManagerDetailQuery2);
            queryWrapper2.lambda().like(ProjectManagerDetail::getErpAccountCode, deviceCodeErp1);
            ProjectManagerDetail projectManagerDetailUpdate = projectManagerDetailService.getOne(queryWrapper2);

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(i6000CiIdStr)) {

                entityMap.put(I6000AttrConstant.CITYPE_ID, ciTypeId);
                entityMap.remove(CmdbAttrConstant.ASSET_CODE_ERP);
                entityMap.remove(CmdbAttrConstant.DEVICE_CODE_ERP);
                i6000EntityMap.put(i6000CiIdStr, entityMap);
                LOGGER.info("开始更新i6000,更新数据: {}", entityMap);
                try {
                    List<I6000ResultResp> resultResp = i6000Service.i6000BatchupdateCopy(i6000EntityMap);
                    LOGGER.info("更新请求i6000结果: {}", JSONObject.toJSON(resultResp));
                    sBuilder.append("更新请求i6000结果: ").append(JSONObject.toJSON(resultResp)).append(";");
                    if (org.apache.commons.lang3.StringUtils.equals("true", resultResp.get(0).getSuccessful())) {
                        projectManagerDetailUpdate.setI6000Status(2);
                        projectManagerDetailUpdate.setI6000Remake("同步成功!");
                        projectManagerDetailUpdate.setDeviceCode(deviceCode);
                        projectManagerDetailUpdate.setDeviceName(fullName);
                        sBuilder.append("i6000更新接口请求成功;");

                        // 更新cmdb
                        Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                        entityMap.put(CmdbAttrConstant.I6000_CI_ID, i6000CiIdStr);
                        entityMap.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                        entityMap.put(CmdbAttrConstant.I6000_UPDATE_TIME, LocalDate.now());
                        entityMap.remove(CmdbAttrConstant.REMARK);
                        entityMap.put(CmdbAttrConstant.ASSET_ORIGINAL, zcYz);
                        entityMap.put(CmdbAttrConstant.NET_WORTH, posidT);
                        longMapMap.put(Long.parseLong(entityMap.get(CmdbAttrConstant.ID).toString()), entityMap);

                        cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                    } else {
                        projectManagerDetailUpdate.setI6000Status(3);
                        projectManagerDetailUpdate.setI6000Remake("同步失败!");
                        sBuilder.append("i6000接口请求失败;");
                    }
                } catch (Exception e) {
                    sBuilder.append("更新i6000台账抛出异常: ").append(e.getMessage()).append(";");
                    LOGGER.info("更新i6000台账抛出异常: {}", e.getMessage());
                }
                projectManagerDetailService.updateById(projectManagerDetailUpdate);
            } else {
                sBuilder.append("信通一体化未查询到关联I6000数据,无需进行数据同步").append(";");
            }
            i6000ImportLog.setDeviceInfo(sBuilder.toString());
        } catch (Exception e) {
            i6000ImportLog.setDeviceInfo(i6000ImportLog.getDeviceInfo() + e);
        }
    }


}

package com.lnsoft.device.api.cmdb.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.lnsoft.cmdb.entity.*;
import com.lnsoft.common.enums.device.ImportFileTypeEnum;
import com.lnsoft.common.enums.device.OssFileTypeEnum;
import com.lnsoft.common.utils.IdevelopUtils;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.oss.model.IdevelopFile;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.api.asset.wrapper.HardwareBasicWrapper;
import com.lnsoft.device.api.cmdb.dto.CmdbCardDTO;
import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.api.cmdb.excel.DeviceCodeListener;
import com.lnsoft.device.api.cmdb.service.ICmdbCiAttrService;
import com.lnsoft.device.api.cmdb.service.ICmdbResourcecenterTypeCiService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.common.dto.ImportResultDTO;
import com.lnsoft.device.api.common.entity.ImportLog;
import com.lnsoft.device.api.common.entity.ImportResult;
import com.lnsoft.device.api.common.service.IImportLogService;
import com.lnsoft.device.api.common.service.IImportResultService;
import com.lnsoft.device.api.common.vo.ImportResultVO;
import com.lnsoft.device.entity.OssFile;
import com.lnsoft.device.api.oss.service.IOssFileService;
import com.lnsoft.device.api.res.enums.AttrMappingType;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.CmdbDictCi;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.eums.*;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.BarCode;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.entity.cmdb.CmdbCientityBatchsave;
import com.lnsoft.system.entity.Dept;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/1 10:04
 * @Description: ICmdbServiceImpl
 */
@Service
@Slf4j
@AllArgsConstructor
public class CmdbServiceImpl implements ICmdbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmdbServiceImpl.class);
    private IHardwareBasicTreeService iHardwareBasicTreeService;
    private IHardwareBasicTreeService hardwareBasicTreeService;
    private ICmdbCiAttrService cmdbCiAttrService;
    private ICmdbResourcecenterTypeCiService cmdbResourcecenterTypeCiService;
    private CmdbCientityProperties cmdbCientityProperties;
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private AliossTemplate aliossTemplate;
    private IOssFileService ossFileService;
    private IImportResultService importResultService;
    private IImportLogService importLogService;
    @Resource
    private OrderNumberUtil orderNumberUtil;
    private static final String GLOBAL_DATA_ID = "global_979768512987136";
    private static final String TYPE_SELECT = "select";
    private static final String TYPE_TEXT = "text";
    private static final String CORP_FULL_NAME = "corpFullName";


    /**
     * 根据设备分类和设备类型 获取模型id
     *
     * @param hardwareBasicTree
     * @return
     */
    @Override
    public Long getCiId(HardwareBasicTree hardwareBasicTree) {
        if (StringUtils.isEmpty(hardwareBasicTree.getDeviceType())) {
            throw new RuntimeException("设备类型不存在！");
        }

        HardwareBasicTree detail = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
        return detail.getCiId();
    }

    /**
     * 支持查询 [IT设备] 下获取分页查询资产台账 列转行
     *
     * @param entity
     * @return
     */
    @Override
    public FeignCiCientity getCiCientityList(List<CiCientitySearchVO> entity, Query query) {
        try {
            Long ciId = cmdbResourcecenterTypeCiService.getItDeviceCiId();

            R<FeignCiCientity> ciCientityListPage = getCiCientityList(entity, query, ciId);
            return ciCientityListPage.getData();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 支持查询 [资产台账] 下获取分页查询 列转行
     *
     * @param entity
     * @return
     */
    @Override
    public FeignCiCientity getCiCientityListByClaccify(List<CiCientitySearchVO> entity, Query query) {
        try {
            Long ciId = cmdbResourcecenterTypeCiService.getAssetStandCiId();
            R<FeignCiCientity> ciCientityListPage = getCiCientityList(entity, query, ciId);
            return ciCientityListPage.getData();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 支持查询 [根据不同的条件处理] 下获取分页查询 列转行
     *
     * @param cientitySearch
     * @return
     */
    @Override
    public FeignCiCientity getCiCientityListByCondition(CiCientitySearch cientitySearch) {
        try {
            Long ciId = cmdbResourcecenterTypeCiService.getItDeviceCiId();
            if (cientitySearch.getFullField()) {
                ciId = cmdbResourcecenterTypeCiService.getAssetStandCiId();
            }
            if (cientitySearch.getSpecicalField()) {
                Long specicalCiId = cientitySearch.getSpecicalCiId();
                if (Objects.isNull(specicalCiId)) {
                    throw new RuntimeException("特殊字段模型ID不能为空");
                }
                ciId = specicalCiId;
            }

            R<FeignCiCientity> ciCientityListPage = getCiCientityListByIdList(cientitySearch, ciId);
            return ciCientityListPage.getData();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 配置项详情 列转行
     *
     * @param feignCmdbCientityGet
     * @return
     */
    @Override
    public Map<String, Object> getCientityDetail(FeignCmdbCientityGet feignCmdbCientityGet) {

        Map<String, Object> returnMap = new HashMap<>();
        R<JSONObject> cientity = CmdbCiAttrWrapper.build().feignCientityGet(feignCmdbCientityGet);
        if (cientity.getCode() == 500) {
            throw new RuntimeException(cientity.getMsg());
        }
        JSONObject aReturn = cientity.getData().getJSONObject("Return");
        if (Objects.isNull(aReturn)) {
            return returnMap;
        }
        JSONObject attrEntityData = aReturn.getJSONObject("attrEntityData");
        if (Objects.isNull(attrEntityData)) {
            return returnMap;
        }
        returnMap.put("id", aReturn.get("id"));
        returnMap.put("ciId", aReturn.get("ciId"));
        Map<String, Object> innerMap = attrEntityData.getInnerMap();
        for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
            Map<String, Object> map = (Map<String, Object>) entry.getValue();
            String name = String.valueOf(map.get("name"));
            List<Object> valueList = (List<Object>) map.get("valueList");
            if (valueList.size() == 0) {
                returnMap.put(name, "");
            } else if (valueList.size() == 1) {
                returnMap.put(name, valueList.get(0));
            } else {
                returnMap.put(name, valueList.toString());
            }
        }
        return returnMap;
    }

    /**
     * 根据模型ID获取配置项信息,后端列转行 只供字典表使用
     *
     * @param ciId
     * @return
     */
    @Override
    public List<Map<String, Object>> getCiCientityList(Long ciId) {
        R<List<Map<String, Object>>> brand = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
        return brand.getData();
    }

    /**
     * 删除配置项
     *
     * @param id          配置项id
     * @param description 删除说明
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_DELETE_DATA, tripleType = TripleTypeEnum.CMDB)
    public Boolean cientityDelete(Long id, String description) {
        try {
            Map<String, Object> innerMap = CmdbCiAttrWrapper.build().feignCientityDelete(id, description);
            Object Status = innerMap.get(DeviceConstant.STATUS);
            if (!Status.equals(DeviceConstant.OK)) {
                throw new RuntimeException("用户删除信息失败!");
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new RuntimeException("用户删除信息失败!" + e.getMessage());
        }
    }

    /**
     * 批量删除配置项
     *
     * @param feignCmdbCientityBatchDelete 配置项
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_DELETE_BATCH_DATA, tripleType = TripleTypeEnum.CMDB)
    public Boolean cientityBatchDelete(FeignCmdbCientityBatchDelete feignCmdbCientityBatchDelete) {
        try {
            R<Map<String, Object>> feignResult = CmdbCiAttrWrapper.build().feignCientityBatchDelete(feignCmdbCientityBatchDelete);

            if (feignResult.getCode() == 500) {
                throw new RuntimeException(feignResult.getMsg());
            }
            Map<String, Object> innerMap = feignResult.getData();
            Object Status = innerMap.get(DeviceConstant.STATUS);
            if (!Status.equals(DeviceConstant.OK)) {
                throw new RuntimeException("用户批量删除信息失败!");
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new RuntimeException("用户批量删除信息失败!" + e.getMessage());
        }
    }

    /**
     * 单条生成打印标签
     *
     * @param cmdbCardDTO
     * @return
     */
    @Override
    public BufferedImage cards(CmdbCardDTO cmdbCardDTO) {
        FeignCmdbCientityGet feignCmdbCientityGet = new FeignCmdbCientityGet();
        feignCmdbCientityGet.setCiId(cmdbCardDTO.getCiId());
        feignCmdbCientityGet.setCiEntityId(cmdbCardDTO.getId());
        Map<String, Object> cientityDetail = this.getCientityDetail(feignCmdbCientityGet);

        String brand = String.valueOf(cientityDetail.get(CmdbAttrConstant.BRAND));
        String deviceModel = String.valueOf(cientityDetail.get(CmdbAttrConstant.DEVICE_MODEL));
        String realID = String.valueOf(cientityDetail.get(CmdbAttrConstant.REAL_ID));
        String sn = String.valueOf(cientityDetail.get(CmdbAttrConstant.SN));
        String deviceCode = String.valueOf(cientityDetail.get(CmdbAttrConstant.DEVICE_CODE));

        Object realIDObj = cientityDetail.get(CmdbAttrConstant.REAL_ID);

        if (IdevelopUtils.isCmdbBlack(realIDObj)) {
            throw new RuntimeException("设备编码为 " + deviceCode + "不存在实物ID，无法生成设备标识！");
        }

        // 标签标题
        String deviceCategoryCode = String.valueOf(cientityDetail.get(CmdbAttrConstant.DEVICE_CATEGORY_CODE));
        String drawTitle = "信息资产设备";
        if (IdevelopUtils.isCmdbNotBlack(deviceCategoryCode)) {
            Map<Object, Object> deviceClaccifyMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify());
            drawTitle = "信息" + deviceClaccifyMap.get(deviceCategoryCode);
        }

        // "设备编码：" 品牌
        String drawBrand = "品   牌：" + brand;
        if (StringUtils.equals("null", brand)) {
            drawBrand = "品   牌：";
        }
        // 型号
        String drawModel = "型   号：" + deviceModel;
        if (StringUtils.equals("null", deviceModel)) {
            drawModel = "型   号：";
        }
        // 序列号
        String drawSn = "序列号 ：" + sn;
        if (StringUtils.equals("null", sn)) {
            drawSn = "序列号 ：";
        }
        // 实物ID
        String drawRealID = "实物ID ：" + realID;
        if (StringUtils.equals("null", realID)) {
            drawRealID = "实物ID ：";
        }

        return BarCode.createQrBarImageInfo(drawTitle, drawBrand, drawModel, drawRealID, drawSn, realID);
    }


    /**
     * 批量生成打印标签
     *
     * @param cmdbCardDTOList
     * @return
     */
    @Override
    public Boolean batchCards(List<CmdbCardDTO> cmdbCardDTOList) {

        IdevelopUser user = SecureUtil.getUser();

        String zipName = "（" + user.getRegionName() + "）信通一体化打印标签-" + System.currentTimeMillis() + ".zip";

        String uuid = UuidUtils.uuid();
        OssFile ossFile = new OssFile();
        ossFile.setId(uuid);
        ossFile.setFileName(zipName);
        ossFile.setExportJson(JSON.toJSONString(cmdbCardDTOList));
        ossFile.setExportTime(new Date());
        ossFile.setStatus(1);
        ossFile.setOssType(OssFileTypeEnum.DEVICE_CARD.getValue());
        ossFileService.saveOrUpdate(ossFile);

        StringBuilder sBuilder = new StringBuilder();
        try {
            ByteArrayOutputStream zipOutStream = new ByteArrayOutputStream();

            try (ZipOutputStream zos = new ZipOutputStream(zipOutStream)) {

                for (CmdbCardDTO cmdbCardDTO : cmdbCardDTOList) {

                    FeignCmdbCientityGet feignCmdbCientityGet = new FeignCmdbCientityGet();
                    feignCmdbCientityGet.setCiId(cmdbCardDTO.getCiId());
                    feignCmdbCientityGet.setCiEntityId(cmdbCardDTO.getId());
                    Map<String, Object> cientityDetail = this.getCientityDetail(feignCmdbCientityGet);

                    String deviceCode = String.valueOf(cientityDetail.get(CmdbAttrConstant.DEVICE_CODE));
                    String brand = String.valueOf(cientityDetail.get(CmdbAttrConstant.BRAND));
                    String deviceModel = String.valueOf(cientityDetail.get(CmdbAttrConstant.DEVICE_MODEL));
                    String realID = String.valueOf(cientityDetail.get(CmdbAttrConstant.REAL_ID));

                    Object realIDObj = cientityDetail.get(CmdbAttrConstant.REAL_ID);
                    String sn = String.valueOf(cientityDetail.get(CmdbAttrConstant.SN));
                    String fullName = String.valueOf(cientityDetail.get(CmdbAttrConstant.FULL_NAME));

                    if (IdevelopUtils.isCmdbBlack(realIDObj)) {
                        sBuilder.append(deviceCode).append(",");
                        continue;
                    }

                    // 标签标题
                    String deviceCategoryCode = String.valueOf(cientityDetail.get(CmdbAttrConstant.DEVICE_CATEGORY_CODE));
                    String drawTitle = "信息资产设备";
                    if (IdevelopUtils.isCmdbNotBlack(deviceCategoryCode)) {
                        Map<Object, Object> deviceClaccifyMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify());
                        drawTitle = "信息" + deviceClaccifyMap.get(deviceCategoryCode);
                    }

                    // "设备编码：" 品牌
                    String drawBrand = "品   牌：" + brand;
                    if (StringUtils.equals("null", brand)) {
                        drawBrand = "品   牌：";
                    }
                    // 型号
                    String drawModel = "型   号：" + deviceModel;
                    if (StringUtils.equals("null", deviceModel)) {
                        drawModel = "型   号：";
                    }
                    // 序列号
                    String drawSn = "序列号 ：" + sn;
                    if (StringUtils.equals("null", sn)) {
                        drawSn = "序列号 ：";
                    }
                    // 实物ID 000110000000000000000000A
                    String drawRealID = "实物ID ：" + realID;
                    if (StringUtils.equals("null", realID)) {
                        drawRealID = "实物ID ：";
                    }

                    BufferedImage image = BarCode.createQrBarImageInfo(drawTitle, drawBrand, drawModel, drawRealID, drawSn, realID);

                    ByteArrayOutputStream imgOut = new ByteArrayOutputStream();
                    if (image == null) {
                        throw new RuntimeException("批量生成打印标签生成二维码标签失败! ");
                    }
                    ImageIO.write(image, "png", imgOut);

                    zos.putNextEntry(new ZipEntry(fullName + "(" + realID + ").png"));
                    zos.write(imgOut.toByteArray());
                    zos.closeEntry();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(zipOutStream.toByteArray());


            IdevelopFile idevelopFile = aliossTemplate.putFile(zipName, inputStream);
            String filePath = aliossTemplate.convertToCurrentModePath(idevelopFile.getLink());

            LOGGER.info("idevelopFile: {}", JSON.toJSON(idevelopFile));
            LOGGER.info("filePath: {}", filePath);

            if (sBuilder.length() > 0) {
                sBuilder.append("不存在实物ID，无法生成设备标识！");
            }
            ossFile.setOssName(idevelopFile.getOriginalName());
            ossFile.setLink(filePath);
            ossFile.setStatus(2);
            ossFile.setRemake(sBuilder.toString());
            ossFileService.saveOrUpdate(ossFile);
        } catch (Exception e) {
            ossFile.setStatus(3);
            ossFileService.saveOrUpdate(ossFile);
        }

        return Boolean.TRUE;
    }

    /**
     * 用户自主生成实物ID
     *
     * @param file
     */
    @Override
    public void importCardsByExcel(MultipartFile file) {

        InputStream inputStream = null;
        try {
            IdevelopUser user = SecureUtil.getUser();
            ImportResultDTO importResultDTO = new ImportResultDTO();
            importResultDTO.setImportStatus(0);
            importResultDTO.setImportType(ImportFileTypeEnum.CREATE_CARD.getValue());
            List<ImportResultVO> importMasterVOS = importResultService.selectImportResultPageByUser(importResultDTO);
            if (importMasterVOS.size() > 30) {
                throw new ServiceException("当前系统同步数量已超过同步限制,请稍后尝试! ");
            }
            List<Long> createUserList = importMasterVOS.stream().map(ImportResultVO::getCreateUser).collect(Collectors.toList());
            if (createUserList.contains(user.getUserId()) && !org.apache.commons.lang3.StringUtils.equals("37", user.getRegionCode())) {
                throw new ServiceException("当前用户已存在数据同步文件, 请等待上次同步文件结束后在上传文件! ");
            }
            DeviceCodeListener deviceCodeListener = new DeviceCodeListener();
            inputStream = new BufferedInputStream(file.getInputStream());
            EasyExcel.read(inputStream, DeviceCodeStencil.class, deviceCodeListener).sheet().doRead();
            List<DeviceCodeStencil> deviceCodeList = deviceCodeListener.getList();

            if (CollectionUtils.isEmpty(deviceCodeList)) {
                return;
            }

            if (deviceCodeList.size() > 1000 && !StringUtils.equals("37", user.getRegionCode())) {
                throw new ServiceException("请上传文件设备同步数量请小于1000条,便于查看同步记录! ");
            }

            String uuid = UuidUtils.uuid();
            ImportResult importResult = new ImportResult();
            importResult.setId(uuid);
            importResult.setImportUuid(uuid);
            importResult.setImportName(file.getOriginalFilename());
            importResult.setImportNumber(deviceCodeList.size());
            importResult.setImportType(ImportFileTypeEnum.CREATE_CARD.getValue());
            importResult.setImportStatus(0);

            importResultService.save(importResult);

            for (DeviceCodeStencil deviceCodeStencil : deviceCodeList) {

                String deviceCode = deviceCodeStencil.getDeviceCode();
                ImportLog importLog = new ImportLog();
                importLog.setImportUuid(uuid);
                importLog.setDeviceCode(deviceCode);

                StringBuilder sBuilder = new StringBuilder();

                CiCientitySearch cientitySearch = new CiCientitySearch();
                List<CiCientitySearchVO> entity = new ArrayList<>();
                CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).expression(Expression.EQUAL).attrValue(deviceCode).build();
                entity.add(ciCientitySearchVO);
                cientitySearch.setEntity(entity);
                cientitySearch.setQuery(new Query().setCurrent(1).setSize(10));
                cientitySearch.setFullField(Boolean.TRUE);

                List<Map<String, Object>> data = this.getCiCientityListByCondition(cientitySearch).getData();
                if (CollectionUtils.isEmpty(data)) {
                    sBuilder.append("根据设备编码未查询到设备台账, 请检查.");
                    importLog.setDeviceInfo(sBuilder.toString());
                    importLogService.save(importLog);
                    continue;
                }
                Map<String, Object> entityMap = data.get(0);

                Object realID = entityMap.get(CmdbAttrConstant.REAL_ID);
                if (IdevelopUtils.isCmdbNotBlack(realID)) {
                    sBuilder.append("该设备台账已存在实物ID, 无需生成.");
                    importLog.setDeviceInfo(sBuilder.toString());
                    importLogService.save(importLog);
                    continue;
                }
                Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                if (IdevelopUtils.isCmdbNotBlack(i6000CiId)) {
                    sBuilder.append("该设备台账存在I6000关联ID, 无需生成, 请联系项目组处理.");
                    importLog.setDeviceInfo(sBuilder.toString());
                    importLogService.save(importLog);
                    continue;
                }
                String serial = "00603" + orderNumberUtil.generateSerial();
                int sum = 0;
                for (int i = 0; i < serial.length(); i++) {
                    int digit = serial.charAt(i) - '0';
                    int weight = (i % 2 == 0) ? 3 : 1;
                    sum += digit * weight;
                }

                int check = (10 - sum % 10) % 10;
                // 006 03 000000000000000000 0 w
                String realIDStr = serial + check + "w";

                entityMap.put(CmdbAttrConstant.REAL_ID, realIDStr);

                Long id = (Long) entityMap.get(CmdbAttrConstant.ID);
                Map<Long, Map<String, Object>> map = new HashMap<>();
                map.put(id, entityMap);

                Map<String, Object> returnMap = this.cientityBatchupdate(map, TransactionActionType.UPDATE);
                Boolean committed = (Boolean) returnMap.get("committed");
                if (Objects.nonNull(committed) && committed) {
                    sBuilder.append("台账更新完成, 生成实物ID: ").append(realIDStr);
                } else {
                    sBuilder.append("台账更新失败! ");
                }
                importLog.setDeviceInfo(sBuilder.toString());
                importLogService.save(importLog);
            }
            importResult.setImportStatus(1);
            importResultService.saveOrUpdate(importResult);
        } catch (IOException e) {
            log.error("读取流失败");
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("流关闭失败");
            }
        }

    }


    /**
     * 新增枚举 配置项
     * PS: 新增方法请放在该方法上面
     *
     * @param ciId       模型id
     * @param entityMap  key为 uuid
     * @param cmdbDictCi 模型信息
     * @param actionType 操作类型
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_DICT_INSERT_DATA, tripleType = TripleTypeEnum.CMDB)
    public Map<String, Object> cientityBatchsaveDict(Long ciId, Map<String, Map<String, Object>> entityMap, CmdbDictCi cmdbDictCi, TransactionActionType actionType) {
        if (!actionType.equals(TransactionActionType.INSERT)) {
            throw new RuntimeException("请选择正确的方法！");
        }

        try {
            checkParamsAdd(ciId);

            // 属性配置
            Long selectCiId = cmdbDictCi.getSelectCiId();
            Map<Object, Object> attrMap = getAttrMap(selectCiId);

            // 构造参数
            CmdbCientityBatchsave cmdbCientityBatchsave = new CmdbCientityBatchsave();
            cmdbCientityBatchsave.setIsSimple(Boolean.FALSE);
            cmdbCientityBatchsave.setNeedCommit(Boolean.TRUE);
            List<CmdbCientityBatchsave.CmdbCientityValidate> cmdbCientityValidates = new ArrayList<>();

            for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {
                String stairKey = stairEntity.getKey();
                Map<String, Object> stairValue = stairEntity.getValue();

                CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate = new CmdbCientityBatchsave.CmdbCientityValidate();
                cmdbCientityValidate.setUuid(stairKey);
                cmdbCientityValidate.setCiId(cmdbDictCi.getCiId());
                cmdbCientityValidate.setCiIcon(cmdbDictCi.getCiIcon());
                cmdbCientityValidate.setCiName(cmdbDictCi.getCiName());
                cmdbCientityValidate.setCiLabel(cmdbDictCi.getCiLabel());

                Map<String, CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData> attrEntityDataMap = new HashMap<>();

                stairValue.entrySet().stream()
                        .filter(entity -> entity.getValue() != null && !"".equals(entity.getValue()))
                        .filter(entity -> Objects.nonNull(attrMap.get(entity.getKey())))
                        .forEach(entity -> {
                            String entryKey = entity.getKey();
                            Object entryValue = entity.getValue();

                            String attrValue = String.valueOf(attrMap.get(entryKey));
                            CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData attrEntityData = new CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData();

                            String[] split = attrValue.split("-");
                            if (Objects.nonNull(attrValue.split("-"))) {
                                String attrId = split[0];
                                String text = split[1];
                                String config = split[2];
                                List<Object> valueList = new ArrayList<>();
                                valueList.add(entryValue);
                                attrEntityData.setValueList(valueList);
                                attrEntityData.setActualValueList(valueList);
                                attrEntityData.setType(text);
                                attrEntityData.setConfig(config);
                                attrEntityDataMap.put("attr_" + attrId, attrEntityData);
                            }
                        });
                cmdbCientityValidate.setAttrEntityData(attrEntityDataMap);
                cmdbCientityValidate.setRelEntityData(new HashMap<>());

                // 设置属性大小
                cmdbCientityValidate.setMaxAttrEntityCount(9999999999L);
                cmdbCientityValidate.setMaxRelEntityCount(9999999999L);
                // 转换认证数据
                converAuthData(cmdbCientityValidate);
                // 全局属性值转换
                converGlobalAttrEntityData(cmdbCientityValidate);
                // 待商榷 需要转换或修改SDK包
                // converElementUniqueAttr(cmdbCientityValidate, hardwareBasicTreeOne);

                // 验证数据
                FeignCmdbCientityValidate cmdbCientityValidateVO = BeanUtil.copy(cmdbCientityValidate, FeignCmdbCientityValidate.class);
                R<JSONObject> validate = CmdbCiAttrWrapper.build().cientityValidate(cmdbCientityValidateVO);
                if (validate.getCode() == 500) {
                    LOGGER.error("新增枚举配置项失败: " + validate.getMsg());
                    throw new RuntimeException(validate.getMsg());
                }
                cmdbCientityValidates.add(cmdbCientityValidate);
            }
            if (!CollectionUtils.isEmpty(cmdbCientityValidates)) {
                LOGGER.info("校验新增枚举配置项数量: " + cmdbCientityValidates.size());
                cmdbCientityBatchsave.setCiEntityList(cmdbCientityValidates);
                FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO = BeanUtil.copy(cmdbCientityBatchsave, FeignCmdbCientityBatchsave.class);
                R<JSONObject> batchsave = CmdbCiAttrWrapper.build().cientityXtythBatchsave(cmdbCientityBatchsaveVO);

                if (Objects.isNull(batchsave)) {
                    throw new RuntimeException("新增枚举配置项失败!");
                }

                if (batchsave.getCode() == 500) {
                    LOGGER.error("新增枚举配置项失败: " + batchsave.getMsg());
                    throw new RuntimeException(batchsave.getMsg());
                }

                JSONObject aReturn = batchsave.getData().getJSONObject("Return");
                JSONObject ciEntityTransactionList = aReturn.getJSONObject("ciEntityTransactionList");
                Map<String, Object> innerMap = ciEntityTransactionList.getInnerMap();
                Boolean committed = aReturn.getBoolean("committed");
                if (committed) {
                    innerMap.put("Status", 200);
                }
                return innerMap;
            } else {
                LOGGER.info("新增枚举配置项数量: " + cmdbCientityValidates.size());
            }
            return new HashMap<>();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * 修改枚举 配置项
     * PS: 新增方法请放在该方法上面
     *
     * @param entityMap  key为 id  value 包括 ciId和uuid
     * @param actionType
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_DICT_UPDATE_DATA, tripleType = TripleTypeEnum.CMDB)
    public Map<String, Object> cientityBatchupdateDict(Map<Long, Map<String, Object>> entityMap, CmdbDictCi cmdbDictCi, TransactionActionType actionType) {

        if (!actionType.equals(TransactionActionType.UPDATE)) {
            throw new RuntimeException("请选择正确的方法！");
        }
        try {
            List<CmdbCientityBatchsave.CmdbCientityValidate> cmdbCientityValidates = new ArrayList<>();
            CmdbCientityBatchsave cmdbCientityBatchsave = new CmdbCientityBatchsave();
            // 构造参数
            cmdbCientityBatchsave.setIsSimple(Boolean.FALSE);
            cmdbCientityBatchsave.setNeedCommit(Boolean.TRUE);

            for (Map.Entry<Long, Map<String, Object>> stairEntity : entityMap.entrySet()) {

                Map<String, Object> stairValue = stairEntity.getValue();
                Map.Entry<String, Object> entry = stairValue.entrySet().stream().filter(map -> StringUtil.equals("ciId", map.getKey()))
                        .findFirst().orElseThrow(() -> new RuntimeException("配置项 ciId 不能为空"));
                Long ciId = Long.parseLong(String.valueOf(entry.getValue()));
                // 属性配置
                Long selectCiId = cmdbDictCi.getSelectCiId();
                Map<Object, Object> attrMap = getAttrMap(selectCiId);

                CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate = new CmdbCientityBatchsave.CmdbCientityValidate();
                cmdbCientityValidate.setId(stairEntity.getKey());
                cmdbCientityValidate.setCiId(ciId);
                cmdbCientityValidate.setCiIcon(cmdbDictCi.getCiIcon());
                cmdbCientityValidate.setCiName(cmdbDictCi.getCiName());
                cmdbCientityValidate.setCiLabel(cmdbDictCi.getCiLabel());

                Map.Entry<String, Object> entryFromUuid = stairValue.entrySet().stream().filter(map -> StringUtil.equals("uuid", map.getKey()))
                        .findFirst().orElseThrow(() -> new RuntimeException("配置项 uuid 不能为空"));
                cmdbCientityValidate.setUuid(String.valueOf(entryFromUuid.getValue()));

                Map<String, CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData> attrEntityDataMap = new HashMap<>();
                stairValue.entrySet().stream()
                        .filter(entity -> Objects.nonNull(attrMap.get(entity.getKey())))
                        .forEach(entity -> {
                            Object entryValue = entity.getValue();
                            Object attMapValue = attrMap.get(entity.getKey());

                            CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData attrEntityData = new CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData();

                            String attrValue = String.valueOf(attMapValue);
                            String[] split = attrValue.split("-");
                            if (Objects.nonNull(attrValue.split("-"))) {
                                String attrId = split[0];
                                String text = split[1];
                                String config = split[2];
                                List<Object> valueList = Lists.newArrayList(entryValue);
                                attrEntityData.setValueList(valueList);
                                attrEntityData.setActualValueList(valueList);
                                attrEntityData.setType(text);
                                attrEntityData.setConfig(config);
                                attrEntityDataMap.put("attr_" + attrId, attrEntityData);
                            }
                        });
                cmdbCientityValidate.setAttrEntityData(attrEntityDataMap);
                cmdbCientityValidate.setRelEntityData(new HashMap<>());

                cmdbCientityValidate.setMaxAttrEntityCount(Long.valueOf(attrEntityDataMap.size() + 1));
                cmdbCientityValidate.setMaxRelEntityCount(Long.valueOf(attrEntityDataMap.size()) + 1);

                checkParamsUpdate(cmdbCientityValidate.getCiId(), cmdbCientityValidate.getId(), cmdbCientityValidate.getUuid());

                // 转换认证数据
                converAuthData(cmdbCientityValidate);
                // 全局属性值转换
                converGlobalAttrEntityData(cmdbCientityValidate);
                // 待商榷 需要转换或修改SDK包
                // converElementUniqueAttr(cmdbCientityValidate, hardwareBasicTreeOne);

                FeignCmdbCientityValidate cmdbCientityValidateVO = BeanUtil.copy(cmdbCientityValidate, FeignCmdbCientityValidate.class);
                R<JSONObject> validate = CmdbCiAttrWrapper.build().cientityValidate(cmdbCientityValidateVO);
                if (validate.getCode() == 500) {
                    LOGGER.error("校验修改枚举配置项失败: " + validate.getMsg());
                    throw new RuntimeException(validate.getMsg());
                }
                JSONObject aReturn = validate.getData().getJSONObject("Return");
                Boolean hasChange = aReturn.getBoolean("hasChange");
                if (hasChange) {
                    cmdbCientityValidates.add(cmdbCientityValidate);
                }
            }
            if (!CollectionUtils.isEmpty(cmdbCientityValidates)) {
                LOGGER.info("修改枚举配置项数量: " + cmdbCientityValidates.size());
                cmdbCientityBatchsave.setCiEntityList(cmdbCientityValidates);
                FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO = BeanUtil.copy(cmdbCientityBatchsave, FeignCmdbCientityBatchsave.class);
                R<JSONObject> batchsave = CmdbCiAttrWrapper.build().cientityBatchsave(cmdbCientityBatchsaveVO);

                if (Objects.isNull(batchsave)) {
                    throw new RuntimeException("修改枚举配置项失败!");
                }

                if (batchsave.getCode() == 500) {
                    LOGGER.error("修改枚举配置项失败: " + batchsave.getMsg());
                    throw new RuntimeException(batchsave.getMsg());
                }
                JSONObject aReturn = batchsave.getData().getJSONObject("Return");
                Map<String, Object> innerMap = aReturn.getInnerMap();
                Boolean committed = aReturn.getBoolean("committed");
                if (committed) {
                    innerMap.put("Status", 200);
                }
                return innerMap;
            } else {
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("Status", 200);
                innerMap.put("Mag", "修改枚举配置项台账!");
                return innerMap;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 新增资产台账
     *
     * @param ciId       模型ID
     * @param entityMap
     * @param actionType
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_INSERT_DATA, tripleType = TripleTypeEnum.CMDB)
    public Map<String, Object> cientityBatchsave(Long ciId, Map<String, Map<String, Object>> entityMap, TransactionActionType actionType) throws RuntimeException {
        if (!actionType.equals(TransactionActionType.INSERT)) {
            throw new RuntimeException("请选择正确的方法！");
        }
        try {
            checkParamsAdd(ciId);
            HardwareBasicTree hardwareBasicTreeOne = getHardwareBasicTree(ciId);
            // 获取单位
            Map<Long, Dept> regionDetailMap = HardwareBasicWrapper.build().getRegionDetail(new Dept());
            // 获取模型所有属性
            Map<Object, Object> attrMap = getAttrMap(ciId);
            // 构造参数
            CmdbCientityBatchsave cmdbCientityBatchsave = new CmdbCientityBatchsave();
            cmdbCientityBatchsave.setIsSimple(Boolean.FALSE);
            cmdbCientityBatchsave.setNeedCommit(Boolean.TRUE);
            List<CmdbCientityBatchsave.CmdbCientityValidate> cmdbCientityValidates = new ArrayList<>();

            for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {
                String stairKey = stairEntity.getKey();
                Map<String, Object> stairValue = stairEntity.getValue();

                // *****默认值填充********
                defaultFill(stairValue, regionDetailMap, actionType);

                CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate = new CmdbCientityBatchsave.CmdbCientityValidate();
                cmdbCientityValidate.setUuid(stairKey);
                cmdbCientityValidate.setCiId(hardwareBasicTreeOne.getCiId());
                cmdbCientityValidate.setCiIcon(hardwareBasicTreeOne.getCiIcon());
                cmdbCientityValidate.setCiName(hardwareBasicTreeOne.getCiName());
                cmdbCientityValidate.setCiLabel(hardwareBasicTreeOne.getCiLabel());

                Map<String, CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData> attrEntityDataMap = new HashMap<>();

                stairValue.entrySet().stream()
                        .filter(entity -> entity.getValue() != null && !"".equals(entity.getValue()))
                        .filter(entity -> Objects.nonNull(attrMap.get(entity.getKey())))
                        .forEach(entity -> {
                            String entryKey = entity.getKey();
                            String entryValue = entity.getValue().toString();

                            String attrValue = String.valueOf(attrMap.get(entryKey));
                            CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData attrEntityData = new CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData();

                            String[] split = attrValue.split("-");
                            if (Objects.nonNull(attrValue.split("-"))) {
                                String attrId = split[0];
                                String text = split[1];
                                String config = split[2];
                                List<Object> valueList = new ArrayList<>();
                                valueList.add(entryValue);
                                attrEntityData.setValueList(valueList);
                                attrEntityData.setActualValueList(valueList);
                                attrEntityData.setType(text);
                                attrEntityData.setConfig(config);
                                attrEntityDataMap.put("attr_" + attrId, attrEntityData);
                            }
                        });
                cmdbCientityValidate.setAttrEntityData(attrEntityDataMap);
                cmdbCientityValidate.setRelEntityData(new HashMap<>());

                // 设置属性大小
                cmdbCientityValidate.setMaxAttrEntityCount(9999999999L);
                cmdbCientityValidate.setMaxRelEntityCount(9999999999L);
                // 转换认证数据
                converAuthData(cmdbCientityValidate);
                // 全局属性值转换
                converGlobalAttrEntityData(cmdbCientityValidate);
                // 待商榷 需要转换或修改SDK包
                converElementUniqueAttr(cmdbCientityValidate, hardwareBasicTreeOne);

                // 验证数据
                FeignCmdbCientityValidate cmdbCientityValidateVO = BeanUtil.copy(cmdbCientityValidate, FeignCmdbCientityValidate.class);
                R<JSONObject> validate = CmdbCiAttrWrapper.build().cientityValidate(cmdbCientityValidateVO);
                if (validate.getCode() == 500) {
                    LOGGER.error("检验保存资产台账失败: " + validate.getMsg());
                    throw new RuntimeException(validate.getMsg());
                }
                cmdbCientityValidates.add(cmdbCientityValidate);
            }
            if (!CollectionUtils.isEmpty(cmdbCientityValidates)) {
                LOGGER.info("新增资产台账数量: " + cmdbCientityValidates.size());
                cmdbCientityBatchsave.setCiEntityList(cmdbCientityValidates);
                FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO = BeanUtil.copy(cmdbCientityBatchsave, FeignCmdbCientityBatchsave.class);
                R<JSONObject> batchsave = CmdbCiAttrWrapper.build().cientityXtythBatchsave(cmdbCientityBatchsaveVO);

                if (Objects.isNull(batchsave)) {
                    throw new RuntimeException("保存资产台账失败!");
                }

                if (batchsave.getCode() == 500) {
                    LOGGER.error("保存资产台账失败: " + batchsave.getMsg());
                    throw new RuntimeException(batchsave.getMsg());
                }
                JSONObject aReturn = batchsave.getData().getJSONObject("Return");
                JSONObject ciEntityTransactionList = aReturn.getJSONObject("ciEntityTransactionList");
                Map<String, Object> innerMap = ciEntityTransactionList.getInnerMap();
                Boolean committed = aReturn.getBoolean("committed");
                if (committed) {
                    innerMap.put("Status", 200);
                }
                return innerMap;
            } else {
                LOGGER.info("新增资产台账数量: " + cmdbCientityValidates.size());
            }
            return new HashMap<>();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * 修改资产台账
     *
     * @param entityMap  Key 为 id Value 必须包括 uuid ciId
     * @param actionType
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_UPDATE_DATA, tripleType = TripleTypeEnum.CMDB)
    public Map<String, Object> cientityBatchupdate(Map<Long, Map<String, Object>> entityMap, TransactionActionType actionType) {
        if (!actionType.equals(TransactionActionType.UPDATE)) {
            throw new RuntimeException("请选择正确的方法！");
        }
        try {
            CmdbCientityBatchsave cmdbCientityBatchsave = new CmdbCientityBatchsave();
            // 构造参数
            cmdbCientityBatchsave.setIsSimple(Boolean.FALSE);
            cmdbCientityBatchsave.setNeedCommit(Boolean.TRUE);

            // 获取单位
            Map<Long, Dept> regionDetailMap = HardwareBasicWrapper.build().getRegionDetail(new Dept());

            List<CmdbCientityBatchsave.CmdbCientityValidate> cmdbCientityValidates = entityMap.entrySet().stream().map(stairEntity -> {
                CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate = new CmdbCientityBatchsave.CmdbCientityValidate();
                Long id = stairEntity.getKey();
                CiCientitySearch cientitySearch = new CiCientitySearch();
                cientitySearch.setFilterCiEntityId(id);
                cientitySearch.setFullField(Boolean.TRUE);
                Query query = new Query();
                query.setCurrent(1);
                query.setSize(5);
                cientitySearch.setQuery(query);
                FeignCiCientity ciCientityListByCondition = this.getCiCientityListByCondition(cientitySearch);
                Map<String, Object> cmdbOne = ciCientityListByCondition.getData().get(0);

                if (CollectionUtils.isEmpty(cmdbOne)) throw new RuntimeException("未查询到该台账, 无法进行修改.");
                Map<String, Object> stairValue = stairEntity.getValue();
                cmdbOne.putAll(stairValue);

                // *****默认值填充********
                defaultFill(cmdbOne, regionDetailMap, actionType);

                Map.Entry<String, Object> entry = stairValue.entrySet().stream().filter(map -> StringUtil.equals("ciId", map.getKey()))
                        .findFirst().orElseThrow(() -> new RuntimeException("配置项 ciId 不能为空."));
                Long ciId = Long.parseLong(String.valueOf(entry.getValue()));
                HardwareBasicTree hardwareBasicTreeOne = getHardwareBasicTree(ciId);
                Map<Object, Object> attrMap = getAttrMap(ciId);

                cmdbCientityValidate.setId(id);
                cmdbCientityValidate.setCiId(ciId);
                cmdbCientityValidate.setCiIcon(hardwareBasicTreeOne.getCiIcon());
                cmdbCientityValidate.setCiName(hardwareBasicTreeOne.getCiName());
                cmdbCientityValidate.setCiLabel(hardwareBasicTreeOne.getCiLabel());

                Map.Entry<String, Object> entryFromUuid = stairValue.entrySet().stream().filter(map -> StringUtil.equals("uuid", map.getKey()))
                        .findFirst().orElseThrow(() -> new RuntimeException("配置项 uuid 不能为空"));
                cmdbCientityValidate.setUuid(String.valueOf(entryFromUuid.getValue()));

                Map<String, CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData> attrEntityDataMap = new HashMap<>();
                cmdbOne.entrySet().stream()
                        .filter(entity -> Objects.nonNull(attrMap.get(entity.getKey())))
                        .forEach(entity -> {
                            String entryValue = "";
                            if (entity.getValue() != null && !"".equals(entity.getValue())) {
                                entryValue = entity.getValue().toString();
                            }
                            Object attMapValue = attrMap.get(entity.getKey());

                            CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData attrEntityData = new CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData();

                            String attrValue = String.valueOf(attMapValue);
                            String[] split = attrValue.split("-");
                            if (Objects.nonNull(attrValue.split("-"))) {
                                String attrId = split[0];
                                String text = split[1];
                                String config = split[2];
                                List<Object> valueList = new ArrayList<>();
                                valueList.add(entryValue);
                                attrEntityData.setValueList(valueList);
                                attrEntityData.setActualValueList(valueList);
                                attrEntityData.setType(text);
                                attrEntityData.setConfig(config);
                                attrEntityDataMap.put("attr_" + attrId, attrEntityData);
                            }
                        });
                cmdbCientityValidate.setAttrEntityData(attrEntityDataMap);
                cmdbCientityValidate.setRelEntityData(new HashMap<>());

                cmdbCientityValidate.setMaxAttrEntityCount((long) (attrEntityDataMap.size() + 1));
                cmdbCientityValidate.setMaxRelEntityCount((long) attrEntityDataMap.size() + 1);

                checkParamsUpdate(cmdbCientityValidate.getCiId(), cmdbCientityValidate.getId(), cmdbCientityValidate.getUuid());

                // 转换认证数据
                converAuthData(cmdbCientityValidate);
                // 全局属性值转换
                converGlobalAttrEntityData(cmdbCientityValidate);
                // 待商榷 需要转换或修改SDK包
                converElementUniqueAttr(cmdbCientityValidate, hardwareBasicTreeOne);

                FeignCmdbCientityValidate cmdbCientityValidateVO = Convert.convert(FeignCmdbCientityValidate.class, cmdbCientityValidate);
                R<JSONObject> validate = CmdbCiAttrWrapper.build().cientityValidate(cmdbCientityValidateVO);
                if (validate.getCode() == 500) {
                    LOGGER.info(id + ", 检验修改资产台账失败: " + validate.getMsg());
                    throw new RuntimeException(validate.getMsg());
                }
                LOGGER.info(id + ", 检验修改资产台账成功");
                JSONObject aReturn = validate.getData().getJSONObject("Return");
                Boolean hasChange = aReturn.getBoolean("hasChange");
                if (hasChange) {
                    return cmdbCientityValidate;
                }
                LOGGER.info(id + ", 检验修改资产台账失败不新增!");
                CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate1 = new CmdbCientityBatchsave.CmdbCientityValidate();
                cmdbCientityValidate1.setUuid("检验修改资产台账失败");
                return cmdbCientityValidate1;
            }).collect(Collectors.toList());

            List<CmdbCientityBatchsave.CmdbCientityValidate> cmdbCientityValidates1 = cmdbCientityValidates.stream()
                    .filter(item -> !StringUtils.equals("检验修改资产台账失败", item.getUuid()))
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(cmdbCientityValidates1)) {

                LOGGER.info("修改资产台账数量: " + cmdbCientityValidates1.size());
                LOGGER.info("修改资产台账参数: " + JSONObject.toJSON(cmdbCientityValidates1));
                cmdbCientityBatchsave.setCiEntityList(cmdbCientityValidates1);
                FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO = BeanUtil.copy(cmdbCientityBatchsave, FeignCmdbCientityBatchsave.class);
                R<JSONObject> batchsave = CmdbCiAttrWrapper.build().cientityBatchsave(cmdbCientityBatchsaveVO);

                if (Objects.isNull(batchsave)) {
                    throw new RuntimeException("修改资产台账失败!");
                }

                if (batchsave.getCode() == 500) {
                    LOGGER.info("修改资产台账失败: " + batchsave.getMsg());
                    throw new RuntimeException(batchsave.getMsg());
                }
                JSONObject aReturn = batchsave.getData().getJSONObject("Return");
                Map<String, Object> innerMap = aReturn.getInnerMap();
                Boolean committed = aReturn.getBoolean("committed");
                if (committed) {
                    innerMap.put("Status", 200);
                }
                return innerMap;
            } else {
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("Status", 200);
                innerMap.put("Mag", "不需要修改台账!");
                return innerMap;
            }

        } catch (Exception e) {
            System.out.println("修改资产台账失败: " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改资产台账
     *
     * @param entityMap  Key 为 id Value 必须包括 uuid ciId
     * @param actionType
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.CMDB_UPDATE_DATA, tripleType = TripleTypeEnum.CMDB)
    public Map<String, Object> cientityBatchupdate1(Map<Long, Map<String, Object>> entityMap, TransactionActionType actionType) {
        if (!actionType.equals(TransactionActionType.UPDATE)) {
            throw new RuntimeException("请选择正确的方法！");
        }
        try {
            List<CmdbCientityBatchsave.CmdbCientityValidate> cmdbCientityValidates = new ArrayList<>();
            CmdbCientityBatchsave cmdbCientityBatchsave = new CmdbCientityBatchsave();
            // 构造参数
            cmdbCientityBatchsave.setIsSimple(Boolean.FALSE);
            cmdbCientityBatchsave.setNeedCommit(Boolean.TRUE);

            // 获取单位
            // Map<Long, Dept> regionDetailMap = HardwareBasicWrapper.build().getRegionDetail(new Dept());

            for (Map.Entry<Long, Map<String, Object>> stairEntity : entityMap.entrySet()) {

                Long id = stairEntity.getKey();
                CiCientitySearch cientitySearch = new CiCientitySearch();
                cientitySearch.setFilterCiEntityId(id);
                cientitySearch.setFullField(Boolean.TRUE);
                Query query = new Query();
                query.setCurrent(1);
                query.setSize(5);
                cientitySearch.setQuery(query);
                FeignCiCientity ciCientityListByCondition = this.getCiCientityListByCondition(cientitySearch);
                Map<String, Object> cmdbOne = ciCientityListByCondition.getData().get(0);

                if (CollectionUtils.isEmpty(cmdbOne)) throw new RuntimeException("未查询到该台账, 无法进行修改.");
                Map<String, Object> stairValue = stairEntity.getValue();
                stairValue.put(CmdbAttrConstant.AREA, cmdbOne.get(CmdbAttrConstant.AREA));
                stairValue.put(CmdbAttrConstant.DEVICE_CODE, cmdbOne.get(CmdbAttrConstant.DEVICE_CODE));
                // stairValue.put(CmdbAttrConstant.REAL_AREA, cmdbOne.get(CmdbAttrConstant.REAL_AREA));
                // cmdbOne.putAll(stairValue);

                // *****默认值填充********
                // defaultFill(stairValue, regionDetailMap, actionType);

                Map.Entry<String, Object> entry = stairValue.entrySet().stream().filter(map -> StringUtil.equals("ciId", map.getKey()))
                        .findFirst().orElseThrow(() -> new RuntimeException("配置项 ciId 不能为空."));
                Long ciId = Long.parseLong(String.valueOf(entry.getValue()));
                HardwareBasicTree hardwareBasicTreeOne = getHardwareBasicTree(ciId);
                Map<Object, Object> attrMap = getAttrMap(ciId);

                CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate = new CmdbCientityBatchsave.CmdbCientityValidate();
                cmdbCientityValidate.setId(id);
                cmdbCientityValidate.setCiId(ciId);
                cmdbCientityValidate.setCiIcon(hardwareBasicTreeOne.getCiIcon());
                cmdbCientityValidate.setCiName(hardwareBasicTreeOne.getCiName());
                cmdbCientityValidate.setCiLabel(hardwareBasicTreeOne.getCiLabel());

                Map.Entry<String, Object> entryFromUuid = stairValue.entrySet().stream().filter(map -> StringUtil.equals("uuid", map.getKey()))
                        .findFirst().orElseThrow(() -> new RuntimeException("配置项 uuid 不能为空"));
                cmdbCientityValidate.setUuid(String.valueOf(entryFromUuid.getValue()));

                Map<String, CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData> attrEntityDataMap = new HashMap<>();
                stairValue.entrySet().stream()
                        .filter(entity -> Objects.nonNull(attrMap.get(entity.getKey())))
                        .forEach(entity -> {
                            Object entryValue = entity.getValue();
                            Object attMapValue = attrMap.get(entity.getKey());

                            CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData attrEntityData = new CmdbCientityBatchsave.CmdbCientityValidate.AttrEntityData();

                            String attrValue = String.valueOf(attMapValue);
                            String[] split = attrValue.split("-");
                            if (Objects.nonNull(attrValue.split("-"))) {
                                String attrId = split[0];
                                String text = split[1];
                                String config = split[2];
                                List<Object> valueList = Lists.newArrayList(entryValue);
                                attrEntityData.setValueList(valueList);
                                attrEntityData.setActualValueList(valueList);
                                attrEntityData.setType(text);
                                attrEntityData.setConfig(config);
                                attrEntityDataMap.put("attr_" + attrId, attrEntityData);
                            }
                        });
                cmdbCientityValidate.setAttrEntityData(attrEntityDataMap);
                cmdbCientityValidate.setRelEntityData(new HashMap<>());

                cmdbCientityValidate.setMaxAttrEntityCount(Long.valueOf(attrEntityDataMap.size() + 1));
                cmdbCientityValidate.setMaxRelEntityCount(Long.valueOf(attrEntityDataMap.size()) + 1);

                checkParamsUpdate(cmdbCientityValidate.getCiId(), cmdbCientityValidate.getId(), cmdbCientityValidate.getUuid());

                // 转换认证数据
                converAuthData(cmdbCientityValidate);
                // 全局属性值转换
                converGlobalAttrEntityData(cmdbCientityValidate);
                // 待商榷 需要转换或修改SDK包
                converElementUniqueAttr(cmdbCientityValidate, hardwareBasicTreeOne);

                FeignCmdbCientityValidate cmdbCientityValidateVO = BeanUtil.copy(cmdbCientityValidate, FeignCmdbCientityValidate.class);
                R<JSONObject> validate = CmdbCiAttrWrapper.build().cientityValidate(cmdbCientityValidateVO);
                if (validate.getCode() == 500) {
                    LOGGER.info("检验修改资产台账失败: " + validate.getMsg());
                    throw new RuntimeException(validate.getMsg());
                }
                LOGGER.info("检验修改资产台账成功");
                JSONObject aReturn = validate.getData().getJSONObject("Return");
                Boolean hasChange = aReturn.getBoolean("hasChange");
                if (hasChange) {
                    cmdbCientityValidates.add(cmdbCientityValidate);
                }
            }
            if (!CollectionUtils.isEmpty(cmdbCientityValidates)) {
                LOGGER.info("修改资产台账数量: " + cmdbCientityValidates.size());
                LOGGER.info("修改资产台账参数: " + JSONObject.toJSON(cmdbCientityValidates));
                cmdbCientityBatchsave.setCiEntityList(cmdbCientityValidates);
                FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO = BeanUtil.copy(cmdbCientityBatchsave, FeignCmdbCientityBatchsave.class);
                R<JSONObject> batchsave = CmdbCiAttrWrapper.build().cientityBatchsave(cmdbCientityBatchsaveVO);

                if (Objects.isNull(batchsave)) {
                    throw new RuntimeException("修改资产台账失败!");
                }

                if (batchsave.getCode() == 500) {
                    LOGGER.info("修改资产台账失败: " + batchsave.getMsg());
                    throw new RuntimeException(batchsave.getMsg());
                }
                JSONObject aReturn = batchsave.getData().getJSONObject("Return");
                Map<String, Object> innerMap = aReturn.getInnerMap();
                Boolean committed = aReturn.getBoolean("committed");
                if (committed) {
                    innerMap.put("Status", 200);
                }
                return innerMap;
            } else {
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("Status", 200);
                innerMap.put("Mag", "不需要修改台账!");
                return innerMap;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 新增/修改默认值填充
     *
     * @param stairValue
     */
    private void defaultFill(Map<String, Object> stairValue, Map<Long, Dept> regionDetailMap, TransactionActionType actionType) {
        Object area = stairValue.get(CmdbAttrConstant.AREA);
        stairValue.put(CmdbAttrConstant.MAINTENANCE_COUNTRY, cmdbCientityProperties.getCountryArea1());
        if (actionType.equals(TransactionActionType.INSERT)) {
            // 实际区域
            stairValue.put(CmdbAttrConstant.REAL_AREA, area);
            stairValue.put(CmdbAttrConstant.IS_RESERVE, cmdbCientityProperties.getYesNo());
        }

        // if (actionType.equals(TransactionActionType.UPDATE)) {
        // 	// cmdb更新时间
        // 	stairValue.put(CmdbAttrConstant.CMDB_UPDATE_TIME, LocalDate.now());
        // }
        // 实际区域
        if (stairValue.containsKey(CmdbAttrConstant.RECEIVE_UNIT_CODE)) {
            String receiveUnitCode = stairValue.get(CmdbAttrConstant.RECEIVE_UNIT_CODE).toString();
            Dept dept = regionDetailMap.get(Long.parseLong(receiveUnitCode));
            if (Objects.nonNull(dept)) {
                stairValue.put(CmdbAttrConstant.REAL_AREA, dept.getRegionCode());
            }
        }

        // 产权单位
        if (!stairValue.containsKey(CmdbAttrConstant.OWNER_UNIT)) {
            Map<String, String> deptMap = regionDetailMap.values().stream().collect(Collectors.toMap(Dept::getRegionCode, Dept::getFullName));
            String fullName = deptMap.get(String.valueOf(area));
            if (StringUtils.isNotEmpty(fullName)) {
                stairValue.put(CmdbAttrConstant.OWNER_UNIT, fullName);
            }
        }

        // 投运年限
        if (!stairValue.containsKey(CmdbAttrConstant.USE_AGE)) {
            stairValue.put(CmdbAttrConstant.USE_AGE, 1);
        }

        // 是否治理
        if (!stairValue.containsKey(CmdbAttrConstant.IS_GOVERN)) {
            stairValue.put(CmdbAttrConstant.IS_GOVERN, cmdbCientityProperties.getGovernYes());
        }

        // 是否信创设备
        if (stairValue.containsKey(CmdbAttrConstant.DEVICE_CATEGORY_CODE) && !stairValue.containsKey(CmdbAttrConstant.IS_IT_AI_CODE)) {
            Object deviceCategory = stairValue.get(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
            if (deviceCategory.equals(cmdbCientityProperties.getT101()) || deviceCategory.equals(cmdbCientityProperties.getT105())) {
                stairValue.put(CmdbAttrConstant.IS_IT_AI_CODE, cmdbCientityProperties.getNoNo());
            }
        }

    }

    /**
     * 校验参数
     *
     * @param ciId
     */
    private void checkParamsAdd(Long ciId) {
        if (Objects.isNull(ciId)) {
            throw new RuntimeException("ciId 不能为空");
        }
    }

    /**
     * 校验参数
     *
     * @param ciId
     * @param id
     * @param uuid
     */
    private void checkParamsUpdate(Long ciId, Long id, String uuid) {
        if (Objects.isNull(ciId)) {
            throw new RuntimeException("配置项 ciId 不能为空");
        }
        if (Objects.isNull(id)) {
            throw new RuntimeException("配置项 id 为空");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("配置项 uuid 为空");
        }
    }

    /**
     * 获取模型所有属性
     *
     * @param ciId
     * @return
     */
    private Map<Object, Object> getAttrMap(Long ciId) {
        return cmdbCiAttrService.selectCiAttrMap(ciId, AttrMappingType.ALL);
    }


    /**
     * 获取模型数据
     *
     * @param ciId
     * @return
     */
    private HardwareBasicTree getHardwareBasicTree(Long ciId) {
        HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
        hardwareBasicTree.setCiId(ciId);
        QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);
        HardwareBasicTree hardwareBasicTreeOne = iHardwareBasicTreeService.getOne(queryWrapper);
        return hardwareBasicTreeOne;
    }

    /**
     * 转换认证数据
     *
     * @param cmdbCientityValidate
     */
    private static void converAuthData(CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate) {
        CmdbCientityBatchsave.CmdbCientityValidate.AuthData authData = new CmdbCientityBatchsave.CmdbCientityValidate.AuthData();
        authData.setCientityinsert(Boolean.TRUE);
        authData.setCimanage(Boolean.TRUE);
        authData.setCientityupdate(Boolean.TRUE);
        authData.setTransactionmanage(Boolean.TRUE);
        cmdbCientityValidate.setAuthData(authData);
    }

    /**
     * 待商榷 需要转换或修改SDK包
     *
     * @param cmdbCientityValidate
     * @param hardwareBasicTreeOne
     */
    private static void converElementUniqueAttr(CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate, HardwareBasicTree hardwareBasicTreeOne) {
        log.info("待商榷 需要转换或修改SDK包");
    }


    /**
     * 全局属性值转换
     *
     * @param cmdbCientityValidate
     */
    private static void converGlobalAttrEntityData(CmdbCientityBatchsave.CmdbCientityValidate cmdbCientityValidate) {
        Map<String, CmdbCientityBatchsave.CmdbCientityValidate.GlobalAttrEntityData> globalAttrEntityDataMap = new HashMap<>();
        CmdbCientityBatchsave.CmdbCientityValidate.GlobalAttrEntityData globalAttrEntityData = new CmdbCientityBatchsave.CmdbCientityValidate.GlobalAttrEntityData();
        List<CmdbCientityBatchsave.CmdbCientityValidate.GlobalAttrEntityDataValue> globalAttrEntityDataValues = new ArrayList<>();
        CmdbCientityBatchsave.CmdbCientityValidate.GlobalAttrEntityDataValue globalAttrEntityDataValue = new CmdbCientityBatchsave.CmdbCientityValidate.GlobalAttrEntityDataValue();
        globalAttrEntityDataValue.setAttrId(979768512987136L);
        globalAttrEntityDataValue.setValue("DEV");
        globalAttrEntityDataValue.setId(651293706166275L);
        globalAttrEntityDataValue.setSort(1);
        globalAttrEntityDataValues.add(globalAttrEntityDataValue);
        globalAttrEntityData.setValueList(globalAttrEntityDataValues);
        globalAttrEntityDataMap.put(GLOBAL_DATA_ID, globalAttrEntityData);
        cmdbCientityValidate.setGlobalAttrEntityData(globalAttrEntityDataMap);
    }

    /**
     * 获取查询cmdb列表
     *
     * @param entity
     * @param query
     * @param ciId
     * @return
     */
    @NotNull
    private R<FeignCiCientity> getCiCientityList(List<CiCientitySearchVO> entity, Query query, Long ciId) {
        FeignCmdbCientitySearch cientitySearch = getFeignCmdbCientitySearch(entity, query, ciId);
        R<FeignCiCientity> ciCientityListPage = CmdbCiAttrWrapper.build().getCiCientityListPage(cientitySearch);
        if (ciCientityListPage.getCode() == 500) {
            throw new RuntimeException(ciCientityListPage.getMsg());
        }
        return ciCientityListPage;
    }

    /**
     * 获取查询cmdb列表 根据不同的条件处理 获取数据;
     *
     * @param cientitySearch
     * @param ciId
     * @return
     */
    @NotNull
    private R<FeignCiCientity> getCiCientityListByIdList(CiCientitySearch cientitySearch, Long ciId) {
        FeignCmdbCientitySearch feignCmdbCientitySearch = getFeignCmdbCientitySearch(cientitySearch.getEntity(), cientitySearch.getQuery(), ciId);
        feignCmdbCientitySearch.setIdList(cientitySearch.getIdList());
        feignCmdbCientitySearch.setFilterCiEntityId(cientitySearch.getFilterCiEntityId());
        feignCmdbCientitySearch.setFilterCiId(cientitySearch.getFilterCiId());

        // 需要显示的字段列表
        feignCmdbCientitySearch.setShowAttrRelList(cientitySearch.getShowAttrRelList());
        // 排序规则
        feignCmdbCientitySearch.setSortConfig(cientitySearch.getSortConfig());
        // 请求CMDB
        R<FeignCiCientity> ciCientityListPage = CmdbCiAttrWrapper.build().getCiCientityListPage(feignCmdbCientitySearch);
        if (ciCientityListPage.getCode() == 500) {
            throw new RuntimeException(ciCientityListPage.getMsg());
        }
        return ciCientityListPage;
    }

    @NotNull
    private FeignCmdbCientitySearch getFeignCmdbCientitySearch(List<CiCientitySearchVO> entity, Query query, Long ciId) {
        FeignCmdbCientitySearch cientitySearch = new FeignCmdbCientitySearch();
        cientitySearch.setCiId(ciId);
        cientitySearch.setCurrentPage(query.getCurrent());
        cientitySearch.setPageSize(query.getSize());
        cientitySearch.setMode(ModeType.PAGE.getValue());
        cientitySearch.setNeedAction(Boolean.TRUE);
        cientitySearch.setNeedActionType(Boolean.FALSE);
        cientitySearch.setNeedCheck(Boolean.TRUE);
        cientitySearch.setNeedExpand(Boolean.FALSE);

        // 增加非空校验;
        if (CollectionUtils.isEmpty(entity)) {
            List<FeignCmdbCientitySearch.CiEntitySearchAttr> ciEntitySearchAttrs = new ArrayList<>();
            cientitySearch.setAttrFilterList(ciEntitySearchAttrs);
            return cientitySearch;
        }
        Map<String, CmdbCiAttr> attrMap = cmdbCiAttrService.selectCiAttr(ciId);
        List<FeignCmdbCientitySearch.CiEntitySearchAttr> ciEntitySearchAttrs = entity.stream()
                .filter(vo -> vo.getAttrValue() != null && !"".equals(vo.getAttrValue())
                        || (vo.getExpression().equals(Expression.ISNULL))
                        || (vo.getExpression().equals(Expression.ISNOTNULL)))
                .filter(vo -> Objects.nonNull(attrMap.get(vo.getAttrName())))
                .map(vo -> {
                    FeignCmdbCientitySearch.CiEntitySearchAttr ciEntitySearchAttr = new FeignCmdbCientitySearch.CiEntitySearchAttr();
                    CmdbCiAttr cmdbCiAttr = attrMap.get(vo.getAttrName());
                    ciEntitySearchAttr.setAttrId(cmdbCiAttr.getAttrId());
                    String attrType = cmdbCiAttr.getAttrType();
                    ciEntitySearchAttr.setType(cmdbCiAttr.getAttrType());
                    Object attrValue = vo.getAttrValue();
                    if (StringUtils.equals(TYPE_SELECT, attrType) && attrValue.toString().contains(",")) {
                        List<String> collect = Arrays.stream(attrValue.toString().split(",")).collect(Collectors.toList());
                        ciEntitySearchAttr.setValueList(collect);
                    } else if ((vo.getBatch() && StringUtils.equals(TYPE_TEXT, attrType)) && attrValue.toString().contains("--")) {
                        List<String> collect = Arrays.stream(attrValue.toString().split("--")).collect(Collectors.toList());
                        ciEntitySearchAttr.setValueList(collect);
                    } else {
                        ciEntitySearchAttr.setValueList(Lists.newArrayList(String.valueOf(attrValue)));
                    }
                    ciEntitySearchAttr.setExpression(vo.getExpression().getExpression());
                    return ciEntitySearchAttr;
                }).collect(Collectors.toList());
        cientitySearch.setAttrFilterList(ciEntitySearchAttrs);
        return cientitySearch;
    }


    // public ResponseEntity<JSONObject> cientityValidate(CmdbCientityValidate cientityValidate) {
    // 	CmdbUser cmdbUser = cmdbApiUserProperties.getCmdbUser();
    // 	ResponseEntity<JSONObject> execute = cmdbServiceDispatcher.execute(ApiUrlEnum.CMDB_CIENTITY_VALIDATE, cmdbUser, cientityValidate);
    // 	return execute;
    // }

}

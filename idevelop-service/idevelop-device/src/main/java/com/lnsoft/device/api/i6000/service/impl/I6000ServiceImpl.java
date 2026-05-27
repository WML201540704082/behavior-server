package com.lnsoft.device.api.i6000.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.common.tool.CommonUtil;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.api.asset.service.IResourceRoomService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.i6000.dto.*;
import com.lnsoft.device.api.i6000.entity.*;
import com.lnsoft.device.api.i6000.mapper.I6000DeptMapper;
import com.lnsoft.device.api.i6000.response.I6000ResultResp;
import com.lnsoft.device.api.i6000.service.II6000CabinetService;
import com.lnsoft.device.api.i6000.service.II6000CmdbMappingService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.vo.I6000CmdbMappingVO;
import com.lnsoft.device.api.i6000.vo.I6000DetailVO;
import com.lnsoft.device.api.i6000.wrapper.DeptWrapper;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.config.CmdbI6000Configuration;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.ErpConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.dto.I6000SrynDTO;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.eums.TripleApiLogValueEnum;
import com.lnsoft.device.eums.TripleTypeEnum;
import com.lnsoft.device.props.*;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import com.sgcc.nrxt.i6000.portal.util.SignHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.lnsoft.device.constant.I6000Constant.*;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/25 15:47
 * @Description: II6000ServiceImpl
 */

@Service
@AllArgsConstructor
public class I6000ServiceImpl implements II6000Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(I6000ServiceImpl.class);

    private I6000TokenProperties i6000Properties;
    private RedisUtil redisUtil;
    private RestTemplate restTemplate;
    private II6000CmdbMappingService mappingService;
    private ThirdProperties thirdProperties;
    private IResourceRoomService resourceRoomService;
    private IWarehouseService warehouseService;
    private ICmdbService cmdbService;
    private IHardwareBasicTreeService hardwareBasicTreeService;
    private IProjectManagerDetailService projectManagerDetailService;
    private II6000CabinetService i6000CabinetService;
    private CmdbCientityProperties cmdbCientityProperties;
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private I6000DeptMapper i6000DeptMapper;
    private I6000CientityProperties i6000CientityProperties;


    /**
     * 生成I6000认证信息
     * 将AccessToken拼接当前的时间随机数生成一个字符串，
     * 用publicKey公钥对字符串进行sm2加密生成密钥串signData，
     * 平台网关用私钥对signData进行解密并比对解密后的AccessToken
     *
     * @param accessToken
     * @param publicKey
     * @return
     */
    @Override
    public String createAuthInfo(String accessToken, String publicKey) {
        try {
            Object signDataObject = redisUtil.get(GENERATE_I6000_SIGN_DATA);
            if (Objects.nonNull(signDataObject)) {
                return String.valueOf(signDataObject);
            }

            if (StringUtils.isEmpty(accessToken) && StringUtils.isEmpty(publicKey)) {
                accessToken = i6000Properties.getAccessToken();
                publicKey = i6000Properties.getPublicKey();
            }
            if ((StringUtils.isNotEmpty(accessToken) && StringUtils.isEmpty(publicKey)) ||
                    (StringUtils.isEmpty(accessToken) && StringUtils.isNotEmpty(publicKey))) {
                throw new RuntimeException("accessToken 和 publicKey 不能只传一个");
            }
            String signData = SignHelper.signData(accessToken, publicKey);

            redisUtil.set(GENERATE_I6000_SIGN_DATA, signData, REDIS_TIME);

            return signData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置类型分类查询接口
     *
     * @param ciTypeId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCiType(String ciTypeId) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/cm/" + ciTypeId);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();
            List<Map<String, Object>> ciTypeList = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
            });

            return ciTypeList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置类型属性信息查询接口
     *
     * @param ciTypeId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCiAttr(String ciTypeId) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/cm/ciAttr/" + ciTypeId);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();
            String resultValue = innerMap.get(RESULT_VALUE).toString();
            List<Map<String, Object>> ciAttrList = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
            });

            List<I6000CiAttr> i6000CiAttrs = new ArrayList<>();
            for (Map<String, Object> ciAttrMap : ciAttrList) {
                List<Map<String, Object>> attrData = (List<Map<String, Object>>) ciAttrMap.get("attrData");
                String ciId = (String) ciAttrMap.get("TYPEATTRGRP_ID");
                for (Map<String, Object> attrDatum : attrData) {
                    I6000CiAttr i6000CiAttr = new I6000CiAttr();
                    i6000CiAttr.setCiCode(ciId.substring(0, 6));
                    i6000CiAttr.setAttrCode(String.valueOf(attrDatum.get("ATTR_CODE")));
                    i6000CiAttr.setAttrName(String.valueOf(attrDatum.get("ATTR_NAME")));
                    i6000CiAttr.setDatatypeName(String.valueOf(attrDatum.get("DATATYPE_NAME")));
                    i6000CiAttr.setAttrDataLen(String.valueOf(attrDatum.get("ATTR_DATA_LEN")));
                    i6000CiAttr.setViewFlag(String.valueOf(attrDatum.get("VIEW_FLAG")));
                    i6000CiAttr.setStandardFlag(String.valueOf(attrDatum.get("STANDARD_FLAG")));
                    i6000CiAttr.setCorpCode(String.valueOf(attrDatum.get("CORP_CODE")));
                    i6000CiAttr.setAsctCitypeId(String.valueOf(attrDatum.get("ASCT_CITYPE_ID")));
                    i6000CiAttr.setCollectFlag(String.valueOf(attrDatum.get("COLLECT_FLAG")));
                    i6000CiAttr.setOrigin(String.valueOf(attrDatum.get("ORIGIN")));
                    i6000CiAttr.setReadonly(String.valueOf(attrDatum.get("READONLY")));
                    i6000CiAttr.setOriType(String.valueOf(attrDatum.get("ORI_TYPE")));
                    i6000CiAttr.setUnit(String.valueOf(attrDatum.get("UNIT")));
                    i6000CiAttr.setViewUnit(String.valueOf(attrDatum.get("VIEW_UNIT")));
                    i6000CiAttr.setInputFlag(String.valueOf(attrDatum.get("INPUT_FLAG")));
                    i6000CiAttr.setNullFlag(String.valueOf(attrDatum.get("NULL_FLAG")));
                    i6000CiAttr.setOtnFlag(String.valueOf(attrDatum.get("OTN_FLAG")));
                    i6000CiAttrs.add(i6000CiAttr);
                }
            }
            return ciAttrList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字典表所有枚举项查询接口
     *
     * @return
     */
    @Override
    public JSONObject selectEnumAll() {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/getCmEnumTypeAll");

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();
            List<Map<String, Object>> enumList = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
            });

            for (Map<String, Object> enumMap : enumList) {
                Object o = enumMap.get("enumCode");
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询字典表某一枚举项的所有枚举值
     *
     * @param enumID
     * @return
     */
    @Override
    public JSONObject selectEnumByID(String enumID, String enumValID, String fEnumValID) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            jsonObject.put(ENUM_VAL_ID, enumValID);
            jsonObject.put(F_ENUM_VAL_ID, fEnumValID);
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/cm/enum/" + enumID);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();
            Map<String, Object> resultValueMap = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });

            List<Map<String, Object>> items = (List<Map<String, Object>>) resultValueMap.get(ITEMS);
            for (Map<String, Object> enumDataMap : items) {
                List<Map<String, Object>> enumData = (List<Map<String, Object>>) enumDataMap.get("enumData");
                String enumName = (String) enumDataMap.get("ENUM_NAME");
                for (Map<String, Object> enumDatum : enumData) {
                    String enumvalCode = String.valueOf(enumDatum.get("ENUMVAL_CODE"));
                    String enumvalId = String.valueOf(enumDatum.get("ENUMVAL_ID"));
                    String enumvalName = String.valueOf(enumDatum.get("ENUMVAL_NAME"));
                    String enumId = String.valueOf(enumDatum.get("ENUM_ID"));
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置项关联信息查询接口
     *
     * @param ciid
     * @return
     */
    @Override
    public JSONObject selectRelationByCiid(String ciid) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            jsonObject.put(DIRECT_FLAG, "0");
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/ci/relation" + ciid);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();

            Map<String, Object> resultValueMap = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询全部I6000外部数据视图接口
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> queryOriView() {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/queryOriView");

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            Map<String, Object> innerMap = result.getInnerMap();
            String resultValue = innerMap.get(RESULT_VALUE).toString();
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> oriViewList = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
            });
            if (CollectionUtils.isEmpty(oriViewList)) {
                return new ArrayList<>();
            }
            for (Map<String, Object> map : oriViewList) {
                // 外部视图ID
                Object oriViewId = map.get(ORI_VIEW_ID);
                // 外部视图CODE
                Object oriViewCode = map.get(ORI_VIEW_CODE_CAP);
                // 外部视图名称
                Object oriViewName = map.get(ORI_VIEW_NAME);
            }

            return oriViewList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询指定的外部数据（分页）接口
     *
     * @param i6000OriViewDTO
     * @return
     */
    @Override
    public List<I6000External> selectOriView(I6000OriViewDTO i6000OriViewDTO) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            jsonObject.put(PAGE_START, i6000OriViewDTO.getPageStart());
            jsonObject.put(PAGE_SIZE, i6000OriViewDTO.getPageSize());
            jsonObject.put(EXT_NAME, i6000OriViewDTO.getExtName());
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/cm/ori/" + i6000OriViewDTO.getOriViewId());

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();
            Map<String, Object> oriViewMap = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> items = (List<Map<String, Object>>) oriViewMap.get(ITEMS);
            List<I6000External> i6000Externals = new ArrayList<>();
            items.forEach((item) -> {
                I6000External i6000External = new I6000External();
                i6000External.setExtCode(i6000OriViewDTO.getOriViewId());
                i6000External.setExtId(String.valueOf(item.get("EXT_ID")));
                i6000External.setExtName(String.valueOf(item.get("EXT_NAME")));
                i6000External.setExtPid(String.valueOf(item.get("EXT_PID")));
                i6000External.setExtState(String.valueOf(item.get("EXT_STATE")));
                i6000External.setMatchModelId(String.valueOf(item.get("MATCH_MODEL_ID")));
                i6000External.setMfrHs(String.valueOf(item.get("MFR_HS")));
                i6000External.setRn(String.valueOf(item.get("RN")));
                i6000External.setCreateTime(new Date());
                i6000External.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                i6000External.setUpdateTime(new Date());
                i6000Externals.add(i6000External);
            });

            return i6000Externals;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询指定的外部数据（不分页）接口
     *
     * @return
     */
    @Override
    public Map<String, Object> selectOriViewAll(String oriViewCode) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            jsonObject.put(ORI_VIEW_CODE, oriViewCode);
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/queryOriViewData");

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();
            Map<String, Object> oriViewMap = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> items = (List<Map<String, Object>>) oriViewMap.get(ITEMS);
            List<I6000External> i6000Externals = new ArrayList<>();
            items.forEach((item) -> {
                I6000External i6000External = new I6000External();
                i6000External.setExtCode(oriViewCode);
                i6000External.setExtId(String.valueOf(item.get("EXT_ID")));
                i6000External.setExtName(String.valueOf(item.get("EXT_NAME")));
                i6000External.setExtPid(String.valueOf(item.get("EXT_PID")));
                i6000External.setExtState(String.valueOf(item.get("EXT_STATE")));
                i6000External.setMatchModelId(String.valueOf(item.get("MATCH_MODEL_ID")));
                i6000External.setMfrHs(String.valueOf(item.get("MFR_HS")));
                i6000External.setRn(String.valueOf(item.get("RN")));
                i6000External.setCreateTime(new Date());
                i6000External.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
                i6000External.setUpdateTime(new Date());
                i6000Externals.add(i6000External);
            });

            return oriViewMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据ERP资产编码和ERP设备编码, 信通一体化查询I6000系统数据
     *
     * @param i6000SrynDTOS
     * @return
     */
    @Override
    public Map<String, I6000DetailVO> selectI6000Detail(List<I6000SrynDTO> i6000SrynDTOS) {

        Map<String, I6000DetailVO> returnMap = new HashMap<>();

        for (I6000SrynDTO i6000SrynDTO : i6000SrynDTOS) {
            // ERP资产编码
            String assetCodeErp = i6000SrynDTO.getErpAssetCode();
            // ERP设备台账编码
            String deviceCodeErp1 = i6000SrynDTO.getErpAccountCode();
            String deviceCodeErp = i6000SrynDTO.getErpAccountCode();
            if (deviceCodeErp.startsWith("00")) {
                deviceCodeErp = deviceCodeErp.substring(2);
            }

            CiCientitySearch cientitySearch = new CiCientitySearch();
            List<CiCientitySearchVO> entity = new ArrayList<>();
            CiCientitySearchVO ciCientitySearchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE_ERP).expression(Expression.LIKE).attrValue(deviceCodeErp).build();
            CiCientitySearchVO ciCientitySearchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.ASSET_CODE_ERP).expression(Expression.EQUAL).attrValue(assetCodeErp).build();

            entity.add(ciCientitySearchVO1);
            entity.add(ciCientitySearchVO2);
            Query query = new Query().setCurrent(1).setSize(10);
            cientitySearch.setEntity(entity);
            cientitySearch.setQuery(query);
            cientitySearch.setFullField(Boolean.TRUE);

            FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
            Integer total = feignCiCientity.getTotal();
            I6000DetailVO i6000DetailVO = new I6000DetailVO();
            if (total == 0) {
                i6000DetailVO.setCode(Boolean.FALSE);
                i6000DetailVO.setRemake("在设备台账中未查询到ERP资产编码: " + assetCodeErp + ", ERP设备台账编码: " + deviceCodeErp + ", 请重新维护!");
                returnMap.put(deviceCodeErp, i6000DetailVO);
                continue;
            }
            if (total >= 2) {
                i6000DetailVO.setCode(Boolean.FALSE);
                i6000DetailVO.setRemake("在设备台账中查询到ERP资产编码: " + assetCodeErp + ", ERP设备台账编码: " + deviceCodeErp + ", 有 " + total + " 条设备台账,请重新维护!");
                returnMap.put(deviceCodeErp, i6000DetailVO);
                continue;
            }

            Map<String, Object> entityMap = feignCiCientity.getData().get(0);
            // 设备类型code
            Object deviceTypeCode = entityMap.get(CmdbAttrConstant.DEVICE_TYPE_CODE);
            // 设备编码
            Object deviceCode = entityMap.get(CmdbAttrConstant.DEVICE_CODE);
            // 维护工厂编码
            Object maintenanceFactoryCode = entityMap.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE);

            if (entityMap.containsKey(CmdbAttrConstant.I6000_CI_ID)) {
                Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                i6000DetailVO.setI6000CiId(String.valueOf(i6000CiId));
            }

            i6000DetailVO.setDeviceCode(String.valueOf(deviceCode));

            HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(String.valueOf(deviceTypeCode)).build();
            HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
            if (Objects.isNull(hardwareBasicTreeOne)) {
                throw new RuntimeException("选择的设备类型在I6000模型中不存在!");
            }

            String ciTypeId = hardwareBasicTreeOne.getI6000Code();

            I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
            i6000CiCientityDTO.setAttrCode("CI_ID,CITYPE_ID,ERP_ASSET_NO,ERP_LEDGER_NO,WBS,WBS_NAME," +
                    "ITEM_NO,ITEM,FUN_SITE_NAME,FUN_SITE,OPDEP_NAME,PROP_CORP,PROP_CORP_NAME");

            List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();
            // ERP资产编码
            I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
            conditions1.setOperator("=");
            conditions1.setAttrCode(I6000AttrConstant.ERP_ASSET_NO);
            conditions1.setValue(String.valueOf(assetCodeErp));
            orConditionList.add(conditions1);

            // ERP设备台账编码
            I6000CiCientityDTO.Conditions conditions2 = new I6000CiCientityDTO.Conditions();
            conditions2.setOperator("like");
            conditions2.setAttrCode(I6000AttrConstant.ERP_LEDGER_NO);
            conditions2.setValue(deviceCodeErp);
            orConditionList.add(conditions2);

            I6000CiCientityDTO.Conditions conditions3 = new I6000CiCientityDTO.Conditions();
            conditions3.setOperator("=");
            conditions3.setAttrCode(I6000AttrConstant.OPDEP);
            conditions3.setValue(String.valueOf(maintenanceFactoryCode));
            orConditionList.add(conditions3);

            i6000CiCientityDTO.setConditions(orConditionList);
            i6000CiCientityDTO.setPageStart("1");
            i6000CiCientityDTO.setPageSize("10");

            if (thirdProperties.getIsGovernProperty()) {

                try {
                    List<Map<String, Object>> i6000ResultMap = this.selectCiCientity(ciTypeId, i6000CiCientityDTO);

                    if (CollectionUtils.isEmpty(i6000ResultMap)) {
                        i6000DetailVO.setCode(Boolean.FALSE);
                        i6000DetailVO.setIsAddI6000Detail(Boolean.TRUE);
                        i6000DetailVO.setRemake("I6000未查询到数据, 请核实I6000系统中数据!");
                    } else {
                        i6000DetailVO.setCode(Boolean.TRUE);
                        i6000DetailVO.setI6000ResultMap(i6000ResultMap);
                        // 如果台账已经同步过I6000数据, 找到对应的查询I6000数据直接同步.
                        if (entityMap.containsKey(CmdbAttrConstant.I6000_CI_ID)) {
                            Object i6000CiId = entityMap.get(CmdbAttrConstant.I6000_CI_ID);
                            List<Map<String, Object>> i6000ResultMap1 = new ArrayList<>();
                            for (Map<String, Object> map1 : i6000ResultMap) {
                                Object i6000CiId1 = map1.get("CI_ID");
                                if (i6000CiId.equals(i6000CiId1)) {
                                    i6000ResultMap1.add(map1);
                                    i6000DetailVO.setI6000ResultMap(i6000ResultMap1);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    i6000DetailVO.setCode(Boolean.FALSE);
                    i6000DetailVO.setRemake("查询I6000系统错误, 异常信息: " + e.getMessage());
                }
            } else {
                List<Map<String, Object>> returnList = getReturnList(ciTypeId, assetCodeErp, deviceCodeErp);

                i6000DetailVO.setCode(Boolean.TRUE);
                i6000DetailVO.setIsAddI6000Detail(Boolean.FALSE);

                // i6000DetailVO.setCode(Boolean.FALSE);
                // i6000DetailVO.setIsAddI6000Detail(Boolean.TRUE);
                // i6000DetailVO.setRemake("I6000未查询到数据, 请核实I6000系统中数据!");
                i6000DetailVO.setI6000ResultMap(returnList);
            }
            returnMap.put(deviceCodeErp1, i6000DetailVO);
        }

        return returnMap;
    }


    @Override
    public List<ProjectManagerDetail> syncI6000Detail(List<I6000SyncDetailDTO> i6000SyncDetailDTOS) {
        List<ProjectManagerDetail> projectManagerDetails = new ArrayList<>();
        if (i6000SyncDetailDTOS.size() > 30) {
            throw new RuntimeException("同步I6000平台数量请控制在30条以内!");
        }
        LOGGER.info("同步i6000接口-初始参数{}", i6000SyncDetailDTOS);
        for (I6000SyncDetailDTO i6000SyncDetailDTO : i6000SyncDetailDTOS) {

            CiCientitySearch cientitySearch = new CiCientitySearch();
            List<CiCientitySearchVO> entity = new ArrayList<>();
            CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder()
                    .attrName(CmdbAttrConstant.DEVICE_CODE)
                    .expression(Expression.EQUAL)
                    .attrValue(i6000SyncDetailDTO.getDeviceCode()).build();
            entity.add(ciCientitySearchVO);
            Query query = new Query().setCurrent(1).setSize(10);
            cientitySearch.setEntity(entity);
            cientitySearch.setQuery(query);
            cientitySearch.setFullField(Boolean.TRUE);

            FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
            Map<String, Object> map1 = feignCiCientity.getData().get(0);
            map1.put(CmdbAttrConstant.REMARK, "信通一体化平台设备编码为:" + i6000SyncDetailDTO.getDeviceCode());
            LOGGER.info("根据设备编码查询台账数据: {}", map1);
            //字段校验
            Map<String, Map<String, Object>> entityMap = new HashMap<>();
            // Map<String, Object> hashMap = new HashMap<>();
            // if (StringUtil.isNotBlank(i6000Properties.getField())) {
            //  String[] split = i6000Properties.getField().split(",");
            //  List<String> fieldList = Arrays.asList(split);
            //  for (Map.Entry<String, Object> entry : map1.entrySet()) {
            //      String key = entry.getKey();
            //      if (!fieldList.contains(key)) {
            //          hashMap.put(key, entry.getValue());
            //      }
            //  }
            //  hashMap.put(I6000AttrConstant.CITYPE_ID, i6000SyncDetailDTO.getCITYPE_ID());
            //  entityMap.put(i6000SyncDetailDTO.getCI_ID(), hashMap);
            // } else {
            //  map1.put(I6000AttrConstant.CITYPE_ID, i6000SyncDetailDTO.getCITYPE_ID());
            //  entityMap.put(i6000SyncDetailDTO.getCI_ID(), map1);
            // }

            ProjectManagerDetail detail = new ProjectManagerDetail();
            if (thirdProperties.getIsRequestI6000()) {
                // 新增
                if (i6000SyncDetailDTO.getIsAddI6000Detail()) {
                    try {
                        ProjectManagerDetail managerDetail = projectManagerDetailService.getById(i6000SyncDetailDTO.getUUID());
                        Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
                        String ciTypeId = erpI6000MapByCiId.get(managerDetail.getDeviceType());
                        Map<String, Map<String, Object>> entityMap2 = new HashMap<>();
                        Object uuid = map1.get(CmdbAttrConstant.UUID);
                        entityMap2.put(String.valueOf(uuid), map1);
                        LOGGER.info("开始新增i6000,新增数据: {}", entityMap2);

                        I6000ResultResp resultResp = this.i6000BatchsaveCopy(ciTypeId, entityMap2);

                        LOGGER.info("新增请求i6000结果: {}", resultResp);
                        if (StringUtils.equals("true", resultResp.getSuccessful())) {
                            LOGGER.info("i6000新增接口请求成功");
                            detail.setI6000Status(2);
                            detail.setI6000Remake("同步成功!");
                            //更新cmdb
                            Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                            // map1.put(CmdbAttrConstant.I6000_CI_ID, i6000SyncDetailDTO.getCI_ID());
                            map1.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                            longMapMap.put(Long.parseLong(String.valueOf(map1.get(CmdbAttrConstant.ID))), map1);
                            cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                        } else {
                            LOGGER.info("i6000接口请求失败！！！！！");
                            detail.setI6000Status(3);
                            detail.setI6000Remake("同步失败!");
                        }
                    } catch (Exception e) {
                        LOGGER.error("新增i6000台账抛出异常: {}", e.getMessage());
                    }
                }
                // 更新
                if (!i6000SyncDetailDTO.getIsAddI6000Detail() && StringUtils.isNotEmpty(i6000SyncDetailDTO.getUUID())) {
                    map1.put(I6000AttrConstant.CITYPE_ID, i6000SyncDetailDTO.getCITYPE_ID());
                    entityMap.put(i6000SyncDetailDTO.getCI_ID(), map1);
                    LOGGER.info("开始更新i6000,更新数据: {}", entityMap);
                    try {
                        List<I6000ResultResp> i6000ResultResps = this.i6000BatchupdateCopy(entityMap);
                        LOGGER.info("更新请求i6000结果: {}", i6000ResultResps);
                        if (StringUtils.equals("true", i6000ResultResps.get(0).getSuccessful())) {
                            LOGGER.info("i6000接口请求成功");
                            detail.setI6000Status(2);
                            detail.setI6000Remake("同步成功!");
                            //更新cmdb
                            Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                            map1.put(CmdbAttrConstant.I6000_CI_ID, i6000SyncDetailDTO.getCI_ID());
                            map1.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                            longMapMap.put(Long.parseLong(String.valueOf(map1.get(CmdbAttrConstant.ID))), map1);
                            cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                        } else {
                            LOGGER.info("i6000接口请求失败！！！！！");
                            detail.setI6000Status(3);
                            detail.setI6000Remake("同步失败!");

                        }
                    } catch (Exception e) {
                        LOGGER.error("更新i6000台账抛出异常: {}", e.getMessage());
                    }
                }
            } else {
                LOGGER.info("-------------未开启更新i6000接口配置-------------");
                //更新cmdb
                Map<Long, Map<String, Object>> longMapMap = new HashMap<>();
                map1.put(CmdbAttrConstant.I6000_CI_ID, i6000SyncDetailDTO.getCI_ID());
                map1.put(CmdbAttrConstant.IS_TO_I6000, cmdbCientityProperties.getYesNo());
                longMapMap.put(Long.parseLong(String.valueOf(map1.get(CmdbAttrConstant.ID))), map1);
                cmdbService.cientityBatchupdate(longMapMap, TransactionActionType.UPDATE);
                detail.setI6000Status(0);
                detail.setI6000Remake("未同步!");
            }
            LOGGER.info("-----------更新projectManagerDetail表------{}", detail);
            detail.setUuid(i6000SyncDetailDTO.getUUID());
            detail.setDeviceCode(i6000SyncDetailDTO.getDeviceCode());
            projectManagerDetailService.updateById(detail);
            ProjectManagerDetail managerDetail = projectManagerDetailService.getById(detail.getUuid());
            projectManagerDetails.add(managerDetail);
        }
        return projectManagerDetails;
    }


    /**
     * 配置项查询接口
     *
     * @param ciTypeId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCiCientity(String ciTypeId, I6000CiCientityDTO i6000CiCientityDTO) {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();

            JSONObject jsonObject = getJsonObject();
            JSONObject json = (JSONObject) JSONObject.toJSON(i6000CiCientityDTO);
            jsonObject.putAll(json);

            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/read/i6000-api/ci/" + ciTypeId);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> innerMap = result.getInnerMap();

            String resultValue = innerMap.get(RESULT_VALUE).toString();
            Map<String, Object> resultValueMap = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });

            Object items = resultValueMap.get(ITEMS);
            if (Objects.nonNull(items)) {
                List<Map<String, Object>> itemList = (List<Map<String, Object>>) resultValueMap.get(ITEMS);
                return itemList;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能位置(新增和修改)
     *
     * @param i6000FuncDTO
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.I6000_UPDATE_DATA_TPLNR, tripleType = TripleTypeEnum.I6000)
    public Map<String, Object> saveOrUpdateFunc(I6000FuncDTO i6000FuncDTO) {
        try {

            // 组装参数
            JSONObject jsonObject = getJsonObject();
            List<JSONObject> items = new ArrayList<>();

            JSONObject item = new JSONObject();
            item.put(OBJID, i6000FuncDTO.getObjId());
            item.put(CODE, i6000FuncDTO.getCode());
            item.put(NAME, i6000FuncDTO.getName());
            item.put(MAINTENANCE, i6000FuncDTO.getMaintenance());
            item.put(DOMAIN, i6000FuncDTO.getDomain());
            item.put(ERP_CODE, i6000FuncDTO.getErpCode());
            item.put(UPFL_CODE, i6000FuncDTO.getUpflcode());
            item.put(VOLTAGE_CLASS, i6000FuncDTO.getVoltageClass());

            items.add(item);
            jsonObject.put(ITEMS, items);

            if (thirdProperties.getIsRequestI6000()) {
                // 设置请求头
                HttpHeaders httpHeaders = this.buildRequestHeaders();
                String url = new String();
                if (StringUtils.equals(ErpConstant.C, i6000FuncDTO.getOperation())) {
                    // 新增
                    url = this.converUrl("/customResourceOperate/operateApi/func/pos/add");
                } else if (StringUtils.equals(ErpConstant.M, i6000FuncDTO.getOperation())) {
                    // 修改
                    if (StringUtils.isEmpty(i6000FuncDTO.getObjId())) {
                        throw new RuntimeException("修改时, I6000唯一ID不能为空");
                    }
                    url = this.converUrl("/customResourceOperate/operateApi/func/pos/update");
                }
                HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);
                JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);
                Map<String, Object> innerMap = result.getInnerMap();
                return innerMap;
            }

            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put(CODE, "0");
            innerMap.put(MESSAGE, "同步成功");
            innerMap.put(ERROR_MSG, "");
            return innerMap;
        } catch (Exception e) {
            throw new RuntimeException("功能位置(新增和修改)异常: " + e.getMessage());
        }
    }


    /**
     * 功能位置(删除)
     *
     * @param objId
     * @return
     */
    @Override
    public Map<String, Object> deleteFunc(String objId) {

        JSONObject jsonObject = getJsonObject();
        List<String> items = new ArrayList<>();
        items.add(objId);
        jsonObject.put(OBJIDS, items);

        if (thirdProperties.getIsRequestI6000()) {
            // 设置请求头
            HttpHeaders httpHeaders = this.buildRequestHeaders();
            String url = this.converUrl("/customResourceOperate/operateApi/func/pos/remove");

            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);
            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);
            Map<String, Object> innerMap = result.getInnerMap();
            return innerMap;
        }

        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put(CODE, "0");
        innerMap.put(MESSAGE, "同步成功");
        innerMap.put(ERROR_MSG, "");
        return null;
    }

    /**
     * 获取I6000的仓库信息(T501)
     *
     * @return
     */
    @Override
    public List<I6000RoomDTO> getRoomList() {

        IdevelopUser user = SecureUtil.getUser();
        String i6000UnitCode = user.getI6000UnitCode();
        String i6000Unit = user.getI6000Unit();
        if (StringUtils.isEmpty(i6000UnitCode)) {
            throw new RuntimeException("该用户未配置对应I6000单位,请联系项目组!");
        }

        I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
        i6000CiCientityDTO.setAttrCode("CI_NAME,RUN_CORP_CODE,RUN_CORP_CODE_NAME");

        List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();
        I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
        conditions1.setOperator("=");
        conditions1.setAttrCode(I6000AttrConstant.RUN_CORP_CODE);
        conditions1.setValue(i6000UnitCode);
        orConditionList.add(conditions1);

        i6000CiCientityDTO.setConditions(orConditionList);
        i6000CiCientityDTO.setPageStart("1");
        i6000CiCientityDTO.setPageSize("10");

        List<I6000RoomDTO> returnList = new ArrayList<>();
        if (thirdProperties.getIsRoomWarehouseI6000()) {
            List<Map<String, Object>> i6000ResultList = this.selectCiCientity("T501", i6000CiCientityDTO);

            if (CollectionUtils.isEmpty(i6000ResultList)) {
                throw new RuntimeException("未查到到当前用户在I6000系统中的机房数据,请联系项目组!");
            }
            for (Map<String, Object> map : i6000ResultList) {
                I6000RoomDTO i6000RoomDTO = new I6000RoomDTO();
                i6000RoomDTO.setUuid(String.valueOf(map.get(CI_ID)));
                i6000RoomDTO.setName(String.valueOf(map.get(CI_NAME)));
                i6000RoomDTO.setCitypeId(String.valueOf(map.get(CITYPE_ID)));
                i6000RoomDTO.setRunCorpCode(String.valueOf(map.get(RUN_CORP_CODE)));
                i6000RoomDTO.setRunCorpCodeName(String.valueOf(map.get(RUN_CORP_CODE_NAME)));
                returnList.add(i6000RoomDTO);
            }
            return returnList;
        }
        I6000RoomDTO i6000RoomDTO = new I6000RoomDTO();
        i6000RoomDTO.setUuid("1212312321");
        i6000RoomDTO.setName("机房01");
        i6000RoomDTO.setCitypeId("T501");
        i6000RoomDTO.setRunCorpCode(i6000UnitCode);
        i6000RoomDTO.setRunCorpCodeName(i6000Unit);
        returnList.add(i6000RoomDTO);
        I6000RoomDTO i6000RoomDTO1 = new I6000RoomDTO();
        i6000RoomDTO1.setUuid("111111111111");
        i6000RoomDTO1.setName("机房02");
        i6000RoomDTO1.setCitypeId("T501");
        i6000RoomDTO1.setRunCorpCode(i6000UnitCode);
        i6000RoomDTO1.setRunCorpCodeName(i6000Unit);
        returnList.add(i6000RoomDTO1);
        return returnList;
    }

    /**
     * 获取I6000的机房信息(T502)
     *
     * @return
     */
    @Override
    public List<I6000WarehouseDTO> getWarehouseList() {
        IdevelopUser user = SecureUtil.getUser();
        String i6000UnitCode = user.getI6000UnitCode();
        String i6000Unit = user.getI6000Unit();
        if (StringUtils.isEmpty(i6000UnitCode)) {
            throw new RuntimeException("该用户未配置对应I6000单位,请联系项目组!");
        }

        I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
        i6000CiCientityDTO.setAttrCode("CI_NAME,RUN_CORP_CODE,RUN_CORP_CODE_NAME");

        List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();
        I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
        conditions1.setOperator("=");
        conditions1.setAttrCode(I6000AttrConstant.RUN_CORP_CODE);
        conditions1.setValue(i6000UnitCode);
        orConditionList.add(conditions1);

        i6000CiCientityDTO.setConditions(orConditionList);
        i6000CiCientityDTO.setPageStart("1");
        i6000CiCientityDTO.setPageSize("10");

        List<I6000WarehouseDTO> returnList = new ArrayList<>();
        if (thirdProperties.getIsRoomWarehouseI6000()) {
            List<Map<String, Object>> i6000ResultList = this.selectCiCientity("T502", i6000CiCientityDTO);

            if (CollectionUtils.isEmpty(i6000ResultList)) {
                throw new RuntimeException("未查到到当前用户在I6000系统中的机房数据,请联系项目组!");
            }
            for (Map<String, Object> map : i6000ResultList) {
                I6000WarehouseDTO i6000WarehouseDTO = new I6000WarehouseDTO();
                i6000WarehouseDTO.setUuid(String.valueOf(map.get(CI_ID)));
                i6000WarehouseDTO.setName(String.valueOf(map.get(CI_NAME)));
                i6000WarehouseDTO.setCitypeId(String.valueOf(map.get(CITYPE_ID)));
                i6000WarehouseDTO.setRunCorpCode(String.valueOf(map.get(RUN_CORP_CODE)));
                i6000WarehouseDTO.setRunCorpCodeName(String.valueOf(map.get(RUN_CORP_CODE_NAME)));
                returnList.add(i6000WarehouseDTO);
            }
            return returnList;
        }
        I6000WarehouseDTO i6000WarehouseDTO = new I6000WarehouseDTO();
        i6000WarehouseDTO.setUuid("1212312321");
        i6000WarehouseDTO.setName("仓库01");
        i6000WarehouseDTO.setCitypeId("T502");
        i6000WarehouseDTO.setRunCorpCode(i6000UnitCode);
        i6000WarehouseDTO.setRunCorpCodeName(i6000Unit);
        returnList.add(i6000WarehouseDTO);
        I6000WarehouseDTO i6000WarehouseDTO1 = new I6000WarehouseDTO();
        i6000WarehouseDTO1.setUuid("111111111111");
        i6000WarehouseDTO1.setName("仓库02");
        i6000WarehouseDTO1.setCitypeId("T502");
        i6000WarehouseDTO1.setRunCorpCode(i6000UnitCode);
        i6000WarehouseDTO1.setRunCorpCodeName(i6000Unit);
        returnList.add(i6000WarehouseDTO1);
        return returnList;
    }

    /**
     * 新增i6000资产台账
     * 使用方法: 示例数据在 I6000Controller 中
     * ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
     * 方法获取 Key: 设备类型编码(例如: T10302), Value: ciTypeId
     *
     * @param ciTypeId
     * @param entityMap key 为 uuid, value 为 数据
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.I6000_INSERT_DATA, tripleType = TripleTypeEnum.I6000)
    public I6000ResultResp i6000Batchsave(String ciTypeId, Map<String, Map<String, Object>> entityMap) {
        if (thirdProperties.getApiI6000()) {
            try {

                String field = i6000Properties.getField();
                List<String> fieldList = new ArrayList<>();
                if (StringUtils.isNotEmpty(field)) {
                    String[] split = i6000Properties.getField().split(",");
                    fieldList = Arrays.asList(split);
                }

                I6000CmdbMapping i6000CmdbMapping = new I6000CmdbMapping();
                i6000CmdbMapping.setI6000CiId(ciTypeId);
                Map<String, I6000CmdbMapping> i6000CmdbMap = mappingService.list(Condition.getQueryWrapper(i6000CmdbMapping))
                        .stream().collect(Collectors.toMap(I6000CmdbMapping::getCmdbAttrCode, item -> item));

                // IdevelopUser user = SecureUtil.getUser();
                JSONObject jsonObject = getJsonObject();
                AtomicInteger num = new AtomicInteger(0);
                List<JSONObject> items = new ArrayList<>();
                for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {
                    String uuid = UuidUtils.uuid();
                    // String stairKey = stairEntity.getKey();
                    Map<String, Object> stairValue = stairEntity.getValue();
                    JSONObject item = new JSONObject();
                    item.put(CITYPE_ID, ciTypeId);
                    item.put(CI_ID, uuid);
                    stairValue.put(CmdbAttrConstant.I6000_CI_ID, uuid);

                    // 默认填充数据
                    extracted(stairValue);

                    // 根据产权单位填充 I6000的运维单位和产权单位.
                    Object ownerUnitCode = stairValue.get(CmdbAttrConstant.OWNER_UNIT_CODE);
                    Dept ownerUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(ownerUnitCode)));

                    // 领用单位
                    Object receiveUnitCode = stairValue.get(CmdbAttrConstant.RECEIVE_UNIT_CODE);
                    Dept receiveUnit;
                    if (Objects.nonNull(receiveUnitCode)) {
                        receiveUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(receiveUnitCode)));
                    } else {
                        receiveUnit = null;
                    }

                    List<String> finalFieldList = fieldList;
                    stairValue.entrySet().stream()
                            .filter(entity -> !finalFieldList.contains(entity.getKey()))
                            .filter(entity -> i6000CmdbMap.containsKey(entity.getKey()))
                            .forEach(entity -> {
                                String key = entity.getKey();
                                Object value = entity.getValue();

                                try {
                                    num.incrementAndGet();

                                    I6000CmdbMapping mapping = i6000CmdbMap.get(key);
                                    Object objectValue = this.getObjectValue(value, mapping, ownerUnit, receiveUnit);
                                    stairValueExtracted(key, objectValue, item, mapping, value);
                                } catch (Exception e) {
                                    LOGGER.info("新增i6000资产台账循环组装数据异常 key: {}, value: {}, 异常信息: {}", key, value, e.getMessage());
                                }

                            });
                    item.put(I6000AttrConstant.MADE_COUNTRY, "40004407");
                    items.add(item);
                }
                if (num.get() == 0) {
                    throw new RuntimeException("请求参数唯有与i6000系统属性相交的数据!");
                }
                jsonObject.put(ITEMS, items);

                LOGGER.info("新增i6000资产台账: " + JSONObject.toJSON(jsonObject));
                // 设置请求头
                return writeAddCmCi(ciTypeId, jsonObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return new I6000ResultResp();
        }

    }

    /**
     * 新增i6000资产台账
     * 使用方法: 示例数据在 I6000Controller 中
     * ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
     * 方法获取 Key: 设备类型编码(例如: T10302), Value: ciTypeId
     *
     * @param ciTypeId
     * @param entityMap key 为 uuid, value 为 数据
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.I6000_INSERT_DATA, tripleType = TripleTypeEnum.I6000)
    public I6000ResultResp i6000BatchsaveCopy(String ciTypeId, Map<String, Map<String, Object>> entityMap) {
        try {

            String field = i6000Properties.getField();
            List<String> fieldList = new ArrayList<>();
            if (StringUtils.isNotEmpty(field)) {
                String[] split = i6000Properties.getField().split(",");
                fieldList = Arrays.asList(split);
            }

            Map<Object, Object> deviceAddMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceAdd());
            List<Map<String, Object>> deviceAddTypeList = cmdbDictProperties.getDictListByCiId(cmdbDictProperties.getDeviceChangeType());
            I6000CmdbMapping i6000CmdbMapping = new I6000CmdbMapping();
            i6000CmdbMapping.setI6000CiId(ciTypeId);
            Map<String, I6000CmdbMapping> i6000CmdbMap = mappingService.list(Condition.getQueryWrapper(i6000CmdbMapping))
                    .stream().collect(Collectors.toMap(I6000CmdbMapping::getCmdbAttrCode, item -> item));

            // IdevelopUser user = SecureUtil.getUser();
            JSONObject jsonObject = getJsonObject();
            AtomicInteger num = new AtomicInteger(0);
            List<JSONObject> items = new ArrayList<>();
            for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {
                String uuid = UuidUtils.uuid();
                // String stairKey = stairEntity.getKey();
                Map<String, Object> stairValue = stairEntity.getValue();
                JSONObject item = new JSONObject();
                item.put(CITYPE_ID, ciTypeId);
                item.put(CI_ID, uuid);
                stairValue.put(CmdbAttrConstant.I6000_CI_ID, uuid);

                // 默认填充数据
                extracted(stairValue);

                // 根据产权单位填充 I6000的运维单位和产权单位.
                Object ownerUnitCode = stairValue.get(CmdbAttrConstant.OWNER_UNIT_CODE);
                Dept ownerUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(ownerUnitCode)));

                // 领用单位
                Object receiveUnitCode = stairValue.get(CmdbAttrConstant.RECEIVE_UNIT_CODE);
                Dept receiveUnit;
                if (Objects.nonNull(receiveUnitCode)) {
                    receiveUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(receiveUnitCode)));
                } else {
                    receiveUnit = null;
                }

                extracted(stairValue, deviceAddMap, deviceAddTypeList);

                List<String> finalFieldList = fieldList;
                stairValue.entrySet().stream()
                        .filter(entity -> !finalFieldList.contains(entity.getKey()))
                        .filter(entity -> i6000CmdbMap.containsKey(entity.getKey()))
                        .forEach(entity -> {
                            String key = entity.getKey();
                            Object value = entity.getValue();

                            try {
                                num.incrementAndGet();

                                I6000CmdbMapping mapping = i6000CmdbMap.get(key);
                                Object objectValue = this.getObjectValue(value, mapping, ownerUnit, receiveUnit);
                                stairValueExtracted(key, objectValue, item, mapping, value);
                            } catch (Exception e) {
                                LOGGER.info("新增i6000资产台账循环组装数据异常 key: {}, value: {}, 异常信息: {}", key, value, e.getMessage());
                            }

                        });
                item.put(I6000AttrConstant.MADE_COUNTRY, i6000CientityProperties.getMadeCountry1());
                if (StringUtils.equalsAny(ciTypeId, "T10602")) {
                    item.put(I6000AttrConstant.AC_TYPE, "50000701");
                }
                items.add(item);
            }
            if (num.get() == 0) {
                throw new RuntimeException("请求参数唯有与i6000系统属性相交的数据!");
            }
            jsonObject.put(ITEMS, items);

            LOGGER.info("新增i6000资产台账: " + JSONObject.toJSON(jsonObject));
            // 设置请求头
            return writeAddCmCi(ciTypeId, jsonObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 新增请求I6000数据
     *
     * @param ciTypeId
     * @param jsonObject
     * @return
     */
    private I6000ResultResp writeAddCmCi(String ciTypeId, JSONObject jsonObject) {
        I6000ResultResp resultResp = new I6000ResultResp();
        LOGGER.info("新增请求I6000数据: " + thirdProperties.getIsRequestI6000());
        if (thirdProperties.getIsRequestI6000()) {
            HttpHeaders httpHeaders = this.buildRequestHeaders();
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/write/i6000-api/ci/" + ciTypeId);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);
            resultResp.setSuccessful(String.valueOf(result.get("successful")));
            resultResp.setResultHint(String.valueOf(result.get("resultHint")));
            resultResp.setResultValue(String.valueOf(result.get("resultValue")));
        } else {
            resultResp.setSuccessful(Boolean.TRUE.toString());
            resultResp.setResultHint(jsonObject.toJSONString());
            resultResp.setResultValue("");
        }
        return resultResp;
    }

    /**
     * 更新i6000资产台账
     * 使用方法: 示例数据在 I6000Controller 中
     * value 中必须 包含 CITYPE_ID: ciTypeId
     * <p>
     * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
     * 方法获取 Key: 设备类型编码, Value: ciTypeId
     *
     * @param entityMap key 为 uuid, value 为 数据
     * @return
     */
    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.I6000_UPDATE_DATA, tripleType = TripleTypeEnum.I6000)
    public List<I6000ResultResp> i6000Batchupdate(Map<String, Map<String, Object>> entityMap) {
        if (thirdProperties.getApiI6000()) {
            try {

                String field = i6000Properties.getField();
                List<String> fieldList = new ArrayList<>();
                if (StringUtils.isNotEmpty(field)) {
                    String[] split = i6000Properties.getField().split(",");
                    fieldList = Arrays.asList(split);
                }

                // 获取所有请求参数的映射属性关系
                Set<String> citypeIds = entityMap.values().stream().map(item -> item.get(CITYPE_ID).toString()).collect(Collectors.toSet());
                List<I6000CmdbMappingVO> i6000CmdbMappingVOS = mappingService.selectI6000CmdbMappingByCiIds(citypeIds);

                // 分组模型属性映射关系
                Map<String, List<I6000CmdbMappingVO>> citypeMap = i6000CmdbMappingVOS.stream().collect(Collectors.groupingBy(I6000CmdbMapping::getI6000CiId));

                JSONObject jsonObject = getJsonObject();
                jsonObject.put(IMPORT_FLAG, ONE);

                AtomicInteger citypeNum = new AtomicInteger(0);
                Map<String, JSONObject> requestMap = new HashMap<>();
                for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {

                    // 自增
                    citypeNum.incrementAndGet();

                    // 处理数据
                    List<JSONObject> items = new ArrayList<>();
                    // String stairKey = stairEntity.getKey();
                    Map<String, Object> stairValue = stairEntity.getValue();

                    // 默认填充数据
                    extracted(stairValue);

                    // 查询CMDB
                    Map<String, Object> cmdbMap = this.getCiCientityListByCondition(stairValue);
                    Object I6000CiId = cmdbMap.get(CmdbAttrConstant.I6000_CI_ID);
                    Object ownerUnitCode = cmdbMap.get(CmdbAttrConstant.OWNER_UNIT_CODE);
                    Object receiveUnitCode = cmdbMap.get(CmdbAttrConstant.RECEIVE_UNIT_CODE);

                    Map.Entry<String, Object> entry = stairValue.entrySet().stream().filter(map -> StringUtils.equals(map.getKey(), CITYPE_ID))
                            .findFirst().orElseThrow(() -> new RuntimeException("参数 CITYPE_ID 不能为空"));
                    String ciTypeId = String.valueOf(entry.getValue());

                    List<I6000CmdbMappingVO> mappingList = citypeMap.get(ciTypeId);
                    if (CollectionUtils.isEmpty(mappingList)) {
                        throw new RuntimeException("未查询到对应的模型映射关系,请求联系运维人员处理! ciTypeId = " + ciTypeId);
                    }

                    // 获取对应模型的属性信息
                    Map<String, I6000CmdbMapping> i6000CmdbMap = mappingList.stream()
                            .collect(Collectors.toMap(I6000CmdbMapping::getCmdbAttrCode, item -> item));

                    JSONObject item = new JSONObject();
                    item.put(CITYPE_ID, ciTypeId);
                    item.put(CI_ID, I6000CiId);

                    // 根据产权单位填充 I6000的运维单位和产权单位.
                    Dept ownerUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(ownerUnitCode)));
                    item.put(I6000AttrConstant.RUN_CORP_CODE, ownerUnit.getI6000UnitCode());
                    item.put(I6000AttrConstant.PROP_CORP, ownerUnit.getI6000UnitCode());

                    // 领用单位
                    Dept receiveUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(receiveUnitCode)));

                    List<String> finalFieldList = fieldList;
                    AtomicInteger num = new AtomicInteger(0);
                    stairValue.entrySet().stream()
                            .filter(entity -> !finalFieldList.contains(entity.getKey()))
                            .filter(entity -> i6000CmdbMap.containsKey(entity.getKey()))
                            .forEach(entity -> {
                                num.incrementAndGet();
                                String key = entity.getKey();
                                Object value = entity.getValue();

                                I6000CmdbMapping mapping = i6000CmdbMap.get(key);

                                Object objectValue = this.getObjectValue(value, mapping, ownerUnit, receiveUnit);
                                stairValueExtracted(key, objectValue, item, mapping, value);
                            });
                    item.put(I6000AttrConstant.MADE_COUNTRY, "40004407");
                    items.add(item);

                    if (num.get() == 0) {
                        throw new RuntimeException("请求参数未有与i6000系统属性相交的数据, 请重新确认!");
                    }

                    jsonObject.put(ITEMS, items);
                    // 需要批量请求接口
                    requestMap.put(ciTypeId + "-" + citypeNum, jsonObject);
                }
                if (citypeNum.get() != entityMap.size()) {
                    throw new RuntimeException("请求参数与实际组装参数不一致, 请重新确认!");
                }

                LOGGER.info("修改i6000资产台账(总): " + JSONObject.toJSON(requestMap));
                List<I6000ResultResp> i6000ResultResps = new ArrayList<>();
                for (Map.Entry<String, JSONObject> stairEntity : requestMap.entrySet()) {
                    String key = stairEntity.getKey().substring(0, 6);
                    JSONObject value = stairEntity.getValue();
                    LOGGER.info("修改i6000资产台账(单): " + JSONObject.toJSON(value));
                    // 请求数据
                    i6000ResultResps.add(writeUpdateCmCi(key, value));
                }
                return i6000ResultResps;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @TripleApiLogA(value = TripleApiLogValueEnum.I6000_UPDATE_DATA, tripleType = TripleTypeEnum.I6000)
    public List<I6000ResultResp> i6000BatchupdateCopy(Map<String, Map<String, Object>> entityMap) {
        try {
            LOGGER.info("进入i6000更新方法");
            String field = i6000Properties.getField();
            List<String> fieldList = new ArrayList<>();
            if (StringUtils.isNotEmpty(field)) {
                String[] split = i6000Properties.getField().split(",");
                fieldList = Arrays.asList(split);
            }

            Map<Object, Object> deviceAddMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceAdd());
            List<Map<String, Object>> deviceAddTypeList = cmdbDictProperties.getDictListByCiId(cmdbDictProperties.getDeviceChangeType());
            // 获取所有请求参数的映射属性关系
            Set<String> citypeIds = entityMap.values().stream().map(item -> item.get(CITYPE_ID).toString()).collect(Collectors.toSet());
            List<I6000CmdbMappingVO> i6000CmdbMappingVOS = mappingService.selectI6000CmdbMappingByCiIds(citypeIds);
            // LOGGER.info("获取映射关系: {}", JSONObject.toJSON(i6000CmdbMappingVOS));
            // 分组模型属性映射关系
            Map<String, List<I6000CmdbMappingVO>> citypeMap = i6000CmdbMappingVOS.stream().collect(Collectors.groupingBy(I6000CmdbMapping::getI6000CiId));

            JSONObject jsonObject = getJsonObject();
            jsonObject.put(IMPORT_FLAG, ONE);

            AtomicInteger citypeNum = new AtomicInteger(0);
            Map<String, JSONObject> requestMap = new HashMap<>();
            for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {

                // 自增
                citypeNum.incrementAndGet();

                // 处理数据
                List<JSONObject> items = new ArrayList<>();
                // String stairKey = stairEntity.getKey();
                Map<String, Object> stairValue = stairEntity.getValue();

                // 默认填充数据
                extracted(stairValue);

                // 查询CMDB
                Map<String, Object> cmdbMap = this.getCiCientityListByCondition(stairValue);
                Object I6000CiId = cmdbMap.get(CmdbAttrConstant.I6000_CI_ID);
                Object ownerUnitCode = cmdbMap.get(CmdbAttrConstant.OWNER_UNIT_CODE);
                Object receiveUnitCode = cmdbMap.get(CmdbAttrConstant.RECEIVE_UNIT_CODE);

                Map.Entry<String, Object> entry = stairValue.entrySet().stream().filter(map -> StringUtils.equals(map.getKey(), CITYPE_ID))
                        .findFirst().orElseThrow(() -> new RuntimeException("参数 CITYPE_ID 不能为空"));
                String ciTypeId = String.valueOf(entry.getValue());

                List<I6000CmdbMappingVO> mappingList = citypeMap.get(ciTypeId);
                if (CollectionUtils.isEmpty(mappingList)) {
                    throw new RuntimeException("未查询到对应的模型映射关系,请求联系运维人员处理! ciTypeId = " + ciTypeId);
                }
                // LOGGER.info("获取对应模型映射关系: {}", JSONObject.toJSON(mappingList));

                // 获取对应模型的属性信息
                Map<String, I6000CmdbMapping> i6000CmdbMap = mappingList.stream()
                        .collect(Collectors.toMap(I6000CmdbMapping::getCmdbAttrCode, item -> item));

                JSONObject item = new JSONObject();
                item.put(CITYPE_ID, ciTypeId);
                item.put(CI_ID, I6000CiId);

                // 根据产权单位填充 I6000的运维单位和产权单位.
                Dept ownerUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(ownerUnitCode)));
                if (Objects.isNull(ownerUnit)) {
                    throw new RuntimeException("根据产权单位填充 I6000的运维单位和产权单位异常." + ownerUnit);
                }
                item.put(I6000AttrConstant.RUN_CORP_CODE, ownerUnit.getI6000UnitCode());
                item.put(I6000AttrConstant.PROP_CORP, ownerUnit.getI6000UnitCode());

                // 领用单位
                Dept receiveUnit;
                if (Objects.nonNull(receiveUnitCode)) {
                    receiveUnit = DeptWrapper.build().getUnitDeptCode(Long.valueOf(String.valueOf(receiveUnitCode)));
                } else {
                    receiveUnit = null;
                }

                // 补充数据
                extracted(stairValue, deviceAddMap, deviceAddTypeList);

                List<String> finalFieldList = fieldList;
                AtomicInteger num = new AtomicInteger(0);
                stairValue.entrySet().stream()
                        .filter(entity -> !finalFieldList.contains(entity.getKey()))
                        .filter(entity -> i6000CmdbMap.containsKey(entity.getKey()))
                        .forEach(entity -> {
                            num.incrementAndGet();
                            String key = entity.getKey();
                            Object value = entity.getValue();
                            LOGGER.info("----组装数据结果1:----{},{}", key, value);
                            I6000CmdbMapping mapping = i6000CmdbMap.get(key);

                            Object objectValue = this.getObjectValue(value, mapping, ownerUnit, receiveUnit);
                            LOGGER.info("----组装数据结果2:----{},{}", key, objectValue);
                            stairValueExtracted(key, objectValue, item, mapping, value);
                        });
                if (StringUtils.equalsAny(ciTypeId, "T10602")) {
                    item.put(I6000AttrConstant.AC_TYPE, "50000701");
                }
                item.put(I6000AttrConstant.MADE_COUNTRY, i6000CientityProperties.getMadeCountry1());
                items.add(item);

                if (num.get() == 0) {
                    throw new RuntimeException("请求参数未有与i6000系统属性相交的数据, 请重新确认!");
                }

                jsonObject.put(ITEMS, items);
                // 需要批量请求接口
                requestMap.put(ciTypeId + "-" + citypeNum, jsonObject);
            }
            LOGGER.info("----组装数据结果----{}", requestMap);
            if (citypeNum.get() != entityMap.size()) {
                throw new RuntimeException("请求参数与实际组装参数不一致, 请重新确认!");
            }

            LOGGER.info("修改i6000资产台账: " + JSONObject.toJSON(requestMap));
            List<I6000ResultResp> i6000ResultResps = new ArrayList<>();
            for (Map.Entry<String, JSONObject> stairEntity : requestMap.entrySet()) {
                String key = stairEntity.getKey().substring(0, 6);
                JSONObject value = stairEntity.getValue();
                LOGGER.info("修改i6000资产台账: " + JSONObject.toJSON(value));
                // 请求数据
                i6000ResultResps.add(writeUpdateCmCi(key, value));
            }
            return i6000ResultResps;

        } catch (Exception e) {
            CommonUtil.StringWriter(e, "设备变更系统异常!");
            throw new RuntimeException(e);
        }
    }

    private static void extracted(Map<String, Object> stairValue, Map<Object, Object> deviceAddMap, List<Map<String, Object>> deviceAddTypeList) {
        if (!stairValue.containsKey("deviceChangeTypeCode") && stairValue.containsKey("deviceAddTypeCode")) {
            String deviceAddType = deviceAddMap.get(stairValue.get("deviceAddTypeCode").toString()).toString();
            for (Map<String, Object> map1 : deviceAddTypeList) {
                Object o3 = map1.get(CmdbAttrConstant.DICT_VALUE);
                Object o4 = map1.get(CmdbAttrConstant.DICT_KEY);
                if (deviceAddType.equals(o3)) {
                    stairValue.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE, o4);
                    stairValue.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, o3);
                    break;
                }
            }
        }

        // 采购日期为空则, 出厂日期 == 采购日期
        if (!stairValue.containsKey("procureDate") && stairValue.containsKey("factoryDate")) {
            stairValue.put("procureDate", stairValue.get("factoryDate"));
        }
    }


    /**
     * 同步I6000默认填充数据
     *
     * @param stairValue
     */
    private void extracted(Map<String, Object> stairValue) {
        // 制造国家与地区(中国)
        if (!stairValue.containsKey(CmdbAttrConstant.MAINTENANCE_COUNTRY)) {
            stairValue.put(CmdbAttrConstant.MAINTENANCE_COUNTRY, cmdbCientityProperties.getCountryArea1());
        }
        // 所属网络(未联网)
        if (!stairValue.containsKey(CmdbAttrConstant.NET_WORK_CODE)) {
            stairValue.put(CmdbAttrConstant.NET_WORK_CODE, cmdbCientityProperties.getNetwork3());
        }
        // 是否同步给ERP
        if (!stairValue.containsKey(CmdbAttrConstant.IS_TO_ERP_CODE)) {
            stairValue.put(CmdbAttrConstant.IS_TO_ERP_CODE, cmdbCientityProperties.getYesNo());
        }
    }


    private Map<String, Object> getCiCientityListByCondition(Map<String, Object> stairValue) {
        CiCientitySearch cientitySearch = new CiCientitySearch();
        List<String> showAttrRelList = new ArrayList<>();
        // showAttrRelList.add(CmdbAttrConstant.I6000_CI_ID);
        // showAttrRelList.add(CmdbAttrConstant.OWNER_UNIT_CODE);
        // showAttrRelList.add(CmdbAttrConstant.RECEIVE_UNIT_CODE);
        cientitySearch.setShowAttrRelList(showAttrRelList);
        cientitySearch.setFullField(Boolean.TRUE);
        cientitySearch.setQuery(new Query().setCurrent(1).setSize(2));
        Object cmdbId = stairValue.get(CmdbAttrConstant.ID);
        if (Objects.isNull(cmdbId)) {
            throw new RuntimeException("查询CMDB参数 CMDB-ID 不能为空");
        }
        cientitySearch.setFilterCiEntityId((Long) cmdbId);

        FeignCiCientity ciCientityListByCondition = cmdbService.getCiCientityListByCondition(cientitySearch);
        List<Map<String, Object>> data = ciCientityListByCondition.getData();
        if (data.size() != 1) {
            throw new RuntimeException("信通一体化平台查询错误, size:" + data.size());
        }
        return data.get(0);
    }

    @Override
    public I6000ResultResp i6000BatchsaveDirect(String ciTypeId, Map<String, Map<String, Object>> entityMap) {
        IdevelopUser user = SecureUtil.getUser();
        try {
            if (StringUtils.equals("371724", user.getRegionCode())) {
                JSONObject jsonObject = getJsonObject();
                AtomicInteger num = new AtomicInteger(0);
                List<JSONObject> items = new ArrayList<>();
                for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {
                    String stairKey = stairEntity.getKey();
                    Map<String, Object> stairValue = stairEntity.getValue();
                    JSONObject item = new JSONObject();
                    item.put(CITYPE_ID, ciTypeId);
                    item.put(CI_ID, stairKey);
                    // TODO 运维单位默认巨鹿市
                    item.put(I6000AttrConstant.RUN_CORP_CODE, "ff8080815100833b0151717a9a500143");
                    item.putAll(stairValue);
                    items.add(item);
                }
                jsonObject.put(ITEMS, items);

                LOGGER.info("新增i6000资产台账(机房仓库): " + JSONObject.toJSON(jsonObject));
                // 设置请求头
                I6000ResultResp resultResp = writeAddCmCi(ciTypeId, jsonObject);

                return resultResp;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new I6000ResultResp();
    }

    @Override
    public List<I6000ResultResp> i6000BatchupdateDirect(Map<String, Map<String, Object>> entityMap) {
        if (thirdProperties.getApiI6000()) {
            try {

                IdevelopUser user = SecureUtil.getUser();
                String regionCode;
                if (Objects.isNull(user)) {
                    String firstKey = new ArrayList<>(entityMap.keySet()).get(0);
                    regionCode = entityMap.get(firstKey).get(CmdbAttrConstant.REGION_CODE).toString();
                } else {
                    regionCode = user.getRegionCode();
                }
                // TODO 后期删除
                if (StringUtils.equals("371724", regionCode)) {
                    JSONObject jsonObject = getJsonObject();
                    jsonObject.put(IMPORT_FLAG, ONE);

                    Boolean breakS = Boolean.FALSE;

                    AtomicInteger citypeNum = new AtomicInteger(0);
                    Map<String, JSONObject> requestMap = new HashMap<>();
                    for (Map.Entry<String, Map<String, Object>> stairEntity : entityMap.entrySet()) {
                        List<JSONObject> items = new ArrayList<>();
                        String stairKey = stairEntity.getKey();
                        Map<String, Object> stairValue = stairEntity.getValue();
                        // 默认填充数据
                        extracted(stairValue);

                        Map.Entry<String, Object> entry = stairValue.entrySet().stream().filter(map -> StringUtils.equals(map.getKey(), CITYPE_ID))
                                .findFirst().orElseThrow(() -> new RuntimeException("参数 CITYPE_ID 不能为空"));
                        String ciTypeId = String.valueOf(entry.getValue());

                        citypeNum.incrementAndGet();

                        // 获取对应模型的属性信息
                        JSONObject item = new JSONObject();
                        item.put(CITYPE_ID, ciTypeId);
                        item.put(CI_ID, stairKey);

                        // TODO 运维单位默认巨鹿市
                        item.put(I6000AttrConstant.RUN_CORP_CODE, "ff8080815100833b0151717a9a500143");

                        AtomicInteger num = new AtomicInteger(0);
                        stairValue.entrySet().forEach(entity -> {
                            num.incrementAndGet();
                            String key = entity.getKey();
                            Object value = entity.getValue();
                            item.put(key, value);
                        });
                        items.add(item);
                        jsonObject.put(ITEMS, items);
                        // 需要批量请求接口
                        requestMap.put(ciTypeId + "-" + citypeNum, jsonObject);
                    }
                    if (breakS) {
                        LOGGER.info("非巨鹿地市数据,不允许增加数据");
                        List<I6000ResultResp> i6000ResultResps = new ArrayList<>();
                        I6000ResultResp resultResp = new I6000ResultResp();
                        resultResp.setSuccessful(Boolean.FALSE.toString());
                        resultResp.setResultHint("非巨鹿地市数据,不允许增加数据");
                        resultResp.setResultValue("");
                        i6000ResultResps.add(resultResp);
                        return i6000ResultResps;
                    }
                    if (citypeNum.get() != entityMap.size()) {
                        throw new RuntimeException("请求参数与实际组装参数不一致, 请重新确认!");
                    }

                    List<I6000ResultResp> i6000ResultResps = new ArrayList<>();
                    for (Map.Entry<String, JSONObject> stairEntity : requestMap.entrySet()) {
                        String key = stairEntity.getKey().substring(0, 6);
                        JSONObject value = stairEntity.getValue();
                        LOGGER.info("修改i6000资产台账: " + JSONObject.toJSON(jsonObject));
                        // 请求数据
                        i6000ResultResps.add(writeUpdateCmCi(key, value));
                    }
                    return i6000ResultResps;
                }
                LOGGER.info("非巨鹿地市数据,不允许增加数据");
                List<I6000ResultResp> i6000ResultResps = new ArrayList<>();
                I6000ResultResp resultResp = new I6000ResultResp();
                resultResp.setSuccessful(Boolean.FALSE.toString());
                resultResp.setResultHint("非巨鹿地市数据,不允许增加数据");
                resultResp.setResultValue("");
                i6000ResultResps.add(resultResp);
                return i6000ResultResps;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 修改请求I6000数据
     *
     * @param ciTypeId
     * @param jsonObject
     * @return
     */
    private I6000ResultResp writeUpdateCmCi(String ciTypeId, JSONObject jsonObject) {
        I6000ResultResp resultResp = new I6000ResultResp();
        LOGGER.info("修改请求I6000数据: " + thirdProperties.getIsRequestI6000());
        if (thirdProperties.getIsRequestI6000()) {
            HttpHeaders httpHeaders = this.buildRequestHeaders();
            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, httpHeaders);

            String url = this.converUrl("/cmdb/write/i6000-api/updateCmCi/" + ciTypeId);

            JSONObject result = restTemplate.postForObject(url, requestEntity, JSONObject.class);
            resultResp.setSuccessful(String.valueOf(result.get("successful")));
            resultResp.setResultHint(String.valueOf(result.get("resultHint")));
            resultResp.setResultValue(String.valueOf(result.get("resultValue")));
        } else {
            resultResp.setSuccessful("true");
            resultResp.setResultHint(JSONObject.toJSONString(jsonObject));
            resultResp.setResultValue("");
        }
        return resultResp;

    }

    /**
     * 处理 i6000属性值源类型: 2-枚举数据，3-配置类型，4-外部数据 的值
     * 1-手工录入 : 直接返回信息
     * 2-枚举数据 : 根据 i6000AttrCode 查询到对应的cmdb 枚举模型 再进行处理
     * 3-配置类型 :
     * 4-外部数据 : 部分根据枚举值获取, 部分再根据 其他手段处理
     * {PROP_CORP 产权单位, PROP_DEPT 产权部门, RCVD_CORP 领用单位, RCVD_DEPT 领用部门, OH_CORP 检修单位, OH_DEPT 检修部门,
     * RUN_CORP_CODE 运维单位, RUN_DEPT 运维部门}
     * KEEP_DEPT 使用保管部门, MANAGE_DEPT    实物管理部门, RUN_USER_NUM    运维责任人账号 等特殊字段, 直接返回数据
     *
     * @param value
     * @param mapping
     * @return
     */
    private Object getObjectValue(Object value, I6000CmdbMapping mapping, Dept ownerUnit, Dept receiveUnit) {

        if (Objects.isNull(value) || "".equals(value)) {
            return value;
        }
        // 开始处理
        String i6000attrCode = mapping.getI6000AttrCode();
        String oriType = mapping.getI6000OriType();
        String cmdbAttrCode = mapping.getCmdbAttrCode();
        String cmdbAttrType = mapping.getCmdbAttrType();

        // 1- 普通类型
        if (StringUtils.equals("数字", cmdbAttrType)) {
            if (StringUtils.equalsAny(i6000attrCode, "HARDDISK_VOLUME", "MEMORY_SIZE", "MEMORY_TOTAL_SIZE")) {
                return (long) (((Double) value) * 1024 * 1024 * 1024);
            }
            return ((Double) value).intValue();
        }

        if (StringUtils.equals("日期", cmdbAttrType)) {
            return String.valueOf(value).replace("-", "") + "000000";
        }

        // 2-枚举数据
        if (StringUtils.equals(oriType, TWO)) {
            Long ciId = CmdbI6000Configuration.getCmdbCiIdByI6000(i6000attrCode, TYPE_ENUM);
            Map<Object, Object> dictMap = getDictI6000MapByCiId(ciId);
            return dictMap.get(String.valueOf(value));
        }
        // 3-配置类型
        if (StringUtils.equals(oriType, THREE)) {
            JSONObject jsonObject = new JSONObject();
            // 所属仓库  INSTALSITE
            if (StringUtils.equals(cmdbAttrCode, "inWarehouseCode")) {
                Warehouse warehouse = warehouseService.getById(String.valueOf(value));
                jsonObject.put("CI_ID", "");
                jsonObject.put("CI_NAME", "");
                jsonObject.put("CITYPE_ID", "T502");
                if (Objects.nonNull(warehouse)) {
                    jsonObject.put("CI_ID", warehouse.getI6000Uuid());
                    jsonObject.put("CI_NAME", warehouse.getI6000Name());
                }
                return jsonObject;
            }
            // 机房 INSTALSITE
            if (StringUtils.equals(cmdbAttrCode, "computerRoomCode")) {
                ResourceRoom resourceRoom = resourceRoomService.getById(String.valueOf(value));
                jsonObject.put("CI_ID", "");
                jsonObject.put("CI_NAME", "");
                jsonObject.put("CITYPE_ID", "T501");
                if (Objects.nonNull(resourceRoom)) {
                    jsonObject.put("CI_ID", resourceRoom.getI6000Uuid());
                    jsonObject.put("CI_NAME", resourceRoom.getI6000Name());
                }
                return jsonObject;
            }
            // 机柜 CABINET
            if (StringUtils.equals(cmdbAttrCode, "cabinetCode")) {
                if (Objects.isNull(receiveUnit)) {
                    receiveUnit = ownerUnit;
                }
                I6000Cabinet i6000Cabinet = new I6000Cabinet();
                i6000Cabinet.setI6000UnitId(receiveUnit.getI6000UnitCode());
                List<I6000Cabinet> i6000CabinetList = i6000CabinetService.list(Condition.getQueryWrapper(i6000Cabinet));
                I6000Cabinet i6000Cabinet1 = getRandomeByCabinet(i6000CabinetList);

                jsonObject.put("CI_ID", "");
                jsonObject.put("CI_NAME", "");
                jsonObject.put("CITYPE_ID", "T10603");
                if (Objects.nonNull(i6000Cabinet1)) {
                    jsonObject.put("CI_ID", i6000Cabinet1.getI6000CabinetId());
                    jsonObject.put("CI_NAME", i6000Cabinet1.getI6000CabinetName());
                }
                return jsonObject;
            }
        }
        // 4-外部数据
        if (StringUtils.equals(oriType, FOUR)) {
            // 产权单位, 运维单位
            if (StringUtils.equalsAny(i6000attrCode, RUN_CORP_CODE, PROP_CORP)) {
                return ownerUnit.getI6000UnitCode();
            }
            // 领用单位, 检修单位
            if (StringUtils.equalsAny(i6000attrCode, RCVD_CORP, OH_CORP)) {
                return receiveUnit.getI6000UnitCode();
            }
            // 产权部门, 运维部门
            if (StringUtils.equalsAny(i6000attrCode, PROP_DEPT, RUN_DEPT)) {
                String i6000UnitCode = ownerUnit.getI6000UnitCode();
                I6000Dept i6000Dept = new I6000Dept();
                i6000Dept.setI6000UnitId(i6000UnitCode);
                List<I6000Dept> i6000DeptList = i6000DeptMapper.selectList(Condition.getQueryWrapper(i6000Dept));
                I6000Dept i6000Dept1 = getRandomeByDept(i6000DeptList);
                return i6000Dept1.getI6000DeptId();
            }
            // 领用部门, 检修部门
            if (StringUtils.equalsAny(i6000attrCode, RCVD_DEPT, OH_DEPT)) {
                String i6000UnitCode = ownerUnit.getI6000UnitCode();
                I6000Dept i6000Dept = new I6000Dept();
                i6000Dept.setI6000UnitId(i6000UnitCode);
                List<I6000Dept> i6000DeptList = i6000DeptMapper.selectList(Condition.getQueryWrapper(i6000Dept));
                I6000Dept i6000Dept1 = getRandomeByDept(i6000DeptList);
                return i6000Dept1.getI6000DeptId();
            }
            // 线站标识, CPU品牌, 制造商, 开发厂商, 操作系统类型,
            if (StringUtils.equalsAny(i6000attrCode, BDZ, CPU_BRAND, MFR, OS_DATATYPE)) {
                Long ciId = CmdbI6000Configuration.getCmdbCiIdByI6000(i6000attrCode, TYPE_EXTERNAL);
                Map<Object, Object> dictMap = getDictI6000MapByCiId(ciId);
                return dictMap.get(String.valueOf(value));
            }
            // 制造商
            if (StringUtils.equals(i6000attrCode, MANUFACTURER)) {
                String makerKey = CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getMaker();
                Map<String, String> cache = (Map<String, String>) redisUtil.get(makerKey);
                return cache.get(String.valueOf(value));
            }
            // 品牌
            if (StringUtils.equals(i6000attrCode, BRAND)) {
                String brandKey = CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getBrand();
                Map<String, String> cache = (Map<String, String>) redisUtil.get(brandKey);
                return cache.get(String.valueOf(value));
            }
            // 系列
            if (StringUtils.equals(i6000attrCode, SERIES)) {
                String seriesKey = CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getSeries();
                Map<String, String> cache = (Map<String, String>) redisUtil.get(seriesKey);
                return cache.get(String.valueOf(value));
            }
            // 型号
            if (StringUtils.equals(i6000attrCode, MODEL)) {
                String modelKey = CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getModel();
                Map<String, String> cache = (Map<String, String>) redisUtil.get(modelKey);
                return cache.get(String.valueOf(value));
            }
            // 使用保管部门, 实物管理部门, 运维责任人账号, WBS元素, 功能位置, 维护工厂 直接返回
            return value;
        }

        // 5-特殊字段处理
        // mac地址格式转换
        // if (StringUtils.equals(attrCode, MAC_ADDR)) {
        //  return String.valueOf(value).replace(":", "-");
        // }

        return value;
    }

    private I6000Dept getRandomeByDept(List<I6000Dept> i6000DeptList) {
//		Random random = new Random();
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        int index = random.nextInt(i6000DeptList.size());
        return i6000DeptList.get(index);
    }

    private I6000Cabinet getRandomeByCabinet(List<I6000Cabinet> i6000CabinetList) {
//		Random random = new Random();
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        int index = random.nextInt(i6000CabinetList.size());
        return i6000CabinetList.get(index);
    }

    @NotNull
    private static JSONObject getJsonObject() {
        // TODO 修改此处
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(APP_CODE, "CMDB");
        jsonObject.put(USER_ID, "E885DB8B8428675CE0408D0A5B0463A0");
        jsonObject.put(RUN_CORP_CODE, "06");
        return jsonObject;
    }


    /**
     * 设置请求头
     *
     * @return
     */
    protected HttpHeaders buildRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (thirdProperties.getIsGeneralI6000()) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.add(ACCESS_TOKEN, "578d67f67c9471530a96dc25c67e13e414a0cf0f");
            requestHeaders.add(SIGN_DATA, "0409F93E1057F5012CECFECE91A899E0A4C3F350192F8757071C06CB0B635698947846FD124F7FFBCC2C89BC5B79937BD0018CE5B7A11BBE3054C6DD2D495187B7");
        } else {
            String accessToken = i6000Properties.getAccessToken();
            String publicKey = i6000Properties.getPublicKey();
            String authInfo = this.createAuthInfo(accessToken, publicKey);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.add(ACCESS_TOKEN, accessToken);
            requestHeaders.add(SIGN_DATA, authInfo);
        }
        return requestHeaders;
    }

    /**
     * 转换URL
     *
     * @param url
     * @return
     */
    protected String converUrl(String url) {
        return i6000Properties.getAddress() + url;
    }

    /**
     * 处理字段
     *
     * <p>
     * 外部数据
     * FUN_SITE 功能位置
     * MFR  开发厂商
     * WBS  WBS元素
     * OPDEP    维护工厂
     * <p>
     *
     * @param ciId
     * @return
     */
    public static Map<Object, Object> getDictI6000MapByCiId(Long ciId) {
        List<Map<String, Object>> data = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId).getData();
        return data.stream()
                .filter(item -> item.containsKey("dictKeyI6000"))
                .collect(Collectors.toMap(map -> map.get("dictKey"), map1 -> map1.get("dictKeyI6000")));
    }

    private static void stairValueExtracted(String key, Object objectValue, JSONObject item, I6000CmdbMapping mapping, Object value) {
        if (StringUtils.equalsAny(key, "maker", "makerCode", "brand", "brandCode", "series", "seriesCode", "deviceModel", "deviceModelCode")) {
            if ((objectValue != null && !"".equals(objectValue))) {
                item.put(mapping.getI6000AttrCode(), objectValue);
            } else {
                LOGGER.info("----组装数据结果3, 未查询到I6000系统:----参数: {}, 参数值: {}", key, value);
            }
        } else if (StringUtils.equalsAny(key, "oprtDate")) {
            // 投运日期 == 领用日期
            item.put(mapping.getI6000AttrCode(), objectValue);
            item.put("RCVD_DATE", objectValue);
            LOGGER.info("----组装数据结果4 ----参数: {}, 参数值: {}", "RCVD_DATE", objectValue);
        } else if (StringUtils.equalsAny(key, "receivingPerson")) {
            // 领用人联系方式 == 运维责任人名称
            item.put(mapping.getI6000AttrCode(), objectValue);
            item.put("RUN_USER_NAME", objectValue);
            LOGGER.info("----组装数据结果5 ----参数: {}, 参数值: {}", "RUN_USER_NAME", objectValue);
        }
        else {
            item.put(mapping.getI6000AttrCode(), objectValue);
        }
        LOGGER.info("----最终同步请求数据----参数: {}, 参数值: {}", key, objectValue);
    }

    @NotNull
    private static List<Map<String, Object>> getReturnList(String ciTypeId, String assetCodeErp, String deviceCodeErp) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        // I6000主键ID
        map1.put("CI_ID", "12356789111");
        // I6000设备分类
        map1.put("CITYPE_ID", ciTypeId);
        // ERP资产编码
        map1.put("ERP_ASSET_NO", assetCodeErp);
        // ERP设备台账编码
        map1.put("ERP_LEDGER_NO", deviceCodeErp);
        // WBS元素
        map1.put("WBS", "11111");
        // WBS元素显示元素
        map1.put("WBS_NAME", "11111");
        // 功能位置显示名称
        map1.put("FUN_SITE_NAME", "11111");
        // 功能位置
        map1.put("FUN_SITE", "11111");
        // 维护工厂显示名称
        map1.put("OPDEP_NAME", "11111");
        // 维护工厂显示名称
        map1.put("PROP_CORP_NAME", "12356789");
        returnList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("CI_ID", "123567892222");
        map1.put("CITYPE_ID", ciTypeId);
        map2.put("ERP_ASSET_NO", assetCodeErp);
        map2.put("ERP_LEDGER_NO", deviceCodeErp);
        map2.put("WBS", "11111");
        map2.put("WBS_NAME", "11111");
        map2.put("FUN_SITE_NAME", "11111");
        map2.put("FUN_SITE", "11111");
        map2.put("OPDEP_NAME", "11111");
        map2.put("PROP_CORP_NAME", "12356789");
        returnList.add(map2);
        return returnList;
    }
}

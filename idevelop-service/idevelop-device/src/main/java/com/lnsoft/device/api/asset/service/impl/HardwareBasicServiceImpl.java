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
package com.lnsoft.device.api.asset.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.cmdb.entity.HardwareBasic;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.cmdb.vo.HardwareBasicVO;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.I6000RequestDTO;
import com.lnsoft.device.api.asset.dto.WarehouseDetailDTO;
import com.lnsoft.device.api.asset.dto.XCTerminalDTO;
import com.lnsoft.device.api.asset.mapper.HardwareBasicMapper;
import com.lnsoft.device.api.asset.service.IHardwareBasicService;
import com.lnsoft.device.api.asset.vo.I6000ResponseVO;
import com.lnsoft.device.api.asset.wrapper.HardwareBasicWrapper;
import com.lnsoft.device.api.cmdb.mapper.IscDeptMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import com.lnsoft.device.api.i6000.dto.I6000CiCientityDTO;
import com.lnsoft.device.api.i6000.service.II6000CiAttrService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.entity.StockUnitDept;
import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.utils.XCTerminalListener;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.lnsoft.device.constant.CmdbAttrConstant.*;

/**
 * 资产台账模型树管理表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Service
@AllArgsConstructor
@Slf4j
public class HardwareBasicServiceImpl implements IHardwareBasicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareBasicServiceImpl.class);

    private RedisUtil redisUtil;

    private ICmdbService iCmdbService;
    private II6000Service i6000Service;
    private IHardwareBasicTreeService hardwareBasicTreeService;
    private OrderNumberUtil orderNumberUtil;
    private II6000CiAttrService i6000CiAttrService;
    private IZfitXtCwztService zfitXtCwztService;
    private CmdbDictProperties cmdbDictProperties;
    private CmdbCientityProperties cmdbCientityProperties;
    private ThirdProperties thirdProperties;
    private ICmdbClient cmdbClient;
    private IDeptClient deptClient;
    @Resource
    private IscDeptMapper iscDeptMapper;
    @Resource
    private HardwareBasicMapper hardwareBasicMapper;


    /**
     * 自定义分页 资产台账
     *
     * @param hardwareBasicVO
     * @return
     */
    @Override
    public JSONObject selectHardwareBasicTreePage(HardwareBasicVO hardwareBasicVO) {
        IdevelopUser user = SecureUtil.getUser();

        if (StringUtils.pathEquals("37", user.getRegionCode())) {
            List<HardwareBasic.CiEntitySearchAttr> attrFilterList = new ArrayList<>();
            HardwareBasic.CiEntitySearchAttr regionCodeAttr = new HardwareBasic.CiEntitySearchAttr();
            regionCodeAttr.setExpression(Expression.LIKE.getExpression());
            regionCodeAttr.setAttrId(1082371320643584L);
            regionCodeAttr.setValueList(Lists.newArrayList(user.getRegionCode()));
            attrFilterList.add(regionCodeAttr);

            List<HardwareBasic.CiEntitySearchAttr> oldAttrFilterList = hardwareBasicVO.getAttrFilterList();
            if (CollectionUtils.isEmpty(oldAttrFilterList)) {
                hardwareBasicVO.setAttrFilterList(attrFilterList);
            } else {
                oldAttrFilterList.addAll(attrFilterList);
            }
        }

        JSONObject jsonObject = HardwareBasicWrapper.build().cientitySearch(hardwareBasicVO);
        return jsonObject;
    }


    /**
     * 详情 资产台账
     *
     * @param feignCmdbCientityGet
     * @return
     */
    @Override
    public JSONObject getDetailOne(FeignCmdbCientityGet feignCmdbCientityGet) {
        JSONObject jsonObject = HardwareBasicWrapper.build().cientityGet(feignCmdbCientityGet);
        return jsonObject;
    }

    /**
     * 批量导入信创终端设备 [ls临时]
     *
     * @param file
     * @return
     */
    @Override
    public Boolean importByExcel(MultipartFile file, String deviceTypeName, String attrCode) {

        List<XCTerminalDTO> xcTerminalDTOList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            EasyExcel.read(inputStream, XCTerminalDTO.class, new XCTerminalListener(xcTerminalDTOList)).sheet().doRead();
        } catch (IOException e) {
            log.error("读取流失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败");
                }
            }
        }

        // 设备分类 (id + value)
        Map<Object, Object> deviceClaccifyMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify());
        // 设备类型 (id + value)
        Map<Object, Object> deviceTypeMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceType());
        // 工厂区域
        Map<String, Map<String, Object>> factoryAreaMap = cmdbDictProperties.getI6000MapByI6000(cmdbDictProperties.getFactoryAreaCode());

        // 需要请求CMBD的请求参数
        Map<String, Map<String, Map<String, Object>>> cmdbBatchsaveMap = new HashMap<>();
        for (XCTerminalDTO xcTerminalDTO : xcTerminalDTOList) {
            String assetCodeErp = xcTerminalDTO.getAssetCodeErp();
            if (ObjectUtil.isEmpty(xcTerminalDTO.getAssetCodeErp())) {
                continue;
            }

            if (StringUtils.hasLength(xcTerminalDTO.getDeviceType())) {
                deviceTypeName = xcTerminalDTO.getDeviceType();
            }
            HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().ciLabel(deviceTypeName).build();
            HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
            if (Objects.isNull(hardwareBasicTreeOne)) {
                throw new RuntimeException("ERP资产编码: " + assetCodeErp + ", 获取设备类型失败!");
            }
            // 组装请求cmdb的参数
            Map<String, Object> map = converMapCmdb(xcTerminalDTO, hardwareBasicTreeOne, deviceClaccifyMap, deviceTypeMap, factoryAreaMap);

            Long ciId = hardwareBasicTreeOne.getCiId();
            String uuid = UuidUtils.uuid();
            Map<String, Map<String, Object>> requestMap = new HashMap<>();
            requestMap.put(uuid, map);

            cmdbBatchsaveMap.put(ciId + "-" + uuid, requestMap);
        }
        for (Map.Entry<String, Map<String, Map<String, Object>>> entry : cmdbBatchsaveMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Map<String, Object>> value = entry.getValue();
            Long keyL = Long.valueOf(key.substring(0, key.indexOf('-')));
            iCmdbService.cientityBatchsave(keyL, value, TransactionActionType.INSERT);
        }

        return Boolean.TRUE;
    }

    /**
     * 根据条件获取 I6000 相关数据信息(数据治理修改功能)
     *
     * @param i6000Info
     * @return
     */
    @Override
    public Map<String, Object> selectInfoByI6000(I6000RequestDTO i6000Info) {
        try {
            return getErpResponseVO(i6000Info);
        } catch (Exception e) {
            throw new RuntimeException("ERP资产编码查询错误, 请输入正确的ERP资产编码.");
        }
    }

    private Map<String, Object> getErpResponseVO(I6000RequestDTO i6000Info) {
        // 请求ERP中资产数据
        ZfitXtCwzt zfitXtCwzt = new ZfitXtCwzt();
        zfitXtCwzt.setAnlnr(i6000Info.getAssetCodeErp());

        IdevelopUser user = SecureUtil.getUser();
        String erpUnitCode = user.getErpUnitCode();
        //根据维护工厂查询区域编码
        R<Dept> deptR = deptClient.getByErpUnitCode(erpUnitCode);
        Dept data = deptR.getData();
        String regionCode = data.getRegionCode();
        if (regionCode.length() > 4) {
            regionCode = regionCode.substring(0, 4);
        }
        R<List<Dept>> deptR1 = deptClient.getByRegionCodeControl(regionCode);
        List<String> erpUnitList = new ArrayList<>();
        if (deptR1.isSuccess()) {
            List<Dept> deptList = deptR1.getData();
            erpUnitList = deptList.stream().map(Dept::getErpUnitCode).collect(Collectors.toList());
        }
        QueryWrapper<ZfitXtCwzt> queryWrapper = Condition.getQueryWrapper(zfitXtCwzt);
        queryWrapper.in("swerk", erpUnitList);

        List<ZfitXtCwzt> zfitXtCwztList = zfitXtCwztService.list(queryWrapper);
        System.out.println("zfitXtCwztOne: " + zfitXtCwztList + ": " + JSONObject.toJSONString(zfitXtCwztList));

        if (CollectionUtil.isEmpty(zfitXtCwztList)) {
            throw new RuntimeException("ERP资产编码查询错误, 请输入正确的ERP资产编码.");
        }
        ZfitXtCwzt zfitXtCwztOne = zfitXtCwztList.get(0);


        Map<String, Object> map = new HashMap<>();

        // ERP设备编码
        map.put(CmdbAttrConstant.DEVICE_CODE_ERP, zfitXtCwztOne.getEqunr());

        // 工厂区域 beber
        String beber = zfitXtCwztOne.getBeber();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(beber)) {
            Map<String, Object> dictMapByErp = this.getDictMapByErp(cmdbDictProperties.getFactoryAreaCode(), beber, null);

            map.put(CmdbAttrConstant.FACTORY_AREA_CODE, dictMapByErp.get(DICT_KEY));
            map.put(CmdbAttrConstant.FACTORY_AREA, dictMapByErp.get(DICT_VALUE));
        }

        // 维护工厂
        map.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, zfitXtCwztOne.getSwerk());
        map.put(CmdbAttrConstant.MAINTENANCE_FACTORY, zfitXtCwztOne.getSwerkT());

        // 实物保管部门
        map.put(CmdbAttrConstant.REAL_MANAGE_DEPT, zfitXtCwztOne.getZsb002());
        map.put(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME, zfitXtCwztOne.getZsb002T());

        // 使用保管部门(成本中心)
        map.put(CmdbAttrConstant.USE_KEEP_DEPT, zfitXtCwztOne.getKostl());
        map.put(CmdbAttrConstant.USE_KEEP_DEPT_NAME, zfitXtCwztOne.getKostlT());

        // 功能位置
        map.put(CmdbAttrConstant.FUN_LOCATION, zfitXtCwztOne.getTplnrT());
        map.put(CmdbAttrConstant.FUN_LOCATION_CODE, zfitXtCwztOne.getTplnr());

        // 设备增加方式
        String zsb005 = zfitXtCwztOne.getZsb005();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(zsb005)) {
            if (zsb005.length() == 1) {
                zsb005 = "00" + zsb005;
            }
            if (zsb005.length() == 2) {
                zsb005 = "0" + zsb005;
            }
            Map<String, Object> dictMapByErp = this.getDictMapByErp(cmdbDictProperties.getDeviceAddType(), zsb005, null);

            map.put(DEVICE_ADD_TYPE_CODE, dictMapByErp.get(DICT_KEY));
            map.put(DEVICE_ADD_TYPE, dictMapByErp.get(DICT_VALUE));

            String deviceAddType = dictMapByErp.get(DICT_VALUE).toString();
            Map<String, Object> dictMapByErp1 = this.getDictMapByErp(cmdbDictProperties.getDeviceChangeType(), null, deviceAddType);

            // 设备变动方式
            map.put(DEVICE_CHANGE_TYPE_CODE, dictMapByErp1.get(DICT_KEY));
            map.put(DEVICE_CHANGE_TYPE, dictMapByErp.get(DICT_VALUE));
        }

        // 计量单位
        String zcabnZtpm1006 = zfitXtCwztOne.getZcabnZtpm1006();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(zcabnZtpm1006)) {
            Map<String, Object> dictMapByErp = this.getDictMapByErp(cmdbDictProperties.getUnifiedCode(), zcabnZtpm1006, null);
            map.put(CmdbAttrConstant.MEASURE_UNIT, dictMapByErp.get(DICT_KEY));
        }

        // WBS元素
        map.put(CmdbAttrConstant.WBS_ELEMENT, zfitXtCwztOne.getPosid());
        // WBS项目
        map.put(CmdbAttrConstant.WBS_ELEMENT_NAME, zfitXtCwztOne.getPosidT());
        // 项目定义
        map.put(CmdbAttrConstant.PROJECT_DEFINE, zfitXtCwztOne.getPspid());
        // 项目名称
        map.put(CmdbAttrConstant.PROJECT_NAME, zfitXtCwztOne.getPspidT());

        // 资产原值(累计购置价值) assetOriginal
        map.put(CmdbAttrConstant.ASSET_ORIGINAL, zfitXtCwztOne.getZcYz());

        // 资产净值
        map.put(CmdbAttrConstant.NET_WORTH, zfitXtCwztOne.getZcJz());

        // 制造国家和地区
        map.put(CmdbAttrConstant.MAINTENANCE_COUNTRY, cmdbCientityProperties.getCountryArea1());

        // 是否转资
        map.put(CmdbAttrConstant.ERP_TRANSFER_STATUS, cmdbCientityProperties.getErpTransferStatus1());

        // 线站标识 LINE_STATION
        map.put(CmdbAttrConstant.LINE_STATION, "00000000000000000");
        map.put(CmdbAttrConstant.LINE_STATION_SIGN, "00000000000000000");

        return map;
    }

    private I6000ResponseVO getI6000ResponseVO(I6000RequestDTO i6000Info) {
        if (!StringUtils.hasLength(i6000Info.getDeviceTypeCode())) {
            throw new RuntimeException("请选择设备类型!");
        }
        HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(i6000Info.getDeviceTypeCode()).build();
        HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
        if (Objects.isNull(hardwareBasicTreeOne)) {
            throw new RuntimeException("选择的设备类型在I6000模型中不存在!");
        }

        String ciTypeId = hardwareBasicTreeOne.getI6000Code();

        I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
        i6000CiCientityDTO.setAttrCode("ERP_LEDGER_NO," +
                "WBS,WBS_NAME,ITEM_NO,ITEM,FUN_SITE_NAME,FUN_SITE,OPDEP_NAME,OPDEP,INIT_ASSET_VALUE,BEBER");

        List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();
        I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
        conditions1.setOperator("=");
        conditions1.setAttrCode(I6000AttrConstant.ERP_ASSET_NO);
        conditions1.setValue(i6000Info.getAssetCodeErp());
        orConditionList.add(conditions1);

        I6000CiCientityDTO.Conditions conditions3 = new I6000CiCientityDTO.Conditions();
        conditions3.setOperator("=");
        conditions3.setAttrCode("OPDEP");
        String regionCode = SecureUtil.getUser().getRegionCode();
        R<List<Dept>> byRegionCode = deptClient.getByRegionCode(regionCode);
        String erpUnitCode = null;
        if (ObjectUtil.isNotEmpty(byRegionCode.getData())) {
            erpUnitCode = byRegionCode.getData().get(0).getErpUnitCode();
        }
        conditions3.setValue(erpUnitCode);
        orConditionList.add(conditions3);

        i6000CiCientityDTO.setConditions(orConditionList);
        i6000CiCientityDTO.setPageStart("1");
        i6000CiCientityDTO.setPageSize("10");

        if (thirdProperties.getIsGovernProperty()) {
            LOGGER.info("根据条件获取 I6000 相关数据信息 请求I6000参数: ciTypeId = {}, i6000CiCientityDTO = {}", ciTypeId, JSONObject.toJSONString(i6000CiCientityDTO));
            List<Map<String, Object>> i6000ResultMap = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);
            LOGGER.info("根据条件获取 I6000 相关数据信息 返回I6000数据: i6000ResultMap = {}", JSONObject.toJSONString(i6000ResultMap));
            if (CollectionUtils.isEmpty(i6000ResultMap)) {
                throw new RuntimeException("ERP资产编码查询错误, 请输入正确的ERP资产编码.");
            }

            Map<String, Object> i6000Map = i6000ResultMap.get(0);

            I6000ResponseVO i6000ResponseVO = I6000ResponseVO.builder()
                    .assetCodeErp(i6000Info.getAssetCodeErp())
                    .deviceCodeErp(Objects.isNull(i6000Map.get(I6000AttrConstant.ERP_LEDGER_NO)) ? "" : (String) i6000Map.get(I6000AttrConstant.ERP_LEDGER_NO))
                    .wbsElement(Objects.isNull(i6000Map.get(I6000AttrConstant.WBS)) ? "" : (String) i6000Map.get(I6000AttrConstant.WBS))
                    .wbsElementName(Objects.isNull(i6000Map.get(I6000AttrConstant.WBS_NAME)) ? "" : (String) i6000Map.get(I6000AttrConstant.WBS_NAME))
                    .projectCode(Objects.isNull(i6000Map.get(I6000AttrConstant.ITEM_NO)) ? "" : (String) i6000Map.get(I6000AttrConstant.ITEM_NO))
                    .projectName(Objects.isNull(i6000Map.get(I6000AttrConstant.ITEM)) ? "" : (String) i6000Map.get(I6000AttrConstant.ITEM))
                    .funLocation(Objects.isNull(i6000Map.get(I6000AttrConstant.FUN_SITE_NAME)) ? "" : (String) i6000Map.get(I6000AttrConstant.FUN_SITE_NAME))
                    .funLocationCode(Objects.isNull(i6000Map.get(I6000AttrConstant.FUN_SITE)) ? "" : (String) i6000Map.get(I6000AttrConstant.FUN_SITE))
                    .maintenanceFactory(Objects.isNull(i6000Map.get(I6000AttrConstant.OPDEP_NAME)) ? "" : (String) i6000Map.get(I6000AttrConstant.OPDEP_NAME))
                    .maintenanceFactoryCode(Objects.isNull(i6000Map.get(I6000AttrConstant.OPDEP)) ? "" : (String) i6000Map.get(I6000AttrConstant.OPDEP))
                    .assetOriginal(Objects.isNull(i6000Map.get(I6000AttrConstant.INIT_ASSET_VALUE)) ? "" : (String) i6000Map.get(I6000AttrConstant.INIT_ASSET_VALUE))
                    .build();

            // 工厂区域
            Map<String, Map<String, Object>> factoryAreaMap = cmdbDictProperties.getI6000MapByI6000(cmdbDictProperties.getFactoryAreaCode());
            if (i6000Map.containsKey(I6000AttrConstant.BEBER)) {
                String beber = (String) i6000Map.get(I6000AttrConstant.BEBER);
                Map<String, Object> beberMap = factoryAreaMap.get(beber);
                i6000ResponseVO.setFactoryAreaCode((String) beberMap.get("dictKey"));
                i6000ResponseVO.setFactoryArea((String) beberMap.get("dictValue"));
            }
            return i6000ResponseVO;

        } else {
            // 线下测试用
            return new I6000ResponseVO();
        }
    }

    @Override
    public Boolean xcUpdate(List<String> assetCodeErpList, String attrCode) {
        // 暂时写死
        attrCode = I6000AttrConstant.CI_NAME + "," + I6000AttrConstant.CYCLE_STATUS + "," + I6000AttrConstant.FIRST_RUN_DATE + "," + I6000AttrConstant.MADE_COUNTRY + "," +
                I6000AttrConstant.MANUFACTURER + "," + I6000AttrConstant.BRAND + "," + I6000AttrConstant.SERIES + "," + I6000AttrConstant.MODEL + "," +
                I6000AttrConstant.MEMORY_SIZE_ONE + "," + I6000AttrConstant.HARDDISK_VOLUME_GB + "," + I6000AttrConstant.CPU_ARCHITEC + "," +
                I6000AttrConstant.CPU_BRAND + "," + I6000AttrConstant.NETWORK + "," + I6000AttrConstant.RUN_DATE + "," + I6000AttrConstant.MAC_ADDR + "," +
                I6000AttrConstant.IP_ADDR + "," + I6000AttrConstant.OS_VER + "," + I6000AttrConstant.OS_RELEASE_VERSION + "," + I6000AttrConstant.RCVD_DATE + "," +
                I6000AttrConstant.RELEASE_DATE + "," + I6000AttrConstant.PUR_DATE + "," + I6000AttrConstant.USE_PEOPLE + "," + I6000AttrConstant.USE_UNIFIED_ACCOUNT + "," +
                I6000AttrConstant.BEBER + "," + I6000AttrConstant.ITEM + "," + I6000AttrConstant.MANAGE_DEPT + "," + I6000AttrConstant.MANAGE_DEPT_NAME + "," +
                I6000AttrConstant.KEEP_DEPT + "," + I6000AttrConstant.KEEP_DEPT_NAME + "," + I6000AttrConstant.ASSET_CHANGE + "," + I6000AttrConstant.SYNC_ERP_FLAG + "," +
                I6000AttrConstant.ASSET_ADD + "," + I6000AttrConstant.ERP_LEDGER_NO + "," + I6000AttrConstant.DEVICE_ASSET_FAIL + "," + I6000AttrConstant.ERP_ASSET_STATE + "," +
                I6000AttrConstant.OPDEP + "," + I6000AttrConstant.WBS_NAME + "," + I6000AttrConstant.WBS + "," + I6000AttrConstant.FUN_SITE + "," +
                I6000AttrConstant.FUN_SITE_NAME + "," + I6000AttrConstant.OS_DATATYPE + "," + I6000AttrConstant.INIT_ASSET_VALUE + "," + I6000AttrConstant.CPU_MODEL + "," +
                I6000AttrConstant.CPU_FRQUENCY + "," + I6000AttrConstant.PUR_NO + "," + I6000AttrConstant.PUR_MODE + "," + I6000AttrConstant.SUP_TEL + "," +
                I6000AttrConstant.SUP_CONTACT + "," + I6000AttrConstant.SRV_COMPANY + "," + I6000AttrConstant.SRV_COMPANY_NAME + "," + I6000AttrConstant.SRV_CONTACT + "," +
                I6000AttrConstant.SRV_REQUIRED + "," + I6000AttrConstant.SRV_NO + "," + I6000AttrConstant.OPDEP_NAME;
        // 工厂区域
        Map<String, Map<String, Object>> factoryAreaMap = cmdbDictProperties.getI6000MapByI6000(cmdbDictProperties.getFactoryAreaCode());
        //通过erp资产编码查询台账数据
        Query query = new Query();
        query.setCurrent(1);
        query.setSize(500);
        List<CiCientitySearchVO> cientitySearchVOS = new ArrayList<>();
        CiCientitySearchVO searchVO = CiCientitySearchVO.builder()
                .expression(Expression.EQUAL)
                .attrName(CmdbAttrConstant.ASSET_CODE_ERP)
                .isBatch(true)
                .attrValue(StringUtil.join(assetCodeErpList.toArray(), "--")).build();
        cientitySearchVOS.add(searchVO);
        FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(cientitySearchVOS, query);
        List<Map<String, Object>> data = ciCientityListByClaccify.getData();
        //获取i6000数据进行组装参数
        for (Map<String, Object> xcDevice : data) {
            Map<Long, Map<String, Object>> map = new HashMap<>();
            try {
                HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().ciLabel(String.valueOf(xcDevice.get(CmdbAttrConstant.DEVICE_TYPE))).build();
                HardwareBasicTree hardwareBasicTreeOne = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));

                String ciTypeId = hardwareBasicTreeOne.getI6000Code();
                List<Map<String, Object>> i6000ResultList = selectI6000ResultMap(xcDevice, ciTypeId, attrCode);
                Map<String, Object> i6000Map = i6000ResultList.get(0);
                Map<String, Object> convertMap = convertMap(i6000Map, factoryAreaMap, xcDevice);
                convertMap.put(CmdbAttrConstant.CI_ID, xcDevice.get(CmdbAttrConstant.CI_ID));
                convertMap.put(CmdbAttrConstant.UUID, xcDevice.get(CmdbAttrConstant.UUID));
                //组装cmdb更新参数
                String id = String.valueOf(xcDevice.get("id"));
                map.put(Long.valueOf(id), convertMap);
                //更新cmdb台账
                iCmdbService.cientityBatchupdate(map, TransactionActionType.UPDATE);
            } catch (Exception e) {
                LOGGER.error("信创设备更新CMDB错误: " + JSON.toJSON(map));
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public List<WarehouseDetailDTO> warehouse(String deviceCode) {
        List<WarehouseDetailDTO> warehouseDetailDTOList = hardwareBasicMapper.warehouse(deviceCode);
        return warehouseDetailDTOList;
    }

    @Override
    public Object apply(String deviceCode) {
        List<Object> list = hardwareBasicMapper.apply(deviceCode);
        return list;
    }

    @Override
    public Object operation(String deviceCode) {
        List<Object> list = hardwareBasicMapper.operation(deviceCode);
        return list;
    }

    @Override
    public Object change(String deviceCode) {
        List<Object> list = hardwareBasicMapper.change(deviceCode);
        return list;
    }

    @Override
    public Object repair(String deviceCode) {
        List<Object> list = hardwareBasicMapper.repair(deviceCode);
        return list;
    }

    @NotNull
    private Map<String, Object> converMapCmdb(XCTerminalDTO xcTerminalDTO,
                                              HardwareBasicTree hardwareBasicTree,
                                              Map<Object, Object> deviceClaccifyMap,
                                              Map<Object, Object> deviceTypeMap, Map<String, Map<String, Object>> factoryAreaMap) {
        Map<String, Object> map = new HashMap<>();
        // 设备来源
        map.put(CmdbAttrConstant.DEVICE_SOURCE_CODE, cmdbCientityProperties.getCientityId(CmdbCientityConstant.DEVICE_SOURCE));
        map.put(CmdbAttrConstant.DEVICE_SOURCE, "统一纳管");
        // 设备状态
        map.put(CmdbAttrConstant.DEVICE_STATUS_CODE, cmdbCientityProperties.getCientityId(CmdbCientityConstant.DEVICE_STATUS_0));
        map.put(CmdbAttrConstant.DEVICE_STATUS, "库存备用");
        // 是否信创终端
        map.put(CmdbAttrConstant.IS_IT_AI_CODE, cmdbCientityProperties.getCientityId(CmdbCientityConstant.YES));
        // 设备分类
        map.put(CmdbAttrConstant.DEVICE_CATEGORY_CODE, hardwareBasicTree.getDeviceClaccify());
        map.put(CmdbAttrConstant.DEVICE_CATEGORY, deviceClaccifyMap.get(hardwareBasicTree.getDeviceClaccify()));
        // 设备类型
        map.put(CmdbAttrConstant.DEVICE_TYPE_CODE, hardwareBasicTree.getDeviceType());
        map.put(CmdbAttrConstant.DEVICE_TYPE, deviceTypeMap.get(hardwareBasicTree.getDeviceType()));

        // 产权单位
        R<Dept> codeByName = deptClient.getCodeByName(xcTerminalDTO.getUnit(), "");
        Long unitCode = 0L;
        if (ObjectUtil.isNotEmpty(codeByName.getData())) {
            unitCode = codeByName.getData().getId();
        }
        map.put(CmdbAttrConstant.OWNER_UNIT_CODE, unitCode);
        map.put(CmdbAttrConstant.OWNER_UNIT, xcTerminalDTO.getUnit());
        // 区域编码
        String regionCode = codeByName.getData().getRegionCode();
        map.put(CmdbAttrConstant.AREA, regionCode);

        // 产权部门
        R<Dept> deptR = deptClient.getCodeByName(xcTerminalDTO.getDept(), String.valueOf(unitCode));
        Long deptCode = 0L;
        if (ObjectUtil.isNotEmpty(deptR.getData())) {
            deptCode = deptR.getData().getId();
        }
        map.put(CmdbAttrConstant.PROPERTY_DEPT_CODE, deptCode);
        map.put(CmdbAttrConstant.PROPERTY_DEPT, xcTerminalDTO.getDept());
        //部门
        map.put(CmdbAttrConstant.DEPT, deptCode);

        // 运维单位
        map.put(CmdbAttrConstant.OPERATION_UNIT_CODE, unitCode);
        map.put(CmdbAttrConstant.OPERATION_UNIT, xcTerminalDTO.getUnit());
        // 运维部门
        map.put(CmdbAttrConstant.OPERATION_DEP_CODE, deptCode);
        map.put(CmdbAttrConstant.OPERATION_DEPT, xcTerminalDTO.getDept());

        // 采购方式
        map.put(CmdbAttrConstant.PROCURE_TYPE_CODE, cmdbCientityProperties.getCientityId(CmdbCientityConstant.PROCURE_TYPE_0));
        // 设备名称
        map.put(CmdbAttrConstant.DEVICE_NAME, hardwareBasicTree.getCiLabel());
        // 设备编码
        map.put(CmdbAttrConstant.DEVICE_CODE, orderNumberUtil.generateCode(hardwareBasicTree.getDeviceType(), regionCode));
        // 服务商
        map.put(CmdbAttrConstant.SERVICE_NAME, xcTerminalDTO.getService());
        map.put(CmdbAttrConstant.SERVICE_FULL_NAME, xcTerminalDTO.getService());
        // ERP资产编码
        map.put(CmdbAttrConstant.ASSET_CODE_ERP, xcTerminalDTO.getAssetCodeErp());
        // 出厂序列号
        map.put(CmdbAttrConstant.SN, xcTerminalDTO.getSn());
        //售后服务到期时间
        map.put(CmdbAttrConstant.AFTER_SALE_EXP_DATE, xcTerminalDTO.getAfterSaleExpDate());
        // 品牌
        FeignCmdbDictCientitySearch search01 = new FeignCmdbDictCientitySearch();
        search01.setDictValue(xcTerminalDTO.getBrand());
        search01.setCiId(cmdbDictProperties.getBrand());
        R<List<Map<String, Object>>> listR01 = cmdbClient.feignGetCiEntityDictListById(search01);
        String seriesPid = null;
        if (ObjectUtil.isNotEmpty(listR01.getData())) {
            seriesPid = listR01.getData().get(0).get("dictKey").toString();
        }
        map.put(CmdbAttrConstant.BRAND_CODE, seriesPid);
        map.put(CmdbAttrConstant.BRAND, xcTerminalDTO.getBrand());
        // 序列
        FeignCmdbDictCientitySearch search02 = new FeignCmdbDictCientitySearch();
        search02.setDictValue(xcTerminalDTO.getSeries());
        search02.setCiId(cmdbDictProperties.getSeries());
        search02.setPid(seriesPid);
        R<List<Map<String, Object>>> listR02 = cmdbClient.feignGetCiEntityDictListById(search02);
        String deviceModelPid = null;
        if (ObjectUtil.isNotEmpty(listR02.getData())) {
            deviceModelPid = listR02.getData().get(0).get("dictKey").toString();
        }
        map.put(CmdbAttrConstant.SERIES, xcTerminalDTO.getSeries());
        map.put(CmdbAttrConstant.SERIES_CODE, deviceModelPid);
        // 型号
        FeignCmdbDictCientitySearch search03 = new FeignCmdbDictCientitySearch();
        search03.setDictValue(xcTerminalDTO.getDeviceModel());
        search03.setCiId(cmdbDictProperties.getModel());
        search03.setPid(deviceModelPid);
        R<List<Map<String, Object>>> listR03 = cmdbClient.feignGetCiEntityDictListById(search03);
        String deviceModel = null;
        if (ObjectUtil.isNotEmpty(listR03.getData())) {
            deviceModel = listR03.getData().get(0).get("dictKey").toString();
        }
        map.put(CmdbAttrConstant.DEVICE_MODEL, xcTerminalDTO.getDeviceModel());
        map.put(CmdbAttrConstant.DEVICE_MODEL_CODE, deviceModel);

        return map;
    }

    @NotNull
    private Map<String, Object> convertMap(Map<String, Object> i6000Map, Map<String, Map<String, Object>> factoryAreaMap, Map<String, Object> xcDevice) {
        Map<String, Object> map = new HashMap<>();

        // 标准全称CI_NAME
        if (i6000Map.containsKey(I6000AttrConstant.CI_NAME)) {
            map.put(CmdbAttrConstant.FULL_NAME, ObjectUtil.isNotEmpty(i6000Map.get(I6000AttrConstant.CI_NAME)) ? i6000Map.get(I6000AttrConstant.CI_NAME) : "台式机");
        }

        // 设备状态
        if (i6000Map.containsKey(I6000AttrConstant.CYCLE_STATUS)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.CYCLE_STATUS, cmdbDictProperties.getDeviceStatus());
            Object dictKey = dictMap.get(CmdbAttrConstant.DICT_KEY);
            map.put(CmdbAttrConstant.DEVICE_STATUS, dictMap.get(CmdbAttrConstant.DICT_VALUE));
            map.put(CmdbAttrConstant.DEVICE_STATUS_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
            if (Objects.nonNull(dictKey) && dictKey.equals(cmdbCientityProperties.getInOperation())) {
                // 领用单位
                map.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, xcDevice.get(CmdbAttrConstant.OWNER_UNIT_CODE));
                map.put(CmdbAttrConstant.RECEIVE_UNIT, xcDevice.get(CmdbAttrConstant.OWNER_UNIT));
                // 领用部门
                map.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, xcDevice.get(CmdbAttrConstant.PROPERTY_DEPT_CODE));
                map.put(CmdbAttrConstant.RECEIVE_DEPT, xcDevice.get(CmdbAttrConstant.PROPERTY_DEPT));
                //使用人
                if (i6000Map.containsKey(I6000AttrConstant.USE_PEOPLE)) {
                    map.put(CmdbAttrConstant.USER, i6000Map.get(I6000AttrConstant.USE_PEOPLE));
                }
                //使用人联系方式
                if (i6000Map.containsKey(I6000AttrConstant.RCVD_USER)) {
                    map.put(CmdbAttrConstant.USER_TEL, i6000Map.get(I6000AttrConstant.RCVD_USER));
                }
                //使用人身份证
                if (i6000Map.containsKey(I6000AttrConstant.USE_UNIFIED_ACCOUNT)) {
                    map.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, i6000Map.get(I6000AttrConstant.USE_UNIFIED_ACCOUNT));
                }
            }
        }
        // 首次投运时间
        if (i6000Map.containsKey(I6000AttrConstant.FIRST_RUN_DATE)) {
            String dateStr = i6000Map.get(I6000AttrConstant.FIRST_RUN_DATE).toString();
            String oprtDateFirst = converToTime(I6000AttrConstant.FIRST_RUN_DATE, dateStr);
            map.put(CmdbAttrConstant.OPRT_DATE_FIRST, oprtDateFirst);
        }
        // 制造国家和地区
        if (i6000Map.containsKey(I6000AttrConstant.MADE_COUNTRY)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.MADE_COUNTRY, cmdbDictProperties.getCountryArea());
            map.put(CmdbAttrConstant.MAINTENANCE_COUNTRY, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }
        // // 制造商
        if (i6000Map.containsKey(I6000AttrConstant.MANUFACTURER)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.MANUFACTURER, cmdbDictProperties.getMaker());
            if (!CollectionUtils.isEmpty(dictMap)) {
                map.put(CmdbAttrConstant.MAKER, dictMap.get(CmdbAttrConstant.DICT_VALUE));
                map.put(CmdbAttrConstant.MAKER_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
            }
        }
        // // 品牌
        // if (i6000Map.containsKey(I6000AttrConstant.BRAND)) {
        // 	Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.BRAND, cmdbDictProperties.getBrand());
        // 	map.put(CmdbAttrConstant.BRAND_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        // 	map.put(CmdbAttrConstant.BRAND, dictMap.get(CmdbAttrConstant.DICT_VALUE));
        // }
        // // 序列
        // if (i6000Map.containsKey(I6000AttrConstant.SERIES)) {
        // 	Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.SERIES, cmdbDictProperties.getSeries());
        // 	map.put(CmdbAttrConstant.SERIES_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        // 	map.put(CmdbAttrConstant.SERIES, dictMap.get(CmdbAttrConstant.DICT_VALUE));
        // }
        // // 型号MODEL
        // if (i6000Map.containsKey(I6000AttrConstant.MODEL)) {
        // 	Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.MODEL, cmdbDictProperties.getModel());
        // 	map.put(CmdbAttrConstant.DEVICE_MODEL_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        // 	map.put(CmdbAttrConstant.DEVICE_MODEL, dictMap.get(CmdbAttrConstant.DICT_VALUE));
        // }
        // 内存大小
        if (i6000Map.containsKey(I6000AttrConstant.MEMORY_SIZE_ONE)) {
            map.put(CmdbAttrConstant.MEM_SIZE, i6000Map.get(I6000AttrConstant.MEMORY_SIZE_ONE));
        }
        // 硬盘容量
        if (i6000Map.containsKey(I6000AttrConstant.HARDDISK_VOLUME_GB)) {
            map.put(CmdbAttrConstant.HARD_DISK_CAPABILITY, i6000Map.get(I6000AttrConstant.HARDDISK_VOLUME_GB));
        }
        // CPU架构
        if (i6000Map.containsKey(I6000AttrConstant.CPU_ARCHITEC)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.CPU_ARCHITEC, cmdbDictProperties.getCpuArchCode());
            map.put(CmdbAttrConstant.CPU_ARCH_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }
        // CPU品牌 CPU_BRAND
        if (i6000Map.containsKey(I6000AttrConstant.CPU_BRAND)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.CPU_BRAND, cmdbDictProperties.getCpuBrand());
            map.put(CmdbAttrConstant.CPU_BRAND_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
            map.put(CmdbAttrConstant.CPU_BRAND, dictMap.get(CmdbAttrConstant.DICT_VALUE));
        }

        // 所属网络
        if (i6000Map.containsKey(I6000AttrConstant.NETWORK)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.NETWORK, cmdbDictProperties.getNetWorkCode());
            map.put(CmdbAttrConstant.NET_WORK_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }
        // 投运日期
        if (i6000Map.containsKey(I6000AttrConstant.RUN_DATE)) {
            String dateStr = i6000Map.get(I6000AttrConstant.RUN_DATE).toString();
            String dateConver = converToTime(I6000AttrConstant.RUN_DATE, dateStr);
            map.put(CmdbAttrConstant.OPRT_DATE, dateConver);
        }
        // MAC地址
        if (i6000Map.containsKey(I6000AttrConstant.MAC_ADDR)) {
            String mac = String.valueOf(i6000Map.get(I6000AttrConstant.MAC_ADDR));
            String replace = mac.replace("-", ":");
            map.put(CmdbAttrConstant.MAC, replace);
        }
        // IP地址
        if (i6000Map.containsKey(I6000AttrConstant.IP_ADDR)) {
            map.put(CmdbAttrConstant.IP, i6000Map.get(I6000AttrConstant.IP_ADDR));
        }
        // 操作系统版本号
        if (i6000Map.containsKey(I6000AttrConstant.OS_VER)) {
            map.put(CmdbAttrConstant.OS_VERSION, i6000Map.get(I6000AttrConstant.OS_VER));
        }
        // 操作系统发行版本
        if (i6000Map.containsKey(I6000AttrConstant.OS_RELEASE_VERSION)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.OS_RELEASE_VERSION, cmdbDictProperties.getOSIssueVersion());
            map.put(CmdbAttrConstant.OS_ISSUE_VERSION, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }
        // 领用日期
        if (i6000Map.containsKey(I6000AttrConstant.RCVD_DATE)) {
            String dateStr = i6000Map.get(I6000AttrConstant.RCVD_DATE).toString();
            String dateConver = converToTime(I6000AttrConstant.RCVD_DATE, dateStr);
            map.put(CmdbAttrConstant.RECEIVING_DATE, dateConver);
        }
        // 出厂日期
        if (i6000Map.containsKey(I6000AttrConstant.RELEASE_DATE)) {
            String dateStr = i6000Map.get(I6000AttrConstant.RELEASE_DATE).toString();
            String dateConver = converToTime(I6000AttrConstant.RELEASE_DATE, dateStr);
            map.put(CmdbAttrConstant.FACTORY_DATE, dateConver);
        }
        // 采购日期
        if (i6000Map.containsKey(I6000AttrConstant.PUR_DATE)) {
            String dateStr = i6000Map.get(I6000AttrConstant.PUR_DATE).toString();
            String dateConver = converToTime(I6000AttrConstant.PUR_DATE, dateStr);
            map.put(CmdbAttrConstant.PROCURE_DATE, dateConver);
        }
        // 使用人 -> 责任人
        if (i6000Map.containsKey(I6000AttrConstant.USE_PEOPLE)) {
            map.put(CmdbAttrConstant.RECEIVING_PERSON, i6000Map.get(I6000AttrConstant.USE_PEOPLE));
        }
        // 使用人统一权限账号 -> 责任人统一权限账号  使用人统一权限账号 -> 责任人身份证号
        if (i6000Map.containsKey(I6000AttrConstant.USE_UNIFIED_ACCOUNT)) {
            map.put(CmdbAttrConstant.RECEIVE_PERSON_UNIFIED_ACC, i6000Map.get(I6000AttrConstant.USE_UNIFIED_ACCOUNT));
            map.put(CmdbAttrConstant.RECEIVING_ID_CARD, i6000Map.get(I6000AttrConstant.USE_UNIFIED_ACCOUNT));
        }
        // 领用人联系名称 -> 责任人联系方式
        if (i6000Map.containsKey(I6000AttrConstant.RCVD_USER)) {
            map.put(CmdbAttrConstant.RECEIVING_PHONE_NUMBER, i6000Map.get(I6000AttrConstant.RCVD_USER));
        }
        // 工厂区域
        if (i6000Map.containsKey(I6000AttrConstant.BEBER)) {
            String beber = (String) i6000Map.get(I6000AttrConstant.BEBER);
            Map<String, Object> beberMap = factoryAreaMap.get(beber);
            map.put(CmdbAttrConstant.FACTORY_AREA_CODE, beberMap.get(CmdbAttrConstant.DICT_KEY));
            map.put(CmdbAttrConstant.FACTORY_AREA, beberMap.get(CmdbAttrConstant.DICT_VALUE));
        }
        // 项目名称
        if (i6000Map.containsKey(I6000AttrConstant.ITEM)) {
            map.put(CmdbAttrConstant.PROJECT_NAME, i6000Map.get(I6000AttrConstant.ITEM));
        }
        // 实物保管部门
        if (i6000Map.containsKey(I6000AttrConstant.MANAGE_DEPT) && i6000Map.containsKey(I6000AttrConstant.MANAGE_DEPT_NAME)) {
            map.put(CmdbAttrConstant.REAL_MANAGE_DEPT, i6000Map.get(I6000AttrConstant.MANAGE_DEPT));
            map.put(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME, i6000Map.get(I6000AttrConstant.MANAGE_DEPT_NAME));
        }
        // 使用保管部门
        if (i6000Map.containsKey(I6000AttrConstant.KEEP_DEPT) && i6000Map.containsKey(I6000AttrConstant.KEEP_DEPT_NAME)) {
            map.put(CmdbAttrConstant.USE_KEEP_DEPT, i6000Map.get(I6000AttrConstant.KEEP_DEPT));
            map.put(CmdbAttrConstant.USE_KEEP_DEPT_NAME, i6000Map.get(I6000AttrConstant.KEEP_DEPT_NAME));
        }
        // 设备变动方式
        if (i6000Map.containsKey(I6000AttrConstant.ASSET_CHANGE)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.ASSET_CHANGE, cmdbDictProperties.getDeviceChangeType());
            map.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
            map.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, dictMap.get(CmdbAttrConstant.DICT_VALUE));
        }
        // 是否同步ERP
        if (i6000Map.containsKey(I6000AttrConstant.SYNC_ERP_FLAG)) {
            map.put(CmdbAttrConstant.IS_TO_ERP_CODE, cmdbCientityProperties.getYesNo());
        }
        // 设备增加方式
        if (i6000Map.containsKey(I6000AttrConstant.ASSET_ADD)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.ASSET_ADD, cmdbDictProperties.getDeviceAddType());
            map.put(CmdbAttrConstant.DEVICE_ADD_TYPE_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
            map.put(CmdbAttrConstant.DEVICE_ADD_TYPE, dictMap.get(CmdbAttrConstant.DICT_VALUE));
        }
        // ERP设备台账编码
        if (i6000Map.containsKey(I6000AttrConstant.ERP_LEDGER_NO)) {
            map.put(CmdbAttrConstant.DEVICE_CODE_ERP, i6000Map.get(I6000AttrConstant.ERP_LEDGER_NO));
        }
        // 设备转资失败原因
        if (i6000Map.containsKey(I6000AttrConstant.DEVICE_ASSET_FAIL)) {
            map.put(CmdbAttrConstant.DEVICE_STATE_ERROR, i6000Map.get(I6000AttrConstant.DEVICE_ASSET_FAIL));
        }
        // ERP转资状态
        if (i6000Map.containsKey(I6000AttrConstant.ERP_ASSET_STATE)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.ERP_ASSET_STATE, cmdbDictProperties.getErpTransferStatus());
            map.put(CmdbAttrConstant.ERP_TRANSFER_STATUS, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }
        // 维护工厂
        if (i6000Map.containsKey(I6000AttrConstant.OPDEP)) {
            map.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, i6000Map.get(I6000AttrConstant.OPDEP));
            map.put(CmdbAttrConstant.MAINTENANCE_FACTORY, i6000Map.get(I6000AttrConstant.OPDEP_NAME));
        }
        // WBS项目
        if (i6000Map.containsKey(I6000AttrConstant.WBS_NAME)) {
            map.put(CmdbAttrConstant.WBS_ELEMENT_NAME, i6000Map.get(I6000AttrConstant.WBS_NAME));
        }
        // WBS元素
        if (i6000Map.containsKey(I6000AttrConstant.WBS)) {
            map.put(CmdbAttrConstant.WBS_ELEMENT, i6000Map.get(I6000AttrConstant.WBS));
        }
        // 功能位置
        if (i6000Map.containsKey(I6000AttrConstant.FUN_SITE) && i6000Map.containsKey(I6000AttrConstant.FUN_SITE_NAME)) {
            map.put(CmdbAttrConstant.FUN_LOCATION_CODE, i6000Map.get(I6000AttrConstant.FUN_SITE));
            map.put(CmdbAttrConstant.FUN_LOCATION, i6000Map.get(I6000AttrConstant.FUN_SITE_NAME));
        }
        // 线站标识
        map.put(CmdbAttrConstant.LINE_STATION, "00000000000000000");
        // 线站标识名称
        map.put(CmdbAttrConstant.LINE_STATION_SIGN, "00000000000000000");

        // 操作系统类型
        if (i6000Map.containsKey(I6000AttrConstant.OS_DATATYPE)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.OS_DATATYPE, cmdbDictProperties.getOSTypeCode());
            map.put(CmdbAttrConstant.OS_TYPE_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }

        StockUnitDept stockUnitDept = new StockUnitDept();
        xcDevice.get(CmdbAttrConstant.AREA);
        List<StockUnitDept> stockUnitDeptList = iscDeptMapper.selectIscDept(stockUnitDept);
        if (!CollectionUtils.isEmpty(stockUnitDeptList)) {
            StockUnitDept one = stockUnitDeptList.get(0);
            // 运维责任人
            map.put(CmdbAttrConstant.OPERATION_PERSON, one.getIscUserName());
            // 运维联系电话
            map.put(CmdbAttrConstant.OPERATION_TEL, one.getIscUserPhone());
        }
        // 资产原值
        if (i6000Map.containsKey(I6000AttrConstant.INIT_ASSET_VALUE)) {
            map.put(CmdbAttrConstant.ASSET_ORIGINAL, i6000Map.get(I6000AttrConstant.INIT_ASSET_VALUE));
        }
        // CPU型号
        if (i6000Map.containsKey(I6000AttrConstant.CPU_MODEL)) {
            map.put(CmdbAttrConstant.CPU_MODEL, i6000Map.get(I6000AttrConstant.CPU_MODEL));
        }
        // CPU主频 CPU_FRQUENCY
        if (i6000Map.containsKey(I6000AttrConstant.CPU_FRQUENCY)) {
            map.put(CmdbAttrConstant.CPU_CLOCK_SPEED, i6000Map.get(I6000AttrConstant.CPU_FRQUENCY));
            map.put(CmdbAttrConstant.CPU_FREQUECY, i6000Map.get(I6000AttrConstant.CPU_FRQUENCY));
        }
        // 采购合同编号
        if (i6000Map.containsKey(I6000AttrConstant.PUR_NO)) {
            map.put(CmdbAttrConstant.PROCURE_CONTRACT_NO, i6000Map.get(I6000AttrConstant.PUR_NO));
        }
        // 采购方式
        if (i6000Map.containsKey(I6000AttrConstant.PUR_MODE)) {
            Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.PUR_MODE, cmdbDictProperties.getProcureTypeCode());
            map.put(CmdbAttrConstant.PROCURE_TYPE_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
        }
        // 供应商联系电话
        if (i6000Map.containsKey(I6000AttrConstant.SUP_TEL)) {
            map.put(CmdbAttrConstant.SUPPLIER_TEL, i6000Map.get(I6000AttrConstant.SUP_TEL));
        }
        // 供应商联系人
        if (i6000Map.containsKey(I6000AttrConstant.SUP_CONTACT)) {
            map.put(CmdbAttrConstant.SUPPLIER_CONTACTS, i6000Map.get(I6000AttrConstant.SUP_CONTACT));
        }
        if (i6000Map.containsKey(I6000AttrConstant.SRV_COMPANY_NAME)) {
            map.put(CmdbAttrConstant.SERVICE_FULL_NAME, i6000Map.get(I6000AttrConstant.SRV_COMPANY_NAME));
        }
        // 服务商联系人
        if (i6000Map.containsKey(I6000AttrConstant.SRV_CONTACT)) {
            map.put(CmdbAttrConstant.SERVICE_CONTACTS, i6000Map.get(I6000AttrConstant.SRV_CONTACT));
        }
        // 服务级别
        if (i6000Map.containsKey(I6000AttrConstant.SRV_REQUIRED)) {
            map.put(CmdbAttrConstant.SERVICE_LEVEL, i6000Map.get(I6000AttrConstant.SRV_REQUIRED));
            // map.put(CmdbAttrConstant.SERVICE_LEVEL_CODE, i6000Map.get(I6000AttrConstant.SRV_REQUIRED));
        }
        // 服务合同编号
        if (i6000Map.containsKey(I6000AttrConstant.SRV_NO)) {
            map.put(CmdbAttrConstant.CONTRACT_NO, i6000Map.get(I6000AttrConstant.SRV_NO));
        }
        // 产权状态
        map.put(CmdbAttrConstant.OWNER_STATUS, cmdbCientityProperties.getPropStatus0());
        // 运维等级
        map.put(CmdbAttrConstant.OPERATION_LEVEL, cmdbCientityProperties.getOperationLevel0());
        // 售后状态
        map.put(CmdbAttrConstant.AFTER_STATUS_CODE, cmdbCientityProperties.getAfterStatus0());
        // 计量单位
        map.put(CmdbAttrConstant.MEASURE_UNIT, cmdbCientityProperties.getJldw_tai());
        return map;
    }

    private Map<String, Object> getDictMap(Map<String, Object> i6000Map, String i6000Str, Long ciId) {
        FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
        cientitySearch.setDictKeyI6000(String.valueOf(i6000Map.get(i6000Str)));
        cientitySearch.setCiId(ciId);
        R<List<Map<String, Object>>> dictListR = cmdbClient.feignGetCiEntityDictListById(cientitySearch);
        List<Map<String, Object>> dictList = dictListR.getData();
        if (CollectionUtils.isEmpty(dictList)) {
            return new HashMap<>();
        }
        return dictList.get(0);

    }

    private Map<String, Object> getDictMapByErp(Long ciId, String dictKeyErp, String dictValueErp) {
        FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(dictKeyErp)) {
            cientitySearch.setDictKeyErp(dictKeyErp);
        }
        cientitySearch.setCiId(ciId);
        R<List<Map<String, Object>>> dictListR = cmdbClient.feignGetCiEntityDictListById(cientitySearch);

        List<Map<String, Object>> dictList = dictListR.getData();
        if (CollectionUtils.isEmpty(dictList)) {
            return new HashMap<>();
        }
        if (ciId.equals(cmdbDictProperties.getFactoryAreaCode()) && dictList.size() > 1) {
            for (Map<String, Object> dict : dictList) {
                Object dictKeyErpObj = dict.get(DICT_KEY_ERP);
                if (Objects.equals(dictKeyErpObj, "003")) {
                    return dict;
                }
            }
        }
        if (ciId.equals(cmdbDictProperties.getDeviceChangeType())) {
            for (Map<String, Object> dict : dictList) {
                Object dictValue = dict.get(DICT_VALUE);
                if (Objects.equals(dictValue, dictValueErp)) {
                    return dict;
                }
            }
        }
        return dictList.get(0);

    }

    /**
     * 获取I6000的信息
     *
     * @param map
     * @param ciTypeId
     * @return
     */
    private List<Map<String, Object>> selectI6000ResultMap(Map<String, Object> map, String ciTypeId, String attrCode) {
        I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
        if (StringUtil.isBlank(attrCode)) {
            attrCode = i6000CiAttrService.selectI6000AttrByCiTypeId(ciTypeId);
        }
        i6000CiCientityDTO.setAttrCode(attrCode);

        List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();

        I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
        conditions1.setOperator("=");
        conditions1.setAttrCode("ERP_ASSET_NO");
        conditions1.setValue(String.valueOf(map.get(CmdbAttrConstant.ASSET_CODE_ERP)));
        orConditionList.add(conditions1);

        I6000CiCientityDTO.Conditions conditions3 = new I6000CiCientityDTO.Conditions();
        conditions3.setOperator("=");
        conditions3.setAttrCode("OPDEP");
        String regionCode = String.valueOf(map.get(CmdbAttrConstant.AREA));
        R<List<Dept>> byRegionCode = deptClient.getByRegionCode(regionCode);
        String erpUnitCode = null;
        if (ObjectUtil.isNotEmpty(byRegionCode.getData())) {
            erpUnitCode = byRegionCode.getData().get(0).getErpUnitCode();
        }
        conditions3.setValue(erpUnitCode);
        orConditionList.add(conditions3);

        i6000CiCientityDTO.setConditions(orConditionList);
        i6000CiCientityDTO.setPageStart("1");
        i6000CiCientityDTO.setPageSize("10");

        LOGGER.info("批量导入信创终端设备 [ls临时] 请求I6000参数: ciTypeId = {}, i6000CiCientityDTO = {}", ciTypeId, i6000CiCientityDTO);
        List<Map<String, Object>> i6000ResultMap = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);
        LOGGER.info("批量导入信创终端设备 [ls临时] 返回I6000数据: i6000ResultMap = {}", i6000ResultMap);
        if (CollectionUtils.isEmpty(i6000ResultMap)) {
            throw new RuntimeException("获取I6000数据失败!");
        }
        return i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);
    }


    private static String converToTime(String item, String dataTimeStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = "";

        if (dataTimeStr.length() != 14) {
            return "";
        }

        try {
            Date parse = inputFormat.parse(dataTimeStr);
            formattedDate = outputFormat.format(parse);
        } catch (Exception e) {
            throw new RuntimeException("日期转换异常: " + item + " 时间: " + dataTimeStr);
        }
        return formattedDate;
    }
}

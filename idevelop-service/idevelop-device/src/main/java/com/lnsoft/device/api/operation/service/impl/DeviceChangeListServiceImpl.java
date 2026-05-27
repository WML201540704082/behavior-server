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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.vo.HardwareBasicQueryVO;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.annotation.EasyExcelUtil;
import com.lnsoft.device.annotation.ExcelSelected;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.entity.ErpMaintain;
import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import com.lnsoft.device.api.erp.mapper.ErpKostlMapper;
import com.lnsoft.device.api.erp.mapper.ErpMaintainMapper;
import com.lnsoft.device.api.erp.mapper.ErpTranstplnrMapper;
import com.lnsoft.device.api.i6000.wrapper.DeptWrapper;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.entity.DeviceChangeListExport;
import com.lnsoft.device.api.operation.listener.ChangeListener;
import com.lnsoft.device.api.operation.mapper.DeviceChangeListMapper;
import com.lnsoft.device.api.operation.service.IDeviceChangeListService;
import com.lnsoft.device.api.operation.vo.DeviceChangeListVO;
import com.lnsoft.device.api.stock.dto.HardwareBasicCmdbQueryDTO;
import com.lnsoft.device.config.ExcelDeviceTemplateConfiguration;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.lnsoft.device.constant.CmdbAttrConstant.AREA;

/**
 * 设备变更 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Slf4j
@Service
public class DeviceChangeListServiceImpl extends BaseServiceImpl<DeviceChangeListMapper, DeviceChangeList> implements IDeviceChangeListService {

    @Resource
    private CmdbDictProperties cmdbDictProperties;

    @Resource
    private ICmdbService cmdbService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ErpKostlMapper erpKostlMapper;

    @Resource
    private ErpTranstplnrMapper erpTranstplnrMapper;

    @Resource
    private ErpMaintainMapper maintainMapper;

    @Override
    public IPage<DeviceChangeListVO> selectDeviceChangeListPage(IPage<DeviceChangeListVO> page, DeviceChangeListVO deviceChangeList) {
        return page.setRecords(baseMapper.selectDeviceChangeListPage(page, deviceChangeList));
    }

    @Override
    public List<DeviceChangeList> getByChangeId(String changeId) {
        LambdaQueryWrapper<DeviceChangeList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeviceChangeList::getChangeId, changeId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int removeByChangeId(String changeId) {
        return baseMapper.deleteByChangeId(changeId);
    }

    @Override
    public List<DeviceChangeList> getByDeviceCode(String deviceCode) {
        return baseMapper.getByDeviceCode(deviceCode);
    }

    @Override
    public void export(HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery, HttpServletResponse response) {
        if (ObjectUtil.isNotEmpty(hardwareBasicCmdbQuery.getIdList())) {
            if (hardwareBasicCmdbQuery.getIdList().size() > 30) {
                throw new ServiceException("导出数据最多30条，请重新选择");
            }
        }
        //根据类型获取 指定的模型 实体类
        Class<T> cls = getClassByClassif();
        //文件输出格式
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", -1);
            String fileName = URLEncoder.encode("变更设备列表", StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setWrapped(false);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .build();
            //创建 sheet
            WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(cls, 0, "设备");
            //获取设备台账列表 数据
            R<FeignCiCientity> cmdbLists = listCmdb(hardwareBasicCmdbQuery);
            FeignCiCientity feignCiCientity = cmdbLists.getData();
            //获取实体类
            excelWriter.write(getBeanByMap(feignCiCientity.getData(), cls), writeSheet);
            excelWriter.finish();
        } catch (Exception e) {
            throw new ServiceException("设备列表导出失败!");
        }
    }

    @Override
    public List<Map<String, Object>> importByExcel(MultipartFile file) {

        Class<T> cls = getClassByClassif();
        List<Map<String, Object>> list = new ArrayList<>();
        //返解析数据
        List<DeviceChangeListExport> deviceChangeListExports = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //解析数据文件
            EasyExcel.read(inputStream, cls, new ChangeListener(list))
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("设备").doRead();
            //数据填充
            fillMessage(list, cls);


        } catch (IOException | InterruptedException e) {
            throw new ServiceException("excel导入文件读取异常:" + e.getMessage());
        } catch (ExcelDataConvertException e) {
            throw new ServiceException("excel导入文件解析异常:");
        } catch (Exception e) {
            log.error("异常抛出:{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败");
                }
            }
        }
        return list;
    }

    private void fillMessage(List<Map<String, Object>> list, Class<T> cls) throws Exception {
        List<Map<String, Object>> listA = new ArrayList<>();
        List<Map<String, Object>> synchronizedList = Collections.synchronizedList(listA);
        IdevelopUser user = SecureUtil.getUser();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 6, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());

        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(list.size());

        for (int i = 0; i < list.size(); i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Class<T> finalCls = cls;
            int finalI = i;
            Future<?> submit = threadPoolExecutor.submit(() -> {
                try {
                    Map<String, Object> map = list.get(finalI);
                    log.error("finalI = " + finalI);
                    // 填充cmdb id  uuid  ciid等基本字段
                    fillCmdb(map);
                    // 填充单位和部门
                    fillUnitCode(map, user);
                    // 填充实物保管部门,使用管理部门，功能位置
                    fillMes(map);
                    // 填充 品牌、系列、型号编码
                    fillBrandSeriesModel(map);
                    //制造商品牌系列型号校验
                    checkBrandSeriesModel(map);
                    // excel字段处理 名称转编码 编码转名称
                    converDictValues(map, finalCls);
                    synchronizedList.add(map);
                } catch (Exception e) {
                    log.error("变更导入-数据校验时出现异常：{}", e.getMessage());
                    throw new ServiceException("数据校验时出现异常：" + e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
            submit.get();
        }

        try {
            long time = System.currentTimeMillis() - startTime;
            log.error("执行总时长:{}毫秒", time);
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            threadPoolExecutor.shutdown();
        }

        List<Map<String, Object>> result = synchronizedList.stream()
                .sorted(Comparator.comparingInt(map -> (Integer) map.get("index")))
                .collect(Collectors.toList());
    }

    private void checkBrandSeriesModel(Map<String, Object> map) {
        String brand = String.valueOf(map.get("brand")).replace("null", "");
        String brandCode = String.valueOf(map.get("brandCode")).replace("null", "");
        String series = String.valueOf(map.get("series")).replace("null", "");
        String seriesCode = String.valueOf(map.get("seriesCode")).replace("null", "");
        String deviceModel = String.valueOf(map.get("deviceModel")).replace("null", "");
        String deviceModelCode = String.valueOf(map.get("deviceModelCode")).replace("null", "");
        String maker = String.valueOf(map.get("maker")).replace("null", "");
        String makerCode = String.valueOf(map.get("makerCode")).replace("null", "");
        String deviceCode = String.valueOf(map.get(CmdbAttrConstant.DEVICE_CODE));
        if (checkMaker((Long) map.get("makerCid"), maker, makerCode)) {
            throw new ServiceException("设备编码: " + deviceCode + "制造商与品牌上下级不匹配!");
        } else {
            if (checkType((Long) map.get("seriesCid"), series, brandCode)) {
                throw new ServiceException("设备编码: " + deviceCode + "品牌与系列上下级不匹配! ");
            } else {
                if (checkType((Long) map.get("deviceModelCid"), deviceModel, seriesCode)) {
                    throw new ServiceException("设备编码: " + deviceCode + "系列与型号上下级不匹配! ");
                }
            }
        }
    }

    /**
     * 校验上下级关系
     *
     * @param cId       模型id
     * @param dictValue 中文名称
     * @param pid       父id
     * @return
     */
    private Boolean checkType(Long cId, String dictValue, String pid) {
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        List<HashMap<String, Object>> dict = (List<HashMap<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + cId);
        List<String> collect = dict.stream()
                .map(item -> item.get("dictValue") + "-" + item.get("pid"))
                .collect(Collectors.toList());
        String targetStr = dictValue + "-" + pid;
        // key是否存在，存在则匹配
        if (collect.contains(targetStr)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 制造商校验
     * ciid  制造商模型id
     * maker 制造商名称
     * makerCode  制造商code
     */
    private Boolean checkMaker(Long ciId, String maker, String makerCode) {
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        List<HashMap<String, Object>> dict = (List<HashMap<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + ciId);
        List<String> collect = dict.stream()
                .map(item -> item.get("dictValue") + "-" + item.get("dictKey"))
                .collect(Collectors.toList());
        String targetStr = maker + "-" + makerCode;

        if (collect.contains(targetStr)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void fillCmdb(Map<String, Object> map) {
        CiCientitySearch cientitySearch = new CiCientitySearch();
        List<CiCientitySearchVO> entity = new ArrayList<>();
        CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder()
                .attrName(CmdbAttrConstant.DEVICE_CODE)
                .expression(Expression.EQUAL)
                .attrValue(map.get(CmdbAttrConstant.DEVICE_CODE)).build();
        entity.add(ciCientitySearchVO);
        Query query = new Query().setCurrent(1).setSize(10);
        cientitySearch.setEntity(entity);
        cientitySearch.setQuery(query);
        cientitySearch.setFullField(Boolean.TRUE);
        FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
        if (feignCiCientity.getTotal() == 0) {
            throw new ServiceException("台账中不存在,请核对设备编码");
        }
        map.put(CmdbAttrConstant.UUID, feignCiCientity.getData().get(0).get(CmdbAttrConstant.UUID));
        map.put(CmdbAttrConstant.ID, feignCiCientity.getData().get(0).get(CmdbAttrConstant.ID));
        map.put(CmdbAttrConstant.CI_ID, feignCiCientity.getData().get(0).get(CmdbAttrConstant.CI_ID));
        map.put(CmdbAttrConstant.DEVICE_STATUS, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_STATUS));
        map.put(CmdbAttrConstant.DEVICE_STATUS_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_STATUS_CODE));
//		map.put(CmdbAttrConstant.MAINTENANCE_FACTORY, feignCiCientity.getData().get(0).get(CmdbAttrConstant.MAINTENANCE_FACTORY));
//		map.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE));
        map.put(CmdbAttrConstant.DEVICE_CATEGORY, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_CATEGORY));
        map.put(CmdbAttrConstant.DEVICE_CATEGORY_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_CATEGORY_CODE));
        map.put(CmdbAttrConstant.OPERATION_UNIT_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_UNIT_CODE));
        map.put(CmdbAttrConstant.OPERATION_UNIT, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_UNIT));
        map.put(CmdbAttrConstant.OPERATION_PERSON, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_PERSON));
        map.put(CmdbAttrConstant.OPERATION_DEPT, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_DEPT));
        map.put(CmdbAttrConstant.OPERATION_DEP_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_DEP_CODE));
        map.put(CmdbAttrConstant.OPERATION_TEL, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_TEL));
        map.put(CmdbAttrConstant.OPERATION_LEVEL, feignCiCientity.getData().get(0).get(CmdbAttrConstant.OPERATION_LEVEL));
//		map.put(CmdbAttrConstant.USER, feignCiCientity.getData().get(0).get(CmdbAttrConstant.USER));
//		map.put(CmdbAttrConstant.USER_TEL, feignCiCientity.getData().get(0).get(CmdbAttrConstant.USER_TEL));
//		map.put(CmdbAttrConstant.DEVICE_USER_ID_CARD, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_USER_ID_CARD));
        map.put(CmdbAttrConstant.INSTALLATION_SITE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.INSTALLATION_SITE));
        map.put(CmdbAttrConstant.DEVICE_TYPE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_TYPE));
        map.put(CmdbAttrConstant.DEVICE_TYPE_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_TYPE_CODE));
        map.put(CmdbAttrConstant.DEVICE_STATUS, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_STATUS));
        map.put(CmdbAttrConstant.DEVICE_STATUS_CODE, feignCiCientity.getData().get(0).get(CmdbAttrConstant.DEVICE_STATUS_CODE));
    }

    private void fillMes(Map<String, Object> map) {

        //使用保管部门
        if (ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.USE_KEEP_DEPT_NAME))) {
            String userKeepDeptName = String.valueOf(map.get(CmdbAttrConstant.USE_KEEP_DEPT_NAME));
            LambdaQueryWrapper<ErpKostl> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ErpKostl::getKostlT, userKeepDeptName).or().eq(ErpKostl::getKostlLt, userKeepDeptName);
            List<ErpKostl> erpKostls = erpKostlMapper.selectList(queryWrapper);
            if (ObjectUtil.isNotEmpty(erpKostls)) {
                map.put(CmdbAttrConstant.USE_KEEP_DEPT, erpKostls.get(0).getKostl());
            }
        }

        //实物管理部门
        if (ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME))) {
            String entityManagementDeptName = String.valueOf(map.get(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME));
            LambdaQueryWrapper<ErpKostl> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ErpKostl::getKostlT, entityManagementDeptName).or().eq(ErpKostl::getKostlLt, entityManagementDeptName);
            List<ErpKostl> erpKostls = erpKostlMapper.selectList(queryWrapper);
            if (ObjectUtil.isNotEmpty(erpKostls)) {
                map.put(CmdbAttrConstant.REAL_MANAGE_DEPT, erpKostls.get(0).getKostl());
            }
        }

        //功能位置
        if (ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.FUN_LOCATION))) {

            String funLocation = String.valueOf(map.get(CmdbAttrConstant.FUN_LOCATION));
            LambdaQueryWrapper<ErpTranstplnr> queryWrapper = new LambdaQueryWrapper<>();
            if (ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE))) {
                String main = String.valueOf(map.get(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE));
                queryWrapper.eq(ErpTranstplnr::getSwerk, main);
            }
            queryWrapper.eq(ErpTranstplnr::getPltxt, funLocation).eq(ErpTranstplnr::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
            List<ErpTranstplnr> erpTranstplnrs = erpTranstplnrMapper.selectList(queryWrapper);
            if (ObjectUtil.isNotEmpty(erpTranstplnrs)) {
                map.put(CmdbAttrConstant.FUN_LOCATION_CODE, erpTranstplnrs.get(0).getTrlnr());
            }
        }

        //维护工厂
        if (ObjectUtil.isNotEmpty(map.get(CmdbAttrConstant.MAINTENANCE_FACTORY))) {
            String maintenance = String.valueOf(map.get(CmdbAttrConstant.MAINTENANCE_FACTORY));
            LambdaQueryWrapper<ErpMaintain> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ErpMaintain::getName, maintenance).or().eq(ErpMaintain::getFullName, maintenance);
            List<ErpMaintain> erpMaintains = maintainMapper.selectList(queryWrapper);
            if (ObjectUtil.isNotEmpty(erpMaintains)) {
                map.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, erpMaintains.get(0).getCode());
            }
        }
    }

    /**
     * 处理单位和部门
     *
     * @param map
     * @param user
     */
    private void fillUnitCode(Map<String, Object> map, IdevelopUser user) {
        if (StringUtil.isNotBlank(String.valueOf(map.get(CmdbAttrConstant.RECEIVE_UNIT)))) {
            //领用单位名称
            String receiveUnit = String.valueOf(map.get(CmdbAttrConstant.RECEIVE_UNIT));
            //领用单位编码
            String receiveUnitCode = DeptWrapper.build().getDeptCodeByName(receiveUnit);
            map.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, receiveUnitCode);
            //领用部门名称
            String receiveDept = String.valueOf(map.get(CmdbAttrConstant.RECEIVE_DEPT));
            //领用部门编码
            String receiveDeptCode = DeptWrapper.build().getDeptCodeByNameAndPid(receiveDept, receiveUnitCode);
            map.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, receiveDeptCode);
        }
    }

    /**
     * excel字段处理 名称转编码 编码转名称
     *
     * @param map
     * @param finalCls
     */
    private void converDictValues(Map<String, Object> map, Class<T> finalCls) {
        try {
            // 电压等级 名称+code
            converDictValue(String.valueOf(map.get("voltageLevel")).replace("null", ""), "voltageLevel", "voltageLevelCode", map, finalCls);
            // 设备增加方式 名称+code
            converDictValue(String.valueOf(map.get("deviceAddType")).replace("null", ""), "deviceAddType", "deviceAddTypeCode", map, finalCls);
            //设备变动方式
            converDictValue(String.valueOf(map.get("deviceChangeType")).replace("null", ""), "deviceChangeType", "deviceChangeTypeCode", map, finalCls);
            // 是否信创设备 名称+code
            converDictValue(String.valueOf(map.get("isITAI")).replace("null", ""), "isITAI", "isITAICode", map, finalCls);
            // 设备来源 名称+code
            converDictValue(String.valueOf(map.get("deviceSource")).replace("null", ""), "deviceSource", "deviceSourceCode", map, finalCls);
            // 设备状态 名称+code
            converDictValue(String.valueOf(map.get("deviceStatus")).replace("null", ""), "deviceStatus", "deviceStatusCode", map, finalCls);
            // CPU品牌 名称+code
            converDictValue(String.valueOf(map.get("cpuBrand")).replace("null", ""), "cpuBrand", "cpuBrandCode", map, finalCls);
            // 所属网络 code
            converDictValue(String.valueOf(map.get("netWork")).replace("null", ""), "netWork", "netWorkCode", map, finalCls);
            // 采购方式 code
            converDictValue(String.valueOf(map.get("procureType")).replace("null", ""), "procureType", "procureTypeCode", map, finalCls);
            // CPU架构 code
            converDictValue(String.valueOf(map.get("cpuArch")).replace("null", ""), "cpuArch", "cpuArchCode", map, finalCls);
            // 硬盘类型 code
            converDictValue(String.valueOf(map.get("hardDiskType")).replace("null", ""), "hardDiskType", "hardDiskTypeCode", map, finalCls);
            // 网络设备用途类型 code
            converDictValue(String.valueOf(map.get("networkDeviceType")).replace("null", ""), "networkDeviceType", "networkDeviceTypeCode", map, finalCls);
            // 所属安全边界 code
            converDictValue(String.valueOf(map.get("securityBoundary")).replace("null", ""), "securityBoundary", "securityBoundaryCode", map, finalCls);
            // 主机设备用途类型 code
            converDictValue(String.valueOf(map.get("serverUseToType")).replace("null", ""), "serverUseToType", "serverUseToTypeCode", map, finalCls);
            // 操作系统类型 code
            converDictValue(String.valueOf(map.get("OSType")).replace("null", ""), "OSType", "OSTypeCode", map, finalCls);
            // 空调类型 code
            converDictValue(String.valueOf(map.get("airConditionType")).replace("null", ""), "airConditionType", "airConditionTypeCode", map, finalCls);
            // 非数字化入网设备 code
            converDictValue(String.valueOf(map.get("isAccessEquipment")).replace("null", ""), "isAccessEquipment", "isAccessEquipmentCode", map, finalCls);
            // 主备属性 code
            converDictValue(String.valueOf(map.get("factoryArea")).replace("null", ""), "factoryArea", "factoryAreaCode", map, finalCls);
            //计量单位
            converDictValue(String.valueOf(map.get("measureUnit")).replace("null", ""), "measureUnit", "measureUnit", map, finalCls);


        } catch (Exception e) {
            log.error("excel字段处理 名称转编码 编码转名称出现异常:{}", e);
        }
    }

    private void converDictValue(String var1, String sourceStr, String fieidCode, Map<String, Object> map, Class<T> cls) {
        if (StringUtil.isNotBlank(var1)) {
            String code = getCmdbValByColName(var1, sourceStr, cls);
            map.put(fieidCode, code);
        }
    }

    /**
     * 根据cmdb字典值回去cmdb字典名称
     *
     * @return
     */
    private String getCmdbValByColName(String name, String fieldName, Class<T> cls) {
        String val = "";
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field != null && field.getName().equals(fieldName)) {
                ExcelSelected property = field.getAnnotation(ExcelSelected.class);
                String id = property.ciId();
                Long ciId = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel(id);
                try {
                    //获取字典数据 缓存redis
                    RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
                    Map<String, String> maps = JSON.parseObject(JSON.toJSONString(redisUtil.get(CacheNames.CMDB_DICT_STORAGE + ciId)), Map.class);
                    if (maps != null) {
                        for (Map.Entry map : maps.entrySet()) {
                            if (name.equals(map.getValue())) {
                                val = String.valueOf(map.getKey());
                                if (StringUtil.isNotBlank(val)) {
                                    return val;
                                }
                            }
                        }
                        //{"1104198587711488":"否","1104198453493761":"是"}
                    }
                } catch (Exception e) {
                    log.error("解析动态下拉框数据异常", e);
                }
            }
        }
        return val;
    }

    private void fillBrandSeriesModel(Map<String, Object> map) {
        Long makerCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker");
        Long brandCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("brand");
        Long seriesCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("series");
        Long deviceModelCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("model");
        map.put("makerCid", makerCid);
        map.put("brandCid", brandCid);
        map.put("seriesCid", seriesCid);
        map.put("deviceModelCid", deviceModelCid);

        String deviceModelCode = "";

        map.put("makerCode", "");
        map.put("brandCode", "");
        map.put("seriesCode", "");
        map.put("deviceModelCode", deviceModelCode);

        // 制造商
        String maker = String.valueOf(map.get("maker")).replace("null", "");
        String brand = String.valueOf(map.get("brand")).replace("null", "");
        String series = String.valueOf(map.get("series")).replace("null", "");
        String model = String.valueOf(map.get("deviceModel")).replace("null", "");


        List<Map<String, Object>> makerDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + makerCid);
        List<Map<String, Object>> makerDictListSelect = makerDictList.stream().filter(m -> m.get("dictValue").equals(maker)).collect(Collectors.toList());
        if (makerDictListSelect.isEmpty()) {
            log.info("制造商不存在: " + maker);

        } else {
            for (Map<String, Object> maker1 : makerDictListSelect) {
                map.put("makerCode", maker1.get("dictKey"));

                List<Map<String, Object>> brandDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + brandCid);
                List<Map<String, Object>> brandDictListSelect = brandDictList.stream().filter(b -> b.get("dictValue").equals(brand) && String.valueOf(b.get("pid")).equals(maker1.get("dictKey"))).collect(Collectors.toList());


                if (brandDictListSelect.isEmpty()) {
                    log.info("制造商: " + maker + "下, 品牌不存在: " + brand);

                } else {
                    for (Map<String, Object> brand1 : brandDictListSelect) {

                        map.put("brandCode", brand1.get("dictKey"));
                        List<Map<String, Object>> seriesDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + seriesCid);
                        List<Map<String, Object>> seriesDictListSelect = seriesDictList.stream().filter(s -> s.get("dictValue").equals(series) && String.valueOf(s.get("pid")).equals(brand1.get("dictKey"))).collect(Collectors.toList());


                        if (seriesDictListSelect.isEmpty()) {
                            log.info("品牌: " + brand + "下, 系列不存在: " + series);

                        } else {
                            for (Map<String, Object> series1 : seriesDictListSelect) {

                                map.put("seriesCode", series1.get("dictKey"));
                                List<Map<String, Object>> deviceModelDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + deviceModelCid);
                                List<Map<String, Object>> deviceModelDictListSelect = deviceModelDictList.stream().filter(m -> m.get("dictValue").equals(model) && String.valueOf(m.get("pid")).equals(series1.get("dictKey"))).collect(Collectors.toList());


                                if (deviceModelDictListSelect.isEmpty()) {
                                    log.info("系列: " + series + "下, 型号不存在: " + model);

                                } else {
                                    for (Map<String, Object> model1 : deviceModelDictListSelect) {
                                        deviceModelCode = model1.get("dictKey").toString();
                                        map.put("deviceModelCode", model1.get("dictKey"));
                                        break;
                                    }
                                }
                                if (StringUtils.isNotBlank(deviceModelCode)) {
                                    break;
                                }
                            }
                        }
                        if (StringUtils.isNotBlank(deviceModelCode)) {
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * 根据类型获取 指定的模型 实体类
     */
    private Class getClassByClassif() {
        Class cls = DeviceChangeListExport.class;
        return cls;
    }

    /**
     * 查詢獲取 cmdb數據 数据
     *
     * @param hardwareBasicCmdbQuery
     * @return
     */
    public R<FeignCiCientity> listCmdb(HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery) {
        //查询阐述 区域 类型 分类
        HardwareBasicQueryVO hardwareBasicQueryVO = new HardwareBasicQueryVO();
        if (StringUtil.isNotBlank(hardwareBasicCmdbQuery.getDeviceType())) {
            hardwareBasicQueryVO.setDeviceTypeCode(hardwareBasicCmdbQuery.getDeviceType());
        }
        if (StringUtil.isNotBlank(hardwareBasicCmdbQuery.getDeviceCategory())) {
            hardwareBasicQueryVO.setDeviceCategoryCode(hardwareBasicCmdbQuery.getDeviceCategory());
        }
        //数据治理标识  否
        hardwareBasicQueryVO.setIsGovern(cmdbDictProperties.getGovernNo());

        Query query = new Query();
        int total = 100;
        int pageSize = 100;
        //设置总数
        try {
            /**
             * 单次限制999行，
             * 缺少分页获取数据接口
             * 带改造
             */
            query.setSize(pageSize);
            query.setCurrent(1);
            List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(hardwareBasicQueryVO);
            String area = hardwareBasicCmdbQuery.getArea();
            if (StringUtil.isNotBlank(area)) {
                CiCientitySearchVO ciCientitySearchVO = new CiCientitySearchVO();
                ciCientitySearchVO.setAttrName(AREA);
                ciCientitySearchVO.setAttrValue(hardwareBasicCmdbQuery.getArea());
                if ("37".equals(area)) {
                    ciCientitySearchVO.setExpression(Expression.LIKE);
                } else {
                    ciCientitySearchVO.setExpression(Expression.EQUAL);
                }
                ciCientitySearchVOS.add(ciCientitySearchVO);
            }
            FeignCiCientity jsonObject = null;
//			if (StringUtil.isBlank(hardwareBasicQueryVO.getDeviceCategoryCode())) {
//				jsonObject = cmdbService.getCiCientityList(ciCientitySearchVOS, query);
//			} else {
            //获取前端传递的台账id列表
            List<Long> idList = hardwareBasicCmdbQuery.getIdList();
            if (idList != null && idList.size() > 0) {
                //按照类型 idList导出数据
                CiCientitySearch cientitySearch = new CiCientitySearch();
                cientitySearch.setIdList(idList);
                cientitySearch.setQuery(query);
                cientitySearch.setFullField(true);
                cientitySearch.setEntity(ciCientitySearchVOS);
                jsonObject = cmdbService.getCiCientityListByCondition(cientitySearch);
            } else {
                //按照类型导出全量
                jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
            }
//			}
            total = jsonObject.getTotal();
            //判断返回总行数
            if (total > pageSize) {
                //分页获取其他页面
                int start = pageSize;
                int current = 2;
                for (; start < total; ) {
                    query.setSize(pageSize);
                    query.setCurrent(current);
                    FeignCiCientity jsonObjectP2 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
                    if (jsonObjectP2 != null) {
                        List<Map<String, Object>> datas = jsonObject.getData();
                        datas.addAll(jsonObjectP2.getData());
                        jsonObject.setData(datas);
                    }
                    current++;
                    //计算下页数量
                    start = start + pageSize;
                }
            }


            return R.data(jsonObject);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return R.data(new FeignCiCientity());
    }

    /**
     * 转换返回数据至实体类
     *
     * @param list
     * @return
     */
    private List<Object> getBeanByMap(List<Map<String, Object>> list, Class<T> cls) {

        List<Object> deviceDTOList = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> device = list.get(i);
                //数据对象增加index 字段
                device.put("index", (i + 1));
                try {
                    //isToI6000 是否同步I6000  字典转换
                    String isToI6000 = String.valueOf(device.get("isToI6000")).replace("null", "");
                    if (StringUtil.isNotBlank(isToI6000)) {
                        device.put("isToI6000", getColNameByCmdbVal(isToI6000, "isToI6000", cls));
                    }
                    //isToErp 是否同步ERP  字典转换
                    String isToErpCode = String.valueOf(device.get("isToErpCode")).replace("null", "");
                    if (StringUtil.isNotBlank(isToErpCode)) {
                        device.put("isToErp", getColNameByCmdbVal(isToErpCode, "isToErp", cls));
                    }
                    //netWorkCode  所属网络
                    String netWorkCode = String.valueOf(device.get("netWorkCode")).replace("null", "");
                    if (StringUtil.isNotBlank(netWorkCode)) {
                        device.put("netWork", getColNameByCmdbVal(netWorkCode, "netWork", cls));
                    }
                    // 采购方式
                    String procureTypeCode = String.valueOf(device.get("procureTypeCode")).replace("null", "");
                    if (StringUtil.isNotBlank(procureTypeCode)) {
                        device.put("procureType", getColNameByCmdbVal(procureTypeCode, "procureType", cls));
                    }
                    // 操作系统类型
                    String OSTypeCode = String.valueOf(device.get("OSTypeCode")).replace("null", "");
                    if (StringUtil.isNotBlank(OSTypeCode)) {
                        device.put("OSType", getColNameByCmdbVal(OSTypeCode, "OSType", cls));
                    }
                    // 是否信创设备
                    String isITAICode = String.valueOf(device.get("isITAICode")).replace("null", "");
                    if (StringUtil.isNotBlank(isITAICode)) {
                        device.put("isITAI", getColNameByCmdbVal(isITAICode, "isITAI", cls));
                    }
                    // 硬盘类型
                    String hardDiskTypeCode = String.valueOf(device.get("hardDiskTypeCode")).replace("null", "");
                    if (StringUtil.isNotBlank(hardDiskTypeCode)) {
                        device.put("hardDiskType", getColNameByCmdbVal(hardDiskTypeCode, "hardDiskType", cls));
                    }
                    // 以下只有一个字段，需要转化
                    // 网络设备用途类型
                    String networkDeviceTypeCode = String.valueOf(device.get("networkDeviceType")).replace("null", "");
                    if (StringUtil.isNotBlank(networkDeviceTypeCode)) {
                        device.put("networkDeviceType", getColNameByCmdbVal(networkDeviceTypeCode, "networkDeviceType", cls));
                    }
                    // 主备属性
                    String standbyAttrCode = String.valueOf(device.get("standbyAttr")).replace("null", "");
                    if (StringUtil.isNotBlank(standbyAttrCode)) {
                        device.put("standbyAttr", getColNameByCmdbVal(standbyAttrCode, "standbyAttr", cls));
                    }
                    // 所属安全边界
                    String securityBoundaryCode = String.valueOf(device.get("securityBoundary")).replace("null", "");
                    if (StringUtil.isNotBlank(securityBoundaryCode)) {
                        device.put("securityBoundary", getColNameByCmdbVal(securityBoundaryCode, "securityBoundary", cls));
                    }
                    // CPU架构
                    String cpuArchCode = String.valueOf(device.get("cpuArchCode")).replace("null", "");
                    if (StringUtil.isNotBlank(cpuArchCode)) {
                        device.put("cpuArch", getColNameByCmdbVal(cpuArchCode, "cpuArch", cls));
                    }
                    // 主机设备用途类型
                    String serverUseToTypeCode = String.valueOf(device.get("serverUseToType")).replace("null", "");
                    if (StringUtil.isNotBlank(serverUseToTypeCode)) {
                        device.put("serverUseToType", getColNameByCmdbVal(serverUseToTypeCode, "serverUseToType", cls));
                    }

                    // airConditionType 空调类型
                    String airConditionType = String.valueOf(device.get("airConditionType")).replace("null", "");
                    if (StringUtil.isNotBlank(airConditionType)) {
                        device.put("airConditionType", getColNameByCmdbVal(airConditionType, "airConditionType", cls));
                    }

                    //默认数据操作类型 编辑
                    device.put("dataOptType", "修改");
                    String isAccessEquipment = String.valueOf(device.get("isAccessEquipmentCode")).replace("null", "");
                    if (StringUtil.isNotBlank(isAccessEquipment)) {
                        device.put("isAccessEquipment", getColNameByCmdbVal(isAccessEquipment, "isAccessEquipment", cls));
                    } else {
                        device.put("isAccessEquipment", "否");
                    }

                    log.error(JSON.toJSONString(device));
                    //转换实体类
                    deviceDTOList.add(JSON.parseObject(JSON.toJSONString(device), cls));
                    //deviceDTOList.add(BeanUtil.toBean(device, cls));
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }
        //数据异常会导致 格式报错 待解决 new ArrayList<>();//
        return deviceDTOList;
    }

    /**
     * 根据cmdb字典值回去cmdb字典名称
     *
     * @return
     */
    private String getColNameByCmdbVal(String val, String fieldName, Class<T> cls) {
        String name = "";
        val = val.replace("null", "");
        if (StringUtil.isNotBlank(val)) {
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field != null && field.getName().equals(fieldName)) {
                    ExcelSelected property = field.getAnnotation(ExcelSelected.class);
                    String id = property.ciId();
                    Long ciId = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel(id);
                    try {
                        //获取字典数据 缓存redis
                        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
                        Map<String, String> maps = JSON.parseObject(JSON.toJSONString(redisUtil.get(CacheNames.CMDB_DICT_STORAGE + ciId)), Map.class);
                        if (maps != null) {
                            name = maps.get(val);
                            if (StringUtil.isBlank(name)) {
                                name = val;
                                return name;
                            }
                            //{"1104198587711488":"否","1104198453493761":"是"}
                        }
                    } catch (Exception e) {
                        log.error("解析动态下拉框数据异常", e);
                    }
                }
            }
        }
        return name;
    }
}

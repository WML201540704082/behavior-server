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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.annotation.EasyExcelUtil;
import com.lnsoft.device.api.stock.dto.HardwareBasicCmdbDeviceDTO;
import com.lnsoft.device.api.warehouse.dto.*;
import com.lnsoft.device.api.warehouse.entity.DeviceStorage;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageList;
import com.lnsoft.device.api.warehouse.mapper.DeviceStorageListMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageListService;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageListImportVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageListVO;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.entity.DeviceRecordList;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.ExcelDeviceStorageListener;
import com.lnsoft.device.utils.OrderNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * 设备入库明细表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Service
@Slf4j
public class DeviceStorageListServiceImpl extends BaseServiceImpl<DeviceStorageListMapper, DeviceStorageList> implements IDeviceStorageListService {

    @Resource
    private OrderNumberUtil orderNumberUtil;
    @Resource
    private CmdbCientityProperties ciEntityProperties;
    @Resource
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CmdbCientityProperties cmdbCientityProperties;

    @Override
    public IPage<DeviceStorageListVO> selectDeviceStorageListPage(IPage<DeviceStorageListVO> page, DeviceStorageListVO deviceStorageList) {
        return page.setRecords(baseMapper.selectDeviceStorageListPage(page, deviceStorageList));
    }

    @Override
    public void deleteByStorageId(String storageId) {
        baseMapper.deleteByStorageId(storageId);
    }


    @Override
    public DeviceStorageListImportVO importByExcel(MultipartFile file, String deviceCategory, String deviceType, String deviceSource) {
        String filename = file.getOriginalFilename();
        if (StringUtil.isBlank(deviceCategory)) {
            throw new ServiceException("设备分类编码不能为空!");
        }
        ExcelTypeEnum excelTypeEnum = StringUtils.endsWithIgnoreCase(filename, ".xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;

        DeviceStorageListImportVO vo = new DeviceStorageListImportVO();
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            InputStream inputStream2 = file.getInputStream();
            List<List<String>> objects = EasyExcel.read(inputStream2).excelType(excelTypeEnum).sheet().doReadSync();
            if (StringUtil.equals(ciEntityProperties.getDeviceSource(), deviceSource) && objects.size() > 50) {
                throw new ServiceException("设备来源为: 统一纳管时, 入库导入数量请小于50条!");
            }
            if (ciEntityProperties.getCientityId(CmdbCientityConstant.T101).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT101DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T102).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT102DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T103).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT103DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T104).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT104DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T105).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT105DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T106).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT106DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T107).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT107DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T108).equals(deviceCategory)) {
                EasyExcel.read(inputStream, DeviceStorageListImportT108DTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                        .excelType(excelTypeEnum).sheet().doRead();
            } else if (ciEntityProperties.getCientityId(CmdbCientityConstant.T109).equals(deviceCategory)) {
                if (ciEntityProperties.getT10901().equals(deviceType)) {
                    EasyExcel.read(inputStream, DeviceStorageListImportT109UPSDTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                            .excelType(excelTypeEnum).sheet().doRead();
                } else if (ciEntityProperties.getT10902().equals(deviceType)) {
                    EasyExcel.read(inputStream, DeviceStorageListImportT109XDCZDTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                            .excelType(excelTypeEnum).sheet().doRead();
                } else if (ciEntityProperties.getT10903().equals(deviceType)) {
                    EasyExcel.read(inputStream, DeviceStorageListImportT109KTDTO.class, new ExcelDeviceStorageListener(list, redisUtil))
                            .excelType(excelTypeEnum).sheet().doRead();
                }
            }
            fillErrorMessage(list, vo);
        } catch (IOException e) {
            log.error("入库管理-设备列表-导入-解析文件异常：{}", e.getMessage());
            throw new ServiceException("解析文件数据异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("流关闭失败");
            }
        }

        return vo;
    }

    private void fillErrorMessage(List<Map<String, Object>> list, DeviceStorageListImportVO vo) {
        StringBuffer exceptionField = new StringBuffer();
        for (Map<String, Object> map : list) {
            String error = (String) map.get("exceptionField");
            if (StringUtil.isNotBlank(error)) {
                exceptionField.append(error).append(",");
            }
        }
        if (StringUtil.isNotBlank(exceptionField)) {
            exceptionField.deleteCharAt(exceptionField.length() - 1);
            vo.setExceptionField(exceptionField.toString());
        }
        vo.setList(list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean customSaveBatch(List<DeviceStorageList> deviceStorageList) {
        if (CollectionUtil.isEmpty(deviceStorageList)) {
            throw new ServiceException("需要保存的数据为空");
        }
        String id = deviceStorageList.get(0).getStorageId();
        if (StringUtil.isBlank(id)) {
            throw new ServiceException("数据中storageId不能为空");
        }
        DeviceStorage storage = baseMapper.getStorageById(id);
        if (storage == null) {
            throw new ServiceException("查询主表为空");
        }
        // 2024-4-28 设备来源选择统一纳管，但所选设备类型不是统一纳管的类型
        // Map<String, String> dictI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType());
        // if (ciEntityProperties.getDeviceSource().equals(storage.getDeviceSource()) && !dictI6000MapByCiId.containsKey(storage.getDeviceType())) {
        // 	throw new ServiceException("当前设备类型不为统一纳管");
        // }
        // 清理暂存数据
        baseMapper.deleteByStorageId(id);

        switch (isFillRecordInfo(storage)) {
            case 1:
                for (DeviceStorageList entity : deviceStorageList) {
                    fillTempData(entity);
                }
                break;
            case 2:
                for (DeviceStorageList entity : deviceStorageList) {
                    fillData(entity, storage);
                }
                break;
            case 3:
                // 填充建档信息
                List<DeviceRecordList> erpAsstCodes = baseMapper.selectErpAssetCode(storage.getDeviceCategory(),
                        storage.getDeviceType(), storage.getWbsElement(), 2, deviceStorageList.size());
                if (erpAsstCodes.size() != deviceStorageList.size()) {
                    throw new ServiceException("建档设备可用数量不足！");
                }
                for (int i = 0; i < deviceStorageList.size(); i++) {
                    DeviceStorageList entity = deviceStorageList.get(i);
                    DeviceRecordList deviceRecordList = erpAsstCodes.get(i);
                    entity.setErpAssetCode(deviceRecordList.getErpAssetCode());
                    entity.setErpAccountCode(deviceRecordList.getErpAccountCode());
                    fillData(entity, storage);
                    entity.setUuid(deviceRecordList.getDeviceUuid());
                }
                break;
            default:
                break;
        }
        this.saveBatch(deviceStorageList);
        return true;
    }

    @Override
    public void exportByExcel(DeviceStorageExportDTO deviceStorageExportDTO, HttpServletResponse response) {
        List<Map<String, Object>> records = deviceStorageExportDTO.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            records = new ArrayList<>();
        }
        try {
            //获取文件名称
            String categoryName = getTypeNameByDeviceCategory(deviceStorageExportDTO.getDeviceCategory(), deviceStorageExportDTO.getDeviceType());
            String fileName = URLEncoder.encode(categoryName + "设备入库台账异常数据结果", StandardCharsets.UTF_8.name()) + ".xlsx";
            //根据类型获取 指定的模型 实体类
            Class<T> cls = getErrorClassByClassif(deviceStorageExportDTO.getDeviceCategory(), deviceStorageExportDTO.getDeviceType());
            List<Object> beans = getErrorBeanByMap(records, cls);
            EasyExcelUtil.exportExcelWithBackGroundError(beans, response, cls, fileName, categoryName);
        } catch (Exception e) {
            log.error("设备台账异常数据下载失败：{}", e.getMessage());
            throw new ServiceException("设备台账异常数据下载失败!原因:" + e.getMessage());
        }
    }

    private List<Object> getErrorBeanByMap(List<Map<String, Object>> list, Class<T> cls) {
        List<Object> deviceDTOList = new ArrayList<Object>();
        if (CollectionUtil.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> device = list.get(i);
                try {
                    //转换实体类
                    deviceDTOList.add(JSON.parseObject(JSON.toJSONString(device), cls));
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }
        return deviceDTOList;
    }


    private Class<T> getErrorClassByClassif(String deviceCategory, String deviceType) {
        Class cls = HardwareBasicCmdbDeviceDTO.class;
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            if (cmdbCientityProperties.getT10901().equals(deviceType)) {
                // 字典枚举ID-UPS
                cls = DeviceStorageListImportErrorT109UPSDTO.class;
            } else if (cmdbCientityProperties.getT10902().equals(deviceType)) {
                // 字典枚举ID-蓄电池组
                cls = DeviceStorageListImportErrorT109XDCZDTO.class;
            } else if (cmdbCientityProperties.getT10903().equals(deviceType)) {
                // 字典枚举ID-机房空调
                cls = DeviceStorageListImportErrorT109KTDTO.class;
            }
        } else if (cmdbCientityProperties.getT108().equals(deviceCategory)) {
            // 字典枚举ID-备品备件
            cls = DeviceStorageListImportErrorT108DTO.class;
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
            // 字典枚举ID-办公设备
            cls = DeviceStorageListImportErrorT107DTO.class;
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 字典枚举ID-辅助设备
            cls = DeviceStorageListImportErrorT106DTO.class;
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            cls = DeviceStorageListImportErrorT105DTO.class;
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            cls = DeviceStorageListImportErrorT104DTO.class;
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
            // 字典枚举ID-网络设备
            cls = DeviceStorageListImportErrorT103DTO.class;
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
            // 字典枚举ID-存储设备
            cls = DeviceStorageListImportErrorT102DTO.class;
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
            // 字典枚举ID-主机设备
            cls = DeviceStorageListImportErrorT101DTO.class;
        }
        return cls;
    }

    /**
     * 获取设备名
     *
     * @param deviceCategory
     * @return
     */
    private String getTypeNameByDeviceCategory(String deviceCategory, String deviceType) {
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            if (cmdbCientityProperties.getT10901().equals(deviceType)) {
                // 字典枚举ID-UPS
                return "UPS";
            } else if (cmdbCientityProperties.getT10902().equals(deviceType)) {
                // 字典枚举ID-蓄电池组
                return "蓄电池组";
            } else if (cmdbCientityProperties.getT10903().equals(deviceType)) {
                // 字典枚举ID-机房空调
                return "机房空调";
            } else {
                // 字典枚举ID-基础设施
                return "基础设施";
            }
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
            // 字典枚举ID-办公设备
            return "办公设备";
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 字典枚举ID-辅助设备
            return "辅助设备";
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            return "终端设备";
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            return "安全设备";
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
            // 字典枚举ID-网络设备
            return "网络设备";
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
            // 字典枚举ID-存储设备
            return "存储设备";
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
            // 字典枚举ID-主机设备
            return "主机设备";
        }
        return "";
    }

    private int isFillRecordInfo(DeviceStorage storage) {
        // 是否暂存
        if (storage.getIsTemp() == 0) {
            // 是否统一纳管
            if (ciEntityProperties.getCientityId(CmdbCientityConstant.DEVICE_SOURCE).equals(storage.getDeviceSource())) {
                // 是否终端设备、办公设备
                if (ciEntityProperties.getCientityId(CmdbCientityConstant.T105).equals(storage.getDeviceCategory()) ||
                        ciEntityProperties.getCientityId(CmdbCientityConstant.T107).equals(storage.getDeviceCategory())) {
                    Map<Object, Object> deviceTypeMapErp = cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType());
                    // 2024-4-28 需要同步erp的数据才需要匹配建档信息
                    if (deviceTypeMapErp.containsKey(storage.getDeviceType())) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else {
                    return 2;
                }
            } else {
                // 非统一纳管，终端设备-台式机 不需要入库
                // if (ciEntityProperties.getCientityId(CmdbCientityConstant.T105).equals(storage.getDeviceType())) {
                // 	throw new ServiceException("非统一纳管-终端设备,不走入库流程");
                // }
                return 2;
            }
        } else {
            return 1;
        }
    }

    /**
     * 暂存-填充数据
     *
     * @param entity
     */
    private void fillTempData(DeviceStorageList entity) {
        IdevelopUser user = SecureUtil.getUser();
        entity.setCreateUser(user.getUserId());
        entity.setCreateDept(user.getDeptId());
        entity.setCreateTime(new Date());
        entity.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        entity.setDeviceStatus(ciEntityProperties.getCientityId(CmdbCientityConstant.DEVICE_STATUS_0));
        entity.setId(null);
    }

    /**
     * 非暂存填充数据
     *
     * @param entity
     * @param storage
     */
    private void fillData(DeviceStorageList entity, DeviceStorage storage) {
        String deviceType = storage.getDeviceType();
        IdevelopUser user = SecureUtil.getUser();
        String deviceCode = orderNumberUtil.generateCode(deviceType);
        entity.setDeviceCode(deviceCode);
        entity.setCreateUser(user.getUserId());
        entity.setCreateDept(user.getDeptId());
        entity.setCreateTime(new Date());
        entity.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
        entity.setDeviceStatus(ciEntityProperties.getCientityId(CmdbCientityConstant.DEVICE_STATUS_0));
        entity.setId(null);
        // 根据规则自动填充设备全称
        // 2024-6-6 标准全称改为手动输入
//		entity.setFullName(orderNumberUtil.getDeviceFullName(storage.getDeviceSource(), storage.getWbsElement(), storage.getWbsProject(), storage.getDeviceType()));
        String deviceHardwareInfo = entity.getDeviceHardwareInfo();
        if (StringUtil.isNotBlank(deviceHardwareInfo)) {
            Map<String, Object> map = JSON.parseObject(deviceHardwareInfo, Map.class);
            String fullName = String.valueOf(map.get("fullName")).replace("null", "");
            if (StringUtil.isNotBlank(fullName)) {
                entity.setFullName(fullName);
            }
        }
        // 设备名称导入为空则自动填充
        if (StringUtil.isBlank(entity.getDeviceName())) {
            entity.setDeviceName(orderNumberUtil.getDeviceName(storage.getDeviceType()));
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        entity.setUuid(uuid);
    }


}

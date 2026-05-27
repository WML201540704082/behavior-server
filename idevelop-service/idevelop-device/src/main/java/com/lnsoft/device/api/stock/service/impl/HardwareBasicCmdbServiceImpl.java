package com.lnsoft.device.api.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientitySearch;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.cmdb.vo.HardwareBasicQueryVO;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.oss.model.IdevelopFile;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.*;
import com.lnsoft.device.annotation.EasyExcelUtil;
import com.lnsoft.device.annotation.ExcelSelected;
import com.lnsoft.device.api.asset.dto.ResourceCabinetsDTO;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.lnsoft.device.api.cmdb.dto.CmdbStockDTO;
import com.lnsoft.device.api.cmdb.entity.FileImportInfo;
import com.lnsoft.device.api.cmdb.entity.FileImportInfoParent;
import com.lnsoft.device.api.cmdb.entity.FileInfo;
import com.lnsoft.device.api.cmdb.handler.CommentHeaderWriteHandler;
import com.lnsoft.device.api.cmdb.mapper.HandlerDeviceMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbResourcecenterTypeCiService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import com.lnsoft.device.api.i6000.dto.I6000CiCientityDTO;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.wrapper.DeptWrapper;
import com.lnsoft.device.api.stock.dto.*;
import com.lnsoft.device.api.stock.service.FileImportInfoService;
import com.lnsoft.device.api.stock.service.FileInfoParentService;
import com.lnsoft.device.api.stock.service.FileInfoService;
import com.lnsoft.device.api.stock.service.IHardwareBasicCmdbService;
import com.lnsoft.device.api.stock.vo.ErrorListVo;
import com.lnsoft.device.api.stock.vo.HardwareBasicCmdbDeviceVO;
import com.lnsoft.device.api.stock.wrapper.HardwareBasicStockWrapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceStorageMapper;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;
import com.lnsoft.device.config.ExcelDeviceTemplateConfiguration;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.ModeType;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.utils.*;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.lnsoft.device.constant.CmdbAttrConstant.*;


/**
 * 数据治理数据 服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class HardwareBasicCmdbServiceImpl implements IHardwareBasicCmdbService {

    private ThirdProperties properties;
    private ICmdbService cmdbService;
    private CmdbCientityProperties cmdbCientityProperties;
    private CmdbDictProperties cmdbDictProperties;
    private RedisUtil redisUtil;
    private HandlerDeviceMapper handlerDeviceMapper;
    private II6000Service i6000Service;
    private ICmdbClient cmdbClient;
    private IWarehouseService iWarehouseService;
    private OrderNumberUtil orderNumberUtil;
    private FileInfoService fileInfoService;
    private FileImportInfoService fileImportInfoService;
    private FileInfoParentService fileInfoParentService;
    private IResourceCabinetsService cabinetsService;
    private IZfitXtCwztService zfitXtCwztService;
    private IDeptClient deptClient;
    private ICmdbService iCmdbService;
    private ICmdbResourcecenterTypeCiService cmdbResourcecenterTypeCiService;

    @Resource
    private DeviceStorageMapper deviceStorageMapper;
    @Resource
    private AliossTemplate aliossTemplate;


    @Override
    public void importByExceldownload(HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO, HttpServletResponse response) {
        try {
            //判断
            List<Map<String, Object>> records = hardwareBasicCmdbDeviceVO.getRecords();
            if (records == null || records.size() == 0) {
                records = new ArrayList<>();
            }
            //获取文件名称
            String fileName = "设备台账数据结果.xlsx";
            //文件输出格式
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", -1);
            response.setHeader("Content-Disposition", " attachment;filename=" + fileName);
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setWrapped(false);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .build();
            //根据类型获取 指定的模型 实体类
            Class<T> cls = getClassByClassif(hardwareBasicCmdbDeviceVO.getDeviceCategory());
            //创建 sheet
            WriteSheet writeSheet = createWriteSheetByClassif(cls, getTypeNameByDeviceCategory(hardwareBasicCmdbDeviceVO.getDeviceCategory()));
            //获取实体类
            excelWriter.write(getBeanByMap(records, cls), writeSheet);
            excelWriter.finish();
        } catch (Exception e) {
            log.error("设备台账数据下载失败：{}", e);
            throw new ServiceException("设备台账下载失败!");
        }
    }

    /**
     * 导出异常数据
     *
     * @param deviceVO
     * @param response
     */
    @Override
    public void downloadError(HardwareBasicCmdbDeviceVO deviceVO, HttpServletResponse response) {
        List<Map<String, Object>> records = deviceVO.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            records = new ArrayList<>();
        }
        try {
            //获取文件名称
            String categoryName = getTypeNameByDeviceCategory(deviceVO.getDeviceCategory());
            String fileName = URLEncoder.encode(categoryName + "设备台账异常数据结果", StandardCharsets.UTF_8.name()) + ".xlsx";
            //根据类型获取 指定的模型 实体类
            Class<T> cls = getErrorClassByClassif(deviceVO.getDeviceCategory());
            TreeMap<Integer, ExcelReadBean> treeMap = checkForDown(deviceVO.getDeviceCategory());
            List<String> index = deviceVO.getErrAddr();
            List<Object> beans = getErrorBeanByMap(records, cls);
            EasyExcelUtil.exportExcelWithBackGroundError(treeMap, beans, response, cls, fileName, categoryName, index);
        } catch (Exception e) {
            log.error("设备台账异常数据下载失败：{}", e.getMessage());
            throw new ServiceException("设备台账异常数据下载失败!原因:" + e.getMessage());
        }
    }

    private Class<T> getErrorClassByClassif(String deviceCategory) {
        Class cls = HardwareBasicCmdbDeviceDTO.class;
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            cls = HardwareBasicCmdbDeviceErrorJCSSDTO.class;
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
            // 字典枚举ID-办公设备
            cls = HardwareBasicCmdbDeviceErrorBGSBDTO.class;
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 字典枚举ID-辅助设备
            cls = HardwareBasicCmdbDeviceErrorFZSBDTO.class;
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            cls = HardwareBasicCmdbDeviceErrorZDSBDTO.class;
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            cls = HardwareBasicCmdbDeviceErrorAQSBDTO.class;
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
            // 字典枚举ID-网络设备
            cls = HardwareBasicCmdbDeviceErrorWLSBDTO.class;
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
            // 字典枚举ID-存储设备
            cls = HardwareBasicCmdbDeviceErrorCCSBDTO.class;
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
            // 字典枚举ID-主机设备
            cls = HardwareBasicCmdbDeviceErrorZJSBDTO.class;
        }
        return cls;
    }

    @Override
    public Map<String, Object> cmdbDataCheckForUpdate(Map<String, Object> map, String deviceCategory) {
        Map<String, Object> result = new HashMap<>();
        //在运 安装地点
        try {
            deviceDateHandler(map, "deviceInstallationSiteRequiredHandler");
            //统一纳管设备 ERP编码不能为空
            deviceDateHandler(map, "deviceErpAccountCodeRequiredHandler");
            //在运 责任人必填
            deviceDateHandler(map, "deviceMiUserRequiredHandler");
            //在运 责任人联系方式
            deviceDateHandler(map, "deviceMiOmPhoneRequiredHandler");
            deviceDateHandler(map, "deviceSourceRequiredHandler");
        } catch (Exception e) {
            log.error("错误");
        }
        result.put("data", map);
        result.put("exceptionField", map.get("exceptionField"));
        return result;
    }

    @Override
    public Map<String, Object> cmdbDataCheck(Map<String, Object> map, String deviceCategory) {
        Map<String, Object> result = new HashMap<>();
        try {
            //判断前端传递阐述与获取的 设备分类一致
            if (StringUtil.isNotBlank(deviceCategory)) {
                checkdeviceCategoryStand(map, deviceCategory);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        //获取 数据库 类型对应是否需要检查IP MAC 网络类型
        String deviceTypeCode = String.valueOf(map.get("deviceTypeCode")).replace("null", "");
        boolean netFlag = getNetFlagByDeviceTypeCode(deviceTypeCode);
        try {
            //判断领用单位 部门合法
            checkDeviceUnitHandler(map);
            //判断设备来源 采购方式 设备增加方式
            checkDeviceSourceHandler(map);
            //判断设备类型 设备分类一致
            checkDeviceCategoryTypeHandler(map);
            //判断品牌系列型号一致
            checkBandSerMdHandler(map, "deviceMakerBrandSeriesModeRequiredHandler");
            //设备状态只能为在运/库存设备/退运设备/待报废/已报废的规则校验
            deviceUnitStatusRequiredHandler(map);
            // 字典枚举ID- 根据用户提供清单，在mysql中按照类型配置是否需要校验校验网络
            if (netFlag && !cmdbCientityProperties.getT106().equals(deviceCategory)) {
                //在运设备  在运、停运设备IP缺失规则校验任务 基础设施不含IP
                deviceIPLessHandler(map, "deviceIPLessHandler");
                //在运 所属网络 不能为空，符合规范
                deviceDateHandler(map, "deviceNetworkRequiredHandler");
                //在运设备mac不能为空，符合规范
                deviceDateHandler(map, "deviceAppMacHandler");
            }
            //售后服务时间不能为空，格式正确， 不能早于出厂日期 采购日期
            deviceDateHandler(map, "deviceAfterExpDateHandler");
            // 1.设备状态为在运时，投运日期不能为空
            // 2.投运日期格式异常
            // 3.投运日期不能早于投运日期;
            // 4.投运日期不能晚于现在；
            // 5IP存在时，投运日期不能为空
            deviceDateHandler(map, "devicePutDateHandler");
            //统一纳管设备 ERP编码不能为空
            deviceDateHandler(map, "deviceErpAccountCodeRequiredHandler");
            //库存备用 退运在库设备  仓库不能为空
            deviceDateHandler(map, "deviceAppDevInWarehouseRequiredHandler");

            //在运 责任人必填
            deviceDateHandler(map, "deviceMiUserRequiredHandler");
            //在运 责任人联系方式
            deviceDateHandler(map, "deviceMiOmPhoneRequiredHandler");
            //在运 责任人身份证号
            deviceDateHandler(map, "deviceMiUseIdCardRequiredHandler");

            //在运 安装地点
            deviceDateHandler(map, "deviceInstallationSiteRequiredHandler");


            //出厂序列号 sn  非辅助/办公分类 不能为空
            // if (!cmdbCientityProperties.getT107().equals(deviceCategory) && cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 	deviceDateHandler(map, "deviceSNRequiredHandler");
            // }

            if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
                // 校验基础设施特征属性
                checkT109CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
                // 校验 办公设备特征属性
                checkT107CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
                // 校验 安全设备特征属性
                checkT104CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
                // 校验主机设备特征属性
                checkT101CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
                // 校验终端设备特征属性
                checkT105CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
                // 校验网络设备特征属性
                checkT103CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
                // 校验存储设备特征属性
                checkT102CharacteristicAttribute(map);
            }
            if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
                // 校验辅助设备特征属性
                checkT106CharacteristicAttribute(map);
            }

            // 恢复设备类型名称
            //toCmdbValByCol(String.valueOf(map.get("deviceType")),"deviceTypeCode",map,cls);
            //设置异常信息
            //map.put("exceptionField", exceptionField);
        } catch (Exception e) {
            log.error("校验失败");
        }
        //返回方法
        result.put("data", map);
        result.put("exceptionField", map.get("exceptionField"));
        return result;
    }

    public Map<String, Object> cmdbDataCheck1(Map<String, Object> map, String deviceCategory, int rowIndex,
                                              HardwareBasicCmdbDeviceVO vo, Map<String, Integer> fieldColumn, List<String> errAddr, IdevelopUser user) {
        Map<String, Object> result = new HashMap<>();
        //判断前端传递阐述与获取的 设备分类一致
        if (StringUtil.isNotBlank(deviceCategory)) {
            checkdeviceCategoryStand1(map, deviceCategory, errAddr, fieldColumn, rowIndex);
        }
        //获取 数据库 类型对应是否需要检查IP MAC 网络类型
        String deviceTypeCode = String.valueOf(map.get("deviceTypeCode")).replace("null", "");
        boolean netFlag = getNetFlagByDeviceTypeCode(deviceTypeCode);
        try {
            //判断领用单位 部门合法
            checkDeviceUnitHandler1(map, errAddr, fieldColumn, rowIndex);
            checkFullName(map, errAddr, fieldColumn, rowIndex);
            //判断设备来源 采购方式
            deviceDateHandler1(map, "deviceSourceRequiredHandler", errAddr, fieldColumn, rowIndex);
            // 设备增加方式
            checkDeviceAddTypeHandler(map, errAddr, fieldColumn, rowIndex);
            //判断设备类型 设备分类一致
            map.put("deviceTypeCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("device-type"));
            map.put("deviceCategoryCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("device-claccify"));
            deviceDateHandler1(map, "deviceCategoryTypeRequiredHandler", errAddr, fieldColumn, rowIndex);
            //判断品牌系列型号一致
            checkBrandSeriesModel(map, errAddr, fieldColumn, rowIndex);
            //设备状态只能为在运/库存设备/退运设备/待报废/已报废的规则校验
            deviceDateHandler1(map, "deviceUnitStatusRequiredHandler", errAddr, fieldColumn, rowIndex);
            // 字典枚举ID- 根据用户提供清单，在mysql中按照类型配置是否需要校验校验网络
            // 2024-6-19 客户提出校核规则变更
            List<String> deviceCategoryList = Arrays.asList(cmdbCientityProperties.getT106(), cmdbCientityProperties.getT107(), cmdbCientityProperties.getT109());
            if (netFlag && !deviceCategoryList.contains(deviceCategory)) {
                //在运 所属网络 不能为空，符合规范
                deviceDateHandler1(map, "deviceNetworkRequiredHandler", errAddr, fieldColumn, rowIndex);
                //在运设备  在运、停运设备IP缺失规则校验任务 基础设施不含IP
                deviceDateHandler1(map, "deviceIPLessHandler", errAddr, fieldColumn, rowIndex);
                //在运设备mac不能为空，符合规范
                deviceDateHandler1(map, "deviceAppMacHandler", errAddr, fieldColumn, rowIndex);
            }
            // 2024-11-20 增加IP和MAC 格式校验
            deviceDateHandler1(map, "deviceIPMacCheckHandler", errAddr, fieldColumn, rowIndex);

            // 投运日期规则
            // 1.设备状态为在运时，投运日期不能为空 2.投运日期格式异常 3.投运日期不能早于投运日期; 4.投运日期不能晚于现在；5.IP存在时，投运日期不能为空
            deviceDateHandler1(map, "devicePutDateHandler", errAddr, fieldColumn, rowIndex);
            //统一纳管设备 ERP编码不能为空
            checkAndFillErpAccountCode(map, errAddr, fieldColumn, rowIndex, user);
            // 校验仓库是否存在
            checkInWarehouse(map, errAddr, fieldColumn, rowIndex);
            deviceDateHandler1(map, "deviceInWarehouseHandler", errAddr, fieldColumn, rowIndex);
            //库存备用 退运在库设备  仓库不能为空
            deviceDateHandler1(map, "deviceAppDevInWarehouseRequiredHandler", errAddr, fieldColumn, rowIndex);
            //在运 责任人必填
            deviceDateHandler1(map, "deviceMiUserRequiredHandler", errAddr, fieldColumn, rowIndex);
            //在运 责任人ISC
            checkISCHandler(map, errAddr, fieldColumn, rowIndex);
            //在运 责任人联系方式
            deviceDateHandler1(map, "deviceMiOmPhoneRequiredHandler", errAddr, fieldColumn, rowIndex);
            //在运 责任人身份证号
            deviceDateHandler1(map, "deviceMiUseIdCardRequiredHandler", errAddr, fieldColumn, rowIndex);
            //在运 安装地点
            deviceDateHandler1(map, "deviceInstallationSiteRequiredHandler", errAddr, fieldColumn, rowIndex);
            //出厂序列号 sn
            deviceDateHandler1(map, "deviceSnHandler", errAddr, fieldColumn, rowIndex);

            // 基础设施
            if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
                // 空调类型
                deviceDateHandler1(map, "deviceAirConditionTypeHandler", errAddr, fieldColumn, rowIndex);
                // 匹数
                deviceDateHandler1(map, "deviceAirHorsepowerHandler", errAddr, fieldColumn, rowIndex);
                // 制冷量
                deviceDateHandler1(map, "deviceAirCoolCapacityHandler", errAddr, fieldColumn, rowIndex);
                // UPS容量
                deviceDateHandler1(map, "deviceUpsCapacityHandler", errAddr, fieldColumn, rowIndex);
                // 电池数
                deviceDateHandler1(map, "deviceBatteryNumHandler", errAddr, fieldColumn, rowIndex);
                // 电池组数
                deviceDateHandler1(map, "deviceBatteryPackNumHandler", errAddr, fieldColumn, rowIndex);
                // 电源负载
                deviceDateHandler1(map, "devicePowerLoadHandler", errAddr, fieldColumn, rowIndex);
                // 额定容量
                deviceDateHandler1(map, "deviceRatedCapacityHandler", errAddr, fieldColumn, rowIndex);
                // 校验机房机柜
                checkComputerRoomCabinet(map, errAddr, fieldColumn, rowIndex, Boolean.FALSE);
                // 所属机房
                deviceDateHandler1(map, "deviceComputerRoomHandler", errAddr, fieldColumn, rowIndex);
                // 所属UPS
                deviceDateHandler1(map, "deviceBelongUpsHandler", errAddr, fieldColumn, rowIndex);
                //在运 电压等级
                deviceDateHandler1(map, "deviceVoltageLevelHandler", errAddr, fieldColumn, rowIndex);
            }
            // 安全设备
            if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
                // 校验机房机柜
                checkComputerRoomCabinet(map, errAddr, fieldColumn, rowIndex, Boolean.TRUE);
                // 所属机房
                deviceDateHandler1(map, "deviceComputerRoomHandler", errAddr, fieldColumn, rowIndex);
                // 所属安全边界
                deviceDateHandler1(map, "deviceSecurityBoundaryHandler", errAddr, fieldColumn, rowIndex);
                // 机柜
                deviceDateHandler1(map, "deviceCabinetHandler", errAddr, fieldColumn, rowIndex);
                // 设备起始高度
                deviceDateHandler1(map, "deviceHeightBeginHandler", errAddr, fieldColumn, rowIndex);
                // 设备高度
                deviceDateHandler1(map, "deviceHeightHandler", errAddr, fieldColumn, rowIndex);
            }
            // 主机设备
            if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
                // 校验机房机柜
                checkComputerRoomCabinet(map, errAddr, fieldColumn, rowIndex, Boolean.TRUE);
                // 所属机房
                deviceDateHandler1(map, "deviceComputerRoomHandler", errAddr, fieldColumn, rowIndex);
                // 机柜
                deviceDateHandler1(map, "deviceCabinetHandler", errAddr, fieldColumn, rowIndex);
                // 操作系统位数
                deviceDateHandler1(map, "deviceSystemNumHandler", errAddr, fieldColumn, rowIndex);
                // 设备起始高度
                deviceDateHandler1(map, "deviceHeightBeginHandler", errAddr, fieldColumn, rowIndex);
                // 设备高度
                deviceDateHandler1(map, "deviceHeightHandler", errAddr, fieldColumn, rowIndex);
                // 主机设备用途类型
                deviceDateHandler1(map, "deviceUseTypeHandler", errAddr, fieldColumn, rowIndex);
                // 所属安全边界
                deviceDateHandler1(map, "deviceSecurityBoundaryHandler", errAddr, fieldColumn, rowIndex);
                // 操作系统版本
                deviceDateHandler1(map, "deviceOSVersionHandler", errAddr, fieldColumn, rowIndex);
            }
            // 终端设备
            if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
                // 使用人
                deviceDateHandler1(map, "deviceMiChargeUserHandler", errAddr, fieldColumn, rowIndex);
                // 使用人身份证
                deviceDateHandler1(map, "deviceMiChargeUserIdCardHandler", errAddr, fieldColumn, rowIndex);
                // 在运 领用日期
                deviceDateHandler1(map, "deviceReceivingDateHandler", errAddr, fieldColumn, rowIndex);
            }
            // 网络设备
            if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
                // 校验机房机柜
                checkComputerRoomCabinet(map, errAddr, fieldColumn, rowIndex, Boolean.TRUE);
                // 网络设备用途类型
                deviceDateHandler1(map, "deviceNetworkDeviceTypeHandler", errAddr, fieldColumn, rowIndex);
                // 所属机房
                deviceDateHandler1(map, "deviceComputerRoomHandler", errAddr, fieldColumn, rowIndex);
                // 机柜
                deviceDateHandler1(map, "deviceCabinetHandler", errAddr, fieldColumn, rowIndex);
                // 设备起始高度
                deviceDateHandler1(map, "deviceHeightBeginHandler", errAddr, fieldColumn, rowIndex);
                // 设备高度
                deviceDateHandler1(map, "deviceHeightHandler", errAddr, fieldColumn, rowIndex);
                // 主备属性
                deviceDateHandler1(map, "deviceStandbyAttrHandler", errAddr, fieldColumn, rowIndex);
                // 所属安全边界
                deviceDateHandler1(map, "deviceSecurityBoundaryHandler", errAddr, fieldColumn, rowIndex);
            }
            // 存储设备
            if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
                // 校验机房机柜
                checkComputerRoomCabinet(map, errAddr, fieldColumn, rowIndex, Boolean.TRUE);
                // 所属机房
                deviceDateHandler1(map, "deviceComputerRoomHandler", errAddr, fieldColumn, rowIndex);
                // 机柜
                deviceDateHandler1(map, "deviceCabinetHandler", errAddr, fieldColumn, rowIndex);
                // 设备起始高度
                deviceDateHandler1(map, "deviceHeightBeginHandler", errAddr, fieldColumn, rowIndex);
                // 设备高度
                deviceDateHandler1(map, "deviceHeightHandler", errAddr, fieldColumn, rowIndex);
                // 所属安全边界
                deviceDateHandler1(map, "deviceSecurityBoundaryHandler", errAddr, fieldColumn, rowIndex);
                // 是否纳入云管
                deviceDateHandler1(map, "deviceIsCloudMangeHandler", errAddr, fieldColumn, rowIndex);
                // 设备使用部门
                deviceDateHandler1(map, "deviceUseDeptHandler", errAddr, fieldColumn, rowIndex);
            }
            // 辅助设备
            if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
                // 校验机房机柜
                checkComputerRoomCabinet(map, errAddr, fieldColumn, rowIndex, Boolean.FALSE);
                // 所属机房
                deviceDateHandler1(map, "deviceComputerRoomHandler", errAddr, fieldColumn, rowIndex);
                // PDU运行功率
                deviceDateHandler1(map, "devicePduOperatePowerHandler", errAddr, fieldColumn, rowIndex);
                // PDU额定功率
                deviceDateHandler1(map, "devicePduRatedPowerHandler", errAddr, fieldColumn, rowIndex);
                // 机柜容量
                deviceDateHandler1(map, "deviceCabinetCapacityHandler", errAddr, fieldColumn, rowIndex);
                // 在运 电压等级
                deviceDateHandler1(map, "deviceVoltageLevelHandler", errAddr, fieldColumn, rowIndex);
                // 布线节点数(机柜)
                deviceDateHandler1(map, "deviceWiringNodeNoHandler", errAddr, fieldColumn, rowIndex);
                // 电源负载("门禁设备", "其他机房辅助设备", "信息机房动环监控系统")
                deviceDateHandler1(map, "devicePowerLoadHandler", errAddr, fieldColumn, rowIndex);
            }

        } catch (Exception e) {
            log.error("数据治理-校验类出现异常：{}", e.getMessage());
        }
        //返回方法
        result.put("data", map);
        result.put("exceptionField", map.get("exceptionField"));
        return result;
    }

    private void checkFullName(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        String fullName = String.valueOf(map.get("fullName")).replace("null", "");
        if (StringUtil.isBlank(fullName)) {
            errMsgAppend("标准全程不能为空！ ", "fullName", map, errAddr, fieldColumn, rowIndex);
        }

    }

    private void checkAndFillErpAccountCode(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex, IdevelopUser user) {
        String deviceSource = String.valueOf(map.get("deviceSource")).replace("null", "");
        String assetCodeErp = String.valueOf(map.get("assetCodeErp")).replace("null", "");
        List<String> status = Arrays.asList("非统一纳管", "统一纳管");

        if (!status.contains(deviceSource)) {
            errMsgAppend("设备来源数据异常! ", "deviceSource", map, errAddr, fieldColumn, rowIndex);
        }
        if ("统一纳管".equals(deviceSource) && properties.getIsGovernProperty()) {
            if (StringUtil.isBlank(assetCodeErp)) {
                errMsgAppend("设备来源为统一纳管时,ERP资产编码不能为空! ", "assetCodeErp", map, errAddr, fieldColumn, rowIndex);
            } else {
                // 请求ERP中资产数据
                ZfitXtCwzt zfitXtCwzt = new ZfitXtCwzt();
                zfitXtCwzt.setAnlnr(assetCodeErp);
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
                    erpUnitList = deptList.stream().map(item -> item.getErpUnitCode()).collect(Collectors.toList());
                }
                QueryWrapper<ZfitXtCwzt> queryWrapper = Condition.getQueryWrapper(zfitXtCwzt);
                queryWrapper.in("swerk", erpUnitList);
                List<ZfitXtCwzt> zfitXtCwztList = zfitXtCwztService.list(queryWrapper);
                if (CollectionUtil.isEmpty(zfitXtCwztList)) {
                    errMsgAppend("该ERP资产编码未查询到资产信息! ", "assetCodeErp", map, errAddr, fieldColumn, rowIndex);
                } else {
                    ZfitXtCwzt zfitXtCwztOne = zfitXtCwztList.get(0);
                    System.out.println("zfitXtCwztOne: " + assetCodeErp + ": " + JSONObject.toJSONString(zfitXtCwztOne));
                    fillErpData(map, zfitXtCwztOne);
                }
            }
        }
    }

    /**
     * 请求ERP中资产数据
     *
     * @param map
     * @param zfitXtCwztOne
     */
    private void fillErpData(Map<String, Object> map, ZfitXtCwzt zfitXtCwztOne) {

        // ERP设备编码
        map.put(CmdbAttrConstant.DEVICE_CODE_ERP, zfitXtCwztOne.getEqunr());

        // 工厂区域 beber
        String beber = zfitXtCwztOne.getBeber();
        if (StringUtils.isNotEmpty(beber)) {
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
        // String zsb005 = zfitXtCwztOne.getZsb005();
        // if (StringUtils.isNotEmpty(zsb005)) {
        //     if (zsb005.length() == 1) {
        //         zsb005 = "00" + zsb005;
        //     }
        //     if (zsb005.length() == 2) {
        //         zsb005 = "0" + zsb005;
        //     }
        //     Map<String, Object> dictMapByErp = this.getDictMapByErp(cmdbDictProperties.getDeviceAddType(), zsb005, null);
        //
        //     map.put(DEVICE_ADD_TYPE_CODE, dictMapByErp.get(DICT_KEY));
        //     map.put(DEVICE_ADD_TYPE, dictMapByErp.get(DICT_VALUE));
        //
        //     // 设备变动方式
        //     String deviceAddType = dictMapByErp.get(DICT_VALUE).toString();
        //     Map<String, Object> dictMapByErp1 = this.getDictMapByErp(cmdbDictProperties.getDeviceChangeType(), null, deviceAddType);
        //     map.put(DEVICE_CHANGE_TYPE_CODE, dictMapByErp1.get(DICT_KEY));
        //     map.put(DEVICE_CHANGE_TYPE, dictMapByErp.get(DICT_VALUE));
        // }

        // 计量单位
        String zcabnZtpm1006 = zfitXtCwztOne.getZcabnZtpm1006();
        if (StringUtils.isNotEmpty(zcabnZtpm1006)) {
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
     * 请求I6000中资产数据
     *
     * @param map
     * @param i6000Map
     */
    private void fillI6000Data(Map<String, Object> map, Map<String, Object> i6000Map) {
        // 主机
        String t101 = cmdbCientityProperties.getT101();
        // 存储
        String t102 = cmdbCientityProperties.getT102();
        // 终端
        String t105 = cmdbCientityProperties.getT105();
        List<String> list3 = Arrays.asList(t101, t102, t105);
        List<String> list2 = Arrays.asList(t101, t105);
        String deviceCategoryCode = String.valueOf(map.get("deviceCategoryCode")).replace("null", "");

        Map<String, Map<String, Object>> factoryAreaMap = cmdbDictProperties.getI6000MapByI6000(cmdbDictProperties.getFactoryAreaCode());
        // 工厂区域
        if (i6000Map.containsKey(I6000AttrConstant.BEBER)) {
            String beber = (String) i6000Map.get(I6000AttrConstant.BEBER);
            Map<String, Object> beberMap = factoryAreaMap.get(beber);
            map.put(CmdbAttrConstant.FACTORY_AREA_CODE, beberMap.get("dictKey"));
            map.put(CmdbAttrConstant.FACTORY_AREA, beberMap.get("dictValue"));
        }

        // 维护工厂
        if (i6000Map.containsKey(I6000AttrConstant.OPDEP) && i6000Map.containsKey(I6000AttrConstant.OPDEP_NAME)) {
            String opdep = (String) i6000Map.get(I6000AttrConstant.OPDEP);
            String opdepName = (String) i6000Map.get(I6000AttrConstant.OPDEP_NAME);
            map.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, opdep);
            map.put(CmdbAttrConstant.MAINTENANCE_FACTORY, opdepName);
        }

        // 实物保管部门
        if (i6000Map.containsKey(I6000AttrConstant.MANAGE_DEPT) && i6000Map.containsKey(I6000AttrConstant.MANAGE_DEPT_NAME)) {
            map.put(CmdbAttrConstant.REAL_MANAGE_DEPT, String.valueOf(i6000Map.get(I6000AttrConstant.MANAGE_DEPT)));
            map.put(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME, String.valueOf(i6000Map.get(I6000AttrConstant.MANAGE_DEPT_NAME)));
        }

        // 使用保管部门
        if (i6000Map.containsKey(I6000AttrConstant.KEEP_DEPT) && i6000Map.containsKey(I6000AttrConstant.KEEP_DEPT_NAME)) {
            map.put(CmdbAttrConstant.USE_KEEP_DEPT, String.valueOf(i6000Map.get(I6000AttrConstant.KEEP_DEPT)));
            map.put(CmdbAttrConstant.USE_KEEP_DEPT_NAME, String.valueOf(i6000Map.get(I6000AttrConstant.KEEP_DEPT_NAME)));
        }
        // 功能位置
        if (i6000Map.containsKey(I6000AttrConstant.FUN_SITE) && i6000Map.containsKey(I6000AttrConstant.FUN_SITE_NAME)) {
            map.put(CmdbAttrConstant.FUN_LOCATION, String.valueOf(i6000Map.get(I6000AttrConstant.FUN_SITE)));
            map.put(CmdbAttrConstant.FUN_LOCATION_CODE, String.valueOf(i6000Map.get(I6000AttrConstant.FUN_SITE_NAME)));
        }

        // 设备变动方式
        if (i6000Map.containsKey(I6000AttrConstant.ASSET_CHANGE)) {
            FeignCmdbDictCientitySearch search02 = new FeignCmdbDictCientitySearch();
            search02.setDictKeyI6000(String.valueOf(i6000Map.get(I6000AttrConstant.ASSET_CHANGE)));
            search02.setCiId(cmdbDictProperties.getDeviceChangeType());
            R<List<Map<String, Object>>> listR = cmdbClient.feignGetCiEntityDictListById(search02);
            String assetChangeCode = null;
            String assetChange = null;
            if (ObjectUtil.isNotEmpty(listR.getData())) {
                assetChangeCode = String.valueOf(listR.getData().get(0).get("dictKey"));
                assetChange = String.valueOf(listR.getData().get(0).get("dictValue"));
            }
            map.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE, assetChangeCode);
            map.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, assetChange);
        }
        // 计量单位
        map.put(CmdbAttrConstant.MEASURE_UNIT, cmdbCientityProperties.getJldw_tai());

        // 硬盘类型 终端
        if (!t105.equals(deviceCategoryCode)) {
            if (i6000Map.containsKey(I6000AttrConstant.HARDDISK_TYPE)) {
                FeignCmdbDictCientitySearch search07 = new FeignCmdbDictCientitySearch();
                search07.setDictKeyI6000(String.valueOf(i6000Map.get(I6000AttrConstant.HARDDISK_TYPE)));
                search07.setCiId(cmdbDictProperties.getHardDiskTypeCode());
                R<List<Map<String, Object>>> listR = cmdbClient.feignGetCiEntityDictListById(search07);
                String harddiskType = null;
                if (ObjectUtil.isNotEmpty(listR.getData())) {
                    harddiskType = String.valueOf(listR.getData().get(0).get("dictKey"));
                }
                map.put(CmdbAttrConstant.HARD_DISK_TYPE_CODE, harddiskType);
            }
        }

        if (!list2.contains(deviceCategoryCode)) {
            // 硬盘容量 HARDDISK_VOLUME_TB 终端、主机
            if (i6000Map.containsKey(I6000AttrConstant.HARDDISK_VOLUME_TB)) {
                map.put(CmdbAttrConstant.HARD_DISK_CAPABILITY, String.valueOf(i6000Map.get(I6000AttrConstant.HARDDISK_VOLUME_TB)));
            }
            // CPU架构 终端、主机
            if (i6000Map.containsKey(I6000AttrConstant.CPU_ARCHITEC)) {
                FeignCmdbDictCientitySearch search08 = new FeignCmdbDictCientitySearch();
                search08.setDictKeyI6000(String.valueOf(i6000Map.get(I6000AttrConstant.CPU_ARCHITEC)));
                search08.setCiId(cmdbDictProperties.getCpuArchCode());
                R<List<Map<String, Object>>> listR = cmdbClient.feignGetCiEntityDictListById(search08);
                String cpuArchitec = null;
                if (ObjectUtil.isNotEmpty(listR.getData())) {
                    cpuArchitec = String.valueOf(listR.getData().get(0).get("dictKey"));
                }
                map.put(CmdbAttrConstant.CPU_ARCH_CODE, cpuArchitec);
            }
            // CPU品牌 CPU_BRAND 终端、主机
            if (i6000Map.containsKey(I6000AttrConstant.CPU_BRAND)) {
                FeignCmdbDictCientitySearch search09 = new FeignCmdbDictCientitySearch();
                search09.setDictKeyI6000(String.valueOf(i6000Map.get(I6000AttrConstant.CPU_BRAND)));
                search09.setCiId(cmdbDictProperties.getCpuBrand());
                R<List<Map<String, Object>>> listR = cmdbClient.feignGetCiEntityDictListById(search09);
                String cpuBrand = null;
                String cpuBrandCode = null;
                if (ObjectUtil.isNotEmpty(listR.getData())) {
                    cpuBrandCode = String.valueOf(listR.getData().get(0).get("dictKey"));
                    cpuBrand = String.valueOf(listR.getData().get(0).get("dictValue"));
                }
                map.put(CmdbAttrConstant.CPU_BRAND_CODE, cpuBrandCode);
                map.put(CmdbAttrConstant.CPU_BRAND, cpuBrand);
            }
        }

        // 内存大小 MEMORY_SIZE_ALL 终端、主机、存储
        if (!list3.contains(deviceCategoryCode)) {
            if (i6000Map.containsKey(I6000AttrConstant.MEMORY_SIZE_ALL)) {
                map.put(CmdbAttrConstant.MEM_SIZE, String.valueOf(i6000Map.get(I6000AttrConstant.MEMORY_SIZE_ALL)));
            }
        }

        // CPU主频 CPU_FRQUENCY
        if (i6000Map.containsKey(I6000AttrConstant.CPU_FRQUENCY)) {
            map.put(CmdbAttrConstant.CPU_CLOCK_SPEED, String.valueOf(i6000Map.get(I6000AttrConstant.CPU_FRQUENCY)));
            map.put(CmdbAttrConstant.CPU_FREQUECY, String.valueOf(i6000Map.get(I6000AttrConstant.CPU_FRQUENCY)));
        }

        // WBS项目
        if (i6000Map.containsKey(I6000AttrConstant.WBS_NAME)) {
            map.put(CmdbAttrConstant.WBS_ELEMENT_NAME, String.valueOf(i6000Map.get(I6000AttrConstant.WBS_NAME)));
        }
        // WBS元素
        if (i6000Map.containsKey(I6000AttrConstant.WBS)) {
            map.put(CmdbAttrConstant.WBS_ELEMENT, String.valueOf(i6000Map.get(I6000AttrConstant.WBS)));
        }
    }

    private void checkBrandSeriesModel(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        String brand = String.valueOf(map.get("brand")).replace("null", "");
        String brandCode = String.valueOf(map.get("brandCode")).replace("null", "");
        String series = String.valueOf(map.get("series")).replace("null", "");
        String seriesCode = String.valueOf(map.get("seriesCode")).replace("null", "");
        String deviceModel = String.valueOf(map.get("deviceModel")).replace("null", "");
        String deviceModelCode = String.valueOf(map.get("deviceModelCode")).replace("null", "");
        String maker = String.valueOf(map.get("maker")).replace("null", "");
        String makerCode = String.valueOf(map.get("makerCode")).replace("null", "");
        if (checkMaker((Long) map.get("makerCid"), maker, makerCode)) {
            errMsgAppend("制造商与品牌上下级不匹配! ", "maker", map, errAddr, fieldColumn, rowIndex);
            errMsgAppend("", "brand", map, errAddr, fieldColumn, rowIndex);
        } else {
            if (checkType((Long) map.get("seriesCid"), series, brandCode)) {
                errMsgAppend("品牌与系列上下级不匹配! ", "brand", map, errAddr, fieldColumn, rowIndex);
                errMsgAppend("", "series", map, errAddr, fieldColumn, rowIndex);
            } else {
                if (checkType((Long) map.get("deviceModelCid"), deviceModel, seriesCode)) {
                    errMsgAppend("系列与型号上下级不匹配! ", "series", map, errAddr, fieldColumn, rowIndex);
                    errMsgAppend("", "deviceModel", map, errAddr, fieldColumn, rowIndex);
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

    /**
     * 组装错误信息
     *
     * @param errMsg      错误信息
     * @param errField    错误字段
     * @param map         数据map
     * @param errAddr     异常坐标
     * @param fieldColumn 字段在excel中的定位
     * @param rowIndex    行数
     */
    private void errMsgAppend(String errMsg, String errField, Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        exceptionFieldAppend(errMsg, map);
        errAddrAppend(errField, errAddr, fieldColumn, rowIndex);
    }

    private void checkISCHandler(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        String basicState = String.valueOf(map.get("deviceStatus")).replace("null", "");
        String receivePersonUnifiedAcc = String.valueOf(map.get("receivePersonUnifiedAcc")).replace("null", "");
        if ("在运".equals(basicState) && StringUtil.isBlank(receivePersonUnifiedAcc)) {
            errMsgAppend("设备状态为在运时,责任人ISC(统一权限)账号不能为空! ", "receivePersonUnifiedAcc", map, errAddr, fieldColumn, rowIndex);
        }
    }

    private void checkDeviceAddTypeHandler(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        String deviceSource = String.valueOf(map.get("deviceSource")).replace("null", "");
        String deviceAddType = String.valueOf(map.get("deviceAddType")).replace("null", "");
        if ("统一纳管".equals(deviceSource) && StringUtil.isBlank(deviceAddType)) {
            errMsgAppend("设备来源为统一纳管时,设备增加方式不能为空! ", "deviceAddType", map, errAddr, fieldColumn, rowIndex);
        } else {
            Map<String, Object> dictMapByErp1 = this.getDictMapByErp(cmdbDictProperties.getDeviceChangeType(), null, deviceAddType);
            map.put(DEVICE_CHANGE_TYPE_CODE, dictMapByErp1.get(DICT_KEY));
            map.put(DEVICE_CHANGE_TYPE, deviceAddType);
        }
    }

    private void checkInWarehouse(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        String inWarehouse = String.valueOf(map.get("inWarehouse")).replace("null", "");
        String regionCode = String.valueOf(map.get("regionCode")).replace("null", "");
        // String deviceStatus = String.valueOf(map.get("deviceStatus")).replace("null", "");
        // List<String> status = Arrays.asList("库存备用", "退运在库", "待报废");
        if (StringUtil.isNotBlank(inWarehouse)) {
            List<WarehouseVO> warehouseVOList = iWarehouseService.findByRegionCodeAndName(regionCode, inWarehouse);
            if (CollectionUtil.isEmpty(warehouseVOList)) {
                errMsgAppend("根据所在仓库名称获取编码失败! ", "inWarehouse", map, errAddr, fieldColumn, rowIndex);
            }
            WarehouseVO warehouseVO = warehouseVOList.get(0);
            map.put(CmdbAttrConstant.IN_WAREHOUSE_CODE, warehouseVO.getUuid());
        }
    }

    // 机房
    private void checkComputerRoomCabinet(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex, Boolean needCabinet) {
        // 设备状态
        String deviceStatus = String.valueOf(map.get("deviceStatus")).replace("null", "");
        // 机房
        String computerRoom = String.valueOf(map.get("computerRoom")).replace("null", "");
        // 区域编码
        String regionCode = String.valueOf(map.get("regionCode")).replace("null", "");
        // 机柜
        String cabinet = String.valueOf(map.get("cabinet")).replace("null", "");

        if (StringUtil.isNotBlank(regionCode)) {
            if (StringUtils.equals("在运", deviceStatus)) {
                String roomId = handlerDeviceMapper.findComputerRoomUuid(computerRoom, regionCode);
                if (StringUtil.isBlank(roomId)) {
                    errMsgAppend("在运时, 根据机房名称获取机房信息失败! ", "computerRoom", map, errAddr, fieldColumn, rowIndex);
                }
                map.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, roomId);
                if (needCabinet) {
                    ResourceCabinetsDTO resourceCabinetsDTO = new ResourceCabinetsDTO();
                    resourceCabinetsDTO.setCabinet(cabinet);
                    resourceCabinetsDTO.setRoomId(roomId);
                    FeignCiCientity feignCiCientity = cabinetsService.getCabinets(resourceCabinetsDTO, new Query());
                    if (ObjectUtil.isEmpty(feignCiCientity)) {
                        errMsgAppend("在运时, 根据机房名称获取机柜信息失败！", "cabinet", map, errAddr, fieldColumn, rowIndex);
                    }
                    String cabinetCode = String.valueOf(feignCiCientity.getData().get(0).get(CmdbAttrConstant.ID));
                    if (StringUtil.isBlank(cabinetCode)) {
                        errMsgAppend("在运时, 根据机柜名称获取机柜信息失败! ", "cabinet", map, errAddr, fieldColumn, rowIndex);
                    }
                    map.put(CmdbAttrConstant.CABINET_CODE, cabinetCode);
                }
            } else {
                if (StringUtils.isNotBlank(computerRoom)) {
                    String roomId = handlerDeviceMapper.findComputerRoomUuid(computerRoom, regionCode);
                    if (StringUtil.isBlank(roomId)) {
                        errMsgAppend("根据机房名称获取机房信息失败! ", "computerRoom", map, errAddr, fieldColumn, rowIndex);
                    } else {
                        map.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, roomId);
                    }
                    if (needCabinet) {
                        ResourceCabinetsDTO resourceCabinetsDTO = new ResourceCabinetsDTO();
                        resourceCabinetsDTO.setCabinet(cabinet);
                        resourceCabinetsDTO.setRoomId(roomId);
                        FeignCiCientity feignCiCientity = cabinetsService.getCabinets(resourceCabinetsDTO, new Query());
                        if (ObjectUtil.isEmpty(feignCiCientity)) {
                            errMsgAppend("根据机房名称获取机柜信息失败！", "cabinet", map, errAddr, fieldColumn, rowIndex);
                        } else {
                            String cabinetCode = String.valueOf(feignCiCientity.getData().get(0).get(CmdbAttrConstant.ID));
                            if (StringUtil.isBlank(cabinetCode)) {
                                errMsgAppend("根据机柜名称获取机柜信息失败! ", "cabinet", map, errAddr, fieldColumn, rowIndex);
                            } else {
                                map.put(CmdbAttrConstant.CABINET_CODE, cabinetCode);
                            }
                        }
                    }
                }
            }
        } else {
            log.error("数据治理-检验机房/机柜-获取区域编码为空");
        }

    }

    @Override
    public SimpleSafeAccessSwitchesDTO getInfoByIP(String ip) {
        SimpleSafeAccessSwitchesDTO switchs = handlerDeviceMapper.getInfoByIP(ip);

        if (ObjectUtil.isNotEmpty(switchs)) {
            if (StringUtil.isNotBlank(switchs.getSnmpReadStr())) {
                try {
                    switchs.setSnmpReadStr(Common.decryptAES(switchs.getSnmpReadStr()));
                } catch (Exception e) {
                    switchs.setSnmpReadStr(switchs.getSnmpReadStr());
                }
            }
            if (StringUtil.isNotBlank(switchs.getSnmpWriteStr())) {
                try {
                    switchs.setSnmpWriteStr(Common.decryptAES(switchs.getSnmpWriteStr()));
                } catch (Exception e) {
                    switchs.setSnmpWriteStr(switchs.getSnmpWriteStr());
                }
            }
            if (StringUtil.isNotBlank(switchs.getConfigPass())) {
                try {
                    switchs.setConfigPass(Common.decryptAES(switchs.getConfigPass()));
                } catch (Exception e) {
                    switchs.setConfigPass(switchs.getConfigPass());
                }
            }
            if (StringUtil.isNotBlank(switchs.getTelPass())) {
                try {
                    switchs.setTelPass(Common.decryptAES(switchs.getTelPass()));
                } catch (Exception e) {
                    switchs.setTelPass(switchs.getTelPass());
                }
            }
        }
        return switchs;
    }

    @Override
    public void importNew(MultipartFile file, String deviceCategory, String userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                excuteSaveAndDelete(file, deviceCategory, userId);
            }
        }).start();
    }

    // 批量存储数据 删除异常数据
    @Transactional
    public void excuteSaveAndDelete(MultipartFile file, String deviceCategory, String userId) {
        if (StringUtil.isBlank(deviceCategory)) {
            log.error("设备分类不能为null");
        } else {
            Class<T> cls = getClassByClassif(deviceCategory);//getVOClassByClassif(deviceCategory);
            List<Map<String, Object>> list = new ArrayList<>();
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                //解析数据文件
                EasyExcel.read(inputStream, cls, new HardBasicCmdbDeviceListener(list)).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
                HardwareBasicCmdbDeviceVO save = new HardwareBasicCmdbDeviceVO();
                save.setRecords(list);
                customSaveBatch(save);
                // 删除异常记录
                FileImportInfoParent infoParent = fileInfoParentService.getBaseMapper().selectOne(new LambdaQueryWrapper<FileImportInfoParent>().eq(FileImportInfoParent::getFileType,
                        deviceCategory).eq(FileImportInfoParent::getCreateUser, userId));
                fileInfoParentService.getBaseMapper().deleteById(infoParent);
                fileImportInfoService.getBaseMapper().delete(new QueryWrapper<FileImportInfo>().eq("parent_id", infoParent.getId()));
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.error("流关闭失败");
                    }
                }
            }
        }
    }

    @Override
    public R errorList(ErrorListVo errorListVo) {
        if (errorListVo == null) {
            return R.fail("参数为null");
        }
        //查父
        FileImportInfoParent infoParent = fileInfoParentService.getBaseMapper().selectOne(new LambdaQueryWrapper<FileImportInfoParent>().eq(FileImportInfoParent::getFileType,
                errorListVo.getDeviceType()).eq(FileImportInfoParent::getCreateUser, errorListVo.getUserId()));
        // 查子集合
        List<FileImportInfo> list = fileImportInfoService.getBaseMapper().selectList(new LambdaQueryWrapper<FileImportInfo>().eq(FileImportInfo::getParentId, infoParent.getId()));
        Map<String, Object> result = new HashMap<>();
        result.put("infoParent", infoParent);
        result.put("infoParentList", list);
        return R.data(result);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param deviceCategory
     * @param userId
     * @return
     */
    @Override
    public R importByExcelNew(MultipartFile file, String deviceCategory, String userId) {
        if (StringUtil.isBlank(deviceCategory)) {
            return R.fail("设备类型不能为null");
        }
        FileImportInfoParent saveParent = fileInfoParentService.getBaseMapper().selectOne(new QueryWrapper<FileImportInfoParent>()
                .eq("file_type", deviceCategory).eq("create_user", userId));
        if (saveParent != null && saveParent.getIsFinishMind() != 1) {
            return R.fail("上个文件未解析完毕，请稍后再试");
        }
        //返解析数据
        InputStream stream = null;
        try {
            stream = file.getInputStream();
            if (saveParent != null) {
                saveParent.setFileName(file.getOriginalFilename());
                byte[] data = new byte[(int) file.getSize()];
                stream.read(data);
                saveParent.setFileInfo(data);
                fileInfoParentService.getBaseMapper().updateById(saveParent);
            } else {
                saveParent = new FileImportInfoParent();
                saveParent.setFileType(deviceCategory);
                saveParent.setCreateUser(Long.valueOf(userId));
                saveParent.setFileName(file.getOriginalFilename());
                byte[] data = new byte[(int) file.getSize()];
                stream.read(data);
                saveParent.setFileInfo(data);
                fileInfoParentService.getBaseMapper().insert(saveParent);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return R.success("上传成功");
    }

    @Override
    public R resolver(String fileId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileImportInfoParent parent = fileInfoParentService.getBaseMapper().selectById(fileId);
                    byte[] data = parent.getFileInfo();
                    if (StringUtil.isBlank(parent.getFileType())) {
                        log.error("文件类型不能为null");
                    } else {
                        Class<T> cls = getClassByClassif(parent.getFileType());
                        List<Map<String, Object>> list = new ArrayList<>();
                        //返解析数据
                        HardwareBasicCmdbDeviceVO vo = new HardwareBasicCmdbDeviceVO();
                        InputStream inputStream = new ByteArrayInputStream(data);
                        //解析数据文件
                        EasyExcel.read(inputStream, cls, new HardBasicCmdbDeviceListener(list)).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
                        //获取异常信息
                        resolverFillErrorMessage(list, vo, cls, parent.getFileType(), parent);
                        // 保存异常信息进库
                        parent.setErrorFileInfo(javaBeanToByteArray(vo));
                        parent.setIsFinishMind(1);
                        fileInfoParentService.getBaseMapper().updateById(parent);
                    }
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }).start();
        return R.success("success");
    }

    @Override
    public void downloadNew(String fileId, HttpServletResponse response) {
        FileImportInfoParent parent = fileInfoParentService.getBaseMapper().selectById(fileId);
        byte[] data = parent.getErrorFileInfo();
        HardwareBasicCmdbDeviceVO vo = byteArrayToJavaBean(data);
        importByExceldownload(vo, response);
    }

    @Override
    public R addNums(String id) {
        FileInfo fileInfo = fileInfoService.getById(id);
        fileInfo.setNums(fileInfo.getNums() + 1);
        fileInfoService.updateById(fileInfo);
        return R.success("操作成功");
    }

    @Override
    public R allNums(String userId) {
        Long s = fileInfoService.getBaseMapper().selectCount(new QueryWrapper<FileInfo>()
                .eq("nums", 0).eq("create_user", userId));
        return R.data(s);
    }


    /**
     * 用于数据治理修改接口
     *
     * @param cmdbStockDTO
     * @return
     */
    @Override
    public Map<String, Object> cientityBatchupdateStock(CmdbStockDTO cmdbStockDTO) {

        Map<String, Object> stockMap = cmdbStockDTO.getStockMap();
        String deviceCode = cmdbStockDTO.getDeviceCode();
        String deviceTypeCode = cmdbStockDTO.getDeviceTypeCode();
        Long filterCiEntityId = cmdbStockDTO.getFilterCiEntityId();
        if (StringUtils.isEmpty(deviceCode) || CollectionUtils.isEmpty(stockMap) || StringUtils.isEmpty(deviceTypeCode)) {
            throw new ServiceException("参数不能为空!");
        }

        Query query = new Query();
        query.setCurrent(1);
        query.setSize(10);

        // TODO 临时处理
        Long deviceCiId = cmdbResourcecenterTypeCiService.getItDeviceCiId();
        FeignCmdbCientitySearch cientitySearch = new FeignCmdbCientitySearch();
        cientitySearch.setCiId(deviceCiId);
        cientitySearch.setCurrentPage(query.getCurrent());
        cientitySearch.setPageSize(query.getSize());
        cientitySearch.setMode(ModeType.PAGE.getValue());
        cientitySearch.setNeedAction(Boolean.TRUE);
        cientitySearch.setNeedActionType(Boolean.FALSE);
        cientitySearch.setNeedCheck(Boolean.TRUE);
        cientitySearch.setNeedExpand(Boolean.FALSE);

        List<FeignCmdbCientitySearch.CiEntitySearchAttr> ciEntitySearchAttrs = new ArrayList<>();
        FeignCmdbCientitySearch.CiEntitySearchAttr ciEntitySearchAttr = new FeignCmdbCientitySearch.CiEntitySearchAttr();
        ciEntitySearchAttr.setAttrId(1082375867269120L);
        ciEntitySearchAttr.setValueList(Lists.newArrayList(deviceCode));
        ciEntitySearchAttr.setExpression(Expression.EQUAL.getExpression());
        ciEntitySearchAttrs.add(ciEntitySearchAttr);

        cientitySearch.setAttrFilterList(ciEntitySearchAttrs);
        if (Objects.nonNull(filterCiEntityId)) {
            cientitySearch.setFilterCiEntityId(filterCiEntityId);
        }
        R<FeignCiCientity> ciCientityListPage = CmdbCiAttrWrapper.build().getCiCientityListPage(cientitySearch);
        FeignCiCientity ciCientityList = ciCientityListPage.getData();

        if (CollectionUtils.isEmpty(ciCientityList.getData())) {
            //新增
            HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
            hardwareBasicTree.setDeviceType(deviceTypeCode);
            Long ciId = iCmdbService.getCiId(hardwareBasicTree);
            String uuid = UuidUtils.uuid();
            Map<String, Map<String, Object>> entityMap = new HashMap<>();
            entityMap.put(uuid, stockMap);
            return iCmdbService.cientityBatchsave(ciId, entityMap, TransactionActionType.INSERT);
        } else {
            //修改
            List<Map<String, Object>> data = ciCientityList.getData();
            Map<String, Object> map = data.get(0);
            String deviceCodeCheck = String.valueOf(map.get("deviceCode"));
            if (!StringUtils.equals(deviceCode, deviceCodeCheck)) {
                throw new RuntimeException("设备编码不唯一，请校验！");
            }

            Long id = (Long) map.get(CmdbAttrConstant.ID);
            String deviceTypeNew = String.valueOf(stockMap.get(CmdbAttrConstant.DEVICE_TYPE_CODE));
            String deviceTypeOld = String.valueOf(map.get(CmdbAttrConstant.DEVICE_TYPE_CODE));
            if (!StringUtils.equals(deviceTypeNew, deviceTypeOld)) {

                Boolean delete = iCmdbService.cientityDelete(id, "数据治理删除");
                if (delete) {
                    HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
                    hardwareBasicTree.setDeviceType(deviceTypeNew);
                    Long ciId = iCmdbService.getCiId(hardwareBasicTree);

                    String uuid = UuidUtils.uuid();
                    Map<String, Map<String, Object>> entityMap = new HashMap<>();
                    entityMap.put(uuid, stockMap);
                    return iCmdbService.cientityBatchsave(ciId, entityMap, TransactionActionType.INSERT);
                }
            }

            stockMap.put(CmdbAttrConstant.UUID, map.get(CmdbAttrConstant.UUID));
            stockMap.put(CmdbAttrConstant.ID, id);
            stockMap.put(CmdbAttrConstant.CI_ID, map.get(CmdbAttrConstant.CI_ID));
            Map<Long, Map<String, Object>> entityMap = new HashMap<>();
            stockMap.remove(DEVICE_CODE);
            entityMap.put(id, stockMap);

            return iCmdbService.cientityBatchupdate(entityMap, TransactionActionType.UPDATE);
        }

    }

    /**
     * array转成HardwareBasicCmdbDeviceVO
     *
     * @param array
     * @return
     */
    public HardwareBasicCmdbDeviceVO byteArrayToJavaBean(byte[] array) {
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(stream);
            try {
                HardwareBasicCmdbDeviceVO vo = (HardwareBasicCmdbDeviceVO) objectInputStream.readObject();
                objectInputStream.close();
                return vo;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * HardwareBasicCmdbDeviceVO转成byte[]
     *
     * @param vo
     * @return
     */
    public byte[] javaBeanToByteArray(HardwareBasicCmdbDeviceVO vo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(vo);
            oos.close();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 针对新版解析封装的异常方法
     *
     * @param list
     * @param vo
     * @param cls
     * @param deviceCategory
     */
    private void resolverFillErrorMessage(List<Map<String, Object>> list, HardwareBasicCmdbDeviceVO vo,
                                          Class<T> cls, String deviceCategory, FileImportInfoParent parent) {
        List<Map<String, Object>> listA = new ArrayList<>();
        cls = getVOClassByClassif(deviceCategory);
        int index = 1;
        for (Map<String, Object> map : list) {
            try {
                checkTag(map, (String) map.get("dataOptType"));
                potting(String.valueOf(map.get("isITAI")), "isITAICode", map, cls);
                potting(String.valueOf(map.get("deviceSource")), "deviceSourceCode", map, cls);
                potting(String.valueOf(map.get("maker")), "makerCode", map, cls);
                potting(String.valueOf(map.get("brand")), "brandCode", map, cls);
                potting(String.valueOf(map.get("series")), "seriesCode", map, cls);
                potting(String.valueOf(map.get("deviceModel")), "deviceModelCode", map, cls);
                potting(String.valueOf(map.get("deviceStatus")), "deviceStatusCode", map, cls);
                potting(String.valueOf(map.get("isToErp")), "isToErpCode", map, cls);
                potting(String.valueOf(map.get("isToI6000")), "isToI6000Code", map, cls);
                potting(String.valueOf(map.get("cpuBrand")), "cpuBrandCode", map, cls);
                potting(String.valueOf(map.get("netWork")), "netWorkCode", map, cls);
                potting(String.valueOf(map.get("procureType")), "procureTypeCode", map, cls);
                potting(String.valueOf(map.get("isExternalUnit")), "isExternalUnitCode", map, cls);
                potting(String.valueOf(map.get("serviceLevel")), "serviceLevelCode", map, cls);
                potting(String.valueOf(map.get("scrapReason")), "scrapReasonCode", map, cls);
                potting(String.valueOf(map.get("deviceType")), "deviceTypeCode", map, cls);
                potting(String.valueOf(map.get("deviceCategory")), "deviceCategoryCode", map, cls);
                potting(String.valueOf(map.get("voltageLevel")), "voltageLevelCode", map, cls);
                potting(String.valueOf(map.get("deviceAddType")), "deviceAddTypeCode", map, cls);
                potting(String.valueOf(map.get("factoryArea")), "factoryAreaCode", map, cls);
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
            //校验 方法
            Map<String, Object> result = cmdbDataCheck(map, deviceCategory);
            String execpStr = String.valueOf(result.get("exceptionField"));
            saveImportInfo(execpStr, parent, index, list.size());
            try {
                //新数据补全 数据  当 设备编码为空时
                if (StringUtil.isBlank(map.get("deviceCode").toString().replace("null", "").trim())) {
                    // 获取设备类型 type -> Txxxx  区域 部门
                    map.put("deviceTypeTo", getDeviceTypeRemark());
                    map.put("area", getRegionCode(map.get("receiveUnit").toString()));
                    map.put("dept", getDeptCodeByName(map.get("receiveDept") + ""));
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
            // 记录错误信息
            if (StringUtil.isNotBlank(execpStr)) {
                listA.add(map);
            }
            index++;
        }
        vo.setTotal(listA.size());
        vo.setRecords(listA);
    }

    /**
     * 存储异常信息
     */
    @Transactional
    public void saveImportInfo(String exceptionField, FileImportInfoParent parent, int index, int size) {
        parent = fileInfoParentService.getBaseMapper().selectById(parent.getId());
        parent.setAllNum(size);
        FileImportInfo fileImportInfo = new FileImportInfo();
        fileImportInfo.setFileName(parent.getFileName());
        fileImportInfo.setErrorInfo(exceptionField);
        fileImportInfo.setDataStatus(1);
        fileImportInfo.setParentId(parent.getId());
        fileImportInfo.setStatus(StringUtil.isNotBlank(exceptionField) ? 0 : 1);
        fileImportInfo.setRowNum(index);
        fileImportInfo.setCreateTime(new Date());
        fileImportInfoService.getBaseMapper().insert(fileImportInfo);
        parent.setYesNum(index);
        if (StringUtil.isNotBlank(exceptionField)) {
            parent.setErrorNum(parent.getErrorNum() + 1);
        }
        parent.setNoNum(parent.getAllNum() - parent.getYesNum());
        fileInfoParentService.getBaseMapper().updateById(parent);
    }

    /**
     * 数据库 获取类型对应是否需要检查IP MAC 网络类型
     *
     * @param deviceTypeCode
     * @return
     */
    private boolean getNetFlagByDeviceTypeCode(String deviceTypeCode) {
        boolean netFlag = false;
        if (StringUtil.isNotBlank(deviceTypeCode)) {
            String flgs = handlerDeviceMapper.selectNetFlagByDeviceType(deviceTypeCode);
            if ("1".equals(flgs)) {
                return true;
            }
        }
        return netFlag;
    }

    /**
     * 辅助设备
     */
    private void checkT106CharacteristicAttribute(Map<String, Object> map) {
        // 布线节点数
//		deviceDateHandler(map, "deviceWiringNodeNoHandler");
        // UPS容量
//		deviceDateHandler(map, "deviceUpsCapacityHandler");
        // PDU额定功率
//		deviceDateHandler(map, "devicePduRatedPowerHandler");
        // PDU运行功率
//		deviceDateHandler(map, "devicePduOperatePowerHandler");
        // 电源负载
//		deviceDateHandler(map, "devicePowerLoadHandler");
        // 所属机房
        deviceDateHandler(map, "deviceComputerRoomHandler");
        // 机柜
        deviceDateHandler(map, "deviceCabinetHandler");
    }

    /**
     * 存储设备
     */
    private void checkT102CharacteristicAttribute(Map<String, Object> map) {
        // 所属机房
        deviceDateHandler(map, "deviceComputerRoomHandler");
        // 机柜
        deviceDateHandler(map, "deviceCabinetHandler");
        // 设备起始高度
        deviceDateHandler(map, "deviceHeightBeginHandler");
        // 设备高度
//		deviceDateHandler(map, "deviceHeightHandler");
        // 所属安全边界
        deviceDateHandler(map, "deviceSecurityBoundaryHandler");
        // 是否纳入云管
        deviceDateHandler(map, "deviceIsCloudMangeHandler");
        // 设备使用部门
        deviceDateHandler(map, "deviceUseDeptHandler");
    }


    /**
     * 网络设备
     */
    private void checkT103CharacteristicAttribute(Map<String, Object> map) {
        // 网络设备用途类型
        deviceDateHandler(map, "deviceNetworkDeviceTypeHandler");
        // 所属机房
        deviceDateHandler(map, "deviceComputerRoomHandler");
        // 机柜
        deviceDateHandler(map, "deviceCabinetHandler");
        // 设备起始高度
        deviceDateHandler(map, "deviceHeightBeginHandler");
        // 设备高度
        deviceDateHandler(map, "deviceHeightHandler");
        // 主备属性
        deviceDateHandler(map, "deviceStandbyAttrHandler");
        // 所属安全边界
        deviceDateHandler(map, "deviceSecurityBoundaryHandler");
        // 出厂序列号
//		deviceDateHandler(map, "deviceSNRequiredHandler");
    }

    /**
     * 终端设备
     */
    private void checkT105CharacteristicAttribute(Map<String, Object> map) {
        // 使用人
        deviceDateHandler(map, "deviceMiChargeUserHandler");
        // 使用人身份证
        deviceDateHandler(map, "deviceMiChargeUserIdCardHandler");
        // 工厂区域
//		deviceDateHandler(map, "deviceFactoryAreaRequiredT105Handler");
        // 在运 领用日期
        deviceDateHandler(map, "deviceReceivingDateHandler");
    }

    /**
     * 主机设备
     */
    private void checkT101CharacteristicAttribute(Map<String, Object> map) {
        // 所属机房
        deviceDateHandler(map, "deviceComputerRoomHandler");
        // 机柜
        deviceDateHandler(map, "deviceCabinetHandler");
        // 操作系统位数
        deviceDateHandler(map, "deviceSystemNumHandler");
        // 设备起始高度
        deviceDateHandler(map, "deviceHeightBeginHandler");
        // 设备高度
        deviceDateHandler(map, "deviceHeightHandler");
        // 主机设备用途类型
        deviceDateHandler(map, "deviceUseTypeHandler");
        // 所属安全边界
        deviceDateHandler(map, "deviceSecurityBoundaryHandler");
        // 出厂序列号
//		deviceDateHandler(map, "deviceSNRequiredHandler");
        // 操作系统版本
        deviceDateHandler(map, "deviceOSVersionHandler");
    }

    /**
     * 安全设备
     */
    private void checkT104CharacteristicAttribute(Map<String, Object> map) {
        // 所属机房
        deviceDateHandler(map, "deviceComputerRoomHandler");
        // 所属安全边界
        deviceDateHandler(map, "deviceSecurityBoundaryHandler");
        // 机柜
        deviceDateHandler(map, "deviceCabinetHandler");
        // 设备起始高度
        deviceDateHandler(map, "deviceHeightBeginHandler");
        // 设备高度
//		deviceDateHandler(map, "deviceHeightHandler");
        // 终止高度
//		deviceDateHandler(map, "deviceTerHeightHandler");
        // 出厂序列号
//		deviceDateHandler(map, "deviceSNRequiredHandler");
    }

    /**
     * 办公设备
     */
    private void checkT107CharacteristicAttribute(Map<String, Object> map) {
        log.info("办公设备");
    }

    /**
     * 基础设施
     */
    private void checkT109CharacteristicAttribute(Map<String, Object> map) {
        // 空调类型
        deviceDateHandler(map, "deviceAirConditionTypeHandler");
        // 匹数
        deviceDateHandler(map, "deviceAirHorsepowerHandler");
        // 制冷量
        deviceDateHandler(map, "deviceAirCoolCapacityHandler");
        // UPS容量
        deviceDateHandler(map, "deviceUpsCapacityHandler");
        // 电池数
        deviceDateHandler(map, "deviceBatteryNumHandler");
        // 电池组数
        deviceDateHandler(map, "deviceBatteryPackNumHandler");
        // 电源负载
        deviceDateHandler(map, "devicePowerLoadHandler");
        // 额定容量
        deviceDateHandler(map, "deviceRatedCapacityHandler");
        // 所属机房
        deviceDateHandler(map, "deviceComputerRoomHandler");
        // 所属UPS
        deviceDateHandler(map, "deviceBelongUpsHandler");
        //在运 电压等级
        deviceDateHandler(map, "deviceVoltageLevelHandler");
    }


    /**
     * 通用校验方法
     *
     * @param map
     * @param ruleCode
     */
    private void deviceDateHandler(Map<String, Object> map, String ruleCode) {
        //map.put("makerCid",ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker"));
        /** 校验方法 * */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, ruleCode);
        //保存校验 结果
        saveErrorMsgToMap(result, map);
    }

    private void deviceDateHandler1(Map<String, Object> map, String ruleCode, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        //map.put("makerCid",ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker"));
        /** 校验方法 * */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, ruleCode);
        //保存校验 结果
        saveErrorMsgToMap1(result, map, errAddr, fieldColumn, rowIndex);
    }


    @Override
    public HardwareBasicCmdbDeviceVO importByExcel(MultipartFile file, String deviceCategory, String isMath) {

        if (StringUtil.isBlank(deviceCategory)) {
            //如果设备分类编码为空，则为非数字化入网设备导入
            return null;
        }
        Class<T> cls = getClassByClassifNew(deviceCategory, isMath);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Integer> fieldColumn = new HashMap<>(32);
        List<String> errAddr = new ArrayList<>();
        //返解析数据
        HardwareBasicCmdbDeviceVO vo = new HardwareBasicCmdbDeviceVO();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //解析数据文件
            EasyExcel.read(inputStream, cls, new HardBasicCmdbDeviceListener1(list, fieldColumn, errAddr))
                    .headRowNumber(4).excelType(ExcelTypeEnum.XLSX)
                    .sheet(getTypeNameByDeviceCategoryNew(deviceCategory, isMath)).doRead();
            vo.setErrAddr(errAddr);
            //获取异常信息
            fillErrorMessage1(list, vo, cls, deviceCategory, fieldColumn);
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("excel导入文件读取异常:" + e.getMessage());
        } catch (ExcelDataConvertException e) {
            throw new ServiceException("excel导入文件解析异常:");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败");
                }
            }
        }
        return vo;
    }

    @Override
    public String customSaveBatch(HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO) {
        //获取设备分类
        String deviceCategory = hardwareBasicCmdbDeviceVO.getDeviceCategory();
        StringBuffer exceptionField = new StringBuffer();
        //获取设备数据保存
        List<Map<String, Object>> list = hardwareBasicCmdbDeviceVO.getRecords();
        //循环保存 cmdb接口
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map cmdb = list.get(i);
                //判断类型一致
                String deviceCategoryCode = String.valueOf(cmdb.get("deviceCategoryCode")).replace("null", "").trim();
                if (cmdb != null && deviceCategoryCode.equals(deviceCategory)) {
                    //判断删除 修改 新增标志
                    String dataOpt = String.valueOf(cmdb.get("dataOptType")).replace("null", "").trim();
                    if ("删除".equals(dataOpt)) {
                        long id = Long.parseLong(String.valueOf(cmdb.get("id")));
                        //删除接口
                        cmdbService.cientityDelete(id, "数据治理用户通过模板删除");
                    } else {
                        //修改 新增接口
                        exceptionField.append(customSaveBatch(cmdb));
                    }
                }
            }
        }
        return exceptionField.toString();
    }

    /**
     * 模型数据保存cmdb接口
     *
     * @param cmdb
     */
    private String customSaveBatch(Map<String, Object> cmdb) {
        //设备类型 设备分类编码
        String deviceCode = String.valueOf(cmdb.get("deviceCode")).replace("null", "").trim();
        String deviceTypeCode = String.valueOf(cmdb.get("deviceTypeCode")).replace("null", "");
        if (StringUtil.isNotBlank(deviceCode)) {
            //设备cmdb数据修改  新增，接口根据设备编号，在对应模型里边查询设备编码数据，如果为空新增数据 如果存在修改
            CmdbStockDTO cmdbStockDTO = new CmdbStockDTO();
            cmdbStockDTO.setDeviceCode(deviceCode);
            cmdbStockDTO.setDeviceTypeCode(deviceTypeCode);
            // 填充字典转化数据
            fillDictData(cmdb);
            cmdbStockDTO.setStockMap(cmdb);
            try {
                Map result = this.cientityBatchupdateStock(cmdbStockDTO);
                log.error("--------设备：{} ==> 同步结果：{}", deviceCode, result);
            } catch (Exception e) {
                log.error("修改CMDB异常:{}", e);
                throw new ServiceException("修改CMDB异常:" + e.getMessage());
            }
        } else {
            //设备cmdb数据修改  新增，接口根据设备编号，在对应模型里边查询设备编码数据，如果为空新增数据 如果存在修改
            CmdbStockDTO cmdbStockDTO = new CmdbStockDTO();
            deviceCode = orderNumberUtil.generateCode(deviceTypeCode);
            cmdb.put("deviceCode", deviceCode);
            cmdbStockDTO.setDeviceCode(deviceCode);
            cmdbStockDTO.setDeviceTypeCode(deviceTypeCode);
            // 填充字典转化数据
            fillDictData(cmdb);
            cmdbStockDTO.setStockMap(cmdb);
            try {
                Map result = this.cientityBatchupdateStock(cmdbStockDTO);
                log.error("--------设备：{} ==> 新增结果：{}", deviceCode, result);
            } catch (Exception e) {
                log.error("新增CMDB异常:{}", e);
                throw new ServiceException("新增CMDB异常:" + e.getMessage());
            }
        }
        return "";
    }

    private void fillDictData(Map<String, Object> cmdb) {
        String area = String.valueOf(cmdb.get("area")).replace("null", "");
        IdevelopUser user = SecureUtil.getUser();
        if (StringUtil.isBlank(area)) {
            cmdb.put("area", user.getRegionCode());
        }
        String dept = String.valueOf(cmdb.get("dept")).replace("null", "");
        if (StringUtil.isBlank(dept)) {
            cmdb.put("dept", user.getDeptId());
        }
        // 完成治理
        cmdb.put(IS_GOVERN, cmdbCientityProperties.getCientityId(CmdbCientityConstant.GOVERN_YES));
        // 治理时间
        cmdb.put(GOVERN_TIME, LocalDate.now());
        // 网络设备用途类型
        String networkDeviceTypeCode = String.valueOf(cmdb.get("networkDeviceTypeCode")).replace("null", "");
        if (StringUtil.isNotBlank(networkDeviceTypeCode)) {
            cmdb.put(NETWORK_DEVICE_TYPE, networkDeviceTypeCode);
        }
        // 所属安全边界
        String securityBoundaryCode = String.valueOf(cmdb.get("securityBoundaryCode")).replace("null", "");
        if (StringUtil.isNotBlank(securityBoundaryCode)) {
            cmdb.put(SECURITY_BOUNDARY, securityBoundaryCode);
        }
        // 主机设备用途类型
        String serverUseToTypeCode = String.valueOf(cmdb.get("serverUseToTypeCode")).replace("null", "");
        if (StringUtil.isNotBlank(serverUseToTypeCode)) {
            cmdb.put(SERVER_USE_TO_TYPE, serverUseToTypeCode);
        }
        // 空调类型
        String airConditionTypeCode = String.valueOf(cmdb.get("airConditionTypeCode")).replace("null", "");
        if (StringUtil.isNotBlank(airConditionTypeCode)) {
            cmdb.put(AIR_CONDITION_TYPE, airConditionTypeCode);
        }
    }

    private void fillErrorMessage1(List<Map<String, Object>> list, HardwareBasicCmdbDeviceVO vo, Class<T> cls,
                                   String deviceCategory, Map<String, Integer> fieldColumn) throws InterruptedException {
        List<Map<String, Object>> listA = new ArrayList<>();
        List<Map<String, Object>> synchronizedList = Collections.synchronizedList(listA);
        List<String> errAddr = vo.getErrAddr();
        cls = getVOClassByClassif(deviceCategory);
        IdevelopUser user = SecureUtil.getUser();

        // 校验文件中ip、mac是否重复
        checkFileIPMAC(list);

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
            threadPoolExecutor.submit(() -> {
                try {
                    Map<String, Object> map = list.get(finalI);
                    // 填充信息
                    map.put("regionCode", user.getRegionCode());
                    // map.put("corpId", user.getCorpId());
                    String dataOptType = String.valueOf(map.get("dataOptType")).replace("null", "");
                    // 设备分类 名称+code
                    converDictValue(String.valueOf(map.get("deviceCategory")).replace("null", ""), "deviceCategory", "deviceCategoryCode", map, finalCls);
                    // 设备类型 名称+code
                    converDictValue(String.valueOf(map.get("deviceType")).replace("null", ""), "deviceType", "deviceTypeCode", map, finalCls);

                    if (StringUtils.isNotEmpty(dataOptType) && !StringUtils.equalsAny(dataOptType, "新增", "修改", "删除")) {
                        map.put("exceptionField", "操作类型不正确! 请重新确认!");
                    } else {
                        if (StringUtils.equals("删除", dataOptType)) {
                            // 判断操作类型为删除时 有没有ID
                            String id = String.valueOf(map.get("id")).replace("null", "");
                            if (StringUtil.isBlank(id.trim())) {
                                map.put("exceptionField", "数据标识为删除时，id不能为空! ");
                            }
                            map.put("exceptionField", "");
                        } else {
                            String isAccessEquipment = String.valueOf(map.get("isAccessEquipment")).replace("null", "");

                            // 判断操作类型修改时 有没有ID
                            checkFlagBit(map, dataOptType, errAddr, fieldColumn, finalI);

                            // 填充单位和部门
                            fillUnitCode(map, user, isAccessEquipment);
                            // 填充 品牌、系列、型号编码
                            fillBrandSeriesModel(map);
                            // excel字段处理 名称转编码 编码转名称
                            converDictValues(map, finalCls);
                            //增加时间格式校验 2024-11-29
                            dateCheck(map, deviceCategory, finalI, vo, fieldColumn, errAddr);

                            if (StringUtils.equals("是", isAccessEquipment)) {
                                // 非数字化入网设备
                                cmdbDataCheckAccessEquipment(map, deviceCategory, finalI, vo, fieldColumn, errAddr, user);
                            } else {
                                // 数字化设备
                                cmdbDataCheck1(map, deviceCategory, finalI, vo, fieldColumn, errAddr, user);
                            }

                            // 填充设备编码
                            fullDeviceCode(map, user);
                            String trim = String.valueOf(map.get("exceptionField")).replace("null", "").trim();
                            map.put("exceptionField", trim);
                        }
                    }
                    synchronizedList.add(map);
                } catch (Exception e) {
                    log.error("数据治理-数据校验时出现异常：{}", e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        long time = System.currentTimeMillis() - startTime;
        log.error("执行总时长:{}毫秒", time);
        countDownLatch.await();
        threadPoolExecutor.shutdown();
        vo.setTotal(synchronizedList.size());
        List<Map<String, Object>> result = synchronizedList.stream()
                .sorted(Comparator.comparingInt(map -> (Integer) map.get("index")))
                .collect(Collectors.toList());
        vo.setRecords(result);
    }

    private void dateCheck(Map<String, Object> map, String deviceCategory, int rowIndex,
                           HardwareBasicCmdbDeviceVO vo, Map<String, Integer> fieldColumn, List<String> errAddr) {
        if (ObjectUtil.isNotEmpty(map.get("factoryDate"))) {
            String factoryDate = String.valueOf(map.get("factoryDate"));
            if (!isValidDate(factoryDate)) {
                errMsgAppend("出厂日期:" + factoryDate + " 格式不正确！", "factoryDate", map, errAddr, fieldColumn, rowIndex);
            }
        }
        if (ObjectUtil.isNotEmpty(map.get("oprtDate"))) {
            String oprtDate = String.valueOf(map.get("oprtDate"));
            if (!isValidDate(oprtDate)) {
                errMsgAppend("投运日期:" + oprtDate + " 格式不正确！", "oprtDate", map, errAddr, fieldColumn, rowIndex);
            }
        }
        if (ObjectUtil.isNotEmpty(map.get("afterSaleExpDate"))) {
            String afterSaleExpDate = String.valueOf(map.get("afterSaleExpDate"));
            if (!isValidDate(afterSaleExpDate)) {
                errMsgAppend("服务到期时间:" + afterSaleExpDate + " 格式不正确！", "afterSaleExpDate", map, errAddr, fieldColumn, rowIndex);
            }
        }
        if (ObjectUtil.isNotEmpty(map.get("retireDate"))) {
            String retireDate = String.valueOf(map.get("retireDate"));
            if (!isValidDate(retireDate)) {
                errMsgAppend("退运日期:" + retireDate + " 格式不正确！", "retireDate", map, errAddr, fieldColumn, rowIndex);
            }
        }
        if (ObjectUtil.isNotEmpty(map.get("serviceExpDate"))) {
            String serviceExpDate = String.valueOf(map.get("serviceExpDate"));
            if (!isValidDate(serviceExpDate)) {
                errMsgAppend("服务到期日期:" + serviceExpDate + " 格式不正确！", "retireDate", map, errAddr, fieldColumn, rowIndex);
            }
        }

    }

    private Boolean isValidDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            Date date = format.parse(dateStr);
            boolean result = dateStr.equals(format.format(date));
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    private void cmdbDataCheckAccessEquipment(Map<String, Object> map, String deviceCategory, int rowIndex,
                                              HardwareBasicCmdbDeviceVO vo, Map<String, Integer> fieldColumn, List<String> errAddr, IdevelopUser user) {

        // 【非数字化入网设备】：1.逻辑校验: 选择是时,只需录入设备分类/设备类型/IP地址/MAC地址/责任人/责任人联系方式/产权部门；
        // 判断前端传递阐述与获取的 设备分类一致
        if (StringUtil.isNotBlank(deviceCategory)) {
            checkdeviceCategoryStand1(map, deviceCategory, errAddr, fieldColumn, rowIndex);
        }

        // 设备来源为(非统一纳管)/ERP资产编码应该为空
        String deviceSource = String.valueOf(map.get("deviceSource")).replace("null", "");
        String assetCodeErp = String.valueOf(map.get("assetCodeErp")).replace("null", "");
        //设备来源默认为非统一纳管
        map.put("deviceSource", "非统一纳管");
        map.put("deviceSourceCode", cmdbCientityProperties.getNoDeviceSource());
        //判断品牌系列型号一致
        checkBrandSeriesModel(map, errAddr, fieldColumn, rowIndex);
        if (StringUtils.equals("统一纳管", deviceSource) && StringUtils.isNotEmpty(assetCodeErp)) {
            checkAndFillErpAccountCode(map, errAddr, fieldColumn, rowIndex, user);
        } else {
            if (StringUtils.equals("统一纳管", deviceSource)) {
                errMsgAppend("非数字化入网设备, 设备来源应为非统一纳管！", "deviceSource", map, errAddr, fieldColumn, rowIndex);
            }

            if (StringUtils.isNotEmpty(assetCodeErp)) {
                errMsgAppend("非数字化入网设备, ERP资产编码应该为空！", "assetCodeErp", map, errAddr, fieldColumn, rowIndex);
            }
        }

        // 获取 数据库 类型对应是否需要检查IP MAC 网络类型
        String deviceTypeCode = String.valueOf(map.get("deviceTypeCode")).replace("null", "");
        boolean netFlag = getNetFlagByDeviceTypeCode(deviceTypeCode);

        // 设备状态应为在运
//		String deviceStatus = String.valueOf(map.get("deviceStatus")).replace("null", "");
//		if (!StringUtils.equals("在运", deviceStatus)) {
//			errMsgAppend("非数字化入网设备,设备状态应为在运！", "deviceStatus", map, errAddr, fieldColumn, rowIndex);
//		}
        // 设备状态默认在运
        map.put("deviceStatus", "在运");
        map.put("deviceStatusCode", cmdbCientityProperties.getInOperation());
        //在运 责任人必填
//		deviceDateHandler1(map, "deviceMiUserRequiredHandler", errAddr, fieldColumn, rowIndex);
        //在运 责任人联系方式
//		deviceDateHandler1(map, "deviceMiOmPhoneRequiredHandler", errAddr, fieldColumn, rowIndex);
        //领用单位领用部门默认为产权单位产权部门
        map.put("receiveUnit", map.get("ownerUnit"));
        map.put("receiveUnitCode", map.get("ownerUnitCode"));
        map.put("receiveDept", map.get("propertyDept"));
        map.put("receiveDeptCode", map.get("propertyDeptCode"));
        // 辅助设备和基础设施没有 IP和MAC
        List<String> deviceCategoryList = Arrays.asList(cmdbCientityProperties.getT106(), cmdbCientityProperties.getT109());
        if (netFlag && !deviceCategoryList.contains(deviceCategory)) {
            //在运设备  在运、停运设备IP缺失规则校验任务 基础设施不含IP
            deviceDateHandler1(map, "deviceIPLessHandler", errAddr, fieldColumn, rowIndex);
            //在运 所属网络 不能为空，符合规范
            deviceDateHandler1(map, "deviceNetworkRequiredHandler", errAddr, fieldColumn, rowIndex);
            //在运设备mac不能为空，符合规范
            deviceDateHandler1(map, "deviceAppMacHandler", errAddr, fieldColumn, rowIndex);
        }

    }

    private void fullDeviceCode(Map<String, Object> map, IdevelopUser user) {
        try {
            //新数据补全 数据  当 设备编码为空时
            String deviceCode = String.valueOf(map.get("deviceCode")).replace("null", "");
            if (StringUtil.isBlank(deviceCode.trim())) {
                // 获取设备类型 type -> Txxxx  区域 部门
                map.put("deviceTypeTo", getDeviceTypeRemark());
                map.put("area", user.getRegionCode());
                map.put("dept", user.getDeptId());
            }
        } catch (Exception e) {
            log.error("新数据补全出现异常:{}", e.getLocalizedMessage());
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
            converDictValue(String.valueOf(map.get("standbyAttr")).replace("null", ""), "standbyAttr", "standbyAttr", map, finalCls);
            // 操作系统发行版本
            converDictValue(String.valueOf(map.get("OSIssueVersion")).replace("null", ""), "OSIssueVersion", "OSIssueVersion", map, finalCls);
            // 云类型
            converDictValue(String.valueOf(map.get("cloudType")).replace("null", ""), "cloudType", "cloudType", map, finalCls);
        } catch (Exception e) {
            log.error("excel字段处理 名称转编码 编码转名称出现异常:{}", e.getLocalizedMessage());
        }
    }

    /**
     * 处理单位和部门
     *
     * @param map
     * @param user
     */
    private void fillUnitCode(Map<String, Object> map, IdevelopUser user, String isAccessEquipment) {
        String fullName = user.getExt().get("corpFullName").toString();
        // 产权单位
        map.put("ownerUnit", fullName);

        map.put("ownerUnitCode", user.getCorpId());
        // 产权部门
        String ownerUnitCode = String.valueOf(map.get("ownerUnitCode")).replace("null", "");
        String propertyDept = String.valueOf(map.get("propertyDept")).replace("null", "");
        if (StringUtil.isNotBlank(propertyDept) || StringUtils.equals("是", isAccessEquipment)) {
            String propertyDeptCode = getDeptCodeByNameAndPid(propertyDept, ownerUnitCode);
            map.put("propertyDept", propertyDept);
            map.put("propertyDeptCode", propertyDeptCode);
            if (StringUtil.isBlank(propertyDeptCode) || StringUtil.isBlank(propertyDept)) {
                exceptionFieldAppend("产权部门在当前登录用户产权单位下未找到！", map);
            }
        } else {
            map.put("propertyDept", user.getDeptName());
            map.put("propertyDeptCode", user.getDeptId());
        }

        // 运维单位
        map.put("operationUnit", fullName);
        map.put("operationUnitCode", user.getCorpId());
        // 运维部门
        map.put("operationDept", user.getDeptName());
        map.put("operationDepCode", user.getDeptId());
    }


    // 填充各种code
    private void fillCode(String deviceCategory, Map<String, Object> map, Class<T> finalCls, IdevelopUser user) {
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            // 填充运维&产权单位&产权部门的code
            map.put("operationUnitCode", user.getCorpId());
            map.put("ownerUnitCode", user.getCorpId());
            map.put("propertyDeptCode", user.getDeptId());
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            converDictValue(String.valueOf(map.get("OSType")).replace("null", ""),
                    "OSType",
                    "OSTypeCode",
                    map,
                    finalCls);
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            converDictValue(String.valueOf(map.get("OSType")).replace("null", ""),
                    "OSType",
                    "OSTypeCode",
                    map,
                    finalCls);
        }
    }

    private void checkFileIPMAC(List<Map<String, Object>> list) {
        // 校验文件中ip、mac重复
        List<Object> ipList = list.stream()
                .filter(item -> ObjectUtil.isNotEmpty(item.get("IP")))
                .map(item -> item.get("IP"))
                .collect(Collectors.toList());
        List<Object> macList = list.stream()
                .filter(item -> ObjectUtil.isNotEmpty(item.get("MAC")))
                .map(item -> item.get("MAC"))
                .collect(Collectors.toList());
        Set<Object> ipSet = new HashSet<>();
        Set<Object> macSet = new HashSet<>();
        List<Object> ipErrList = Lists.newArrayList();
        List<Object> macErrList = Lists.newArrayList();
        for (Object ip : ipList) {
            if (!ipSet.add(ip)) {
                ipErrList.add(ip);
            }
        }
        for (Object mac : macList) {
            if (!macSet.add(mac)) {
                macErrList.add(mac);
            }
        }
        StringBuilder massage = new StringBuilder();
        if (CollectionUtil.isNotEmpty(ipErrList)) {
            massage.append(ipErrList);
        }
        if (CollectionUtil.isNotEmpty(macErrList)) {
            massage.append(macErrList);
        }
        if (massage.length() > 0) {
            throw new ServiceException("当前文件数据中IP或MAC重复：" + massage);
        }
    }

    private Boolean fillMakerBrandSeriesModel(Map<String, Object> map) {

        map.put("brandCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("brand"));
        map.put("seriesCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("series"));
        map.put("deviceModelCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("model"));
        map.put("makerCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker"));
        // 制造商
        Boolean makerBL = excuteData(cmdbDictProperties.getMaker(), "maker", map);
        // 品牌
        Boolean brandBL = excuteData(cmdbDictProperties.getBrand(), "brand", map);
        // 系列
        Boolean seriesBL = excuteData(cmdbDictProperties.getSeries(), "series", map);
        // 型号
        Boolean modelBL = excuteData(cmdbDictProperties.getModel(), "deviceModel", map);

        if (makerBL && brandBL && seriesBL && modelBL) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean excuteData(Long ciId, String name, Map<String, Object> map) {

        String replace = String.valueOf(map.get(name)).replace("null", "");

        if (!StringUtils.containsAny(replace, "→", "←")) {
            return Boolean.TRUE;
        }
        FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
        String str = replace.replace("→", "").replace("_", "-");
        String[] strs = str.split("←");

        String dictKey = strs.length > 1 ? strs[1] : "";
        String dictValue = strs.length > 1 ? strs[0] : "";

        List<Map<String, Object>> dict = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + ciId);
        List<Map<String, Object>> result = dict.stream().filter(item -> StringUtils.equals(item.get("remarkTemp").toString(), dictKey) &&
                StringUtils.equals(item.get("dictValue").toString(), dictValue)).collect(Collectors.toList());

        List<Map<String, Object>> list = result != null ? result : null;
        String code = CollectionUtil.isEmpty(list) ? "" : String.valueOf(list.get(0).get("dictKey"));
        map.put(name + "Code", code);
        if (StringUtils.equals("deviceModel", name)) {
            String value = CollectionUtil.isEmpty(list) ? "" : String.valueOf(list.get(0).get("dictValue"));
            map.put(name, value);
        } else {
            map.put(name, strs.length > 1 ? strs[0] : "");
        }

        return Boolean.FALSE;
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
     * 判断前端传递阐述与获取的 设备分类一致asdfa
     */
    private void checkdeviceCategoryStand(Map<String, Object> cmdb, String deviceCategory) {
        //
        String deviceCategoryCode = cmdb.get("deviceCategoryCode").toString().replace("null", "").trim();
        if (!deviceCategoryCode.equals(deviceCategory)) {
            exceptionFieldAppend("数据设备分类与选择操作的设备分类不一致! ", cmdb);
        }
    }

    private void checkdeviceCategoryStand1(Map<String, Object> cmdb, String deviceCategory,
                                           List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        String deviceCategoryCode = cmdb.get("deviceCategoryCode").toString().replace("null", "").trim();
        if (!deviceCategoryCode.equals(deviceCategory)) {
            errMsgAppend("所选左侧设备分类与模板中设备分类不匹配,请重新选择! ", "deviceCategory", cmdb, errAddr, fieldColumn, rowIndex);
        }
    }

    private void errAddrAppend(String field, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        // 列数
        Integer columnIndex = fieldColumn.get(field);
        if (columnIndex != null) {
            String excelColumn = EasyExcelUtil.convertToExcelColumn(columnIndex + 1);
            // 生成类似B2的excel坐标
            errAddr.add(excelColumn + (rowIndex + 5));
        }
    }

    /**
     * 根据标识判断
     *
     * @param map
     * @param tag 0 add,1 update, 2 delete
     */
    private void checkTag(Map<String, Object> map, String tag) {
        StringBuilder exceptionField = new StringBuilder();
        if (StringUtil.isBlank(String.valueOf(map.get("deviceCode")).replace("null", "").trim())) {
            if ("修改".equals(tag) || "删除".equals(tag)) {
                exceptionField.append("数据标识为修改或删除时，设备编码不能为空! ");
            }
        }
        //设置异常信息
        exceptionFieldAppend(exceptionField.toString(), map);
    }

    private void checkFlagBit(Map<String, Object> map, String tag, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        // 修改时，校验设备是否存在
        if ("修改".equals(tag)) {
            String id = String.valueOf(map.get("id")).replace("null", "");
            String deviceCode = String.valueOf(map.get("deviceCode")).replace("null", "");
            if (StringUtil.isNotBlank(id) && StringUtil.isNotBlank(deviceCode)) {
                CiCientitySearch search = new CiCientitySearch();
                search.setFilterCiEntityId(Long.valueOf(id));
                // 查询设备编码
                CiCientitySearchVO ciCientitySearchVO1 = new CiCientitySearchVO();
                ciCientitySearchVO1.setExpression(Expression.EQUAL);
                ciCientitySearchVO1.setAttrValue(deviceCode);
                ciCientitySearchVO1.setAttrName("deviceCode");
                List<CiCientitySearchVO> list = new ArrayList<>();
                list.add(ciCientitySearchVO1);
                search.setEntity(list);

                Query query = new Query();
                query.setCurrent(1);
                query.setSize(1);
                search.setQuery(query);
                FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(search);
                List<Map<String, Object>> data = feignCiCientity.getData();
                if (CollectionUtil.isEmpty(data)) {
                    errMsgAppend("数据标识为修改时,无法获取设备信息,请检查id或设备编码是否正确! ", "id", map, errAddr, fieldColumn, rowIndex);
                }
            } else {
                errMsgAppend("数据标识为修改时,id和设备编码不能为空! ", "id", map, errAddr, fieldColumn, rowIndex);
            }

        }
    }


    /**
     * 追加错误提示信息
     */
    private void exceptionFieldAppend(String exceptionField, Map<String, Object> map) {
        //设置异常信息
        map.put("exceptionField", String.valueOf(map.get("exceptionField")).replace("null", "") + exceptionField + " ");
    }

    /**
     * 判断领用单位 领用部门合法
     */
    private void checkDeviceUnitHandler(Map<String, Object> map) {
        try {
            //设备状态
            String deviceStatus = String.valueOf(map.get("deviceStatus")).replace("null", "");
            if ("在运".equals(deviceStatus)) {
                //获取部门 单位编码
                String receiveUnit = String.valueOf(map.get("receiveUnit")).replace("null", "");
                String receiveDept = String.valueOf(map.get("receiveDept")).replace("null", "");
                if (StringUtil.isNotBlank(receiveUnit)) {
                    String receiveUnitCode = getDeptCodeByName(receiveUnit);
                    if (StringUtil.isNotBlank(receiveUnitCode)) {
                        map.put("receiveUnitCode", receiveUnitCode);
                        map.put("receiveDeptCode", getDeptCodeByNameAndPid(receiveDept, receiveUnitCode));
                    }
                }
                if (StringUtil.isBlank(receiveDept) || StringUtil.isBlank(receiveUnit)) {
                    exceptionFieldAppend("在运设备领用单位、领用部门不能为空！", map);
                }
                String receiveUnitCode = String.valueOf(map.get("receiveUnitCode")).replace("null", "");
                String receiveDeptCode = String.valueOf(map.get("receiveDeptCode")).replace("null", "");
                if (StringUtil.isNotBlank(receiveUnit) && StringUtil.isBlank(receiveUnitCode)) {
                    exceptionFieldAppend("领用单位在系统内未找到！", map);
                }
                if (StringUtil.isNotBlank(receiveDept) && StringUtil.isBlank(receiveDeptCode)) {
                    exceptionFieldAppend("领用部门在系统内领用单位下未找到！", map);
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private void checkDeviceUnitHandler1(Map<String, Object> map, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        try {
            //设备状态
            String deviceStatus = String.valueOf(map.get("deviceStatus")).replace("null", "");
            //获取部门 单位编码
            String receiveUnit = String.valueOf(map.get("receiveUnit")).replace("null", "");
            String receiveDept = String.valueOf(map.get("receiveDept")).replace("null", "");
            if (StringUtil.isNotBlank(receiveUnit)) {
                String receiveUnitCode = getDeptCodeByName(receiveUnit);
                if (StringUtil.isNotBlank(receiveUnitCode)) {
                    map.put("receiveUnitCode", receiveUnitCode);
                    map.put("receiveDeptCode", getDeptCodeByNameAndPid(receiveDept, receiveUnitCode));
                }
            }
            String receiveUnitCode = String.valueOf(map.get("receiveUnitCode")).replace("null", "");
            String receiveDeptCode = String.valueOf(map.get("receiveDeptCode")).replace("null", "");
            if (StringUtil.isNotBlank(receiveUnit) && StringUtil.isBlank(receiveUnitCode)) {
                errMsgAppend("领用单位在系统内未找到！", "receiveUnit", map, errAddr, fieldColumn, rowIndex);
            }
            if (StringUtil.isNotBlank(receiveDept) && StringUtil.isBlank(receiveDeptCode)) {
                errMsgAppend("领用部门在系统内领用单位下未找到！", "receiveDept", map, errAddr, fieldColumn, rowIndex);
            }


            if ("在运".equals(deviceStatus)) {

                // 2024-6-20 新增内容校验
                String corpId = String.valueOf(map.get("corpId")).replace("null", "");
                if (!StringUtil.equals(receiveUnitCode, corpId) && StringUtil.isBlank(receiveUnitCode)) {
                    errMsgAppend("领用单位必须与登录账号所在单位保持一致！", "receiveUnit", map, errAddr, fieldColumn, rowIndex);
                }

                if (StringUtil.isBlank(receiveUnit)) {
                    errMsgAppend("在运设备领用单位不能为空！", "receiveUnit", map, errAddr, fieldColumn, rowIndex);
                }
                if (StringUtil.isBlank(receiveDept)) {
                    errMsgAppend("在运设备领用部门不能为空！", "receiveDept", map, errAddr, fieldColumn, rowIndex);
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * 判断设备来源 合法
     */
    private void checkDeviceSourceHandler(Map<String, Object> map) {
        /** 校验方法 设备来源为 统一纳管、 非同一纳管 规则校验; */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, "deviceSourceRequiredHandler");
        //保存校验 结果
        saveErrorMsgToMap(result, map);
//		StringBuilder exceptionField = new StringBuilder();
//		String deviceSource = String.valueOf(map.get("deviceSource")).replace("null", "");
//		//判断设备来源 统一纳管  非统一纳管
//		if (StringUtil.isBlank(deviceSource) ||  !("非统一纳管".equals(deviceSource) ||  "统一纳管".equals(deviceSource) ) ) {
//			exceptionField.append("设备来源异常，");
//		}
//		//设置异常信息
//		exceptionFieldAppend(exceptionField.toString(),map);
    }

    /**
     * 将调用fengin校验方法的返回值保存值map
     *
     * @param result
     * @param cmdb
     */
    private void saveErrorMsgToMap(R<Map<String, Object>> result, Map<String, Object> cmdb) {
        if (result != null) {
            Map<String, Object> stringObjectMap = result.getData();
            String msg = result.getMsg();
            int code = result.getCode();
            if (200 == code) {
                //正常返回
                List<Map<String, Object>> objectMap = (List<Map<String, Object>>) stringObjectMap.get("list");
                for (Map<String, Object> entry : objectMap) {
                    String error = (String) entry.get("warnInfo");
                    exceptionFieldAppend(error, cmdb);
                }
            } else {
                //参数异常 未完成校验
                exceptionFieldAppend(msg, cmdb);
            }
        }
    }

    private void saveErrorMsgToMap1(R<Map<String, Object>> result, Map<String, Object> cmdb, List<String> errAddr, Map<String, Integer> fieldColumn, int rowIndex) {
        if (result != null) {
            Map<String, Object> stringObjectMap = result.getData();
            String msg = result.getMsg();
            int code = result.getCode();
            if (200 == code) {
                //正常返回
                List<Map<String, Object>> objectMap = (List<Map<String, Object>>) stringObjectMap.get("list");
                for (Map<String, Object> entry : objectMap) {
                    String error = (String) entry.get("warnInfo");
                    exceptionFieldAppend(error, cmdb);

                    String colName = (String) entry.get("colName");
                    if (StringUtil.isNotBlank(colName)) {
                        errAddrAppend(colName, errAddr, fieldColumn, rowIndex);
                    }
                }
            } else {
                //参数异常 未完成校验
                exceptionFieldAppend(msg, cmdb);
            }
        }
    }

    /**
     * 判断设备类型 设备分类一致
     */
    private void checkDeviceCategoryTypeHandler(Map<String, Object> map) {
        //获取设备类型 字典cid
        map.put("deviceTypeCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("device-type"));
        map.put("deviceCategoryCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("device-claccify"));
        /** 校验方法 型字典 分类名称类型正确 分类 类型上下级正常 规则校验 */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, "deviceCategoryTypeRequiredHandler");
        //保存校验 结果
        saveErrorMsgToMap(result, map);
    }

    /**
     * 设备状态只能为在运/库存设备/退运设备/待报废/已报废的规则校验
     *
     * @param map
     */
    private void deviceUnitStatusRequiredHandler(Map<String, Object> map) {
        //校验工具类bean
        String ruleCode = "deviceUnitStatusRequiredHandler";
        /** 校验方法 * */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, ruleCode);
        //保存校验 结果
        saveErrorMsgToMap(result, map);
    }

    /**
     * 在运、停运设备IP缺失规则校验任务
     *
     * @param map
     * @param ruleCode
     */
    private void deviceIPLessHandler(Map<String, Object> map, String ruleCode) {


        //map.put("makerCid",ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker"));
        /** 校验方法 * */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, ruleCode);
        //保存校验 结果
        saveErrorMsgToMap(result, map);
    }

    //上下级不匹配校验
    private void checkBandSerMdHandler(Map<String, Object> map, String ruleCode) {

        /**  临时方法屏蔽，统一使用rule.run
         // 品牌
         checkType(map,"brand", (String) map.get("brand"), (String) map.get("makerCode"),"品牌上下级不匹配");
         // 系列
         checkType(map,"series", (String) map.get("series"), (String) map.get("brandCode"),"系列上下级不匹配");
         // 型号
         checkType(map,"model", (String) map.get("deviceModel"), (String) map.get("seriesCode"),"型号上下级不匹配");
         */
        map.put("makerCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker"));
        map.put("brandCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("brand"));
        map.put("seriesCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("series"));
        map.put("deviceModelCid", ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("model"));
        /** 校验方法 * */
        R<Map<String, Object>> result = HardwareBasicStockWrapper.build().feignDeviceAssetCheckByMap(map, ruleCode);
        //保存校验 结果
        saveErrorMsgToMap(result, map);
    }

    /**
     * i6000 数据补全
     *
     * @param map
     * @return
     */
    private List<Map<String, Object>> selectI6000ResultMap(Map<String, Object> map) {
        if (properties.getIsGovernProperty()) {
            // 查库找到ciType
            String code = handlerDeviceMapper.selectI6000ByDeviceCode(map.get("deviceTypeCode").toString());
            I6000CiCientityDTO dto = new I6000CiCientityDTO();

            List<I6000CiCientityDTO.Conditions> orConditionList = new ArrayList<>();

            // 请求I6000的参数问题.
            I6000CiCientityDTO.Conditions conditions1 = new I6000CiCientityDTO.Conditions();
            conditions1.setAttrCode("ERP_ASSET_NO");
            conditions1.setValue(map.get("assetCodeErp").toString());
            conditions1.setOperator("=");
            orConditionList.add(conditions1);

            I6000CiCientityDTO.Conditions conditions2 = new I6000CiCientityDTO.Conditions();
            conditions2.setOperator("=");
            conditions2.setAttrCode("OPDEP");
            String regionCode = SecureUtil.getUser().getRegionCode();
            R<List<Dept>> byRegionCode = deptClient.getByRegionCode(regionCode);
            String erpUnitCode = null;
            if (ObjectUtil.isNotEmpty(byRegionCode.getData())) {
                erpUnitCode = byRegionCode.getData().get(0).getErpUnitCode();
            }
            conditions2.setValue(erpUnitCode);
            orConditionList.add(conditions2);

            dto.setConditions(orConditionList);

            String attrCode = I6000AttrConstant.BEBER + "," +
                    I6000AttrConstant.BEBER_NAME_99 + "," +
                    I6000AttrConstant.OPDEP + "," +
                    I6000AttrConstant.OPDEP_NAME + "," +
                    I6000AttrConstant.MANAGE_DEPT + "," +
                    I6000AttrConstant.MANAGE_DEPT_NAME + "," +
                    I6000AttrConstant.KEEP_DEPT + "," +
                    I6000AttrConstant.KEEP_DEPT_NAME + "," +
                    I6000AttrConstant.FUN_SITE + "," +
                    I6000AttrConstant.FUN_SITE_NAME + "," +
                    I6000AttrConstant.ASSET_ADD + "," +
                    I6000AttrConstant.ASSET_CHANGE + "," +
                    I6000AttrConstant.HARDDISK_VOLUME_TB + "," +
                    I6000AttrConstant.MEMORY_SIZE_ALL + "," +
                    I6000AttrConstant.CPU_ARCHITEC + "," +
                    I6000AttrConstant.CPU_BRAND + "," +
                    I6000AttrConstant.CPU_FRQUENCY + "," +
                    I6000AttrConstant.WBS_NAME + "," +
                    I6000AttrConstant.WBS + "," +
                    I6000AttrConstant.CI_NAME + "," +
                    I6000AttrConstant.SRV_COMPANY + "," +
                    I6000AttrConstant.SRV_COMPANY_NAME;
            dto.setAttrCode(attrCode);
            dto.setPageStart("1");
            dto.setPageSize("1");
            return i6000Service.selectCiCientity(code, dto);
        }
        return new ArrayList<>();
    }

    /**
     * 字段转换名称
     */
    private void potting(String var1, String fieidCode, Map<String, Object> map, Class<T> cls) {
        if (StringUtil.isNotBlank(var1)) {
            String code = getCmdbValByColName(var1, fieidCode, cls);
            map.put(fieidCode, code);
        }
    }

    private void converDictValue(String var1, String sourceStr, String fieidCode, Map<String, Object> map, Class<T> cls) {
        if (StringUtil.isNotBlank(var1)) {
            String code = getCmdbValByColName(var1, sourceStr, cls);
            map.put(fieidCode, code);
        }
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
            if (StringUtil.isBlank(hardwareBasicQueryVO.getDeviceCategoryCode())) {
                jsonObject = cmdbService.getCiCientityList(ciCientitySearchVOS, query);
            } else {
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
            }
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

    @Override
    public void downloadTemplate(HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery, HttpServletResponse response) {

        FileInfo infoDto = null;
        try {
            //判断设备分类
            if (StringUtil.isBlank(hardwareBasicCmdbQuery.getDeviceCategory())) {
                //返回异常
                return;
            }

            String prefix = null;
            if (StringUtils.isNotBlank(hardwareBasicCmdbQuery.getDeviceType())) {
                //  设备类型(id + value)
                Map<Object, Object> deviceTypeMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceType());
                prefix = String.valueOf(deviceTypeMap.get(hardwareBasicCmdbQuery.getDeviceType()));
            } else if (StringUtils.isNotBlank(hardwareBasicCmdbQuery.getDeviceCategory())) {
                // 设备分类 (id + value)
                Map<Object, Object> deviceClaccifyMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify());
                prefix = String.valueOf(deviceClaccifyMap.get(hardwareBasicCmdbQuery.getDeviceCategory()));
            }
            //获取文件名称
            SimpleDateFormat DATE_FORMAT_NO_UNDERLINE_TIME = new SimpleDateFormat("yyyyMMddHHmm");
            String timePrefix = prefix + "_" + DATE_FORMAT_NO_UNDERLINE_TIME.format(new Date());
            String fileName = getFileNameByQuery(timePrefix);
            if (StringUtil.isBlank(hardwareBasicCmdbQuery.getBs())) {
                infoDto = new FileInfo();
                infoDto.setStatus(1);
                infoDto.setId(UuidUtils.uuid());
                infoDto.setFileName(timePrefix + ".xlsx");
                infoDto.setExportTime(new Date());
                infoDto.setExportJson(JSON.toJSONString(hardwareBasicCmdbQuery));
                fileInfoService.save(infoDto);
            } else {
                infoDto = fileInfoService.getById(hardwareBasicCmdbQuery.getFileId());
                infoDto.setStatus(1);
                fileInfoService.updateById(infoDto);
            }
            //根据类型获取 指定的模型 实体类
            Class<T> cls = getClassByClassif(hardwareBasicCmdbQuery.getDeviceCategory());
            //文件输出格式
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", -1);
            response.setHeader("Content-Disposition", " attachment;filename=" + fileName);
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setWrapped(false);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new CommentHeaderWriteHandler(checkForDown(hardwareBasicCmdbQuery.getDeviceCategory())))
                    .build();
            //创建 sheet
            WriteSheet writeSheet = createWriteSheetByClassif(cls, getTypeNameByDeviceCategory(hardwareBasicCmdbQuery.getDeviceCategory()));
            //获取设备台账列表 数据
            R<FeignCiCientity> cmdbLists = listCmdb(hardwareBasicCmdbQuery);
            FeignCiCientity feignCiCientity = cmdbLists.getData();
            //获取实体类
            excelWriter.write(getBeanByMap(feignCiCientity.getData(), cls), writeSheet);
            File fileT = null;
            try {
                /**
                 * 技术验证将文件存储至oss  将 返回的路径 写入idevelop_device_cmdb_file
                 *     "link": "http://xt-public-storage.oss-cn-jinan-sddlyf-d01-a.ops-devcloud.sd.sgcc.com.cn/upload/20240529/ceea039147eae409444833b21bb4b3a5.xlsx",
                 *     "domain": "http://xt-public-storage.oss-cn-jinan-sddlyf-d01-a.ops-devcloud.sd.sgcc.com.cn",
                 *     "name": "upload/20240529/ceea039147eae409444833b21bb4b3a5.xlsx",
                 *     "originalName": "设备台账3711305618855034881130567656865792 - 2024-05-29T152359.242.xlsx",
                 *     "fileType": "xlsx"
                 *   },
                 */
                fileT = File.createTempFile("cmdb", DateUtil.format(DateUtil.now(), "yyyyMMddHHmmss") + ".xlsx");
                ExcelWriter excelWriterLocal = EasyExcel.write(fileT.getAbsolutePath())
                        .registerWriteHandler(horizontalCellStyleStrategy)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .registerWriteHandler(new CommentHeaderWriteHandler(checkForDown(hardwareBasicCmdbQuery.getDeviceCategory())))
                        .build();
                excelWriterLocal.write(getBeanByMap(feignCiCientity.getData(), cls), writeSheet);
                excelWriterLocal.finish();
                //获取文件输出流
                InputStream inputStream = new FileInputStream(fileT);
                IdevelopFile idevelopFile = aliossTemplate.putFile(fileName, inputStream);
                String link = idevelopFile.getLink();
                String filePath = link.split("\\?Expires=")[0];
                // TODO 将 返回的路径 写入idevelop_device_cmdb_file
                inputStream.close();

                fileT.delete();
                if (StringUtil.isNotBlank(hardwareBasicCmdbQuery.getBs())) {
                    infoDto = fileInfoService.getById(hardwareBasicCmdbQuery.getFileId());
                }
                infoDto.setLink(filePath);
                infoDto.setOssName(idevelopFile.getOriginalName());
                infoDto.setStatus(2);
                fileInfoService.updateById(infoDto);
                excelWriter.finish();
            } catch (Exception e) {
                log.error("设备台账数据文件存储至oss异常：{}", e.getMessage());
                throw new ServiceException("设备台账数据文件存储至oss异常!");
            } finally {
                if (fileT != null) {
                    fileT.delete();
                }
            }
        } catch (Exception e) {
            log.error("设备台账数据下载失败：{}", e.getMessage());
            infoDto.setStatus(3);
            if (StringUtil.isBlank(hardwareBasicCmdbQuery.getBs())) {
                fileInfoService.updateById(infoDto);
            } else {
                infoDto = fileInfoService.getById(hardwareBasicCmdbQuery.getFileId());
                fileInfoService.updateById(infoDto);
            }
            throw new ServiceException("设备台账下载失败!");
        }
    }

    // 针对导出的添加说明
    public TreeMap<Integer, ExcelReadBean> checkForDown(String deviceCategory) {
        TreeMap<Integer, ExcelReadBean> cellMap = new TreeMap<>();
        Object cls = null;
        //Class cls = HardwareBasicCmdbDeviceDTO.class;
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            cls = new HardwareBasicCmdbDeviceJCSSDTO();
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
            // 字典枚举ID-办公设备
            cls = new HardwareBasicCmdbDeviceBGSBDTO();
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 字典枚举ID-辅助设备
            cls = new HardwareBasicCmdbDeviceFZSBDTO();
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            cls = new HardwareBasicCmdbDeviceZDSBDTO();
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            cls = new HardwareBasicCmdbDeviceAQSBDTO();
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
            // 字典枚举ID-网络设备
            cls = new HardwareBasicCmdbDeviceWLSBDTO();
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
            // 字典枚举ID-存储设备
            cls = new HardwareBasicCmdbDeviceCCSBDTO();
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
            // 字典枚举ID-主机设备
            cls = new HardwareBasicCmdbDeviceZJSBDTO();
        }
        // 测试
        if (cls == null) {
            return cellMap;
        }
        Field[] fields = cls.getClass().getDeclaredFields();
        Map<String, ExcelReadBean> stringMap = getCheckKey(deviceCategory);
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (name.equals("serialVersionUID") || name.equals("index") ||
                    name.equals("id")) {
                continue;
            }
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            int index = property.index();
            ExcelReadBean check = stringMap.get(name);
            cellMap.put(index, check);
        }
        return cellMap;
    }

    private Map<String, ExcelReadBean> getCheckKey(String deviceCategory) {
        Map<String, ExcelReadBean> result = new HashMap<>();
        ClassPathResource resource = new ClassPathResource("/template/keyRead.xlsx");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            List<ExcelReadBean> list = EasyExcel.read(inputStream).head(ExcelReadBean.class).doReadAllSync();
            for (ExcelReadBean readBean : list) {
                result.put(readBean.getKey(), readBean);
            }
//			if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
////				result.remove("brand");
////				result.remove("series");
////				result.remove("deviceModel");
////				result.remove("sn");
////				result.remove("hardDiskCapability");
////				result.remove("memSize");
////				result.remove("cpuCoreSize");
////				result.remove("cpuBrand");
////				result.remove("networkCardSize");
////				result.remove("cpuArchCode");
////				result.remove("cpuArch");
////				result.remove("cpuArch");
////				result.remove("cpuArch");
//			} else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
////				result.remove("brand");
////				result.remove("series");
////				result.remove("deviceModel");
////				result.remove("sn");
////				result.remove("hardDiskCapability");
////				result.remove("ratedPower");
////				result.remove("powerModel");
////				result.remove("storageCapacity");
//			} else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
////				result.remove("brand");
////				result.remove("series");
////				result.remove("deviceModel");
////				result.remove("sn");
//			} else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
////				result.remove("brand");
////				result.remove("series");
////				result.remove("deviceModel");
////				result.remove("sn");
//			}
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败");
                }
            }
        }
        return result;
    }

    /**
     * 获取设备名
     *
     * @param deviceCategory
     * @return
     */
    private String getTypeNameByDeviceCategory(String deviceCategory) {
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            return "基础设施";
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
        } else if ("0".equals(deviceCategory)) {
            // 非数字化入网设备
            return "非数字化入网设备";
        }
        return "";
    }

    /**
     * 获取设备名(存量数据治理，是否数字化入网设备用)
     *
     * @param deviceCategory
     * @return
     */
    private String getTypeNameByDeviceCategoryNew(String deviceCategory, String isMath) {
        if (cmdbCientityProperties.getT109().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-基础设施
            return "基础设施";
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-办公设备
            return "办公设备";
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-辅助设备
            return "辅助设备";
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-终端设备
            return "终端设备";
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-安全设备
            return "安全设备";
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-网络设备
            return "网络设备";
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-存储设备
            return "存储设备";
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-主机设备
            return "主机设备";
        } else if ("1".equals(isMath)) {
            // 非数字化入网设备
            return "非数字化入网设备";
        }
        return "";
    }

    /**
     * 根据类型获取 指定的模型 实体类
     */
    private Class getClassByClassif(String deviceCategory) {
        Class cls = HardwareBasicCmdbDeviceDTO.class;
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            cls = HardwareBasicCmdbDeviceJCSSDTO.class;
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
            // 字典枚举ID-办公设备
            cls = HardwareBasicCmdbDeviceBGSBDTO.class;
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 字典枚举ID-辅助设备
            cls = HardwareBasicCmdbDeviceFZSBDTO.class;
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            cls = HardwareBasicCmdbDeviceZDSBDTO.class;
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            cls = HardwareBasicCmdbDeviceAQSBDTO.class;
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
            // 字典枚举ID-网络设备
            cls = HardwareBasicCmdbDeviceWLSBDTO.class;
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
            // 字典枚举ID-存储设备
            cls = HardwareBasicCmdbDeviceCCSBDTO.class;
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
            // 字典枚举ID-主机设备 1
            cls = HardwareBasicCmdbDeviceZJSBDTO.class;
        }
        return cls;
    }

    /**
     * 根据类型获取 指定的模型 实体类 (存量数据治理，是否数字化入网设备用)
     */
    private Class getClassByClassifNew(String deviceCategory, String isMath) {
        Class cls = HardwareBasicCmdbDeviceDTO.class;
        if (cmdbCientityProperties.getT109().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-基础设施
            cls = HardwareBasicCmdbDeviceJCSSDTO.class;
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-办公设备
            cls = HardwareBasicCmdbDeviceBGSBDTO.class;
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-辅助设备
            cls = HardwareBasicCmdbDeviceFZSBDTO.class;
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-终端设备
            cls = HardwareBasicCmdbDeviceZDSBDTO.class;
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-安全设备
            cls = HardwareBasicCmdbDeviceAQSBDTO.class;
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-网络设备
            cls = HardwareBasicCmdbDeviceWLSBDTO.class;
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-存储设备
            cls = HardwareBasicCmdbDeviceCCSBDTO.class;
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory) && "0".equals(isMath)) {
            // 字典枚举ID-主机设备 1
            cls = HardwareBasicCmdbDeviceZJSBDTO.class;
        } else if ("1".equals(isMath)) {
            // 非数字化入网设备
            cls = HardwareBasicCmdbDeviceFSZHRWSBDTO.class;
        }
        return cls;
    }


    /**
     * 根据类型获取 指定的模型 实体类
     */
    private Class getVOClassByClassif(String deviceCategory) {
        Class cls = HardwareBasicCmdbDeviceDTO.class;
        if (cmdbCientityProperties.getT109().equals(deviceCategory)) {
            // 字典枚举ID-基础设施
            cls = HardwareBasicCmdbDeviceJCSSDTO.class;
        } else if (cmdbCientityProperties.getT107().equals(deviceCategory)) {
            // 字典枚举ID-办公设备
            cls = HardwareBasicCmdbDeviceBGSBDTO.class;
        } else if (cmdbCientityProperties.getT106().equals(deviceCategory)) {
            // 字典枚举ID-辅助设备
            cls = HardwareBasicCmdbDeviceFZSBDTO.class;
        } else if (cmdbCientityProperties.getT105().equals(deviceCategory)) {
            // 字典枚举ID-终端设备
            cls = HardwareBasicCmdbDeviceZDSBDTO.class;
        } else if (cmdbCientityProperties.getT104().equals(deviceCategory)) {
            // 字典枚举ID-安全设备
            cls = HardwareBasicCmdbDeviceAQSBDTO.class;
        } else if (cmdbCientityProperties.getT103().equals(deviceCategory)) {
            // 字典枚举ID-网络设备
            cls = HardwareBasicCmdbDeviceWLSBDTO.class;
        } else if (cmdbCientityProperties.getT102().equals(deviceCategory)) {
            // 字典枚举ID-存储设备
            cls = HardwareBasicCmdbDeviceCCSBDTO.class;
        } else if (cmdbCientityProperties.getT101().equals(deviceCategory)) {
            // 字典枚举ID-主机设备
            cls = HardwareBasicCmdbDeviceZJSBDTO.class;
        } else if ("0".equals(deviceCategory)) {
            // 非数字化入网设备
            cls = HardwareBasicCmdbDeviceFSZHRWSBDTO.class;
        }
        return cls;
    }

    /**
     * 根据类型获取 指定的模型 实体类
     */
    private WriteSheet createWriteSheetByClassif(Class cls, String DeviceCategory) {
        WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(cls, 0, DeviceCategory);
        //writeSheet.setColumnWidthMap(getColumnWidthByMap(HardwareBasicCmdbDeviceDTO.class));
        return writeSheet;
    }


    /**
     * 设置固定 宽度
     *
     * @param hardwareBasicCmdbDeviceDTOClass
     * @return
     */
    private Map<Integer, Integer> getColumnWidthByMap(Class<HardwareBasicCmdbDeviceDTO> hardwareBasicCmdbDeviceDTOClass) {
        Map<Integer, Integer> colMap = new HashMap<>();
        Field[] fields = hardwareBasicCmdbDeviceDTOClass.getDeclaredFields();
        int idx = 0;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            if (property != null) {
                colMap.put(idx, 30);
                idx++;
            }
        }
        return colMap;
    }


    /**
     * 根据查询参数返回文件名称
     *
     * @param prefix
     * @return
     */
    private String getFileNameByQuery(String prefix) {
        String fileName = prefix;
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return fileName + ".xlsx";
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

                    //操作系统发行版本
                    String oSIssueVersion = String.valueOf(device.get("OSIssueVersion")).replace("null", "");
                    if (StringUtil.isNotBlank(oSIssueVersion)) {
                        device.put("OSIssueVersion", getColNameByCmdbVal(oSIssueVersion, "OSIssueVersion", cls));
                    }

                    //操作系统发行版本
                    String cloudType = String.valueOf(device.get("cloudType")).replace("null", "");
                    if (StringUtil.isNotBlank(cloudType)) {
                        device.put("cloudType", getColNameByCmdbVal(cloudType, "cloudType", cls));
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


    /**
     * 获取设备类型--T10101
     */
    @Override
    public Map<String, String> getDeviceTypeRemark() {
        Long deviceType = cmdbDictProperties.getDeviceType();
        String redisKey = CacheNames.CMDB_DICT_STORAGE_TXXXX + deviceType;
        Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
        if (ObjectUtils.isEmpty(cache)) {
            R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(deviceType);
            cache = CmdbDictUtil.getTxxx(dict);
            //防火墙 -> T10401
            redisUtil.set(redisKey, cache);
        }
        return cache;
    }

    /**
     * 根据领用单位名称获取区域编码
     *
     * @param receiveUnit 领用单位名称
     * @return
     */
    public String getRegionCode(String receiveUnit) {
        if (StringUtil.isBlank(receiveUnit)) {
            throw new ServiceException("获取区域编码:领用单位不能为空");
        }
        String regionCode = deviceStorageMapper.getRegionCode(receiveUnit);
        if (StringUtil.isBlank(regionCode)) {
            throw new ServiceException("获取区域编码失败");
        }
        return regionCode;
    }

    /**
     * 根据名称获取 部门单位的编码
     */
    private String getDeptCodeByName(String name) {
        // 产权单位, 领用单位, 检修单位, 运维单位
        name = name.replace("null", "");
        if (StringUtil.isNotBlank(name)) {
            String unitCode = DeptWrapper.build().getDeptCodeByName(name);
            return unitCode;
        }
        return null;
    }

    private String getDeptCodeByNameAndPid(String name, String pid) {
        // 产权单位, 领用单位, 检修单位, 运维单位
        name = name.replace("null", "");
        if (StringUtil.isNotBlank(name)) {
            String unitCode = DeptWrapper.build().getDeptCodeByNameAndPid(name, pid);
            return unitCode.replace("null", "");
        }
        return null;
    }


}

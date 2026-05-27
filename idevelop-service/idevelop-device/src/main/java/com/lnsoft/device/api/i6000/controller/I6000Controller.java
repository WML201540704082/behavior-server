package com.lnsoft.device.api.i6000.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.api.cmdb.excel.DeviceCodeListener;
import com.lnsoft.device.api.erp.service.IErpSyncService;
import com.lnsoft.device.api.i6000.dto.*;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.device.api.i6000.response.I6000ResultResp;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.vo.I6000DetailVO;
import com.lnsoft.device.api.stock.service.II6000ERPService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.dto.I6000SrynDTO;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.common.utils.UuidUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/25 10:43
 * @Description: I6000Controller
 */

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/I6000")
@Api(value = "i6000主要接口", tags = "i6000主要接口")
public class I6000Controller {

    private II6000Service i6000Service;
    private RedisUtil redisUtil;
    private CmdbDictProperties cmdbDictProperties;
    private IErpSyncService i6000ErpService;
    private II6000ERPService i6000ERPService;

    /**
     * I6000授权配置
     */
    @PostMapping("/auth")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "认证信息", notes = "传入accessToken, publicKey")
    public R<String> createAuthInfo(@RequestParam String accessToken, @RequestParam String publicKey) {
        String authInfo = i6000Service.createAuthInfo(accessToken, publicKey);
        return R.data(authInfo);
    }

    /**
     * 配置类型分类查询接口
     */
    @GetMapping("/ci/type")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "配置类型分类查询接口", notes = "传入ciTypeId")
    public R<List<Map<String, Object>>> selectCiType(String ciTypeId) {
        List<Map<String, Object>> result = i6000Service.selectCiType(ciTypeId);
        return R.data(result);
    }

    /**
     * 配置类型属性信息查询接口
     */
    @GetMapping("/ci/attr")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "配置类型属性信息查询接口", notes = "传入ciTypeId")
    public R<List<Map<String, Object>>> selectCiAttr(String ciTypeId) {
        List<Map<String, Object>> result = i6000Service.selectCiAttr(ciTypeId);
        return R.data(result);
    }

    /**
     * 字典表所有枚举项查询接口
     */
    @GetMapping("/enum/all")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "字典表所有枚举项查询接口")
    public R<JSONObject> selectEnumAll() {
        JSONObject result = i6000Service.selectEnumAll();
        return R.data(result);
    }

    /**
     * 查询字典表某一枚举项的所有枚举值
     */
    @GetMapping("/enum")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "查询字典表某一枚举项的所有枚举值", notes = "传入enumID")
    public R<JSONObject> selectEnumByID(String enumID, String enumValID, String fEnumValID) {
        JSONObject authInfo = i6000Service.selectEnumByID(enumID, enumValID, fEnumValID);
        return R.data(authInfo);
    }

    /**
     * 模型间关联关系类型查询接口
     */
    @GetMapping("/cm/asct")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "配置项关联信息查询接口", notes = "传入ciid(父id)")
    public R<JSONObject> selectRelationByCiid(String ciid) {
        JSONObject authInfo = i6000Service.selectRelationByCiid(ciid);
        return R.data(authInfo);
    }

    /**
     * 查询全部I6000外部数据视图接口
     */
    @GetMapping("/ori/queryoriview")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "查询全部I6000外部数据视图接口")
    public R<List<Map<String, Object>>> queryOriView() {
        List<Map<String, Object>> result = i6000Service.queryOriView();
        return R.data(result);
    }

    /**
     * 查询指定的外部数据（分页）接口
     */
    @GetMapping("/ori/view")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "查询指定的外部数据（分页）接口", notes = "传入i6000OriViewDTO")
    public R<List<I6000External>> selectOriView(I6000OriViewDTO i6000OriViewDTO) {
        List<I6000External> result = i6000Service.selectOriView(i6000OriViewDTO);
        return R.data(result);
    }

    /**
     * 查询指定的外部数据（不分页）接口
     */
    @GetMapping("/ori/view/all")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "查询指定的外部数据（不分页）接口", notes = "传入oriViewCode(外部视图CODE)")
    public R<Map<String, Object>> selectOriViewAll(String oriViewCode) {
        Map<String, Object> result = i6000Service.selectOriViewAll(oriViewCode);
        return R.data(result);
    }

    // /**
    //  * 更新i6000资产台账接口 实例代码,仅供参考
    //  */
    // @GetMapping("/i6000/sync/batch/update/")
    // @ApiOperationSupport(order = 100)
    // @ApiOperation(value = "更新i6000资产台账接口")
    // public R<List<I6000ResultResp>> i6000SyncBatchupdate() {
    //
    //
    //
    // 	List<I6000ResultResp> authInfo = i6000Service.i6000Batchupdate(stringMapMap);
    // 	return R.data(authInfo);
    // }

    /**
     * 根据ERP资产编码和ERP设备编码, 信通一体化查询I6000系统数据
     */
    @PostMapping("/select/i6000detail")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "根据ERP资产编码和ERP设备编码, 信通一体化查询I6000系统数据", notes = "传入ERP设备台账编码,ERP资产编码")
    public R<Map<String, I6000DetailVO>> selectI6000Detail(@RequestBody List<I6000SrynDTO> i6000SrynDTOS) {
        Map<String, I6000DetailVO> result = i6000Service.selectI6000Detail(i6000SrynDTOS);
        return R.data(result);
    }

    @PostMapping("/sync/I6000detail")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "根据ERP资产编码和ERP设备编码, 信通一体化同步I6000系统数据", notes = "传入deviceCode设备编码")
    public R<List<ProjectManagerDetail>> syncI6000Detail(@RequestBody List<I6000SyncDetailDTO> i6000SyncDetailDTOS) {
        List<ProjectManagerDetail> projectManagerDetails = i6000Service.syncI6000Detail(i6000SyncDetailDTOS);
        return R.data(projectManagerDetails);
    }


    /**
     * 根据信通一体化设备编码, 同步I6000系统数据
     *
     * @param file
     * @param type 0：新增；1：修改
     * @return
     */
    @PostMapping("/import/sync/i6000detail")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "根据信通一体化设备编码, 同步I6000系统数据", notes = "导入模板传入deviceCode设备编码")
    public R<String> importSyncI6000Detail(MultipartFile file, Integer type) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            throw new ServiceException("请上传文件!");
        }
        if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx")) && !StringUtils.endsWithIgnoreCase(filename, ".xlsm")) {
            throw new ServiceException("请上传正确的excel文件!");
        }
        if (file.getSize() > 1024 * 1024 * 100) {
            return R.fail("文件大小超过限制，最大允许" + 1024 * 1024 * 100 + "MB");
        }
        InputStream inputStream = null;
        try {
            DeviceCodeListener deviceCodeListener = new DeviceCodeListener();
            inputStream = new BufferedInputStream(file.getInputStream());
            EasyExcel.read(inputStream, DeviceCodeStencil.class, deviceCodeListener).sheet().doRead();
            List<DeviceCodeStencil> list = deviceCodeListener.getList();
            String result = i6000ERPService.importSyncI6000Detail(list, type);
            return R.data(result);
        } catch (IOException e) {
            log.error("流读取失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败");
                }
            }
        }
        return R.data("同步失败");
    }


    @PostMapping("/import/sync/erpDetail")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "根据信通一体化设备编码, 同步Erp系统数据", notes = "导入模板传入deviceCode设备编码")
    public R<String> importSyncErpDetail(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            throw new ServiceException("请上传文件!");
        }
        if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx")) && !StringUtils.endsWithIgnoreCase(filename, ".xlsm")) {
            throw new ServiceException("请上传正确的excel文件!");
        }
        if (file.getSize() > 1024 * 1024 * 100) {
            return R.fail("文件大小超过限制，最大允许" + 1024 * 1024 * 100 + "MB");
        }

        InputStream inputStream = null;
        try {
            DeviceCodeListener deviceCodeListener = new DeviceCodeListener();
            inputStream = new BufferedInputStream(file.getInputStream());
            EasyExcel.read(inputStream, DeviceCodeStencil.class, deviceCodeListener).sheet().doRead();
            List<DeviceCodeStencil> list = deviceCodeListener.getList();
            String result = i6000ErpService.importSyncErpDetail(list);
            return R.data(result);
        } catch (IOException e) {
            log.error("流读取失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败");
                }
            }
        }
        return R.data("同步失败");
    }


    /**
     * 获取I6000台账 填充信通一体化实物ID
     *
     * @param i6000EntityIdDTO
     * @return
     */
    @PostMapping("/getEntityId")
    @ApiOperationSupport(order = 13)
    @ApiOperation(value = "获取I6000台账 填充信通一体化实物ID", notes = "导入模板传入deviceCode设备编码")
    public R<String> getI6000EntityId(@RequestBody I6000EntityIdDTO i6000EntityIdDTO) {
        try {
            String result = i6000ErpService.getI6000EntityId(i6000EntityIdDTO);
            return R.data(result);
        } catch (Exception e) {
            log.error("获取I6000台账 填充信通一体化实物ID: {}", e.getMessage());
            return R.fail("同步失败");
        }
    }


    /**
     * 获取I6000的仓库信息(T501)
     */
    @GetMapping("/get/room")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "获取I6000的仓库信息(T501)")
    public R<List<I6000RoomDTO>> getRoomList() {
        List<I6000RoomDTO> i6000RoomDTOList = i6000Service.getRoomList();
        return R.data(i6000RoomDTOList);
    }

    /**
     * 获取I6000的机房信息(T502)
     */
    @GetMapping("/get/warehouse")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "获取I6000的机房信息(T502)")
    public R<List<I6000WarehouseDTO>> getWarehouseList() {
        List<I6000WarehouseDTO> i6000WarehouseDTOS = i6000Service.getWarehouseList();
        return R.data(i6000WarehouseDTOS);
    }

    /**
     * 配置项查询接口
     */
    @PostMapping("/ci/cientity")
    @ApiOperationSupport(order = 95)
    @ApiOperation(value = "配置项查询接口", notes = "传入ciTypeId")
    public R<List<Map<String, Object>>> selectCiCientity(@Valid @RequestBody I6000CiCientityAllDTO i6000CiCientityAllDTO) {
        I6000CiCientityDTO i6000CiCientityDTO = i6000CiCientityAllDTO.getI6000CiCientityDTO();
        String ciTypeId = i6000CiCientityAllDTO.getCiTypeId();
        List<Map<String, Object>> result = i6000Service.selectCiCientity(ciTypeId, i6000CiCientityDTO);
        return R.data(result);
    }

    /**
     * 功能位置(新增和修改)
     */
    @PostMapping("/func/add/update")
    @ApiOperationSupport(order = 96)
    @ApiOperation(value = "功能位置(新增和修改)", notes = "传入 i6000FuncDTO")
    public R<Map<String, Object>> saveOrUpdateFunc(@Valid @RequestBody I6000FuncDTO i6000FuncDTO) {
        try {
            Map<String, Object> resultMap = i6000Service.saveOrUpdateFunc(i6000FuncDTO);
            return R.data(resultMap);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 功能位置(删除)
     */
    @PostMapping("/func/delete")
    @ApiOperationSupport(order = 97)
    @ApiOperation(value = "功能位置(删除)", notes = "传入 objId")
    public R<Map<String, Object>> deleteFunc(@Valid @RequestBody String objId) {
        try {
            Map<String, Object> resultMap = i6000Service.deleteFunc(objId);
            return R.data(resultMap);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 刷新I6000字典缓存
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/refresh/i6000")
    @ApiOperationSupport(order = 98)
    @ApiOperation(value = "刷新I6000字典缓存")
    public R refreshCache(@RequestBody JSONObject jsonObject) {
        Map<String, Object> innerMap = jsonObject.getInnerMap();

        // 制造商
        redisUtil.set(CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getMaker(), cmdbDictProperties.getDictI6000MapByCiId(cmdbDictProperties.getMaker()));
        // 品牌
        redisUtil.set(CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getBrand(), cmdbDictProperties.getDictI6000MapByCiId(cmdbDictProperties.getBrand()));
        // 系列
        redisUtil.set(CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getSeries(), cmdbDictProperties.getDictI6000MapByCiId(cmdbDictProperties.getSeries()));
        // 型号
        redisUtil.set(CacheNames.GENERATE_DICT_CMDB_I6000 + cmdbDictProperties.getModel(), cmdbDictProperties.getDictI6000MapByCiId(cmdbDictProperties.getModel()));

        return R.data(Boolean.TRUE);
    }

    /**
     * 新增i6000资产台账接口 实例代码,仅供参考
     */
    @GetMapping("/i6000/batch/save")
    @ApiOperationSupport(order = 99)
    @ApiOperation(value = "新增i6000资产台账接口", notes = "传入ciTypeId")
    public R<I6000ResultResp> i6000Batchsave(String ciTypeId) {

        Map<String, Map<String, Object>> stringMapMap = new HashMap<>();
        Map<String, Object> upMap = new HashMap<>();
        upMap.put("uuid", "ec0ae48e8d2c473288711a133fb26107");
        upMap.put("area", "370112");
        upMap.put("dept", "1768840940550901762");
        upMap.put("inWarehouseDate", "2024-04-03");
        upMap.put("operationUnit", "历城公司");
        upMap.put("operationUnitCode", "1768803691075489793");
        upMap.put("deviceSource", "统一纳管");
        upMap.put("deviceSourceCode", "0");
        upMap.put("wbsElementName", "历城供电公司资金支付虚拟项目");
        upMap.put("wbsElement", "L0601AA000");
        upMap.put("deviceCategory", "终端设备");
        upMap.put("deviceStatusCode", "1105089449492480");
        upMap.put("receiveUnitCode", "1773660554869415937");
        upMap.put("receiveDeptCode", "1780068581785354241");
        upMap.put("brandCode", "1780068581785354241");
        stringMapMap.put("ec0ae48e8d2c473288711a133fb26107", upMap);

        ciTypeId = "T10501";
        I6000ResultResp authInfo = i6000Service.i6000Batchsave(ciTypeId, stringMapMap);
        return R.data(authInfo);
    }

    /**
     * 新增i6000资产台账接口 实例代码,仅供参考  (机房仓库)
     */
    @GetMapping("/i6000/batch/save/test")
    @ApiOperationSupport(order = 99)
    @ApiOperation(value = "新增i6000资产台账接口(机房仓库)", notes = "传入ciTypeId")
    public R<I6000ResultResp> i6000BatchsaveTest() {

        Map<String, Map<String, Object>> stringMapMap = new HashMap<>();
        Map<String, Object> i6000Map = new HashMap<>();
        i6000Map.put(CmdbAttrConstant.CI_NAME, "测试");
        i6000Map.put(I6000AttrConstant.CITYPE_ID, "T501");
        i6000Map.put(I6000AttrConstant.CI_ID, UuidUtils.uuid());
        i6000Map.put(I6000AttrConstant.RUN_LEVEL, "");
        i6000Map.put(I6000AttrConstant.CITYPE, "T501");
        i6000Map.put(I6000AttrConstant.LOCATION, "测试地址");
        i6000Map.put(I6000AttrConstant.STOREY, "");
        i6000Map.put(I6000AttrConstant.OPERATE_STATUS, "");
        i6000Map.put(I6000AttrConstant.TOTAL_CABINET, "");
        i6000Map.put(I6000AttrConstant.RUN_CORP_CODE, "ff808081510087920151713698a07019");
        i6000Map.put(I6000AttrConstant.RUN_CORP_CODE_NAME, "国网山东省电力公司本部");
        stringMapMap.put("ec0ae48e8d2c473288711a133fb26107", i6000Map);
        I6000ResultResp authInfo = i6000Service.i6000BatchsaveDirect("T501", stringMapMap);
        return R.data(authInfo);
    }

    /**
     * 更新i6000资产台账接口 实例代码,仅供参考
     */
    @GetMapping("/i6000/batch/update")
    @ApiOperationSupport(order = 100)
    @ApiOperation(value = "更新i6000资产台账接口", notes = "")
    public R<List<I6000ResultResp>> i6000Batchupdate() {

        Map<String, Map<String, Object>> stringMapMap = new HashMap<>();
        Map<String, Object> upMap = new HashMap<>();
        upMap.put("uuid", "ec0ae48e8d2c473288711a133fb26107");
        upMap.put("area", "370112");
        upMap.put("dept", "1768840940550901762");
        upMap.put("inWarehouseDate", "2024-04-03");
        upMap.put("operationUnit", "历城公司");
        upMap.put("operationUnitCode", "1768803691075489793");
        upMap.put("deviceSource", "统一纳管");
        upMap.put("deviceSourceCode", "0");
        upMap.put("wbsElementName", "历城供电公司资金支付虚拟项目");
        upMap.put("wbsElement", "L0601AA000");
        upMap.put("deviceCategory", "终端设备");

        // 重要 必须有
        upMap.put("CITYPE_ID", "T10501");
        stringMapMap.put("ec0ae48e8d2c473288711a133fb26107", upMap);

        Map<String, Object> upMap1 = new HashMap<>();
        upMap1.put("uuid", "ec0ae48e8d2c473288711a133fb26103");
        upMap1.put("area", "370112");
        upMap1.put("dept", "1768840940550901762");
        upMap1.put("inWarehouseDate", "2024-04-03");
        upMap1.put("operationUnit", "历城公司");
        upMap1.put("operationUnitCode", "1768803691075489793");
        upMap1.put("deviceSource", "统一纳管");
        upMap1.put("deviceSourceCode", "0");
        upMap1.put("wbsElementName", "历城供电公司资金支付虚拟项目");
        upMap1.put("wbsElement", "L0601AA000");
        upMap1.put("deviceCategory", "终端设备");

        // 重要 必须有
        upMap1.put("CITYPE_ID", "T10501");
        stringMapMap.put("ec0ae48e8d2c473288711a133fb26102", upMap1);

        List<I6000ResultResp> authInfo = i6000Service.i6000Batchupdate(stringMapMap);
        return R.data(authInfo);
    }


    /**
     * 更新i6000资产台账接口 实例代码,仅供参考 (机房仓库)
     */
    @GetMapping("/i6000/batch/update/test")
    @ApiOperationSupport(order = 100)
    @ApiOperation(value = "更新i6000资产台账接口(机房仓库)", notes = "")
    public R<List<I6000ResultResp>> i6000BatchupdateTest() {

        Map<String, Map<String, Object>> stringMapMap = new HashMap<>();
        Map<String, Object> i6000Map = new HashMap<>();
        i6000Map.put(CmdbAttrConstant.CI_NAME, "测试");
        i6000Map.put(I6000AttrConstant.CITYPE_ID, "T501");
        i6000Map.put(I6000AttrConstant.CI_ID, UuidUtils.uuid());
        i6000Map.put(I6000AttrConstant.RUN_LEVEL, "");
        i6000Map.put(I6000AttrConstant.CITYPE, "T501");
        i6000Map.put(I6000AttrConstant.LOCATION, "测试地址");
        i6000Map.put(I6000AttrConstant.STOREY, "");
        i6000Map.put(I6000AttrConstant.OPERATE_STATUS, "");
        i6000Map.put(I6000AttrConstant.TOTAL_CABINET, "");
        i6000Map.put(I6000AttrConstant.RUN_CORP_CODE, "ff808081510087920151713698a07019");
        i6000Map.put(I6000AttrConstant.RUN_CORP_CODE_NAME, "国网山东省电力公司本部");

        // 重要 必须有
        i6000Map.put("CITYPE_ID", "T501");
        stringMapMap.put("ec0ae48e8d2c473288711a133fb26107", i6000Map);

        Map<String, Object> i6000Map1 = new HashMap<>();
        i6000Map1.put(CmdbAttrConstant.CI_NAME, "测试");
        i6000Map1.put(I6000AttrConstant.CITYPE_ID, "T501");
        i6000Map1.put(I6000AttrConstant.CI_ID, UuidUtils.uuid());
        i6000Map1.put(I6000AttrConstant.RUN_LEVEL, "");
        i6000Map1.put(I6000AttrConstant.CITYPE, "T501");
        i6000Map1.put(I6000AttrConstant.LOCATION, "测试地址");
        i6000Map1.put(I6000AttrConstant.STOREY, "");
        i6000Map1.put(I6000AttrConstant.OPERATE_STATUS, "");
        i6000Map1.put(I6000AttrConstant.TOTAL_CABINET, "");
        i6000Map1.put(I6000AttrConstant.RUN_CORP_CODE, "ff808081510087920151713698a07019");
        i6000Map1.put(I6000AttrConstant.RUN_CORP_CODE_NAME, "国网山东省电力公司本部");

        // 重要 必须有
        i6000Map1.put("CITYPE_ID", "T1501");
        stringMapMap.put("ec0ae48e8d2c473288711a133fb26102", i6000Map1);

        List<I6000ResultResp> authInfo = i6000Service.i6000Batchupdate(stringMapMap);
        return R.data(authInfo);
    }
}

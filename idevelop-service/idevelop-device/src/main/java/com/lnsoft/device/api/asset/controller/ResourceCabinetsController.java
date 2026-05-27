package com.lnsoft.device.api.asset.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
import com.lnsoft.device.api.asset.dto.ExportCabinets;
import com.lnsoft.device.api.asset.dto.ResourceCabinetsDTO;
import com.lnsoft.device.api.asset.entity.ResourceCabinets;
import com.lnsoft.device.api.asset.entity.ResourceCabinetsLs;
import com.lnsoft.device.api.asset.eums.ResourceTreeTypeEnum;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lnsoft.device.constant.CmdbAttrConstant.*;

/**
 * 空间资源管理机柜表 控制器
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/resource/cabinets")
@Api(value = "空间资源管理机柜表", tags = "空间资源管理机柜表接口")
public class ResourceCabinetsController extends IdevelopController {

	private IResourceCabinetsService resourceCabinetsService;
	private ICmdbService cmdbService;
	private OrderNumberUtil orderNumberUtil;
	private ICmdbService iCmdbService;
	private CmdbCientityProperties cmdbCientityProperties;
	/**
	 * 详情 列转行
	 */
	@GetMapping("/cientity/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "机柜详情（新）", notes = "传入feignCmdbCientityGet  id为cientityId   ciid 为 ciid")
	public R<Map<String, Object>> cientityDetail(FeignCmdbCientityGet feignCmdbCientityGet) {
		try {
			Map<String, Object> jsonObject = cmdbService.getCientityDetail(feignCmdbCientityGet);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 机柜分页（新）
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "机柜分页（新）", notes = "传入resourceCabinets")
	public R list(ResourceCabinetsDTO resourceCabinets, Query query) {
		return resourceCabinetsService.getList(resourceCabinets,query);
	}

	/**
	 * 自定义分页 空间资源管理机柜表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页(暂时不用)", notes = "传入resourceCabinets")
	public R<IPage<ResourceCabinetsLs>> page(ResourceCabinetsLs resourceCabinets, Query query) {
		IPage<ResourceCabinetsLs> resourceCabinetsVOIPage = resourceCabinetsService.selectResourceCabinetsPage(Condition.getPage(query), resourceCabinets);
		return R.data(resourceCabinetsVOIPage);
	}

	/**
	 * 移除机柜（新）
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "机房移除机柜（新）", notes = "传入id、uuid、ciid")
	public R remove(@RequestBody List<ResourceCabinets> resourceCabinetsList) {
		return resourceCabinetsService.removeCabinets(resourceCabinetsList);
	}
	/**
	 * 机柜数据导出
	 */
	@PostMapping("exportExcel")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "机柜导出(暂时不用)", notes = "")
	public void export(@RequestBody ResourceCabinetsDTO resourceCabinetsDTO, HttpServletResponse servletResponse) {
		resourceCabinetsService.export(resourceCabinetsDTO,servletResponse);

	}
	/**
	 * 机柜模板下载
	 */
	@PostMapping("downExcel")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "机柜模板下载(暂时不用)", notes = "")
	public void down(HttpServletResponse response) {
		try {
			ArrayList<ExportCabinets> exportCabinets = new ArrayList<>();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机柜列表模板", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), ExportCabinets.class).sheet("机柜列表").doWrite(exportCabinets);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}
	/**
	 * 机柜选择设备
	 */
	@GetMapping("getDeviceList")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "机柜选择设备(新)+机柜设备列表", notes = "")
	public R getDeviceList(DeviceCmdbDTO deviceCmdbDTO,Query query){
		return resourceCabinetsService.getDeviceList(deviceCmdbDTO,query);
	}
	/**
	 * U位校验
	 */
	@PostMapping("check")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "U位校验", notes = "传入机柜容量，设备列表，设备列表必须包含id，设备起始高度，设备高度")
	public R check(@RequestBody ResourceCabinetsDTO resourceCabinetsDTO){
		return resourceCabinetsService.check(resourceCabinetsDTO);

	}
	/**
	 * 提交关联设备
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "提交关联设备", notes = "")
	public R submit(@RequestBody ResourceCabinetsDTO resourceCabinetsDTO) {
		return resourceCabinetsService.submit(resourceCabinetsDTO);
	}
	/**
	 * U位选择接口
	 */
	@PostMapping("/getPlace")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "U位选择接口", notes = "")
	public R getPlace(@RequestBody ResourceCabinetsDTO resourceCabinetsDTO) {
		return resourceCabinetsService.getPlace(resourceCabinetsDTO);
	}
	/**
	 * 机柜移除设备
	 */
	@PostMapping("/removeDev")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "机柜移除设备", notes = "传入id、uuid、ciid")
	public R removeDev(@RequestBody List<ResourceCabinets> resourceCabinetsList) {
		return resourceCabinetsService.removeDev(resourceCabinetsList);
	}
	/**
	 * 新增机柜(空间资源管理)
	 */
	@PostMapping("/stock/add")
	@ApiOperationSupport(order = 98)
	@ApiOperation(value = "新增机柜(空间资源管理)")
	public R<Map<String, Object>> stockAdd(@RequestBody JSONObject jsonObject) {
		try {
			IdevelopUser user = SecureUtil.getUser();
			// 新增 资产台账
			Map<String, Map<String, Object>> hashMap = new HashMap<>();
			Map<String, Object> innerMap = jsonObject.getInnerMap();

			// 获取模型ID
			String deviceCategoryCode = (String) innerMap.get(DEVICE_CATEGORY_CODE);
			String deviceTypeCode = (String) innerMap.get(DEVICE_TYPE_CODE);
			if (org.apache.commons.lang3.StringUtils.isEmpty(deviceCategoryCode) || StringUtils.isEmpty(deviceTypeCode)) {
				return R.fail("请先选择设备分类和设备类型!");
			}
			// 设备编码
			String deviceCode = orderNumberUtil.generateCode(deviceTypeCode);
			innerMap.put(DEVICE_CODE, deviceCode);
			innerMap.put(AREA, user.getRegionCode());
			innerMap.put(DEPT, user.getDeptId());
			innerMap.put(OWNER_UNIT, jsonObject.get(RECEIVE_UNIT));
			innerMap.put(OWNER_UNIT_CODE, jsonObject.get(RECEIVE_UNIT_CODE));
			innerMap.put(CmdbAttrConstant.DEVICE_CATEGORY,"辅助设备");
			innerMap.put(CmdbAttrConstant.DEVICE_CATEGORY_CODE,cmdbCientityProperties.getT106());
			innerMap.put(CmdbAttrConstant.DEVICE_TYPE,"机柜");
			innerMap.put(CmdbAttrConstant.DEVICE_TYPE_CODE,cmdbCientityProperties.getT10603());
			innerMap.put(DEVICE_NAME,innerMap.get(CmdbAttrConstant.FULL_NAME));
			innerMap.put(CmdbAttrConstant.CABINET_USE_CAPACITY,"0");
			HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceClaccify(deviceCategoryCode).deviceType(deviceTypeCode).build();
			Long ciId = iCmdbService.getCiId(hardwareBasicTree);
			String uuid = UuidUtils.uuid();
			hashMap.put(uuid, innerMap);
			Map<String, Object> returnMap = iCmdbService.cientityBatchsave(ciId, hashMap, TransactionActionType.INSERT);
			//返回数据
			Map<String, Object> jsonObject1 = new HashMap<>();
			if (ResultCode.SUCCESS.getCode() == Integer.parseInt(String.valueOf(returnMap.get("Status")))){
				FeignCmdbCientityGet feignCmdbCientityGet = new FeignCmdbCientityGet();
				feignCmdbCientityGet.setCiId(ciId);
				Long ciEntityId = Long.valueOf(String.valueOf(returnMap.get(uuid)));
				feignCmdbCientityGet.setCiEntityId(ciEntityId);
				jsonObject1 = cmdbService.getCientityDetail(feignCmdbCientityGet);
				jsonObject1.put("type", ResourceTreeTypeEnum.CABINETS.getCode());
			}
			return R.data(jsonObject1);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}
	/**
	 * 机柜下拉（根据机房查询）
	 */
	@GetMapping("/getList")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "机柜下拉（根据机房查询）", notes = "传入resourceCabinets")
	public R getList(ResourceCabinetsDTO resourceCabinets,Query query) {
		return R.data(resourceCabinetsService.getCabinets(resourceCabinets,query));
	}
}

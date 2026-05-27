package com.lnsoft.device.api.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityBatchDelete;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.cmdb.vo.HardwareBasicQueryVO;
import com.lnsoft.cmdb.vo.HardwareBasicVO;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.I6000RequestDTO;
import com.lnsoft.device.api.asset.dto.WarehouseDetailDTO;
import com.lnsoft.device.api.asset.service.IHardwareBasicService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 资产台账 控制器
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/hardwarebasic")
@Api(value = "资产台账", tags = "资产台账管理接口")
public class HardwareBasicController extends IdevelopController {

	private IHardwareBasicService hardwareBasicService;
	private ICmdbService cmdbService;
	private CmdbCientityProperties ciEntityProperties;
	private ThirdProperties thirdProperties;

	/**
	 * 自定义分页 资产台账
	 */
	@PostMapping("/page/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "自定义分页", notes = "传入hardwareBasicVO")
	public R<JSONObject> page(@RequestBody HardwareBasicVO hardwareBasicVO) {
		JSONObject jsonObject = hardwareBasicService.selectHardwareBasicTreePage(hardwareBasicVO);
		return R.data(jsonObject);
	}

	/**
	 * 详情 资产台账
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "详情", notes = "传入feignCmdbCientityGet")
	public R<JSONObject> detail(FeignCmdbCientityGet feignCmdbCientityGet) {
		try {
			JSONObject jsonObject = hardwareBasicService.getDetailOne(feignCmdbCientityGet);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据条件分页查询 资产台账 列转行 (废弃)
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "根据条件分页查询 资产台账", notes = "传入deviceTransferDetail")
	public R<FeignCiCientity> list(HardwareBasicQueryVO hardwareBasicQueryVO, Query query) {
		try {

			// 2025-01-26 增加实际区域查询
			if (StringUtils.isNotEmpty(hardwareBasicQueryVO.getOwnerUnitCode())) {
				hardwareBasicQueryVO.setOwnerUnitCode("");
			}
			String area = hardwareBasicQueryVO.getArea();
			String areaSub = area.substring(0, 4);
			if (area.length() == 6) {
				hardwareBasicQueryVO.setArea(areaSub);
			}

			List<CiCientitySearchVO> ciCientitySearchVOS = getCiCientitySearchVOS(hardwareBasicQueryVO);

			Boolean isChange = hardwareBasicQueryVO.getIsChange();
			// 2024-12-03 先查询入库管理的设备
			if (!StringUtils.contains(thirdProperties.getRegions(), areaSub)  && !isChange) {
				CiCientitySearchVO sourceSystem = CiCientitySearchVO.builder()
					.attrName(CmdbAttrConstant.SOURCE_SYSTEM)
					.expression(Expression.EQUAL)
					.attrValue(ciEntityProperties.getSourceSystem1() + "," + ciEntityProperties.getSourceSystem2()).build();
				ciCientitySearchVOS.add(sourceSystem);
			}

			// 2025-01-26 增加实际区域查询
			if (area.length() == 6) {
				CiCientitySearchVO realArea = CiCientitySearchVO.builder()
					.attrName(CmdbAttrConstant.REAL_AREA)
					.expression(Expression.EQUAL).attrValue(area).build();
				ciCientitySearchVOS.add(realArea);
			}

			CiCientitySearch cientitySearch = new CiCientitySearch();
			cientitySearch.setEntity(ciCientitySearchVOS);
			cientitySearch.setFullField(Boolean.TRUE);
			cientitySearch.setQuery(query);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByCondition(cientitySearch);

			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据条件分页查询 资产台账 列转行 全量字段
	 */
	@GetMapping("/list/all")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "根据条件分页查询 资产台账", notes = "传入deviceTransferDetail")
	public R<FeignCiCientity> listAll(HardwareBasicQueryVO hardwareBasicQueryVO, Query query) {
		try {

			String area = hardwareBasicQueryVO.getArea();
			String areaSub = area.substring(0, 4);
			Boolean isChange = hardwareBasicQueryVO.getIsChange();
			if (hardwareBasicQueryVO.getIsSpecial()) {
				List<CiCientitySearchVO> ciCientitySearchVOS = getCiCientitySearchVOS(hardwareBasicQueryVO);
				CiCientitySearch cientitySearch = new CiCientitySearch();
				cientitySearch.setEntity(ciCientitySearchVOS);
				cientitySearch.setFullField(Boolean.TRUE);
				cientitySearch.setQuery(query);
				FeignCiCientity jsonObject = cmdbService.getCiCientityListByCondition(cientitySearch);
				return R.data(jsonObject);
			}

			// 2025-01-26 增加实际区域查询
			if (StringUtils.isNotEmpty(hardwareBasicQueryVO.getOwnerUnitCode())) {
				hardwareBasicQueryVO.setOwnerUnitCode("");
			}

			List<CiCientitySearchVO> ciCientitySearchVOS = getCiCientitySearchVOS(hardwareBasicQueryVO);

			// 2025-06-16 如果是市的 只用 区域 = 37XX 控制, 如果是县 需要 区域 like 37XX 加上实际区域 = 37XXXX 控制.
			if (area.length() == 6) {
				hardwareBasicQueryVO.setArea(areaSub);
			}
			// 2024-12-03 先查询入库管理的设备
			if (!StringUtils.contains(thirdProperties.getRegions(), areaSub) && !isChange) {
				CiCientitySearchVO sourceSystem = CiCientitySearchVO.builder()
					.attrName(CmdbAttrConstant.SOURCE_SYSTEM)
					.expression(Expression.EQUAL)
					.attrValue(ciEntityProperties.getSourceSystem1() + "," + ciEntityProperties.getSourceSystem2()).build();
				ciCientitySearchVOS.add(sourceSystem);
			}
			// 2025-01-26 增加实际区域查询
			if (area.length() == 6) {
				CiCientitySearchVO realArea = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.REAL_AREA).expression(Expression.EQUAL).attrValue(area).build();
				ciCientitySearchVOS.add(realArea);
			}
			CiCientitySearch cientitySearch = new CiCientitySearch();
			cientitySearch.setEntity(ciCientitySearchVOS);
			cientitySearch.setFullField(Boolean.TRUE);
			cientitySearch.setQuery(query);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByCondition(cientitySearch);

			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 详情 列转行
	 */
	@GetMapping("/cientity/detail")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "详情 列转行", notes = "传入feignCmdbCientityGet")
	public R<Map<String, Object>> cientityDetail(FeignCmdbCientityGet feignCmdbCientityGet) {
		try {
			Map<String, Object> jsonObject = cmdbService.getCientityDetail(feignCmdbCientityGet);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 批量删除配置项
	 */
	@PostMapping("/batch/delete")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "批量删除配置项", notes = "feignCmdbCientitySearch")
	public R<Boolean> update(@RequestBody FeignCmdbCientityBatchDelete feignCmdbCientityBatchDelete) {
		Boolean returnBL = cmdbService.cientityBatchDelete(feignCmdbCientityBatchDelete);
		return R.data(returnBL);
	}


	/**
	 * 根据条件获取 I6000 相关数据信息(数据治理修改功能)
	 */
	@GetMapping("/i6000/info")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "根据条件获取 I6000 相关数据信息(数据治理修改功能)", notes = "传入jsonObject")
	public R<Map<String, Object>> selectInfoByI6000(I6000RequestDTO selectI6000Info) {
		try {
			Map<String, Object> returnBL = hardwareBasicService.selectInfoByI6000(selectI6000Info);
			return R.data(returnBL);
		} catch (Exception e) {
			throw new RuntimeException("ERP资产编码查询错误, 请输入正确的ERP资产编码.");
		}
	}

	/**
	 * 批量导入信创终端设备 [ls临时]
	 */
	@PostMapping("/import/ls")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "批量导入信创终端设备 [ls临时]", notes = "feignCmdbCientitySearch")
	public R<Boolean> importByExcel(MultipartFile file
		, @ApiParam(value = "设备类型名称", required = false) String deviceTypeName
		, @ApiParam(value = "I6000属性字段", required = false) String attrCode) {
		String filename = file.getOriginalFilename();
		if (org.springframework.util.StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!org.springframework.util.StringUtils.endsWithIgnoreCase(filename, ".xls") && !org.springframework.util.StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		Boolean returnBL = hardwareBasicService.importByExcel(file, deviceTypeName, attrCode);
		return R.data(returnBL);
	}

	/**
	 * 批量填充信创终端设备 [ls临时]
	 */
	@PostMapping("/import/ls/update")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "批量导入信创终端设备 [ls临时]", notes = "feignCmdbCientitySearch")
	public R<Boolean> xcUpdate(@RequestBody List<String> assetCodeErpList, String attrCode) {
		Boolean returnBL = hardwareBasicService.xcUpdate(assetCodeErpList, attrCode);
		return R.data(returnBL);
	}

	/**
	 * 设备出库记录
	 */
	@GetMapping("/cientity/detail/warehouse")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "设备详情页出入库记录", notes = "传入设备编码")
	public R<List<WarehouseDetailDTO>> warehouse(String deviceCode) {
		return R.data(hardwareBasicService.warehouse(deviceCode));
	}

	/**
	 * 设备申请记录
	 */
	@GetMapping("/cientity/detail/apply")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "设备详情页设备申请记录", notes = "传入设备编码")
	public R<Object> apply(String deviceCode) {
		return R.data(hardwareBasicService.apply(deviceCode));
	}

	/**
	 * 设备投运记录
	 */
	@GetMapping("/cientity/detail/operation")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "设备详情页设备投运记录", notes = "传入设备编码")
	public R<Object> operation(String deviceCode) {
		return R.data(hardwareBasicService.operation(deviceCode));
	}

	/**
	 * 设备变更记录
	 */
	@GetMapping("/cientity/detail/change")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "设备详情页设备变更记录", notes = "传入设备编码")
	public R<Object> change(String deviceCode) {
		return R.data(hardwareBasicService.change(deviceCode));
	}

	/**
	 * 设备报修记录
	 */
	@GetMapping("/cientity/detail/repair")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "设备详情页设备报修记录", notes = "传入设备编码")
	public R<Object> repair(String deviceCode) {
		return R.data(hardwareBasicService.repair(deviceCode));
	}


	@NotNull
	private static List<CiCientitySearchVO> getCiCientitySearchVOS(HardwareBasicQueryVO hardwareBasicQueryVO) {
		List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(hardwareBasicQueryVO);
		ciCientitySearchVOS.forEach(item -> {
			if (item.getAttrName().equals(CmdbAttrConstant.OWNER_UNIT)) {
				item.setExpression(Expression.EQUAL);
			}
			if (item.getAttrName().equals(CmdbAttrConstant.OWNER_UNIT_CODE)) {
				item.setExpression(Expression.EQUAL);
			}
			if (item.getAttrName().equals(CmdbAttrConstant.DEVICE_TYPE_CODE) && item.getAttrValue().toString().contains(",")) {
				item.setExpression(Expression.EQUAL);
			}
			if (item.getAttrName().equals(CmdbAttrConstant.DEVICE_CATEGORY_CODE) && item.getAttrValue().toString().contains(",")) {
				item.setExpression(Expression.LIKE);
			}
			if (StringUtils.equals(CmdbAttrConstant.IP_0, item.getAttrName())) {
				item.setAttrName(CmdbAttrConstant.IP);
			}
			if (StringUtils.equals(CmdbAttrConstant.MAC_0, item.getAttrName())) {
				item.setAttrName(CmdbAttrConstant.MAC);
			}
			if (hardwareBasicQueryVO.getArea().length() == 4 && item.getAttrName().equals(CmdbAttrConstant.AREA)) {
				item.setExpression(Expression.EQUAL);
			}
		});
		return ciCientitySearchVOS;
	}


}

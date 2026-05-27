package com.lnsoft.device.api.asset.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.DeviceCmdbDTO;
import com.lnsoft.device.api.asset.dto.ExportRacks;
import com.lnsoft.device.api.asset.dto.ResourceRacksDTO;
import com.lnsoft.device.api.asset.entity.ResourceRacks;
import com.lnsoft.device.api.asset.vo.ResourceRacksVO;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.res.service.IResourceRacksService;
import com.lnsoft.device.api.warehouse.vo.DeviceResourceVo;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.vo.CiCientitySearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 空间资源管理机架表 控制器
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/resource/racks")
@Api(value = "空间资源管理机架表", tags = "空间资源管理机架表接口")
public class ResourceRacksController extends IdevelopController {
	private ICmdbService cmdbService;

	private IResourceRacksService resourceRacksService;
	private ILogOptService logOptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入resourceRacks")
	public R<ResourceRacks> detail(ResourceRacks resourceRacks) {
		ResourceRacks detail = resourceRacksService.getOne(Condition.getQueryWrapper(resourceRacks));
		return R.data(detail);
	}

	/**
	 * 分页 空间资源管理机架表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入resourceRacks")
	public R<IPage<ResourceRacks>> list(ResourceRacks resourceRacks, Query query) {
		IPage<ResourceRacks> pages = resourceRacksService.page(Condition.getPage(query), Condition.getQueryWrapper(resourceRacks));
		return R.data(pages);
	}

	/**
	 * 自定义分页 空间资源管理机架表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入resourceRacks")
	public R<IPage<ResourceRacksVO>> page(ResourceRacksVO resourceRacks, Query query) {
		IPage<ResourceRacksVO> page = Condition.getPage(query);
		IPage<ResourceRacksVO> pages = page.setRecords(resourceRacksService.selectResourceRacksPage(page, resourceRacks));
		return R.data(pages);
	}

	/**
	 * 新增 空间资源管理机架表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入resourceRacks")
	public R save(@Valid @RequestBody ResourceRacks resourceRacks) {
		resourceRacks.setType("racks");
		return R.status(resourceRacksService.save(resourceRacks));
	}

	/**
	 * 修改 空间资源管理机架表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入resourceRacks")
	public R update(@Valid @RequestBody ResourceRacks resourceRacks) {
		return R.status(resourceRacksService.updateById(resourceRacks));
	}

	/**
	 * 新增或修改 空间资源管理机架表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入resourceRacks")
	public R submit(@Valid @RequestBody ResourceRacks resourceRacks) {
		IdevelopUser user = SecureUtil.getUser();
		resourceRacks.setType("racks");
		resourceRacks.setCreateDept(user.getDeptId());
		if (resourceRacksService.saveOrUpdate(resourceRacks)){
			return R.data(resourceRacks,"操作成功");
		}
		return R.fail("操作失败");
	}


	/**
	 * 删除 空间资源管理机架表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

		List<String> idList = Func.toStrList(ids);
		for (String id : idList) {
			Query query = new Query();
			DeviceResourceVo deviceResourceVo = new DeviceResourceVo();
			deviceResourceVo.setRackCode(id);
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(deviceResourceVo);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (jsonObject.getData().size()!=0 && jsonObject.getData()!=null){
				return R.fail("存在下级关联设备，禁止删除");
			}
		}
		return R.status(resourceRacksService.deleteLogic(Func.toLongList(ids)));
	}
	/**
	 * 机柜数据导出
	 */
	@PostMapping("exportExcel")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "机架导出", notes = "")
	public void export(@RequestBody ResourceRacksDTO resourceRacksDTO, HttpServletResponse servletResponse) {
		resourceRacksService.export(resourceRacksDTO,servletResponse);
	}
	/**
	 * 机架模板下载
	 */
	@PostMapping("downExcel")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "机架模板下载", notes = "")
	public void down(HttpServletResponse response) {
		try {
			ArrayList<ExportRacks> exportRacksList = new ArrayList<>();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机架列表模板", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), ExportRacks.class).sheet("机架列表").doWrite(exportRacksList);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	/**
	 * 根据条件分页查询设备 空间资源
	 */
	@GetMapping("/device/list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "根据条件分页查询 空间资源", notes = "deviceResourceVo")
	public R<FeignCiCientity> ResourceDeviceList(DeviceCmdbDTO deviceResourceVo, Query query) {

		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(deviceResourceVo.getCabinetCode()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS.add(searchVO2);
		// 设备分类
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceCategoryCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(deviceResourceVo.getDeviceCategoryCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 设备类型
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceTypeCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_TYPE_CODE).attrValue(deviceResourceVo.getDeviceTypeCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 设备编码
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).attrValue(deviceResourceVo.getDeviceCode()).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 品牌
		if (StringUtil.isNotBlank(deviceResourceVo.getBrandCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.BRAND_CODE).attrValue(deviceResourceVo.getBrandCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 系列
		if (StringUtil.isNotBlank(deviceResourceVo.getSeriesCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.SERIES_CODE).attrValue(deviceResourceVo.getSeriesCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 型号
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceModelCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_MODEL_CODE).attrValue(deviceResourceVo.getDeviceModelCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}// 设备来源
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceSourceCode())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_SOURCE_CODE).attrValue(deviceResourceVo.getDeviceSourceCode()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 设备高度
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceHeight())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_HEIGHT).attrValue(deviceResourceVo.getDeviceHeight()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}
		// 设备起始高度
		if (StringUtil.isNotBlank(deviceResourceVo.getDeviceHeightBegin())){
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_HEIGHT_BEGIN).attrValue(deviceResourceVo.getDeviceHeightBegin()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO1);
		}

		try {
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 详情 列转行
	 */
	@GetMapping("/cientity/detail")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "详情 列转行", notes = "传入feignCmdbCientityGet")
	public R<Map<String, Object>> cientityDetail(FeignCmdbCientityGet feignCmdbCientityGet) {
		try {
			Map<String, Object> jsonObject = cmdbService.getCientityDetail(feignCmdbCientityGet);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



}

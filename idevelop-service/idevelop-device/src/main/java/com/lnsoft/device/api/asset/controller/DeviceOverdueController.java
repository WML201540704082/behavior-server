package com.lnsoft.device.api.asset.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.BeOverdueAssetsDTO;
import com.lnsoft.device.api.asset.dto.BecomeDueAssetsDTO;
import com.lnsoft.device.api.asset.dto.OverdueAssetSearchListDTO;
import com.lnsoft.device.api.asset.service.IDeviceOverdueService;
import com.lnsoft.device.api.asset.task.DeviceAssetTask;
import com.lnsoft.device.api.asset.vo.BeOverdueAssetsVo;
import com.lnsoft.device.api.asset.vo.BecomeDueAssetsVo;
import com.lnsoft.device.api.asset.vo.OverdueAssetVO;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.task.CmdbTask;
import com.lnsoft.device.vo.CiCientitySearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产台账 逾期资产-转资到期 接口
 *
 * @author Idevelop
 * @since 2024-03-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/overdue")
@Api(value = "资产逾期资产-转资到期", tags = "逾期资产-转资到期")
public class DeviceOverdueController extends IdevelopController {


	private ICmdbService cmdbService;
	private CmdbTask cmdbTask;
	private IDeviceOverdueService deviceOverdueService;
	private DeviceAssetTask deviceAssetTask;
	private CmdbCientityProperties cmdbCientityProperties;

	/**
	 * 根据条件分页查询 资产台账 逾期资产
	 */
	@GetMapping("/overdue/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据条件分页查询 资产台账", notes = "传入BeOverdueAssetsVo")
	public R<FeignCiCientity> overdueList(BeOverdueAssetsVo beOverdueVo, Query query) {
		FeignCiCientity jsonObject =  deviceOverdueService.getList(beOverdueVo,query);
		return R.data(jsonObject);
	}

	/**
	 * 根据条件分页查询 资产台账 转资
	 */
	@GetMapping("/becomeDue/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据条件分页查询 资产台账", notes = "传入BecomeDueAssetsVo")
	public R<FeignCiCientity> overdueList(BecomeDueAssetsVo becomeDue, Query query) {
		try {
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(becomeDue);
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.ERP_TRANSFER_STATUS).attrValue(cmdbCientityProperties.getErpTransferStatus1()).expression(Expression.NOTLIKE).build();
			ciCientitySearchVOS.add(searchVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			return R.data(jsonObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据条件分页导出 资产台账 转资
	 */
	@SneakyThrows
	@PostMapping("/becomeDue/export")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据条件导出 资产台账", notes = "传入BecomeDueAssetsVo")
	public void becomeDueExport(BecomeDueAssetsVo becomeDue, Query query, HttpServletResponse response) {
		try {
			//分页获取全部设备
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(becomeDue);
			FeignCiCientity results = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			List<BecomeDueAssetsDTO> dataList = new ArrayList<>();
			if (results != null) {
				results.getData().forEach(s -> {
					BecomeDueAssetsDTO becomeDueAssetsDTO = new BecomeDueAssetsDTO();
					becomeDueAssetsDTO = JSON.parseObject(JSON.toJSONString(s), BecomeDueAssetsDTO.class);
					dataList.add(becomeDueAssetsDTO);
				});
			}
			try {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding(StandardCharsets.UTF_8.name());
				String fileName = URLEncoder.encode("转资到期设备列表导出", StandardCharsets.UTF_8.name());
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				EasyExcel.write(response.getOutputStream(), BecomeDueAssetsDTO.class).sheet("入库管理表").doWrite(dataList);
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 根据条件导出 资产台账 逾期资产
	 */
	@SneakyThrows
	@PostMapping("/overdue/export")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据条件导出 资产台账", notes = "传入BecomeDueAssetsVo")
	public void overdueExport(BeOverdueAssetsDTO becomeDue, Query query, HttpServletResponse response) {
		try {
			//分页获取全部设备
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(becomeDue);
			FeignCiCientity results = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			List<BeOverdueAssetsDTO> dataList = new ArrayList<>();
			if (results != null) {
				results.getData().forEach(s -> {
					BeOverdueAssetsDTO assetsDTO = new BeOverdueAssetsDTO();
					assetsDTO = JSON.parseObject(JSON.toJSONString(s), BeOverdueAssetsDTO.class);
					dataList.add(assetsDTO);
				});
			}
			try {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding(StandardCharsets.UTF_8.name());
				String fileName = URLEncoder.encode("逾期资产设备列表导出", StandardCharsets.UTF_8.name());
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				EasyExcel.write(response.getOutputStream(), BeOverdueAssetsDTO.class).sheet("逾期资产表").doWrite(dataList);
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 刷新投运年限
	 */
	@GetMapping("/refresh/useage")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "刷新投运年限")
	public R<Boolean> refreshUseAge() {
		try {
			cmdbTask.refreshUseAge();
			return R.data(Boolean.TRUE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 刷新资产统计
	 */
	@GetMapping("/refresh/asset")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "刷新资产统计")
	public R<Boolean> refreshAsset() {
		try {
			deviceAssetTask.updateAsset();
			return R.data(Boolean.TRUE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 老旧设备分类统计
	 */
	@GetMapping("/statistics")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "老旧设备分类统计", notes = "")
	public R overdueDeviceStatistics(OverdueAssetSearchListDTO overdueAssetSearchListDTO) {
		List<OverdueAssetVO> list = deviceOverdueService.overdueDeviceStatistics(overdueAssetSearchListDTO);
		return R.data(list);
	}
	/**
	 * 老旧设备投运年限分布
	 */
	@GetMapping("/age/statistics")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "老旧设备投运年限分布",notes = "传入OverdueAssetVO")
	public R oldDeviceAgeStatistics(OverdueAssetVO overdueAssetVO){
		List<OverdueAssetVO> list =deviceOverdueService.oldDeviceAgeStatistics(overdueAssetVO);
		return R.data(list);
	}
}

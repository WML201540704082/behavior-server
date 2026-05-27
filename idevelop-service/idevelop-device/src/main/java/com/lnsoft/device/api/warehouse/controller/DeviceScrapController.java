package com.lnsoft.device.api.warehouse.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.BeOverdueAssetsDTO;
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.entity.ErpPersonAuth;
import com.lnsoft.device.api.erp.response.ErpPersonAuthResp;
import com.lnsoft.device.api.erp.service.IErpKostlService;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.warehouse.dto.DeviceScrapDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceScrapListDTO;
import com.lnsoft.device.api.warehouse.dto.ScrapErpDto;
import com.lnsoft.device.api.warehouse.entity.DeviceScrap;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapService;
import com.lnsoft.device.api.warehouse.vo.DeviceScrapVO;
import com.lnsoft.device.utils.ScrapDeviceListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 设备报废 控制器
 *
 * @author cwb
 * @since 2024-03-18
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/device/scrap")
@Api(value = "设备报废", tags = "设备报废接口")
public class DeviceScrapController extends IdevelopController {

	@Autowired
	private IDeviceScrapService deviceScrapService;
	@Autowired
	private IErpService erpService;
	@Autowired
	private IErpKostlService erpKostlService;

	/**
	 * 详情
	 */
	@ApiLog("设备报废-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceScrap")
	public R<DeviceScrapVO> detail(DeviceScrapDTO dto) {
		if (Objects.isNull(dto.getId())) {
			return R.fail("请选择要查看的设备报废");
		}
		return deviceScrapService.detail(dto);
	}

	/**
	 * 分页 设备报废
	 */
	@ApiLog("设备报废-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入deviceScrap")
	public R<IPage<DeviceScrap>> list(DeviceScrapDTO dto, Query query) {
		IPage<DeviceScrap> pages = deviceScrapService.scrapList(dto, query);
		return R.data(pages);
	}

	/**
	 * 保存 设备报废
	 */
	@ApiLog("设备报废-保存")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "保存", notes = "传入deviceScrap")
	public R<DeviceScrapVO> save(@Valid @RequestBody DeviceScrapDTO dto) {
		return deviceScrapService.saveScrap(dto);
	}

	/**
	 * 删除 设备报废
	 */
	@ApiLog("设备报废-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestBody Map<String, String> ids) {
		return deviceScrapService.removeDeviceScraps(ids.get("ids"));
	}

	/**
	 * 提交报废
	 */
	@ApiLog("设备报废-提交报废")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "报废", notes = "传入deviceScrapDTO")
	public R<DeviceScrapVO> submit(@Valid @RequestBody DeviceScrapDTO dto, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return R.fail(result.getAllErrors().get(0).getDefaultMessage());
		}
		return deviceScrapService.deviceScrapSubmit(dto);
	}

	@ApiLog("设备报废-获取单据人信息")
	@GetMapping("/getItemResp")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取单据人信息", notes = "传入useKeepDeptCode")
	public R<List<ErpPersonAuthResp.ItemResp>> getItemResp(@RequestParam("useKeepDeptCode") String useKeepDeptCode, @RequestParam("type") Integer type) {
		ErpPersonAuth erpPersonAuth = new ErpPersonAuth();
		erpPersonAuth.setKostl(useKeepDeptCode);
		ErpKostl erpKostl = erpKostlService.getOne(new LambdaQueryWrapper<ErpKostl>().eq(ErpKostl::getKostl, useKeepDeptCode));
		if (Objects.isNull(erpKostl)) {
			throw new RuntimeException("未查询到对应使用保管部门,请确认数据!");
		}
		String nodeId = null;
		if (type == 0) {
			nodeId = erpKostl.getSwerk().startsWith("00") ? "10" : "11";
		} else {
			nodeId = "26";
		}
		erpPersonAuth.setNodeId(nodeId);
		ErpPersonAuthResp erpPersonAuthResp = erpService.personAuth(erpPersonAuth);
		return R.data(erpPersonAuthResp.getItemResp());
	}

	/**
	 * 设备报废erp回调接口
	 */
	@ApiLog("设备报废-设备报废erp回调接口")
	@PostMapping("/scrapErpReturn")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "erp报废回调")
	public R deviceScrapErpReturn(@Valid @RequestBody ScrapErpDto dtoList) throws Exception {
		return deviceScrapService.deviceScrapErpReturn(dtoList);
	}

	/**
	 * 工作台获取设备报废列表
	 */
	@ApiLog("设备报废-工作台获取设备报废列表")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "工作台获取设备报废列表")
	public R<IPage<DeviceScrapVO>> deskDeviceScrapList(DeviceScrapDTO dto, Query query) {
		return deviceScrapService.deskDeviceScrapList(dto, query);
	}

	/**
	 * 更新设备报废工单状态
	 */
	@ApiLog("设备报废-更新设备报废工单状态")
	@PostMapping("/edit/status")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "更新设备报废工单状态")
	public R<Integer> deskDeviceScarpStatus(@RequestBody DeviceScrapDTO dto) throws Exception {
		return deviceScrapService.deskDeviceScrapStatus(dto);
	}

	/**
	 * 根据条件导出
	 */
	@ApiLog("设备报废-根据条件导出")
	@SneakyThrows
	@PostMapping("/export")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据条件导出", notes = "传入list")
	public void overdueExport(@RequestBody List<DeviceScrapListDTO> scrapListDTOS, HttpServletResponse response) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("报废设备列表", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), BeOverdueAssetsDTO.class).sheet("报废设备列表").doWrite(scrapListDTOS);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 导入设备
	 */
	@ApiLog("设备报废-导入设备")
	@PostMapping("import")
	@ApiOperation(value = "导入设备", notes = "传入excel")
	public R importUser(MultipartFile file) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new RuntimeException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new RuntimeException("请上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		List<DeviceScrapListDTO> list = new ArrayList<>();
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			EasyExcel.read(inputStream, DeviceScrapListDTO.class, new ScrapDeviceListener(list)).sheet().doRead();
		} catch (Exception e) {
			log.error("读取流失败");
		}finally {
			try {
				if (inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				log.error("关闭流失败");
			}
		}
		return R.data(list);
	}
	/**
	 * 校验
	 */
	@ApiLog("设备报废-校验")
	@PostMapping("/check")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "校验", notes = "传入deviceScrap")
	public R check(@Valid @RequestBody DeviceScrapDTO dto) {
		return deviceScrapService.check(dto);
	}
}

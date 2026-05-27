package com.lnsoft.device.api.warehouse.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDeviceDTO;
import com.lnsoft.device.api.warehouse.dto.ErpDeviceOrderDTO;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferService;
import com.lnsoft.device.api.warehouse.vo.DeviceTransferDeviceVO;
import com.lnsoft.device.api.warehouse.vo.DeviceTransferVO;
import com.lnsoft.device.utils.TransferDeviceListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
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
import java.util.Objects;

/**
 * 设备转资 控制器
 *
 * @author Idevelop
 * @since 2024-02-27
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/device/transfer")
@Api(value = "设备转资", tags = "设备转资接口")
public class DeviceTransferController extends IdevelopController {

	private IDeviceTransferService deviceTransferService;

	/**
	 * 详情
	 */
	@ApiLog("设备转资-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceTransfer")
	public R<DeviceTransferVO> detail(DeviceTransferDTO deviceTransferDTO) {
		if (Objects.isNull(deviceTransferDTO.getId())) {
			return R.fail("请选择要查看的设备转资");
		}
		return deviceTransferService.detail(deviceTransferDTO);
	}

	/**
	 * 分页 设备转资
	 */
	@ApiLog("设备转资-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入deviceTransfer")
	public R<IPage<DeviceTransferVO>> list(DeviceTransferDTO deviceTransferDTO, Query query) {
		return deviceTransferService.deviceTransferList(deviceTransferDTO, query);
	}

	/**
	 * 发起设备转资
	 */
	@ApiLog("设备转资-发起设备转资")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "转资", notes = "传入deviceTransfer")
	public R<DeviceTransferVO> save(@Valid @RequestBody DeviceTransferDTO deviceTransferDTO) throws Exception {
		return deviceTransferService.insert(deviceTransferDTO);
	}

	/**
	 * 新增或修改 设备转资暂存
	 */
	@ApiLog("设备转资-发起设备转资")
	@PostMapping("/新增或修改")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入deviceTransfer")
	public R<DeviceTransferVO> submit(@Valid @RequestBody DeviceTransferDTO deviceTransferDTO) throws Exception {
		return deviceTransferService.temporarily(deviceTransferDTO);
	}

	/**
	 * 删除 设备转资
	 */
	@ApiLog("设备转资-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Integer> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return deviceTransferService.deleteDeviceTransfer(ids);
	}

	/**
	 * 获取当前登录人
	 */
	@ApiLog("设备转资-获取当前登录人")
	@GetMapping("/name")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取当前登录人")
	public R<String> getLoginUser() {
		return deviceTransferService.getLoginUser();
	}

	// todo erp回调接口
	// todo i6000回调接口


	/**
	 * 根据条件导出
	 */
	@ApiLog("设备转资-根据条件导出")
	@SneakyThrows
	@PostMapping("/export")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据条件导出", notes = "传入list")
	public void overdueExport(@RequestBody DeviceTransferDeviceVO deviceTransferDeviceVO, HttpServletResponse response) {
		try {
			List<DeviceTransferDeviceDTO> dataList = new ArrayList<>();
			dataList = deviceTransferDeviceVO.getList();
			try {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding(StandardCharsets.UTF_8.name());
				String fileName = URLEncoder.encode("转资设备列表", StandardCharsets.UTF_8.name());
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				EasyExcel.write(response.getOutputStream(), DeviceTransferDeviceDTO.class).sheet("转资设备列表").doWrite(dataList);
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 导入设备
	 */
	@ApiLog("设备转资-导入设备")
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
		List<DeviceTransferDeviceDTO> list = new ArrayList<>();
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			EasyExcel.read(inputStream, DeviceTransferDeviceDTO.class, new TransferDeviceListener(list)).sheet().doRead();
		} catch (Exception e) {
			log.error("读取流失败");
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("流关闭失败");
				}
			}
		}
		return R.data(list);
	}

	/**
	 * 更新设备转资工单状态
	 */
	@ApiLog("设备转资-更新设备转资工单状态")
	@PostMapping("/edit/status")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "更新设备转资工单状态")
	public R<Integer> deskDeviceTransferStatus(@RequestBody DeviceTransferDTO dto) throws Exception {
		/*if (Objects.isNull(dto.getWorkerStatus()) || StringUtils.isEmpty(dto.getTaskDefinitionKey())) {
			return R.fail("数据异常！");
		}*/
		return deviceTransferService.deskDeviceTransferStatus(dto);
	}

	/**
	 * 工作台获取设备转资列表
	 */
	@ApiLog("设备转资-工作台获取设备转资列表")
	@GetMapping("/desk/list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "工作台获取设备转资列表")
	public R<IPage<DeviceTransferVO>> deskDeviceTransferList(DeviceTransferDTO dto, Query query) {
		return deviceTransferService.deskDeviceTransferList(dto, query);
	}

	/**
	 * ERP审核回传接口
	 */
	@ApiLog("设备转资-ERP审核回传接口")
	@PostMapping("/erp/transfer")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "ERP回调测试接口")
	public R<Integer> erpDeviceTransfer(@RequestBody ErpDeviceOrderDTO erpDeviceOrderDTO) throws Exception {
		return deviceTransferService.erpDeviceTransfer(erpDeviceOrderDTO);
	}
}

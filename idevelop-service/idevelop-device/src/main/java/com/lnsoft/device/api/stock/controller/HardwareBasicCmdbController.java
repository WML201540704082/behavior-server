package com.lnsoft.device.api.stock.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.service.IDeviceWorkBenchService;
import com.lnsoft.device.api.stock.dto.HardwareBasicCmdbQueryDTO;
import com.lnsoft.device.api.stock.service.FileImportInfoService;
import com.lnsoft.device.api.stock.service.FileInfoService;
import com.lnsoft.device.api.stock.service.IHardwareBasicCmdbService;
import com.lnsoft.device.api.stock.vo.DownloadInfoVo;
import com.lnsoft.device.api.stock.vo.ErrorListVo;
import com.lnsoft.device.api.stock.vo.HardwareBasicCmdbDeviceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/hardwarebasic/cmdb")
@Api(value = "资产台账治理", tags = "资产台账治理接口")
public class HardwareBasicCmdbController extends IdevelopController {

	private IHardwareBasicCmdbService hardwareBasicCmdbService;

	private FileInfoService fileInfoService;

	private FileImportInfoService importInfoService;

	/**
	 * 父文件列表&子文件详情-（对应点击刷新） -新版
	 */
	@PostMapping("/error-list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "异常列表", notes = "异常列表")
	public R errorList(@RequestBody ErrorListVo errorListVo) {
		return hardwareBasicCmdbService.errorList(errorListVo);
	}

	/**
	 * 数据治理，根据设备分类下载模板
	 */
	@PostMapping("/download")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "模板下载", notes = "传入设备分类编码")
	public void downloadTemplate(@ApiParam(value = "设备分类编码", required = false) @RequestBody HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery, HttpServletResponse response) {
		hardwareBasicCmdbService.downloadTemplate(hardwareBasicCmdbQuery, response);
	}

	/**
	 * 添加下载次数
	 */
	@GetMapping("/add-nums")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "添加下载次数", notes = "添加下载次数")
	public R addNums(@ApiParam(value = "记录id", required = true) String id) {
		return hardwareBasicCmdbService.addNums(id);
	}

	/**
	 * 统计未下载次数
	 */
	@GetMapping("/all-nums")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "添加下载次数", notes = "添加下载次数")
	public R allNums(String userId) {
		return hardwareBasicCmdbService.allNums(userId);
	}

	/**
	 * 数据治理，根据设备分类下载模板
	 */
	@PostMapping("/download-error")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "导出异常数据下载", notes = "导出异常数据")
	public void downloadError(@RequestBody HardwareBasicCmdbDeviceVO deviceVO, HttpServletResponse response) {
		hardwareBasicCmdbService.downloadError(deviceVO, response);
	}

	/**
	 * 数据下载记录分页列表查询
	 */
	@PostMapping("/download-info")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = " 数据下载记录", notes = " 数据下载记录")
	public R downloadInfo(@RequestBody DownloadInfoVo vo) {
		return fileInfoService.findList(vo);
	}

	@PostMapping("/download-delete")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = " 数据删除记录", notes = " 数据删除记录")
	public R downloadDelete(@RequestBody List<String> list) {
		return fileInfoService.downloadDelete(list);
	}

	@Autowired
	private IDeviceWorkBenchService deviceWorkBenchService;
	/**
	 * excel导入并回显数据
	 */
	@PostMapping("/import")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public R importByExcel(MultipartFile file, @ApiParam(value = "设备分类编码", required = false) String deviceCategory,String isMath) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx")) && !StringUtils.endsWithIgnoreCase(filename, ".xlsm")) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		//数据回显
		return R.data(hardwareBasicCmdbService.importByExcel(file, deviceCategory,isMath));
	}

	/**
	 * 批量保存  修改台站数据
	 */
	@PostMapping("/batchSave")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "批量保存", notes = "hardwareBasicCmdbDeviceVO")
	public R batchSave(@Valid @RequestBody HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO) {
		return R.success(hardwareBasicCmdbService.customSaveBatch(hardwareBasicCmdbDeviceVO));
	}

	/**
	 * 解析-新版
	 */
	@PostMapping("/resolver")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "解析", notes = "传入excel")
	public R resolver(@ApiParam(value = "文件id", required = true) String fileId) {
		if (StringUtils.isEmpty(fileId)) {
			throw new ServiceException("请上传文件!");
		}
		return hardwareBasicCmdbService.resolver(fileId);
	}


	/**
	 * excel 上传文件 --新版
	 */
	@PostMapping("/import-excel-new")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public R importByExcelNew(MultipartFile file, @ApiParam(value = "设备分类编码", required = false) String deviceCategory, String userId) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		//数据回显
		return R.data(hardwareBasicCmdbService.importByExcelNew(file, deviceCategory, userId));
	}

	/**
	 * excel 新版异常信息下载
	 */
	@PostMapping("/import/download/new")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public void downloadNew(@ApiParam(value = "文件id", required = true) String fileId, HttpServletResponse response) {
		hardwareBasicCmdbService.downloadNew(fileId, response);
	}


	/**
	 * excel导入-直接存库-新版
	 */
	@PostMapping("/import-new")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public R importNew(MultipartFile file, @ApiParam(value = "设备分类编码", required = false) String deviceCategory, String userId) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new ServiceException("请-上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		//异步处理
		hardwareBasicCmdbService.importNew(file, deviceCategory, userId);
		return R.success("success");
	}

	/**
	 * excel导入并回显数据 下载
	 */
	@PostMapping("/import/download")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public void importByExcel(@Valid @RequestBody HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO, HttpServletResponse response) {
		hardwareBasicCmdbService.importByExceldownload(hardwareBasicCmdbDeviceVO, response);
	}


	@GetMapping("/getDeviceTypeRemark")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "获取T10101")
	public R getDeviceTypeRemark() {
		return R.data(hardwareBasicCmdbService.getDeviceTypeRemark());
	}

	@PostMapping("/cmdbDataCheck")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "根据cmdb 检查设备数据是否符合规范", notes = "检查设备数据是否符合规范")
	public R cmdbDataCheck(@Valid @RequestBody HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO) {
		Map<String, Object> result = new HashMap<>();
		StringBuilder exceptionField = new StringBuilder();
		List<Map<String, Object>> records = new ArrayList<>();
		//接受数据
		String deviceCategory = hardwareBasicCmdbDeviceVO.getDeviceCategory();
		List<Map<String, Object>> list = hardwareBasicCmdbDeviceVO.getRecords();
		if (StringUtil.isBlank(deviceCategory)) {
			return R.fail("设备分类编码不能为空！");
		}
		//循环校验数据
		int i = 0;
		for (Map<String, Object> map : list) {
			Map<String, Object> cmdbDataCheck = hardwareBasicCmdbService.cmdbDataCheckForUpdate(map, deviceCategory);
			if (cmdbDataCheck.get("exceptionField") != null) {
				exceptionField.append("[设备数据-" + (i + 1) + "校验：]" + cmdbDataCheck.get("exceptionField"));
			}
			records.add((Map) cmdbDataCheck.get("data"));
			i++;
		}
		result.put("records", records);
		result.put("exceptionField", exceptionField);
		return R.data(result);
	}

	@GetMapping("/getInfoByIP")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "根据IP地址获取网络设备属性")
	public R getInfoByIP(@RequestParam(value = "ip", required = true) String ip) {
		return R.data(hardwareBasicCmdbService.getInfoByIP(ip));
	}
}

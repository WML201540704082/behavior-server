package com.lnsoft.device.api.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.annotation.ApiLog;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageExportSO;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageSO;
import com.lnsoft.device.api.warehouse.dto.GetFullNameDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceStorage;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageList;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageService;
import com.lnsoft.device.props.CheckProperties;
import com.lnsoft.device.props.CmdbCientityProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


/**
 * 设备入库表 控制器
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/storage")
@Api(value = "设备入库表", tags = "设备入库主表接口")
public class DeviceStorageController extends IdevelopController {

	private IDeviceStorageService deviceStorageService;
	@Resource
	private CheckProperties checkProperties;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;

	/**
	 * 详情
	 */
	@ApiLog("设备入库列表-详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入deviceStorage中的id")
	public R<DeviceStorage> detail(DeviceStorage deviceStorage) {
		DeviceStorage detail = deviceStorageService.getOne(Condition.getQueryWrapper(deviceStorage));
		return R.data(detail);
	}

	/**
	 * 分页 设备入库表
	 */
	@ApiLog("设备入库列表-分页")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页列表", notes = "传入deviceStorage")
	public R<IPage<DeviceStorage>> list(DeviceStorageSO deviceStorage, Query query) {
		IPage<DeviceStorage> pages = deviceStorageService.selectDeviceStorage(deviceStorage, query);
		return R.data(pages);
	}


	/**
	 * 新增 设备入库表
	 */
	@ApiLog("设备入库列表-新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "设备入库", notes = "传入deviceStorage")
	public R save(@Valid @RequestBody DeviceStorageDTO deviceStorage) {
		List<DeviceStorageList> devices = deviceStorage.getDevices();
		if (StringUtils.equals(deviceStorage.getDeviceSource(),cmdbCientityProperties.getDeviceSource())){
			//统一纳管增加标准全称校验
			for (DeviceStorageList device : devices) {
				String fullName = device.getFullName();
				R check = nameCheck(fullName);
				if (!check.isSuccess()){
					return check;
				}
			}
		}
		return R.data(deviceStorageService.saveEntry(deviceStorage));
	}


	/**
	 * 删除 设备入库表
	 */
	@ApiLog("设备入库列表-删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deviceStorageService.delete(Func.toLongList(ids)));
	}


	/**
	 * 暂存
	 */
	@ApiLog("设备入库列表-暂存")
	@PostMapping("/tempSave")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "暂存", notes = "传入deviceStorage")
	public R tempSave(@Valid @RequestBody DeviceStorageDTO deviceStorage) {
		List<DeviceStorageList> devices = deviceStorage.getDevices();
		if (StringUtils.equals(deviceStorage.getDeviceSource(),cmdbCientityProperties.getDeviceSource())){
			//统一纳管增加标准全称校验
			for (DeviceStorageList device : devices) {
				String fullName = device.getFullName();
				R check = nameCheck(fullName);
				if (!check.isSuccess()){
					return check;
				}
			}
		}
		return R.data(deviceStorageService.tempSave(deviceStorage));
	}

	/**
	 * 导出
	 */
	@ApiLog("设备入库列表-导出")
	@PostMapping("/export")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "导出", notes = "传入ids或者查询条件")
	public void export(@RequestBody DeviceStorageExportSO so, HttpServletResponse response) {
		deviceStorageService.export(so, response);
	}

	@ApiLog("设备入库列表-手动刷新字典缓存")
	@GetMapping("/refreshCache")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "手动刷新字典缓存")
	public R refreshCache(String ciIds) {
		return R.data(deviceStorageService.refreshCache(Func.toLongList(ciIds)));
	}

	/**
	 * 用于数据治理生成标准全称 (Deprecated)
	 *
	 * @param dto
	 * @return
	 */
	@ApiLog("设备入库列表-用于数据治理生成标准全称")
	@PostMapping("/getFullName")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "根据条件获取标准全称", notes = "传入设备来源类型、WBS项目名称、设备类型编码")
	@Deprecated
	public R getFullName(@RequestBody GetFullNameDTO dto) {
		return R.data(deviceStorageService.getFullName(dto));
	}


	/**
	 * 名称校验规则（标准全称校验）
	 */
	private  R nameCheck(String name){
		//名称中包含关键词校验
		List<String> invalidKeywords = checkProperties.getInvalidKeywords();
		for (String invalidKeyword : invalidKeywords) {
			if (name.contains(invalidKeyword)){
				return R.fail("设备名称中包含特殊词语 ["+invalidKeyword+"]，请进行修改");
			}
		}
		//特殊符号校验
		List<String> invalidCharacters = checkProperties.getInvalidCharacters();
		for (String invalidCharacter : invalidCharacters) {
			if (name.contains(invalidCharacter)){
				return R.fail("设备名称中包含特殊符号 ["+invalidCharacter+"]，请进行修改");
			}
		}
		//名称以关键词结尾校验
		List<String> invalidEndings = checkProperties.getInvalidEndings();
		for (String invalidEnding : invalidEndings) {
			if (name.endsWith(invalidEnding)){
				return R.fail("设备名称以特殊名称 ["+invalidEnding+"]结尾，请进行修改");
			}
		}
		//包含特殊词汇校验
		List<String> invalidEqualWords = checkProperties.getInvalidEqualWords();
		for (String invalidEqualWord : invalidEqualWords) {
			if (name.contains(invalidEqualWord)){
				return R.fail("设备名称中包含特殊词语 ["+invalidEqualWord+"]，请进行修改");
			}
		}
		return R.success("校验成功");
	}

}

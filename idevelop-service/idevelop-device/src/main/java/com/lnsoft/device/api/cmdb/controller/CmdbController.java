package com.lnsoft.device.api.cmdb.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.I6000RequestDTO;
import com.lnsoft.device.api.asset.service.IHardwareBasicService;
import com.lnsoft.device.api.cmdb.dto.CmdbCardDTO;
import com.lnsoft.device.api.cmdb.dto.CmdbRestoreDTO;
import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.api.cmdb.excel.DeviceCodeListener;
import com.lnsoft.device.api.cmdb.mapper.HandlerDeviceMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.task.CmdbTask;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.lnsoft.device.constant.CmdbAttrConstant.*;

/**
 * CMDB修复接口 控制器
 *
 * @author xueli
 * @since 2024-04-18
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cmdb/repair")
@Api(value = "CMDB台账管理相关接口", tags = "CMDB台账管理相关接口")
public class CmdbController {

	private ICmdbService iCmdbService;
	private OrderNumberUtil orderNumberUtil;
	private CmdbTask cmdbTask;
	private HandlerDeviceMapper handlerDeviceMapper;
	private CmdbCientityProperties cmdbCientityProperties;
	private IHardwareBasicService hardwareBasicService;


	/**
	 * 新增资产台账 请勿使用 如果需要请复制这个
	 */
	@PostMapping("/add")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "新增资产台账")
	public R<Map<String, Object>> add(@RequestBody JSONObject jsonObject) {
		try {
			// 新增 资产台账
			Map<String, Map<String, Object>> hashMap = new HashMap<>();
			Long ciId = 0L;
			Map<String, Object> innerMap = jsonObject.getInnerMap();
			for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
				ciId = Long.valueOf(entry.getKey());
				List<Map<String, Object>> value = (List<Map<String, Object>>) entry.getValue();
				for (Map<String, Object> values : value) {
					hashMap.put(UuidUtils.uuid(), values);
				}
			}
			Map<String, Object> returnMap = iCmdbService.cientityBatchsave(ciId, hashMap, TransactionActionType.INSERT);
			return R.data(returnMap);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 修改资产台账 请勿使用 如果需要请复制这个
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "修改资产台账")
	public R<Map<String, Object>> update(@RequestBody JSONObject jsonObject) {

		// 修改 资产台账
		Map<Long, Map<String, Object>> hashMap = new HashMap<>();
		Map<String, Object> innerMap = jsonObject.getInnerMap();
		for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
			String id = entry.getKey();
			Map<String, Object> value = (Map<String, Object>) entry.getValue();
			hashMap.put(Long.valueOf(id), value);
		}
		Map<String, Object> returnMap = iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
		return R.data(returnMap);
	}

	/**
	 * 修改资产台账1 请勿使用 如果需要请复制这个
	 */
	@PostMapping("/update1")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "修改资产台账1")
	public R<Map<String, Object>> update1(@RequestBody JSONObject jsonObject) {

		// 修改 资产台账
		Map<Long, Map<String, Object>> hashMap = new HashMap<>();
		Map<String, Object> innerMap = jsonObject.getInnerMap();
		for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
			String id = entry.getKey();
			Map<String, Object> value = (Map<String, Object>) entry.getValue();
			hashMap.put(Long.valueOf(id), value);
		}
		Map<String, Object> returnMap = iCmdbService.cientityBatchupdate1(hashMap, TransactionActionType.UPDATE);
		return R.data(returnMap);
	}


	/**
	 * 查询配置项列表
	 */
	@PostMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询配置项列表")
	public R<FeignCiCientity> list(@RequestBody JSONObject jsonObject) {

		// 查询配置项列表
		Map<String, Object> innerMap = jsonObject.getInnerMap();

		CiCientitySearch cientitySearch = new CiCientitySearch();

		for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
			Map<String, Object> value = (Map<String, Object>) entry.getValue();
			List<CiCientitySearchVO> entity = new ArrayList<>();
			for (Map.Entry<String, Object> entryUp : value.entrySet()) {
				String keyUp = entryUp.getKey();
				Object valueUp = entryUp.getValue();

				CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder()
					.attrName(keyUp)
					.attrValue(valueUp)
					.expression(Expression.LIKE).build();

				entity.add(ciCientitySearchVO);
			}
			cientitySearch.setEntity(entity);
		}

		Query query = new Query();
		query.setCurrent(1);
		query.setSize(100);
		FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityListByCondition(cientitySearch);

		return R.data(ciCientityListByClaccify);
	}

	/**
	 * 修改还原存量台账到存量数据治理中
	 */
	@PostMapping("/restore/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改还原存量台账到存量数据治理中", notes = "传入 cmdbRestoreDTOList")
	public R<List<Map<String, Object>>> restoreUpdate(@RequestBody CmdbRestoreDTO cmdbRestore) throws InterruptedException{
		Query query = new Query();
		String deviceCode = cmdbRestore.getDeviceCode();
		String[] split = deviceCode.split(",");
		List<String> deviceCodeList = Arrays.asList(split);

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
		long startTime = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(deviceCodeList.size());

		List<String> error = new ArrayList<>();
		threadPoolExecutor.submit(() -> {
			for (int i = 0; i < deviceCodeList.size(); i++) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				try {
					String code = deviceCodeList.get(i);
					List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
					CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).attrValue(code).expression(Expression.EQUAL).build();
					ciCientitySearchVOS.add(searchVO);
					FeignCiCientity jsonObject = iCmdbService.getCiCientityList(ciCientitySearchVOS, query);
					if (jsonObject.getTotal() < 0 || jsonObject.getTotal() == 0) {
						error.add(code);
					} else {
						CmdbRestoreDTO cmdbRestoreDTO = new CmdbRestoreDTO();
						cmdbRestoreDTO.setCiId(Long.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.CI_ID).toString()));
						cmdbRestoreDTO.setId(Long.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.ID).toString()));
						cmdbRestoreDTO.setUUID(jsonObject.getData().get(0).get(CmdbAttrConstant.UUID).toString());

						Map<Long, Map<String, Object>> hashMap = new HashMap<>();
						Map<String, Object> value = new HashMap<>();
						value.put(CmdbAttrConstant.CI_ID, cmdbRestoreDTO.getCiId());
						value.put(CmdbAttrConstant.ID, cmdbRestoreDTO.getId());
						value.put(CmdbAttrConstant.UUID, cmdbRestoreDTO.getUUID());
						value.put(CmdbAttrConstant.IS_GOVERN, cmdbCientityProperties.getGovernNo());
						hashMap.put(cmdbRestoreDTO.getId(), value);

						iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
					}
				} catch (Exception e) {
					log.error("数据还原-数据还原时出现异常：{}", e.getMessage());
				} finally {
					countDownLatch.countDown();
				}
			}
		});


		long time = System.currentTimeMillis() - startTime;
		log.error("执行总时长:{}毫秒", time);
		countDownLatch.await();
		threadPoolExecutor.shutdown();

		if (!error.isEmpty()) {
			return R.fail("数据还原失败，未查询到设备编码为" + error + "的设备");
		}

		return R.data(new ArrayList<>());
	}

	/**
	 * 单条 新增资产台账(数据治理)
	 */
	@PostMapping("/stock/add")
	@ApiOperationSupport(order = 98)
	@ApiOperation(value = "新增资产台账(数据治理)")
	public R<Map<String, Object>> stockAdd(@RequestBody JSONObject jsonObject) {
		try {
			IdevelopUser user = SecureUtil.getUser();
			// 新增 资产台账
			Map<String, Map<String, Object>> hashMap = new HashMap<>();
			Map<String, Object> innerMap = jsonObject.getInnerMap();

			// 获取模型ID
			String deviceCategoryCode = (String) innerMap.get(DEVICE_CATEGORY_CODE);
			String deviceTypeCode = (String) innerMap.get(DEVICE_TYPE_CODE);
			if (StringUtils.isEmpty(deviceCategoryCode) || StringUtils.isEmpty(deviceTypeCode)) {
				return R.fail("请先选择设备分类和设备类型!");
			}
			// 设备编码
			String deviceCode = orderNumberUtil.generateCode(deviceTypeCode);
			innerMap.put(DEVICE_CODE, deviceCode);
			innerMap.put(AREA, user.getRegionCode());
			innerMap.put(DEPT, user.getDeptId());
			innerMap.put(OWNER_UNIT, jsonObject.get(RECEIVE_UNIT));
			innerMap.put(OWNER_UNIT_CODE, jsonObject.get(RECEIVE_UNIT_CODE));

			HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceClaccify(deviceCategoryCode).deviceType(deviceTypeCode).build();
			Long ciId = iCmdbService.getCiId(hardwareBasicTree);
			hashMap.put(UuidUtils.uuid(), innerMap);

			Map<String, Object> returnMap = iCmdbService.cientityBatchsave(ciId, hashMap, TransactionActionType.INSERT);
			return R.data(returnMap);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 单条 修改资产台账(数据治理)
	 */
	@PostMapping("/stock/update")
	@ApiOperationSupport(order = 99)
	@ApiOperation(value = "修改资产台账(数据治理)")
	public R<Map<String, Object>> stockUpdate(@RequestBody JSONObject jsonObject) {
		try {
			IdevelopUser user = SecureUtil.getUser();
			// 修改 资产台账
			Map<Long, Map<String, Object>> hashMap = new HashMap<>();
			Map<String, Object> innerMap = jsonObject.getInnerMap();
			String deviceCodes = "";
			for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
				Long id = Long.valueOf(entry.getKey());
				Map<String, Object> valueMap = (Map<String, Object>) entry.getValue();

				String deviceTypeCode = (String) valueMap.get(CmdbAttrConstant.DEVICE_TYPE_CODE);
				if (ObjectUtils.isEmpty(deviceTypeCode)) {
					throw new RuntimeException("设备类型不能为空!");
				}
				deviceCodes = String.valueOf(valueMap.get(DEVICE_CODE));
				// 查询ERP信息
				Object assetCodeErp = valueMap.get(ASSET_CODE_ERP);
				Object deviceSource = valueMap.get(DEVICE_SOURCE);
				if (!Objects.isNull(assetCodeErp) && StringUtils.equals("统一纳管", deviceSource.toString())) {
					I6000RequestDTO selectI6000Info = new I6000RequestDTO();
					selectI6000Info.setAssetCodeErp(String.valueOf(assetCodeErp));
					Map<String, Object> returnBL = hardwareBasicService.selectInfoByI6000(selectI6000Info);
					valueMap.putAll(returnBL);
				}

				// 是否治理
				if (valueMap.containsKey(IS_GOVERN)) {
					valueMap.put(GOVERN_TIME, LocalDate.now());
				}

				valueMap.put(AREA, user.getRegionCode());
				valueMap.put(DEPT, user.getDeptId());
				valueMap.put(OWNER_UNIT, valueMap.get(RECEIVE_UNIT));
				valueMap.put(OWNER_UNIT_CODE, valueMap.get(RECEIVE_UNIT_CODE));
				System.out.println("11111111111111111111111111111111");
				valueMap.remove(PROJECT_CODE);

				// 判断是否发生修改设备分类和设备类型问题.如果有 先删除,后新增.
				Boolean isCategoryType = (Boolean) valueMap.get(DeviceConstant.IS_CATEGORY_TYPE);
				if (isCategoryType) {
					String deviceCode = (String) valueMap.get(CmdbAttrConstant.DEVICE_CODE);

					// 查询cmdb数据
					List<CiCientitySearchVO> entityList = new ArrayList<>();
					CiCientitySearchVO ciCientitySearchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CODE).attrValue(deviceCode).expression(Expression.EQUAL).build();
					entityList.add(ciCientitySearchVO);
					Query query = new Query();
					query.setCurrent(1);
					query.setSize(2);
					FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityList(entityList, query);
					List<Map<String, Object>> dataOldList = ciCientityListByClaccify.getData();
					List<Map<String, Object>> dataOldOne = dataOldList.stream().filter(item -> item.get(CmdbAttrConstant.ID).equals(id)).collect(Collectors.toList());
					Map<String, Object> dataOldMap = dataOldOne.get(0);
					if (CollectionUtils.isEmpty(dataOldMap)) {
						throw new RuntimeException("修改数据治理设备台账失败, 请联系运维人员处理!");
					}

					// 删除cmdb数据
					Boolean delete = iCmdbService.cientityDelete(id, "用户数据治理手动删除数据.");
					if (delete) {
						// 新增数据
						HardwareBasicTree hardwareBasicTree = HardwareBasicTree.builder().deviceType(deviceTypeCode).build();
						Long ciId = iCmdbService.getCiId(hardwareBasicTree);

						HashMap<String, Object> newMap = new HashMap<>();
						newMap.putAll(dataOldMap);
						newMap.putAll(valueMap);

						// 再去除 原ID和UUID
						newMap.remove(CmdbAttrConstant.ID);
						newMap.remove(CmdbAttrConstant.UUID);

						Map<String, Map<String, Object>> entityMap = new HashMap<>();
						entityMap.put(UuidUtils.uuid(), newMap);
						iCmdbService.cientityBatchsave(ciId, entityMap, TransactionActionType.INSERT);
						break;
					}
				}
				hashMap.put(id, valueMap);
				handlerDeviceMapper.updateStatus(deviceCodes);
			}
			Map<String, Object> returnMap = iCmdbService.cientityBatchupdate(hashMap, TransactionActionType.UPDATE);
			return R.data(returnMap);

		} catch (Exception e) {
			log.info("修改资产台账(数据治理): " + e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 模板下载
	 */
	@PostMapping("/downExcel")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "数据还原模板下载", notes = "")
	public void down(HttpServletResponse response) {
		try {
			ArrayList<DeviceCodeStencil> exportCabinets = new ArrayList<>();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机柜列表模板", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), DeviceCodeStencil.class).sheet("设备编码").doWrite(exportCabinets);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * 数据还原 excel导入并回显数据
	 */
	@PostMapping("/import")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "excel导入并回显数据", notes = "传入excel")
	public R<List<DeviceCodeStencil>> importByExcel(MultipartFile file) {
		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new ServiceException("请上传文件!");
		}
		if ((!org.springframework.util.StringUtils.endsWithIgnoreCase(filename, ".xls") && !org.springframework.util.StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			return R.fail("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		InputStream inputStream = null;
		try {
			DeviceCodeListener deviceCodeListener = new DeviceCodeListener();
			inputStream = new BufferedInputStream(file.getInputStream());
			EasyExcel.read(inputStream, DeviceCodeStencil.class, deviceCodeListener).sheet().doRead();
			List<DeviceCodeStencil> list = deviceCodeListener.getList();
			return R.data(list);
		} catch (IOException e) {
			log.error("读取流失败");
		} finally {
			try {
				if (inputStream !=null){
					inputStream.close();
				}
			} catch (IOException e) {
				log.error("流关闭失败");
			}
		}
		return null;
	}


	/**
	 * 单条生成打印标签
	 */
	@PostMapping("/card")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "单条生成打印标签", notes = "传入 cmdbCardDTOList")
	public List<String> getBarCode(@RequestBody List<CmdbCardDTO> cmdbCardDTOList) {

		List<String> rstList = new ArrayList<>();
		try {

			for (CmdbCardDTO cmdbCardDTO : cmdbCardDTOList) {
				BufferedImage image = iCmdbService.cards(cmdbCardDTO);
				FastByteArrayOutputStream os = new FastByteArrayOutputStream();
				try {
					ImageIO.write(image, "png", os);
					rstList.add(com.lnsoft.common.sign.Base64.encode(os.toByteArray()));
					log.info("标签图片生成成功！");
				} catch (Exception e) {
					log.error("ImageIO write err", e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return rstList;
	}

	/**
	 * 批量生成打印标签
	 */
	@PostMapping("/batch/cards")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量生成打印标签", notes = "传入 cmdbRestoreDTOList")
	public void batchCards(@RequestBody List<CmdbCardDTO> cmdbCardDTOList) {
		try {
			iCmdbService.batchCards(cmdbCardDTOList);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}


	/**
	 * 用户自主生成实物ID
	 *
	 * @param file
	 */
	@PostMapping("/cards/import")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "用户自主生成实物ID", notes = "传入excel")
	public R<String> importCardsByExcel(MultipartFile file) {
		if (StringUtils.isEmpty(file.getOriginalFilename())) {
			throw new ServiceException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".xls") && !StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".xlsx"))) {
			throw new ServiceException("请上传正确的excel文件!");
		}
		if (file.getSize()>1024*1024*100){
			throw new ServiceException("文件大小超过限制，最大允许"+ 1024*1024*100 + "MB");
		}
		try {
			iCmdbService.importCardsByExcel(file);
			return R.data("用户自主生成实物ID完成");
		} catch (Exception e) {
			log.error(e.getMessage());
			return R.fail(e.getMessage());
		}

	}

}

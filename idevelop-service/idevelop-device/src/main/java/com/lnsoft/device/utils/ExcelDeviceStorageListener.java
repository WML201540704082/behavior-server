package com.lnsoft.device.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.device.annotation.ExcelImportValid;
import com.lnsoft.device.config.ExcelDeviceTemplateConfiguration;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: ExcelListener
 * @description:
 * @author: zhangs
 * @create: 2024-02-27 15:21
 **/
@AllArgsConstructor
public class ExcelDeviceStorageListener extends AnalysisEventListener {

	private static final Logger log = LoggerFactory.getLogger(ExcelDeviceStorageListener.class);

	private List<Map<String, Object>> dataList;

	private RedisUtil redisUtil;

	@Override
	public void invoke(Object o, AnalysisContext analysisContext) {
		log.info("数据对象：{}", o);
		List<String> error = ExcelImportValid.valid(o);
		Map<String, Object> map = BeanUtil.beanToMap(o, false, true);
		//填充品牌系列型号编码
		fillBrandSeriesModel(map);
		// 2024-4-27 新增设备高度校验规则
		Object deviceHeight = map.get("deviceHeight");
		if (ObjectUtil.isEmpty(deviceHeight)) {
			// 2024-5-22 默认设备高度为1
			map.put("deviceHeight", "1");
		}
		// 非信创设备不需要录入CPU品牌、型号、主频，非必填
		String isITAI = (String) map.get("isITAI");
		if ("是".equals(isITAI)) {
			Object cpuBrand = map.get("cpuBrand");
			if (ObjectUtil.isEmpty(cpuBrand)) {
				error.add("信创设备CPU品牌:不能为空");
			}
			Object cpuModel = map.get("cpuModel");
			if (ObjectUtil.isEmpty(cpuModel)) {
				error.add("信创设备CPU型号:不能为空");
			}
// 			Object cpuFrequecy = map.get("cpuFrequecy");
// 			if (ObjectUtil.isEmpty(cpuFrequecy)) {
// 				error.add("信创设备CPU主频(GHZ):不能为空");
// 			}
		}
		//品牌系列型号校验
		String brand = String.valueOf(map.get("brand")).replace("null", "");
		String brandCode = String.valueOf(map.get("brandCode")).replace("null", "");
		String series = String.valueOf(map.get("series")).replace("null", "");
		String seriesCode = String.valueOf(map.get("seriesCode")).replace("null", "");
		String deviceModel = String.valueOf(map.get("deviceModel")).replace("null", "");
		String deviceModelCode = String.valueOf(map.get("deviceModelCode")).replace("null", "");
		String maker = String.valueOf(map.get("maker")).replace("null","");
		String makerCode = String.valueOf(map.get("makerCode")).replace("null","");
		if(checkMaker((Long)map.get("makerCid"),maker,makerCode)){
			error.add("制造商与品牌上下级不匹配!");
		}
		if (checkType((Long) map.get("seriesCid"), series, brandCode)) {
			error.add("品牌与系列上下级不匹配!");
		}
		if (checkType((Long) map.get("deviceModelCid"), deviceModel, seriesCode)) {
			error.add("系列与型号上下级不匹配!");
		}
		if (CollectionUtil.isNotEmpty(error)) {
			String errorStr = error.toString();
			map.put("exceptionField", errorStr);
		}
		dataList.add(map);
	}
	/**
	 * 校验上下级关系
	 *
	 * @param cId       模型id
	 * @param dictValue 中文名称
	 * @param pid       父id
	 * @return
	 */
	private Boolean checkType(Long cId, String dictValue, String pid) {
		RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
		List<HashMap<String, Object>> dict = (List<HashMap<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + cId);
		List<String> collect = dict.stream()
			.map(item -> item.get("dictValue") + "-" + item.get("pid"))
			.collect(Collectors.toList());
		String targetStr = dictValue + "-" + pid;
		// key是否存在，存在则匹配
		if (collect.contains(targetStr)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	/**
	 * 制造商校验
	 * ciid  制造商模型id
	 * maker 制造商名称
	 * makerCode  制造商code
	 */
	private Boolean checkMaker(Long ciId,String maker,String makerCode){
		RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
		List<HashMap<String, Object>> dict = (List<HashMap<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + ciId);
		List<String> collect = dict.stream()
			.map(item -> item.get("dictValue") + "-" + item.get("dictKey"))
			.collect(Collectors.toList());
		String targetStr = maker + "-" + makerCode;
		if (collect.contains(targetStr)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 填充品牌系列型号编码
	 * @param map
	 */
	private void fillBrandSeriesModel(Map<String, Object> map) {
		Long makerCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("maker");
		Long brandCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("brand");
		Long seriesCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("series");
		Long deviceModelCid = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel("model");
		map.put("makerCid", makerCid);
		map.put("brandCid", brandCid);
		map.put("seriesCid", seriesCid);
		map.put("deviceModelCid", deviceModelCid);

		map.put("makerCode", "");
		map.put("brandCode", "");
		map.put("seriesCode", "");
		map.put("deviceModelCode", "");

		// 制造商
		String maker = String.valueOf(map.get("maker")).replace("null", "");
		String brand = String.valueOf(map.get("brand")).replace("null", "");
		String series = String.valueOf(map.get("series")).replace("null", "");
		String model = String.valueOf(map.get("deviceModel")).replace("null", "");



		List<Map<String, Object>> makerDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + makerCid);
		List<Map<String, Object>> makerDictListSelect = makerDictList.stream().filter(m -> m.get("dictValue").equals(maker)).collect(Collectors.toList());
		if (makerDictListSelect.isEmpty()) {
			log.info("制造商不存在: " + maker);

		} else {
			for (Map<String, Object> maker1 : makerDictListSelect) {
				map.put("makerCode", maker1.get("dictKey"));

				List<Map<String, Object>> brandDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + brandCid);
				List<Map<String, Object>> brandDictListSelect = brandDictList.stream().filter(b -> b.get("dictValue").equals(brand) && String.valueOf(b.get("pid")).equals(maker1.get("dictKey"))).collect(Collectors.toList());

				if (brandDictListSelect.isEmpty()) {
					log.info("制造商: "+ maker + "下, 品牌不存在: " + brand);

				} else {
					for (Map<String, Object> brand1 : brandDictListSelect) {
						map.put("brandCode", brand1.get("dictKey"));
						List<Map<String, Object>> seriesDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + seriesCid);
						List<Map<String, Object>> seriesDictListSelect = seriesDictList.stream().filter(s -> s.get("dictValue").equals(series) && String.valueOf(s.get("pid")).equals(brand1.get("dictKey"))).collect(Collectors.toList());

						if (seriesDictListSelect.isEmpty()) {
							log.info("品牌: "+ brand + "下, 系列不存在: " + series);

						} else {
							for (Map<String, Object> series1 : seriesDictListSelect) {
								map.put("seriesCode", series1.get("dictKey"));
								List<Map<String, Object>> deviceModelDictList = (List<Map<String, Object>>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE + deviceModelCid);
								List<Map<String, Object>> deviceModelDictListSelect = deviceModelDictList.stream().filter(m -> m.get("dictValue").equals(model) && String.valueOf(m.get("pid")).equals(series1.get("dictKey"))).collect(Collectors.toList());

								if (deviceModelDictListSelect.isEmpty()) {
									log.info("系列: "+ series + "下, 型号不存在: " + model);
								} else {
									for (Map<String, Object> model1 : deviceModelDictListSelect) {
										map.put("deviceModelCode", model1.get("dictKey"));
									}
								}
							}
						}
					}
				}

			}
		}
	}
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}

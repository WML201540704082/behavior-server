package com.lnsoft.device.utils;

import com.google.common.collect.Maps;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.Func;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: CmdbDictUtil
 * @description:
 * @author: zhangs
 * @create: 2024-03-16 17:08
 **/
public class CmdbDictUtil {

	@Builder
	@Data
	public static class Dict {
		String key;
		String value;
	}

	/**
	 * 返回value的集合
	 *
	 * @param data
	 * @return
	 */
	public static String[] getList(R<List<Map<String, Object>>> data) {
		List<Map<String, Object>> brandList = data.getData();
		String values = brandList.stream().map(m -> String.valueOf(m.get("dictValue"))).collect(Collectors.joining(","));
		return Func.toStrArray(values);
	}

	/**
	 * 返回value的集合
	 *
	 * @param data
	 * @return
	 */
	public static String[] getValueListByCache(Map<String, String> data) {
		String collect = data.values().stream().collect(Collectors.joining(","));
		return Func.toStrArray(collect);
	}

	/**
	 * 返回key的集合
	 *
	 * @param data
	 * @return
	 */
	public static String[] getKeyListByCache(Map<String, String> data) {
		String collect = data.keySet().stream().collect(Collectors.joining(","));
		return Func.toStrArray(collect);
	}

	/**
	 * 返回 11111111：中文 形式的map,入库的字典缓存用
	 *
	 * @param data
	 * @return
	 */
	public static Map<String, String> getCache(R<List<Map<String, Object>>> data) {
		List<Map<String, Object>> brandList = data.getData();
		if (CollectionUtil.isEmpty(brandList)) {
			return null;
		}
		HashMap<String, String> map = new HashMap();
		brandList.stream()
			.map(m -> Dict.builder()
				.key(String.valueOf(m.get("dictKey")))
				.value(String.valueOf(m.get("dictValue")))
				.build()).forEach(dict -> map.put(dict.getKey(), dict.getValue()));
		return map;
	}

	/**
	 * 返回 11111111：中文 形式的map
	 *
	 * @param data
	 * @return
	 */
	public static Map<String, String> getValue(R<List<Map<String, Object>>> data) {
		List<Map<String, Object>> brandList = data.getData();
		if (CollectionUtil.isEmpty(brandList)) {
			return null;
		}
		HashMap<String, String> map = new HashMap();
		brandList.stream()
			.map(m -> Dict.builder()
				.key(String.valueOf(m.get("dictKey")))
				.value(String.valueOf(m.get("dictValue")))
				.build()).forEach(dict -> map.put(dict.getKey(), dict.getValue()));
		return map;
	}

	/**
	 * 返回中文value
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String getValue(R<List<Map<String, Object>>> data, String key) {
		List<Map<String, Object>> brandList = data.getData();
		if (CollectionUtil.isEmpty(brandList)) {
			return null;
		}
		HashMap<String, String> map = new HashMap();
		brandList.stream()
			.map(m -> Dict.builder()
				.key(String.valueOf(m.get("dictKey")))
				.value(String.valueOf(m.get("dictValue")))
				.build()).forEach(dict -> map.put(dict.getKey(), dict.getValue()));
		return map.get(key);
	}

	/**
	 * 从 1111111：中文的map 转化为 中文：11111111的map
	 *
	 * @param data
	 * @return
	 */
	public static Map<String, String> getKey(Map<String, String> data) {
		if (CollectionUtil.isEmpty(data)) {
			return null;
		}
		Map<String, String> map = Maps.newHashMap();
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			map.put(value, key);
		}
		return map;
	}

//	public static long getNexDay() {
//		LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
//		return tomorrow.toEpochSecond(ZoneOffset.UTC) * 1000;
//	}

	/**
	 * 获取设备类型--T10101
	 *
	 * @param data
	 * @return
	 */
	public static Map<String, String> getTxxx(R<List<Map<String, Object>>> data) {
		List<Map<String, Object>> brandList = data.getData();
		if (CollectionUtil.isEmpty(brandList)) {
			return null;
		}
		Map<String, String> map = new HashMap();
		brandList.stream()
			.map(m -> Dict.builder()
				.value(String.valueOf(m.get("remark")))
				.key(String.valueOf(m.get("dictValue")))
				.build()).forEach(dict -> {
			map.put(dict.getKey(), dict.getValue());
		});
		return map;
	}
}

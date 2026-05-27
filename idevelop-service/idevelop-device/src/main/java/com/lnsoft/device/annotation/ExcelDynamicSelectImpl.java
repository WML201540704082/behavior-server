package com.lnsoft.device.annotation;

import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.utils.CmdbDictUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: BrandServiceImpl
 * @description:
 * @author: zhangs
 * @create: 2024-03-19 17:58
 **/
@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDynamicSelectImpl implements ExcelDynamicSelect {
	private RedisUtil redisUtil;

	@Override
	public String[] getSource(Long ciId) {
		String redisKey = CacheNames.CMDB_DICT_STORAGE + ciId;
		Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
		if (CollectionUtil.isEmpty(cache)) {
			R<List<Map<String, Object>>> brand = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
			cache = CmdbDictUtil.getCache(brand);
			redisUtil.set(redisKey, cache);
			return CmdbDictUtil.getList(brand);
		} else {
			return CmdbDictUtil.getValueListByCache(cache);
		}
	}
}

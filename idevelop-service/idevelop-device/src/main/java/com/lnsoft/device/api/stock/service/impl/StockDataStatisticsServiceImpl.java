package com.lnsoft.device.api.stock.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.data.entity.DevelopWarning;
import com.lnsoft.data.feign.IDeviceWarningClient;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.stock.mapper.StockDataStatisticsMapper;
import com.lnsoft.device.api.stock.service.IStockDataStatisticsService;
import com.lnsoft.device.api.stock.vo.CountyVO;
import com.lnsoft.device.api.stock.vo.GovernanceSituationVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Region;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */
@Service
public class StockDataStatisticsServiceImpl implements IStockDataStatisticsService {

	@Resource
	private ICmdbService cmdbService;
	@Resource
	private ICmdbClient cmdbClient;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private IDeviceWarningClient deviceWarningClient;
	@Resource
	private StockDataStatisticsMapper stockDataStatisticsMapper;
	@Resource
	private RedisUtil redisUtil;

	@Override
	public List<GovernanceSituationVO> list(String flush) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtil.isBlank(flush)) {
			if (ObjectUtil.isNotEmpty(redisUtil.get(CacheNames.GOVERNANCE_SITUATION))) {
				String result = (String) redisUtil.get(CacheNames.GOVERNANCE_SITUATION);
				List<GovernanceSituationVO> governanceSituationVOS1 = mapper.readValue(result, new TypeReference<List<GovernanceSituationVO>>() {
				});
				return governanceSituationVOS1;
			}
		}
		List<GovernanceSituationVO> governanceSituationVOS = new ArrayList<>();
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		List<String> regionList = new ArrayList<>();
		regionList.add("3701");
		regionList.add("3702");
		regionList.add("3703");
		regionList.add("3704");
		regionList.add("3705");
		regionList.add("3706");
		regionList.add("3707");
		regionList.add("3708");
		regionList.add("3709");
		regionList.add("3710");
		regionList.add("3711");
		regionList.add("3712");
		regionList.add("3713");
		regionList.add("3714");
		regionList.add("3715");
		regionList.add("3716");
		regionList.add("3717");
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(1);
		List<String> strings = new ArrayList<>();
		strings.add("attr_1082375867269120");
		for (Map<String, Object> map : categoryListData) {
			CiCientitySearch ciCientitySearch = new CiCientitySearch();

			//
			String category = String.valueOf(map.get("dictValue"));
			GovernanceSituationVO situationVOyes = new GovernanceSituationVO();
			GovernanceSituationVO situationVOno = new GovernanceSituationVO();
			GovernanceSituationVO situationVOrate = new GovernanceSituationVO();
			situationVOyes.setDeviceType(category);
			situationVOno.setDeviceType(category);
			situationVOrate.setDeviceType(category);
			situationVOyes.setGovernance("已治理");
			situationVOno.setGovernance("未治理");
			situationVOrate.setGovernance("治理进度");
			Integer allYes = 0;
			Integer allNo = 0;
			for (String area : regionList) {
				List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
				//设备分类
				CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(map.get("dictKey")).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO);
				//区域
				CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(area).expression(Expression.LIKE).build();
				ciCientitySearchVOS.add(searchVO1);
				//是否治理  是
				CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernYes()).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO2);
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				ciCientitySearch.setQuery(query);
				ciCientitySearch.setShowAttrRelList(strings);
				FeignCiCientity ciCientityListByConditionYes = cmdbService.getCiCientityListByCondition(ciCientitySearch);

				ciCientitySearchVOS.remove(searchVO2);
				//是否治理  否
				CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernNo()).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO3);
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				FeignCiCientity ciCientityListByConditionNo = cmdbService.getCiCientityListByCondition(ciCientitySearch);
				Integer yesTotal = ciCientityListByConditionYes.getTotal();
				Integer noTotal = ciCientityListByConditionNo.getTotal();
				allYes += yesTotal;
				allNo += noTotal;
				double rate;
				if (yesTotal + noTotal == 0) {
					rate = 0.00;
				} else {
					rate = (double) yesTotal / (yesTotal + noTotal);
				}
				BigDecimal bigDecimal = BigDecimal.valueOf(rate);
				BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				double v = setScale.doubleValue() * 100;

				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String format = decimalFormat.format(v);
				if (area.equals("3701")) {
					situationVOyes.setJiNan(String.valueOf(yesTotal));
					situationVOno.setJiNan(String.valueOf(noTotal));
					situationVOrate.setJiNan(format);
				} else if (area.equals("3702")) {
					situationVOyes.setQingDao(String.valueOf(yesTotal));
					situationVOno.setQingDao(String.valueOf(noTotal));
					situationVOrate.setQingDao(format);
				} else if (area.equals("3703")) {
					situationVOyes.setZiBo(String.valueOf(yesTotal));
					situationVOno.setZiBo(String.valueOf(noTotal));
					situationVOrate.setZiBo(format);
				} else if (area.equals("3704")) {
					situationVOyes.setZaoZhuang(String.valueOf(yesTotal));
					situationVOno.setZaoZhuang(String.valueOf(noTotal));
					situationVOrate.setZaoZhuang(format);
				} else if (area.equals("3705")) {
					situationVOyes.setDongYing(String.valueOf(yesTotal));
					situationVOno.setDongYing(String.valueOf(noTotal));
					situationVOrate.setDongYing(format);
				} else if (area.equals("3706")) {
					situationVOyes.setYanTai(String.valueOf(yesTotal));
					situationVOno.setYanTai(String.valueOf(noTotal));
					situationVOrate.setYanTai(format);
				} else if (area.equals("3707")) {
					situationVOyes.setWeiFang(String.valueOf(yesTotal));
					situationVOno.setWeiFang(String.valueOf(noTotal));
					situationVOrate.setWeiFang(format);
				} else if (area.equals("3708")) {
					situationVOyes.setJiNing(String.valueOf(yesTotal));
					situationVOno.setJiNing(String.valueOf(noTotal));
					situationVOrate.setJiNing(format);
				} else if (area.equals("3709")) {
					situationVOyes.setTaiAn(String.valueOf(yesTotal));
					situationVOno.setTaiAn(String.valueOf(noTotal));
					situationVOrate.setTaiAn(format);
				} else if (area.equals("3710")) {
					situationVOyes.setWeiHai(String.valueOf(yesTotal));
					situationVOno.setWeiHai(String.valueOf(noTotal));
					situationVOrate.setWeiHai(format);
				} else if (area.equals("3711")) {
					situationVOyes.setRiZhao(String.valueOf(yesTotal));
					situationVOno.setRiZhao(String.valueOf(noTotal));
					situationVOrate.setRiZhao(format);
				} else if (area.equals("3712")) {
					situationVOyes.setLaiWu(String.valueOf(yesTotal));
					situationVOno.setLaiWu(String.valueOf(noTotal));
					situationVOrate.setLaiWu(format);
				} else if (area.equals("3713")) {
					situationVOyes.setLinYi(String.valueOf(yesTotal));
					situationVOno.setLinYi(String.valueOf(noTotal));
					situationVOrate.setLinYi(format);
				} else if (area.equals("3714")) {
					situationVOyes.setDeZhou(String.valueOf(yesTotal));
					situationVOno.setDeZhou(String.valueOf(noTotal));
					situationVOrate.setDeZhou(format);
				} else if (area.equals("3715")) {
					situationVOyes.setLiaoCheng(String.valueOf(yesTotal));
					situationVOno.setLiaoCheng(String.valueOf(noTotal));
					situationVOrate.setLiaoCheng(format);
				} else if (area.equals("3716")) {
					situationVOyes.setBinZhou(String.valueOf(yesTotal));
					situationVOno.setBinZhou(String.valueOf(noTotal));
					situationVOrate.setBinZhou(format);
				} else if (area.equals("3717")) {
					situationVOyes.setHeZe(String.valueOf(yesTotal));
					situationVOno.setHeZe(String.valueOf(noTotal));
					situationVOrate.setHeZe(format);
				}

			}
			double rate;
			if (allYes + allNo == 0) {
				rate = 0.00;
			} else {
				rate = (double) allYes / (allYes + allNo);
			}
			BigDecimal bigDecimal = BigDecimal.valueOf(rate);
			BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			double v = setScale.doubleValue() * 100;
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String format = decimalFormat.format(v);
			situationVOyes.setAll(String.valueOf(allYes));
			situationVOno.setAll(String.valueOf(allNo));
			situationVOrate.setAll(format);
			governanceSituationVOS.add(situationVOyes);
			governanceSituationVOS.add(situationVOno);
			governanceSituationVOS.add(situationVOrate);
		}
		GovernanceSituationVO situationVOYesAll = new GovernanceSituationVO();
		GovernanceSituationVO situationVONoAll = new GovernanceSituationVO();
		GovernanceSituationVO situationVORateAll = new GovernanceSituationVO();
		situationVOYesAll.setGovernance("已治理");
		situationVONoAll.setGovernance("未治理");
		situationVORateAll.setGovernance("治理进度");
		situationVOYesAll.setDeviceType("各单位总治理进度");
		situationVONoAll.setDeviceType("各单位总治理进度");
		situationVORateAll.setDeviceType("各单位总治理进度");
		Integer allYes = 0;
		Integer allNo = 0;
		for (String region : regionList) {
			CiCientitySearch ciCientitySearch = new CiCientitySearch();
			List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			//区域
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(region).expression(Expression.LIKE).build();
			ciCientitySearchVOS.add(searchVO1);
			//是否治理  是
			CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernYes()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO2);
			ciCientitySearch.setEntity(ciCientitySearchVOS);
			ciCientitySearch.setQuery(query);
			ciCientitySearch.setShowAttrRelList(strings);
			FeignCiCientity ciCientityListByConditionYes = cmdbService.getCiCientityListByCondition(ciCientitySearch);
			Integer yesTotal = ciCientityListByConditionYes.getTotal();
			allYes += yesTotal;
			ciCientitySearchVOS.remove(searchVO2);
			//是否治理  否
			CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernNo()).expression(Expression.EQUAL).build();
			ciCientitySearchVOS.add(searchVO3);
			ciCientitySearch.setEntity(ciCientitySearchVOS);
			FeignCiCientity ciCientityListByConditionNo = cmdbService.getCiCientityListByCondition(ciCientitySearch);
			Integer noTotal = ciCientityListByConditionNo.getTotal();
			allNo += noTotal;


			double rate;
			if (yesTotal + noTotal == 0) {
				rate = 0.00;
			} else {
				rate = (double) yesTotal / (yesTotal + noTotal);
			}
			BigDecimal bigDecimal = BigDecimal.valueOf(rate);
			BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			double v = setScale.doubleValue() * 100;

			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String format = decimalFormat.format(v);
			if (region.equals("3701")) {
				situationVOYesAll.setJiNan(String.valueOf(yesTotal));
				situationVONoAll.setJiNan(String.valueOf(noTotal));
				situationVORateAll.setJiNan(format);
			} else if (region.equals("3702")) {
				situationVOYesAll.setQingDao(String.valueOf(yesTotal));
				situationVONoAll.setQingDao(String.valueOf(noTotal));
				situationVORateAll.setQingDao(format);
			} else if (region.equals("3703")) {
				situationVOYesAll.setZiBo(String.valueOf(yesTotal));
				situationVONoAll.setZiBo(String.valueOf(noTotal));
				situationVORateAll.setZiBo(format);
			} else if (region.equals("3704")) {
				situationVOYesAll.setZaoZhuang(String.valueOf(yesTotal));
				situationVONoAll.setZaoZhuang(String.valueOf(noTotal));
				situationVORateAll.setZaoZhuang(format);
			} else if (region.equals("3705")) {
				situationVOYesAll.setDongYing(String.valueOf(yesTotal));
				situationVONoAll.setDongYing(String.valueOf(noTotal));
				situationVORateAll.setDongYing(format);
			} else if (region.equals("3706")) {
				situationVOYesAll.setYanTai(String.valueOf(yesTotal));
				situationVONoAll.setYanTai(String.valueOf(noTotal));
				situationVORateAll.setYanTai(format);
			} else if (region.equals("3707")) {
				situationVOYesAll.setWeiFang(String.valueOf(yesTotal));
				situationVONoAll.setWeiFang(String.valueOf(noTotal));
				situationVORateAll.setWeiFang(format);
			} else if (region.equals("3708")) {
				situationVOYesAll.setJiNing(String.valueOf(yesTotal));
				situationVONoAll.setJiNing(String.valueOf(noTotal));
				situationVORateAll.setJiNing(format);
			} else if (region.equals("3709")) {
				situationVOYesAll.setTaiAn(String.valueOf(yesTotal));
				situationVONoAll.setTaiAn(String.valueOf(noTotal));
				situationVORateAll.setTaiAn(format);
			} else if (region.equals("3710")) {
				situationVOYesAll.setWeiHai(String.valueOf(yesTotal));
				situationVONoAll.setWeiHai(String.valueOf(noTotal));
				situationVORateAll.setWeiHai(format);
			} else if (region.equals("3711")) {
				situationVOYesAll.setRiZhao(String.valueOf(yesTotal));
				situationVONoAll.setRiZhao(String.valueOf(noTotal));
				situationVORateAll.setRiZhao(format);
			} else if (region.equals("3712")) {
				situationVOYesAll.setLaiWu(String.valueOf(yesTotal));
				situationVONoAll.setLaiWu(String.valueOf(noTotal));
				situationVORateAll.setLaiWu(format);
			} else if (region.equals("3713")) {
				situationVOYesAll.setLinYi(String.valueOf(yesTotal));
				situationVONoAll.setLinYi(String.valueOf(noTotal));
				situationVORateAll.setLinYi(format);
			} else if (region.equals("3714")) {
				situationVOYesAll.setDeZhou(String.valueOf(yesTotal));
				situationVONoAll.setDeZhou(String.valueOf(noTotal));
				situationVORateAll.setDeZhou(format);
			} else if (region.equals("3715")) {
				situationVOYesAll.setLiaoCheng(String.valueOf(yesTotal));
				situationVONoAll.setLiaoCheng(String.valueOf(noTotal));
				situationVORateAll.setLiaoCheng(format);
			} else if (region.equals("3716")) {
				situationVOYesAll.setBinZhou(String.valueOf(yesTotal));
				situationVONoAll.setBinZhou(String.valueOf(noTotal));
				situationVORateAll.setBinZhou(format);
			} else if (region.equals("3717")) {
				situationVOYesAll.setHeZe(String.valueOf(yesTotal));
				situationVONoAll.setHeZe(String.valueOf(noTotal));
				situationVORateAll.setHeZe(format);
			}
		}

		double rate;
		if (allYes + allNo == 0) {
			rate = 0.00;
		} else {
			rate = (double) allYes / (allYes + allNo);
		}
		BigDecimal bigDecimal = BigDecimal.valueOf(rate);
		BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		double v = setScale.doubleValue() * 100;
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String format = decimalFormat.format(v);
		situationVOYesAll.setAll(String.valueOf(allYes));
		situationVONoAll.setAll(String.valueOf(allNo));
		situationVORateAll.setAll(format);
		governanceSituationVOS.add(situationVOYesAll);
		governanceSituationVOS.add(situationVONoAll);
		governanceSituationVOS.add(situationVORateAll);
		String json = mapper.writeValueAsString(governanceSituationVOS);
		redisUtil.set(CacheNames.GOVERNANCE_SITUATION, json, 86400);
		return governanceSituationVOS;
	}

	@Override
	public List<GovernanceSituationVO> accurate() {
		List<GovernanceSituationVO> governanceSituationVOS = new ArrayList<>();
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		List<String> regionList = new ArrayList<>();
		regionList.add("3701");
		regionList.add("3702");
		regionList.add("3703");
		regionList.add("3704");
		regionList.add("3705");
		regionList.add("3706");
		regionList.add("3707");
		regionList.add("3708");
		regionList.add("3709");
		regionList.add("3710");
		regionList.add("3711");
		regionList.add("3712");
		regionList.add("3713");
		regionList.add("3714");
		regionList.add("3715");
		regionList.add("3716");
		regionList.add("3717");
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(1);
		List<String> strings = new ArrayList<>();
		strings.add("attr_1082375867269120");
		for (Map<String, Object> map : categoryListData) {
			CiCientitySearch ciCientitySearch = new CiCientitySearch();

			//
			String category = String.valueOf(map.get("dictValue"));
			GovernanceSituationVO situationVOyes = new GovernanceSituationVO();
			GovernanceSituationVO situationVOno = new GovernanceSituationVO();
			GovernanceSituationVO situationVOrate = new GovernanceSituationVO();
			situationVOyes.setDeviceType(category);
			situationVOno.setDeviceType(category);
			situationVOrate.setDeviceType(category);
			situationVOyes.setGovernance("已通过数");
			situationVOno.setGovernance("已治理数");
			situationVOrate.setGovernance("准确性");
			Integer allYes = 0;
			Integer allGoven = 0;
			for (String area : regionList) {
				List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
				//设备分类
				CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(map.get("dictKey")).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO);
				//区域
				CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(area).expression(Expression.LIKE).build();
				ciCientitySearchVOS.add(searchVO1);
				//是否治理  是
				CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernYes()).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO2);
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				ciCientitySearch.setQuery(query);
				ciCientitySearch.setShowAttrRelList(strings);
				FeignCiCientity ciCientityListByConditionYes = cmdbService.getCiCientityListByCondition(ciCientitySearch);


				Integer yesTotal = ciCientityListByConditionYes.getTotal();
				allYes += yesTotal;

				//已通过数量
				DevelopWarning developWarning = new DevelopWarning();
				developWarning.setWarningCategory(String.valueOf(map.get("dictKey")));
				developWarning.setRegionCode(area);
				R<List<DevelopWarning>> listR = deviceWarningClient.getCount(developWarning);
				Integer size = listR.getData().size();
				Integer count = yesTotal - size;
				allGoven += count;
				double rate;
				if (yesTotal == 0) {
					rate = 0.00;
				} else {
					rate = (double) count / yesTotal;
				}
				BigDecimal bigDecimal = BigDecimal.valueOf(rate);
				BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				double v = setScale.doubleValue() * 100;

				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String format = decimalFormat.format(v);
				if (area.equals("3701")) {
					situationVOyes.setJiNan(String.valueOf(count));
					situationVOno.setJiNan(String.valueOf(yesTotal));
					situationVOrate.setJiNan(format);
				} else if (area.equals("3702")) {
					situationVOyes.setQingDao(String.valueOf(count));
					situationVOno.setQingDao(String.valueOf(yesTotal));
					situationVOrate.setQingDao(format);
				} else if (area.equals("3703")) {
					situationVOyes.setZiBo(String.valueOf(count));
					situationVOno.setZiBo(String.valueOf(yesTotal));
					situationVOrate.setZiBo(format);
				} else if (area.equals("3704")) {
					situationVOyes.setZaoZhuang(String.valueOf(count));
					situationVOno.setZaoZhuang(String.valueOf(yesTotal));
					situationVOrate.setZaoZhuang(format);
				} else if (area.equals("3705")) {
					situationVOyes.setDongYing(String.valueOf(count));
					situationVOno.setDongYing(String.valueOf(yesTotal));
					situationVOrate.setDongYing(format);
				} else if (area.equals("3706")) {
					situationVOyes.setYanTai(String.valueOf(count));
					situationVOno.setYanTai(String.valueOf(yesTotal));
					situationVOrate.setYanTai(format);
				} else if (area.equals("3707")) {
					situationVOyes.setWeiFang(String.valueOf(count));
					situationVOno.setWeiFang(String.valueOf(yesTotal));
					situationVOrate.setWeiFang(format);
				} else if (area.equals("3708")) {
					situationVOyes.setJiNing(String.valueOf(count));
					situationVOno.setJiNing(String.valueOf(yesTotal));
					situationVOrate.setJiNing(format);
				} else if (area.equals("3709")) {
					situationVOyes.setTaiAn(String.valueOf(count));
					situationVOno.setTaiAn(String.valueOf(yesTotal));
					situationVOrate.setTaiAn(format);
				} else if (area.equals("3710")) {
					situationVOyes.setWeiHai(String.valueOf(count));
					situationVOno.setWeiHai(String.valueOf(yesTotal));
					situationVOrate.setWeiHai(format);
				} else if (area.equals("3711")) {
					situationVOyes.setRiZhao(String.valueOf(count));
					situationVOno.setRiZhao(String.valueOf(yesTotal));
					situationVOrate.setRiZhao(format);
				} else if (area.equals("3712")) {
					situationVOyes.setLaiWu(String.valueOf(count));
					situationVOno.setLaiWu(String.valueOf(yesTotal));
					situationVOrate.setLaiWu(format);
				} else if (area.equals("3713")) {
					situationVOyes.setLinYi(String.valueOf(count));
					situationVOno.setLinYi(String.valueOf(yesTotal));
					situationVOrate.setLinYi(format);
				} else if (area.equals("3714")) {
					situationVOyes.setDeZhou(String.valueOf(count));
					situationVOno.setDeZhou(String.valueOf(yesTotal));
					situationVOrate.setDeZhou(format);
				} else if (area.equals("3715")) {
					situationVOyes.setLiaoCheng(String.valueOf(count));
					situationVOno.setLiaoCheng(String.valueOf(yesTotal));
					situationVOrate.setLiaoCheng(format);
				} else if (area.equals("3716")) {
					situationVOyes.setBinZhou(String.valueOf(count));
					situationVOno.setBinZhou(String.valueOf(yesTotal));
					situationVOrate.setBinZhou(format);
				} else if (area.equals("3717")) {
					situationVOyes.setHeZe(String.valueOf(count));
					situationVOno.setHeZe(String.valueOf(yesTotal));
					situationVOrate.setHeZe(format);
				}

			}
			double rate;
			if (allYes == 0) {
				rate = 0.00;
			} else {
				rate = (double) allGoven / allYes;
			}
			BigDecimal bigDecimal = BigDecimal.valueOf(rate);
			BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			double v = setScale.doubleValue() * 100;
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String format = decimalFormat.format(v);
			situationVOyes.setAll(String.valueOf(allGoven));
			situationVOno.setAll(String.valueOf(allYes));
			situationVOrate.setAll(format);
			governanceSituationVOS.add(situationVOyes);
			governanceSituationVOS.add(situationVOno);
			governanceSituationVOS.add(situationVOrate);
		}

		return governanceSituationVOS;
	}

	@Override
	public List<GovernanceSituationVO> complete() {
		List<GovernanceSituationVO> governanceSituationVOS = new ArrayList<>();
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		List<String> regionList = new ArrayList<>();
		regionList.add("3701");
		regionList.add("3702");
		regionList.add("3703");
		regionList.add("3704");
		regionList.add("3705");
		regionList.add("3706");
		regionList.add("3707");
		regionList.add("3708");
		regionList.add("3709");
		regionList.add("3710");
		regionList.add("3711");
		regionList.add("3712");
		regionList.add("3713");
		regionList.add("3714");
		regionList.add("3715");
		regionList.add("3716");
		regionList.add("3717");
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(1);
		List<String> strings = new ArrayList<>();
		strings.add("attr_1082375867269120");
		for (Map<String, Object> map : categoryListData) {
			CiCientitySearch ciCientitySearch = new CiCientitySearch();

			//
			String category = String.valueOf(map.get("dictValue"));
			GovernanceSituationVO situationVOyes = new GovernanceSituationVO();
			GovernanceSituationVO situationVOno = new GovernanceSituationVO();
			GovernanceSituationVO situationVOrate = new GovernanceSituationVO();
			situationVOyes.setDeviceType(category);
			situationVOno.setDeviceType(category);
			situationVOrate.setDeviceType(category);
			situationVOyes.setGovernance("治准数");
			situationVOno.setGovernance("转资数");
			situationVOrate.setGovernance("完整性");
			Integer allErp = 0;
			Integer allGoven = 0;
			for (String area : regionList) {
				List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
				//设备分类
				CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(map.get("dictKey")).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO);
				//区域
				CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(area).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO1);
				//是否治理  是
				CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernYes()).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO2);
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				ciCientitySearch.setQuery(query);
				ciCientitySearch.setShowAttrRelList(strings);
				FeignCiCientity ciCientityListByConditionYes = cmdbService.getCiCientityListByCondition(ciCientitySearch);


				Integer yesTotal = ciCientityListByConditionYes.getTotal();


				DevelopWarning developWarning = new DevelopWarning();
				developWarning.setWarningCategory(String.valueOf(map.get("dictKey")));
				developWarning.setRegionCode(area);
				R<List<DevelopWarning>> listR = deviceWarningClient.getCount(developWarning);
				Integer size = listR.getData().size();
				//治理准确数
				Integer count = yesTotal - size;
				allGoven += count;

				//设备转资数量
				Integer co = stockDataStatisticsMapper.getDeviceType(String.valueOf(map.get("dictKey")), area);
				allErp += co;

				double rate;
				if (co == 0) {
					rate = 0.00;
				} else {
					rate = (double) count / co;
				}
				BigDecimal bigDecimal = BigDecimal.valueOf(rate);
				BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				double v = setScale.doubleValue() * 100;

				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String format = decimalFormat.format(v);
				if (area.equals("3701")) {
					situationVOyes.setJiNan(String.valueOf(count));
					situationVOno.setJiNan(String.valueOf(co));
					situationVOrate.setJiNan(format);
				} else if (area.equals("3702")) {
					situationVOyes.setQingDao(String.valueOf(count));
					situationVOno.setQingDao(String.valueOf(co));
					situationVOrate.setQingDao(format);
				} else if (area.equals("3703")) {
					situationVOyes.setZiBo(String.valueOf(count));
					situationVOno.setZiBo(String.valueOf(co));
					situationVOrate.setZiBo(format);
				} else if (area.equals("3704")) {
					situationVOyes.setZaoZhuang(String.valueOf(count));
					situationVOno.setZaoZhuang(String.valueOf(co));
					situationVOrate.setZaoZhuang(format);
				} else if (area.equals("3705")) {
					situationVOyes.setDongYing(String.valueOf(count));
					situationVOno.setDongYing(String.valueOf(co));
					situationVOrate.setDongYing(format);
				} else if (area.equals("3706")) {
					situationVOyes.setYanTai(String.valueOf(count));
					situationVOno.setYanTai(String.valueOf(co));
					situationVOrate.setYanTai(format);
				} else if (area.equals("3707")) {
					situationVOyes.setWeiFang(String.valueOf(count));
					situationVOno.setWeiFang(String.valueOf(co));
					situationVOrate.setWeiFang(format);
				} else if (area.equals("3708")) {
					situationVOyes.setJiNing(String.valueOf(count));
					situationVOno.setJiNing(String.valueOf(co));
					situationVOrate.setJiNing(format);
				} else if (area.equals("3709")) {
					situationVOyes.setTaiAn(String.valueOf(count));
					situationVOno.setTaiAn(String.valueOf(co));
					situationVOrate.setTaiAn(format);
				} else if (area.equals("3710")) {
					situationVOyes.setWeiHai(String.valueOf(count));
					situationVOno.setWeiHai(String.valueOf(co));
					situationVOrate.setWeiHai(format);
				} else if (area.equals("3711")) {
					situationVOyes.setRiZhao(String.valueOf(count));
					situationVOno.setRiZhao(String.valueOf(co));
					situationVOrate.setRiZhao(format);
				} else if (area.equals("3712")) {
					situationVOyes.setLaiWu(String.valueOf(count));
					situationVOno.setLaiWu(String.valueOf(co));
					situationVOrate.setLaiWu(format);
				} else if (area.equals("3713")) {
					situationVOyes.setLinYi(String.valueOf(count));
					situationVOno.setLinYi(String.valueOf(co));
					situationVOrate.setLinYi(format);
				} else if (area.equals("3714")) {
					situationVOyes.setDeZhou(String.valueOf(count));
					situationVOno.setDeZhou(String.valueOf(co));
					situationVOrate.setDeZhou(format);
				} else if (area.equals("3715")) {
					situationVOyes.setLiaoCheng(String.valueOf(count));
					situationVOno.setLiaoCheng(String.valueOf(co));
					situationVOrate.setLiaoCheng(format);
				} else if (area.equals("3716")) {
					situationVOyes.setBinZhou(String.valueOf(count));
					situationVOno.setBinZhou(String.valueOf(co));
					situationVOrate.setBinZhou(format);
				} else if (area.equals("3717")) {
					situationVOyes.setHeZe(String.valueOf(count));
					situationVOno.setHeZe(String.valueOf(co));
					situationVOrate.setHeZe(format);
				}
			}
			double rate;
			if (allErp == 0) {
				rate = 0.00;
			} else {
				rate = (double) allGoven / allErp;
			}
			BigDecimal bigDecimal = BigDecimal.valueOf(rate);
			BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			double v = setScale.doubleValue() * 100;
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String format = decimalFormat.format(v);
			situationVOyes.setAll(String.valueOf(allGoven));
			situationVOno.setAll(String.valueOf(allErp));
			situationVOrate.setAll(format);
			governanceSituationVOS.add(situationVOyes);
			governanceSituationVOS.add(situationVOno);
			governanceSituationVOS.add(situationVOrate);
		}
		return governanceSituationVOS;
	}

	@Override
	public List<GovernanceSituationVO> timely() {

		List<GovernanceSituationVO> governanceSituationVOS = new ArrayList<>();
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		List<String> regionList = new ArrayList<>();
		regionList.add("3701");
		regionList.add("3702");
		regionList.add("3703");
		regionList.add("3704");
		regionList.add("3705");
		regionList.add("3706");
		regionList.add("3707");
		regionList.add("3708");
		regionList.add("3709");
		regionList.add("3710");
		regionList.add("3711");
		regionList.add("3712");
		regionList.add("3713");
		regionList.add("3714");
		regionList.add("3715");
		regionList.add("3716");
		regionList.add("3717");
		for (Map<String, Object> map : categoryListData) {
			String category = String.valueOf(map.get("dictValue"));
			GovernanceSituationVO situationVOyes = new GovernanceSituationVO();
			GovernanceSituationVO situationVOno = new GovernanceSituationVO();
			GovernanceSituationVO situationVOrate = new GovernanceSituationVO();
			situationVOyes.setDeviceType(category);
			situationVOno.setDeviceType(category);
			situationVOrate.setDeviceType(category);
			situationVOyes.setGovernance("已处置数");
			situationVOno.setGovernance("告警总数");
			situationVOrate.setGovernance("及时性");
			Integer allHandle = 0;
			Integer all = 0;
			for (String area : regionList) {
				DevelopWarning developWarning = new DevelopWarning();
				developWarning.setWarningCategory(String.valueOf(map.get("dictKey")));
				developWarning.setRegionCode(area);
				R<List<DevelopWarning>> listR = deviceWarningClient.getCount(developWarning);
				Integer size = listR.getData().size();
				developWarning.setDisposeStatus(1);
				R<List<DevelopWarning>> listR1 = deviceWarningClient.getCount(developWarning);
				Integer handle = listR1.getData().size();
				//告警总数
				all += size;
				//告警已处置数
				allHandle += handle;

				double rate;
				if (size == 0) {
					rate = 0.00;
				} else {
					rate = (double) handle / size;
				}
				BigDecimal bigDecimal = BigDecimal.valueOf(rate);
				BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				double v = setScale.doubleValue() * 100;

				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String format = decimalFormat.format(v);
				if (area.equals("3701")) {
					situationVOyes.setJiNan(String.valueOf(handle));
					situationVOno.setJiNan(String.valueOf(size));
					situationVOrate.setJiNan(format);
				} else if (area.equals("3702")) {
					situationVOyes.setQingDao(String.valueOf(handle));
					situationVOno.setQingDao(String.valueOf(size));
					situationVOrate.setQingDao(format);
				} else if (area.equals("3703")) {
					situationVOyes.setZiBo(String.valueOf(handle));
					situationVOno.setZiBo(String.valueOf(size));
					situationVOrate.setZiBo(format);
				} else if (area.equals("3704")) {
					situationVOyes.setZaoZhuang(String.valueOf(handle));
					situationVOno.setZaoZhuang(String.valueOf(size));
					situationVOrate.setZaoZhuang(format);
				} else if (area.equals("3705")) {
					situationVOyes.setDongYing(String.valueOf(handle));
					situationVOno.setDongYing(String.valueOf(size));
					situationVOrate.setDongYing(format);
				} else if (area.equals("3706")) {
					situationVOyes.setYanTai(String.valueOf(handle));
					situationVOno.setYanTai(String.valueOf(size));
					situationVOrate.setYanTai(format);
				} else if (area.equals("3707")) {
					situationVOyes.setWeiFang(String.valueOf(handle));
					situationVOno.setWeiFang(String.valueOf(size));
					situationVOrate.setWeiFang(format);
				} else if (area.equals("3708")) {
					situationVOyes.setJiNing(String.valueOf(handle));
					situationVOno.setJiNing(String.valueOf(size));
					situationVOrate.setJiNing(format);
				} else if (area.equals("3709")) {
					situationVOyes.setTaiAn(String.valueOf(handle));
					situationVOno.setTaiAn(String.valueOf(size));
					situationVOrate.setTaiAn(format);
				} else if (area.equals("3710")) {
					situationVOyes.setWeiHai(String.valueOf(handle));
					situationVOno.setWeiHai(String.valueOf(size));
					situationVOrate.setWeiHai(format);
				} else if (area.equals("3711")) {
					situationVOyes.setRiZhao(String.valueOf(handle));
					situationVOno.setRiZhao(String.valueOf(size));
					situationVOrate.setRiZhao(format);
				} else if (area.equals("3712")) {
					situationVOyes.setLaiWu(String.valueOf(handle));
					situationVOno.setLaiWu(String.valueOf(size));
					situationVOrate.setLaiWu(format);
				} else if (area.equals("3713")) {
					situationVOyes.setLinYi(String.valueOf(handle));
					situationVOno.setLinYi(String.valueOf(size));
					situationVOrate.setLinYi(format);
				} else if (area.equals("3714")) {
					situationVOyes.setDeZhou(String.valueOf(handle));
					situationVOno.setDeZhou(String.valueOf(size));
					situationVOrate.setDeZhou(format);
				} else if (area.equals("3715")) {
					situationVOyes.setLiaoCheng(String.valueOf(handle));
					situationVOno.setLiaoCheng(String.valueOf(size));
					situationVOrate.setLiaoCheng(format);
				} else if (area.equals("3716")) {
					situationVOyes.setBinZhou(String.valueOf(handle));
					situationVOno.setBinZhou(String.valueOf(size));
					situationVOrate.setBinZhou(format);
				} else if (area.equals("3717")) {
					situationVOyes.setHeZe(String.valueOf(handle));
					situationVOno.setHeZe(String.valueOf(size));
					situationVOrate.setHeZe(format);
				}
			}
			double rate;
			if (all == 0) {
				rate = 0.00;
			} else {
				rate = (double) allHandle / all;
			}
			BigDecimal bigDecimal = BigDecimal.valueOf(rate);
			BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			double v = setScale.doubleValue() * 100;
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String format = decimalFormat.format(v);
			situationVOyes.setAll(String.valueOf(allHandle));
			situationVOno.setAll(String.valueOf(all));
			situationVOrate.setAll(format);
			governanceSituationVOS.add(situationVOyes);
			governanceSituationVOS.add(situationVOno);
			governanceSituationVOS.add(situationVOrate);
		}
		return governanceSituationVOS;
	}

	@Override
	public List<CountyVO> county(String region, String flush) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtil.isBlank(flush)) {
			if (ObjectUtil.isNotEmpty(redisUtil.get(CacheNames.GOVERNANCE_SITUATION + region))) {
				String result = (String) redisUtil.get(CacheNames.GOVERNANCE_SITUATION + region);
				List<CountyVO> list = mapper.readValue(result, new TypeReference<List<CountyVO>>() {
				});
				return list;
			}
		}
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(1);
		List<String> strings = new ArrayList<>();
		strings.add("attr_1082375867269120");
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		//获取区县编码
		List<Map<String, String>> regionList = null;
		if (region.equals("37")) {
			regionList = stockDataStatisticsMapper.getRegionCity();
		} else {
			regionList = stockDataStatisticsMapper.getRegion(region);
		}
		List<CountyVO> list = new ArrayList<>();
		for (Map<String, Object> map : categoryListData) {
			CiCientitySearch ciCientitySearch = new CiCientitySearch();
			for (Map<String, String> regionMap : regionList) {
				List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
				//设备分类
				CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE).attrValue(map.get("dictKey")).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO);
				//区域
				CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.AREA).attrValue(regionMap.get("region_code")).expression(region.equals("37") ? Expression.LIKE : Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO1);
				//总数
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				ciCientitySearch.setQuery(query);
				ciCientitySearch.setShowAttrRelList(strings);
				FeignCiCientity ciCientityAll = cmdbService.getCiCientityListByCondition(ciCientitySearch);
				Integer totalAll = ciCientityAll.getTotal();

				//是否治理  是
				CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernYes()).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO2);
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				FeignCiCientity ciCientityListByConditionYes = cmdbService.getCiCientityListByCondition(ciCientitySearch);

				ciCientitySearchVOS.remove(searchVO2);
				//是否治理  否
				CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_GOVERN).attrValue(cmdbCientityProperties.getGovernNo()).expression(Expression.EQUAL).build();
				ciCientitySearchVOS.add(searchVO3);
				ciCientitySearch.setEntity(ciCientitySearchVOS);
				FeignCiCientity ciCientityListByConditionNo = cmdbService.getCiCientityListByCondition(ciCientitySearch);
				Integer yesTotal = ciCientityListByConditionYes.getTotal();
				Integer noTotal = ciCientityListByConditionNo.getTotal();
				//组装数据
				CountyVO countyVO = new CountyVO();
				countyVO.setRegionName(String.valueOf(regionMap.get("region_name")));
				countyVO.setRegionCode(String.valueOf(regionMap.get("region_code")));
				countyVO.setGovernanceYes(String.valueOf(yesTotal));
				countyVO.setGovernanceNo(String.valueOf(noTotal));
				countyVO.setAllCount(String.valueOf(totalAll));
				countyVO.setDeviceType(String.valueOf(map.get("dictValue")));
				if (totalAll == (yesTotal + noTotal)) {
					countyVO.setIsTrue(true);
				} else {
					countyVO.setIsTrue(false);
				}
				double rate;
				if (totalAll == 0) {
					rate = 0.00;
				} else {
					rate = (double) yesTotal / totalAll;
				}
				BigDecimal bigDecimal = BigDecimal.valueOf(rate);
				BigDecimal setScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				double v = setScale.doubleValue() * 100;

				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String rateResult = decimalFormat.format(v);
				countyVO.setRate(rateResult);
				list.add(countyVO);
			}
		}
		String json = mapper.writeValueAsString(list);
		redisUtil.set(CacheNames.GOVERNANCE_SITUATION + region, json);
		return list;
	}

	@Override
	public List<Region> regionList() {
		List<Region> list = stockDataStatisticsMapper.getRegionList();
		return list;
	}
}

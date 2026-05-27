/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.cmdb.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.lnsoft.cmdb.entity.FeignCmdbCiListattr;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.device.api.cmdb.mapper.CmdbCiAttrGradeMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbCiAttrGradeService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.cmdb.vo.CmdbCiAttrGradeConvertVO;
import com.lnsoft.device.api.cmdb.vo.CmdbCiAttrGradeVO;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.cmdb.wrapper.HardwareBasicTreeWrapper;
import com.lnsoft.device.entity.CmdbCiAttrGrade;
import com.lnsoft.device.entity.HardwareBasicTree;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模型属性映射表(编辑) 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-14
 */
@Service
@AllArgsConstructor
public class CmdbCiAttrGradeServiceImpl extends BaseServiceImpl<CmdbCiAttrGradeMapper, CmdbCiAttrGrade> implements ICmdbCiAttrGradeService {

	private static final String DETAIL = "detail";

	private IHardwareBasicTreeService hardwareBasicTreeService;
	private IHardwareBasicTreeService iHardwareBasicTreeService;

	@Override
	public IPage<CmdbCiAttrGradeVO> selectCmdbCiAttrGradePage(IPage<CmdbCiAttrGradeVO> page, CmdbCiAttrGradeVO cmdbCiAttrGrade) {
		page.setSize(1000);
		Optional.ofNullable(cmdbCiAttrGrade.getCiId()).orElseThrow(() -> new RuntimeException("参数错误!"));

		cmdbCiAttrGrade.setAttrCiId(cmdbCiAttrGrade.getCiId() + "-");

		List<CmdbCiAttrGradeVO> cmdbCiAttrGradeVOList = baseMapper.selectCmdbCiAttrGradePage(page, cmdbCiAttrGrade);

		if (!CollectionUtils.isEmpty(cmdbCiAttrGradeVOList)) {
			for (CmdbCiAttrGradeVO cmdbCiAttrGradeVO : cmdbCiAttrGradeVOList) {
				cmdbCiAttrGradeVO.setConfig(JSON.parseObject(cmdbCiAttrGradeVO.getCmdbConfig()));
			}
			return page.setRecords(cmdbCiAttrGradeVOList);
		}

		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setCiId(cmdbCiAttrGrade.getCiId());
		HardwareBasicTree one = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));
		Optional.ofNullable(one.getParentCiId()).orElseThrow(() -> new RuntimeException("未配置相关数据, 请联系运维人员!"));

		cmdbCiAttrGrade.setAttrCiId(one.getParentCiId() + "-");
		List<CmdbCiAttrGradeVO> cmdbCiAttrGradeVOS = baseMapper.selectCmdbCiAttrGradePage(page, cmdbCiAttrGrade);
		if (CollectionUtils.isEmpty(cmdbCiAttrGradeVOS)) {
			throw new ServiceException("未配置相关数据, 请联系运维人员!!");
		}
		for (CmdbCiAttrGradeVO cmdbCiAttrGradeVO : cmdbCiAttrGradeVOS) {
			cmdbCiAttrGradeVO.setConfig(JSON.parseObject(cmdbCiAttrGradeVO.getCmdbConfig()));
		}

		return page.setRecords(cmdbCiAttrGradeVOS);
	}

	/**
	 * 重写删除
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public Boolean deleteLogicNew(List<String> ids) {
		return baseMapper.delectCmdbCiAttrGradeList(Sets.newHashSet(ids));
	}


	/**
	 * 刷新 模型属性映射表
	 *
	 * @param ciId   模型ID
	 * @param ciName 模型英文名
	 * @return
	 */
	@Override
	public String refreshCiAttr(Long ciId, String ciName) {
		Integer total = 0;

		if (Objects.isNull(ciId) && !StringUtils.hasLength(ciName)) {
			HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
			QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);
			queryWrapper.lambda().eq(HardwareBasicTree::getIsUpdate, 1);
			List<HardwareBasicTree> hardwareBasicTreeList = iHardwareBasicTreeService.list(queryWrapper);
			if (CollectionUtils.isEmpty(hardwareBasicTreeList)) {
				return String.format("本次刷新数据共 { 0 } 条.");
			}
			for (HardwareBasicTree basicTree : hardwareBasicTreeList) {
				total += getTotal(basicTree.getCiId(), basicTree.getCiName(), Boolean.TRUE);
			}
		} else {
			total = getTotal(ciId, ciName, Boolean.FALSE);
		}

		return String.format("本次刷新数据共 { %s } 条.", total);
	}

	@Override
	public String refreshCiAttrLs(Long deviceClaccify) {
		Long ciId = 1097745969774592L;
		Long pid = deviceClaccify;
		List<Map<String, Object>> deviceTypeList = CmdbCiAttrWrapper.build().feignGetCiEntityDictListByPid(ciId, pid).getData();
		for (Map<String, Object> map : deviceTypeList) {
			Object dictKey = map.get("dictKey");
			HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
			hardwareBasicTree.setDeviceType((String) dictKey);
			HardwareBasicTree one = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));

			Long parentCmdbId = one.getParentCmdbId();
			IPage page = new Page();
			page.setSize(10000);
			CmdbCiAttrGradeVO cmdbCiAttrGrade = new CmdbCiAttrGradeVO();
			cmdbCiAttrGrade.setAttrCiId(String.valueOf(parentCmdbId));
			List<CmdbCiAttrGradeVO> cmdbCiAttrGradeVOS = baseMapper.selectCmdbCiAttrGradePage(page, cmdbCiAttrGrade);
			Map<String, String> cmdbCiAttrGradeVOSMap = cmdbCiAttrGradeVOS.stream().collect(Collectors.toMap(CmdbCiAttrGradeVO::getName, CmdbCiAttrGradeVO::getAttrGrade));

			FeignCmdbCiListattr feignCmdbCiListattr = new FeignCmdbCiListattr();
			feignCmdbCiListattr.setCiId(one.getCiId());
			feignCmdbCiListattr.setCiName(one.getCiName());
			feignCmdbCiListattr.setShowType(DETAIL);
			JSONObject jsonObject = HardwareBasicTreeWrapper.build().feignCiListattrBy(feignCmdbCiListattr);
			JSONArray jsonArray = jsonObject.getJSONArray("Return");
			List<CmdbCiAttrGrade> cmdbCiAttrList = new ArrayList<>();
			Gson gson = new Gson();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				CmdbCiAttrGradeConvertVO convertVO = gson.fromJson(object.toString(), CmdbCiAttrGradeConvertVO.class);
				CmdbCiAttrGrade cmdbCiAttrGrade1 = Convert.convert(new TypeReference<CmdbCiAttrGrade>() {
				}, convertVO);

				JSONArray expressionList = object.getJSONArray("expressionList");
				Object config = object.get("config");
				Long attrId = object.getLong("id");
				cmdbCiAttrGrade1.setAttrCiId(one.getCiId() + "-" + attrId);
				cmdbCiAttrGrade1.setExpressionList(expressionList.toString());
				cmdbCiAttrGrade1.setCmdbConfig(JSONObject.toJSONString(config));
				cmdbCiAttrGrade1.setCreateTime(new Date());
				cmdbCiAttrGrade1.setUpdateTime(new Date());

				// 处理
				String attrGrade = cmdbCiAttrGradeVOSMap.get(convertVO.getName());
				cmdbCiAttrGrade1.setAttrGrade(attrGrade);

				cmdbCiAttrList.add(cmdbCiAttrGrade1);
			}

			// System.out.println("cmdbCiAttrList" + cmdbCiAttrList);
			List<String> collect1 = cmdbCiAttrList.stream().map(CmdbCiAttrGrade::getAttrCiId).collect(Collectors.toList());
			List<String> collect2 = baseMapper.selectCmdbAttrCiIdList(ciId);
			// List<String> contains1 = collect1.stream().filter(item -> !collect2.contains(item)).collect(Collectors.toList());
			List<String> contains2 = collect2.stream().filter(item -> !collect1.contains(item)).collect(Collectors.toList());
			// contains1.addAll(contains2);
			if (!CollectionUtils.isEmpty(contains2)) {
				baseMapper.delectCmdbCiAttrGradeList(Sets.newHashSet(contains2));
			}
			saveOrUpdateBatch(cmdbCiAttrList);
		}
		return null;
	}

	private Integer getTotal(Long ciId, String ciName, Boolean type) {
		Integer total = 0;

		// 如果没有提前处理
		List<String> advanCiList = baseMapper.selectCmdbAttrCiIdList(ciId);
		if (type && CollectionUtils.isEmpty(advanCiList)) {
			return getTotalLs(ciId, ciName);
		}

		FeignCmdbCiListattr feignCmdbCiListattr = new FeignCmdbCiListattr();
		feignCmdbCiListattr.setCiId(ciId);
		feignCmdbCiListattr.setCiName(ciName);
		feignCmdbCiListattr.setShowType(DETAIL);
		JSONObject jsonObject = HardwareBasicTreeWrapper.build().feignCiListattrBy(feignCmdbCiListattr);
		JSONArray jsonArray = jsonObject.getJSONArray("Return");
		List<CmdbCiAttrGrade> cmdbCiAttrList = new ArrayList<>();
		Gson gson = new Gson();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			CmdbCiAttrGradeConvertVO convertVO = gson.fromJson(object.toString(), CmdbCiAttrGradeConvertVO.class);
			CmdbCiAttrGrade cmdbCiAttrGrade = Convert.convert(new TypeReference<CmdbCiAttrGrade>() {
			}, convertVO);

			JSONArray expressionList = object.getJSONArray("expressionList");
			Object config = object.get("config");
			Long attrId = object.getLong("id");
			cmdbCiAttrGrade.setAttrCiId(ciId + "-" + attrId);
			cmdbCiAttrGrade.setExpressionList(expressionList.toString());
			cmdbCiAttrGrade.setCmdbConfig(JSONObject.toJSONString(config));
			cmdbCiAttrGrade.setCreateTime(new Date());
			cmdbCiAttrGrade.setUpdateTime(new Date());
			cmdbCiAttrGrade.setTargetCiId(object.getLong("targetCiId"));
			cmdbCiAttrList.add(cmdbCiAttrGrade);
			total++;
		}

		List<String> collect1 = cmdbCiAttrList.stream().map(CmdbCiAttrGrade::getAttrCiId).collect(Collectors.toList());
		List<String> collect2 = baseMapper.selectCmdbAttrCiIdList(ciId);

		// List<String> contains1 = collect1.stream().filter(item -> !collect2.contains(item)).collect(Collectors.toList());
		List<String> contains2 = collect2.stream().filter(item -> !collect1.contains(item)).collect(Collectors.toList());
		// contains1.addAll(contains2);
		if (!CollectionUtils.isEmpty(contains2)) {
			baseMapper.delectCmdbCiAttrGradeList(Sets.newHashSet(contains2));
		}

		saveOrUpdateBatch(cmdbCiAttrList);
		return total;
	}

	private Integer getTotalLs(Long ciId, String ciName) {
		Integer total = 0;

		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setCiId(ciId);
		HardwareBasicTree one = hardwareBasicTreeService.getOne(Condition.getQueryWrapper(hardwareBasicTree));

		Long parentCmdbId = one.getParentCmdbId();
		IPage page = new Page();
		page.setSize(10000);
		CmdbCiAttrGradeVO cmdbCiAttrGrade = new CmdbCiAttrGradeVO();
		cmdbCiAttrGrade.setAttrCiId(String.valueOf(parentCmdbId));
		List<CmdbCiAttrGradeVO> cmdbCiAttrGradeVOS = baseMapper.selectCmdbCiAttrGradePage(page, cmdbCiAttrGrade);
		Map<String, String> cmdbCiAttrGradeVOSMap = cmdbCiAttrGradeVOS.stream().collect(Collectors.toMap(CmdbCiAttrGradeVO::getName, CmdbCiAttrGradeVO::getAttrGrade));

		//
		FeignCmdbCiListattr feignCmdbCiListattr = new FeignCmdbCiListattr();
		feignCmdbCiListattr.setCiId(ciId);
		feignCmdbCiListattr.setCiName(ciName);
		feignCmdbCiListattr.setShowType(DETAIL);
		JSONObject jsonObject = HardwareBasicTreeWrapper.build().feignCiListattrBy(feignCmdbCiListattr);
		JSONArray jsonArray = jsonObject.getJSONArray("Return");

		List<CmdbCiAttrGrade> cmdbCiAttrList = new ArrayList<>();
		Gson gson = new Gson();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			CmdbCiAttrGradeConvertVO convertVO = gson.fromJson(object.toString(), CmdbCiAttrGradeConvertVO.class);
			CmdbCiAttrGrade cmdbCiAttrGrade1 = Convert.convert(new TypeReference<CmdbCiAttrGrade>() {
			}, convertVO);

			JSONArray expressionList = object.getJSONArray("expressionList");
			Object config = object.get("config");
			Long attrId = object.getLong("id");
			cmdbCiAttrGrade1.setAttrCiId(ciId + "-" + attrId);
			cmdbCiAttrGrade1.setExpressionList(expressionList.toString());
			cmdbCiAttrGrade1.setCmdbConfig(JSONObject.toJSONString(config));
			cmdbCiAttrGrade1.setCreateTime(new Date());

			// 处理
			String attrGrade = cmdbCiAttrGradeVOSMap.get(convertVO.getName());
			cmdbCiAttrGrade1.setAttrGrade(attrGrade);

			cmdbCiAttrList.add(cmdbCiAttrGrade1);
			total++;
		}

		List<String> collect1 = cmdbCiAttrList.stream().map(CmdbCiAttrGrade::getAttrCiId).collect(Collectors.toList());
		List<String> collect2 = baseMapper.selectCmdbAttrCiIdList(ciId);

		// List<String> contains1 = collect1.stream().filter(item -> !collect2.contains(item)).collect(Collectors.toList());
		List<String> contains2 = collect2.stream().filter(item -> !collect1.contains(item)).collect(Collectors.toList());
		// contains1.addAll(contains2);
		if (!CollectionUtils.isEmpty(contains2)) {
			baseMapper.delectCmdbCiAttrGradeList(Sets.newHashSet(contains2));
		}

		saveOrUpdateBatch(cmdbCiAttrList);
		return total;
	}
}

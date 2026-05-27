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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.CmdbCiAttr;
import com.lnsoft.cmdb.entity.FeignCmdbCiListattr;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.cmdb.vo.CmdbCiAttrVO;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.mapper.CmdbCiAttrMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbCiAttrService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.cmdb.wrapper.HardwareBasicTreeWrapper;
import com.lnsoft.device.api.res.enums.AttrMappingType;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模型属性映射表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-23
 */
@Service
@AllArgsConstructor
public class CmdbCiAttrServiceImpl extends BaseServiceImpl<CmdbCiAttrMapper, CmdbCiAttr> implements ICmdbCiAttrService {

	private IHardwareBasicTreeService iHardwareBasicTreeService;
	private RedisUtil redisUtil;

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cmdbCiAttr
	 * @return
	 */
	@Override
	public IPage<CmdbCiAttrVO> selectCmdbCiAttrPage(IPage<CmdbCiAttrVO> page, CmdbCiAttrVO cmdbCiAttr) {
		return page.setRecords(baseMapper.selectCmdbCiAttrPage(page, cmdbCiAttr));
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
			queryWrapper.lambda().eq(HardwareBasicTree::getIsMap, 1);
			List<HardwareBasicTree> hardwareBasicTreeList = iHardwareBasicTreeService.list(queryWrapper);
			if (CollectionUtils.isEmpty(hardwareBasicTreeList)) {
				return String.format("本次刷新数据共 { 0 } 条.");
			}
			for (HardwareBasicTree basicTree : hardwareBasicTreeList) {
				total += getTotal(basicTree.getCiId(), basicTree.getCiName());
			}
		} else {
			total = getTotal(ciId, ciName);
		}

		return String.format("本次刷新数据共 { %s } 条.", total);
	}

	/**
	 * 查询模型下所有的属性
	 *
	 * @param ciId
	 * @return
	 */
	@Override
	public Map<Object, Object> selectCiAttrMap(Long ciId, AttrMappingType returnType) {

		if (StringUtils.pathEquals(returnType.getValue(), AttrMappingType.ALL.getValue())) {
			Map<Object, Object> attrMap = redisUtil.hmget(CacheNames.GENERATE_ATTR_BUSINESS_KEY + ciId);
			if (!CollectionUtils.isEmpty(attrMap)) {
				return attrMap;
			}
			List<CmdbCiAttr> cmdbCiAttrList = getCmdbCiAttrAttrListAll(ciId);
			return cmdbCiAttrList.stream()
				.collect(Collectors.toMap(CmdbCiAttr::getAttrName,
					ciAttr -> ciAttr.getAttrId() + "-" + ciAttr.getAttrType() + "-" + ciAttr.getAttrConfig()));
		} else if (StringUtils.pathEquals(returnType.getValue(), AttrMappingType.ATTRID.getValue())) {
			Map<Object, Object> attrMap = redisUtil.hmget(CacheNames.GENERATE_ATTR_IT_KEY + ciId);
			if (!CollectionUtils.isEmpty(attrMap)) {
				return attrMap;
			}
			List<CmdbCiAttr> cmdbCiAttrList = getCmdbCiAttrAttrListAll(ciId);
			return cmdbCiAttrList.stream()
				.collect(Collectors.toMap(CmdbCiAttr::getAttrName, CmdbCiAttr::getAttrId));
		} else {
			throw new RuntimeException("查询模型属性失败!");
		}
	}

	/**
	 * 查询模型下所有的属性2.0
	 *
	 * @param ciId
	 * @return
	 */
	@Override
	public Map<String, CmdbCiAttr> selectCiAttr(Long ciId) {
		List<CmdbCiAttr> cmdbCiAttrList = getCmdbCiAttrAttrListAll(ciId);
		return cmdbCiAttrList.stream()
			.collect(Collectors.toMap(CmdbCiAttr::getAttrName, ciAttr -> ciAttr));
	}

	/**
	 * 获取模型属性列表
	 *
	 * @param ciId
	 * @return
	 */
	@NotNull
	private List<CmdbCiAttr> getCmdbCiAttrAttrListAll(Long ciId) {
		String attrCiId = ciId + "-";

		CmdbCiAttr cmdbCiAttr = new CmdbCiAttr();
		QueryWrapper<CmdbCiAttr> queryWrapper = Condition.getQueryWrapper(cmdbCiAttr);
		queryWrapper.lambda().like(CmdbCiAttr::getAttrCiId, attrCiId);
		List<CmdbCiAttr> cmdbCiAttrList = baseMapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(cmdbCiAttrList)) {
			throw new RuntimeException(String.format("未查询到ID为 { %s } 的模型的映射数据", ciId));
		}
		return cmdbCiAttrList;
	}

	/**
	 * 处理刷新数据
	 *
	 * @param ciId
	 * @param ciName
	 * @return
	 */
	private Integer getTotal(Long ciId, String ciName) {
		Integer total = 0;
		FeignCmdbCiListattr feignCmdbCiListattr = new FeignCmdbCiListattr();
		feignCmdbCiListattr.setCiId(ciId);
		feignCmdbCiListattr.setCiName(ciName);
		JSONObject jsonObject = HardwareBasicTreeWrapper.build().feignCiListattrBy(feignCmdbCiListattr);
		JSONArray jsonArray = jsonObject.getJSONArray("Return");
		List<CmdbCiAttr> cmdbCiAttrList = new ArrayList<>();
		Map<String, Object> businessAttrMap = new HashMap<>();
		Map<String, Object> itCiAttrMap = new HashMap<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			CmdbCiAttr cmdbCiAttr = new CmdbCiAttr();
			JSONObject object = jsonArray.getJSONObject(i);
			Long id = object.getLong("ciId");
			Long attrId = object.getLong("id");
			String attrName = object.getString("name");
			String attrLabel = object.getString("label");
			String type = object.getString("type");
			String typeText = object.getString("typeText");
			Object config = object.get("config");
			cmdbCiAttr.setCiId(id);
			cmdbCiAttr.setAttrCiId(ciId + "-" + attrId);
			cmdbCiAttr.setAttrId(attrId);
			cmdbCiAttr.setAttrName(attrName);
			cmdbCiAttr.setAttrLabel(attrLabel);
			cmdbCiAttr.setAttrType(type);
			cmdbCiAttr.setAttrTypeText(typeText);
			cmdbCiAttr.setAttrText(object.toJSONString());
			cmdbCiAttr.setAttrConfig(JSONObject.toJSONString(config));
			cmdbCiAttr.setTargetCiId(object.getLong("targetCiId"));
			cmdbCiAttrList.add(cmdbCiAttr);
			businessAttrMap.put(attrName, attrId + "-" + type + "-" + config);
			itCiAttrMap.put(attrName, attrId);
			total++;
		}
		baseMapper.deleteCiAttrByAttrCi(ciId + "-");
		boolean batch = this.saveOrUpdateBatch(cmdbCiAttrList);
		if (batch) {
			redisUtil.del(CacheNames.GENERATE_ATTR_BUSINESS_KEY + ciId);
			redisUtil.hmset(CacheNames.GENERATE_ATTR_BUSINESS_KEY + ciId, businessAttrMap);

			redisUtil.del(CacheNames.GENERATE_ATTR_IT_KEY + ciId);
			redisUtil.hmset(CacheNames.GENERATE_ATTR_IT_KEY + ciId, itCiAttrMap);
		}
		return total;
	}

}

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
package com.lnsoft.device.api.i6000.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.i6000.dto.I6000ReqManualDTO;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import com.lnsoft.device.api.i6000.mapper.I6000CiAttrMapper;
import com.lnsoft.device.api.i6000.service.II6000CiAttrService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.vo.I6000CiAttrVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * i6000模型属性 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
@Service
public class I6000CiAttrServiceImpl extends BaseServiceImpl<I6000CiAttrMapper, I6000CiAttr> implements II6000CiAttrService {

	@Resource
	private II6000Service ii6000Service;
	@Override
	public IPage<I6000CiAttrVO> selectI6000CiAttrPage(IPage<I6000CiAttrVO> page, I6000CiAttrVO i6000CiAttr) {
		return page.setRecords(baseMapper.selectI6000CiAttrPage(page, i6000CiAttr));
	}

	@Override
	public Boolean saveManual(I6000ReqManualDTO i6000ReqManualDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			List<Map<String, Object>> ciAttrList = objectMapper.readValue(i6000ReqManualDTO.getResultValue(), new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
			});

			List<I6000CiAttr> i6000CiAttrs = new ArrayList<>();
			for (Map<String, Object> ciAttrMap : ciAttrList) {
				List<Map<String, Object>> attrData = (List<Map<String, Object>>) ciAttrMap.get("attrData");
				String ciId = (String) ciAttrMap.get("TYPEATTRGRP_ID");
				for (Map<String, Object> attrDatum : attrData) {
					I6000CiAttr i6000CiAttr = new I6000CiAttr();
					i6000CiAttr.setCiCode(ciId.substring(0, 6));
					i6000CiAttr.setAttrCode(String.valueOf(attrDatum.get("ATTR_CODE")));
					i6000CiAttr.setAttrName(String.valueOf(attrDatum.get("ATTR_NAME")));
					i6000CiAttr.setDatatypeName(String.valueOf(attrDatum.get("DATATYPE_NAME")));
					i6000CiAttr.setAttrDataLen(String.valueOf(attrDatum.get("ATTR_DATA_LEN")));
					i6000CiAttr.setViewFlag(String.valueOf(attrDatum.get("VIEW_FLAG")));
					i6000CiAttr.setStandardFlag(String.valueOf(attrDatum.get("STANDARD_FLAG")));
					i6000CiAttr.setCorpCode(String.valueOf(attrDatum.get("CORP_CODE")));
					i6000CiAttr.setAsctCitypeId(String.valueOf(attrDatum.get("ASCT_CITYPE_ID")));
					i6000CiAttr.setCollectFlag(String.valueOf(attrDatum.get("COLLECT_FLAG")));
					i6000CiAttr.setOrigin(String.valueOf(attrDatum.get("ORIGIN")));
					i6000CiAttr.setReadonly(String.valueOf(attrDatum.get("READONLY")));
					i6000CiAttr.setOriType(String.valueOf(attrDatum.get("ORI_TYPE")));
					i6000CiAttr.setUnit(String.valueOf(attrDatum.get("UNIT")));
					i6000CiAttr.setViewUnit(String.valueOf(attrDatum.get("VIEW_UNIT")));
					i6000CiAttr.setInputFlag(String.valueOf(attrDatum.get("INPUT_FLAG")));
					i6000CiAttr.setNullFlag(String.valueOf(attrDatum.get("NULL_FLAG")));
					i6000CiAttr.setOtnFlag(String.valueOf(attrDatum.get("OTN_FLAG")));
					i6000CiAttrs.add(i6000CiAttr);
				}
			}
			return this.saveOrUpdateBatch(i6000CiAttrs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更新是否与CMDB映射
	 *
	 * @param cmdbAttrCode
	 * @return
	 */
	@Override
	public Boolean updateByAttrCode(String cmdbAttrCode, String ciCode) {
		return baseMapper.updateByAttrCode(cmdbAttrCode, ciCode);
	}


	/**
	 * 根据I6000 ciTypeId 的获取I6000所有的模型属性
	 *
	 * @param ciTypeId
	 * @return
	 */
	@Override
	public String selectI6000AttrByCiTypeId(String ciTypeId) {
		if (StringUtils.isEmpty(ciTypeId)) {
			throw new RuntimeException("设备类型不能为空!");
		}
		List<String> attrList = baseMapper.selectI6000AttrByCiTypeId(ciTypeId);
		if (CollectionUtils.isEmpty(attrList)) {
			throw new RuntimeException("未查询到该设备类型的I6000模型属性!");
		}

		return attrList.stream().collect(Collectors.joining(","));
	}

	@Override
	public R refresh(I6000CiAttr i6000CiAttrDto) {
		String i6000CiId = i6000CiAttrDto.getCiCode();
		List<Map<String, Object>> selectCiAttr = ii6000Service.selectCiAttr(i6000CiId);
		LambdaQueryWrapper<I6000CiAttr> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(I6000CiAttr::getCiCode,i6000CiId);
		List<I6000CiAttr> i6000CiAttrs = baseMapper.selectList(queryWrapper);
		List<I6000CiAttr> i6000CiAttrList = new ArrayList<>();
		for (Map<String, Object> ciAttrMap : selectCiAttr) {
			List<Map<String, Object>> attrData = (List<Map<String, Object>>) ciAttrMap.get("attrData");
			String ciId = (String) ciAttrMap.get("TYPEATTRGRP_ID");
			for (Map<String, Object> attrDatum : attrData) {
				I6000CiAttr i6000CiAttr = new I6000CiAttr();
				i6000CiAttr.setCiCode(ciId.substring(0, 6));
				i6000CiAttr.setAttrCode(String.valueOf(attrDatum.get("ATTR_CODE")));
				i6000CiAttr.setAttrName(String.valueOf(attrDatum.get("ATTR_NAME")));
				i6000CiAttr.setDatatypeName(String.valueOf(attrDatum.get("DATATYPE_NAME")));
				i6000CiAttr.setAttrDataLen(String.valueOf(attrDatum.get("ATTR_DATA_LEN")));
				i6000CiAttr.setViewFlag(String.valueOf(attrDatum.get("VIEW_FLAG")));
				i6000CiAttr.setStandardFlag(String.valueOf(attrDatum.get("STANDARD_FLAG")));
				i6000CiAttr.setCorpCode(String.valueOf(attrDatum.get("CORP_CODE")));
				i6000CiAttr.setAsctCitypeId(String.valueOf(attrDatum.get("ASCT_CITYPE_ID")));
				i6000CiAttr.setCollectFlag(String.valueOf(attrDatum.get("COLLECT_FLAG")));
				i6000CiAttr.setOrigin(String.valueOf(attrDatum.get("ORIGIN")));
				i6000CiAttr.setReadonly(String.valueOf(attrDatum.get("READONLY")));
				i6000CiAttr.setOriType(String.valueOf(attrDatum.get("ORI_TYPE")));
				i6000CiAttr.setUnit(String.valueOf(attrDatum.get("UNIT")));
				i6000CiAttr.setViewUnit(String.valueOf(attrDatum.get("VIEW_UNIT")));
				i6000CiAttr.setInputFlag(String.valueOf(attrDatum.get("INPUT_FLAG")));
				i6000CiAttr.setNullFlag(String.valueOf(attrDatum.get("NULL_FLAG")));
				i6000CiAttr.setOtnFlag(String.valueOf(attrDatum.get("OTN_FLAG")));
				i6000CiAttrList.add(i6000CiAttr);
			}
		}
		//新数据  i6000CiAttrList
		//存量数据 i6000CiAttrs
		List<I6000CiAttr> deleteList = i6000CiAttrs.stream().filter(itemA -> i6000CiAttrList.stream().noneMatch(itemB -> itemB.getAttrCode().equals(itemA.getAttrCode()))).collect(Collectors.toList());
		List<I6000CiAttr> insertList = i6000CiAttrList.stream().filter(itemA -> i6000CiAttrs.stream().noneMatch(itemB -> itemB.getAttrCode().equals(itemA.getAttrCode()))).collect(Collectors.toList());
		i6000CiAttrList.removeAll(insertList);
		//更新
		for (I6000CiAttr i6000CiAttr : i6000CiAttrList) {
			LambdaUpdateWrapper<I6000CiAttr> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(I6000CiAttr::getAttrCode,i6000CiAttr.getAttrCode()).set(I6000CiAttr::getUpdateTime,new Date()).set(I6000CiAttr::getStatus,2);
			baseMapper.update(i6000CiAttr,updateWrapper);
		}
		//新增
		this.saveBatch(insertList.stream().map( item ->{ item.setStatus(1); item.setIsMapping(0); return item;}).collect(Collectors.toList()));
		//删除
		for (I6000CiAttr i6000CiAttr : deleteList) {
			i6000CiAttr.setIsDeleted(1);
			baseMapper.updateById(i6000CiAttr);
		}

		return R.success("更新成功");
	}




}

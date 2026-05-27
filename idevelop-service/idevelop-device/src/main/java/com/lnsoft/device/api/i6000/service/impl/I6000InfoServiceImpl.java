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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.device.api.i6000.dto.I6000CiCientityDTO;
import com.lnsoft.device.api.i6000.dto.I6000InfoDTO;
import com.lnsoft.device.api.i6000.entity.I6000Info;
import com.lnsoft.device.api.i6000.mapper.I6000InfoMapper;
import com.lnsoft.device.api.i6000.service.II6000InfoService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.vo.I6000InfoVO;
import com.lnsoft.device.constant.I6000Constant;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于根据条件获取I6000信息插入CMDB模块 服务实现类
 *
 * @author Idevelop
 * @since 2024-05-18
 */
@Service
@AllArgsConstructor
public class I6000InfoServiceImpl extends ServiceImpl<I6000InfoMapper, I6000Info> implements II6000InfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(I6000InfoServiceImpl.class);


	private II6000Service i6000Service;

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000Info
	 * @return
	 */
	@Override
	public IPage<I6000InfoVO> selectI6000InfoPage(IPage<I6000InfoVO> page, I6000InfoVO i6000Info) {
		return page.setRecords(baseMapper.selectI6000InfoPage(page, i6000Info));
	}


	/**
	 * 重写新增
	 *
	 * @param i6000InfoDTO
	 * @return
	 */
	@Override
	public boolean saveNew(I6000InfoDTO i6000InfoDTO) {
		LOGGER.info("com.lnsoft.device.api.i6000.service.impl.I6000InfoServiceImpl.saveNew请求参数: {}", i6000InfoDTO);

		String ciCodeRequest = i6000InfoDTO.getCiCode();
		Map<String, String> requestMap = i6000InfoDTO.getRequestMap();
		I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();

		List<I6000CiCientityDTO.Conditions> orCondition = i6000InfoDTO.getRequestMap().entrySet().stream()
			.map(item -> {
				I6000CiCientityDTO.Conditions conditions = new I6000CiCientityDTO.Conditions();
				conditions.setAttrCode(item.getKey());
				conditions.setOperator("=");
				conditions.setValue(item.getValue());
				return conditions;
			}).collect(Collectors.toList());
		i6000CiCientityDTO.setConditions(orCondition);
		if (CollectionUtils.isEmpty(requestMap)) {
			List<Map<String, Object>> ciAttrList = i6000Service.selectCiAttr(ciCodeRequest);
			List<String> i6000CiAttrs = new ArrayList<>();
			for (Map<String, Object> ciAttrMap : ciAttrList) {
				List<Map<String, Object>> attrData = (List<Map<String, Object>>) ciAttrMap.get(I6000Constant.ATTR_DATA);
				for (Map<String, Object> attrDatum : attrData) {
					String attrCode = String.valueOf(attrDatum.get(I6000Constant.ATTR_CODE));
					i6000CiAttrs.add(attrCode);
				}
			}
			String attrCodes = i6000CiAttrs.stream().collect(Collectors.joining(","));
			i6000CiCientityDTO.setAttrCode(attrCodes);
		} else {
			if (StringUtils.hasLength(i6000InfoDTO.getRequestAttr())) {
				i6000CiCientityDTO.setAttrCode(i6000InfoDTO.getRequestAttr());
			} else {
				String attrCodes = i6000InfoDTO.getRequestMap().keySet().stream().collect(Collectors.joining(","));
				i6000CiCientityDTO.setAttrCode(attrCodes);
			}
		}
		i6000CiCientityDTO.setPageStart("1");
		i6000CiCientityDTO.setPageSize("1000");
		List<Map<String, Object>> ciCientityList = i6000Service.selectCiCientity(ciCodeRequest, i6000CiCientityDTO);

		I6000Info i6000Info = Convert.convert(new TypeReference<I6000Info>() {}, i6000InfoDTO);
		i6000Info.setResponse(JSONObject.toJSONString(ciCientityList));

		return this.save(i6000Info);
	}

}

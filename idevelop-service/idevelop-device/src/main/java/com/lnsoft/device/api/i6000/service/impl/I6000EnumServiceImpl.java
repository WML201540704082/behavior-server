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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.i6000.entity.I6000Enum;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.i6000.vo.I6000EnumVO;
import com.lnsoft.device.api.i6000.mapper.I6000EnumMapper;
import com.lnsoft.device.api.i6000.service.II6000EnumService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lnsoft.device.constant.I6000Constant.ITEMS;
import static com.lnsoft.device.constant.I6000Constant.RESULT_VALUE;

/**
 * i6000枚举数据表 服务实现类
 *
 * @author Idevelop
 * @since 2024-08-02
 */
@Service
public class I6000EnumServiceImpl extends BaseServiceImpl<I6000EnumMapper, I6000Enum> implements II6000EnumService {
	@Resource
	private II6000Service ii6000Service;
	@Override
	public IPage<I6000EnumVO> selectI6000EnumPage(IPage<I6000EnumVO> page, I6000EnumVO i6000Enum) {
		return page.setRecords(baseMapper.selectI6000EnumPage(page, i6000Enum));
	}

	@Override
	public R insert(I6000Enum i6000Enum) {
		try {
			JSONObject jsonObject = ii6000Service.selectEnumByID(i6000Enum.getEnumId(), null, null);
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> innerMap = jsonObject.getInnerMap();

			String resultValue = innerMap.get(RESULT_VALUE).toString();
			Map<String, Object> resultValueMap = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
			});

			List<Map<String, Object>> items = (List<Map<String, Object>>) resultValueMap.get(ITEMS);
			for (Map<String, Object> enumDataMap : items) {
				List<Map<String, Object>> enumData = (List<Map<String, Object>>) enumDataMap.get("enumData");
				String enumName = (String) enumDataMap.get("ENUM_NAME");
				for (Map<String, Object> enumDatum : enumData) {
					String enumvalCode = String.valueOf(enumDatum.get("ENUMVAL_CODE"));
					String enumvalId = String.valueOf(enumDatum.get("ENUMVAL_ID"));
					String enumvalName = String.valueOf(enumDatum.get("ENUMVAL_NAME"));
					String enumId = String.valueOf(enumDatum.get("ENUM_ID"));
					I6000Enum anEnum = new I6000Enum();
					anEnum.setEnumId(enumId);
					anEnum.setEnumName(enumName);
					anEnum.setEnumvalCode(enumvalCode);
					anEnum.setEnumvalName(enumvalName);
					anEnum.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
					LambdaQueryWrapper<I6000Enum> queryWrapper = new LambdaQueryWrapper<>();
					queryWrapper.eq(I6000Enum::getEnumvalCode,enumvalCode);
					I6000Enum i6000Enums = baseMapper.selectOne(queryWrapper);
					if (ObjectUtil.isNotEmpty(i6000Enums)){
						anEnum.setUpdateTime(new Date());
						baseMapper.updateByEnumvalCode(anEnum);
					}else{
						anEnum.setCreateTime(new Date());
						baseMapper.insert(anEnum);
					}
				}
			}
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return R.success("操作成功");
	}

}

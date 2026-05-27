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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.vo.HardwareBasicTreeVO;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.mapper.HardwareBasicTreeMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbResourcecenterTypeCiService;
import com.lnsoft.device.api.cmdb.service.IHardwareBasicTreeService;
import com.lnsoft.device.api.cmdb.wrapper.HardwareBasicTreeWrapper;
import com.lnsoft.device.props.CmdbDictProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 资产台账模型树管理表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Service
@AllArgsConstructor
public class HardwareBasicTreeServiceImpl extends BaseServiceImpl<HardwareBasicTreeMapper, HardwareBasicTree> implements IHardwareBasicTreeService {

	private static Integer total;
	private static final Map<Object, Object> deviceClaccifyMap = new HashMap<>();
	private static final Map<Object, Object> deviceTypeMap = new HashMap<>();
	private static final Map<Object, Object> deviceTypeErpMap = new HashMap<>();
	private static final Map<String, String> deviceTypeI6000Map = new HashMap<>();


	private RedisUtil redisUtil;
	private ICmdbResourcecenterTypeCiService cmdbResourcecenterTypeCiService;
	private CmdbDictProperties cmdbDictProperties;


	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param hardwareBasicTree
	 * @return
	 */
	@Override
	public IPage<HardwareBasicTreeVO> selectHardwareBasicTreePage(IPage<HardwareBasicTreeVO> page, HardwareBasicTreeVO hardwareBasicTree) {
		return page.setRecords(baseMapper.selectHardwareBasicTreePage(page, hardwareBasicTree));
	}

	/**
	 * 刷新 资产台账模型树管理表
	 *
	 * @return
	 */
	@Override
	public String refresh(String keyword) {
		JSONObject body = HardwareBasicTreeWrapper.build().resourcetypeTree(keyword);

		deviceClaccifyMap.putAll(cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify()));
		deviceTypeMap.putAll(cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceType()));
		deviceTypeErpMap.putAll(cmdbDictProperties.getDictErpMapByCiId(cmdbDictProperties.getDeviceType()));
		deviceTypeI6000Map.putAll(cmdbDictProperties.getDictI6000MapByCiId(cmdbDictProperties.getDeviceType()));

		total = 0;
		if (Objects.isNull(body.getJSONArray("Return"))) {
			return String.format("本次刷新数据共 { %s } 条.", total);
		}
		this.jsonToResourceType(body.getJSONArray("Return"));
		return String.format("本次刷新数据共 { %s } 条.", total);
	}

	/**
	 * 批量删除数据(物理)
	 *
	 * @param ciIds
	 * @return
	 */
	@Override
	public boolean deleteByCiIds(List<Long> ciIds) {
		return baseMapper.deleteByCiIds(ciIds);
	}

	private void jsonToResourceType(JSONArray returnArray) {
		if (!CollectionUtils.isEmpty(returnArray)) {
			JSONObject jsonObject = returnArray.getJSONObject(0);
			Long parentId = jsonObject.getLong("parentId");
			this.parseResourceType(jsonObject, 0, parentId);
		}

	}

	private void parseResourceType(JSONObject jsonObject, int level, Long parentId) {


		Long id = jsonObject.getLong("id");
		String label = jsonObject.getString("label");
		Integer isMenu = jsonObject.getInteger("isMenu");
		String name = jsonObject.getString("name");
		String icon = jsonObject.getString("icon");
		Long typeId = jsonObject.getLong("typeId");
		Long parentCmdbId = jsonObject.getLong("parentId");
		Long parentCiId = parentId;

		if (level == 0 || level == 10) {
			parentId = id;
		}

		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setCiId(id);
		hardwareBasicTree.setCiName(name);
		hardwareBasicTree.setCiLabel(label);
		hardwareBasicTree.setParentCiId(parentCiId);
		hardwareBasicTree.setParentCmdbId(parentCmdbId);
		hardwareBasicTree.setLevel(level);
		hardwareBasicTree.setIsMenu(String.valueOf(isMenu));
		hardwareBasicTree.setCiIcon(icon);
		hardwareBasicTree.setCiTypeId(typeId);

		String deviceClaccify = null;
		for (Map.Entry<Object, Object> entry : deviceClaccifyMap.entrySet()) {
			if (StringUtils.pathEquals(label, String.valueOf(entry.getValue()))) {
				deviceClaccify = String.valueOf(entry.getKey());
				break;
			}
		}
		if (!StringUtils.hasLength(deviceClaccify)){
			for (Map.Entry<Object, Object> entry : deviceTypeMap.entrySet()) {
				if (StringUtils.pathEquals(label, String.valueOf(entry.getValue()))) {
					String key = String.valueOf(entry.getKey());
					String erp  = String.valueOf(deviceTypeErpMap.get(key));
					if (StringUtils.hasLength(erp)) {
						hardwareBasicTree.setErpCode(erp);
						String i6000 = deviceTypeI6000Map.get(key);
						hardwareBasicTree.setI6000Code(i6000);
					}
					hardwareBasicTree.setDeviceType(String.valueOf(entry.getKey()));
					break;
				}
			}
		}
		// if (isMenu == 1) {
		// 	setCiAttrGlobal(id, hardwareBasicTree);
		// }

		boolean saveOrUpdate = saveOrUpdate(hardwareBasicTree);
		if (saveOrUpdate) {
			total++;
		}

		JSONArray children = jsonObject.getJSONArray("children");
		if (!CollectionUtils.isEmpty(children)) {
			for (int i = 0; i < children.size(); i++) {
				parseResourceType(children.getJSONObject(i), level + 1, parentId);
			}
		}
	}

	// /**
	//  * 获取全局属性和 模型所有属性
	//  *
	//  * @param id
	//  * @param hardwareBasicTree
	//  */
	// private static void setCiAttrGlobal(Long id, HardwareBasicTree hardwareBasicTree) {
	// 	FeignCmdbCiListattr feignCmdbCiListattr = new FeignCmdbCiListattr();
	// 	feignCmdbCiListattr.setCiId(id);
	// 	feignCmdbCiListattr.setAllowEdit(1);
	// 	JSONObject ciListattrBody = HardwareBasicTreeWrapper.build().feignCiListattrBy(feignCmdbCiListattr);
	//
	// 	FeignCmdbCiListglobalattr feignCmdbCiListglobalattr = new FeignCmdbCiListglobalattr();
	// 	feignCmdbCiListglobalattr.setCiId(id);
	// 	feignCmdbCiListglobalattr.setAllowEdit(1);
	// 	feignCmdbCiListglobalattr.setIsActive(1);
	// 	JSONObject ciListglobalattrBody = HardwareBasicTreeWrapper.build().feignCiListglobalattr(feignCmdbCiListglobalattr);
	//
	// 	ciListattrBody.putAll(ciListglobalattrBody);
	// 	hardwareBasicTree.setCiAttrGlobal(ciListattrBody.toJSONString());
	// }

}

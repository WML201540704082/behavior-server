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
package com.lnsoft.device.api.cmdb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.CmdbCiAttr;
import com.lnsoft.cmdb.vo.CmdbCiAttrVO;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.res.enums.AttrMappingType;

import java.util.Map;

/**
 * 模型属性映射表 服务类
 *
 * @author Idevelop
 * @since 2024-02-23
 */
public interface ICmdbCiAttrService extends BaseService<CmdbCiAttr> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cmdbCiAttr
	 * @return
	 */
	IPage<CmdbCiAttrVO> selectCmdbCiAttrPage(IPage<CmdbCiAttrVO> page, CmdbCiAttrVO cmdbCiAttr);

	/**
	 * 刷新 模型属性映射表
	 *
	 * @param ciId
	 * @param ciName
	 * @return
	 */
	String refreshCiAttr(Long ciId, String ciName);


	/**
	 * 查询模型下所有的属性
	 *
	 * @param ciId
	 * @return
	 */
	Map<Object, Object> selectCiAttrMap(Long ciId, AttrMappingType returnType);

	/**
	 * 查询模型下所有的属性2.0
	 *
	 * @param ciId
	 * @return
	 */
	Map<String, CmdbCiAttr> selectCiAttr(Long ciId);
}

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
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.entity.CmdbCiAttrGrade;
import com.lnsoft.device.api.cmdb.vo.CmdbCiAttrGradeVO;

import java.util.List;

/**
 * 模型属性映射表(编辑) 服务类
 *
 * @author Idevelop
 * @since 2024-03-14
 */
public interface ICmdbCiAttrGradeService extends BaseService<CmdbCiAttrGrade> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cmdbCiAttrGrade
	 * @return
	 */
	IPage<CmdbCiAttrGradeVO> selectCmdbCiAttrGradePage(IPage<CmdbCiAttrGradeVO> page, CmdbCiAttrGradeVO cmdbCiAttrGrade);

	/**
	 * 重写删除
	 *
	 * @param ids
	 * @return
	 */
	Boolean deleteLogicNew(List<String> ids);

	/**
	 * 刷新 模型属性映射表
	 *
	 * @param ciId
	 * @param ciName
	 * @return
	 */
	String refreshCiAttr(Long ciId, String ciName);


	String refreshCiAttrLs(Long deviceClaccify);
}

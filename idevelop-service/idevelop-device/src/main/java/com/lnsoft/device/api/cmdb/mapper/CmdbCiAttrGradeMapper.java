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
package com.lnsoft.device.api.cmdb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.entity.CmdbCiAttrGrade;
import com.lnsoft.device.api.cmdb.vo.CmdbCiAttrGradeVO;

import java.util.List;
import java.util.Set;

/**
 * 模型属性映射表(编辑) Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-14
 */
public interface CmdbCiAttrGradeMapper extends BaseMapper<CmdbCiAttrGrade> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cmdbCiAttrGrade
	 * @return
	 */
	List<CmdbCiAttrGradeVO> selectCmdbCiAttrGradePage(IPage page, CmdbCiAttrGradeVO cmdbCiAttrGrade);


	/**
	 * 获取CmdbAttrCiIdList
	 *
	 * @param ciId
	 * @return
	 */
	List<String> selectCmdbAttrCiIdList(Long ciId);

	/**
	 * 根据ciIds删除数据
	 *
	 * @param ciIds
	 * @return
	 */
	Boolean delectCmdbCiAttrGradeList(Set<String> ciIds);

}

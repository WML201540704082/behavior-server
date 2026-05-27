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
import com.lnsoft.device.entity.IscDept;
import com.lnsoft.device.entity.StockDept;
import com.lnsoft.device.entity.StockUnitDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Idevelop
 * @since 2024-05-30
 */
public interface IscDeptMapper extends BaseMapper<IscDept> {

	/**
	 * 获取需要处理的单位和部门
	 *
	 * @return
	 */
	List<StockUnitDept> selectIscDept(StockUnitDept stockUnitDept);


	/**
	 * 获取部门信息
	 *
	 * @param iscDeptList
	 * @return
	 */
	List<StockDept> selectDeptIdByIscId(List<String> iscDeptList);


	/**
	 * 根据分页获取需要处理的配置项ID
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Long> selectCientityId(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("isSuccess") Integer isSuccess);

	/**
	 * 根据ID更新状态
	 *
	 * @param cientityId
	 * @param isSuccess
	 * @return
	 */
	Boolean updateCientityId(@Param("cientityId") Long cientityId, @Param("isSuccess") Integer isSuccess);
}

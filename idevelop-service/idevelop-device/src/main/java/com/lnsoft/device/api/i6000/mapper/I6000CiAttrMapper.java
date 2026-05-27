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
package com.lnsoft.device.api.i6000.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import com.lnsoft.device.api.i6000.vo.I6000CiAttrVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * i6000模型属性 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-19
 */
public interface I6000CiAttrMapper extends BaseMapper<I6000CiAttr> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000CiAttr
	 * @return
	 */
	List<I6000CiAttrVO> selectI6000CiAttrPage(IPage page, I6000CiAttrVO i6000CiAttr);

	/**
	 * 更新是否与CMDB映射
	 *
	 * @param cmdbAttrCode
	 * @return
	 */
	Boolean updateByAttrCode(String cmdbAttrCode, String ciCode);

	/**
	 * 根据I6000 ciTypeId 的获取I6000所有的模型属性
	 *
	 * @param ciTypeId
	 * @return
	 */
	List<String> selectI6000AttrByCiTypeId(@Param("ciCode") String ciCode);
}

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
package com.lnsoft.device.api.safeaccess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.device.dto.SafeaccessSwitcheDTO;
import com.lnsoft.device.entity.SafeaccessSwitche;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 交换机管理 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-07
 */
public interface SafeaccessSwitcheMapper extends BaseMapper<SafeaccessSwitche> {


	@Select("select sw_ip from idevelop_safeaccess_switche where subnet_id=#{subnet} and is_deleted = 0")
	List<String> selectSwitches(String subnet);

	/**
	 * 投运批量更新交换机数据
	 *
	 * @param safeAccessSwitchesDTOList 交换机数据
	 */
	void updateBatchSafeAccessSwitches(@Param("list") List<SafeaccessSwitcheDTO> safeAccessSwitchesDTOList);
}

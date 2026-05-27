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
package com.lnsoft.device.api.res.mapper;

import com.lnsoft.device.api.res.vo.HussarBpmUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批流程记录表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface HussarBpmMapper {

	/**
	 * 根据获取的用户id获取用户信息
	 *
	 * @param assigneeList 用户id
	 * @return List
	 */
	List<HussarBpmUserVO> selectUserList(@Param("assigneeList") List<String> assigneeList);
}

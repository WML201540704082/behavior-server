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

import com.lnsoft.device.api.i6000.entity.I6000Dept;
import com.lnsoft.device.api.i6000.vo.I6000DeptVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * I6000部门 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-04-12
 */
public interface I6000DeptMapper extends BaseMapper<I6000Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000Dept
	 * @return
	 */
	List<I6000DeptVO> selectI6000DeptPage(IPage page, I6000DeptVO i6000Dept);

	/**
	 * 查询单位id列表
	 * @return
	 */
    List<String> selectUnitId();

}

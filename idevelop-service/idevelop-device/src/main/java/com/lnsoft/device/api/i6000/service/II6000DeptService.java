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
package com.lnsoft.device.api.i6000.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.i6000.dto.I6000DeptDTO;
import com.lnsoft.device.api.i6000.entity.I6000Dept;
import com.lnsoft.device.api.i6000.vo.I6000DeptVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.i6000.vo.I6000UnitDeptVO;
import com.lnsoft.device.api.i6000.vo.I6000XtythUnitVO;

import java.util.List;

/**
 * I6000部门 服务类
 *
 * @author Idevelop
 * @since 2024-04-12
 */
public interface II6000DeptService extends BaseService<I6000Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000Dept
	 * @return
	 */
	IPage<I6000DeptVO> selectI6000DeptPage(IPage<I6000DeptVO> page, I6000DeptVO i6000Dept);

	/**
	 * 懒加载 I6000单位
	 * @return
	 */
	List<I6000XtythUnitVO> lazyI6000Unit();


	/**
	 * 懒加载 I6000单位和部门
	 *
	 * @return
	 */
	I6000UnitDeptVO lazyI6000UnitDept(String unitCode);


	R insert();

	R marry();
}

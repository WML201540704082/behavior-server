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
import com.lnsoft.device.api.i6000.entity.I6000Unit;
import com.lnsoft.device.api.i6000.vo.I6000UnitVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * I6000单位 服务类
 *
 * @author Idevelop
 * @since 2024-04-12
 */
public interface II6000UnitService extends BaseService<I6000Unit> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000Unit
	 * @return
	 */
	IPage<I6000UnitVO> selectI6000UnitPage(IPage<I6000UnitVO> page, I6000UnitVO i6000Unit);

	/**
	 * 获取并更新单位
	 * @return
	 */
    R inert();

//	/**
//	 * 单位匹配接口
//	 * @return
//	 */
//	R marry();
}

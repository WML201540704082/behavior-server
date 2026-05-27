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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.i6000.entity.I6000Cabinet;
import com.lnsoft.device.api.i6000.vo.I6000CabinetVO;

/**
 * I6000单位 服务类
 *
 * @author Idevelop
 * @since 2025-02-24
 */
public interface II6000CabinetService extends BaseService<I6000Cabinet> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000Cabinet
	 * @return
	 */
	IPage<I6000CabinetVO> selectI6000CabinetPage(IPage<I6000CabinetVO> page, I6000CabinetVO i6000Cabinet);

}

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
import com.lnsoft.device.api.i6000.entity.I6000Enum;
import com.lnsoft.device.api.i6000.vo.I6000EnumVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * i6000枚举数据表 服务类
 *
 * @author Idevelop
 * @since 2024-08-02
 */
public interface II6000EnumService extends BaseService<I6000Enum> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000Enum
	 * @return
	 */
	IPage<I6000EnumVO> selectI6000EnumPage(IPage<I6000EnumVO> page, I6000EnumVO i6000Enum);

	/**
	 * 获取枚举数据入库
	 * @param i6000Enum
	 * @return
	 */
    R insert(I6000Enum i6000Enum);
}

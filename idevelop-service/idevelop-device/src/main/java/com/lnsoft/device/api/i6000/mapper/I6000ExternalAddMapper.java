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

import com.lnsoft.device.api.i6000.entity.I6000ExternalAdd;
import com.lnsoft.device.api.i6000.vo.I6000ExternalAddVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 外部数据表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-06-18
 */
public interface I6000ExternalAddMapper extends BaseMapper<I6000ExternalAdd> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000ExternalAdd
	 * @return
	 */
	List<I6000ExternalAddVO> selectI6000ExternalAddPage(IPage page, I6000ExternalAddVO i6000ExternalAdd);

}

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

import com.lnsoft.device.api.i6000.entity.I6000ExternalAdd;
import com.lnsoft.device.api.i6000.vo.I6000ExternalAddVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 外部数据表 服务类
 *
 * @author Idevelop
 * @since 2024-06-18
 */
public interface II6000ExternalAddService extends BaseService<I6000ExternalAdd> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000ExternalAdd
	 * @return
	 */
	IPage<I6000ExternalAddVO> selectI6000ExternalAddPage(IPage<I6000ExternalAddVO> page, I6000ExternalAddVO i6000ExternalAdd);
	/**
	 * 根据外部数据code删除
	 */
	Integer delete(String extCode);
}

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
package com.lnsoft.device.api.safeaccess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.safeaccess.dto.SdnUserAccessDTO;
import com.lnsoft.device.api.safeaccess.entity.SdnUserAccess;
import com.lnsoft.device.api.safeaccess.vo.SdnUserSdnUserAccessVO;

import java.util.Map;

/**
 *  服务类
 *
 * @author Idevelop
 * @since 2025-04-19
 */
public interface ISdnUserAccessService extends BaseService<SdnUserAccess> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param access
	 * @return
	 */
	IPage<SdnUserSdnUserAccessVO> selectAccessPage(IPage<SdnUserSdnUserAccessVO> page, SdnUserSdnUserAccessVO access);

	/**
	 * 分页
	 * @param page
	 * @param sdnUserAccess
	 * @return
	 */
	IPage<SdnUserAccess> getList(IPage<Object> page, SdnUserAccessDTO sdnUserAccess);

	Map<String, Object> getUserAccessList(SdnUserAccessDTO sdnUserAccessDTO);

	Map<String, Object> delUserAccess(SdnUserAccessDTO sdnUserAccessDTO);

	Map<String, Object> updateUserAccess(SdnUserAccessDTO sdnUserAccessDTO);
}

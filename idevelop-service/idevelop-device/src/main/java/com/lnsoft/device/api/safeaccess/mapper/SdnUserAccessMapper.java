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

import com.lnsoft.device.api.safeaccess.entity.SdnUserAccess;
import com.lnsoft.device.api.safeaccess.vo.SdnUserSdnUserAccessVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Idevelop
 * @since 2025-04-19
 */
public interface SdnUserAccessMapper extends BaseMapper<SdnUserAccess> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param access
	 * @return
	 */
	List<SdnUserSdnUserAccessVO> selectAccessPage(IPage page, SdnUserSdnUserAccessVO access);

	/**
	 * 查询地市地址
	 * @param regionCode
	 * @return
	 */
    String getUrl(String regionCode);
}

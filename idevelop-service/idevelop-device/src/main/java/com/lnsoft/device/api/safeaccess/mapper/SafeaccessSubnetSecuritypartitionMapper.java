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

import com.lnsoft.device.api.safeaccess.entity.SafeaccessSubnetSecuritypartition;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessSubnetSecuritypartitionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 子网安全分区管理 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-11
 */
public interface SafeaccessSubnetSecuritypartitionMapper extends BaseMapper<SafeaccessSubnetSecuritypartition> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param safeaccessSubnetSecuritypartition
	 * @return
	 */
	List<SafeaccessSubnetSecuritypartitionVO> selectSafeaccessSubnetSecuritypartitionPage(IPage page, SafeaccessSubnetSecuritypartitionVO safeaccessSubnetSecuritypartition);

}

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
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.safeaccess.dto.DatchSyncRadiusDTO;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessUserAccessSaveDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.dto.SafeaccessUserAccessDTO;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.lnsoft.device.so.SafeaccessUserAccessSO;

import java.util.List;
import java.util.Map;

/**
 * 终端用户入网信息表 服务类
 *
 * @author Idevelop
 * @since 2024-03-09
 */
public interface ISafeaccessUserAccessService extends BaseService<SafeaccessUserAccess> {

	List<SafeaccessUserAccess> findByConditionSubnetId(Map<String, Object> map);

	void saveEditNet(SafeaccessEditSubnet csTEditNet);

	void updateUserAccess(Map<String, String> userAccessMap);

	IPage<SafeaccessUserAccess> selectSafeaccessUserAccessPage(SafeaccessUserAccess safeaccessUserAccess, Query query);

	/**
	 * 根据ip地址查询
	 *
	 * @param ipAddress
	 * @return
	 */
	SafeaccessUserAccessDTO fingUserAccessByIpAddress(String ipAddress);

	boolean syncRadius(String id);

	R<Object> batchSyncRadius(DatchSyncRadiusDTO datchSyncRadiusDTO);

	IPage<SafeaccessUserAccessDTO> customPage(SafeaccessUserAccessSO safeaccessUserAccess, Query query, IdevelopUser sysUser);

	List<SafeaccessUserAccessDTO> customList(SafeaccessUserAccessSO safeaccessUserAccess, IdevelopUser sysUser);

	void saveUserAccess(SafeaccessUserAccessSaveDTO dto);

	SafeaccessUserAccessDTO customGetOne(SafeaccessUserAccess safeaccessUserAccess);

	/**
	 * 用户入网恢复
	 *
	 * @param id 用户入网id
	 * @return R
	 */
	R<Integer> accessSafeAccess(String id);
}

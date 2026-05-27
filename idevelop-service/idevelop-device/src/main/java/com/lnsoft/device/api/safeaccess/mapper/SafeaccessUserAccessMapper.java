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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.device.dto.SafeaccessUserAccessDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.device.so.SafeaccessUserAccessSO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 终端用户入网信息表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-09
 */
public interface SafeaccessUserAccessMapper extends BaseMapper<SafeaccessUserAccess> {

	/**
	 * 网段变更-记录
	 *
	 * @param csTEditNet
	 */
	void saveEditNet(SafeaccessEditSubnet csTEditNet);

	/**
	 * 更新子网 ip
	 *
	 * @param userAccessMap
	 */
	@Update("update idevelop_safeaccess_user_access set ip_address = #{newIp},subnet_id = #{subnetNew} where id=#{id}")
	void updateUserAccess(Map<String, String> userAccessMap);

	Map<String, String> findRadiusMap(String id);

	SafeaccessSubnet findSubnetById(String id);

	List<Map<String, Object>> getApplyTypeByDeviceNoList(Map<String, Object> params);

	List<Map<String, Object>> getEquipmentByDeviceNoAndIdList(Map<String, Object> param);

	IPage<SafeaccessUserAccessDTO> customPage(Page<SafeaccessUserAccessDTO> page, SafeaccessUserAccessSO param);
	List<SafeaccessUserAccessDTO> customList(@Param("param") SafeaccessUserAccessSO param);

	/**
	 * 自定义查询
	 *
	 * @param safeaccessUserAccess
	 * @return
	 */
	SafeaccessUserAccessDTO customGetOne(SafeaccessUserAccess safeaccessUserAccess);

	/**
	 * @param safeaccessUserAccess
	 * 根据ip和mac获取认证用户和密码（变更用）
	 * @return
	 */
	@Select("SELECT * FROM idevelop_safeaccess_user_access where is_deleted=0 and ip_address = #{ipAddress} and mac_address = #{macAddress}")
	SafeaccessUserAccessDTO getApproveuUserAndPassword(SafeaccessUserAccess safeaccessUserAccess);
}

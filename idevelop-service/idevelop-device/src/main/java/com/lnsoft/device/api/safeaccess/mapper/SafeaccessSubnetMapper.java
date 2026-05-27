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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.device.vo.SafeaccessSubnetVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 子网管理表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-08
 */
public interface SafeaccessSubnetMapper extends BaseMapper<SafeaccessSubnet> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param safeaccessSubnet
	 * @return
	 */
	List<SafeaccessSubnetVO> selectSafeaccessSubnetPage(IPage page, SafeaccessSubnetVO safeaccessSubnet);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, String>> findByCondition(Map<String, Object> map);

	/**
	 * 根据子网id查询所有ip
	 *
	 * @param ipMap
	 * @return
	 */
	@Select("select IP from ${tableName} where subnet = #{subnetId}")
	List<String> queryIpBySubnet(Map<String, String> ipMap);

	/**
	 * 查询新生成的ip是否已存在
	 *
	 * @param list
	 * @param tableName
	 * @return
	 */
	int queryNewIpBySubnet(List<String> list, String tableName);

	@Select("select ip from ${tableName} where subnet = #{subnetId} and is_used = '1' order by inet_aton(ip)")
	List<String> queryUsedIpBySubnet(Map<String, String> paramMap);

	@Select("select ip_address from idevelop_safeaccess_user_access where subnet_id = #{subnetId} order by INET_ATON(ip_address)")
	List<String> queryUsedIpFromUserAccess(String id);

	@Select("select sw_ip from idevelop_safeaccess_switche where bak_col2=#{subnet}")
	List<String> selectSwitches(String subnetId);

	@Update("")
	void updateCsTEditNet(SafeaccessEditSubnet csTEditNet);

	void saveEditNet(SafeaccessEditSubnet csTEditNet);

	@Select("select count(1)  from idevelop_safeaccess_subnet_ippool where ippool_segment = #{ippoolSegment}")
	int selectSubnetIppoolByIppoolSegment(String ippoolSegment);

	void insertSubnetIppool(Map<String, String> subnetIppool);

	List<String> selectSubnetIppoolList(Map<String, String> param);

	@Select("SELECT dept_name FROM idevelop_dept WHERE is_deleted = 0 AND id = #{institutionCode}")
	String getDeptName(String institutionCode);

	int hasTerminal(Map<String, Object> map);

	/**
	 * 网络变更同步
	 *
	 * @param deviceChangeList
	 * @return
	 */
	Integer deviceChangeUpdate(DeviceChangeList deviceChangeList);

	void initUserAccess();

	void updateUserAccessRegionCode();

	void initSubnet();

	void updateSubnetRegionCode();

	void updateSubnetInstitutionCode();

	void updateSubnetInstitutionName();

	@Select("SELECt * FROM idevelop_safeaccess_subnet WHERE is_deleted = 0 and region_code like CONCAT(#{deptCode},'%')")
	List<SafeaccessSubnet> selectAll(String deptCode);

    void updateUserAccessCompany();

	void updateUserAccessDepartment();

	void initSwitches();

	void updateRegionCode();

	void updateDeptCode();

	void deviceChangeUpdateOnline(DeviceChangeList deviceChangeList);

	/**
	 * 删除用户入网表
	 * @param deviceChangeList
	 */
	void deviceChangeDelete(DeviceChangeList deviceChangeList);
}

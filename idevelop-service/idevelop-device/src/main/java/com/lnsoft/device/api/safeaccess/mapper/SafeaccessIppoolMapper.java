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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.device.api.asset.vo.WorkBenchIpAssignNumberVO;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessIppoolDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessIppoolVO;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IP地址池 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-08
 */
public interface SafeaccessIppoolMapper extends BaseMapper<SafeaccessIppool> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param safeaccessIppool
	 * @return
	 */
	List<SafeaccessIppoolVO> selectSafeaccessIppoolPage(IPage page, SafeaccessIppoolVO safeaccessIppool);

	/**
	 * 批量保存
	 *
	 * @param tableName
	 * @param list
	 */
	void insertIpPool(String tableName, List<Map<String, String>> list);

	/**
	 * 设置网关
	 *
	 * @param map
	 */
	@Update("update ${ipPoolName} set ip_level=3, is_used=2 where ip= #{gateway} and subnet = #{subnet}")
	void updateGatewayByIp(Map<String, String> map);

	/**
	 * 删除ip地址池
	 *
	 * @param saTIppool
	 * @return
	 */
//	@Update("update ${ipPoolName} set is_deleted = 1 where org_no=#{orgNo} and subnet =#{subnet}")
	@Delete("delete from ${ipPoolName} where org_no=#{orgNo} and subnet =#{subnet} ")
	int deleteIpPool(SafeaccessIppoolDTO saTIppool);

	@Select("select ip_address from idevelop_safeaccess_user_access where subnet_id=#{subnet} and is_deleted = 0")
	List<String> selectUserAccess(String subnet);

	void updateIpPool(SafeaccessIppoolDTO saTIppool);

	@Select("select count(1) from ${ipPoolName} where subnet = #{subnet} and is_used = #{isUsed}")
	int getIpUsedCount(Map<String, String> ippool);

	@Update("update ${ipPoolName} t set t.is_used = '1' where t.ip = #{newIp} and t.org_no=#{orgNo}")
	void updateIpPoolNewSubnet(Map tmap);

	@Update("update ${ipPoolName} t set t.is_used = '0' where t.is_used = '1' and t.org_no=#{orgNo} and t.subnet = #{subnet}")
	void updateIpPoolOld(SafeaccessIppoolDTO ippool);

	int updateIpLevel(SafeaccessIppoolDTO ippool);

	IPage<SafeaccessIppoolDTO> searchByPage(Page<SafeaccessIppoolDTO> page, SafeaccessIppoolDTO param);

	List<SafeaccessIppoolDTO> search(Map<String, String> map);

	@Update("update ${ipPoolName} set ip_level=3, is_used=2 where ip_id = #{ipId}")
	int setNewGateway(SafeaccessIppoolDTO ippool);

	List<SafeaccessIppoolDTO> getQueryList(Map<String, String> map);

	List<Map<String, Object>> getDetails(HashMap<String, Object> param);

	@Select("select count(1) from idevelop_safeaccess_user_access where ip_address = #{ipAddress} and dept_code =#{deptCode}")
	int selectByAccessIp(SafeaccessUserAccess csTUserAccess);

	@Update("update ${ipPoolName} set is_used = #{isUsed} where ip_id  = #{ipId}")
	int updateIsUsed(SafeaccessIppoolDTO ippool);

	@Select("select subnet as subnetId,ip as ipAddress from  ${ipPoolName} stip where stip.ip =#{authIp}")
	Map<String, String> selectByAuthId(Map<String, String> map);

	Map<String, Object> selectSwitches(@Param("subnetId") String subnetId);

	Map<String, Object> selectSdnSwitchPort(Map<String, String> map);

	Map<String, Object> selectInterface(Map<String, String> map);

	@Select("select * from ${ipPoolName} WHERE is_deleted = 0 and ip_id =#{ipId}")
	SafeaccessIppool selectCustomById(SafeaccessIppoolDTO ippool);

	@Update("update ${ipPoolName} set is_used = 0 where is_deleted = 0 and subnet = #{subnetId} and ip = #{ip} ")
	int releaseIpBySubnetIdAndIp(String ipPoolName, String subnetId, String ip);

	/**
	 * IP地址池进行占用处理
	 *
	 * @param updateSurface 修改表
	 * @param deviceIpList  投运设备ip信息信息
	 * @param userId        更新人id
	 * @param ipUse         标识
	 * @param deviceSubnet  子网
	 */
	void updateBatchIpPool(@Param("updateSurface") String updateSurface,
						   @Param("deviceIpList") List<String> deviceIpList,
						   @Param("userId") Long userId,
						   @Param("ipUse") String ipUse,
						   @Param("deviceSubnet") String deviceSubnet);

	List<SafeaccessIppoolDTO> searchNoPage(SafeaccessIppoolDTO safeaccessIppool);

	/**
	 * 校验所选设备IP是否被占用
	 *
	 * @param updateSurface 查询表
	 * @param deviceIpList  投运设备ip信息信息
	 * @return int
	 */
	int selectBatchIpCount(@Param("updateSurface") String updateSurface, @Param("deviceIpList") List<String> deviceIpList);

	/**
	 * 占用ip地址 变更用
	 *
	 * @param ippool
	 * @return
	 */
	SafeaccessIppool selectBySubnetIdAndIp(SafeaccessIppoolDTO ippool);

	/**
	 * 个人工作台 - IP资源：IP地址总数
	 *
	 * @param regionCode
	 * @param ipPoolDB
	 * @return
	 */
	Integer getIpAllNumber(@Param("regionCode") String regionCode, @Param("ipPoolDB") String ipPoolDB);

	/**
	 * 人工作台 - IP资源：内网/外网
	 *
	 * @param regionCode
	 * @param ipPoolDB
	 * @return
	 */
	List<WorkBenchIpAssignNumberVO> getIpAssignNumber(@Param("regionCode") String regionCode,
													  @Param("ipPoolDB") String ipPoolDB,
													  @Param("networkTypeList") List<String> networkTypeList);

	/**
	 * 根据区域表查询ip
	 *
	 * @param updateSurface 查询表名
	 * @param deviceIp      设备ip
	 * @return SafwaccessIppool
	 */
	SafeaccessIppool selectIpPoolOne(@Param("updateSurface") String updateSurface, @Param("deviceIp") String deviceIp);

	@Select("select * from ${tableName} WHERE is_deleted = 0 and ip =#{deviceIp}")
	List<SafeaccessIppool> getSubnetList(String tableName, String deviceIp);
}

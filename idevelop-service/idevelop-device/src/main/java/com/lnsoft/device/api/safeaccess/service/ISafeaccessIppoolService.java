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
import com.lnsoft.device.api.safeaccess.dto.SafeaccessIppoolDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.vo.SafeaccessIppoolVO;

import java.util.List;
import java.util.Map;

/**
 * IP地址池 服务类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
public interface ISafeaccessIppoolService extends BaseService<SafeaccessIppool> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param safeaccessIppool
	 * @return
	 */
	IPage<SafeaccessIppoolVO> selectSafeaccessIppoolPage(IPage<SafeaccessIppoolVO> page, SafeaccessIppoolVO safeaccessIppool);

	/**
	 * 批量新增
	 *
	 * @param tableName
	 * @param ipList
	 */
	void insertIpPool(String tableName, List<Map<String, String>> ipList);

	/**
	 * 设置网关
	 *
	 * @param map
	 */
	void updateGatewayByIp(Map<String, String> map);

	int deleteIpPool(SafeaccessIppoolDTO saTIppool);

	List<String> selectUserAccess(String subnet);

	void updateIpPool(SafeaccessIppoolDTO saTIppool);

	/**
	 * 获取已用地址或空闲地址总数
	 *
	 * @param ippool
	 * @return
	 */
	int queryIpPoolCount(Map<String, String> ippool);

	/**
	 * 占用新子网ip地址
	 *
	 * @param tmap
	 */
	void updateIpPoolNewSubnet(Map tmap);

	/**
	 * 释放原子网ip地址
	 *
	 * @param ippool
	 */
	void updateIpPoolOld(SafeaccessIppoolDTO ippool);

	/**
	 * 根据子网和使用状态查询IP地址池
	 *
	 * @param map
	 * @return
	 */
	List<SafeaccessIppool> selectIpPools(Map<String, Object> map);

	/**
	 * 设置地址级别
	 *
	 * @param ippool
	 * @return
	 */
	int setIpLevel(SafeaccessIppoolDTO ippool);

	/**
	 * 根据子网获取ip地址池
	 *
	 * @param safeaccessIppool
	 * @param query
	 * @return
	 */
	IPage<SafeaccessIppoolDTO> searchByPage(SafeaccessIppoolDTO safeaccessIppool, Query query);

	/**
	 * 设置新网关
	 *
	 * @param ippool
	 * @return
	 */
	int setNewGateway(SafeaccessIppoolDTO ippool);

	/**
	 * 条件查询
	 *
	 * @param map
	 * @return
	 */
	List<SafeaccessIppoolDTO> search(Map<String, String> map);

	/**
	 * 查询ip地址池范围信息
	 *
	 * @param saTIppool
	 * @return
	 */
	Map<String, Object> getQueryList(Map<String, String> saTIppool);

	/**
	 * 根据地址池id查询
	 *
	 * @param ippoolId
	 * @return
	 */
	List<Map<String, Object>> getDetails(String ippoolId);

	/**
	 * 设置使用状态
	 *
	 * @param ippool
	 * @return
	 */
	int setIsUsed(SafeaccessIppoolDTO ippool);

	/**
	 * 根据ip地址查询交换机详细信息
	 *
	 * @param map
	 * @return
	 */
	Map<String, Object> getRadiusIp(Map<String, String> map);

	/**
	 * 释放ip地址
	 *
	 * @param subnetId 子网ip
	 * @param ip       ip地址
	 * @return
	 */
	boolean releaseIpBySubnetIdAndIp(String subnetId, String ip);

	/**
	 * 占用IP地址 变更用
	 *
	 * @param subnetId 子网ip
	 * @param ip       ip地址
	 * @return
	 */
	boolean setIsUsedBySubnetIdAndIp(String subnetId, String ip);

	List<SafeaccessIppoolDTO> searchNoPage(SafeaccessIppoolDTO safeaccessIppool);

	Map<String, Object> getRadiusIp(String ip);

	/**
	 * 根据ip查询所属子网id
	 * @param deviceIp
	 * @return
	 */
	List<SafeaccessIppool> getSubnetList(String deviceIp);
}

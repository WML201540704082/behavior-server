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

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 子网管理表 服务类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
public interface ISafeaccessSubnetService extends BaseService<SafeaccessSubnet> {

	/**
	 * 自定义分页
	 *
	 * @param safeaccessSubnet
	 * @param query
	 * @return
	 */
	IPage<SafeaccessSubnet> selectSafeaccessSubnetPage(SafeaccessSubnet safeaccessSubnet, Query query, IdevelopUser sysUser);

	/**
	 * 自定义保存
	 *
	 * @param safeaccessSubnet
	 * @return
	 */
	List<SafeaccessSubnet> customSave(SafeaccessSubnet safeaccessSubnet);

	/**
	 * 获取子网地址与广播地址
	 *
	 * @param id
	 * @param subnetGateway
	 * @param subnetMask
	 * @return
	 */
	List getSubnetInfo(String id, String subnetGateway, String subnetMask);

	/**
	 * 查询子网中是否有终端存在
	 *
	 * @param subnetId
	 * @return
	 */
	boolean hasTerminal(String subnetId);

	/**
	 * 自定义删除
	 *
	 * @param ids
	 * @return
	 */
	Map<String, List<Map<String, Object>>> customRemove(List<SafeaccessSubnet> ids);

	/**
	 * 初始化并更新单个子网的地址池
	 *
	 * @param subnetId
	 * @return
	 */
	boolean initIpPool(String subnetId);

	/**
	 * 初始化并更新单个子网的地址池
	 *
	 * @param subnetId
	 * @return
	 */
	boolean initIpPool1(String subnetId);

	/**
	 * 子网同步radius
	 *
	 * @param subnetId
	 * @return
	 */
	boolean syncSubnet(String subnetId);

	/**
	 * 根据子网id查询是否存在交换机信息
	 *
	 * @param subnetId
	 * @return
	 */
	boolean selectSwitches(String subnetId);

	/**
	 * 子网网段变更
	 *
	 * @param list
	 * @return
	 */
	List<SafeaccessEditSubnet> updateSubnet(ArrayList<SafeaccessEditSubnet> list);

	/**
	 * 设置公共子网
	 *
	 * @param subnet
	 * @return
	 */
	boolean setCommonSubNet(SafeaccessSubnet subnet);

	/**
	 * 返回子网段
	 *
	 * @param subnetId
	 * @return
	 */
	List<String> ipPoolIdSegmentList(String subnetId);


	/**
	 * 根据所属单位、公共子网获取子网
	 *
	 * @param institutionCode
	 * @param isPublic
	 * @return
	 */
	List<Map<String, String>> queryByParam(String institutionCode, String isPublic);

	/**
	 * 子网修改-同步数据
	 *
	 * @param safeaccessSubnet
	 */
	void syncUpdateBySafeaccessSubnetId(SafeaccessSubnet safeaccessSubnet);

	boolean initIpPoolData();

	boolean initIpPoolInfo(String deptCode);

	/**
	 * 根据区域查询所属子网列表
	 * @param regionCode
	 * @return
	 */
    List<SafeaccessSubnet> getByRegon(String regionCode);
}

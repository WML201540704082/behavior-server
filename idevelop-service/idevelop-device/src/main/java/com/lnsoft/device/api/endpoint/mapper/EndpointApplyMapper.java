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
package com.lnsoft.device.api.endpoint.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.endpoint.entity.EndpointApply;
import com.lnsoft.device.api.endpoint.entity.EndpointPortVO;
import com.lnsoft.device.api.endpoint.vo.EndpointApplyVO;
import com.lnsoft.endpoint.entity.EndpointPortUser;
import com.lnsoft.system.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据共享接口申请表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-07-17
 */
public interface EndpointApplyMapper extends BaseMapper<EndpointApply> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param endpointApply
	 * @return
	 */
	List<EndpointApplyVO> selectEndpointApplyPage(IPage page, EndpointApplyVO endpointApply);

    void insertPort(@Param("portId") String portId, @Param("filingNo") String filingNo,@Param("id")  String id);

	/**
	 * 根据工单id删除
	 * @param applyId
	 */
	void deletePort(@Param("applyId") String applyId);

	/**
	 * 新增工单、接口绑定关系
	 * @param endpointPortUser
	 */
	void insertPortUser(EndpointPortUser endpointPortUser);

	/**
	 * 获取用户列表
	 * @param name
	 * @return
	 */
	List<User> getUserList(String name);

	/**
	 * 根据接口单id查询接口id
	 * @param id
	 * @return
	 */
	EndpointPortVO getPortById(String id);

	/**
	 * 获取接口地址
	 * @param portById
	 * @return
	 */
	String getAddress(String portById);
}

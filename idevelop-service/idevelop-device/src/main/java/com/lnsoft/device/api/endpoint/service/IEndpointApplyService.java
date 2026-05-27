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
package com.lnsoft.device.api.endpoint.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.endpoint.dto.EndpointApplyDTO;
import com.lnsoft.device.api.endpoint.entity.EndpointApply;
import com.lnsoft.device.api.endpoint.entity.EndpointPortVO;
import com.lnsoft.device.api.endpoint.vo.EndpointApplyVO;
import com.lnsoft.device.api.warehouse.dto.OrderUpdateStatusDTO;
import com.lnsoft.system.user.entity.User;

import java.util.List;

/**
 * 数据共享接口申请表 服务类
 *
 * @author Idevelop
 * @since 2024-07-17
 */
public interface IEndpointApplyService extends BaseService<EndpointApply> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param endpointApply
	 * @return
	 */
	IPage<EndpointApplyVO> selectEndpointApplyPage(IPage<EndpointApplyVO> page, EndpointApplyVO endpointApply);

	/**
	 * 暂存
	 * @param endpointApply
	 * @return
	 */
	EndpointApplyVO staging(EndpointApplyDTO endpointApply);

	/**
	 * 提交-发起流程
	 * @param endpointApply
	 * @return
	 */
	EndpointApplyVO submit(EndpointApplyDTO endpointApply) throws Exception;

	/**
	 * 审批
	 * @param orderUpdateStatusDTO
	 * @return
	 */
	R approval(OrderUpdateStatusDTO orderUpdateStatusDTO) throws Exception;

	/**
	 * 获取用户列表
	 * @param name
	 * @return
	 */
	R<List<User>> getUserList(String name);

	/**
	 * 根据申请单id查询接口id
	 * @param detail
	 * @return
	 */
	EndpointPortVO getPortById(EndpointApply detail);
}

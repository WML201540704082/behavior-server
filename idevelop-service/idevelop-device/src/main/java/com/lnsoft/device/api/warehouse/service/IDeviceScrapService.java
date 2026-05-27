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
package com.lnsoft.device.api.warehouse.service;

import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.warehouse.dto.DeviceScrapDTO;
import com.lnsoft.device.api.warehouse.dto.ScrapErpDto;
import com.lnsoft.device.api.warehouse.entity.DeviceScrap;
import com.lnsoft.device.api.warehouse.vo.DeviceScrapVO;

import java.util.List;

/**
 * 设备报废 服务类
 *
 * @author Idevelop
 * @since 2024-03-18
 */
public interface IDeviceScrapService extends BaseService<DeviceScrap> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceScrap
	 * @return
	 */
	IPage<DeviceScrapVO> selectDeviceScrapPage(IPage<DeviceScrapVO> page, DeviceScrapVO deviceScrap);

	/**
	 * 查看设备报废详情
	 * @param dto
	 * @return
	 */
	R<DeviceScrapVO> detail(DeviceScrapDTO dto);

	/**
	 * 分页查询设备报废列表
	 * @param dto
	 * @param query
	 * @return
	 */
	IPage<DeviceScrap> scrapList(DeviceScrapDTO dto, Query query);

	/**
	 * 保存设备报废
	 * @param dto
	 * @return
	 */
	R<DeviceScrapVO> saveScrap(DeviceScrapDTO dto);

	/**
	 * 删除设备报废
	 * @param ids
	 * @return
	 */
	R removeDeviceScraps(String ids);

	/**
	 * 提交设备报废
	 * @param dto
	 * @return
	 */
	R<DeviceScrapVO> deviceScrapSubmit(DeviceScrapDTO dto) throws Exception;

	/**
	 * 获取工作台设备报废列表
	 * @param dto
	 * @param query
	 * @return
	 */
	R<IPage<DeviceScrapVO>> deskDeviceScrapList(DeviceScrapDTO dto, Query query);

	/**
	 * 更新设备报废流程状态
	 * @param dto
	 * @return
	 */
	R<Integer> deskDeviceScrapStatus(DeviceScrapDTO dto) throws Exception;

	/**
	 * 设备报废时填充数据
	 * @return
	 */
	R deviceScrapErpReturn(ScrapErpDto dtoList) throws Exception;

	/**
	 * 校验
	 * @param dto
	 * @return
	 */
	R check(DeviceScrapDTO dto);
}

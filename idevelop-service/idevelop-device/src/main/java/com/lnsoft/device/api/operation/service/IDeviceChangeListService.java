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
package com.lnsoft.device.api.operation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import com.lnsoft.device.api.operation.vo.DeviceChangeListVO;
import com.lnsoft.device.api.stock.dto.HardwareBasicCmdbQueryDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 设备变更 服务类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
public interface IDeviceChangeListService extends BaseService<DeviceChangeList> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceChangeList
	 * @return
	 */
	IPage<DeviceChangeListVO> selectDeviceChangeListPage(IPage<DeviceChangeListVO> page, DeviceChangeListVO deviceChangeList);

	/**
	 * 根据changeId查询列表
	 *
	 *
	 * @param changeId
	 * @return
	 */
    List<DeviceChangeList> getByChangeId(String changeId);
	/**
	 * 根据changeId删除
	 * @param changeId
	 * @return
	 */
	int removeByChangeId(String changeId);
	/**
	 * 根据deviceCode查询
	 * @param deviceCode
	 * @return
	 */
	List<DeviceChangeList> getByDeviceCode(String deviceCode);

	/**
	 * 设备列表导出接口
	 * @param hardwareBasicCmdbQuery
	 * @param response
	 */
    void export(HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery, HttpServletResponse response);

	List<Map<String, Object>> importByExcel(MultipartFile file);
}

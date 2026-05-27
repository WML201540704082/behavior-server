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
package com.lnsoft.device.api.asset.mapper;

import com.lnsoft.device.api.asset.dto.DeviceOldListDTO;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import com.lnsoft.device.api.asset.vo.DeviceOldListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 老旧设备表	 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-06-19
 */
public interface DeviceOldListMapper extends BaseMapper<DeviceOldList> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceOldList
	 * @return
	 */
	List<DeviceOldListVO> selectDeviceOldListPage(IPage page, DeviceOldListVO deviceOldList);

	/**
	 * 获取详情
	 * @param deviceOldList
	 * @return
	 */
	DeviceOldListVO getDetail(DeviceOldListDTO deviceOldList);
}

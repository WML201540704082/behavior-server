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
package com.lnsoft.device.api.warehouse.mapper;

import com.lnsoft.device.api.warehouse.entity.DeviceStorageTemplate;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageTemplateVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设备入库导入模板表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface DeviceStorageTemplateMapper extends BaseMapper<DeviceStorageTemplate> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceStorageTemplate
	 * @return
	 */
	List<DeviceStorageTemplateVO> selectDeviceStorageTemplatePage(IPage page, DeviceStorageTemplateVO deviceStorageTemplate);

	/**
	 * 获取模板实体类地址
	 *
	 * @param deviceCategory
	 * @return
	 */
	@Select("SELECT file_path FROM idevelop_device_storage_template WHERE is_deleted = 0 AND device_category = #{deviceCategory} AND ISNULL(device_type)")
	String getPatchByCode(String deviceCategory);

	@Select("SELECT file_path FROM idevelop_device_storage_template WHERE is_deleted = 0 AND device_type = #{deviceType}")
	String getPatchByTypeCode(String deviceType);
}

/**
 * .
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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageExportDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageList;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageListImportVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageListVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备入库明细表 服务类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface IDeviceStorageListService extends BaseService<DeviceStorageList> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceStorageList
	 * @return
	 */
	IPage<DeviceStorageListVO> selectDeviceStorageListPage(IPage<DeviceStorageListVO> page, DeviceStorageListVO deviceStorageList);

	/**
	 * 根据主表id删除明细表
	 *
	 * @param storageId
	 */
	void deleteByStorageId(String storageId);

	/**
	 * 导入数据校验并回显
	 *
	 * @param file
	 * @param deviceCategory
	 * @return
	 */
	DeviceStorageListImportVO importByExcel(MultipartFile file, String deviceCategory, String deviceType, String deviceSource);

	/**
	 * 自定义批量保存
	 *
	 * @param deviceStorageList
	 * @return
	 */
	Boolean customSaveBatch(List<DeviceStorageList> deviceStorageList);

	/**
	 * excel导出问题
	 *
	 * @param deviceStorageExportDTO
	 */
	void exportByExcel(DeviceStorageExportDTO deviceStorageExportDTO, HttpServletResponse response);

}

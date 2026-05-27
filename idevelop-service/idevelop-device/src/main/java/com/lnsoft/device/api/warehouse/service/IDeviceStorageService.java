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

import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageDTO;
import com.lnsoft.device.api.warehouse.dto.GetFullNameDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceStorage;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageExportSO;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageSO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.warehouse.vo.WarehouseVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备入库表 服务类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface IDeviceStorageService extends BaseService<DeviceStorage> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceStorage
	 * @return
	 */
	IPage<DeviceStorageVO> selectDeviceStoragePage(IPage<DeviceStorageVO> page, DeviceStorageVO deviceStorage);

	/**
	 * 自定义保存
	 *
	 * @param deviceStorage
	 * @return
	 */
	DeviceStorage saveEntry(DeviceStorageDTO deviceStorage);

	/**
	 * 暂存
	 *
	 * @param deviceStorage
	 * @return
	 */
	DeviceStorage tempSave(DeviceStorageDTO deviceStorage);

	/**
	 * 自定义删除
	 *
	 * @param ids
	 * @return
	 */
	Boolean delete(List<Long> ids);

	/**
	 * 导出
	 *
	 * @param so
	 * @param response
	 */
	void export(DeviceStorageExportSO so, HttpServletResponse response);

	/**
	 * 自定义搜索
	 *
	 * @param deviceStorage
	 * @param query
	 * @return
	 */
	IPage<DeviceStorage> selectDeviceStorage(DeviceStorageSO deviceStorage, Query query);

	/**
	 * 手动刷新字典缓存
	 *
	 * @param ciIds
	 * @return
	 */
	boolean refreshCache(List<Long> ciIds);

	/**
	 * 获取标准全程
	 *
	 * @param dto
	 * @return
	 */
	String getFullName(GetFullNameDTO dto);
}

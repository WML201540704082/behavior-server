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

import com.lnsoft.device.api.warehouse.dto.DeviceStorageSO;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageSaveCmdbDTO;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageSyncErpDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceStorage;
import com.lnsoft.device.api.warehouse.dto.DeviceStorageExportSO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageExportVO;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 设备入库表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface DeviceStorageMapper extends BaseMapper<DeviceStorage> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceStorage
	 * @return
	 */
	List<DeviceStorageVO> selectDeviceStoragePage(IPage page, DeviceStorageVO deviceStorage);

	/**
	 * 根据ids查询导出数据
	 *
	 * @param ids
	 * @return
	 */
	List<DeviceStorageExportVO> selectByExportIds(List<Long> ids);

	/**
	 * 根据条件查询导出数据
	 *
	 * @param so
	 * @return
	 */
	List<DeviceStorageExportVO> selectByExportParam(DeviceStorageExportSO so);

	/**
	 * 保存cmdb用
	 *
	 * @param id
	 * @return
	 */
	List<DeviceStorageSaveCmdbDTO> findById(String id);

	@Update("update idevelop_device_storage_list set ci_entity_id = #{ciId},is_to_cmdb = 1 WHERE uuid = #{uuid} and is_deleted = 0")
	void update2Id(@Param("uuid") String uuid, @Param("ciId") Object ciId);

	/**
	 * 同步erp用
	 *
	 * @param uuid
	 * @return
	 */
	DeviceStorageSyncErpDTO findById2Erp(String uuid);

	void updateByMap(@Param("backMap") Map<String, Object> backMap);

	@Select("SELECT region_code FROM `idevelop_dept` WHERE is_deleted = 0 and full_name like concat(#{receiveUnit},'%')")
	String getRegionCode(String receiveUnit);

	IPage<DeviceStorage> customSelectPage(@Param("page") IPage page, @Param("so") DeviceStorageSO so);
}

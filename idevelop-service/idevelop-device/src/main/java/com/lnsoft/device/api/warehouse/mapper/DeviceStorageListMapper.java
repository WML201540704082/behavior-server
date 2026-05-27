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

import com.lnsoft.device.entity.DeviceRecordList;
import com.lnsoft.device.api.warehouse.entity.DeviceStorage;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageList;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备入库明细表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface DeviceStorageListMapper extends BaseMapper<DeviceStorageList> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceStorageList
	 * @return
	 */
	List<DeviceStorageListVO> selectDeviceStorageListPage(IPage page, DeviceStorageListVO deviceStorageList);

	/**
	 * 根据主表id删除明细表
	 *
	 * @param storageId
	 */
	@Update("update idevelop_device_storage_list set is_deleted = '1' WHERE storage_id = #{storageId} ")
	void deleteByStorageId(@Param("storageId") String storageId);

	@Select("SELECT * FROM idevelop_device_storage WHERE is_deleted = 0 and id = #{id}")
	DeviceStorage getStorageById(String id);

	/**
	 * 获取模板实体类地址
	 *
	 * @param deviceCategory
	 * @return
	 */
	@Select("SELECT file_path FROM idevelop_device_storage_template WHERE is_deleted = 0 AND device_category = #{deviceCategory}")
	String getPatchByCode(String deviceCategory);

	/**
	 * 根据条件查询建档信息
	 *
	 * @param deviceCategory 设备分类
	 * @param deviceType     设备类型
	 * @param wbsElement     wbs元素
	 * @param erpStatus      erp同步状态
	 * @param size           设备数量
	 * @return
	 */
	List<DeviceRecordList> selectErpAssetCode(@Param("deviceCategory") String deviceCategory, @Param("deviceType") String deviceType, @Param("wbsElement") String wbsElement, @Param("erpStatus") int erpStatus, @Param("size") int size);
}

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
package com.lnsoft.device.api.res.mapper;

import com.lnsoft.device.api.res.entity.DeviceAttach;
import com.lnsoft.device.api.res.vo.DeviceAttachVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备-附件表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-02-23
 */
public interface DeviceAttachMapper extends BaseMapper<DeviceAttach> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param deviceAttach
	 * @return
	 */
	List<DeviceAttachVO> selectDeviceAttachPage(IPage page, DeviceAttachVO deviceAttach);

	/**
	 * 根据入库单号和附件id获取附件信息
	 *
	 * @param storageId
	 * @return
	 */
	@Select("SELECT * FROM idevelop_device_attach WHERE filing_no = #{storageId}  AND is_deleted = 0")
	List<DeviceAttach> getByFilingNoAndId(@Param("storageId") String storageId);

	/**
	 * 根据入库单号删除附件
	 *
	 * @param storageId
	 */
	@Update("update idevelop_device_attach set is_deleted = '1' WHERE filing_no = #{storageId} ")
	void deleteByStorageId(@Param("storageId") String storageId);
}

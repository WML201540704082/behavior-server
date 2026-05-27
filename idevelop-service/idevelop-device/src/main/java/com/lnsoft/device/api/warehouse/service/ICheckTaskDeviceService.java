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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.operation.vo.DeviceChangeVO;
import com.lnsoft.device.api.warehouse.dto.CheckDeviceQueryDto;
import com.lnsoft.device.api.warehouse.dto.CheckTaskDeviceDTO;
import com.lnsoft.device.api.warehouse.entity.CheckStatistic;
import com.lnsoft.device.api.warehouse.entity.CheckTaskDevice;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceCountVO;
import com.lnsoft.device.api.warehouse.vo.CheckDeviceRecordVo;
import com.lnsoft.device.api.warehouse.vo.CheckTaskDeviceVO;

import java.util.List;

/**
 * 盘点任务设备详情 服务类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
public interface ICheckTaskDeviceService extends BaseService<CheckTaskDevice> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param checkTaskDevice
	 * @return
	 */
	IPage<CheckTaskDeviceVO> selectCheckTaskDevicePage(IPage<CheckTaskDeviceVO> page, CheckTaskDeviceVO checkTaskDevice);

	/**
	 * 查询盘点任务设备详情列表
	 * @param page
	 * @param queryDto
	 * @return
	 */
    IPage<CheckTaskDevice> getList(IPage<Object> page, CheckDeviceQueryDto queryDto);

	/**
	 * 修改盘点任务设备处置状态
	 * @param dto
	 * @return
	 */
	R<Integer> editTaskDevice(CheckTaskDeviceDTO dto) throws Exception;

	/**
	 * 查看盘点任务设备处置信息
	 * @param id
	 * @param editType
	 * @return
	 */
	CheckTaskDeviceVO getTaskDevice(String id, String editType);

	/**
	 * 建立测试数据
	 * @return
	 */
	R addTestData();

	/**
	 * 根据人员信息获取盘点设备列表
	 * @param type
	 * @return
	 */
	R<IPage<CheckTaskDevice>> getCheckDeviceListByUser(String type,Query query);

	CheckTaskDevice saveDevice(CheckTaskDeviceDTO checkTaskDevice) throws Exception;

	void submitCheck(String id);
	/**
	 * 获取当前登陆人下的设备列表
	 * @return
	 */
	R<FeignCiCientity> getDeviceByUser(String type,Query query);
	/**
	 * 根据ip和mac查询台账设备列表
	 * @param checkTaskDevice
	 * @return
	 */
	R<FeignCiCientity> getDeviceByIpAndMac(CheckTaskDevice checkTaskDevice,Query query);
	/**
	 * 设备变更
	 * @param checkTaskDevice
	 * @return
	 */
	R<DeviceChangeVO> add(CheckTaskDeviceDTO checkTaskDevice) throws Exception;
	/**
	 * 获取设备履历记录
	 * @param deviceCode 设备编码
	 * @return
	 */
	CheckDeviceRecordVo getRecord(String deviceCode);
	/**
	 * 盘点扫码后判断
	 * @param checkTaskDevice
	 * @return
	 */
	CheckTaskDevice isNewDevice(CheckTaskDevice checkTaskDevice);
	/**
	 * 获取个人设备数量
	 * @return
	 */
	R<CheckDeviceCountVO> getDeviceCount();
	/**
	 * 设备注册
	 * @param id
	 * @return
	 */
	R deviceAdd(String id) throws Exception;
	/**
	 * 盘点设备周期校验
	 * @return
	 */
    void checkTaskNetwork();
	/**
	 * 获取个人盘点任务列表
	 * @param type 0-只看名下 1-看任务下所有
	 *
	 * @return
	 */
	R<IPage<CheckTaskDevice>> getListByUser(String type,String id, Query query);

	CheckStatistic statistic();

	R<List<CheckStatistic>> dept();

}

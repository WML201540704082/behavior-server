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
package com.lnsoft.device.api.cmdb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.vo.HardwareBasicTreeVO;
import com.lnsoft.core.mp.base.BaseService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 资产台账模型树管理表 服务类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface IHardwareBasicTreeService extends BaseService<HardwareBasicTree> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param hardwareBasicTree
	 * @return
	 */
	IPage<HardwareBasicTreeVO> selectHardwareBasicTreePage(IPage<HardwareBasicTreeVO> page, HardwareBasicTreeVO hardwareBasicTree);


	/**
	 * 刷新 资产台账模型树管理表
	 *
	 * @return
	 */
	String refresh(String keyword);

	/**
	 * 批量删除数据(物理)
	 *
	 * @param ciIds
	 * @return
	 */
	boolean deleteByCiIds(@NotEmpty List<Long> ciIds);
}

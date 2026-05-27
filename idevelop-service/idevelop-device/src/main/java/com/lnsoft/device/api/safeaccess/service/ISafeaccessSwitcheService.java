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
package com.lnsoft.device.api.safeaccess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.dto.SafeaccessSwitcheDTO;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessSwitcheSaveDTO;
import com.lnsoft.device.entity.SafeaccessSwitche;
import com.lnsoft.device.so.SafeaccessSwitcheSO;

import java.util.List;

/**
 * 交换机管理 服务类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
public interface ISafeaccessSwitcheService extends BaseService<SafeaccessSwitche> {

	/**
	 * 自定义分页
	 *
	 * @param safeaccessSwitche
	 * @return
	 */
	IPage<SafeaccessSwitche> selectSafeaccessSwitchePage(SafeaccessSwitcheSO safeaccessSwitche, Query query);

	/**
	 * 交换机同步radius
	 *
	 * @param id
	 * @return
	 */
	Boolean getRadiusState(String id);

	List<String> selectSwitches(String subnet);

	/**
	 * 保存接口
	 *
	 * @param dto
	 */
	void saveSwitches(SafeaccessSwitcheSaveDTO dto);

	/**
	 * 投运批量更新交换机数据
	 *
	 * @param safeAccessSwitchesDTOList 交换机数据
	 */
	void updateBatchSafeAccessSwitches(List<SafeaccessSwitcheDTO> safeAccessSwitchesDTOList);
}

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
package com.lnsoft.device.api.i6000.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.i6000.dto.I6000CmdbMappingDTO;
import com.lnsoft.device.api.i6000.dto.I6000ReqManualDTO;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import com.lnsoft.device.api.i6000.vo.I6000CiAttrVO;

/**
 * i6000模型属性 服务类
 *
 * @author Idevelop
 * @since 2024-03-19
 */
public interface II6000CiAttrService extends BaseService<I6000CiAttr> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param i6000CiAttr
	 * @return
	 */
	IPage<I6000CiAttrVO> selectI6000CiAttrPage(IPage<I6000CiAttrVO> page, I6000CiAttrVO i6000CiAttr);

	/**
	 * 手动新增I6000属性字段
	 *
	 * @param i6000ReqManualDTO
	 * @return
	 */
	Boolean saveManual(I6000ReqManualDTO i6000ReqManualDTO);


	/**
	 * 更新是否与CMDB映射
	 *
	 * @param cmdbAttrCode
	 * @return
	 */
	Boolean updateByAttrCode(String cmdbAttrCode, String ciCode);

	/**
	 * 根据I6000 ciTypeId 的获取I6000所有的模型属性
	 *
	 * @param ciTypeId
	 * @return
	 */
	String selectI6000AttrByCiTypeId(String ciTypeId);

	/**
	 * 获取并更新i6000模型属性
	 * @param i6000CiAttr
	 * @return
	 */
    R refresh(I6000CiAttr i6000CiAttr);



	// /**
	//  * 通过接口获取
	//  *
	//  * @param jsonObject
	//  * @return
	//  */
	// Boolean saveManualInter(I6000ReqManualDTO i6000ReqManualDTO);
}

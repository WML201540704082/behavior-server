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
package com.lnsoft.device.api.erp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.device.api.erp.entity.ErpMaintain;
import com.lnsoft.device.api.erp.vo.ErpMaintainVO;

import java.util.List;

/**
 * erp维护工厂(对应单位) 服务类
 *
 * @author Idevelop
 * @since 2024-03-22
 */
public interface IErpMaintainService extends BaseService<ErpMaintain> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param erpMaintain
	 * @return
	 */
	IPage<ErpMaintainVO> selectErpMaintainPage(IPage<ErpMaintainVO> page, ErpMaintainVO erpMaintain);


	/**
	 * 根据维护工厂获取 其所在的市县 维护工厂编码
	 *
	 * @param swerk
	 * @return
	 */
	List<String> selectErpListBySwerk(String swerk);

	/**
	 * 根据维护工厂获取 其所在的市县 维护工厂数据
	 * @param erpMaintain
	 * @return
	 */
	List<ErpMaintain> getSwerk(ErpMaintain erpMaintain);

}

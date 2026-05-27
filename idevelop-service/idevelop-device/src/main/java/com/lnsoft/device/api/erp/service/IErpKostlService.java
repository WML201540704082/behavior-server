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
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.vo.ErpKostlVO;
import com.lnsoft.device.api.erp.vo.ErpMaintainKostlVO;
import com.lnsoft.device.api.erp.vo.ErpXtythMaintainVO;

import java.util.List;

/**
 * erp成本中心 服务类
 *
 * @author Idevelop
 * @since 2024-04-02
 */
public interface IErpKostlService extends BaseService<ErpKostl> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param erpKostl
	 * @return
	 */
	IPage<ErpKostlVO> selectErpKostlPage(IPage<ErpKostlVO> page, ErpKostlVO erpKostl);

	/**
	 * 懒加载 ERP维护工厂(对应单位)
	 *
	 * @return
	 */
	List<ErpXtythMaintainVO> lazyMaintain();

	/**
	 * 懒加载 ERP维护工厂(对应单位)和成本中心
	 *
	 * @return
	 */
	ErpMaintainKostlVO lazyMaintainKostl(String unitCode);

	/**
	 * 同步ERP成本中心
	 *
	 * @param swerk
	 * @return
	 */
	boolean synchronousRefresh(String swerk);


}

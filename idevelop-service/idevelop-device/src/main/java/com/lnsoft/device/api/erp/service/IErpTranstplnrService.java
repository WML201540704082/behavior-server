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

import com.lnsoft.device.api.erp.dto.ErpTranstplnrDTO;
import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import com.lnsoft.device.api.erp.vo.ErpTranstplnrVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * ERP功能位置 服务类
 *
 * @author Idevelop
 * @since 2024-03-23
 */
public interface IErpTranstplnrService extends BaseService<ErpTranstplnr> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param erpTranstplnr
	 * @return
	 */
	IPage<ErpTranstplnrVO> selectErpTranstplnrPage(IPage<ErpTranstplnrVO> page, ErpTranstplnrVO erpTranstplnr);


	/**
	 * 新增或修改 ERP功能位置
	 *
	 * @param erpTranstplnr
	 * @return
	 */
	Map<String, String> saveOrUpdateNew(ErpTranstplnrDTO erpTranstplnr);

	/**
	 * 删除 ERP功能位置
	 *
	 * @param idList
	 */
	void deleteByTrlnr(List<String> idList);

	/**
	 * 详情
	 *
	 * @param erpTranstplnr
	 * @return
	 */
	ErpTranstplnr getDetail(ErpTranstplnr erpTranstplnr);

	/**
	 * 随机生成功能位置编码
	 *
	 * @param swerk
	 * @return
	 */
	String buildCode(String swerk);
}

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

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityBatchDelete;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.api.cmdb.dto.CmdbCardDTO;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.entity.CmdbDictCi;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.vo.CiCientitySearchVO;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * cmdb通用 服务类
 *
 * @author xuel
 * @since 2024-02-21
 */
public interface ICmdbService {


	/**
	 * 获取模型id 根据设备分类和设备类型
	 *
	 * @param hardwareBasicTree
	 * @return
	 */
	Long getCiId(HardwareBasicTree hardwareBasicTree);

	/**
	 * 根据条件获取分页查询资产台账
	 *
	 * @param entity
	 * @return
	 */
	FeignCiCientity getCiCientityList(List<CiCientitySearchVO> entity, Query query);

	/**
	 * 支持查询设备分类下获取分页查询资产台账
	 *
	 * @param entity
	 * @return
	 */
	FeignCiCientity getCiCientityListByClaccify(List<CiCientitySearchVO> entity, Query query);

	/**
	 * 支持查询 根据不同的条件处理 下获取分页查询 列转行
	 *
	 * @param cientitySearch 用这个
	 * @return
	 */
	FeignCiCientity getCiCientityListByCondition(CiCientitySearch cientitySearch);

	/**
	 * 配置项详情 列转行
	 *
	 * @param feignCmdbCientityGet
	 * @return
	 */
	Map<String, Object> getCientityDetail(FeignCmdbCientityGet feignCmdbCientityGet);

	/**
	 * 根据模型ID获取配置项信息,后端列转行 只供字典表使用
	 *
	 * @param ciId
	 * @return
	 */
	List<Map<String, Object>> getCiCientityList(Long ciId);


	/**
	 * 删除配置项
	 *
	 * @param id          配置项id
	 * @param description 删除说明
	 * @return
	 */
	Boolean cientityDelete(Long id, String description);


	/**
	 * 批量删除配置项
	 *
	 * @param feignCmdbCientityBatchDelete 配置项
	 * @return
	 */
	Boolean cientityBatchDelete(FeignCmdbCientityBatchDelete feignCmdbCientityBatchDelete);

	/**
	 * 单条生成打印标签
	 *
	 * @param cmdbCardDTO
	 * @return
	 */
	BufferedImage cards(CmdbCardDTO cmdbCardDTO);


	/**
	 * 批量生成打印标签
	 *
	 * @param cmdbCardDTOList
	 * @return
	 */
	Boolean batchCards(List<CmdbCardDTO> cmdbCardDTOList);

	/**
	 * 用户自主生成实物ID
	 *
	 * @param file
	 */
	void importCardsByExcel(MultipartFile file);

	/**
	 * 新增枚举 配置项
	 * PS: 新增方法请放在该方法上面
	 * @param ciId 模型id
	 * @param entity  key为 uuid
	 * @param cmdbDictCi 模型信息
	 * @param actionType 操作类型
	 * @return
	 */
	Map<String, Object> cientityBatchsaveDict(Long ciId, Map<String, Map<String, Object>> entity, CmdbDictCi cmdbDictCi, TransactionActionType actionType);

	/**
	 * 修改枚举 配置项
	 * PS: 新增方法请放在该方法上面
	 *
	 * @param entity     key为 id  value 包括 ciId和uuid
	 * @param actionType
	 * @return
	 */
	Map<String, Object> cientityBatchupdateDict(Map<Long, Map<String, Object>> entity, CmdbDictCi cmdbDictCi, TransactionActionType actionType);

	/**
	 * 新增资产台账
	 * PS: 新增方法请放在该方法上面
	 *
	 * @param ciId       模型id
	 * @param entity     key为 uuid
	 * @param actionType
	 * @return
	 */
	Map<String, Object> cientityBatchsave(Long ciId, Map<String, Map<String, Object>> entity, TransactionActionType actionType);

	/**
	 * 修改资产台账
	 * PS: 新增方法请放在该方法上面
	 *
	 * @param entity     key为 id  value 包括 ciId和uuid
	 * @param actionType
	 * @return
	 */
	Map<String, Object> cientityBatchupdate(Map<Long, Map<String, Object>> entity, TransactionActionType actionType);

	/**
	 * 修改资产台账 (测试)
	 * PS: 新增方法请放在该方法上面
	 *
	 * @param entity     key为 id  value 包括 ciId和uuid
	 * @param actionType
	 * @return
	 */
	Map<String, Object> cientityBatchupdate1(Map<Long, Map<String, Object>> entity, TransactionActionType actionType);
}

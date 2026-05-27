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
import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import com.lnsoft.device.api.i6000.vo.CmdbI6000MappingVO;
import com.lnsoft.device.api.i6000.vo.I6000CmdbMappingVO;

import java.util.List;
import java.util.Set;

/**
 * cmdb和i6000的映射关系表 服务类
 *
 * @author Idevelop
 * @since 2024-03-24
 */
public interface II6000CmdbMappingService extends BaseService<I6000CmdbMapping> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param I6000CmdbMapping
	 * @return
	 */
	IPage<I6000CmdbMappingVO> selectI6000CmdbMappingPage(IPage<I6000CmdbMappingVO> page, I6000CmdbMappingVO I6000CmdbMapping);


	/**
	 * 物理删除 cmdb和i6000的映射关系表
	 *
	 * @param id
	 * @return
	 */
	boolean deleteLogic(String id);


	/**
	 * 根据I6000集合编码获取集合数据
	 *
	 * @param ciIds
	 * @return
	 */
	List<I6000CmdbMappingVO> selectI6000CmdbMappingByCiIds(Set<String> ciIds);

	/**
	 * 刷新映射关系
	 * @param i6000CmdbMapping
	 * @return
	 */
    R checkRefresh(I6000CmdbMapping i6000CmdbMapping);

	/**
	 * 获取属性列表
	 * @param deviceType
	 * @return
	 */
    R<CmdbI6000MappingVO> getAttrListCmdb(String deviceType);

	/**
	 * 新增关系绑定
	 * @param i6000CmdbMappingList
	 * @return
	 */
	R insertRelation(List<I6000CmdbMapping> i6000CmdbMappingList);

	/**
	 * 列表查询
	 * @return
	 */
	R<List<I6000CmdbMappingVO>> getList();

	/**
	 * 获取映射关系列表
	 * @return
	 */
	R<List<I6000CmdbMappingVO>> getMappingList(String ciId);
}

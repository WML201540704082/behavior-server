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
package com.lnsoft.device.api.i6000.mapper;

import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import com.lnsoft.device.api.i6000.vo.I6000CmdbMappingVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Set;

/**
 * cmdb和i6000的映射关系表 Mapper 接口
 *
 * @author Idevelop
 * @since 2024-03-24
 */
public interface I6000CmdbMappingMapper extends BaseMapper<I6000CmdbMapping> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param I6000CmdbMapping
	 * @return
	 */
	List<I6000CmdbMappingVO> selectI6000CmdbMappingPage(IPage page, I6000CmdbMappingVO I6000CmdbMapping);

	/**
	 * 重写删除
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
	 * 获取列表
	 * @return
	 */
    List<I6000CmdbMappingVO> getList();

	/**
	 * 根据模型id获取设备分类字典id
	 * @param deviceType
	 * @return
	 */
	String getDeviceTypeId(String deviceType);

	/**
	 * 获取映射关系列表
	 * @param ciId
	 * @return
	 */
    List<I6000CmdbMappingVO> getMappingList(String ciId);

	/**
	 * 根据模型id删除关联关系
	 * @param cmdbCiId
	 */
    void deleteAll(Long cmdbCiId);
}

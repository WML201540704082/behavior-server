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
package com.lnsoft.device.api.i6000.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.CmdbCiAttr;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.cmdb.mapper.CmdbCiAttrMapper;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import com.lnsoft.device.api.i6000.mapper.I6000CiAttrMapper;
import com.lnsoft.device.api.i6000.mapper.I6000CmdbMappingMapper;
import com.lnsoft.device.api.i6000.service.II6000CmdbMappingService;
import com.lnsoft.device.api.i6000.vo.CmdbI6000MappingVO;
import com.lnsoft.device.api.i6000.vo.I6000CmdbMappingVO;
import com.lnsoft.device.props.CmdbDictProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * cmdb和i6000的映射关系表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-24
 */
@Service
public class I6000CmdbMappingServiceImpl extends BaseServiceImpl<I6000CmdbMappingMapper, I6000CmdbMapping> implements II6000CmdbMappingService {
	@Resource
	private I6000CiAttrMapper i6000CiAttrMapper;
	@Resource
	private CmdbCiAttrMapper cmdbCiAttrMapper;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private ICmdbClient cmdbClient;
	@Override
	public IPage<I6000CmdbMappingVO> selectI6000CmdbMappingPage(IPage<I6000CmdbMappingVO> page, I6000CmdbMappingVO I6000CmdbMapping) {
		return page.setRecords(baseMapper.selectI6000CmdbMappingPage(page, I6000CmdbMapping));
	}

	/**
	 * 物理删除 cmdb和i6000的映射关系表
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteLogic(String id) {
		if (!StringUtils.hasLength(id)) {
			throw new ServiceException("id 不能为空");
		}
		return baseMapper.deleteLogic(id);
	}


	/**
	 * 根据I6000集合编码获取集合数据
	 *
	 * @param ciIds
	 * @return
	 */
	@Override
	public List<I6000CmdbMappingVO> selectI6000CmdbMappingByCiIds(Set<String> ciIds) {

		if (CollectionUtils.isEmpty(ciIds)) {
			throw new RuntimeException("请求参数 ciIds 不能为空!");
		}
		List<I6000CmdbMappingVO> i6000CmdbMappingVOS = baseMapper.selectI6000CmdbMappingByCiIds(ciIds);

		if (CollectionUtils.isEmpty(ciIds)) {
			throw new RuntimeException("未查询到对应的模型映射关系,请求联系运维人员处理! ciTypeIds = " + ciIds);
		}

		return i6000CmdbMappingVOS;
	}

	@Override
	public R checkRefresh(I6000CmdbMapping i6000CmdbMapping) {
		String i6000CiId = i6000CmdbMapping.getI6000CiId();
		LambdaQueryWrapper<I6000CmdbMapping> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(I6000CmdbMapping::getI6000CiId,i6000CiId).eq(I6000CmdbMapping::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		List<I6000CmdbMapping> i6000CiAttrList = baseMapper.selectList(queryWrapper);
		LambdaQueryWrapper<I6000CiAttr> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(I6000CiAttr::getCiCode,i6000CiId).eq(I6000CiAttr::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
		List<I6000CiAttr> i6000CiAttrs = i6000CiAttrMapper.selectList(wrapper);
		List<I6000CmdbMapping> deleteList = i6000CiAttrList.stream().filter(itemA -> i6000CiAttrs.stream().noneMatch(itemB -> itemB.getAttrCode().equals(itemA.getI6000AttrCode()))).collect(Collectors.toList());
		for (I6000CmdbMapping cmdbMapping : deleteList) {
			cmdbMapping.setIsDeleted(1);
			cmdbMapping.setUpdateTime(new Date());
			baseMapper.updateById(cmdbMapping);
		}
		List<I6000CiAttr> insertList = i6000CiAttrs.stream().filter(itemA -> i6000CiAttrList.stream().noneMatch(itemB -> itemB.getI6000AttrCode().equals(itemA.getAttrCode()))).collect(Collectors.toList());
		List<I6000CiAttr> ciAttrs = insertList.stream().map(item -> {
			item.setIsNeed(1);
			return item;
		}).collect(Collectors.toList());
		for (I6000CiAttr ciAttr : ciAttrs) {
			i6000CiAttrMapper.updateById(ciAttr);
		}
		return R.success("操作成功");

	}

	@Override
	public R<CmdbI6000MappingVO> getAttrListCmdb(String deviceType) {
//		Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(Long.valueOf(deviceType));
		Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(1097745969774592L);
		String deviceTypeId = baseMapper.getDeviceTypeId(deviceType);
		String i6000Code = erpI6000MapByCiId.get(deviceTypeId);
		List<I6000CiAttr> attrListI6000 = this.getAttrListI6000(i6000Code);
		String ciCode = attrListI6000.get(0).getCiCode();
		FeignCmdbDictCientitySearch feignCmdbDictCientitySearch = new FeignCmdbDictCientitySearch();
		feignCmdbDictCientitySearch.setDictKeyI6000(ciCode);
		feignCmdbDictCientitySearch.setCiId(1097745969774592L);
		R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(feignCmdbDictCientitySearch);
		if (feignCiCientityR.isSuccess()){
			Map<String, Object> stringObjectMap = feignCiCientityR.getData().getData().get(0);
			String dictValueI6000 = String.valueOf(stringObjectMap.get("dictValueI6000"));
			for (I6000CiAttr i6000CiAttr : attrListI6000) {
				String code = i6000CiAttr.getCiCode();
				i6000CiAttr.setCiCode(dictValueI6000+"("+code+")");
			}
		}
		LambdaQueryWrapper<CmdbCiAttr> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.likeRight(CmdbCiAttr::getAttrCiId,deviceType).eq(CmdbCiAttr::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
		List<CmdbCiAttr> cmdbCiAttrs = cmdbCiAttrMapper.selectList(queryWrapper);
		CmdbI6000MappingVO cmdbI6000MappingVO = new CmdbI6000MappingVO();
		cmdbI6000MappingVO.setCmdbCiAttrList(cmdbCiAttrs);
		cmdbI6000MappingVO.setI6000CiAttrList(attrListI6000);
		return R.data(cmdbI6000MappingVO);
	}

	@Override
	public R insertRelation(List<I6000CmdbMapping> i6000CmdbMappingList) {

		Long cmdbCiId = i6000CmdbMappingList.get(0).getCmdbCiId();
		List<I6000CmdbMappingVO> mappingList = baseMapper.getMappingList(String.valueOf(cmdbCiId));
		String i6000CiId = mappingList.get(0).getI6000CiId();
		//先删除
		baseMapper.deleteAll(cmdbCiId);
		for (I6000CmdbMapping i6000CmdbMapping : i6000CmdbMappingList) {
			i6000CmdbMapping.setUpdateTime(new Date());
			i6000CmdbMapping.setUpdateUser(SecureUtil.getUserId());
			i6000CmdbMapping.setStatus(0);
			i6000CmdbMapping.setI6000CiId(i6000CiId);
			baseMapper.insert(i6000CmdbMapping);
		}
		return R.success("操作成功");
	}

	@Override
	public R<List<I6000CmdbMappingVO>> getList() {
		List<I6000CmdbMappingVO> i6000CmdbMappingList = baseMapper.getList();
		FeignCmdbDictCientitySearch feignCmdbDictCientitySearch = new FeignCmdbDictCientitySearch();
		feignCmdbDictCientitySearch.setCiId(1097745969774592L);
		for (I6000CmdbMappingVO i6000CmdbMappingVO : i6000CmdbMappingList) {
			String i6000CiId = i6000CmdbMappingVO.getI6000CiId();
			feignCmdbDictCientitySearch.setDictKeyI6000(i6000CiId);
			R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(feignCmdbDictCientitySearch);
			if (feignCiCientityR.isSuccess()){
				String dictValueI6000 = "";
				List<Map<String, Object>> data = feignCiCientityR.getData().getData();
				if (ObjectUtil.isNotEmpty(data)){
					dictValueI6000 = String.valueOf(data.get(0).get("dictValueI6000"));
				}
				String mappingVOI6000CiId = i6000CmdbMappingVO.getI6000CiId();
				i6000CmdbMappingVO.setI6000CiId(dictValueI6000+"("+mappingVOI6000CiId+")");
			}
		}
		return R.data(i6000CmdbMappingList);
	}

	@Override
	public R<List<I6000CmdbMappingVO>> getMappingList(String ciId) {
		List<I6000CmdbMappingVO> i6000CmdbMappingList = baseMapper.getMappingList(ciId);
		String i6000CiId = i6000CmdbMappingList.get(0).getI6000CiId();
		FeignCmdbDictCientitySearch feignCmdbDictCientitySearch = new FeignCmdbDictCientitySearch();
		feignCmdbDictCientitySearch.setDictKeyI6000(i6000CiId);
		feignCmdbDictCientitySearch.setCiId(1097745969774592L);
		R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(feignCmdbDictCientitySearch);
		if (feignCiCientityR.isSuccess()){
			String dictValueI6000 = "";
			List<Map<String, Object>> data = feignCiCientityR.getData().getData();
			if (ObjectUtil.isNotEmpty(data)){
				dictValueI6000 = String.valueOf(data.get(0).get("dictValueI6000"));
			}
			for (I6000CmdbMappingVO i6000CmdbMappingVO : i6000CmdbMappingList) {
				String mappingVOI6000CiId = i6000CmdbMappingVO.getI6000CiId();
				i6000CmdbMappingVO.setI6000CiId(dictValueI6000+"("+mappingVOI6000CiId+")");
			}
		}
		return R.data(i6000CmdbMappingList);
	}


	private List<I6000CiAttr> getAttrListI6000(String deviceType) {
		LambdaQueryWrapper<I6000CiAttr> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(I6000CiAttr::getCiCode,deviceType).eq(I6000CiAttr::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
		List<I6000CiAttr> i6000CiAttrs = i6000CiAttrMapper.selectList(queryWrapper);
		return i6000CiAttrs;
	}

}

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
package com.lnsoft.device.api.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.asset.entity.ProjectManagerErp;
import com.lnsoft.device.api.asset.mapper.ProjectManagerMapper;
import com.lnsoft.device.api.asset.service.IProjectManagerService;
import com.lnsoft.device.api.asset.vo.ProjectManagerErpVO;
import com.lnsoft.device.api.asset.vo.ProjectManagerVO;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.service.IZfitXtCwztService;
import com.lnsoft.device.api.erp.vo.ZfitXtCwztVO;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目管理 服务实现类
 *
 * @author xuel
 * @since 2024-03-04
 */
@Service
@AllArgsConstructor
public class ProjectManagerServiceImpl extends BaseServiceImpl<ProjectManagerMapper, ProjectManager> implements IProjectManagerService {
	private ICmdbService iCmdbService;
	private CmdbCientityProperties cmdbCientityProperties;
	private IZfitXtCwztService zfitXtCwztService;
	private IDeptClient deptClient;

	@Override
	public IPage<ProjectManagerVO> selectProjectManagerPage(IPage<ProjectManagerVO> page, ProjectManagerVO projectManager) {
		//根据维护工厂查询区域编码
		R<Dept> deptR = deptClient.getByErpUnitCode(projectManager.getProjectUnitCode());
		Dept data = deptR.getData();
		String regionCode = data.getRegionCode();
		if (regionCode.length() > 4) {
			regionCode = regionCode.substring(0, 4);
		}
		projectManager.setRegionCode(regionCode);
		List<ProjectManagerVO> projectManagerVOS = baseMapper.selectProjectManagerPage(page, projectManager);
		return page.setRecords(projectManagerVOS);
	}

	@Override
	public IPage<ProjectManagerErpVO> getErpList(ProjectManagerErp projectManagerErp) {
		List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		ciCientitySearchVOS.add(CiCientitySearchVO.builder().attrName(CmdbAttrConstant.WBS_ELEMENT_NAME).attrValue(projectManagerErp.getWbsProject()).expression(Expression.EQUAL).build());
		ciCientitySearchVOS.add(CiCientitySearchVO.builder().attrName(CmdbAttrConstant.WBS_ELEMENT).attrValue(projectManagerErp.getWbsElement()).expression(Expression.EQUAL).build());
		ciCientitySearchVOS.add(CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_SOURCE_CODE).attrValue(cmdbCientityProperties.getDeviceSource()).expression(Expression.EQUAL).build());
		// ERP资产编码
		if (StringUtils.isNotEmpty(projectManagerErp.getErpAssetCode())) {
			ciCientitySearchVOS.add(CiCientitySearchVO.builder()
				.attrName(CmdbAttrConstant.ASSET_CODE_ERP)
				.attrValue(projectManagerErp.getErpAssetCode())
				.expression(Expression.LIKE).build());
		}
		// 设备编码
		if (StringUtils.isNotEmpty(projectManagerErp.getDeviceCode())) {
			ciCientitySearchVOS.add(CiCientitySearchVO.builder()
				.attrName(CmdbAttrConstant.DEVICE_CODE)
				.attrValue(projectManagerErp.getDeviceCode())
				.expression(Expression.LIKE).build());
		}
		// ERP同步状态
		if (StringUtils.isNotEmpty(projectManagerErp.getErpStatus())) {
			ciCientitySearchVOS.add(CiCientitySearchVO.builder()
				.attrName(CmdbAttrConstant.IS_TO_ERP_CODE)
				.attrValue(projectManagerErp.getErpStatus())
				.expression(Expression.LIKE).build());
		}
		// i6000同步状态
		if (StringUtils.isNotEmpty(projectManagerErp.getI6000Status())) {
			ciCientitySearchVOS.add(CiCientitySearchVO.builder()
				.attrName(CmdbAttrConstant.IS_TO_I6000)
				.attrValue(projectManagerErp.getI6000Status())
				.expression(Expression.LIKE).build());
		}
		Query query = new Query();
		query.setCurrent(projectManagerErp.getCurrent());
		query.setSize(projectManagerErp.getSize());
		FeignCiCientity ciCientityListByClaccify = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		List<Map<String, Object>> data = ciCientityListByClaccify.getData();
		projectManagerErp.setCurrent(projectManagerErp.getCurrent() - 1);
		Page page = new Page();
		page.setRecords(data);
		page.setTotal(ciCientityListByClaccify.getTotal());
		return page;
	}

	@Override
	public Boolean input() {
		List<ZfitXtCwztVO> zfitXtCwztList = zfitXtCwztService.selectData();
		for (ZfitXtCwztVO zfitXtCwztVO : zfitXtCwztList) {
			if (StringUtil.isNotBlank(zfitXtCwztVO.getZzxmlx())) {
				ProjectManager projectManager = new ProjectManager();
				projectManager.setWbsCode(zfitXtCwztVO.getPosid());
				projectManager.setWbsName(zfitXtCwztVO.getPosidT());
				projectManager.setProjectTypeCode(zfitXtCwztVO.getZzxmlx());
				projectManager.setProjectType(zfitXtCwztVO.getZzxmlxT());
				projectManager.setProjectDefine(zfitXtCwztVO.getPspidT());
				projectManager.setProjectDefineCode(zfitXtCwztVO.getPspid());
				projectManager.setProjectUnitCode(zfitXtCwztVO.getSwerk());
				projectManager.setProjectUnitName(zfitXtCwztVO.getSwerkT());
				projectManager.setLoevm("Y");
				LocalDateTime now = LocalDateTime.now();
				projectManager.setProjectCreateTime(now);
				projectManager.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				projectManager.setCreateTime(new Date());
				Long userId = SecureUtil.getUser().getUserId();
				projectManager.setCreateUser(userId);
				projectManager.setStatus(0);
				projectManager.setUpdateTime(new Date());
				projectManager.setUpdateUser(userId);
				LambdaQueryWrapper<ProjectManager> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(ProjectManager::getWbsCode, zfitXtCwztVO.getPosid());
				ProjectManager selectOne = baseMapper.selectOne(queryWrapper);
				if (ObjectUtil.isNotEmpty(selectOne)) {
					baseMapper.updateById(projectManager);
				} else {
					baseMapper.insert(projectManager);

				}
			}
		}
		return true;
	}

}

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

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.dto.ProjectManagerDetailDTO;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.entity.ProjectManagerDetail;
import com.lnsoft.device.api.asset.entity.ProjectManagerDetailExport;
import com.lnsoft.device.api.asset.mapper.ProjectManagerDetailMapper;
import com.lnsoft.device.api.asset.mapper.ProjectManagerMapper;
import com.lnsoft.device.api.asset.service.IProjectManagerDetailService;
import com.lnsoft.device.vo.ProjectManagerDetailVO;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.mapper.ZfitXtCwztMapper;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.entity.ZfitXtCwzt;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目下的ERP资产编码 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-29
 */
@Slf4j
@Service
public class ProjectManagerDetailServiceImpl extends BaseServiceImpl<ProjectManagerDetailMapper, ProjectManagerDetail> implements IProjectManagerDetailService {
	@Resource
	private ProjectManagerMapper projectManagerMapper;
	@Resource
	private ZfitXtCwztMapper zfitXtCwztMapper;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private ICmdbClient cmdbClient;
	@Resource
	private ICmdbService iCmdbService;

	@Override
	public IPage<ProjectManagerDetailVO> selectProjectManagerDetailPage(IPage<ProjectManagerDetailVO> page, ProjectManagerDetailVO projectManagerDetail) {
		return page.setRecords(baseMapper.selectProjectManagerDetailPage(page, projectManagerDetail));
	}

	@Override
	public R input() {
		LambdaQueryWrapper<ProjectManager> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProjectManager::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(ProjectManager::getLoevm, "Y");
		List<ProjectManager> projectManagerList = projectManagerMapper.selectList(queryWrapper);
		for (ProjectManager projectManager : projectManagerList) {
			LambdaQueryWrapper<ZfitXtCwzt> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(ZfitXtCwzt::getPosid, projectManager.getWbsCode()).ne(ZfitXtCwzt::getStat, "E0007");
			List<ZfitXtCwzt> zfitXtCwztList = zfitXtCwztMapper.selectList(wrapper);
			ArrayList<ProjectManagerDetail> projectManagerDetails = new ArrayList<>();
			if (ObjectUtil.isNotEmpty(zfitXtCwztList)) {
				for (ZfitXtCwzt zfitXtCwzt : zfitXtCwztList) {
					ProjectManagerDetail projectManagerDetail = new ProjectManagerDetail();
					//设备编码
					projectManagerDetail.setDeviceCode("");
					projectManagerDetail.setUuid(UuidUtils.uuid());
					projectManagerDetail.setDeviceName(zfitXtCwzt.getEqktx());
					projectManagerDetail.setErpAssetCode(zfitXtCwzt.getAnlnr());
					//erp台账编码
					projectManagerDetail.setErpAccountCode(zfitXtCwzt.getEqunr());
					//erp转资状态
					projectManagerDetail.setErpTransferStatus("1130566482460672");
					//ERP资产编码使用状态 0未使用, 1已使用
					projectManagerDetail.setErpAssetStatus(1);
					//ERP同步状态 0: 未同步, 1 同步中 ,2 已同步,3 同步失败
					projectManagerDetail.setErpStatus(2);
					//I6000同步状态 0: 未同步, 1 同步中 ,2 已同步,3 同步失败
					projectManagerDetail.setI6000Status(0);
					//设备类型
					String sbfl = zfitXtCwzt.getSbfl();
					Map<Object, Object> map = cmdbDictProperties.getDictErpMapByCiId(1097745969774592L);
					String deviceType = null;
					for (Map.Entry<Object, Object> entry : map.entrySet()) {
						if (String.valueOf(entry.getValue()).equals(sbfl)) {
							deviceType = String.valueOf(entry.getKey());
						}
					}
					projectManagerDetail.setDeviceType(deviceType);
					//wbs项目编码
					projectManagerDetail.setWbsCode(zfitXtCwzt.getPosid());
					projectManagerDetail.setWbsName(zfitXtCwzt.getPosidT());
					//所在阶段
					projectManagerDetail.setStage("设备建档");
					projectManagerDetail.setStatus(0);
					projectManagerDetails.add(projectManagerDetail);
				}

			}
			this.saveBatch(projectManagerDetails);
		}
		return R.success("操作成功");
	}

	@Override
	public R updateDeviceCode() {
		LambdaQueryWrapper<ProjectManagerDetail> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProjectManagerDetail::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		List<ProjectManagerDetail> projectManagerDetailList = baseMapper.selectList(queryWrapper);
		for (ProjectManagerDetail projectManagerDetail : projectManagerDetailList) {
			String erpAssetCode = projectManagerDetail.getErpAssetCode();
			List<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().expression(Expression.EQUAL).attrName(CmdbAttrConstant.ASSET_CODE_ERP).attrValue(erpAssetCode).build();
			ciCientitySearchVOS.add(searchVO);
			FeignCiCientity jsonObject = null;
			jsonObject = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, new Query());
			if (ObjectUtil.isNotEmpty(jsonObject) && ObjectUtil.isNotEmpty(jsonObject.getData())) {
				log.info("查询台账数据：{}", jsonObject);
				String deviceCode = String.valueOf(jsonObject.getData().get(0).get(CmdbAttrConstant.DEVICE_CODE));
				projectManagerDetail.setDeviceCode(deviceCode);
				baseMapper.updateById(projectManagerDetail);
				log.info("更新设备编码{}", projectManagerDetail.getDeviceCode());
			}
		}
		return R.success("操作成功");

	}

	@Override
	public R export(ProjectManagerDetailDTO projectManagerDetailDTO, HttpServletResponse servletResponse) {
		List<ProjectManagerDetail> projectManagerDetails = new ArrayList<>();
		if (ObjectUtil.isEmpty(projectManagerDetailDTO.getUuidList())) {
			if (StringUtil.isNotBlank(projectManagerDetailDTO.getWbsCode())) {
				String wbsCode = projectManagerDetailDTO.getWbsCode();
				LambdaQueryWrapper<ProjectManagerDetail> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(ProjectManagerDetail::getWbsCode, wbsCode);
				projectManagerDetails = baseMapper.selectList(queryWrapper);
			}
		}else {
			List<String> uuidList = projectManagerDetailDTO.getUuidList();
			LambdaQueryWrapper<ProjectManagerDetail> queryWrapper = new LambdaQueryWrapper();
			queryWrapper.in(ProjectManagerDetail::getUuid,uuidList);
			projectManagerDetails = baseMapper.selectList(queryWrapper);
		}
		ArrayList<ProjectManagerDetailExport> projectManagerDetailExports = new ArrayList<>();
		for (ProjectManagerDetail projectManagerDetail : projectManagerDetails) {
			ProjectManagerDetailExport projectManagerDetailExport = new ProjectManagerDetailExport();
			BeanUtil.copy(projectManagerDetail,projectManagerDetailExport);
			String deviceType = projectManagerDetailExport.getDeviceType();
			long code = Long.parseLong(deviceType);
			R<Map<String, Object>> mapR = cmdbClient.feignCientityDetailById(cmdbDictProperties.getDeviceType(), code);
			if (mapR.isSuccess()){
				projectManagerDetailExport.setDeviceType(String.valueOf(mapR.getData().get("dictValue")));
			}
			projectManagerDetailExports.add(projectManagerDetailExport);
		}
		try {
			servletResponse.setContentType("application/vnd.ms-excel");
			servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("wbs项目导出", StandardCharsets.UTF_8.name());
			servletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(servletResponse.getOutputStream(), ProjectManagerDetailExport.class).sheet("wbs项目信息").doWrite(projectManagerDetailExports);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
		return null;
	}

}

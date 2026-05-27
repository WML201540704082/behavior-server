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

import com.lnsoft.device.api.asset.dto.ExportRacks;
import com.lnsoft.device.api.asset.dto.ResourceRacksDTO;
import com.lnsoft.device.api.asset.entity.ResourceRacks;
import com.lnsoft.device.api.asset.vo.ResourceRacksVO;
import com.lnsoft.device.api.asset.mapper.ResourceRacksMapper;
import com.lnsoft.device.api.res.service.IResourceRacksService;
import com.alibaba.excel.EasyExcel;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.utils.Func;

import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 空间资源管理机架表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Service
public class ResourceRacksServiceImpl extends BaseServiceImpl<ResourceRacksMapper, ResourceRacks> implements IResourceRacksService {

	@Override
	public List<ResourceRacksVO> selectResourceRacksPage(IPage<ResourceRacksVO> page, ResourceRacksVO resourceRacks) {
		return baseMapper.selectResourceRacksPage(page, resourceRacks);
	}

	@Override
	public List<ResourceRacks> selectResourceRacksByCabinetsIds(List<String> cabinetsIds) {
		return baseMapper.selectResourceRacksByCabinetsId(cabinetsIds);
	}

	@Override
	public List<ExportRacks> selectRacksList(ResourceRacksDTO resourceRacks) {
		return baseMapper.selectRacksList(resourceRacks);
	}

	@Override
	public ExportRacks selectRacksById(String id) {
		return baseMapper.selectRacksById(id);
	}

	@Override
	public void export(ResourceRacksDTO resourceRacksDTO, HttpServletResponse servletResponse) {
		String ids = resourceRacksDTO.getIds();
		List<ExportRacks> racksList = new ArrayList<>();
		if ("".equals(ids)){
			racksList=selectRacksList(resourceRacksDTO);
		}else {
			List<String> idList = Func.toStrList(ids);
			for (String id : idList) {
				ExportRacks exportRacks= selectRacksById(id);
				racksList.add(exportRacks);
			}
		}
		try {
			servletResponse.setContentType("application/vnd.ms-excel");
			servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("机架列表导出", StandardCharsets.UTF_8.name());
			servletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(servletResponse.getOutputStream(), ExportRacks.class).sheet("机架列表").doWrite(racksList);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<ResourceRacks> findByCabinetsId(String cabinetsId) {
		return baseMapper.findByCabinetsId(cabinetsId);
	}

}

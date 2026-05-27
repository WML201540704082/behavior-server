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
package com.lnsoft.device.api.warehouse.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.annotation.EasyExcelUtil;
import com.lnsoft.device.api.warehouse.entity.DeviceStorageTemplate;
import com.lnsoft.device.api.warehouse.mapper.DeviceStorageTemplateMapper;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageTemplateService;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageTemplateVO;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.props.CmdbCientityProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 设备入库导入模板表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Service
public class DeviceStorageTemplateServiceImpl extends BaseServiceImpl<DeviceStorageTemplateMapper, DeviceStorageTemplate> implements IDeviceStorageTemplateService {

	@Resource
	private CmdbCientityProperties ciEntityProperties;

	@Override
	public IPage<DeviceStorageTemplateVO> selectDeviceStorageTemplatePage(IPage<DeviceStorageTemplateVO> page, DeviceStorageTemplateVO deviceStorageTemplate) {
		return page.setRecords(baseMapper.selectDeviceStorageTemplatePage(page, deviceStorageTemplate));
	}

	@Override
	public void downloadTemplate(String deviceCategory,String deviceType, HttpServletResponse response) {
		try {
			String clazzPatch;
			clazzPatch = baseMapper.getPatchByCode(deviceCategory);
			if (ciEntityProperties.getCientityId(CmdbCientityConstant.T109).equals(deviceCategory)){
				clazzPatch = baseMapper.getPatchByTypeCode(deviceType);
			}
			Class<?> aClass = Class.forName(clazzPatch);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			String fileName = URLEncoder.encode("设备列表模板", StandardCharsets.UTF_8.name());

			WriteCellStyle headWriteCellStyle = new WriteCellStyle();
			headWriteCellStyle.setWrapped(false);
			HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);
			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
				.build();
			WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(aClass, 0, "设备列表");
			excelWriter.write(new ArrayList(), writeSheet);
			excelWriter.finish();
		} catch (ClassNotFoundException e) {
			throw new ServiceException("模板实体类未找到!");
		} catch (Exception e) {
			log.error("模板下载失败原因：{}", e);
			throw new ServiceException("模板下载失败!");
		}
	}

}

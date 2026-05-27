/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.lnsoft.device.api.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.common.dto.ImportLogImportDTO;
import com.lnsoft.device.api.common.entity.ImportLog;
import com.lnsoft.device.api.common.mapper.ImportLogMapper;
import com.lnsoft.device.api.common.service.IImportLogService;
import com.lnsoft.device.api.common.vo.ImportLogVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 信通一体化平台导入文件结果下载日志 服务实现类
 *
 * @author Idevelop
 * @since 2026-03-01
 */
@Service
public class ImportLogServiceImpl extends BaseServiceImpl<ImportLogMapper, ImportLog> implements IImportLogService {

	@Override
	public IPage<ImportLogVO> selectImportLogPage(IPage<ImportLogVO> page, ImportLogVO importLog) {
		return page.setRecords(baseMapper.selectImportLogPage(page, importLog));
	}



	/**
	 * 自定义导出
	 *
	 * @param i6000ImportLog
	 * @param response
	 */
	@Override
	public void exportByExcel(ImportLog i6000ImportLog, HttpServletResponse response) {
		LambdaQueryWrapper<ImportLog> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ImportLog::getImportUuid, i6000ImportLog.getImportUuid()).eq(ImportLog::getIsDeleted,0);
		List<ImportLog> i6000ImportLogList = this.list(queryWrapper);
		try {
			List<ImportLogImportDTO> i6000ImportLogs = i6000ImportLogList.stream().map(item -> {
				ImportLogImportDTO importLogImportDTO = new ImportLogImportDTO();
				importLogImportDTO.setCreateTime(item.getCreateTime());
				importLogImportDTO.setDeviceCode(item.getDeviceCode());
				importLogImportDTO.setDeviceInfo(item.getDeviceInfo());
				return importLogImportDTO;
			}).collect(Collectors.toList());
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("生成实物ID结果_", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), ImportLogImportDTO.class).sheet("生成实物ID结果").doWrite(i6000ImportLogs);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}

	}

}

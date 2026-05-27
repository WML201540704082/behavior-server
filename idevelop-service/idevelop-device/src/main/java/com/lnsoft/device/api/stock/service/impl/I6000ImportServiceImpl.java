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
package com.lnsoft.device.api.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.device.api.stock.dto.I6000ImportLogImportDTO;
import com.lnsoft.device.api.stock.entity.I6000ImportLog;
import com.lnsoft.device.api.stock.mapper.I6000ImportLogMapper;
import com.lnsoft.device.api.stock.service.II6000ImportLogService;
import com.lnsoft.device.api.stock.vo.I6000ImportLogVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员批量导入I6000数据记录表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@Service
public class I6000ImportServiceImpl extends BaseServiceImpl<I6000ImportLogMapper, I6000ImportLog> implements II6000ImportLogService {

	@Override
	public IPage<I6000ImportLogVO> selectI6000ImportLogPage(IPage<I6000ImportLogVO> page, I6000ImportLogVO i6000ImportLog) {
		return page.setRecords(baseMapper.selectI6000ImportLogPage(page, i6000ImportLog));
	}


	/**
	 * 自定义导出
	 *
	 * @param i6000ImportLog
	 * @param response
	 */
	@Override
	public void exportByExcel(I6000ImportLog i6000ImportLog, HttpServletResponse response) {
		LambdaQueryWrapper<I6000ImportLog> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(I6000ImportLog::getImportUuid, i6000ImportLog.getImportUuid()).eq(I6000ImportLog::getIsDeleted,0);
		List<I6000ImportLog> i6000ImportLogList = this.list(queryWrapper);
		try {
			List<I6000ImportLogImportDTO> i6000ImportLogs = i6000ImportLogList.stream().map(item -> {
				I6000ImportLogImportDTO i6000ImportLogImportDTO = new I6000ImportLogImportDTO();
				i6000ImportLogImportDTO.setCreateTime(item.getCreateTime());
				i6000ImportLogImportDTO.setDeviceCode(item.getDeviceCode());
				i6000ImportLogImportDTO.setDeviceInfo(item.getDeviceInfo());
				return i6000ImportLogImportDTO;
			}).collect(Collectors.toList());
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("数据同步结果_", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), I6000ImportLogImportDTO.class).sheet("数据同步结果").doWrite(i6000ImportLogs);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}

	}
}

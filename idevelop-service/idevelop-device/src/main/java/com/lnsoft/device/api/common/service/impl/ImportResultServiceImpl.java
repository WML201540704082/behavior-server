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

import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.device.api.common.dto.ImportResultDTO;
import com.lnsoft.device.api.common.entity.ImportResult;
import com.lnsoft.device.api.common.vo.ImportResultVO;
import com.lnsoft.device.api.common.mapper.ImportResultMapper;
import com.lnsoft.device.api.common.service.IImportResultService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 信通一体化平台导入文件结果下载 服务实现类
 *
 * @author Idevelop
 * @since 2026-03-01
 */
@Service
public class ImportResultServiceImpl extends BaseServiceImpl<ImportResultMapper, ImportResult> implements IImportResultService {

	@Override
	public IPage<ImportResultVO> selectImportResultPage(IPage<ImportResultVO> page, ImportResultVO importResult) {

		IdevelopUser user = SecureUtil.getUser();
		importResult.setCreateUser(user.getUserId());
		return page.setRecords(baseMapper.selectImportResultPage1(page, importResult));
	}


	@Override
	public List<ImportResultVO> selectImportResultPageByUser(ImportResultDTO importResultDTO) {

		Integer importStatus = importResultDTO.getImportStatus();
		Long userId = importResultDTO.getCreateUser();
		String importType = importResultDTO.getImportType();
		return baseMapper.selectImportResultPageByUser(userId, importStatus, importType);
	}

}

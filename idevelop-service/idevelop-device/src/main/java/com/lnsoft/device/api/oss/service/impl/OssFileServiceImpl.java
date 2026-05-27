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
package com.lnsoft.device.api.oss.service.impl;

import com.lnsoft.device.entity.OssFile;
import com.lnsoft.device.vo.OssFileVO;
import com.lnsoft.device.api.oss.mapper.OssFileMapper;
import com.lnsoft.device.api.oss.service.IOssFileService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 信通一体化通用OSS上传文件 服务实现类
 *
 * @author Idevelop
 * @since 2026-02-28
 */
@Service
public class OssFileServiceImpl extends BaseServiceImpl<OssFileMapper, OssFile> implements IOssFileService {

	@Override
	public IPage<OssFileVO> selectOssFilePage(IPage<OssFileVO> page, OssFileVO ossFile) {
		return page.setRecords(baseMapper.selectOssFilePage(page, ossFile));
	}

}

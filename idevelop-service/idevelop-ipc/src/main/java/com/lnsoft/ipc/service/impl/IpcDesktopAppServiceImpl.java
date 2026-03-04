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
package com.lnsoft.ipc.service.impl;

import com.lnsoft.ipc.entity.IpcDesktopApp;
import com.lnsoft.ipc.vo.IpcDesktopAppVO;
import com.lnsoft.ipc.mapper.IpcDesktopAppMapper;
import com.lnsoft.ipc.service.IIpcDesktopAppService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 工控机管控--桌面应用维护表 服务实现类
 *
 * @author Idevelop
 * @since 2026-03-04
 */
@Service
@AllArgsConstructor
public class IpcDesktopAppServiceImpl extends BaseServiceImpl<IpcDesktopAppMapper, IpcDesktopApp> implements IIpcDesktopAppService {

	private IpcDesktopAppMapper ipcDesktopAppMapper;

	@Override
	public IPage<IpcDesktopAppVO> selectIpcDesktopAppPage(IPage<IpcDesktopAppVO> page, IpcDesktopAppVO ipcDesktopApp) {
		return page.setRecords(ipcDesktopAppMapper.selectIpcDesktopAppPage(page, ipcDesktopApp));
	}

}

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

import com.lnsoft.ipc.dto.IpcTerminalDTO;
import com.lnsoft.ipc.entity.IpcTerminal;
import com.lnsoft.ipc.vo.IpcTerminalVO;
import com.lnsoft.ipc.mapper.IpcTerminalMapper;
import com.lnsoft.ipc.service.IIpcTerminalService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 工控机管控--终端管理表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Service
public class IpcTerminalServiceImpl extends BaseServiceImpl<IpcTerminalMapper, IpcTerminal> implements IIpcTerminalService {

	@Override
	public IPage<IpcTerminalVO> selectIpcTerminalPage(IPage<IpcTerminalVO> page, IpcTerminalVO ipcTerminal) {
		return page.setRecords(baseMapper.selectIpcTerminalPage(page, ipcTerminal));
	}

	@Override
	public List<IpcTerminalVO> terminalRank(IpcTerminalDTO ipcTerminalDTO) {
		return baseMapper.terminalRank(ipcTerminalDTO);
	}

}

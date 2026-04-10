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
package com.lnsoft.ipc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.ipc.dto.IpcUserDTO;
import com.lnsoft.ipc.entity.IpcFaceLoginLog;
import com.lnsoft.ipc.entity.IpcUserTime;
import com.lnsoft.ipc.vo.IpcUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Mapper
public interface IpcFaceLoginLogMapper extends BaseMapper<IpcFaceLoginLog> {

	/**
	 * 用户登录拍民
	 * @param ipcUserDTO
	 * @return
	 */
    List<IpcUserVO> userRank(IpcUserDTO ipcUserDTO);
}

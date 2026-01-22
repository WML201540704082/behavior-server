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
package com.lnsoft.ipc.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.ipc.dto.IpcUserDTO;
import com.lnsoft.ipc.entity.IpcUser;
import com.lnsoft.ipc.vo.IpcUserVO;
import com.lnsoft.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 工控机管控--用户表 服务类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
public interface IIpcUserService extends BaseService<IpcUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipcUser
	 * @return
	 */
	IPage<IpcUserVO> selectIpcUserPage(IPage<IpcUserVO> page, IpcUserVO ipcUser);

	/**
	 * 人脸照片上传接口
	 * @param file
	 * @return
	 */
    R<String> upload(MultipartFile file) throws IOException;

	/**
	 * 新增用户
	 * @param ipcUser
	 * @return
	 */
    R insert(IpcUser ipcUser);

	/**
	 * 修改用户信息
	 * @param ipcUser
	 * @return
	 */
	R edit(IpcUser ipcUser);

	/**
	 * 删除用户
	 * @param ipcUser
	 * @return
	 */
	R delete(IpcUser ipcUser);

	/**
	 * 用户使用时间排名
	 * @param ipcUserDTO
	 * @return
	 */
	R<List<IpcUserVO>> userRank(IpcUserDTO ipcUserDTO);

}

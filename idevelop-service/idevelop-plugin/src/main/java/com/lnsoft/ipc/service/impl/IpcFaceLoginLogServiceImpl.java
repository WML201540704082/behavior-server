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

import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.ipc.entity.IpcFaceLoginLog;
import com.lnsoft.ipc.entity.IpcUserTime;
import com.lnsoft.ipc.mapper.IpcFaceLoginLogMapper;
import com.lnsoft.ipc.mapper.IpcUserTimeMapper;
import com.lnsoft.ipc.service.IIpcFaceLoginLogService;
import com.lnsoft.ipc.service.IIpcUserTimeService;
import org.springframework.stereotype.Service;

/**
 * 工控机管控--用户表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Service
public class IpcFaceLoginLogServiceImpl extends BaseServiceImpl<IpcFaceLoginLogMapper, IpcFaceLoginLog> implements IIpcFaceLoginLogService {

}

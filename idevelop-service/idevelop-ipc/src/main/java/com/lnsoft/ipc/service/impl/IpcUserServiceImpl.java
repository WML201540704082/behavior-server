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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.ipc.dto.IpcUserDTO;
import com.lnsoft.ipc.entity.IpcUser;
import com.lnsoft.ipc.entity.IpcUserTime;
import com.lnsoft.ipc.mapper.IpcFaceLoginLogMapper;
import com.lnsoft.ipc.mapper.IpcUserTimeMapper;
import com.lnsoft.ipc.vo.IpcUserVO;
import com.lnsoft.ipc.mapper.IpcUserMapper;
import com.lnsoft.ipc.service.IIpcUserService;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

/**
 * 工控机管控--用户表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Service
public class IpcUserServiceImpl extends BaseServiceImpl<IpcUserMapper, IpcUser> implements IIpcUserService {
	@Resource
	private IpcUserTimeMapper ipcUserTimeMapper;
	@Resource
	private IpcFaceLoginLogMapper ipcFaceLoginLogMapper;

	@Override
	public IPage<IpcUserVO> selectIpcUserPage(IPage<IpcUserVO> page, IpcUserVO ipcUser) {
		return page.setRecords(baseMapper.selectIpcUserPage(page, ipcUser));
	}

	@Override
	public R<String> upload(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return R.fail("请上传图片！");
		}
		try {
			// 2. 获取文件输入流
			InputStream inputStream = file.getInputStream();

			// 3. 将输入流转换为字节数组
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024]; // 缓冲区大小，可以调整
			int bytesRead;
			// 循环读取输入流到缓冲区
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, bytesRead);
			}
			// 将 ByteArrayOutputStream 中的数据转换为字节数组
			byte[] bytes = byteArrayOutputStream.toByteArray();

			// 4. 对字节数组进行 Base64 编码
			String base64String = Base64.getEncoder().encodeToString(bytes);

			// 5. 构建并返回成功响应
			// 通常会在 Base64 字符串前加上 "data:image/类型;base64," 的前缀，方便前端直接使用
			String fullBase64String = "data:" + file.getContentType() + ";base64," + base64String;
			return R.data(fullBase64String);

		} catch (IOException e) {
			return R.fail("上传失败，请重试！");
		}
	}

	@Override
	public R insert(IpcUser ipcUser) {
		ipcUser.setIsSync("1");
		//新增用户表
		baseMapper.insert(ipcUser);
		//新增用户可登录时间表
		List<IpcUserTime> timeList = ipcUser.getTimeList();
		for (IpcUserTime ipcUserTime : timeList) {
			ipcUserTime.setUserId(ipcUser.getId());
			ipcUserTime.setIsSync("1");
			ipcUserTimeMapper.insert(ipcUserTime);
		}
		return R.success("新增成功");
	}

	@Override
	public R edit(IpcUser ipcUser) {
		ipcUser.setIsSync("1");
		baseMapper.updateById(ipcUser);
		if (ObjectUtil.isNotEmpty(ipcUser.getTimeList())) {
			LambdaQueryWrapper<IpcUserTime> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(IpcUserTime::getUserId, ipcUser.getId());
			ipcUserTimeMapper.delete(queryWrapper);
			for (IpcUserTime ipcUserTime : ipcUser.getTimeList()) {
				ipcUserTime.setIsSync("1");
				ipcUserTimeMapper.insert(ipcUserTime);
			}
		}
		return R.success("修改成功");
	}

	@Override
	public R delete(IpcUser ipcUser) {
		ipcUser.setIsSync("1");
		baseMapper.updateById(ipcUser);
		List<IpcUserTime> timeList = ipcUser.getTimeList();
		for (IpcUserTime ipcUserTime : timeList) {
			ipcUserTime.setIsSync("1");
			ipcUserTime.setIsDeleted(1);
			ipcUserTimeMapper.updateById(ipcUserTime);
		}
		baseMapper.deleteById(ipcUser);
		return R.success("删除成功");
	}

	@Override
	public R<List<IpcUserVO>> userRank(IpcUserDTO ipcUserDTO) {
		List<IpcUserVO> list = ipcFaceLoginLogMapper.userRank(ipcUserDTO);
		return R.data(list);
	}

}

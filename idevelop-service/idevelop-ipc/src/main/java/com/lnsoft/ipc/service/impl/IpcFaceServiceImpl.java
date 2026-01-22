package com.lnsoft.ipc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.crypto.sm4.SM4Util;
import com.lnsoft.ipc.contant.MethodConstant;
import com.lnsoft.ipc.contant.OperationTypeConstant;
import com.lnsoft.ipc.contant.ResponseCodeConstant;
import com.lnsoft.ipc.entity.*;
import com.lnsoft.ipc.faceCommon.*;
import com.lnsoft.ipc.mapper.*;
import com.lnsoft.ipc.service.IIpcFaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IpcFaceServiceImpl implements IIpcFaceService {
	@Resource
	private IpcUserMapper ipcUserMapper;
	@Resource
	private IpcUserTimeMapper ipcUserTimeMapper;
	@Resource
	private IpcFaceLoginLogMapper ipcFaceLoginLogMapper;
	@Resource
	private IpcTerminalMapper ipcTerminalMapper;
	@Resource
	private IpcTerminalMonitoringMapper ipcTerminalMonitoringMapper;

	@Override
	public Response upload(UserRequest userRequest, HttpServletRequest request) {
		Data data = new Data();
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		String method = userRequest.getMethod();
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getUserList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		String type = null;
		LambdaQueryWrapper<IpcUser> queryWrapper = new LambdaQueryWrapper<>();
		if (MethodConstant.UPLOAD_ADMIN.equals(method)) {
			//管理员信息
			type = "管理员";
		} else if (MethodConstant.UPLOAD_TEMP.equals(method)) {
			//临时用户信息
			type = "临时用户";
		}

		List<FaceUser> userList = userRequest.getData().getUserList();
		for (FaceUser faceUser : userList) {
			IpcUser ipcUser = new IpcUser();
			ipcUser.setId(faceUser.getId());
			ipcUser.setName(faceUser.getUsername());
			ipcUser.setUserType(type);
			ipcUser.setDepartment(faceUser.getDepartment());
			ipcUser.setUpdateTime(faceUser.getOperationTime());
			ipcUser.setFace(faceUser.getFaceData());
			ipcUser.setTerminal(ip);
			//对密码进行加密 todo
			String userPwd = faceUser.getUserPwd();

			ipcUser.setPassword(faceUser.getUserPwd());
			ipcUser.setIsSync("0");
			if (OperationTypeConstant.DELETE.equals(faceUser.getType())) {
				int delete = ipcUserMapper.deleteById(ipcUser);
				if (delete == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.ADD.equals(faceUser.getType())) {
				String id = faceUser.getId();
				IpcUser user = ipcUserMapper.selectById(id);
				if (ObjectUtil.isNotEmpty(user)) {
					continue;
				}
				int insert = ipcUserMapper.insert(ipcUser);
				if (insert == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.UPDATE.equals(faceUser.getType())) {
				IpcUser select = ipcUserMapper.selectById(ipcUser);
				if (ObjectUtil.isEmpty(select)) {
					int insert = ipcUserMapper.insert(ipcUser);
					if (insert == 0) {
						//todo 失败机制

					}
				} else {
					int update = ipcUserMapper.updateById(ipcUser);
					if (update == 0) {
						//todo 失败机制

					}
				}
			}
		}
		//查询本地需要同步的数据
		IpcUser user = new IpcUser();
		user.setTerminal(ip);
		user.setUserType(type);
		user.setIsSync("1");
		List<IpcUser> ipcUsers = ipcUserMapper.getList(user);
		List<FaceUser> faceUsers = new ArrayList<>();
		for (IpcUser ipcUser : ipcUsers) {
			FaceUser faceUser = new FaceUser();
			faceUser.setFaceData(ipcUser.getFace());
			faceUser.setDepartment(ipcUser.getDepartment());
			faceUser.setUsername(ipcUser.getName());
			faceUser.setId(ipcUser.getId());
			faceUser.setOperationTime(new Date());
			faceUser.setType(ipcUser.getIsDeleted() == 1 ? "delete" : "update");
			faceUser.setUserPwd(ipcUser.getPassword());
			faceUsers.add(faceUser);
			ipcUser.setIsSync("0");
			ipcUserMapper.editById(ipcUser);
		}
		data.setUserList(faceUsers);
		return Response.data(data);
	}

	@Override
	public Response uploadTime(UserRequest userRequest) {
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getTime())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<FaceTime> faceTimeList = userRequest.getData().getTime();
		for (FaceTime faceTime : faceTimeList) {
			IpcUserTime ipcUserTime = new IpcUserTime();
			ipcUserTime.setId(faceTime.getId());
			ipcUserTime.setUserId(faceTime.getUserId());
			ipcUserTime.setLoginBeginTime(faceTime.getStartTime());
			ipcUserTime.setLoginEndTime(faceTime.getEndTime());
			ipcUserTime.setUpdateTime(faceTime.getOperationTime());
			ipcUserTime.setIsSync("0");
			if (OperationTypeConstant.DELETE.equals(faceTime.getType())) {
				int delete = ipcUserTimeMapper.deleteById(ipcUserTime);
				if (delete == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.ADD.equals(faceTime.getType())) {
				int insert = ipcUserTimeMapper.insert(ipcUserTime);
				if (insert == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.UPDATE.equals(faceTime.getType())) {
				IpcUserTime select = ipcUserTimeMapper.selectById(ipcUserTime);
				if (ObjectUtil.isEmpty(select)) {
					int insert = ipcUserTimeMapper.insert(ipcUserTime);
					if (insert == 0) {
						//todo 失败机制

					}
				} else {
					int update = ipcUserTimeMapper.updateById(ipcUserTime);
					if (update == 0) {
						//todo 失败机制

					}
				}
			}
		}
		//查询需要同步的数据
		IpcUserTime userTime = new IpcUserTime();
		userTime.setIsSync("1");
		List<IpcUserTime> ipcUserTimes = ipcUserTimeMapper.getList(userTime);
		List<FaceTime> faceTimes = new ArrayList<>();
		for (IpcUserTime ipcUserTime : ipcUserTimes) {
			FaceTime faceTime = new FaceTime();
			faceTime.setId(ipcUserTime.getId());
			faceTime.setUserId(ipcUserTime.getUserId());
			faceTime.setStartTime(ipcUserTime.getLoginBeginTime());
			faceTime.setEndTime(ipcUserTime.getLoginEndTime());
			faceTime.setType(ipcUserTime.getIsDeleted() == 1 ? "delete" : "update");
			faceTime.setOperationTime(new Date());
			faceTimes.add(faceTime);
			ipcUserTime.setIsSync("0");
			ipcUserTimeMapper.updateById(ipcUserTime);
		}
		Data data = new Data();
		data.setTime(faceTimes);
		return Response.data(data);
	}

	@Override
	public Response uploadLog(UserRequest userRequest, HttpServletRequest request) {
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//根据ip获取终端信息
		LambdaQueryWrapper<IpcTerminal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcTerminal::getIp, ip).eq(IpcTerminal::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getLogList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<FaceLog> logList = userRequest.getData().getLogList();

//		for (FaceLog faceLog : logList) {
//			IpcTerminalMonitoring monitoring = new IpcTerminalMonitoring();
//			monitoring.setIp(ip);
//			monitoring.setUserName(faceLog.getUsername());
//			monitoring.setOpenTime(faceLog.getLoginTime());
//			monitoring.setShowdownTime(faceLog.getLogoutTime());
//			long between = ChronoUnit.SECONDS.between(faceLog.getLoginTime(), faceLog.getLogoutTime());
//			monitoring.setActivityLength(between);
//			monitoring.setOnlineLength(between);
//			monitoring.setCreateTime(new Date());
//			ipcTerminalMonitoringMapper.insert(monitoring);
//		}

		for (FaceLog faceLog : logList) {
			IpcFaceLoginLog ipcFaceLoginLog = new IpcFaceLoginLog();
			ipcFaceLoginLog.setLoginTime(faceLog.getLoginTime());
			ipcFaceLoginLog.setLogoutTime(faceLog.getLogoutTime());
			ipcFaceLoginLog.setUsername(faceLog.getUsername());
			ipcFaceLoginLog.setDepartment(faceLog.getDepartment());
			ipcFaceLoginLogMapper.insert(ipcFaceLoginLog);
		}
		return Response.code(ResponseCodeConstant.SUCCESS);
	}

	@Override
	public Response downUser(UserRequest userRequest,HttpServletRequest request) throws Exception {
		String method = userRequest.getMethod();
		String type = null;
		if (MethodConstant.DOWN_ADMIN.equals(method)) {
			//管理员信息
			type = "管理员";
		} else if (MethodConstant.DOWN_TEMP.equals(method)) {
			//临时用户信息
			type = "临时用户";
		}
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//查询本地需要同步的数据
		IpcUser user = new IpcUser();
		user.setTerminal(ip);
		user.setUserType(type);
		user.setIsSync("1");
		List<IpcUser> ipcUsers = ipcUserMapper.getList(user);
		List<FaceUser> faceUsers = new ArrayList<>();
		for (IpcUser ipcUser : ipcUsers) {
			FaceUser faceUser = new FaceUser();
			faceUser.setFaceData(ipcUser.getFace());
			faceUser.setDepartment(ipcUser.getDepartment());
			faceUser.setUsername(ipcUser.getName());
			faceUser.setId(ipcUser.getId());
			faceUser.setOperationTime(new Date());
			faceUser.setUserPwd(ipcUser.getPassword());
			faceUser.setType(ipcUser.getIsDeleted() == 1 ? "delete" : "update");
			faceUsers.add(faceUser);
			ipcUser.setIsSync("0");
			ipcUserMapper.editById(ipcUser);
		}
		Data data = new Data();
		data.setUserList(faceUsers);
		return Response.data(data);
	}

	@Override
	public Response downTime(UserRequest userRequest) throws Exception {
		//查询需要同步的数据
		LambdaQueryWrapper<IpcUserTime> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcUserTime::getIsSync, "1")
			.and(i -> i.eq(IpcUserTime::getIsDeleted, 0).or().eq(IpcUserTime::getIsDeleted, 1));
		List<IpcUserTime> ipcUserTimes = ipcUserTimeMapper.selectList(queryWrapper);
		List<FaceTime> faceTimes = new ArrayList<>();
		for (IpcUserTime ipcUserTime : ipcUserTimes) {
			FaceTime faceTime = new FaceTime();
			faceTime.setId(ipcUserTime.getId());
			faceTime.setUserId(ipcUserTime.getUserId());
			faceTime.setStartTime(ipcUserTime.getLoginBeginTime());
			faceTime.setEndTime(ipcUserTime.getLoginEndTime());
			faceTime.setType(ipcUserTime.getIsDeleted() == 1 ? "delete" : "update");
			faceTime.setOperationTime(new Date());
			faceTimes.add(faceTime);
			ipcUserTime.setIsSync("0");
			ipcUserTimeMapper.editById(ipcUserTime);
		}
		Data data = new Data();
		data.setTime(faceTimes);
		return Response.data(data);
	}

	@Override
	public Response uploadTimeList(UserRequest userRequest,HttpServletRequest request) {
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//根据ip获取终端信息
		LambdaQueryWrapper<IpcTerminal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcTerminal::getIp, ip).eq(IpcTerminal::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getTimeList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<FaceOnOffTime> timeList = userRequest.getData().getTimeList();
		for (FaceOnOffTime faceOnOffTime : timeList) {
			IpcTerminalMonitoring monitoring = new IpcTerminalMonitoring();
			monitoring.setIp(ip);
			monitoring.setOpenTime(faceOnOffTime.getPowerOnTime());
			monitoring.setShowdownTime(faceOnOffTime.getPowerOffTime());
			long between = ChronoUnit.SECONDS.between(faceOnOffTime.getPowerOnTime(), faceOnOffTime.getPowerOffTime());
			monitoring.setActivityLength(between);
			monitoring.setOnlineLength(between);
			monitoring.setCreateTime(new Date());
			ipcTerminalMonitoringMapper.insert(monitoring);
		}
		return Response.code(ResponseCodeConstant.SUCCESS);
	}
}
